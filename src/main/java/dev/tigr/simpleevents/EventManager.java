package dev.tigr.simpleevents;

import dev.tigr.simpleevents.event.Event;
import dev.tigr.simpleevents.listener.EventHandler;
import dev.tigr.simpleevents.listener.EventListener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Used to post {@link Event}s to {@link EventListener}s and register/unregister {@link EventListener}s
 * @author Tigermouthbear
 * @since 3/11/20
 */
public class EventManager {
	/**
	 * Stores {@link PriorityComparator} for use in post()
	 */
	private static final PriorityComparator PRIORITY_COMPARATOR = new PriorityComparator();

	/**
	 * Stores a map with key of Event and values of the EventListeners for that event
	 */
	private final Map<Class<?>, List<EventListener>> listenerMap = new HashMap<>();

	/**
	 * Accepts all consumers from the {@link EventListener}s of the event passed through
	 * @param event event to post to {@link EventManager}
	 * @param <T> event type to post
	 * @return Event passed through
	 */
	public <T> T post(T event) {
		Class<?> eventClass = event.getClass();
		if(listenerMap.containsKey(eventClass)) listenerMap.get(eventClass).forEach(eventListener -> eventListener.invoke(event));
		return event;
	}

	/**
	 * Registers a listener to the {@link EventManager}
	 * @param listener {@link EventListener} to add to the listener list
	 */
	public void register(EventListener listener) {
		Class<?> eventClass = listener.getEventClass();

		// get or set listener list for class
		List<EventListener> listeners;
		if(listenerMap.containsKey(eventClass)) listeners = listenerMap.get(eventClass);
		else {
			listeners = new CopyOnWriteArrayList<>();
			listenerMap.put(eventClass, listeners);
		}

		// add listener and sort listeners by priority
		listeners.add(listener);
		listeners.sort(PRIORITY_COMPARATOR);
	}

	/**
	 * Unregisters a listener from the {@link EventManager}
	 * @param listener {@link EventListener} to remove from the listener list
	 */
	public void unregister(EventListener listener) {
		List<EventListener> listeners = listenerMap.get(listener.getEventClass());
		if(listeners != null) listeners.remove(listener);
	}

	/**
	 * Registers the listener of a field
	 * @param field field to be registered
	 * @param object object which field resides in
	 */
	public void register(Field field, Object object) {
		EventListener listener = getListenerFromField(field, object);
		if(listener != null) register(listener);
	}

	/**
	 * Unregisters the listener of a field
	 * @param field field to be unregistered
	 * @param object object which field resides in
	 */
	public void unregister(Field field, Object object) {
		EventListener listener = getListenerFromField(field, object);
		if(listener != null) unregister(listener);
	}

	/**
	 * Registers all {@link EventListener}s in an object
	 * @param object Instance of class with {@link EventListener}s to add to listeners
	 */
	public void register(Object object) {
		Class<?> clazz = object.getClass();
		do {
			for(Field field: clazz.getDeclaredFields()) {
				if(!Modifier.isStatic(field.getModifiers()) && field.isAnnotationPresent(EventHandler.class))
					register(field, object);
			}
		} while((clazz = clazz.getSuperclass()) != Object.class);
	}

	/**
	 * Unregisters all {@link EventListener}s in an object
	 * @param object Instance of class with {@link EventListener}s to remove to listeners
	 */
	public void unregister(Object object) {
		Class<?> clazz = object.getClass();
		do {
			for(Field field: clazz.getDeclaredFields()) {
				if(!Modifier.isStatic(field.getModifiers()) && field.isAnnotationPresent(EventHandler.class))
					unregister(field, object);
			}
		} while((clazz = clazz.getSuperclass()) != Object.class);
	}

	/**
	 * Registers all static {@link EventListener}s in a class
	 * @param clazz parent class of listeners to register
	 */
	public void register(Class<?> clazz) {
		do {
			for(Field field: clazz.getDeclaredFields()) {
				if(Modifier.isStatic(field.getModifiers())&& field.isAnnotationPresent(EventHandler.class))
					register(field, null); // pass null for static
			}
		} while((clazz = clazz.getSuperclass()) != Object.class);
	}

	/**
	 * Unregisters all static {@link EventListener}s in a class
	 * @param clazz parent class of listeners to unregister
	 */
	public void unregister(Class<?> clazz) {
		do {
			for(Field field: clazz.getDeclaredFields()) {
				if(Modifier.isStatic(field.getModifiers())&& field.isAnnotationPresent(EventHandler.class))
					unregister(field, null); // pass null for static
			}
		} while((clazz = clazz.getSuperclass()) != Object.class);
	}

	/**
	 * Gets the {@link EventListener} from a field
	 * @param field listener to get
	 * @param object object the listener is in
	 * @return the {@link EventListener}
	 */
	private static EventListener getListenerFromField(Field field, Object object) {
		EventListener listener = null;

		try {
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			listener = (EventListener) field.get(object);
			field.setAccessible(accessible);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return listener;
	}

	/**
	 * Comparator for {@link EventListener} priorities
	 */
	private static class PriorityComparator implements Comparator<EventListener> {
		public int compare(EventListener listener1, EventListener listener2) {
			return Integer.compare(listener2.getPriority(), listener1.getPriority());
		}
	}
}