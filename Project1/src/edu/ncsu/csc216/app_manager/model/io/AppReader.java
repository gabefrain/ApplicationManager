package edu.ncsu.csc216.app_manager.model.io;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;

import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * This class reads lists of applications from files
 * 
 * @author Gabe Frain
 */
public class AppReader {

	/**
	 * Reads applications from an input file and returns them in an array list to
	 * the user
	 * 
	 * @param fileName name of input file
	 * @return array list of applications based on input file
	 * @throws IllegalArgumentException if any exceptions occur while processing the
	 *                                  file
	 */
	public static ArrayList<Application> readAppsFromFile(String fileName) {
		try {
			Scanner fileReader = new Scanner(new FileInputStream(fileName));
			fileReader.useDelimiter("\\r?\\n?[*]");
			ArrayList<Application> list = new ArrayList<Application>();
			while (fileReader.hasNext()) {
				list.add(processApp(fileReader.next()));
			}
			fileReader.close();
			if (list.size() == 0) {
				throw new IllegalArgumentException();
			}
			return list;
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load file.");
		}

	}

	/**
	 * Processes an individual application based on the inputed string
	 * 
	 * @param app string of application being processed
	 * @return application object corresponding with input application
	 * @throws IllegalArgumentException if any of the parameters are invalid
	 */
	private static Application processApp(String app) {
		Scanner appReader = new Scanner(app);
		Scanner appLineReader = new Scanner(appReader.nextLine());
		appLineReader.useDelimiter(",");
		int id = appLineReader.nextInt();
		String state = appLineReader.next();
		String appType = appLineReader.next();
		String summary = appLineReader.next();
		String reviewer = appLineReader.next();
		if ("".equals(reviewer)) {
			reviewer = null;
		}
		boolean processPaperwork = appLineReader.nextBoolean();
		String resolution = null;
		if (appLineReader.hasNext()) {
			resolution = appLineReader.next();
		}
		if (appLineReader.hasNext()) {
			appLineReader.close();
			appReader.close();
			throw new IllegalArgumentException("Unable to load file.");
		}
		appLineReader.close();

		appReader.useDelimiter("\\r?\\n?[-]");
		ArrayList<String> notes = new ArrayList<String>();
		while (appReader.hasNext()) {
			notes.add(appReader.next());
		}
		appReader.close();
		return new Application(id, state, appType, summary, reviewer, processPaperwork, resolution, notes);

	}
}
