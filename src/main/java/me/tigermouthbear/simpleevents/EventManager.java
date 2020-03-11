package me.tigermouthbear.simpleevents;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Used to post {@link Event}s to {@link EventListener}s
 * @author Tigermouthbear
 * @since 3/11/20
 */
public class EventManager {
	/**
	 * Stores {@link PriorityComparator} for use in post()
	 */
	private static final PriorityComparator priorityComparator = new PriorityComparator();

	/**
	 * Holds all {@link EventListener}s for accpeting consumers in post()
	 */
	static ArrayList<EventListener> listeners = new ArrayList<>();

	/**
	 * Accepts all consumers from {@link EventListener}s of the {@link Event} passed through
	 * @param event {@link Event} to post to {@link EventManager}
	 * @param <T> Gentrifies {@link Event}
	 * @return Event passed through
	 */
	public <T extends Event> T post(T event) {
		listeners.stream().sorted(priorityComparator).filter(listener ->
				listener.getEventClass() == event.getClass()).forEach(listener ->
						listener.getConsumer().accept(event));

		return event;
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