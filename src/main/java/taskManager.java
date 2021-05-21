import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class taskManager {
    public static void main(String[] args) {

        String[][] tabs = readFile("tasks.csv");
        int quit = 0;
        Scanner scanner = new Scanner(System.in);

        while (quit == 0) {
            System.out.println(ConsoleColors.BLUE_BOLD + "Please select an option: ");
            System.out.println(ConsoleColors.RESET + "add");
            System.out.println("remove");
            System.out.println("list");
            System.out.println("exit");
            String menu = scanner.nextLine().trim();
            switch (menu) {
                case "add":
                    //System.out.println("add");
                    tabs = add(tabs);
                    break;
                case "remove":
                    //System.out.println("remove");
                    tabs = remove(tabs);
                    break;
                case "list":
                    //System.out.println("list");
                    list(tabs);
                    break;
                case "exit":
                    //System.out.println("exit");
                    quit = quit(tabs);
                    break;
                default: {
                    System.out.println("Błędna komenda");
                }
            }
        }


    }

    private static int quit(String[][] tabs) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < tabs.length; i++) {
            list.add(tabs[i][0] + ", " + tabs[i][1] + ", " + tabs[i][2]);
        }
        Path path = Paths.get("tasks.csv");

        try {
            Files.write(path, list);
            System.out.println(ConsoleColors.RED + "Bye, Bye.");
        } catch (IOException e) {
            System.out.println("Problem z zapisem do pliku! " + e.getMessage());
        }

        //tasks.csv
        return 1;
    }

    private static String[][] remove(String[][] tabs) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        int index = 0;
        while (true) {
            try {
                index = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
                index = -1;
            }
            if(index < 0 || index > tabs.length){
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

        //czytam plik
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
                System.out.println("Problem z plikiem " + e.getMessage());
            }
        } else {
            System.out.println(ConsoleColors.RED + "Plik nie istnieje/brak zadań do wykonania!" + ConsoleColors.RESET);
        }

        return tabs;
    }

    private static String[][] add(String[][] tabs) {
        Scanner scanner = new Scanner(System.in);
        //czytam opis
        System.out.println("Please add task description");
        String description = scanner.nextLine().trim();

        //czytam date
        System.out.println("Please add task due date");
        String date;

        date = scanner.nextLine();

        // czytam true / false
        System.out.println("Is your task is important: true/false");
        String important;
        while (true) {
            important = scanner.nextLine().trim();
            if (important.equals("true") || important.equals("false")) {
                break;
            } else {
                System.out.println("true/false");
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
        // wyswietlam liste:
        if (tabs.length == 0) {
            System.out.println(ConsoleColors.RED + "Brak zadań do wykonania!" + ConsoleColors.RESET);
        } else {
            for (int i = 0; i < tabs.length; i++) {
                System.out.println(i + ":  " + tabs[i][0] + "  " + tabs[i][1] + "  " + tabs[i][2]);
            }
        }
    }

}
