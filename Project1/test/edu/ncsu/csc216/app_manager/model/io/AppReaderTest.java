package edu.ncsu.csc216.app_manager.model.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * Tests the Application Reader Class
 * 
 * @author Gabe Frain
 */
public class AppReaderTest {

	/** Valid Application Records */
	private final String validTestFile = "test-files/valid_records.txt";
	/** Invalid Application Records */
	private final String invalidTestFile = "test-files/invalid_records.txt";
	/** Empty Application Records */
	private final String emptyTestFile = "test-files/empty_records.txt";

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
	private final String[] validApplications = { validApplication1.toString(), validApplication2.toString(),
			validApplication3.toString(), validApplication4.toString(), validApplication5.toString(),
			validApplication6.toString() };

	/**
	 * Resets valid_records.txt for use in other tests.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset valid_records.txt for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_valid_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "valid_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}
	
	/**
	 * Test the Constructor for AppReader
	 */
	@Test
	public void testAppReader() {
		AppReader ar = new AppReader();
		assertNotEquals(null, ar);
	}
	
	/**
	 * Tests the ReadAppsFromFile Method
	 */
	@Test
	public void testReadAppsFromFile() {
		// Testing a valid file
		ArrayList<Application> applications = AppReader.readAppsFromFile(validTestFile);
		assertEquals(6, applications.size());
		for (int i = 0; i < validApplications.length; i++) {
			assertEquals(validApplications[i], applications.get(i).toString());
		}
		
		// Testing invalid files
		Exception e = assertThrows(IllegalArgumentException.class, () -> AppReader.readAppsFromFile(invalidTestFile));
		assertEquals("Unable to load file.", e.getMessage());
		Exception ie = assertThrows(IllegalArgumentException.class, () -> AppReader.readAppsFromFile(emptyTestFile));
		assertEquals("Unable to load file.", ie.getMessage());
		
	}

}
