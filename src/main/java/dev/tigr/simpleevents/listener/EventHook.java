package dev.tigr.simpleevents.listener;

/**
 * Functional interface created by an {@link EventListener} and invoked when an event is posted to its manager
 * @author Tigermouthbear
 * @since 6/16/20
 * @param <T> event type
 */
@FunctionalInterface
public interface EventHook<T> {
	void invoke(T event);
}
