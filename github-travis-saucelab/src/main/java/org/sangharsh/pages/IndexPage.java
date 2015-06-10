package org.sangharsh.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("http://www.nikweli.com")
public class IndexPage extends AbstractPage {

	@FindBy(xpath = "//*[@id='details']")
	private WebElementFacade positionDd;
	
	@FindBy(xpath = "//button[text()=' Search']")
	private WebElementFacade btnSearch;
	
	@FindBy(xpath = "//div[@class='toggle-filter']/a")
	private WebElementFacade btnAdvanceSearch;
	
	@FindBy(xpath = "//*[@id='districtList']")
	private WebElementFacade district;
	
	@FindBy(xpath = "//*[@id='wardList']")
	private WebElementFacade ward;
	
	public void checkSearchFieldIsDisplayed() {
		isAvailable(this.positionDd);	
	}

	public void position(String text) {
		super.select(positionDd, text);
	}
	
	public void search() {
		this.btnSearch.click();
	}
	
	public void waitForTextFieldTobeVisible(){
		waitForCondition().until(elementIsDisplayed(positionDd));
	}
	
	public void openAdvanceSearch(){
		btnAdvanceSearch.click();
	}

	public void district(String district) {
		select(this.district, district);
	}
	
	public void ward(String ward) {
		select(this.ward, ward);
	}

}
