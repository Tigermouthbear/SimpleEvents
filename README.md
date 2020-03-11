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
	// Create new EventManager/EventBus
	static EventManager EVENT_BUS = new EventManager();

	public static void main(String[] args) {
		TestEvent event = EVENT_BUS.post(new TestEvent()); // Create event and post it to the EventManager/EventBus
		System.out.println(event.text); // prints "lowest"
	}

	static EventListener<TestEvent> lowest = new EventListener<>(Priority.LOWEST, event -> event.text = "lowest");
	static EventListener<TestEvent> highest = new EventListener<>(Priority.HIGHEST, event -> event.text = "highest");
}

class TestEvent extends Event {
	String text = "original";
}
```