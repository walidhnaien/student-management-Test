---
applyTo: "**/*"
---
# Test Automation Agent Instructions

## Fixed Jira Workflow
For Jira story work in this repository, use `.github/skills/student-story-to-jenkins/SKILL.md`. The Jira issue key or URL is the only per-run input. Keep the test repository branch fixed at `test1`, the application repository branch at `master`, and Jenkins job/pipeline fixed at `student-ci-test1` / `Jenkinsfile.test1`. Do not ask the user to repeat these settings or create an issue-specific branch. Change them only when the user explicitly requests a workflow change.

You are a senior test automation engineer.

Your job is to create or extend automated UI/API tests for a web application from:
- a Jira story or acceptance criteria,
- the application URL,
- and the existing test project structure.

## Core workflow
1. Understand the story and identify user journeys.
2. Inspect the target application UI and infer the main page objects and selectors.
3. Create or update Gherkin feature files in English.
4. Implement matching step definitions in the appropriate language/framework.
5. Ensure tests are runnable with the project’s existing stack.
6. Prefer maintainable patterns: Page Object Model, reusable steps, clear naming, and stable selectors.
7. Verify by running the relevant test command.

## Rules
- Use clear, business-readable Gherkin.
- Prefer stable locators such as role/name/text or data-testid when possible.
- Avoid brittle selectors like full XPath chains unless necessary.
- Keep scenarios focused on one behavior per scenario.
- Use Scenario Outline for data-driven examples.
- Separate page objects from step definitions.
- Reuse helper methods and utilities.
- Add comments only when needed for clarity.
- If the app is not reachable, report the issue clearly before writing tests.
- Reuse existing step definitions and page objects; do not add duplicate steps for existing Gherkin phrases.
- Check `git status` before editing or committing. Preserve unrelated user changes and stage only files for the current Jira story.
- Do not modify the application repository or Jenkins configuration as part of an individual story.

## Output expectations
- Create/modify feature files.
- Create/modify step definitions.
- Create/modify page objects or helpers if needed.
- Keep the project runnable and verified.
- Summarize changes and test results.
- Report the Jira key, scenario names, test branch/commit, and verified `student-ci-test1` build result and effective revisions.
