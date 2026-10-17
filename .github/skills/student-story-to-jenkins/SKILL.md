---
name: student-story-to-jenkins
description: "Use a Jira issue key or URL as the only variable to add Student Management Cucumber/Selenium automation on test1, push it, and verify the student-ci-test1 Jenkins build."
---

# Student Story to Jenkins

Run this workflow when given a Jira issue key or URL. That Jira reference is the only per-run input; do not ask the user to restate the repository, branch, application, or Jenkins settings. The automation project is Java 17, Maven, Cucumber, Selenium, and JUnit. Keep Gherkin in English and follow the existing Page Object Model under `src/test/java/com/training/student`.

## Fixed workflow profile

- Test repository: `https://github.com/walidhnaien/student-management-Test.git`
- Test branch for every story: `test1`; continue using this branch instead of creating `feature/<issue>` branches.
- Application repository and branch: `https://github.com/walidhnaien/student-management-system.git`, `master`.
- Local application URL when available: `http://localhost:4200`.
- Jenkins job: `http://localhost:8084/job/student-ci-test1/`.
- Jenkins pipeline file: `Jenkinsfile.test1`, loaded from the test repository's `test1` branch. It deploys the application from `master` and checks out tests from `test1`.
- Existing job `student-ci-test` is not the story workflow job. Do not change its configuration or use it to claim a `test1` result.

These values stay fixed across stories unless the user explicitly requests a workflow change.

## Credentials and access

- Read the required variable names and minimum permissions in `harness/README.md`.
- Use environment variables or an approved secret manager. Never put credentials in source, `.md` files, `.env.example`, git remotes, shell history, URLs, screenshots, or logs.
- Use read-only Jira access to retrieve the issue description and acceptance criteria.
- Limit GitHub write access to contents on the test repository's `test1` branch. Do not create pull requests unless explicitly requested; never merge.
- For normal story runs, use Jenkins only to inspect and start the existing `student-ci-test1` job. Do not create or reconfigure Jenkins jobs for individual stories.
- If a credential is missing, expired, or unauthorized, stop that integration and report the exact missing permission without requesting the secret in chat.

## Workflow

1. Read the supplied Jira issue. Retrieve its summary, description, acceptance criteria, status, and links. If criteria are absent or ambiguous, ask a focused question before inventing expected behavior.
2. Inspect the existing feature files, step definitions, page objects, hooks, runner, and relevant application UI at `http://localhost:4200` when available. Confirm stable locators. If the application cannot be inspected and acceptance behavior depends on it, report that before writing tests.
3. Translate independently testable acceptance criteria into focused business-readable Gherkin. Reuse existing steps and page objects; add only missing Java automation. Do not duplicate a step that already covers the feature text. Do not change the application repository.
4. Work on the existing `test1` branch. Check `git status` first and preserve unrelated user changes. Do not create an issue-specific branch.
5. Run a focused Cucumber dry-run to verify step bindings, then the relevant Maven test(s); run the full suite when practical. Report local failures honestly and do not treat a local pass as a Jenkins pass.
6. Stage only story-related feature/step/page-object files. Do not commit generated `target/` output or unrelated changes. Commit and push the story update to `origin/test1`; create a pull request only if explicitly requested.
7. Read the `student-ci-test1` job configuration before triggering it. It must load `Jenkinsfile.test1` from test repo branch `test1`; that pipeline must check out application branch `master` and tests branch `test1`. If this profile is missing or differs, stop and report the administrator action; do not use `student-ci-test` as a substitute.
8. Trigger `student-ci-test1`, wait for completion, and inspect console output and JUnit/Cucumber reports. Confirm the effective test commit is the pushed `test1` commit and the application checkout is `master`. A successful trigger response is not proof of a green build.
9. Never merge. Do not modify the fixed pipeline or Jenkins job during a story run unless the user explicitly requests a pipeline change.

## Completion report

Include the Jira key, scenario names, changed files, local test command/result, branch and commit, Jenkins build URL/result, and effective test/application revisions. Mark the workflow `BLOCKED` if Jenkins did not run the pushed `test1` commit or any required integration could not be verified. Only report green when the completed `student-ci-test1` build is `SUCCESS`.