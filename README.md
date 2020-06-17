# SimpleEvents
A simple Java EventManager using lambdas

## Gradle
```
repositories {
    maven {
   	    name = "jitpack.io"
   		url = "https://jitpack.io"
   	}
}

dependencies {
    compile "com.github.Tigermouthbear:SimpleEvents:master-SNAPSHOT"
}
```

## Java
```
public class Test {
	static EventManager EVENT_MANAGER = new EventManager(); // Create new EventManager

	public static void main(String[] args) {
		EVENT_MANAGER.register(new Test()); // Register object to EventManager
		TestEvent event = EVENT_MANAGER.post(new TestEvent()); // Create event and post it to the EventManager
		System.out.println(event.text); // prints "lowest"
		System.out.println(event.isCancelled()); // prints false
	}

	@EventHandler
	EventListener<TestEvent> lowest = new EventListener<>(Priority.LOWEST, event -> {
		event.text = "lowest";
		event.setCancelled(false);
	});

	@EventHandler
	EventListener<TestEvent> highest = new EventListener<>(Priority.HIGHEST, event -> {
		event.text = "highest";
		event.setCancelled(true);
	});
}

// TestEvent does not need to extend Event, unless the built in functions of an SimpleEvent Event is needed
class TestEvent extends Event {
	String text = "original";
}
```
