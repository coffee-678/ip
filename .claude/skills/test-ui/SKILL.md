# Test UI

Run the project's UI test cases against the `Duncan` program and report the
results. Use this when asked to test the UI, run UI tests, or check the
program's console output against expectations.

## What this does

`test/ui-test-plan.md` holds the list of test cases. Each test case is a
single program session: an ordered list of commands to type in, and the
exact console output that session is expected to produce (from the startup
banner through the exit message).

For each test case, in order, the harness:

1. Compiles the current sources under `src/main/java/`.
2. Feeds the test case's commands to the program's stdin as one session and
   captures everything it prints to stdout.
3. Compares that actual output against the expected output recorded in the
   plan.
4. Prints a console-session record — each command paired with the block of
   output it produced — so the session can be reviewed.

If a test case's actual output does not match its expected output, the
harness stops immediately (it does not run the remaining test cases) and
reports both the expected and the actual output for that test case.

## Running it

From the repository root:

```bash
python3 .claude/skills/test-ui/scripts/run_ui_tests.py . test/ui-test-plan.md
```

Run this, then relay its output to the user:

* On success, report how many test cases passed.
* On failure, report which test case failed, its aim, and the expected vs.
  actual output shown by the script — this is usually enough to tell the
  user which command's behavior regressed.
* Always surface the console-session record the script prints — that is
  the record of console input/output the user needs to review the test
  session, whether or not everything passed.

## Adding or editing test cases

Edit `test/ui-test-plan.md` directly. Each test case needs:

* A `## Test Case N: <name>` heading.
* A `**Aim:**` line describing what the test case is checking.
* A **Commands** fenced code block: one command per line, ending with
  `bye` so the program exits cleanly.
* An **Expected Output** fenced code block: the literal stdout the session
  should produce. The most reliable way to get this text exactly right is
  to actually run the program with those commands and capture its output,
  rather than typing it out by hand — for example:

  ```bash
  javac -d _temp/test-ui-classes src/main/java/*.java
  printf 'read book\nbye\n' | java -cp _temp/test-ui-classes Duncan
  ```

  then paste the captured output as the Expected Output block.

See `test/ui-test-plan.md` for the exact format and existing examples.
