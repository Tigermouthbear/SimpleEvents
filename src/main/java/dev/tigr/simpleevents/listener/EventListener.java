package dev.tigr.simpleevents.listener;

import dev.tigr.simpleevents.EventManager;
import net.jodah.typetools.TypeResolver;

/**
 * Links an event to a consumer through the {@link EventManager}
 * @author Tigermouthbear
 * @since 3/11/20
 */
public class EventListener<T> {
	/**
	 * Stores priority of {@link EventListener}
	 */
	private int priority;

	/**
	 * Stores consumer of {@link EventListener}
	 */
	private EventHook<T> hook;

	/**
	 * Stores class of the event listened for
	 */
	private Class<T> eventClass;

	/**
	 * Constructor for {@link EventListener} with the default {@link Priority}
	 * @param hook Consumer to accept on listen
	 */
	public EventListener(EventHook<T> hook) {
		this(Priority.DEFAULT, hook);
	}

	/**
	 * Constructor for {@link EventListener}
	 * @param priority Priority of listener
	 * @param hook Consumer to accept on listen
	 */
	@SuppressWarnings("unchecked")
	public EventListener(int priority, EventHook<T> hook) {
		this.priority = priority;
		this.hook = hook;
		this.eventClass = (Class<T>) TypeResolver.resolveRawArgument(EventHook.class, hook.getClass());
	}

	/**
	 * Getter for listener's priority
	 * @return Priority of {@link EventListener}
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Invokes the hook for this listener
	 * @param event event passed through
	 */
	public void invoke(T event) {
		hook.invoke(event);
	}

	/**
	 * Setter for hook
	 * @param value {@link EventHook} to set the hook to
	 */
	public void setHook(EventHook<T> value) {
		hook = value;
	}

	/**
	 * Getter for eventClass
	 * @return Class of the event which it is listening for
	 */
	public Class<T> getEventClass() {
		return eventClass;
	}
}