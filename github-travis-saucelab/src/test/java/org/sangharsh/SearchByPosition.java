package org.sangharsh;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.sangharsh.steps.NikweliSearchSteps;

@RunWith(SerenityRunner.class)
public class SearchByPosition {

	public SearchByPosition(){
		System.setProperty("webdriver.firefox.bin", "C:\\Users\\sagautam\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
//		System.setProperty("webdriver.firefox.driver", "C:\\Users\\sagautam\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
	}
	
    @Managed(driver="firefox", uniqueSession = true)                              
    WebDriver driver;

    @Steps                                                                       
    NikweliSearchSteps steps;

    @Test
    public void should_see_a_list_of_items_related_to_the_specified_keyword() {  
        // GIVEN
    	steps.openIndexPage();
        // WHEN
    	steps.setPosition("Accounting");
        // THEN.
    	steps.resultDisplayed();
    }
}
