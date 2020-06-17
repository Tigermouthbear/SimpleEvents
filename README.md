# SimpleEvents
A simple Java EventManager using lambdas

## Gradle
```
repositories {
    maven {
        name = "tigr.dev"
        url = "https://maven.tigr.dev"
    }
}

dependencies {
    compile "dev.tigr:simpleevents:1.0"
}
```

## Java Example
```
public class Test {
	static EventManager EVENT_MANAGER = new EventManager(); // Create new EventManager

	public static void main(String[] args) {
		EVENT_MANAGER.register(Test.class); // registers static listeners of class
		EVENT_MANAGER.unregister(lowest); // unregisters single listener
		EVENT_MANAGER.register(lowest); // registers single listener
		EVENT_MANAGER.register(new Test()); // registers non-static listeners of object

		TestEvent event = EVENT_MANAGER.post(new TestEvent()); // Create event and post it to the EventManager
		System.out.println("\n");
		System.out.println(event.text); // prints "lowest"
		System.out.println(event.isCancelled()); // prints false
	}

	@EventHandler
	private static EventListener<TestEvent> lowest = new EventListener<>(Priority.LOWEST, event -> {
		System.out.println("lowest called!");

		event.text = "lowest";
		event.setCancelled(false);
	});

	@EventHandler
	private static EventListener<TestEvent> highest = new EventListener<>(Priority.HIGHEST, event -> {
		System.out.println("highest called!");

		event.text = "highest";
		event.setCancelled(true);
	});

	@EventHandler
	private EventListener<TestEvent> defaulT = new EventListener<>(event -> {
		System.out.println("default called!");
	});
}

// TestEvent does not need to extend Event, unless the built in functions of a SimpleEvent Event is needed
class TestEvent extends Event {
	String text = "original";
}
```
