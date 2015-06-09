package org.sangharsh.steps;

import net.thucydides.core.pages.Pages;

import org.sangharsh.pages.IndexPage;

public abstract class SearchSteps extends GlobalSteps {

	IndexPage indexPage;
	
	public SearchSteps(Pages pages){
		super(pages);
		indexPage = getPages().get(IndexPage.class);
	}

	public void openIndexPage() {
		indexPage.open();
	}

}
