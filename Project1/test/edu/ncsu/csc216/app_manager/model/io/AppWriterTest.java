package edu.ncsu.csc216.app_manager.model.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * Tests the Application Writer Class
 * 
 * @author Gabe Frain
 */
public class AppWriterTest {

	/** Valid application records */
	private final String validTestFile = "test-files/valid_records.txt";
	/** Actual application records */
	private final String actualTestFile = "test-files/actual_records.txt";

	/** Expected notes of first application in valid_records.txt */
	private final ArrayList<String> notes1 = new ArrayList<String>(Arrays.asList("[Review] Note 1"));
	/** Expected first application in valid_records.txt */
	private final Application validApplication1 = new Application(1, "Review", "New", "Application summary", null,
			false, null, notes1);
	/** Expected notes of second application in valid_records.txt */
	private final ArrayList<String> notes2 = new ArrayList<String>(
			Arrays.asList("[Review] Note 1", "[Interview] Note 2\r\nthat goes on a new line"));
	/** Expected second application in valid_records.txt */
	private final Application validApplication2 = new Application(3, "Interview", "Old", "Application summary",
			"reviewer", false, null, notes2);
	/** Expected notes of third application in valid_records.txt */
	private final ArrayList<String> notes3 = new ArrayList<String>(
			Arrays.asList("[Review] Note 1", "[Interview] Note 2", "[RefCheck] Note 3"));
	/** Expected third application in valid_records.txt */
	private final Application validApplication3 = new Application(7, "RefCheck", "Old", "Application summary",
			"reviewer", true, null, notes3);
	/** Expected notes of fourth application in valid_records.txt */
	private final ArrayList<String> notes4 = new ArrayList<String>(
			Arrays.asList("[Review] Note 1", "[Waitlist] Note 2\r\nthat goes on a new line"));
	/** Expected fourth application in valid_records.txt */
	private final Application validApplication4 = new Application(14, "Waitlist", "New", "Application summary", null,
			false, "ReviewCompleted", notes4);
	/** Expected notes of fifth application in valid_records.txt */
	private final ArrayList<String> notes5 = new ArrayList<String>(Arrays.asList(
			"[Review] Note 1\r\nthat goes on a new line", "[Interview] Note 2", "[RefCheck] Note 3", "[Offer] Note 4"));
	/** Expected fifth application in valid_records.txt */
	private final Application validApplication5 = new Application(16, "Offer", "Old", "Application summary", "reviewer",
			true, null, notes5);
	/** Expected notes of sixth application in valid_records.txt */
	private final ArrayList<String> notes6 = new ArrayList<String>(
			Arrays.asList("[Review] Note 1\r\nthat goes on a new line", "[Interview] Note 2", "[RefCheck] Note 3",
					"[Offer] Note 4", "[Closed] Note 6"));
	/** Expected sixth application in valid_records.txt */
	private final Application validApplication6 = new Application(15, "Closed", "Old", "Application summary",
			"reviewer", true, "OfferCompleted", notes6);

	/** List to hold expected application results */
	private final ArrayList<Application> validApplications = new ArrayList<Application>(Arrays.asList(validApplication1,
			validApplication2, validApplication3, validApplication4, validApplication5, validApplication6));

	/**
	 * Test the Constructor for AppWriter
	 */
	@Test
	public void testAppWriter() {
		AppWriter aw = new AppWriter();
		assertNotEquals(null, aw);
	}
	
	/**
	 * Test the WriteAppsToFile method
	 */
	@Test
	public void testWriteAppsToFile() {
		// Valid Application
		try {
			AppWriter.writeAppsToFile("test-files/actual_records.txt", validApplications);
		} catch (IllegalArgumentException e) {
			fail("Unable to save file.");
		}
		ArrayList<Application> valApp = AppReader.readAppsFromFile(validTestFile);
		ArrayList<Application> actApp = AppReader.readAppsFromFile(actualTestFile);
		for (int i = 0; i < valApp.size(); i++) {
			assertEquals(valApp.get(i).toString(), actApp.get(i).toString());
		}
		
		// Invalid Application
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> AppWriter.writeAppsToFile("/home/ghfrain/actual_records.txt", validApplications));
		assertEquals("Unable to save file.", exception.getMessage());
	}
}
