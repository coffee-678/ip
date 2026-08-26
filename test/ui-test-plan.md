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
  `src/main/java/Event.java`.
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
OOPS!!! I'm sorry, but I don't know what that means :-(
____________________________________________________________

____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
