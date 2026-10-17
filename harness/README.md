# Student BDD Automation Harness

This repository is the test harness for `student-management-system`. For Jira stories, the only per-run input is the Jira issue key or URL; use the fixed workflow profile below for everything else.

## Layout

```text
.github/
  agents/student-story-to-jenkins.agent.md
  skills/student-story-to-jenkins/SKILL.md
  instructions/testing-agent.instructions.md
harness/
  .env.example
  README.md
src/test/
  java/com/training/student/{hooks,pages,runners,steps,support}/
  resources/features/
```

The Java test harness uses Java 17, Maven, Cucumber/Gherkin, Selenium WebDriver, JUnit Platform, and the Page Object Model. The Copilot agent coordinates Jira story analysis, test implementation, feature-branch delivery, and Jenkins verification; it does not replace the test runner.

## Configuration

Use the variable names in `.env.example` as an inventory. Supply values to the VS Code/agent process through your operating-system environment or an approved secret manager. Do not commit a populated file. `/harness/.env` is ignored as a defense in depth, but local plaintext files are not a recommended secret store and are not automatically loaded by the agent.

Minimum access:

- `GITHUB_TOKEN`: contents read/write on `walidhnaien/student-management-Test` only. Pull-request access is not needed unless explicitly requested.
- `JIRA_EMAIL` and `JIRA_API_TOKEN`: read access to the Jira project and issue.
- `JENKINS_USER` and `JENKINS_API_TOKEN`: read job/build information and start builds for `student-ci-test1` only. Do not change Jenkins job configuration during a story run.

Never put tokens in chat, documentation, source files, Git URLs, command arguments, or logs. If a token has been shared or committed, revoke it and create a replacement before continuing.

## Endpoints

- Jira: `https://hnaien-1792145124924.atlassian.net`
- Application: `http://localhost:4200`
- Application source: `https://github.com/walidhnaien/student-management-system`
- Test source: `https://github.com/walidhnaien/student-management-Test`
- Test branch: `test1` (fixed for all Jira stories)
- Application branch: `master` (fixed)
- Jenkins job: `http://localhost:8084/job/student-ci-test1/`
- Jenkins pipeline file: `Jenkinsfile.test1` on the test repository's `test1` branch

The `student-ci-test1` job is configured as Pipeline from SCM with repository `student-management-Test`, SCM branch `*/test1`, and Script Path `Jenkinsfile.test1`. That pipeline deploys `student-management-system` from `master`, then tests `student-management-Test` from `test1`. The agent must verify both effective checkouts and the completed build result; never substitute the old `student-ci-test` job or infer success from the trigger response.

Run the existing suite locally with `mvn -B clean test -Dheadless=true -DbaseUrl=http://localhost:4200`. For step-binding verification, use a Cucumber dry-run filtered to the target scenario. Commit only Jira-specific source/feature changes to `test1`; leave generated `target/` files and unrelated local changes untouched.