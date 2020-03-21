package me.tigermouthbear.simpleevents.listener;

import me.tigermouthbear.simpleevents.EventManager;
import me.tigermouthbear.simpleevents.event.IEvent;
import net.jodah.typetools.TypeResolver;

import java.util.function.Consumer;

/**
 * Links an {@link IEvent} to a consumer through the {@link EventManager}
 * @author Tigermouthbear
 * @since 3/11/20
 */
public class EventListener<T extends IEvent> {
	/**
	 * Stores priority of {@link EventListener}
	 */
	private int priority;

	/**
	 * Stores consumer of {@link EventListener}
	 */
	private Consumer<T> consumer;

	/**
	 * Stores class of {@link IEvent} listened for
	 */
	private Class<T> eventClass;

	/**
	 * Constructor for {@link EventListener} with the default {@link Priority}
	 * @param consumer Consumer to accept on listen
	 */
	public EventListener(Consumer<T> consumer) {
		this(Priority.DEFAULT, consumer);
	}

	/**
	 * Constructor for {@link EventListener}
	 * @param priority Priority of listener
	 * @param consumer Consumer to accept on listen
	 */
	@SuppressWarnings("unchecked")
	public EventListener(int priority, Consumer<T> consumer) {
		this.priority = priority;
		this.consumer = consumer;
		this.eventClass = (Class<T>)TypeResolver.resolveRawArgument(Consumer.class, consumer.getClass());
	}

	/**
	 * Getter for
	 * @return Priority of {@link EventListener}
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Getter for consumer
	 * @return Consumer passed through {@link EventListener}
	 */
	public Consumer<T> getConsumer() {
		return consumer;
	}

	/**
	 * Getter for eventClass
	 * @return Class of {@link IEvent} which it is listening for
	 */
	public Class<T> getEventClass() {
		return eventClass;
	}
}