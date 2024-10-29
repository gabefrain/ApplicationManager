package edu.ncsu.csc216.app_manager.model.application;

import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;

/**
 * This class represents an Application. Application interacts with most other
 * classes in the project
 * 
 * @author Gabe Frain
 */
public class Application {

	/** A constant representing the new application type */
	public static final String A_NEW = "New";
	/** A constant representing the old application type */
	public static final String A_OLD = "Old";
	/** A constant representing the hired application type */
	public static final String A_HIRED = "Hired";
	/** A constant representing the review state */
	public static final String REVIEW_NAME = "Review";
	/** A constant representing the interview state */
	public static final String INTERVIEW_NAME = "Interview";
	/** A constant representing the waitlist state */
	public static final String WAITLIST_NAME = "Waitlist";
	/** A constant representing the reference check state */
	public static final String REFCHK_NAME = "RefCheck";
	/** A constant representing the offer state */
	public static final String OFFER_NAME = "Offer";
	/** A constant representing the closed state */
	public static final String CLOSED_NAME = "Closed";

	/** The unique application Id */
	private int appId;
	/** The current state of the application */
	private AppState state;
	/** The type of application */
	private AppType appType;
	/** Summary of application information */
	private String summary;
	/** Reviewers User Id */
	private String reviewer;
	/** If application paperwork has been processed */
	private boolean processPaperwork;
	/** Resolution of application */
	private Resolution resolution;
	/** ArrayList of Notes */
	private ArrayList<String> notes;

	/** Final instance of the Review State */
	private final AppState reviewState = new ReviewState();
	/** Final instance of the Interview State */
	private final AppState interviewState = new InterviewState();
	/** Final instance of the Waitlist State */
	private final AppState waitlistState = new WaitlistState();
	/** Final instance of the Reference Check State */
	private final AppState refChkState = new RefChkState();
	/** Final instance of the Offer State */
	private final AppState offerState = new OfferState();
	/** Final instance of the Closed State */
	private final AppState closedState = new ClosedState();

	/**
	 * This enumeration represents a list of application types specific to the
	 * application class
	 */
	public enum AppType {
		/** New Application */
		NEW,
		/** Old Application */
		OLD,
		/** Hired Application */
		HIRED,
	}

	/**
	 * Constructs an application using the given parameters. Throws an illegal
	 * argument exception if any parameters are null, any strings are empty, or the
	 * application id is less than 1
	 * 
	 * @param appId   Id of application
	 * @param appType Type of application
	 * @param summary Summary of application
	 * @param note    Note for application
	 * @throws IllegalArgumentException if any parameters are null, any strings are
	 *                                  empty or the application id is less than 1
	 */
	public Application(int appId, AppType appType, String summary, String note) {
		setAppId(appId);
		if (appType != AppType.NEW && appType != AppType.OLD && appType != AppType.HIRED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		this.appType = appType;
		setSummary(summary);
		setResolution(null);
		setState(REVIEW_NAME);
		addNote(note);
		setReviewer(null);
		setProcessPaperwork(false);

	}

	/**
	 * Constructs an application using the given parameters. Throws an illegal
	 * argument exception if any of the parameter yield invalid values
	 * 
	 * @param appId            Id of application
	 * @param state            State of application
	 * @param appType          Type of application
	 * @param summary          Summary of application
	 * @param reviewer         Id of application reviewer
	 * @param processPaperwork Application paperwork status
	 * @param resolution       Resolution of application
	 * @param notes            Notes list for application
	 * @throws IllegalArgumentException if any of parameters are invalid or their
	 *                                  combinations are invalid
	 */
	public Application(int appId, String state, String appType, String summary, String reviewer,
			boolean processPaperwork, String resolution, ArrayList<String> notes) {
		setResolution(resolution);
		setAppId(appId);
		setReviewer(reviewer);
		setState(state);
		setAppType(appType);
		setSummary(summary);
		setProcessPaperwork(processPaperwork);
		setNotes(notes);

		if (this.reviewer != null && state == REVIEW_NAME) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (resolution == Command.R_INTCOMPLETED && this.reviewer == null && state == WAITLIST_NAME) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
	}

	/**
	 * Gets the application Id
	 * 
	 * @return the application Id
	 */
	public int getAppId() {
		return appId;
	}

	/**
	 * Sets the application Id
	 * 
	 * @param appId application Id
	 * @throws IllegalArgumentException if application Id is less than 1
	 */
	public void setAppId(int appId) {
		if (appId < 1) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		this.appId = appId;
	}

	/**
	 * Gets the state of the application
	 * 
	 * @return the application state
	 */
	public String getStateName() {
		if (state == reviewState) {
			return reviewState.getStateName();
		} else if (state == interviewState) {
			return interviewState.getStateName();
		} else if (state == waitlistState) {
			return waitlistState.getStateName();
		} else if (state == refChkState) {
			return refChkState.getStateName();
		} else if (state == offerState) {
			return offerState.getStateName();
		} else {
			return closedState.getStateName();
		}
	}

	/**
	 * Sets the state of the application
	 * 
	 * @param state state of the application
	 * @throws IllegalArgumentException if incorrect state name
	 */
	public void setState(String state) {
		switch (state) {
		case REVIEW_NAME:
			if (getResolution() != null) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			this.state = reviewState;
			break;
		case INTERVIEW_NAME:
			if (getResolution() != null) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			if (reviewer == null) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			this.state = interviewState;
			break;
		case WAITLIST_NAME:
			if (getResolution() == null || Command.R_REFCHKCOMPLETED.equals(getResolution())
					|| Command.R_OFFERCOMPLETED.equals(getResolution())) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			this.state = waitlistState;
			break;
		case REFCHK_NAME:
			if (getResolution() != null) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			if (reviewer == null) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			this.state = refChkState;
			break;
		case OFFER_NAME:
			if (getResolution() != null) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			if (reviewer == null) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			this.state = offerState;
			break;
		case CLOSED_NAME:
			if (getResolution() == null) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			this.state = closedState;
			break;
		default:
			throw new IllegalArgumentException("Application cannot be created.");
		}
	}

	/**
	 * Gets the application type
	 * 
	 * @return the application type
	 */
	public String getAppType() {
		if (appType == AppType.NEW) {
			return A_NEW;
		} else if (appType == AppType.OLD) {
			return A_OLD;
		} else {
			return A_HIRED;
		}
	}

	/**
	 * Sets the application type
	 * 
	 * @param appType application type
	 * @throws IllegalArgumentException if application type is not new or old
	 */
	private void setAppType(String appType) {
		switch (appType) {
		case A_NEW:
			if (INTERVIEW_NAME.equals(getStateName()) || REFCHK_NAME.equals(getStateName())
					|| OFFER_NAME.equals(getStateName())) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			if (resolution != null && !Command.R_REVCOMPLETED.equals(getResolution())) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
			this.appType = AppType.NEW;
			break;
		case A_OLD:
			this.appType = AppType.OLD;
			break;
		case A_HIRED:
			this.appType = AppType.HIRED;
			break;
		default:
			throw new IllegalArgumentException("Application cannot be created.");
		}
	}

	/**
	 * Gets the application summary
	 * 
	 * @return the application summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the application summary
	 * 
	 * @param summary application summary
	 * @throws IllegalArgumentException if application summary is null or empty
	 */
	private void setSummary(String summary) {
		if (summary == null || "".equals(summary)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		this.summary = summary;
	}

	/**
	 * Gets the applications reviewer id
	 * 
	 * @return reviewer Id of application
	 */
	public String getReviewer() {
		return reviewer;
	}

	/**
	 * Sets the applications reviewer id
	 * 
	 * @param reviewer application reviewer id
	 */
	private void setReviewer(String reviewer) {
		if ("".equals(reviewer)) {
			this.reviewer = null;
		} else {
			this.reviewer = reviewer;
		}
	}

	/**
	 * Gets the status of the paperwork
	 * 
	 * @return if the paperwork has been processed
	 */
	public boolean isProcessed() {
		return processPaperwork;
	}

	/**
	 * Sets the applications processed paperwork status
	 * 
	 * @param processPaperwork the status of the paperwork
	 * @throws IllegalArgumentException if paperwork is processed in wrong state
	 */
	private void setProcessPaperwork(boolean processPaperwork) {
		if ((getStateName() == REVIEW_NAME || getStateName() == INTERVIEW_NAME) && processPaperwork) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (processPaperwork && (CLOSED_NAME.equals(getStateName()) || WAITLIST_NAME.equals(getStateName()))
				&& resolution == Resolution.REVCOMPLETED) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (!processPaperwork && (REFCHK_NAME.equals(getStateName()) || OFFER_NAME.equals(getStateName()))) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		this.processPaperwork = processPaperwork;
	}

	/**
	 * Gets the resolution of the application
	 * 
	 * @return resolution of application
	 */
	public String getResolution() {
		if (resolution == Resolution.REVCOMPLETED) {
			return Command.R_REVCOMPLETED;
		} else if (resolution == Resolution.INTCOMPLETED) {
			return Command.R_INTCOMPLETED;
		} else if (resolution == Resolution.REFCHKCOMPLETED) {
			return Command.R_REFCHKCOMPLETED;
		} else if (resolution == Resolution.OFFERCOMPLETED) {
			return Command.R_OFFERCOMPLETED;
		} else {
			return null;
		}
	}

	/**
	 * Sets the resolution of the application
	 * 
	 * @param resolution resolution of the application
	 */
	private void setResolution(String resolution) {
		if (resolution == null) {
			this.resolution = null;
		} else {
			switch (resolution) {
			case Command.R_REVCOMPLETED:
				this.resolution = Resolution.REVCOMPLETED;
				break;
			case Command.R_INTCOMPLETED:
				this.resolution = Resolution.INTCOMPLETED;
				break;
			case Command.R_REFCHKCOMPLETED:
				this.resolution = Resolution.REFCHKCOMPLETED;
				break;
			case Command.R_OFFERCOMPLETED:
				this.resolution = Resolution.OFFERCOMPLETED;
				break;
			default:
				this.resolution = null;
			}
		}
	}

	/**
	 * Gets the application notes
	 * 
	 * @return the application notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}

	/**
	 * Sets a list of application notes
	 * 
	 * @param notes the notes to set
	 * @throws IllegalArgumentException if the notes list has a size of 0
	 */
	private void setNotes(ArrayList<String> notes) {
		if (notes == null || notes.size() == 0) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		this.notes = notes;
	}

	/**
	 * Adds a note to the notes list
	 * 
	 * @param note note being added to notes list
	 * @throws IllegalArgumentException if note is null or empty
	 */
	private void addNote(String note) {
		if (note == null || "".equals(note)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if (notes == null) {
			notes = new ArrayList<String>();
		}
		notes.add("[" + getStateName() + "] " + note);
	}

	/**
	 * Creates a string representation of the notes
	 * 
	 * @return notes string
	 */
	public String getNotesString() {
		String output = "";
		for (int i = 0; i < notes.size(); i++) {
			output = output + "-" + notes.get(i) + "\n";
		}
		
		return output;
	}

	/**
	 * Creates a string representation of Application
	 * 
	 * @return application string
	 */
	public String toString() {
		String output = "*" + getAppId() + "," + getStateName() + "," + getAppType() + "," + getSummary() + ",";
		output = output + getReviewer() + "," + isProcessed() + ",";
		if (getResolution() == null) {
			output = output + "\r\n" + getNotesString();
		} else {
			output = output + getResolution() + "\r\n" + getNotesString();
		}
		return output;
	}

	/**
	 * Drives the finite state machine by delegating to the correct updateState
	 * command based on the current state
	 * 
	 * @param command command being executed by the application
	 * @throws UnsupportedOperationException if the command is not appropriate for
	 *                                       the current state
	 */
	public void update(Command command) {
		switch (getStateName()) {
		case REVIEW_NAME:
			reviewState.updateState(command);
			break;
		case INTERVIEW_NAME:
			interviewState.updateState(command);
			break;
		case WAITLIST_NAME:
			waitlistState.updateState(command);
			break;
		case REFCHK_NAME:
			refChkState.updateState(command);
			break;
		case OFFER_NAME:
			offerState.updateState(command);
			break;
		case CLOSED_NAME:
			closedState.updateState(command);
			break;
		default:
			break;
		}
	}

	/**
	 * Interface for states in the Application State Pattern. All concrete
	 * application states must implement the AppState interface. The AppState
	 * interface should be a private interface of the Application class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 * @author Dr. Chandrika Satyavolu (jsatyav@ncsu.edu)
	 */
	private interface AppState {

		/**
		 * Update the Application based on the given Command. An
		 * UnsupportedOperationException is thrown if the Command is not a valid action
		 * for the given state.
		 * 
		 * @param command Command describing the action that will update the
		 *                Application's state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		void updateState(Command command);

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}

	/**
	 * This class is the first state of the Application State Pattern. It represents
	 * an application in the review state
	 * 
	 * @author Gabe Frain
	 */
	private class ReviewState implements AppState {

		/**
		 * Updates the application based on the given command
		 * 
		 * @param command Command describing the action that will update the
		 *                Application's state.
		 * @throws UnsupportedOperationException if the command is not appropriate for
		 *                                       the current state
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.ACCEPT) {
				setReviewer(command.getReviewerId());
				setState(INTERVIEW_NAME);
				setAppType(A_OLD);
			} else if (command.getCommand() == CommandValue.REJECT) {
				setReviewer(command.getReviewerId());
				setResolution(Command.R_REVCOMPLETED);
				setState(CLOSED_NAME);
			} else if (command.getCommand() == CommandValue.STANDBY) {
				if (A_NEW.equals(getAppType())) {
					setReviewer(command.getReviewerId());
					setResolution(Command.R_REVCOMPLETED);
					setState(WAITLIST_NAME);
				} else {
					throw new UnsupportedOperationException("Invalid information.");
				}
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			addNote(command.getNote());
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return REVIEW_NAME;
		}
	}

	/**
	 * This class is the second state of the Application State Pattern. It
	 * represents an application in the interview state
	 * 
	 * @author Gabe Frain
	 */
	private class InterviewState implements AppState {

		/**
		 * Updates the application based on the given command
		 * 
		 * @param command Command describing the action that will update the
		 *                Application's state.
		 * @throws UnsupportedOperationException if the command is not appropriate for
		 *                                       the current state
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.ACCEPT) {
				if (command.getReviewerId() == null) {
					throw new UnsupportedOperationException("Invalid information.");
				}
				setState(REFCHK_NAME);
				setProcessPaperwork(true);
			} else if (command.getCommand() == CommandValue.REJECT) {
				setResolution(Command.R_INTCOMPLETED);
				setState(CLOSED_NAME);
			} else if (command.getCommand() == CommandValue.STANDBY) {
				setResolution(Command.R_INTCOMPLETED);
				setState(WAITLIST_NAME);
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			setReviewer(command.getReviewerId());
			addNote(command.getNote());
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return INTERVIEW_NAME;
		}
	}

	/**
	 * This class is the third state of the Application State Pattern. It represents
	 * an application in the waitlist state
	 * 
	 * @author Gabe Frain
	 */
	private class WaitlistState implements AppState {

		/**
		 * Updates the application based on the given command
		 * 
		 * @param command Command describing the action that will update the
		 *                Application's state.
		 * @throws UnsupportedOperationException if the command is not appropriate for
		 *                                       the current state
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.REOPEN) {
				setReviewer(command.getReviewerId());
				if (Command.R_INTCOMPLETED.equals(getResolution())) {
					setResolution(null);
					setState(REFCHK_NAME);
					setProcessPaperwork(true);
				} else if (Command.R_REVCOMPLETED.equals(getResolution())) {
					setAppType(A_OLD);
					setResolution(null);
					setState(REVIEW_NAME);
				}
				addNote(command.getNote());
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return WAITLIST_NAME;
		}
	}

	/**
	 * This class is the fourth state of the Application State Pattern. It
	 * represents an application in the reference check state
	 * 
	 * @author Gabe Frain
	 */
	private class RefChkState implements AppState {

		/**
		 * Updates the application based on the given command
		 * 
		 * @param command Command describing the action that will update the
		 *                Application's state.
		 * @throws UnsupportedOperationException if the command is not appropriate for
		 *                                       the current state
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.ACCEPT) {
				setState(OFFER_NAME);
			} else if (command.getCommand() == CommandValue.REJECT) {
				setResolution(Command.R_REFCHKCOMPLETED);
				setState(CLOSED_NAME);
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			setReviewer(command.getReviewerId());
			addNote(command.getNote());
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return REFCHK_NAME;
		}
	}

	/**
	 * This class is the fifth state of the Application State Pattern. It represents
	 * an application in the offer state
	 * 
	 * @author Gabe Frain
	 */
	private class OfferState implements AppState {

		/**
		 * Updates the application based on the given command
		 * 
		 * @param command Command describing the action that will update the
		 *                Application's state.
		 * @throws UnsupportedOperationException if the command is not appropriate for
		 *                                       the current state
		 */
		public void updateState(Command command) {
			setResolution(Command.R_OFFERCOMPLETED);
			if (command.getCommand() == CommandValue.ACCEPT) {
				setState(CLOSED_NAME);
				setAppType(A_HIRED);
			} else if (command.getCommand() == CommandValue.REJECT) {
				setState(CLOSED_NAME);
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			setReviewer(command.getReviewerId());
			addNote(command.getNote());
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return OFFER_NAME;
		}
	}

	/**
	 * This class is the sixth state of the Application State Pattern. It represents
	 * an application in the closed state
	 * 
	 * @author Gabe Frain
	 */
	private class ClosedState implements AppState {

		/**
		 * Updates the application based on the given command
		 * 
		 * @param command Command describing the action that will update the
		 *                Application's state.
		 * @throws UnsupportedOperationException if the command is not appropriate for
		 *                                       the current state
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.REOPEN) {
				if (Command.R_REVCOMPLETED.equals(getResolution()) && appType == AppType.NEW) {
					setAppType(A_OLD);
					setResolution(null);
					setState(REVIEW_NAME);
					setReviewer(command.getReviewerId());
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid information.");
				}
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return CLOSED_NAME;
		}
	}

}
