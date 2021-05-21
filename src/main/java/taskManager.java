import org.apache.commons.lang3.ArrayUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;

public class taskManager {
    public static void main(String[] args) {

        String filename = "tasks.csv";
        String[][] tabs = readFile(filename);
        Scanner scanner = new Scanner(System.in);

        while(true) {
            menuPrint();
            String menu = scanner.nextLine().trim();
            switch (menu) {
                case "add" -> tabs = add(tabs);
                case "remove" -> tabs = remove(tabs);
                case "list" -> list(tabs);
                case "exit" -> {
                    scanner.close();
                    quit(tabs, filename);
                }
                default -> System.out.println(ConsoleColors.RED_BOLD + "Wrong command" + ConsoleColors.RESET);
            }
        }
    }

    private static void menuPrint(){
        System.out.println();
        System.out.println(ConsoleColors.BLUE_BOLD + "Please select an option: " + ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }

    private static void quit(String[][] tabs, String filename) {
        List<String> list = new ArrayList<>();

        for (String[] tab : tabs) {
            list.add(tab[0] + ", " + tab[1] + ", " + tab[2]);
        }
        Path path = Paths.get(filename);

        try {
            Files.write(path, list);
            System.out.println(ConsoleColors.RED + "Bye, Bye.");
        } catch (IOException e) {
            System.out.println("Problem with writing to file! " + e.getMessage());
        }
        System.exit(1);
    }

    private static String[][] remove(String[][] tabs) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        int index;
        while (true) {
            try {
                index = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
                index = -1;
            }
            if(index < 0 || index > tabs.length - 1){
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            } else {
                break;
            }
        }
        tabs = ArrayUtils.remove(tabs, index);

        return tabs;
    }

    private static String[][] readFile(String fileName) {
        Path path = Paths.get(fileName);

        String[][] tabs = new String[0][3];

        if (Files.exists(path)) {
            try {
                for (String line: Files.readAllLines(path)) {
                    tabs = Arrays.copyOf(tabs, tabs.length+1);
                    tabs[tabs.length-1] = new String[3];
                    String[] parts = line.split(", ");
                    tabs[tabs.length-1][0] = parts[0];
                    tabs[tabs.length-1][1] = parts[1];
                    tabs[tabs.length-1][2] = parts[2];
                }
            } catch (IOException e) {
                System.out.println("Access error" + e.getMessage());
            }
        } else {
            System.out.println(ConsoleColors.RED + "File no exist" + ConsoleColors.RESET);
    }

        return tabs;
    }

    private static String[][] add(String[][] tabs) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine().trim();

        System.out.println("Please add task due date");
        String date;
        while (true) {
            date = scanner.nextLine();
            try {
                LocalDate.parse(date);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Enter YYYY-MM-DD");
            }
        }

        System.out.println("Is your task is important: true/false");
        String important;
        while (true) {
            important = scanner.nextLine().trim();
            if (important.equals("true") || important.equals("false")) {
                break;
            } else {
                System.out.println("Invalid format. Enter true or false");
            }
        }

        tabs = Arrays.copyOf(tabs, tabs.length+1);
        tabs[tabs.length-1] = new String[3];
        tabs[tabs.length-1][0] = description;
        tabs[tabs.length-1][1] = date;
        tabs[tabs.length-1][2] = important;

        return tabs;
    }

    private static void list(String[][] tabs) {
        if (tabs.length == 0) {
            System.out.println(ConsoleColors.RED + "No task!" + ConsoleColors.RESET);
        } else {
            for (int i = 0; i < tabs.length; i++) {
                System.out.println(i + ":  " + tabs[i][0] + "  " + tabs[i][1] + "  " + tabs[i][2]);
            }
        }
    }

}
