package me.tigermouthbear.simpleevents.event;

/**
 * The basic implementation of {@link IEvent}
 * @author Tigermouthbear
 * @since 3/11/20
 */
public abstract class Event implements IEvent {
	/**
	 * Result of {@link Event},  default Result.NONE
	 */
	private Result result = Result.NONE;

	/**
	 * Stores whether the {@link Event} is cancelled
	 */
	private boolean isCancelled = false;

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

	/**
	 * Gets if the {@link Event} is cancelled
	 * @return the boolean value of if the {@link Event} is cancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * Sets whether the {@link Event} is cancelled or not
	 * @param value Boolean value to set the cancelled value to
	 */
	public void setCancelled(boolean value) {
		isCancelled = value;
	}
}