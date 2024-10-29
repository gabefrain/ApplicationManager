package edu.ncsu.csc216.app_manager.model.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.app_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;

/**
 * Tests the Command Class
 * 
 * Getters are tested through the valid constructor test
 * 
 * @author Gabe Frain
 */
public class CommandTest {

	/** Valid Command Value */
	private static final CommandValue C = CommandValue.ACCEPT;
	/** Null Command Value */
	private static final CommandValue NC = null;
	/** Standby Command Value */
	private static final CommandValue SC = CommandValue.STANDBY;
	/** Reject Command Value */
	private static final CommandValue RC = CommandValue.REJECT;
	/** Reviewer Id */
	private static final String RID = "ghfrain";
	/** Null Reviewer Id */
	private static final String NRID = null;
	/** Empty Reviewer Id */
	private static final String ERID = "";
	/** Resolution */
	private static final Resolution R = Resolution.REVCOMPLETED;
	/** Null Resolution */
	private static final Resolution NR = null;
	/** Note */
	private static final String NOTE = "Example note";
	/** Null Note */
	private static final String NNOTE = null;
	/** Empty Note */
	private static final String ENOTE = "";

	/**
	 * Tests the Command constructor
	 */
	@Test
	public void testCommand() {
		// Test a valid command
		Command validcommand = new Command(C, RID, R, NOTE);
		assertEquals(C, validcommand.getCommand());
		assertEquals(RID, validcommand.getReviewerId());
		assertEquals(R, validcommand.getResolution());
		assertEquals(NOTE, validcommand.getNote());

		// Test command with null command value
		Exception nce = assertThrows(IllegalArgumentException.class, () -> new Command(NC, RID, R, NOTE));
		assertEquals("Invalid information.", nce.getMessage());

		// Test command with accept command value and null reviewer id
		Exception acnrie = assertThrows(IllegalArgumentException.class, () -> new Command(C, NRID, R, NOTE));
		assertEquals("Invalid information.", acnrie.getMessage());

		// Test command with accept command value and null reviewer id
		Exception acerie = assertThrows(IllegalArgumentException.class, () -> new Command(C, ERID, R, NOTE));
		assertEquals("Invalid information.", acerie.getMessage());

		// Test command with standby command value and null resolution
		Exception scnre = assertThrows(IllegalArgumentException.class, () -> new Command(SC, RID, NR, NOTE));
		assertEquals("Invalid information.", scnre.getMessage());

		// Test command with reject command value and null resolution
		Exception rcnre = assertThrows(IllegalArgumentException.class, () -> new Command(RC, RID, NR, NOTE));
		assertEquals("Invalid information.", rcnre.getMessage());

		// Test command with null note
		Exception nne = assertThrows(IllegalArgumentException.class, () -> new Command(C, RID, R, NNOTE));
		assertEquals("Invalid information.", nne.getMessage());

		// Test command with empty note
		Exception ene = assertThrows(IllegalArgumentException.class, () -> new Command(C, RID, R, ENOTE));
		assertEquals("Invalid information.", ene.getMessage());
	}

}
