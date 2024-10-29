package edu.ncsu.csc216.app_manager.model.command;

/**
 * This class represents a Command. Command interacts with the Application class
 * through a composition relationship
 * 
 * @author Gabe Frain
 */
public class Command {

	/** A constant representing a completed review */
	public static final String R_REVCOMPLETED = "ReviewCompleted";
	/** A constant representing a completed interview */
	public static final String R_INTCOMPLETED = "InterviewCompleted";
	/** A constant representing a completed reference check */
	public static final String R_REFCHKCOMPLETED = "ReferenceCheckCompleted";
	/** A constant representing a completed offer */
	public static final String R_OFFERCOMPLETED = "OfferCompleted";

	/** A command value representing a command */
	private CommandValue c;
	/** A string representing a reviewer Id */
	private String reviewerId;
	/** A resolution representing a resolution */
	private Resolution r;
	/** A string representing a note */
	private String note;

	/**
	 * This enumeration represents a list of command values specific to the command
	 * class
	 */
	public enum CommandValue {
		/** Accept Command */
		ACCEPT, 
		/** Reject Command */
		REJECT, 
		/** Standby Command */
		STANDBY, 
		/** Reopen Command */
		REOPEN
	}

	/**
	 * This enumeration represents a list of resolutions specific to the command
	 * class
	 */
	public enum Resolution {
		/** Review Completed Resolution */
		REVCOMPLETED, 
		/** Interview Completed Resolution */
		INTCOMPLETED, 
		/** Reference Check Completed Resolution */
		REFCHKCOMPLETED, 
		/** Offer Completed Resolution */
		OFFERCOMPLETED
	}

	/**
	 * Constructs a command object using the given parameters. Throws an illegal
	 * argument exception if four specific instances occur with the parameters
	 * 
	 * @param c          Command Value of the Command
	 * @param reviewerId Reviewer Id of the Command
	 * @param r          Resolution of the Command
	 * @param note       Note of the Command
	 * @throws IllegalArgumentException if null command value, command value accept
	 *                                  and null/empty reviewer id, command value
	 *                                  standby/reject with null resolution, or
	 *                                  null/empty note
	 */
	public Command(CommandValue c, String reviewerId, Resolution r, String note) {
		if (c == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		if (c == CommandValue.ACCEPT && (reviewerId == null || "".equals(reviewerId))) {
			throw new IllegalArgumentException("Invalid information.");
		}
		if ((c == CommandValue.STANDBY || c == CommandValue.REJECT) && r == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		if (note == null || "".equals(note)) {
			throw new IllegalArgumentException("Invalid information.");
		}
		this.c = c;
		this.reviewerId = reviewerId;
		this.r = r;
		this.note = note;
	}

	/**
	 * This method gets c
	 * 
	 * @return the CommandValue c
	 */
	public CommandValue getCommand() {
		return c;
	}

	/**
	 * This method gets reviewer id
	 * 
	 * @return the String reviewerId
	 */
	public String getReviewerId() {
		return reviewerId;
	}

	/**
	 * This method gets r
	 * 
	 * @return the Resolution r
	 */
	public Resolution getResolution() {
		return r;
	}

	/**
	 * This method gets note
	 * 
	 * @return the String note
	 */
	public String getNote() {
		return note;
	}

}