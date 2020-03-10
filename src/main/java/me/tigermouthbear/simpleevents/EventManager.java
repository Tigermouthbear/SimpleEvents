package me.tigermouthbear.simpleevents;

import java.util.ArrayList;

/**
 * Used to post {@link Event}s to {@link EventListener}s
 * @author Tigermouthbear
 */
public class EventManager {
	/***
	 * Holds all {@link EventListener}s for accpeting consumers in post()
	 */
	static ArrayList<EventListener> listeners = new ArrayList<>();

	/***
	 * Accepts all consumers from {@link EventListener}s of the {@link Event} passed through
	 * @param event {@link Event} to post to {@link EventManager}
	 * @param <T> Gentrifies {@link Event}
	 * @return Event passed through
	 */
	public <T extends Event> T post(T event) {
		for(EventListener listener: listeners) {
			if(listener.clazz == event.getClass()) listener.consumer.accept(event);
		}
		return event;
	}
}
