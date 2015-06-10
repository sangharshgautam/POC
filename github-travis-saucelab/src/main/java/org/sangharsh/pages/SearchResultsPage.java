package org.sangharsh.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import org.hamcrest.MatcherAssert;

public class SearchResultsPage extends AbstractPage{
	
	@FindBy(xpath = "//table[contains(@class, 'search-result')]")
	public WebElementFacade searchResults;
	
	public void resultIsDisplayed(){
		waitForCondition().until(elementIsDisplayed(searchResults));
		MatcherAssert.assertThat("Search Result is Available", searchResults.isDisplayed());
//		List<WebElementFacade> records = searchResults.findBy(".details-tr-sel");
//		System.out.println("##################### "+records.size());
	}
}
