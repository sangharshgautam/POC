package org.sangharsh.pages;

import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
public abstract class AbstractPage extends PageObject {
	protected void isAvailable(WebElementFacade field) {
//		Assert.assertTrue(field.isVisible());
	}
	protected void clearAndType(WebElementFacade field, String password) {
		field.clear();
		field.type(password);
	}
	
	protected void select(WebElementFacade field, String value) {
		List<String> options = field.getSelectOptions();
		MatcherAssert.assertThat(options.contains(value), Matchers.is(true));
		field.selectByValue(value);
	}
	protected ExpectedCondition<Boolean> elementIsDisplayed(final WebElementFacade element) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return (element.isDisplayed());
            }
        };
    }
}
