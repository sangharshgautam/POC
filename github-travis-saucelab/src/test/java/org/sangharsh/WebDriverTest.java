package org.sangharsh;

import java.net.URL;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverTest {
	private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        // Choose the browser, version, and platform to test
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("version", "17");
        capabilities.setCapability("platform", Platform.XP);
        // Create the connection to Sauce Labs to run the tests
        this.driver = new RemoteWebDriver(new URL("http://sangharshgautam:a74ca004-af23-4af2-b4b3-0ad926f37967@ondemand.saucelabs.com:80/wd/hub"),capabilities);
    }

    @Test
    public void webDriver() throws Exception {
        // Make the browser get the page and check its title
        driver.get("http://www.amazon.com/");
        MatcherAssert.assertThat("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", Matchers.equalTo(driver.getTitle()));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
