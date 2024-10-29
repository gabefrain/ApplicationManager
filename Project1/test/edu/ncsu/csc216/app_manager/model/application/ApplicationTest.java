package edu.ncsu.csc216.app_manager.model.application;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;

/**
 * Tests the Command Class
 * 
 * Getters are tested through the valid constructor test
 * 
 * @author Gabe Frain
 */
public class ApplicationTest {

	/** New Application Type */
	private static final AppType NEW_APP_TYPE = AppType.NEW;
	/** Summary String */
	private static final String SUMMARY = "Application summary";
	/** Null Summary String */
	private static final String N_SUMMARY = null;
	/** Empty Summary String */
	private static final String E_SUMMARY = "";
//	/** Note String */
//	private static final String NOTE = "note";
//	/** Old Application Type */
//	private static final AppType OLD_APP_TYPE = AppType.OLD;
//	/** Second Summary String */
//	private static final String SUMMARY2 = "Application Summary";
//	/** Second Note String */
//	private static final String NOTE2 = "note 2";
//	/** Hired Application Type */
//	private static final AppType HIRED_APP_TYPE = AppType.OLD;
	/** First Application Id */
	private static final int FIRST_ID = 1;
	/** Second Application Id */
	private static final int SECOND_ID = 2;
	/** Null Application Id */
	private static final int NULL_ID = 0;

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

	/** A constant representing a completed review */
	public static final String R_REVCOMPLETED = "ReviewCompleted";
	/** A constant representing a completed interview */
	public static final String R_INTCOMPLETED = "InterviewCompleted";
	/** A constant representing a completed reference check */
	public static final String R_REFCHKCOMPLETED = "ReferenceCheckCompleted";
	/** A constant representing a completed offer */
	public static final String R_OFFERCOMPLETED = "OfferCompleted";

	/** Valid Command Value */
	private static final CommandValue ACCEPT_COMMAND = CommandValue.ACCEPT;
	/** Standby Command Value */
	private static final CommandValue STANDBY_COMMAND = CommandValue.STANDBY;
	/** Reject Command Value */
	private static final CommandValue REJECT_COMMAND = CommandValue.REJECT;
	/** Reopen Command Value */
	private static final CommandValue REOPEN_COMMAND = CommandValue.REOPEN;
	/** Reviewer Id */
	private static final String RID = "ghfrain";
	/** Null Reviewer Id */
	private static final String NRID = null;
	/** Review Completed Resolution */
	private static final Resolution REV_COMPLETED = Resolution.REVCOMPLETED;
	/** Interview Completed Resolution */
	private static final Resolution INT_COMPLETED = Resolution.INTCOMPLETED;
//	/** Reference Check Completed Resolution */
//	private static final Resolution REF_COMPLETED = Resolution.REFCHKCOMPLETED;
//	/** Offer Completed Resolution */
//	private static final Resolution OFF_COMPLETED = Resolution.OFFERCOMPLETED;
	/** Command Note */
	private static final String COMMAND_NOTE = "Example note";
	/** Command Note Added */
	private static final String COMMAND_NOTE_ADDED = "Added Note";
	/** Application Note List */
	private static final ArrayList<String> NOTES = new ArrayList<String>(Arrays.asList("First Note"));
	/** Application Practice Note List */
	private static final ArrayList<String> NOTES_PRACTICE = new ArrayList<String>(Arrays.asList("Practice Note"));

	/** Accept Application */
	private static final Command ACCEPT_APP = new Command(ACCEPT_COMMAND, RID, REV_COMPLETED, COMMAND_NOTE);
	/** Reject Application */
	private static final Command REJECT_APP = new Command(REJECT_COMMAND, RID, REV_COMPLETED, COMMAND_NOTE);
	/** Standby Application */
	private static final Command STANDBY_APP = new Command(STANDBY_COMMAND, RID, REV_COMPLETED, COMMAND_NOTE);
	/** Reopen Application */
	private static final Command REOPEN_APP_INT = new Command(REOPEN_COMMAND, RID, INT_COMPLETED, COMMAND_NOTE);
	/** Reopen Application */
	private static final Command REOPEN_APP_REV = new Command(REOPEN_COMMAND, NRID, REV_COMPLETED, COMMAND_NOTE);

	/**
	 * Tests the both Application Constructors
	 */
	@Test
	public void testApplication() {
		// Test large application constructor
		Application largeApp = new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, NRID, false, null,
				NOTES_PRACTICE);
		assertAll(() -> assertEquals(FIRST_ID, largeApp.getAppId()),
				() -> assertEquals(REVIEW_NAME, largeApp.getStateName()),
				() -> assertEquals(A_NEW, largeApp.getAppType()), () -> assertEquals(SUMMARY, largeApp.getSummary()),
				() -> assertEquals(NRID, largeApp.getReviewer()), () -> assertEquals(false, largeApp.isProcessed()),
				() -> assertEquals(null, largeApp.getResolution()),
				() -> assertEquals("-Practice Note\n", largeApp.getNotesString()));

		// Test small application constructor
		Application smallApp = new Application(FIRST_ID, NEW_APP_TYPE, SUMMARY, COMMAND_NOTE_ADDED);
		assertAll(() -> assertEquals(FIRST_ID, smallApp.getAppId()),
				() -> assertEquals(REVIEW_NAME, smallApp.getStateName()),
				() -> assertEquals(A_NEW, smallApp.getAppType()), () -> assertEquals(SUMMARY, smallApp.getSummary()),
				() -> assertEquals(NRID, smallApp.getReviewer()), () -> assertEquals(false, smallApp.isProcessed()),
				() -> assertEquals(null, smallApp.getResolution()),
				() -> assertEquals("-[Review] Added Note\n", smallApp.getNotesString()));

		// Test invalid constructors
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, RID, false, null,
						NOTES_PRACTICE));
		assertEquals("Application cannot be created.", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Application(FIRST_ID, WAITLIST_NAME, A_OLD, SUMMARY, NRID, false, R_INTCOMPLETED,
						NOTES_PRACTICE));
		assertEquals("Application cannot be created.", e2.getMessage());
	}

	/**
	 * Tests the setAppId method
	 */
	@Test
	public void testSetAppId() {
		Application app = new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, NRID, false, null, NOTES_PRACTICE);
		assertEquals(FIRST_ID, app.getAppId());

		// Test valid Id
		app.setAppId(SECOND_ID);
		assertEquals(SECOND_ID, app.getAppId());

		// Test invalid Id
		Exception e = assertThrows(IllegalArgumentException.class, () -> app.setAppId(NULL_ID));
		assertEquals("Application cannot be created.", e.getMessage());
	}

	/**
	 * Tests the setState method
	 */
	@Test
	public void testSetState() {
		Application app = new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, NRID, false, null, NOTES_PRACTICE);
		assertEquals(REVIEW_NAME, app.getStateName());

		// Test valid states
		app = new Application(FIRST_ID, INTERVIEW_NAME, A_OLD, SUMMARY, RID, false, null, NOTES_PRACTICE);
		assertEquals(INTERVIEW_NAME, app.getStateName());
		app.setState(REFCHK_NAME);
		assertEquals(REFCHK_NAME, app.getStateName());
		app.setState(OFFER_NAME);
		assertEquals(OFFER_NAME, app.getStateName());
		app.setState(REVIEW_NAME);
		assertEquals(REVIEW_NAME, app.getStateName());

		// Test invalid states
		Application invalidApp1 = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, RID, false, R_REVCOMPLETED,
				NOTES_PRACTICE);
		Exception re = assertThrows(IllegalArgumentException.class, () -> invalidApp1.setState(REVIEW_NAME));
		assertEquals("Application cannot be created.", re.getMessage());
		Application invalidApp2 = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, RID, false, R_INTCOMPLETED,
				NOTES_PRACTICE);
		Exception ie = assertThrows(IllegalArgumentException.class, () -> invalidApp2.setState(INTERVIEW_NAME));
		assertEquals("Application cannot be created.", ie.getMessage());
		Application invalidApp3 = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, RID, false, R_REFCHKCOMPLETED,
				NOTES_PRACTICE);
		Exception fe = assertThrows(IllegalArgumentException.class, () -> invalidApp3.setState(REFCHK_NAME));
		assertEquals("Application cannot be created.", fe.getMessage());
		Application invalidApp4 = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, RID, false, R_OFFERCOMPLETED,
				NOTES_PRACTICE);
		Exception oe = assertThrows(IllegalArgumentException.class, () -> invalidApp4.setState(OFFER_NAME));
		assertEquals("Application cannot be created.", oe.getMessage());

	}

	/**
	 * Tests the setAppType method
	 */
	@Test
	public void testSetAppType() {
		// Test valid application types
		Application newApp = new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, NRID, false, null, NOTES_PRACTICE);
		assertEquals(A_NEW, newApp.getAppType());
		Application oldApp = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, RID, false, R_REVCOMPLETED,
				NOTES_PRACTICE);
		assertEquals(A_OLD, oldApp.getAppType());

	}

	/**
	 * Tests the setSummary method
	 */
	@Test
	public void testSetSummary() {
		// Test valid summary
		Application validApp = new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, NRID, false, null,
				NOTES_PRACTICE);
		assertEquals(SUMMARY, validApp.getSummary());

		// Test invalid summaries
		Exception ne = assertThrows(IllegalArgumentException.class,
				() -> new Application(FIRST_ID, REVIEW_NAME, A_NEW, N_SUMMARY, NRID, false, null, NOTES_PRACTICE));
		assertEquals("Application cannot be created.", ne.getMessage());
		Exception ee = assertThrows(IllegalArgumentException.class,
				() -> new Application(FIRST_ID, REVIEW_NAME, A_NEW, E_SUMMARY, NRID, false, null, NOTES_PRACTICE));
		assertEquals("Application cannot be created.", ee.getMessage());
	}

	/**
	 * Tests the setReviewer method
	 */
	@Test
	public void testSetReviewer() {
		Application app = new Application(FIRST_ID, INTERVIEW_NAME, A_OLD, SUMMARY, RID, false, null, NOTES_PRACTICE);
		assertEquals(RID, app.getReviewer());
	}

	/**
	 * Tests the setProcessPaperwork method
	 */
	@Test
	public void testSetProcessPaperwork() {
		// Test valid paperwork
		Application app = new Application(FIRST_ID, OFFER_NAME, A_OLD, SUMMARY, RID, true, null, NOTES_PRACTICE);
		assertEquals(true, app.isProcessed());

		// Test invalid paperwork
		Exception re = assertThrows(IllegalArgumentException.class,
				() -> new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, NRID, true, null, NOTES_PRACTICE));
		assertEquals("Application cannot be created.", re.getMessage());
		Exception ie = assertThrows(IllegalArgumentException.class,
				() -> new Application(FIRST_ID, INTERVIEW_NAME, A_OLD, SUMMARY, RID, true, null, NOTES_PRACTICE));
		assertEquals("Application cannot be created.", ie.getMessage());
	}

	/**
	 * Tests the setResolution method
	 */
	@Test
	public void testSetResolution() {
		// Test valid resolutions
		Application revApp = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, NRID, false, R_REVCOMPLETED,
				NOTES_PRACTICE);
		assertEquals(R_REVCOMPLETED, revApp.getResolution());
		Application intApp = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, RID, false, R_INTCOMPLETED,
				NOTES_PRACTICE);
		assertEquals(R_INTCOMPLETED, intApp.getResolution());
		Application refApp = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, RID, true, R_REFCHKCOMPLETED,
				NOTES_PRACTICE);
		assertEquals(R_REFCHKCOMPLETED, refApp.getResolution());
		Application offApp = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, RID, true, R_OFFERCOMPLETED,
				NOTES_PRACTICE);
		assertEquals(R_OFFERCOMPLETED, offApp.getResolution());

		// Test null resolutions
		Application nullApp = new Application(FIRST_ID, REVIEW_NAME, A_OLD, SUMMARY, NRID, false, null, NOTES_PRACTICE);
		assertEquals(null, nullApp.getResolution());
		Application ranApp = new Application(FIRST_ID, INTERVIEW_NAME, A_OLD, SUMMARY, RID, false, "resolution",
				NOTES_PRACTICE);
		assertEquals(null, ranApp.getResolution());
	}

	/**
	 * Tests the toString method
	 */
	@Test
	public void testToString() {
		Application nullResApp = new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, NRID, false, null,
				NOTES_PRACTICE);
		assertEquals("*1,Review,New,Application summary,null,false,\r\n-Practice Note\n", nullResApp.toString());
		Application resApp = new Application(FIRST_ID, CLOSED_NAME, A_OLD, SUMMARY, RID, false, R_REVCOMPLETED,
				NOTES_PRACTICE);
		assertEquals("*1,Closed,Old,Application summary,ghfrain,false,ReviewCompleted\r\n-Practice Note\n",
				resApp.toString());
	}

	/**
	 * Tests the Review State
	 */
	@Test
	public void testReviewState() {
		Application revAccApp = new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, null, false, null, NOTES);
		revAccApp.update(ACCEPT_APP);
		assertAll(() -> assertEquals(RID, revAccApp.getReviewer()), () -> assertEquals(null, revAccApp.getResolution()),
				() -> assertEquals(INTERVIEW_NAME, revAccApp.getStateName()));
		Application revRejApp = new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, null, false, null, NOTES);
		revRejApp.update(REJECT_APP);
		assertAll(() -> assertEquals(RID, revRejApp.getReviewer()),
				() -> assertEquals(R_REVCOMPLETED, revRejApp.getResolution()),
				() -> assertEquals(CLOSED_NAME, revRejApp.getStateName()));
		Application revStaApp = new Application(FIRST_ID, REVIEW_NAME, A_NEW, SUMMARY, null, false, null, NOTES);
		revStaApp.update(STANDBY_APP);
		assertAll(() -> assertEquals(RID, revStaApp.getReviewer()),
				() -> assertEquals(R_REVCOMPLETED, revStaApp.getResolution()),
				() -> assertEquals(WAITLIST_NAME, revStaApp.getStateName()));
	}

	/**
	 * Tests the Interview State
	 */
	@Test
	public void testInterviewState() {
		Application intAccApp = new Application(FIRST_ID, INTERVIEW_NAME, A_OLD, SUMMARY, RID, false, null, NOTES);
		intAccApp.update(ACCEPT_APP);
		assertAll(() -> assertEquals(RID, intAccApp.getReviewer()), () -> assertEquals(null, intAccApp.getResolution()),
				() -> assertEquals(REFCHK_NAME, intAccApp.getStateName()));
		Application intRejApp = new Application(FIRST_ID, INTERVIEW_NAME, A_OLD, SUMMARY, RID, false, null, NOTES);
		intRejApp.update(REJECT_APP);
		assertAll(() -> assertEquals(RID, intRejApp.getReviewer()),
				() -> assertEquals(R_INTCOMPLETED, intRejApp.getResolution()),
				() -> assertEquals(CLOSED_NAME, intRejApp.getStateName()));
		Application intReoApp = new Application(FIRST_ID, INTERVIEW_NAME, A_OLD, SUMMARY, RID, false, null, NOTES);
		Exception ire = assertThrows(UnsupportedOperationException.class, () -> intReoApp.update(REOPEN_APP_INT));
		assertEquals("Invalid information.", ire.getMessage());
		Application intStaApp = new Application(FIRST_ID, INTERVIEW_NAME, A_OLD, SUMMARY, RID, false, null, NOTES);
		intStaApp.update(STANDBY_APP);
		assertAll(() -> assertEquals(RID, intStaApp.getReviewer()),
				() -> assertEquals(R_INTCOMPLETED, intStaApp.getResolution()),
				() -> assertEquals(WAITLIST_NAME, intStaApp.getStateName()));
	}

	/**
	 * Tests the Waitlist State
	 */
	@Test
	public void testWaitlistState() {
		Application waitlistApp = new Application(FIRST_ID, WAITLIST_NAME, A_OLD, SUMMARY, RID, false, R_INTCOMPLETED,
				NOTES);
		Exception wae = assertThrows(UnsupportedOperationException.class, () -> waitlistApp.update(ACCEPT_APP));
		assertEquals("Invalid information.", wae.getMessage());
		Exception wre = assertThrows(UnsupportedOperationException.class, () -> waitlistApp.update(REJECT_APP));
		assertEquals("Invalid information.", wre.getMessage());
		Exception wse = assertThrows(UnsupportedOperationException.class, () -> waitlistApp.update(STANDBY_APP));
		assertEquals("Invalid information.", wse.getMessage());
		waitlistApp.update(REOPEN_APP_INT);
		assertAll(() -> assertEquals(RID, waitlistApp.getReviewer()),
				() -> assertEquals(null, waitlistApp.getResolution()),
				() -> assertEquals(REFCHK_NAME, waitlistApp.getStateName()));
		Application waitlistApp2 = new Application(FIRST_ID, WAITLIST_NAME, A_OLD, SUMMARY, RID, false, R_REVCOMPLETED,
				NOTES);
		waitlistApp2.update(REOPEN_APP_REV);
		assertAll(() -> assertEquals(NRID, waitlistApp2.getReviewer()),
				() -> assertEquals(null, waitlistApp2.getResolution()),
				() -> assertEquals(REVIEW_NAME, waitlistApp2.getStateName()));
	}

	/**
	 * Tests the Reference Check State
	 */
	@Test
	public void testRefChkState() {
		Application refChkAccApp = new Application(FIRST_ID, REFCHK_NAME, A_OLD, SUMMARY, RID, true, null, NOTES);
		refChkAccApp.update(ACCEPT_APP);
		assertAll(() -> assertEquals(RID, refChkAccApp.getReviewer()),
				() -> assertEquals(null, refChkAccApp.getResolution()),
				() -> assertEquals(OFFER_NAME, refChkAccApp.getStateName()));
		Application refChkRejApp = new Application(FIRST_ID, REFCHK_NAME, A_OLD, SUMMARY, RID, true, null, NOTES);
		refChkRejApp.update(REJECT_APP);
		assertAll(() -> assertEquals(RID, refChkRejApp.getReviewer()),
				() -> assertEquals(R_REFCHKCOMPLETED, refChkRejApp.getResolution()),
				() -> assertEquals(CLOSED_NAME, refChkRejApp.getStateName()));
	}

	/**
	 * Tests the Offer State
	 */
	@Test
	public void testOfferState() {
		Application offerAccApp = new Application(FIRST_ID, OFFER_NAME, A_OLD, SUMMARY, RID, true, null, NOTES);
		offerAccApp.update(ACCEPT_APP);
		assertAll(() -> assertEquals(RID, offerAccApp.getReviewer()),
				() -> assertEquals(R_OFFERCOMPLETED, offerAccApp.getResolution()),
				() -> assertEquals(CLOSED_NAME, offerAccApp.getStateName()));
		Application offerRejApp = new Application(FIRST_ID, OFFER_NAME, A_OLD, SUMMARY, RID, true, null, NOTES);
		offerRejApp.update(REJECT_APP);
		assertAll(() -> assertEquals(RID, offerRejApp.getReviewer()),
				() -> assertEquals(R_OFFERCOMPLETED, offerRejApp.getResolution()),
				() -> assertEquals(CLOSED_NAME, offerRejApp.getStateName()));
	}

	/**
	 * Tests the Closed State
	 */
	@Test
	public void testClosedState() {
		Application closedApp = new Application(FIRST_ID, CLOSED_NAME, A_NEW, SUMMARY, RID, false, R_REVCOMPLETED,
				NOTES);
		Exception cae = assertThrows(UnsupportedOperationException.class, () -> closedApp.update(ACCEPT_APP));
		assertEquals("Invalid information.", cae.getMessage());
		Exception cre = assertThrows(UnsupportedOperationException.class, () -> closedApp.update(REJECT_APP));
		assertEquals("Invalid information.", cre.getMessage());
		Exception cse = assertThrows(UnsupportedOperationException.class, () -> closedApp.update(STANDBY_APP));
		assertEquals("Invalid information.", cse.getMessage());
		closedApp.update(REOPEN_APP_REV);
		assertAll(() -> assertEquals(NRID, closedApp.getReviewer()),
				() -> assertEquals(null, closedApp.getResolution()),
				() -> assertEquals(REVIEW_NAME, closedApp.getStateName()));
	}
}
