# Project context

This repository is a starter template for a greenfield Java project used in an introductory software engineering course in an undergraduate computer science program. Students use it as the starting point for their own projects.

# Default user context

Unless the user says otherwise, assume that you are assisting a student working on a project in this repository. If the user identifies themselves as an instructor or another project stakeholder, adapt your response to that role.

# Student profile

* Prior knowledge: Basic Java and OOP concepts.
* Level of programming experience: Comfortable with basic Java/OOP; new to building a multi-class Java project from scratch and to Git workflows beyond basic add/commit/push.
* IDE and level of expertise: IntelliJ IDEA, still learning the IDE (new to its refactoring tools, run configs, and debugger).

# Guidance for interacting with users

* Explain the rationale for significant actions: what you did and why.
* Keep explanations brief but instructive, supporting learning through responsible use of AI. For example:

  * When suggesting a Git command, briefly explain what it does.
  * Add explanatory Javadoc comments to all classes and to nontrivial methods and fields when their purpose or behavior is not obvious.
  * Make generated code as self-explanatory as possible, and include explanatory comments where they improve understanding.
  * When faced with a design choice, choose the simplest option that is sufficient for the requirements, while briefly explaining relevant more advanced alternatives.

# Project-specific requirements

## Java version:

Ensure that Java 25 is used when running the application or build tasks. On macOS, use `sdk use java 25.0.3.fx-zulu` to switch to Java 25 if needed.

## Coding standard

All Java code in this project (new code and edits to existing code) must follow the `seedu-java-coding-standard` skill: naming, layout, statement style, and comment rules. Invoke it whenever writing, editing, or reviewing a `.java` file.

## Git

* Use lightweight tags unless the user requests an annotated tag.
* Always merge branches with `--no-ff` (e.g. `git merge --no-ff branch-name`), so that every merge creates an explicit merge commit and the branch's history stays visible in the commit graph.
* Do not commit or push unless explicitly asked.
* Never edit or amend a pushed commit message.

All commit messages and branch names must follow the `seedu-git-standard` skill. Invoke it whenever proposing a commit message or naming a branch.

## Implementation

* When implementing a stated requirement, implement only what's asked. If existing code won't compile without handling a case outside the stated requirement, stop and ask rather than filling the gap with your own design choice.

* If you introduce a user-facing string, command name, or class name that does not appear in this repo or in the prompt, say so explicitly in your response rather than adding it silently.

## Testing

* Target ~50% JUnit test coverage, focused on the highest-value methods: complex, core, or critical business logic (e.g. parsing/validation, command execution, persistence round-trips, domain formatting). Deprioritize thin I/O wrappers, the program's entry point/main loop, and trivial one-line methods.
* After any code change, update the JUnit tests so coverage of the affected logic stays at that target — add tests for new logic, and fix or extend existing tests for changed logic, rather than letting them go stale.
