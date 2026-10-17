# Generate BDD tests from a Jira story

Use the repository conventions and the instructions in .github/instructions/testing-agent.instructions.md.

## Only per-run input
- Jira issue key or URL: `<JIRA-ISSUE>`

The fixed repository, branch, application, and Jenkins settings are defined in `.github/skills/student-story-to-jenkins/SKILL.md`. Do not ask for those values or change them per story.

## Workflow
1. Read the supplied Jira issue and acceptance criteria.
2. Inspect existing features, step definitions, page objects, runner, and relevant UI.
3. Add focused English Gherkin and only missing automation; reuse existing steps.
4. Verify step bindings and run relevant Maven tests.
5. Commit only story-related files to the existing `test1` branch, push it, then run and verify `student-ci-test1` using `Jenkinsfile.test1` (application branch `master`).

## Expected outcome
A runnable, maintainable test suite for the Jira issue, verified against the fixed `test1`/`master` Jenkins pipeline.
