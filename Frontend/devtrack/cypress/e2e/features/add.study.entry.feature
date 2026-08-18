Feature:
  Scenario: Successfully add a study entry
    Given I navigate to the study progress page
    When I click on the "Add study entry" button
    And I fill in the following study entry details:
      | Field         | Value                   | Type   |
      | Course name:  | Full-stack development  | select |
      | Description:  | Worked on Next.js setup | text   |
      | Time spent:   | 2.5                     | number |
      | Date:         | 2026-08-18              | date   |
    And I click the "Add Study Entry" button
    Then I should see a success message indicating that the study entry was added successfully

  Scenario: Fail to add a study entry due to missing course
    Given I navigate to the study progress page
    When I click on the "Add study entry" button
    And I fill in the following study entry details:
      | Field         | Value                  | Type   |
      | Course name:  |                        | select |
      | Description:  | Worked on Next.js setup | text   |
      | Time spent:   | 2.5                    | number |
      | Date:         | 2026-08-18             | date   |
    And I click the "Add Study Entry" button
    Then I should see an error message indicating that a valid course must be selected

  Scenario: Fail to add a study entry due to missing description
    Given I navigate to the study progress page
    When I click on the "Add study entry" button
    And I fill in the following study entry details:
      | Field         | Value                  | Type   |
      | Course name:  | Full-stack development | select |
      | Description:  |                        | text   |
      | Time spent:   | 2.5                    | number |
      | Date:         | 2026-08-18             | date   |
    And I click the "Add Study Entry" button
    Then I should see an error message indicating that a description is required


  Scenario: Fail to add a study entry due to invalid time spent
    Given I navigate to the study progress page
    When I click on the "Add study entry" button
    And I fill in the following study entry details:
      | Field         | Value                  | Type   |
      | Course name:  | Full-stack development | select |
      | Description:  | Worked on Next.js setup | text   |
      | Time spent:   | -1                     | number |
      | Date:         | 2026-08-18             | date   |
    And I click the "Add Study Entry" button
    Then I should see an error message indicating that time spent must be a positive number

  Scenario: Fail to add a study entry due to missing date
    Given I navigate to the study progress page
    When I click on the "Add study entry" button
    And I fill in the following study entry details:
      | Field         | Value                  | Type   |
      | Course name:  | Full-stack development | select |
      | Description:  | Worked on Next.js setup | text   |
      | Time spent:   | 2.5                    | number |
      | Date:         |                        | date   |
    And I click the "Add Study Entry" button
    Then I should see an error message indicating that a date is required