import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;


public class taskManager {
    public static void main(String[] args) {

        String[][] tabs = readFile("tasks.csv");

        while (true) {
            System.out.println(ConsoleColors.BLUE_BOLD + "Please select an option: ");
            System.out.println(ConsoleColors.RESET + "add");
            System.out.println("remove");
            System.out.println("list");
            System.out.println("exit");
            Scanner scanner = new Scanner(System.in);
            String menu = scanner.nextLine();
            switch (menu) {
                case "add":
                    System.out.println("add");
                    tabs = add(tabs);
                    break;
                case "remove":
                    System.out.println("remove");
                    break;
                case "list":
                    System.out.println("list");
                    list(tabs);
                    break;
                case "exit":
                    System.out.println("exit");
                    break;
                default: {
                    System.out.println("Błędna komenda");
                }
            }
        }


    }

    private static String[][] readFile(String fileName) {
        Path path = Paths.get(fileName);
        //czytam plik
        String[][] tabs = new String[0][3];
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
        return tabs;
    }

    private static String[][] add(String[][] tabs) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String date = scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        String important = scanner.nextLine();
        tabs = Arrays.copyOf(tabs, tabs.length+1);
        tabs[tabs.length-1] = new String[3];
        tabs[tabs.length-1][0] = description;
        tabs[tabs.length-1][1] = date;
        tabs[tabs.length-1][2] = important;

        return tabs;
    }



    private static void list(String[][] tabs) {

        // wyswietlam liste:
        for (int i = 0; i < tabs.length; i++) {
            System.out.println(i + ":  " + tabs[i][0] + "  " + tabs[i][1] + "  " + tabs[i][2]);
        }
    }


}
