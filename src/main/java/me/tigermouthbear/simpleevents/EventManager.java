package me.tigermouthbear.simpleevents;

import me.tigermouthbear.simpleevents.event.Event;
import me.tigermouthbear.simpleevents.listener.EventHandler;
import me.tigermouthbear.simpleevents.listener.EventListener;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
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
	 * Holds all {@link EventListener}s for accepting consumers in post()
	 * Uses a CopyOnWrite to prevent Concurrent Modification Exceptions when dispatching events
	 */
	private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

	/**
	 * Accepts all consumers from the {@link EventListener}s of the event passed through
	 * @param event event to post to {@link EventManager}
	 * @return Event passed through
	 */
	public <T> T post(T event) {
		// Sorting is not needed here, as it is done when objects are added to the list
		for (EventListener listener : this.listeners) {
			if (listener.getEventClass() == event.getClass()) {
				listener.getConsumer().accept(event);
			}
		}
		return event;
	}

	/**
	 * Adds all the {@link EventListener}s in an object's class to the listener list for receiving posts
	 * @param obj Instance of class with {@link EventListener}s to add to listeners
	 */
	public void register(Object obj) {
		for(Field field: obj.getClass().getDeclaredFields()) {
			if(field.isAnnotationPresent(EventHandler.class)) {
				EventListener listener = null;

				try {
					boolean accessible = field.isAccessible();
					field.setAccessible(true);
					listener = (EventListener) field.get(obj);
					field.setAccessible(accessible);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				if(listener != null) {
					listeners.add(listener);
					// Sort by priority on adding, so continuous sorting can be avoided
					this.listeners.sort(PRIORITY_COMPARATOR);
				}
			}
		}
	}

	/**
	 * Removes all the {@link EventListener}s in an object's class from the listener list
	 * @param obj Instance of class with {@link EventListener}s to remove to listeners
	 */
	public void unregister(Object obj) {
		for(Field field: obj.getClass().getDeclaredFields()) {
			if(field.isAnnotationPresent(EventHandler.class)) {
				EventListener listener = null;

				try {
					boolean accessible = field.isAccessible();
					field.setAccessible(true);
					listener = (EventListener) field.get(obj);
					field.setAccessible(accessible);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				if(listener != null) {
					listeners.remove(listener);
				}
			}
		}
	}

	/**
	 * Adds a listener to the listener list
	 * @param listener {@link EventListener} to add to the listener list
	 */
	public void register(EventListener listener) {
		listeners.add(listener);
		// Sort by priority on adding, so continuous sorting can be avoided
		this.listeners.sort(PRIORITY_COMPARATOR);
	}

	/**
	 * Removes a listener from the listener list
	 * @param listener {@link EventListener} to remove from the listener list
	 */
	public void unregister(EventListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Getter for listeners
	 * @return {@link List} of listeners
	 */
	public List<EventListener> getListeners() {
		return listeners;
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