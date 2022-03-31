Feature: User account

  Scenario Outline: Adding information to create a new account
    Given I have started "<browser>"
    And I enter my "<email>"
    And I enter a username "<username>"
    And I enter a "<password>"
    When I click on SignUp button
    Then I get registration <result>

    Examples:
      | browser | email   | username     | password | result           |
      | chrome  | hannahM | Hannah       | Hejsan-8 | successful       |
      | edge    | hannahM | Hannahbanana | Hejsan-8 | taken            |
      | chrome  |         | Hannah       | Hejsan-8 | email missing    |
      | edge    | hannahM | Hannahlong   | Hejsan-8 | username to long |


