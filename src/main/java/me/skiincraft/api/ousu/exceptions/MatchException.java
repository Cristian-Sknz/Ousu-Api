package me.skiincraft.api.ousu.exceptions;

@SuppressWarnings("serial")
public class MatchException extends RuntimeException {

	private final Exception originalerror;
	
	public MatchException(String message, Exception originalerror) {
		super(message);
		this.originalerror = originalerror;
	}
	
	public Exception getOriginalError() {
		if (originalerror == null) {
			return this;
		}
		return originalerror;
	}

}
