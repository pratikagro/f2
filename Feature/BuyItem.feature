Feature: User will search items and add them to kart and check out

Scenario:  User opens greenKart website
Given : Browser is already open
When : User types URL
And  : navigates to greenKart URL
Then : GreenKart website opens up

Scenario Outline: User searches for item
Given : URL is already open
When User types <item>
And : User clicks on search items
Then <item> gets listed
Examples:
|item      |
|Cucumber  |
|Brocolli  |

Scenario:  User adds items to kart
Given : items is listed
When : User clicks on add to kart button
Then : item is added to the kart
Then Browser is closed 