package org.sangharsh;
import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.sangharsh.steps.NikweliSearchSteps;
public class Nikweli{
	
	@Steps
	NikweliSearchSteps searchSteps;
	
	@Given("the user is on Nikweli index page")
	public void givenTheUserIsOnIndexPage(){
		searchSteps.openIndexPage(); 
	}
	
	@When("position dropdown is displayed")
	public void positionDropDownDisplayed(){
		
	}
	
	@When("the user selects $position")
	public void selectPosition(@Named("position") String position){
		searchSteps.setPosition(position);
	}
	
	@When("the user press the Search button")
	public void clickSearch(){
		searchSteps.search();
	}
	
	@Then("the search result should be displayed")
	public void resultDisplayed(){
		searchSteps.resultDisplayed();
	}

	@When("the user clicks Advance Search")
	public void openAdvanceFilters(){
		searchSteps.clickAdvanceSearch();
	}
	
	@When("the user selects a district $district")
	public void selectrDistrict(String district){
		searchSteps.district(district);
	}
}