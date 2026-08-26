# UI Test Plan

This file drives the `test-ui` skill. It lists UI test cases for the
`Duncan` program; each one is run as a single program session (state such
as the task list persists across the commands within one test case, but
each test case starts a fresh program instance).

## How to run

Invoke the `test-ui` skill, or run the harness directly from the repo root:

```
python3 .claude/skills/test-ui/scripts/run_ui_tests.py . test/ui-test-plan.md
```

## Format notes (for anyone editing this file)

* Source under test: `src/main/java/Duncan.java`, `src/main/java/Task.java`,
  `src/main/java/Todo.java`, `src/main/java/Deadline.java`,
  `src/main/java/Event.java`, `src/main/java/DukeException.java`.
* Tasks are stored in an `ArrayList<Task>`.
* Each test case has an **Aim**, a **Commands** block (one command per
  line, in the order they are typed), and an **Expected Output** block.
* **Commands** must end with `bye` so the program exits cleanly.
* **Expected Output** is the literal, byte-for-byte stdout produced by the
  program for the whole session, from the startup banner through the exit
  message.
* A line of 10 or more underscores is the program's block separator; the
  harness uses it to line up each command with the block of output it
  produced when displaying the console-session record.

---

## Test Case 1: Add todos and list them

**Aim:** Verify that `todo <description>` adds a todo task and that `list`
shows them all with the `[T]` type icon and not-done `[ ]` status icon, in
the order they were added.

**Commands:**
```
todo read book
todo return book
todo buy bread
list
bye
```

**Expected Output:**
```
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
Got it. I've added this task:
  [T][ ] buy bread
Now you have 3 tasks in the list.
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[T][ ] return book
3.[T][ ] buy bread
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 2: Mark a task as done

**Aim:** Verify that `mark <index>` marks the correct task done, prints the
confirmation message, and that `list` afterwards reflects the `[X]` status.

**Commands:**
```
todo read book
todo return book
todo buy bread
mark 2
list
bye
```

**Expected Output:**
```
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
Got it. I've added this task:
  [T][ ] buy bread
Now you have 3 tasks in the list.
____________________________________________________________

____________________________________________________________
Nice! I've marked this task as done:
  [T][X] return book
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[T][X] return book
3.[T][ ] buy bread
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 3: Unmark a task (reverse done status)

**Aim:** Verify that `unmark <index>` reverses a task's done status back to
not-done, prints the confirmation message, and that `list` afterwards
reflects the `[ ]` status, leaving other tasks unaffected.

**Commands:**
```
todo read book
todo return book
todo buy bread
mark 1
mark 2
list
unmark 2
list
bye
```

**Expected Output:**
```
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
Got it. I've added this task:
  [T][ ] buy bread
Now you have 3 tasks in the list.
____________________________________________________________

____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________

____________________________________________________________
Nice! I've marked this task as done:
  [T][X] return book
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[T][X] return book
3.[T][ ] buy bread
____________________________________________________________

____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] return book
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[T][ ] return book
3.[T][ ] buy bread
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 4: Exit immediately

**Aim:** Verify the startup banner and the farewell message when the user
exits without entering any other command.

**Commands:**
```
bye
```

**Expected Output:**
```
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

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 5: Add a deadline

**Aim:** Verify that `deadline <description> /by <date/time>` adds a
deadline task, stores the date/time as-is (unparsed), and displays it with
the `[D]` type icon and `(by: ...)` suffix.

**Commands:**
```
deadline return book /by Sunday
list
bye
```

**Expected Output:**
```
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

____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[D][ ] return book (by: Sunday)
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 6: Add an event

**Aim:** Verify that `event <description> /from <start> /to <end>` adds an
event task, stores both date/times as-is (unparsed), and displays it with
the `[E]` type icon and `(from: ... to: ...)` suffix.

**Commands:**
```
event project meeting /from Mon 2pm /to 4pm
list
bye
```

**Expected Output:**
```
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

____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 7: Unrecognized command

**Aim:** Verify that a command that isn't `list`, `mark`, `unmark`, `todo`,
`deadline`, or `event` produces an error message instead of crashing or
silently doing nothing.

**Commands:**
```
blah
bye
```

**Expected Output:**
```
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

____________________________________________________________
HEY! idk what's that supposed to be
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 8: Deadline missing /by

**Aim:** Verify that a `deadline` command without a `/by` part prints a
friendly error message instead of crashing, and does not add a task.

**Commands:**
```
deadline return book
bye
```

**Expected Output:**
```
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

____________________________________________________________
HEY! deadlines must have /by <date/time>
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 9: Event missing /from or /to

**Aim:** Verify that an `event` command missing its `/to` part prints a
friendly error message instead of crashing, and does not add a task.

**Commands:**
```
event project meeting /from Mon 2pm
bye
```

**Expected Output:**
```
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

____________________________________________________________
HEY! events must use /from and /to <date/time>
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 10: Missing description on todo, deadline, and event

**Aim:** Verify that `todo`, `deadline`, and `event` commands with no
description each print a friendly error message instead of crashing, and
do not add a task.

**Commands:**
```
todo
deadline /by Sunday
event /from Mon /to Tue
bye
```

**Expected Output:**
```
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

____________________________________________________________
HEY! the description can't be left empty
____________________________________________________________

____________________________________________________________
HEY! the description can't be left empty
____________________________________________________________

____________________________________________________________
HEY! the description can't be left empty
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 11: Missing, invalid, or out-of-range task number on mark/unmark

**Aim:** Verify that `mark`/`unmark` with a missing task number, a
non-numeric task number, or an out-of-range task number each print a
friendly error message instead of crashing, and leave existing tasks
unaffected.

**Commands:**
```
todo read book
mark
mark abc
mark 100
unmark
unmark xyz
unmark 100
bye
```

**Expected Output:**
```
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

____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 12: Boundary and negative task numbers on mark/unmark

**Aim:** Verify that `mark 0` and `mark -1` (a boundary and a negative task
number) are rejected without corrupting the list, and that valid `mark`
and `unmark` on the resulting list still work correctly afterwards.

**Commands:**
```
todo task one
todo task two
mark 0
mark -1
list
mark 1
list
unmark 1
list
bye
```

**Expected Output:**
```
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

____________________________________________________________
Got it. I've added this task:
  [T][ ] task one
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
Got it. I've added this task:
  [T][ ] task two
Now you have 2 tasks in the list.
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] task one
2.[T][ ] task two
____________________________________________________________

____________________________________________________________
Nice! I've marked this task as done:
  [T][X] task one
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][X] task one
2.[T][ ] task two
____________________________________________________________

____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] task one
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] task one
2.[T][ ] task two
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 13: Invalid commands interleaved with valid adds don't corrupt numbering

**Aim:** Verify that failed `blah` and `deadline` (missing `/by`) commands
in between successful `todo` adds do not create phantom tasks or disturb
the numbering of tasks that were actually added.

**Commands:**
```
todo first task
blah
todo second task
deadline no by here
todo third task
list
bye
```

**Expected Output:**
```
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

____________________________________________________________
Got it. I've added this task:
  [T][ ] first task
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
HEY! idk what's that supposed to be
____________________________________________________________

____________________________________________________________
Got it. I've added this task:
  [T][ ] second task
Now you have 2 tasks in the list.
____________________________________________________________

____________________________________________________________
HEY! deadlines must have /by <date/time>
____________________________________________________________

____________________________________________________________
Got it. I've added this task:
  [T][ ] third task
Now you have 3 tasks in the list.
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] first task
2.[T][ ] second task
3.[T][ ] third task
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 14: Whitespace-only descriptions are treated as empty

**Aim:** Verify that `todo`, `deadline`, and `event` commands whose
description is only whitespace are rejected the same way as a genuinely
empty description, and that `list` on an empty task list doesn't crash.

**Commands:**
```
todo    
deadline    /by Sunday
event    /from Mon /to Tue
list
bye
```

**Expected Output:**
```
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

____________________________________________________________
HEY! the description can't be left empty
____________________________________________________________

____________________________________________________________
HEY! the description can't be left empty
____________________________________________________________

____________________________________________________________
HEY! the description can't be left empty
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 15: Alternating valid and invalid mark/unmark on a growing list

**Aim:** Verify that repeatedly alternating invalid (out-of-range) and
valid `mark`/`unmark` calls, as the task list grows, never corrupts the
status of tasks that were validly marked or unmarked.

**Commands:**
```
todo alpha
mark 5
todo beta
mark 5
mark 2
list
unmark 5
unmark 2
list
bye
```

**Expected Output:**
```
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

____________________________________________________________
Got it. I've added this task:
  [T][ ] alpha
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
Got it. I've added this task:
  [T][ ] beta
Now you have 2 tasks in the list.
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
Nice! I've marked this task as done:
  [T][X] beta
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] alpha
2.[T][X] beta
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] beta
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] alpha
2.[T][ ] beta
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 16: Delete a task

**Aim:** Verify that `delete <index>` removes the correct task, prints the
confirmation message with the removed task's details, and that `list`
afterwards shows the remaining tasks renumbered contiguously.

**Commands:**
```
todo read book
deadline return book /by Sunday
todo borrow book
list
delete 2
list
bye
```

**Expected Output:**
```
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

____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________

____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 3 tasks in the list.
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
3.[T][ ] borrow book
____________________________________________________________

____________________________________________________________
Noted. I've removed this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[T][ ] borrow book
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

---

## Test Case 17: Missing, invalid, or out-of-range task number on delete

**Aim:** Verify that `delete` with a missing, non-numeric, or out-of-range
task number reuses the same error message as `mark`/`unmark` instead of
crashing, leaves the list untouched, and that a subsequent valid `delete`
still works (including deleting the last remaining task down to zero).

**Commands:**
```
todo read book
delete
delete abc
delete 100
delete 0
list
delete 1
list
bye
```

**Expected Output:**
```
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

____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
HEY! this task number is bad
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
____________________________________________________________

____________________________________________________________
Noted. I've removed this task:
  [T][ ] read book
Now you have 0 tasks in the list.
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
