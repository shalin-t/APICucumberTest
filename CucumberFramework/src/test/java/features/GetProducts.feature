Feature: API Test - Manage Shopping Carts

  Scenario: Verify the GET API for retrieving products
    Given I set up the API request
    When I send a GET request to retrieve products
    Then I receive the response code as 200

  Scenario: Add a cart for a user
    Given I set up the API request
    When I send a POST request to add a cart for user 1 with products
    Then The cart is successfully created with status 200

  Scenario: Retrieve a cart by ID
    Given I set up the API request
    When I send a GET request for cart by id 1
    Then I receive the cart details with status 200

  Scenario: Delete a cart
    Given I set up the API request
    When I send a DELETE request for the created cart
    Then The cart is successfully deleted with status 200
