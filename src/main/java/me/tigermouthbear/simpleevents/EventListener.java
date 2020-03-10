package me.tigermouthbear.simpleevents;

import java.util.function.Consumer;

/**
 * Links an {@link Event} to a consumer through the {@link EventManager}
 * @author Tigermouthbear
 */
public class EventListener<T extends Event> {
	/***
	 * Stores consumer of {@link EventListener}
	 */
	Consumer<T> consumer;

	/***
	 * Stores class of {@link Event} listened for
	 */
	Class<T> clazz;

	/***
	 * Creates an {@link EventListener}
	 * @param clazz Class of {@link Event} listening for
	 * @param consumer Consumer to accept on listen
	 */
	public EventListener(Class<T> clazz, Consumer<T> consumer) {
		this.consumer = consumer;
		this.clazz = clazz;

		EventManager.listeners.add(this);
	}
}