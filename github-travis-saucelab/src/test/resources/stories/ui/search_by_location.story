Nikweli Story

Meta:
@author Sangharsh Gautam
@Run: Yes

@version:Release-1

Narrative:
In order to work on Nikweli
As a end user
I want to be able to search for positions

Scenario:  Search people based on location on Nikweli

Given the user is on Nikweli index page
When position dropdown is displayed
When the user clicks Advance Search
And the user selects a district $district
And the user press the Search button
Then the search result should be displayed
Examples:
	|district|
	|Ilala|
