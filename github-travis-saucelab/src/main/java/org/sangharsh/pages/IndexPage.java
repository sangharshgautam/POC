package org.sangharsh.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

@DefaultUrl("http://www.google.com")
public class IndexPage extends AbstractPage {

	@FindBy(xpath = "//*[@id='lst-ib']")
	private WebElementFacade searchField;
	
	@FindBy(xpath = "//input[@value='Google Search']")
	private WebElementFacade btnSearch;
	
	public void checkSearchFieldIsDisplayed() {
		isAvailable(this.searchField);	
	}

	public void enterSearchText(String text) {
		clearAndType(searchField, text);
	}
	
	public void clickGoogleSearch(){
		this.btnSearch.click();
	}
	
	public void waitForTextFieldTobeVisible(){
		waitForCondition().until(textFieldIsVisible());
	}
	
	private ExpectedCondition<Boolean> textFieldIsVisible() {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return (searchField.isDisplayed());
            }
        };
    }
	
	

	public void assertInlineError(String inlineError) {
//		MatcherAssert.assertThat("Username Inline Error is displayed", usernameInlineError.isDisplayed());
//		MatcherAssert.assertThat(usernameInlineError.getTextValue(), Matchers.is(Matchers.equalTo(inlineError)));
	}
	
	public void assertDetails(String details) {
//		MatcherAssert.assertThat("Error Details is displayed", errroDetails.isDisplayed());
//		MatcherAssert.assertThat(errroDetails.getTextValue(), Matchers.is(Matchers.equalTo(details)));
	}
}
