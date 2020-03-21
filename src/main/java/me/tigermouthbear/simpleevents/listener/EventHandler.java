package me.tigermouthbear.simpleevents.listener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to mark an {@link EventListener} so it can be registered to the {@link me.tigermouthbear.simpleevents.EventManager}
 * @author Tigermouthbear
 * @since 3/21/20
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
}