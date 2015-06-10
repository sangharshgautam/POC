package org.sangharsh;

import net.serenitybdd.jbehave.SerenityStories;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;


public class AcceptanceTestSuite extends SerenityStories {
	
	public AcceptanceTestSuite(){
		/*EnvironmentVariables ev = new SystemEnvironmentVariables();
		ev.setProperty("webdriver.firefox.bin", "C:\\Users\\sagautam\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
		super.setEnvironmentVariables(ev);*/
		runSerenity().inASingleSession();
	}
}
