package org.sangharsh;
import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.sangharsh.steps.SearchSteps;
public class UserSteps{
	
	@Steps
	SearchSteps searchSteps;
	
	@Given("the user is on search page")
	public void givenTheUserIsOnIndexPage(){
		searchSteps.openIndexPage(); 
	}
}