package me.tigermouthbear.simpleevents;

/**
 * Represents an event, postable to the {@link EventManager}
 * @author Tigermouthbear
 * @since 3/11/20
 */
public abstract class Event {
	/**
	 * Result of {@link Event},  default Result.NONE
	 */
	private Result result = Result.NONE;

	/**
	 * Get result of {@link Event}
	 * @return {@link Result} of event
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * Set result of {@link Event}
	 * @param value Value to set {@link Result} to
	 */
	public void setResult(Result value) {
		result = value;
	}
}