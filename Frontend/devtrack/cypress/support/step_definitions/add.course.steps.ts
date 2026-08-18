import {Given, When, Then} from "@badeball/cypress-cucumber-preprocessor";

Given("I navigate to the study progress page", () => {
    cy.visit("http://localhost:3000/study-progress");
});

When("I click on the \"Add course\" button", () => {
    cy.contains('a', 'Add course').click();
});

When("I fill in the following course details:", (dataTable) => {
    dataTable.hashes().forEach((detail: { Field: string; Value: string }) => {
        cy.contains("label", detail.Field)
            .siblings("input")
            .type(`{selectall}${detail.Value}`);
    });
});

When("I click the \"Add Course\" button", () => {
    cy.contains('button', 'Add Course').click();
});

Then("I should see a success message indicating that the course was added successfully", () => {
    cy.contains("Course added successfully").should("be.visible");
});