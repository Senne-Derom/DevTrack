import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";

When("I click on the \"Add study entry\" button", () => {
    cy.contains("a", "Add study entry").click();
});

When("I fill in the following study entry details:", (dataTable) => {
    dataTable.hashes().forEach((detail: { Field: string; Value: string; Type: string }) => {
        const field = detail.Field.trim();
        const type = detail.Type.trim();
        const value = detail.Value;

        if (type === "select") {
            const select = cy.contains("label", field).siblings("select");
            if (value === "") {
                select.select("");
            } else {
                select.select(value);
            }
            return;
        }

        const input = cy.contains("label", field).siblings("input");
        input.clear();
        if (value !== "") {
            input.type(value);
        }
    });
});

When("I click the \"Add Study Entry\" button", () => {
    cy.contains("button", "Add Study Entry").click();
});

Then("I should see a success message indicating that the study entry was added successfully", () => {
    cy.contains("Study entry added successfully.").should("be.visible");
});

Then("I should see an error message indicating that a valid course must be selected", () => {
    cy.contains("Course is required.").should("be.visible");
});

Then("I should see an error message indicating that a description is required", () => {
    cy.contains("Description is required").should("be.visible");
});

Then("I should see an error message indicating that time spent must be a positive number", () => {
    cy.get("#timeSpentInput").invoke("prop", "validationMessage").should("not.be.empty");
});

Then("I should see an error message indicating that a date is required", () => {
    cy.contains("Date is required.").should("be.visible");
});