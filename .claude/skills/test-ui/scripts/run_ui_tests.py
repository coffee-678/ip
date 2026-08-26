#!/usr/bin/env python3
"""Runs the UI test cases described in test/ui-test-plan.md against Duncan.

For each test case (in order):
  1. Compile the current sources.
  2. Feed the test case's commands to the program's stdin, in one session.
  3. Capture the full stdout produced.
  4. Compare it against the expected output recorded in the plan.
  5. Print a console-session record (commands interleaved with the output
     they produced) so a human can review what actually happened.

If a test case's actual output does not match its expected output, the
actual and expected outputs are reported and the script stops immediately
without running the remaining test cases.

Usage:
    python3 .claude/skills/test-ui/scripts/run_ui_tests.py [repo_root] [plan_file]

Both arguments are optional. Defaults: repo_root="." and
plan_file="<repo_root>/test/ui-test-plan.md".
"""

import glob
import os
import re
import subprocess
import sys

MAIN_CLASS = "Duncan"
DELIMITER_RE = re.compile(r"^_{10,}\s*$")
TEST_CASE_RE = re.compile(
    r"^## Test Case \d+:\s*(?P<name>.+?)\s*$", re.MULTILINE
)
AIM_RE = re.compile(r"\*\*Aim:\*\*\s*(?P<aim>.*?)\n\s*\n", re.DOTALL)
FENCE_RE = re.compile(r"```[^\n]*\n(?P<body>.*?)\n```", re.DOTALL)


class TestCase:
    def __init__(self, index, name, aim, commands, expected):
        self.index = index
        self.name = name
        self.aim = aim
        self.commands = commands
        self.expected = expected


def parse_plan(plan_text):
    """Splits the plan file into TestCase objects, in file order."""
    headers = list(TEST_CASE_RE.finditer(plan_text))
    cases = []
    for i, header in enumerate(headers):
        start = header.end()
        end = headers[i + 1].start() if i + 1 < len(headers) else len(plan_text)
        section = plan_text[start:end]

        aim_match = AIM_RE.search(section)
        if not aim_match:
            raise ValueError(f"Test Case {i + 1} ('{header.group('name')}') is missing an **Aim:** line.")
        aim = " ".join(aim_match.group("aim").split())

        fences = FENCE_RE.findall(section)
        if len(fences) < 2:
            raise ValueError(
                f"Test Case {i + 1} ('{header.group('name')}') must have two "
                "fenced code blocks: Commands, then Expected Output."
            )
        commands_block, expected_block = fences[0], fences[1]
        commands = [line for line in commands_block.split("\n") if line.strip() != ""]
        if not commands:
            raise ValueError(f"Test Case {i + 1} ('{header.group('name')}') has no commands.")
        if commands[-1] != "bye":
            raise ValueError(
                f"Test Case {i + 1} ('{header.group('name')}') must end with a "
                "'bye' command so the program exits cleanly."
            )

        cases.append(TestCase(i + 1, header.group("name"), aim, commands, expected_block))
    if not cases:
        raise ValueError("No test cases found (expected headings like '## Test Case 1: ...').")
    return cases


def compile_sources(repo_root, classes_dir):
    sources = sorted(glob.glob(os.path.join(repo_root, "src", "main", "java", "*.java")))
    if not sources:
        raise RuntimeError(f"No .java files found under {repo_root}/src/main/java")
    os.makedirs(classes_dir, exist_ok=True)
    result = subprocess.run(
        ["javac", "-d", classes_dir, *sources],
        capture_output=True, text=True,
    )
    if result.returncode != 0:
        raise RuntimeError(f"Compilation failed:\n{result.stdout}{result.stderr}")


def run_session(classes_dir, commands):
    stdin_data = "\n".join(commands) + "\n"
    result = subprocess.run(
        ["java", "-cp", classes_dir, MAIN_CLASS],
        input=stdin_data, capture_output=True, text=True, timeout=15,
    )
    return result.stdout, result.stderr


def split_blocks(output):
    """Splits program output into blocks delimited by horizontal-line rows.

    Returns a list where block[0] is the startup banner (printed before any
    command is read) and block[i] for i >= 1 is the output produced by the
    i-th command.
    """
    lines = output.split("\n")
    delim_indices = [i for i, line in enumerate(lines) if DELIMITER_RE.match(line)]
    blocks = []
    for a, b in zip(delim_indices[0::2], delim_indices[1::2]):
        blocks.append("\n".join(lines[a:b + 1]))
    return blocks


def build_session_record(commands, output):
    blocks = split_blocks(output)
    record_lines = []
    if blocks:
        record_lines.append("(program startup)")
        record_lines.append(blocks[0])
        record_lines.append("")
    for cmd, block in zip(commands, blocks[1:]):
        record_lines.append(f"$ {cmd}")
        record_lines.append(block)
        record_lines.append("")
    if len(blocks) - 1 < len(commands):
        record_lines.append(
            f"(warning: only {max(len(blocks) - 1, 0)} output block(s) for "
            f"{len(commands)} command(s) - program may have exited early or crashed)"
        )
    return "\n".join(record_lines).rstrip("\n")


def main():
    repo_root = sys.argv[1] if len(sys.argv) > 1 else "."
    plan_file = sys.argv[2] if len(sys.argv) > 2 else os.path.join(repo_root, "test", "ui-test-plan.md")
    classes_dir = os.path.join(repo_root, "_temp", "test-ui-classes")

    with open(plan_file, "r") as f:
        plan_text = f.read()
    cases = parse_plan(plan_text)

    compile_sources(repo_root, classes_dir)

    passed = 0
    for case in cases:
        print(f"=== Test Case {case.index}: {case.name} ===")
        print(f"Aim: {case.aim}")

        stdout, stderr = run_session(classes_dir, case.commands)

        print("--- Console session ---")
        print(build_session_record(case.commands, stdout))
        print("--- end console session ---")
        if stderr.strip():
            print(f"(stderr output was produced):\n{stderr}")

        if stdout.rstrip("\n") == case.expected.rstrip("\n"):
            print(f"PASS: Test Case {case.index}\n")
            passed += 1
            continue

        print(f"FAIL: Test Case {case.index} ({case.name})")
        print("\n--- Expected output ---")
        print(case.expected)
        print("\n--- Actual output ---")
        print(stdout)
        print(f"\nStopped after {passed} passing test case(s); "
              f"Test Case {case.index} failed.")
        sys.exit(1)

    print(f"All {passed} test case(s) passed.")


if __name__ == "__main__":
    try:
        main()
    except (ValueError, RuntimeError) as e:
        print(f"error: {e}", file=sys.stderr)
        sys.exit(2)
