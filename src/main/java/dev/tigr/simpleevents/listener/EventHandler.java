package dev.tigr.simpleevents.listener;

import dev.tigr.simpleevents.EventManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to mark an {@link EventListener} so it can be registered to the {@link EventManager}
 * @author Tigermouthbear
 * @since 3/21/20
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
}