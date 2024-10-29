package edu.ncsu.csc216.app_manager.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;

/**
 * This class represents a list of Applications.
 * 
 * @author Gabe Frain
 */
public class AppList {

	/** Counter of next application id */
	private int counter;
	/** List of applications */
	private ArrayList<Application> apps;

	/**
	 * Constructs a list of applications and sets the counter to 0
	 */
	public AppList() {
		apps = new ArrayList<Application>();
		counter = 0;
	}

	/**
	 * Adds a single application to the application list. Adds application by using
	 * counter as id and other parameters
	 * 
	 * @param appType type of application
	 * @param summary summary of application
	 * @param note    application note
	 * @return new counter number
	 */
	public int addApp(AppType appType, String summary, String note) {
		counter = counter + 1;
		apps.add(new Application(counter, appType, summary, note));
		return counter;
	}

	/**
	 * Adds a list of applications to the application list
	 * 
	 * @param applicationlist list of applications being added
	 */
	public void addApps(ArrayList<Application> applicationlist) {
		apps = new ArrayList<Application>();
		counter = 0;
		for (int i = 0; i < applicationlist.size(); i++) {
			addApp(applicationlist.get(i));
		}
		counter = apps.get(apps.size() - 1).getAppId();
	}

	/**
	 * Adds a single application to the application list. Checks for duplicate ids
	 * and sorts the applications by id
	 * 
	 * @param app application being added
	 */
	private void addApp(Application app) {
		boolean dup = false;
		for (int i = 0; i < apps.size(); i++) {
			if (apps.get(i).getAppId() == app.getAppId()) {
				dup = true;
			}
		}
		if (!dup) {
			int index = 0;
			for (int j = 0; j < apps.size(); j++) {
				if (app.getAppId() > apps.get(j).getAppId()) {
					index = j + 1;
				}
			}
			apps.add(index, app);
		}
	}

	/**
	 * Gets the entire application list
	 * 
	 * @return entire list of applications
	 */
	public ArrayList<Application> getApps() {
		return apps;
	}

	/**
	 * Gets the application list by type
	 * 
	 * @param type type of applications on list
	 * @return list of applications of a certain type
	 * @throws IllegalArgumentException if type is null
	 */
	public ArrayList<Application> getAppsByType(String type) {
		if (type == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<Application> appByType = new ArrayList<Application>();
		for (int i = 0; i < apps.size(); i++) {
			if (type.equals(apps.get(i).getAppType())) {
				appByType.add(apps.get(i));
			}
		}
		return appByType;
	}

	/**
	 * Gets an application by its id
	 * 
	 * @param id id of application
	 * @return application with corresponding id
	 */
	public Application getAppById(int id) {
		for (int i = 0; i < apps.size(); i++) {
			if (apps.get(i).getAppId() == id) {
				return apps.get(i);
			}
		}
		return null;
	}

	/**
	 * Executes the given command with the corresponding application based on
	 * application id
	 * 
	 * @param id      id of application being commanded
	 * @param command command being executed
	 */
	public void executeCommand(int id, Command command) {
		for (int i = 0; i < apps.size(); i++) {
			if (apps.get(i).getAppId() == id) {
				apps.get(i).update(command);
			}
		}
	}

	/**
	 * Deletes an application from the application list based on id
	 * 
	 * @param id id of application being deleted
	 */
	public void deleteAppById(int id) {
		for (int i = 0; i < apps.size(); i++) {
			if (apps.get(i).getAppId() == id) {
				apps.remove(i);
			}
		}
	}
}
