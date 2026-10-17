# Agent setup for test automation

This folder contains reusable instructions, prompts, and a custom agent for Jira-driven Student Management UI automation.

## Files
- .github/instructions/testing-agent.instructions.md
  General instructions for creating or extending tests from Jira stories and application URLs.
- .github/prompts/create-tests-from-story.prompt.md
- .github/agents/student-story-to-jenkins.agent.md
- .github/skills/student-story-to-jenkins/SKILL.md
- Jenkinsfile.test1 (at the repository root)
  Prompt template for generating tests from a story.

## How to use it
Invoke the `Student Story to Jenkins` agent with the Jira issue key or URL. That Jira reference is the only per-run input; repository, branch, application, and Jenkins settings stay fixed in the skill.

The workflow uses test repository branch `test1`, application branch `master`, and Jenkins job `student-ci-test1` loading `Jenkinsfile.test1`. The existing `student-ci-test` job is not used for this workflow.

## Recommended pattern for any testing project
- Keep feature files in src/test/resources/features
- Keep step definitions in src/test/java/.../steps
- Keep UI interactions in page objects under src/test/java/.../pages
- Keep shared state in support/context classes
- Use a runner class such as RunCucumberTest
