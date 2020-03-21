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
public class Main {
	static EventManager EVENT_MANAGER = new EventManager(); // Create new EventManager

	public static void main(String[] args) {
        EVENT_MANAGER.register(new Main()); // Register object to EventManager
		TestEvent event = EVENT_MANAGER.post(new TestEvent()); // Create event and post it to the EventManager
		System.out.println(event.text); // prints "lowest"
	}

    @EventHandler
	EventListener<TestEvent> lowest = new EventListener<>(Priority.LOWEST, event -> event.text = "lowest");

    @EventHandler
	EventListener<TestEvent> highest = new EventListener<>(Priority.HIGHEST, event -> event.text = "highest");
}

class TestEvent extends Event {
	String text = "original";
}
```