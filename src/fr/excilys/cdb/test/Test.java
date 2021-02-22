package fr.excilys.cdb.test;

import junit.framework.TestCase;
import main.Main;

public class Test extends TestCase {
	
	public void testCalcul() throws Exception {
		assertEquals(2,Main.calcul(1,1));
	}

}
