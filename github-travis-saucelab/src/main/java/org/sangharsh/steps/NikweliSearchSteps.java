package org.sangharsh.steps;

import net.thucydides.core.pages.Pages;

import org.sangharsh.pages.IndexPage;
import org.sangharsh.pages.SearchResultsPage;

public abstract class NikweliSearchSteps extends GlobalSteps {

	IndexPage indexPage;
	
	SearchResultsPage resultPage;
	
	public NikweliSearchSteps(Pages pages){
		super(pages);
		indexPage = getPages().get(IndexPage.class);
		resultPage = getPages().get(SearchResultsPage.class);
	}

	public void openIndexPage() {
		indexPage.open();
	}

	public void setPosition(String position) {
		indexPage.position(position);
	}

	public void search(){
		indexPage.search();
	}

	public void resultDisplayed() {
		resultPage.resultIsDisplayed();
	}

	public void clickAdvanceSearch() {
		indexPage.openAdvanceSearch();
	}

	public void district(String district) {
		indexPage.district(district);
	}
}
