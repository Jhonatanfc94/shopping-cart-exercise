Feature: Shopping Cart
  Scenario: Add and delete product
    Given I am on the home page
    When I search for "iPhone"
    Then I should see the search results page
    When I click "iPhone" from search results
    And I add the product to the shopping cart
    Then I should see "Success: You have added iPhone to your shopping cart!" alert
    When I click "Shopping Cart" from top menu
    Then I should see "iPhone" in the shopping cart
    When I remove "iPhone" from the cart
    Then I should not see "iPhone" in the shopping cart
