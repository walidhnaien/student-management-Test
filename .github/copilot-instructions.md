# Copilot instructions for this repository

Use the testing agent instructions for any task involving new tests, feature files, step definitions, or UI automation.
Use the `Student Story to Jenkins` custom agent for Jira-to-GitHub-to-Jenkins story workflows. For each run, the Jira issue reference is the only variable; the fixed branch, application, and Jenkins profile are in `.github/skills/student-story-to-jenkins/SKILL.md`.

## Preferred invocation phrases
Use these phrases to trigger the testing workflow:
- "create tests from this story"
- "add BDD tests for this feature"
- "generate Cucumber tests for this application"
- "create a new feature file and step definitions"
- "automate this user journey"
- "automate Jira SCRUM-<key> and run Jenkins"

## Default behavior
- Prefer Gherkin + Cucumber for UI acceptance tests.
- Keep steps readable and business-oriented.
- Separate page objects from step definitions.
- Reuse existing steps; do not duplicate bindings.
- Keep tests on the existing `test1` branch, test the application from `master`, and use Jenkins job `student-ci-test1` with `Jenkinsfile.test1`.
- Verify the effective branch/commit and completed Jenkins result before reporting success.
