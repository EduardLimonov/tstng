Feature: Exercise2 of hw3

  Background:
    Given I'm opened homepage
    And I'm logged as Roman - Jdi1234

  Scenario: assert browser title
    Then Browser title is "Home Page"

  Scenario: assert username
    Then Username is "ROMAN IOVLEV"

  Scenario: assert header list items
    When I click on service at header
    Then I see 9 header list items
    And  Header list items have proper text

  Scenario: assert sidebar list items
    When I click on service at left sidebar
    Then I see 9 sidebar list items
    And  Sidebar list items have proper text

  Scenario: looking around at Element Page
    Given I am on Element Page
    Then I see 4 checkboxes
    And I see 4 radio buttons
    And I see the dropdown
    And I see the button1
    And I see the button2

  Scenario: element page right sidebar
    Given I am on Element Page
    Then I see the right sidebar

  Scenario:
    Given I am on Element Page
    Then I see the left sidebar

  Scenario:
    Given I am on Element Page
    When I select "Water, Wind" at checkboxes
    Then "Water, Wind" are checked

  Scenario:
    Given I am on Element Page
    When I select "Water, Wind" at checkboxes
    Then "Water, Wind" is logged as "true, true"

  Scenario: selen is selected
    Given I am on Element Page
    When I select "Selen" at radios
    Then "Selen" is selected at radios

  Scenario: log test
    Given I am on Element Page
    When I select "Selen" at radios
    Then "metal" is logged as "Selen"

  Scenario: yellow is selected
    Given I am on Element Page
    When I select "Yellow" at dropdown
    Then "Yellow" is selected at dropdown

  Scenario: log test 2
    Given I am on Element Page
    When I select "Yellow" at dropdown
    Then "Colors" is logged as "Yellow"

  Scenario:
    Given I am on Element Page
    When I select "Water, Wind" at checkboxes
    And  I select "Water, Wind" at checkboxes
    Then "Water, Wind" are not checked

  Scenario: log test
    Given I am on Element Page
    When I select "Water, Wind" at checkboxes
    And  I select "Water, Wind" at checkboxes
    Then "Water, Wind, Water, Wind" is logged as "true, true, false, false"