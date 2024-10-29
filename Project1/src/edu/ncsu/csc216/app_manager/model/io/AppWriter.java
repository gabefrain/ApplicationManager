package edu.ncsu.csc216.app_manager.model.io;

import java.io.PrintWriter;
import java.util.List;
import java.io.FileOutputStream;

import edu.ncsu.csc216.app_manager.model.application.Application;

/**
 * This class writes lists of applications to files
 * 
 * @author Gabe Frain
 */
public class AppWriter {

	/**
	 * Writes applications to an output file using an array list of applications
	 * 
	 * @param fileName name of output file
	 * @param list     list of applications
	 * @throws IllegalArgumentException if any exceptions occur while processing the
	 *                                  file
	 */
	public static void writeAppsToFile(String fileName, List<Application> list) {
		try {
			PrintWriter fileWriter = new PrintWriter(new FileOutputStream(fileName));
			for (int i = 0; i < list.size() - 1; i++) {
				fileWriter.print(list.get(i).toString());
			}
			String lastApp = list.get(list.size() - 1).toString();
			fileWriter.print(lastApp.substring(0, lastApp.length() - 1));
			fileWriter.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
}
