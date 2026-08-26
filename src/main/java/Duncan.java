import java.util.Scanner;

public class Duncan {
    public static void main(String[] args) {
        String banner = " /$$$$$$$                                                   \n"
                + "| $$__  $$                                                  \n"
                + "| $$  \\ $$ /$$   /$$ /$$$$$$$   /$$$$$$$  /$$$$$$  /$$$$$$$ \n"
                + "| $$  | $$| $$  | $$| $$__  $$ /$$_____/ |____  $$| $$__  $$\n"
                + "| $$  | $$| $$  | $$| $$  \\ $$| $$        /$$$$$$$| $$  \\ $$\n"
                + "| $$  | $$| $$  | $$| $$  | $$| $$       /$$__  $$| $$  | $$\n"
                + "| $$$$$$$/|  $$$$$$/| $$  | $$|  $$$$$$$|  $$$$$$$| $$  | $$\n"
                + "|_______/  \\______/ |__/  |__/ \\_______/ \\_______/|__/  |__/\n";
        String horizontalLine = "____________________________________________________________";

        System.out.println(horizontalLine);
        System.out.println(banner);
        System.out.println("Hello! I'm Duncan.");
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);
        System.out.println();

        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(horizontalLine);
            if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(input.substring(5).trim()) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (input.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(input.substring(7).trim()) - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskIndex]);
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("added: " + input);
            }
            System.out.println(horizontalLine);
            System.out.println();
            input = scanner.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }
}
