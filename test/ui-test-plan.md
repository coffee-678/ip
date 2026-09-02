# Duncan text UI test plan

This file is the single source of truth for the text UI tests. It is read by
`.claude/skills/test-ui/scripts/run-ui-tests.py`, which is what the `test-ui`
skill runs. Editing this file is how you add, change, or remove a test.

## How a test case is run

1. The program's sources are compiled once, into a temporary directory.
2. Every test case gets its **own fresh, empty working directory**, and the
   program is started there. This matters because the program saves its task
   list to `data/duncan.txt` relative to the working directory — without a
   fresh directory, one test case would see the tasks left behind by an
   earlier one, and results would depend on the order tests ran in.
3. The case's **Input** lines are fed to the program's standard input, one per
   line. The **Exit command** below is appended automatically unless the input
   already ends with it, so test cases do not need to repeat it.
4. The program's standard output is captured. The **Greeting** and
   **Farewell** blocks below are checked and then removed, because every
   session prints them and repeating them in each case would bury the part of
   the output the case is actually about.
5. What is left is compared with the case's **Expected output**.

Comparison ignores trailing whitespace on a line and blank lines at the very
start and end of a block. Blank lines *inside* a block are significant: the
program prints one after each command's response, so a case with several
commands must show those blank lines between the response blocks.

Testing stops at the first failing case, and reports that case's expected and
actual output.

## Setup

- **Source directory:** `src/main/java`
- **Main class:** `duncan.Duncan`
- **Exit command:** `bye`

### Greeting

Printed once at the start of every session, before any command is handled.

```text
____________________________________________________________
 /$$$$$$$                                                   
| $$__  $$                                                  
| $$  \ $$ /$$   /$$ /$$$$$$$   /$$$$$$$  /$$$$$$  /$$$$$$$ 
| $$  | $$| $$  | $$| $$__  $$ /$$_____/ |____  $$| $$__  $$
| $$  | $$| $$  | $$| $$  \ $$| $$        /$$$$$$$| $$  \ $$
| $$  | $$| $$  | $$| $$  | $$| $$       /$$__  $$| $$  | $$
| $$$$$$$/|  $$$$$$/| $$  | $$|  $$$$$$$|  $$$$$$$| $$  | $$
|_______/  \______/ |__/  |__/ \_______/ \_______/|__/  |__/

Hello! I'm Duncan.
What can I do for you?
____________________________________________________________
```

### Farewell

Printed once at the end of every session, after the exit command.

```text
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test cases

Each case is a `### <id>: <title>` heading followed by an **Aim**, an
**Input** block, and an **Expected output** block.

### TC01: Add a todo

**Aim:** Check that a todo is added, echoed back with the `[T][ ]` prefix, and
counted.

**Input:**

```text
todo read book
```

**Expected output:**

```text
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
```

### TC02: Add a deadline

**Aim:** Check that a deadline is added and that the date typed in as
`yyyy-mm-dd` is displayed in the friendlier `MMM d yyyy` format.

**Input:**

```text
deadline return book /by 2019-12-02
```

**Expected output:**

```text
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Dec 2 2019)
Now you have 1 tasks in the list.
____________________________________________________________
```

### TC03: Add an event

**Aim:** Check that an event keeps both its start and end dates and displays
each in the friendlier format.

**Input:**

```text
event project fair /from 2019-12-01 /to 2019-12-02
```

**Expected output:**

```text
____________________________________________________________
Got it. I've added this task:
  [E][ ] project fair (from: Dec 1 2019 to: Dec 2 2019)
Now you have 1 tasks in the list.
____________________________________________________________
```

### TC04: List all three task types

**Aim:** Check that `list` numbers the tasks from 1 in the order they were
added, and shows each task type in its own format.

**Input:**

```text
todo read book
deadline return book /by 2019-12-02
event project fair /from 2019-12-01 /to 2019-12-02
list
```

**Expected output:**

```text
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Dec 2 2019)
Now you have 2 tasks in the list.
____________________________________________________________

____________________________________________________________
Got it. I've added this task:
  [E][ ] project fair (from: Dec 1 2019 to: Dec 2 2019)
Now you have 3 tasks in the list.
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Dec 2 2019)
3.[E][ ] project fair (from: Dec 1 2019 to: Dec 2 2019)
____________________________________________________________
```

### TC05: List when there are no tasks

**Aim:** Check that `list` on an empty list prints only the heading, with no
task lines under it.

**Input:**

```text
list
```

**Expected output:**

```text
____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
```

### TC06: Mark a task, then unmark it

**Aim:** Check that `mark` sets the done icon to `X` and `unmark` clears it
again, and that each prints the task it changed.

**Input:**

```text
todo read book
mark 1
unmark 1
```

**Expected output:**

```text
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________

____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] read book
____________________________________________________________
```

### TC07: Delete a task

**Aim:** Check that `delete` removes the task at the given number, reports the
removed task, and that the tasks after it are renumbered in the next `list`.

**Input:**

```text
todo read book
todo return book
delete 1
list
```

**Expected output:**

```text
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
Got it. I've added this task:
  [T][ ] return book
Now you have 2 tasks in the list.
____________________________________________________________

____________________________________________________________
Noted. I've removed this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] return book
____________________________________________________________
```

### TC08: Unrecognised command

**Aim:** Check that a command word the program does not know is rejected with
an error message rather than crashing the session.

**Input:**

```text
blah
```

**Expected output:**

```text
____________________________________________________________
HEY! idk what's that supposed to be
____________________________________________________________
```

### TC09: Todo with an empty description

**Aim:** Check that a todo with nothing after the command word is rejected,
and that the session carries on afterwards.

**Input:**

```text
todo
list
```

**Expected output:**

```text
____________________________________________________________
HEY! the description can't be left empty
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
```

### TC10: Deadline with a date in the wrong format

**Aim:** Check that a date that is not `yyyy-mm-dd` is rejected with the
format reminder, and that the task is not added.

**Input:**

```text
deadline return book /by next Monday
list
```

**Expected output:**

```text
____________________________________________________________
HEY! dates must be in yyyy-mm-dd format
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
```

### TC11: Deadline with no /by

**Aim:** Check that a deadline missing its `/by` part is rejected with the
message that names the missing part.

**Input:**

```text
deadline return book
```

**Expected output:**

```text
____________________________________________________________
HEY! deadlines must have /by <date/time>
____________________________________________________________
```

### TC12: Mark a task number that does not exist

**Aim:** Check that a task number outside the list is rejected rather than
throwing an index error.

**Input:**

```text
todo read book
mark 5
```

**Expected output:**

```text
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________
```

### TC13: Delete with a task number that is not a number

**Aim:** Check that a non-numeric task number is rejected with the same
message rather than crashing.

**Input:**

```text
todo read book
delete two
```

**Expected output:**

```text
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________
```

## Not covered by this plan

- **Saving and loading between runs.** Every case runs in its own empty
  working directory, so it always starts from an empty task list. Testing that
  tasks survive a restart needs a case that runs the program twice in the same
  directory, which this plan's format does not express.
- **Anything that is not typed input and printed output**, such as the layout
  of the save file. Those belong in unit tests rather than a UI test.
