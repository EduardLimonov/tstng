Feature: Exercise1 of testing3

  Background:
    Given I'm opened homepage
    And I'm logged as Roman - Jdi1234

  Scenario: assert browser title
    Then Browser title is "Home Page"

  Scenario: assert username
    Then Username is "ROMAN IOVLEV"

  Scenario: assert header contents
    Then I see 4 header items
    And Header labels are proper

  Scenario: assert there are 4 images
    Then I see 4 images

  Scenario: assert there are 4 text blocks
    Then I see 4 texts
    And Text blocks have proper text

  Scenario: assert text of the main header
    Then I see "EPAM FRAMEWORK WISHES…" as main header
    And I see "Lorem ipsum" as text below

  Scenario: check how iFrame works
    When I switch to iFrame
    Then I see the EPAM logo

  Scenario: assert sub header
    Then I see "JDI GITHUB" as sub header

  Scenario: assert hyperref
    Then The hyperref is proper

  Scenario: assert left section
    Then I see the left section

  Scenario: assert footer
    Then I see the footer