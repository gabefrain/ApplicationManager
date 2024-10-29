package edu.ncsu.csc216.app_manager.model.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;

/**
 * Tests the AppManager class
 * 
 * @author Gabe Frain
 */
public class AppManagerTest {

	/** Actual application records */
	private final String actualTestFile = "test-files/actual_records_2.txt";

	/** New Application Type */
	private static final AppType NEW_APP_TYPE = AppType.NEW;
	/** Old Application Type */
	private static final AppType OLD_APP_TYPE = AppType.OLD;
	/** New Application Type String */
	public static final String A_NEW = "New";
	/** Old Application Type String */
	public static final String A_OLD = "Old";
	/** Review State String */
	public static final String REVIEW_NAME = "Review";
	/** A constant representing the interview state */
	public static final String INTERVIEW_NAME = "Interview";
	/** First Summary String */
	private static final String SUMMARY1 = "Great work";
	/** Second Summary String */
	private static final String SUMMARY2 = "Ok employ/nDon't want to hire though";
	/** Third Summary String */
	private static final String SUMMARY3 = "WHO????";
	/** First Note String */
	private static final String NOTE1 = "Odd employ";
	/** Second Note String */
	private static final String NOTE2 = "Cool guy";
	/** Third Note String */
	private static final String NOTE3 = "Ummm...";
	/** First Note String as Notes */
	private static final String NOTE1A = "-[Review] Odd employ\n";
	/** Valid Command Value */
	private static final CommandValue C = CommandValue.ACCEPT;
	/** Reviewer Id */
	private static final String RID = "ghfrain";
	/** Null Resolution */
	private static final Resolution NR = null;
	/** Note */
	private static final String NOTE = "Passed Review";
	/** Command */
	private static final Command COMMAND = new Command(C, RID, NR, NOTE);
	/** Note String */
	private static final String NOTES = "-[Review] Odd employ\n-[Interview] Passed Review\n";

	/**
	 * Tests the singleton of the appManager
	 */
	@Test
	public void testGetInstance() {
		assertEquals(AppManager.getInstance(), AppManager.getInstance());
	}

	/**
	 * Tests the saveAppsToFile method
	 */
	@Test
	public void testSaveAppsToFile() {
		AppManager.getInstance().createNewAppList();
		AppManager.getInstance().addAppToList(NEW_APP_TYPE, SUMMARY1, NOTE1);
		AppManager.getInstance().addAppToList(NEW_APP_TYPE, SUMMARY2, NOTE2);
		AppManager.getInstance().addAppToList(OLD_APP_TYPE, SUMMARY3, NOTE3);
		try {
			AppManager.getInstance().saveAppsToFile(actualTestFile);
		} catch (IllegalArgumentException e) {
			fail("Unable to save file.");
		}
		AppManager.getInstance().loadAppsFromFile(actualTestFile);
		Object[][] list = AppManager.getInstance().getAppListAsArray();
		assertEquals(1, list[0][0]);
		assertEquals(REVIEW_NAME, list[0][1]);
		assertEquals(A_NEW, list[0][2]);
		assertEquals(SUMMARY1, list[0][3]);
		assertEquals(2, list[1][0]);
		assertEquals(REVIEW_NAME, list[1][1]);
		assertEquals(A_NEW, list[1][2]);
		assertEquals(SUMMARY2, list[1][3]);
		assertEquals(3, list[2][0]);
		assertEquals(REVIEW_NAME, list[2][1]);
		assertEquals(A_OLD, list[2][2]);
		assertEquals(SUMMARY3, list[2][3]);
	}

	/**
	 * Tests the loadAppsFromFile method
	 */
	@Test
	public void testLoadAppsFromFile() {
		AppManager.getInstance().createNewAppList();
		AppManager.getInstance().loadAppsFromFile(actualTestFile);
		Object[][] list = AppManager.getInstance().getAppListAsArray();
		assertEquals(1, list[0][0]);
		assertEquals(REVIEW_NAME, list[0][1]);
		assertEquals(A_NEW, list[0][2]);
		assertEquals(SUMMARY1, list[0][3]);
		assertEquals(2, list[1][0]);
		assertEquals(REVIEW_NAME, list[1][1]);
		assertEquals(A_NEW, list[1][2]);
		assertEquals(SUMMARY2, list[1][3]);
		assertEquals(3, list[2][0]);
		assertEquals(REVIEW_NAME, list[2][1]);
		assertEquals(A_OLD, list[2][2]);
		assertEquals(SUMMARY3, list[2][3]);
	}

	/**
	 * Tests the getAppListAsArray
	 */
	@Test
	public void testGetAppListAsArray() {
		AppManager.getInstance().createNewAppList();
		AppManager.getInstance().addAppToList(NEW_APP_TYPE, SUMMARY1, NOTE1);
		AppManager.getInstance().addAppToList(NEW_APP_TYPE, SUMMARY2, NOTE2);
		AppManager.getInstance().addAppToList(OLD_APP_TYPE, SUMMARY3, NOTE3);

		Object[][] list = AppManager.getInstance().getAppListAsArray();
		assertEquals(1, list[0][0]);
		assertEquals(REVIEW_NAME, list[0][1]);
		assertEquals(A_NEW, list[0][2]);
		assertEquals(SUMMARY1, list[0][3]);
		assertEquals(2, list[1][0]);
		assertEquals(REVIEW_NAME, list[1][1]);
		assertEquals(A_NEW, list[1][2]);
		assertEquals(SUMMARY2, list[1][3]);
		assertEquals(3, list[2][0]);
		assertEquals(REVIEW_NAME, list[2][1]);
		assertEquals(A_OLD, list[2][2]);
		assertEquals(SUMMARY3, list[2][3]);
	}

	/**
	 * Tests the getAppListAsArrayByAppType method
	 */
	@Test
	public void testGetAppListAsArrayByAppType() {
		// Test valid appType
		AppManager.getInstance().createNewAppList();
		AppManager.getInstance().addAppToList(NEW_APP_TYPE, SUMMARY1, NOTE1);
		AppManager.getInstance().addAppToList(NEW_APP_TYPE, SUMMARY2, NOTE2);
		AppManager.getInstance().addAppToList(OLD_APP_TYPE, SUMMARY3, NOTE3);

		Object[][] listNew = AppManager.getInstance().getAppListAsArrayByAppType(A_NEW);
		assertEquals(1, listNew[0][0]);
		assertEquals(REVIEW_NAME, listNew[0][1]);
		assertEquals(A_NEW, listNew[0][2]);
		assertEquals(SUMMARY1, listNew[0][3]);
		assertEquals(2, listNew[1][0]);
		assertEquals(REVIEW_NAME, listNew[1][1]);
		assertEquals(A_NEW, listNew[1][2]);
		assertEquals(SUMMARY2, listNew[1][3]);
		Object[][] listOld = AppManager.getInstance().getAppListAsArrayByAppType(A_OLD);
		assertEquals(3, listOld[0][0]);
		assertEquals(REVIEW_NAME, listOld[0][1]);
		assertEquals(A_OLD, listOld[0][2]);
		assertEquals(SUMMARY3, listOld[0][3]);

		// Test null appType
		assertThrows(IllegalArgumentException.class, () -> AppManager.getInstance().getAppListAsArrayByAppType(null));
	}
	
	/**
	 * Tests the getAppById method
	 */
	@Test
	public void testGetAppById() {
		AppManager.getInstance().createNewAppList();
		AppManager.getInstance().loadAppsFromFile(actualTestFile);
		Application app = AppManager.getInstance().getAppById(1);
		assertEquals(1, app.getAppId());
		assertEquals(A_NEW, app.getAppType());
		assertEquals(NOTE1A, app.getNotesString());
		assertEquals(null, app.getResolution());
		assertEquals(REVIEW_NAME, app.getStateName());
		assertEquals(SUMMARY1, app.getSummary());
		assertEquals(false, app.isProcessed());
	}
	
	/**
	 * Tests the executeCommand method
	 */
	@Test
	public void testExecuteCommand() {
		AppManager.getInstance().createNewAppList();
		AppManager.getInstance().loadAppsFromFile(actualTestFile);
		AppManager.getInstance().executeCommand(1, COMMAND);
		Application app = AppManager.getInstance().getAppById(1);
		assertEquals(1, app.getAppId());
		assertEquals(A_OLD, app.getAppType());
		assertEquals(NOTES, app.getNotesString());
		assertEquals(null, app.getResolution());
		assertEquals(INTERVIEW_NAME, app.getStateName());
		assertEquals(SUMMARY1, app.getSummary());
		assertEquals(false, app.isProcessed());
	}
	
	/**
	 * Tests the deleteAppById method
	 */
	@Test
	public void testDeleteAppById() {
		AppManager.getInstance().createNewAppList();
		AppManager.getInstance().loadAppsFromFile(actualTestFile);
		Object[][] list = AppManager.getInstance().getAppListAsArray();
		assertEquals(1, list[0][0]);
		assertEquals(REVIEW_NAME, list[0][1]);
		assertEquals(A_NEW, list[0][2]);
		assertEquals(SUMMARY1, list[0][3]);
		assertEquals(2, list[1][0]);
		assertEquals(REVIEW_NAME, list[1][1]);
		assertEquals(A_NEW, list[1][2]);
		assertEquals(SUMMARY2, list[1][3]);
		assertEquals(3, list[2][0]);
		assertEquals(REVIEW_NAME, list[2][1]);
		assertEquals(A_OLD, list[2][2]);
		assertEquals(SUMMARY3, list[2][3]);
		AppManager.getInstance().deleteAppById(2);
		list = AppManager.getInstance().getAppListAsArray();
		assertEquals(1, list[0][0]);
		assertEquals(REVIEW_NAME, list[0][1]);
		assertEquals(A_NEW, list[0][2]);
		assertEquals(SUMMARY1, list[0][3]);
		assertEquals(3, list[1][0]);
		assertEquals(REVIEW_NAME, list[1][1]);
		assertEquals(A_OLD, list[1][2]);
		assertEquals(SUMMARY3, list[1][3]);
	}
	
	/**
	 * Tests the addAppToList method
	 */
	@Test
	public void testAddAppToList() {
		AppManager.getInstance().addAppToList(NEW_APP_TYPE, SUMMARY1, NOTE1);
		AppManager.getInstance().addAppToList(NEW_APP_TYPE, SUMMARY2, NOTE2);
		AppManager.getInstance().addAppToList(OLD_APP_TYPE, SUMMARY3, NOTE3);
		Object[][] list = AppManager.getInstance().getAppListAsArray();
		assertEquals(1, list[0][0]);
		assertEquals(REVIEW_NAME, list[0][1]);
		assertEquals(A_NEW, list[0][2]);
		assertEquals(SUMMARY1, list[0][3]);
		assertEquals(2, list[1][0]);
		assertEquals(REVIEW_NAME, list[1][1]);
		assertEquals(A_NEW, list[1][2]);
		assertEquals(SUMMARY2, list[1][3]);
		assertEquals(3, list[2][0]);
		assertEquals(REVIEW_NAME, list[2][1]);
		assertEquals(A_OLD, list[2][2]);
		assertEquals(SUMMARY3, list[2][3]);
		
	}
}
