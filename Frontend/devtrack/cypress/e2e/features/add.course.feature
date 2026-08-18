Feature:
  Scenario: Successfully add a course
    Given I navigate to the study progress page
    When I click on the "Add course" button
    And I fill in the following course details:
        | Field         | Value                  | Type   |
        | Name:         | Full-stack development | text   |
        | Study Points: | 5                      | number |
    And I click the "Add Course" button
    Then I should see a success message indicating that the course was added successfully
  Scenario: Fail to add a course due to missing fields
    Given I navigate to the study progress page
    When I click on the "Add course" button
    And I fill in the following course details:
        | Field         | Value | Type   |
        | Name:         |       | text   |
        | Study Points: | 5     | number |
    And I click the "Add Course" button
    Then I should see an error message indicating that a name is required
  Scenario: Fail to add a course due to invalid study points
    Given I navigate to the study progress page
    When I click on the "Add course" button
    And I fill in the following course details:
        | Field         | Value                  | Type   |
        | Name:         | Full-stack development | text   |
        | Study Points: | -5                     | number |
    And I click the "Add Course" button
    Then I should see an error message indicating that study points must be a positive number
  Scenario: Adding a course that already exists
    Given I navigate to the study progress page
    When I click on the "Add course" button
    And I fill in the following course details:
        | Field         | Value                  | Type   |
        | Name:         | Full-stack development | text   |
        | Study Points: | 5                      | number |
    And I click the "Add Course" button
    Then I should see an error message indicating that the course already exists