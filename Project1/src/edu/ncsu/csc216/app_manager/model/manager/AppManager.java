package edu.ncsu.csc216.app_manager.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.io.AppReader;
import edu.ncsu.csc216.app_manager.model.io.AppWriter;

/**
 * This class represents the ApplicationManager. ApplicationManager implements
 * the Singleton design pattern
 * 
 * @author Gabe Frain
 */
public class AppManager {

	/** Single instance of the Application Manager */
	private static AppManager singleton;
	/** List of applications */
	private AppList appList;

	/**
	 * Private constructor of AppManager enforces singleton pattern
	 */
	private AppManager() {
		createNewAppList();
	}

	/**
	 * Gets the single instance of the application manager. Also creates the
	 * instance if it has not been initialized yet
	 * 
	 * @return single instance of AppManager
	 */
	public static synchronized AppManager getInstance() {
		if (singleton == null) {
			singleton = new AppManager();
		}
		return singleton;
	}

	/**
	 * Saves the application list to the provided file
	 * 
	 * @param fileName name of file application list is saved to
	 * @throws IllegalArgumentException if any exceptions occur while processing the
	 *                                  file
	 */
	public void saveAppsToFile(String fileName) {
		AppWriter.writeAppsToFile(fileName, appList.getApps());
	}

	/**
	 * Loads an application list from the provided file
	 * 
	 * @param fileName name of file application list is loaded from
	 * @throws IllegalArgumentException if any exceptions occur while processing the
	 *                                  file
	 */
	public void loadAppsFromFile(String fileName) {
		ArrayList<Application> apps = AppReader.readAppsFromFile(fileName);
		createNewAppList();
		appList.addApps(apps);
	}

	/**
	 * Creates a new application list
	 */
	public void createNewAppList() {
		appList = new AppList();
	}

	/**
	 * Gets a 2D list of objects based on the application list
	 * 
	 * @return 2D array of applications
	 */
	public Object[][] getAppListAsArray() {
		ArrayList<Application> list = appList.getApps();
		Object[][] output = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			output[i][0] = list.get(i).getAppId();
			output[i][1] = list.get(i).getStateName();
			output[i][2] = list.get(i).getAppType();
			output[i][3] = list.get(i).getSummary();
		}
		return output;
	}

	/**
	 * Gets a 2D list of objects based on the application list and application type
	 * 
	 * @param appType type of application being listed
	 * @return 2D array of applications based on type
	 * @throws IllegalArgumentException if appType is null
	 */
	public Object[][] getAppListAsArrayByAppType(String appType) {
		if (appType == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<Application> list = appList.getAppsByType(appType);
		Object[][] output = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			output[i][0] = list.get(i).getAppId();
			output[i][1] = list.get(i).getStateName();
			output[i][2] = list.get(i).getAppType();
			output[i][3] = list.get(i).getSummary();
		}
		return output;
	}

	/**
	 * Gets an application by its id
	 * 
	 * @param id id of application
	 * @return application of corresponding id
	 */
	public Application getAppById(int id) {
		return appList.getAppById(id);
	}

	/**
	 * Executes the given command with the corresponding application based on
	 * application id
	 * 
	 * @param id      id of application being commanded
	 * @param command command being executed
	 */
	public void executeCommand(int id, Command command) {
		appList.executeCommand(id, command);
	}

	/**
	 * Deletes an application from the application list based on id
	 * 
	 * @param id id of application being deleted
	 */
	public void deleteAppById(int id) {
		appList.deleteAppById(id);
	}

	/**
	 * Adds a single application to the application list. Adds application by using
	 * application type, summary, and note
	 * 
	 * @param appType type of application
	 * @param summary summary of application
	 * @param note    application note
	 */
	public void addAppToList(AppType appType, String summary, String note) {
		appList.addApp(appType, summary, note);
	}
}
