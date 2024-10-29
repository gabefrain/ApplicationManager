package edu.ncsu.csc216.app_manager.model.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.app_manager.model.io.AppReader;

/**
 * Tests the AppList class
 * 
 * @author Gabe Frain
 */
public class AppListTest {

	/** Application 1 */
	private final String testFile = "test-files/app1.txt";

	/** New Application Type */
	private static final AppType NEW_APP_TYPE = AppType.NEW;
	/** Old Application Type */
	private static final AppType OLD_APP_TYPE = AppType.OLD;
	/** New Application Type String */
	public static final String A_NEW = "New";
	/** Old Application Type String */
	public static final String A_OLD = "Old";
	/** First Summary String */
	private static final String SUMMARY1 = "Great work";
	/** Second Summary String */
	private static final String SUMMARY2 = "Ok employ/nDon't want to hire though";
	/** First Note String */
	private static final String NOTE1 = "Odd employ";
	/** Second Note String */
	private static final String NOTE2 = "Cool guy";
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
	/** Note String as Notes */
	private static final String NOTE1A = "-[Review] Odd employ\n";
	/** Note String */
	private static final String NOTES = "-[Review] Odd employ\n-[Interview] Passed Review\n";
	/** A constant representing the interview state */
	public static final String INTERVIEW_NAME = "Interview";
	/** Review State String */
	public static final String REVIEW_NAME = "Review";

	/**
	 * Tests the AppList Constructor
	 */
	@Test
	public void testAppList() {
		AppList appList = new AppList();
		assertEquals(0, appList.getApps().size());
	}

	/**
	 * Tests the addApp method
	 */
	@Test
	public void testAddApp() {
		AppList appList = new AppList();
		assertEquals(1, appList.addApp(NEW_APP_TYPE, SUMMARY1, NOTE1));
		Application actual = new Application(1, NEW_APP_TYPE, SUMMARY1, NOTE1);
		assertEquals(actual.toString(), appList.getAppById(1).toString());
	}

	/**
	 * Tests the addApps method
	 */
	@Test
	public void testAddApps() {
		AppList appList = new AppList();
		ArrayList<Application> applicationlist = new ArrayList<Application>();
		Application app1 = new Application(1, NEW_APP_TYPE, SUMMARY1, NOTE1);
		Application app2 = new Application(2, NEW_APP_TYPE, SUMMARY1, NOTE2);
		Application app3 = new Application(5, OLD_APP_TYPE, SUMMARY1, NOTE2);
		Application app4 = new Application(4, OLD_APP_TYPE, SUMMARY2, NOTE1);
		Application app5 = new Application(4, NEW_APP_TYPE, SUMMARY2, NOTE1);
		applicationlist.add(app1);
		applicationlist.add(app2);
		applicationlist.add(app3);
		applicationlist.add(app4);
		applicationlist.add(app5);
		appList.addApps(applicationlist);
		ArrayList<Application> outputlist = new ArrayList<Application>();
		outputlist.add(app1);
		outputlist.add(app2);
		outputlist.add(app4);
		outputlist.add(app3);
		for (int i = 0; i < appList.getApps().size(); i++) {
			assertEquals(outputlist.get(i).toString(), appList.getApps().get(i).toString());
		}

	}

	/**
	 * Tests the getAppsById method
	 */
	@Test
	public void testGetAppsByType() {
		// Valid types
		AppList appList = new AppList();
		appList.addApp(NEW_APP_TYPE, SUMMARY1, NOTE1);
		appList.addApp(NEW_APP_TYPE, SUMMARY2, NOTE2);
		appList.addApp(OLD_APP_TYPE, SUMMARY2, NOTE1);
		Application app1 = new Application(1, NEW_APP_TYPE, SUMMARY1, NOTE1);
		Application app2 = new Application(2, NEW_APP_TYPE, SUMMARY2, NOTE2);
		Application app3 = new Application(3, OLD_APP_TYPE, SUMMARY2, NOTE1);
		ArrayList<Application> newlist = new ArrayList<Application>();
		newlist.add(app1);
		newlist.add(app2);
		ArrayList<Application> oldlist = new ArrayList<Application>();
		oldlist.add(app3);
		assertEquals(newlist.get(0).toString(), appList.getAppsByType(A_NEW).get(0).toString());
		assertEquals(newlist.get(1).toString(), appList.getAppsByType(A_NEW).get(1).toString());
		assertEquals(oldlist.get(0).toString(), appList.getAppsByType(A_OLD).get(0).toString());
		assertEquals(2, appList.getAppsByType(A_NEW).size());
		assertEquals(1, appList.getAppsByType(A_OLD).size());

		appList.addApps(AppReader.readAppsFromFile(testFile));
		assertEquals(2, appList.getAppsByType(A_NEW).size());
		
		// Invalid type
		assertThrows(IllegalArgumentException.class, () -> appList.getAppsByType(null));
	}

	/**
	 * Tests the getAppById method
	 */
	@Test
	public void testGetAppsById() {
		AppList appList = new AppList();
		appList.addApp(NEW_APP_TYPE, SUMMARY1, NOTE1);
		appList.addApp(NEW_APP_TYPE, SUMMARY2, NOTE2);
		Application app1 = new Application(1, NEW_APP_TYPE, SUMMARY1, NOTE1);
		Application app2 = new Application(2, NEW_APP_TYPE, SUMMARY2, NOTE2);
		assertEquals(app1.toString(), appList.getAppById(1).toString());
		assertEquals(app2.toString(), appList.getAppById(2).toString());
		assertNull(appList.getAppById(20));
	}

	/**
	 * Tests the executeCommand method
	 */
	@Test
	public void testExecuteCommand() {
		AppList appList = new AppList();
		assertEquals(1, appList.addApp(NEW_APP_TYPE, SUMMARY1, NOTE1));
		// Empty command
		appList.executeCommand(2,  COMMAND);
		Application app = appList.getAppById(1);
		assertEquals(1, app.getAppId());
		assertEquals(A_NEW, app.getAppType());
		assertEquals(NOTE1A, app.getNotesString());
		assertEquals(null, app.getResolution());
		assertEquals(REVIEW_NAME, app.getStateName());
		assertEquals(SUMMARY1, app.getSummary());
		assertEquals(false, app.isProcessed());
		
		// Valid command
		appList.executeCommand(1, COMMAND);
		app = appList.getAppById(1);
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
	public void testDelteAppById() {
		AppList appList = new AppList();
		appList.addApp(NEW_APP_TYPE, SUMMARY1, NOTE1);
		appList.addApp(NEW_APP_TYPE, SUMMARY2, NOTE2);
		appList.addApp(OLD_APP_TYPE, SUMMARY1, NOTE2);
		appList.deleteAppById(2);
		Application app1 = new Application(1, NEW_APP_TYPE, SUMMARY1, NOTE1);
		Application app3 = new Application(3, OLD_APP_TYPE, SUMMARY1, NOTE2);
		assertEquals(2, appList.getApps().size());
		assertEquals(app1.toString(), appList.getApps().get(0).toString());
		assertEquals(app3.toString(), appList.getApps().get(1).toString());
	}

}
