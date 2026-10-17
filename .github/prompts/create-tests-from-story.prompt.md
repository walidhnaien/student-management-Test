# Create tests from a Jira story

Use the repository conventions and the instructions in .github/instructions/testing-agent.instructions.md.

## Only per-run input
- Jira issue key or URL: `<JIRA-ISSUE>`

All other values are fixed in `.github/skills/student-story-to-jenkins/SKILL.md`; do not request or change the repository, branch, application URL, or Jenkins job for each story.

## Tasks
1. Read the supplied Jira issue and acceptance criteria.
2. Inspect existing features, step definitions, page objects, hooks, runner, and relevant UI.
3. Add focused Gherkin only where needed and reuse matching step definitions; avoid duplicates.
4. Implement only missing step definitions or page-object methods.
5. Verify bindings and run the relevant Maven tests.
6. Commit only story-specific files to `test1` and push to the existing test repository.
7. Trigger `student-ci-test1` and verify it checked out the pushed `test1` commit and application `master`.

## Expected result
A runnable, maintainable automation suite for the new Jira issue, using the fixed `test1` test branch and `student-ci-test1` pipeline.
