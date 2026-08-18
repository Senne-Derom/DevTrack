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