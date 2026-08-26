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

* Source under test: `src/main/java/Duncan.java`, `src/main/java/Task.java`.
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

## Test Case 1: Add tasks and list them

**Aim:** Verify that tasks typed in are added and that `list` shows them all
with the not-done `[ ]` status icon, in the order they were added.

**Commands:**
```
read book
return book
buy bread
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
added: read book
____________________________________________________________

____________________________________________________________
added: return book
____________________________________________________________

____________________________________________________________
added: buy bread
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[ ] read book
2.[ ] return book
3.[ ] buy bread
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
read book
return book
buy bread
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
added: read book
____________________________________________________________

____________________________________________________________
added: return book
____________________________________________________________

____________________________________________________________
added: buy bread
____________________________________________________________

____________________________________________________________
Nice! I've marked this task as done:
  [X] return book
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[ ] read book
2.[X] return book
3.[ ] buy bread
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
read book
return book
buy bread
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
added: read book
____________________________________________________________

____________________________________________________________
added: return book
____________________________________________________________

____________________________________________________________
added: buy bread
____________________________________________________________

____________________________________________________________
Nice! I've marked this task as done:
  [X] read book
____________________________________________________________

____________________________________________________________
Nice! I've marked this task as done:
  [X] return book
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[X] read book
2.[X] return book
3.[ ] buy bread
____________________________________________________________

____________________________________________________________
OK, I've marked this task as not done yet:
  [ ] return book
____________________________________________________________

____________________________________________________________
Here are the tasks in your list:
1.[X] read book
2.[ ] return book
3.[ ] buy bread
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
