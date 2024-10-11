import java.io.*;
import java.util.*;

class Users {
    String login;
    int password;

    public Users(String login, int password) {
        this.login = login;
        this.password = password;
    }
}

public class UserSystem {

    public static void readUsers(List<Users> users) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String login = parts[0];
                int password = Integer.parseInt(parts[1]);
                users.add(new Users(login, password));
            }
        } catch (IOException e) {
            System.out.println("Error reading system users");
        }
    }

    public static boolean verifyUser(List<Users> users, String login, int password) {
        for (Users user : users) {
            if (user.login.equals(login) && user.password == password) {
                return true;
            }
        }
        return false;
    }

    public static void userRegistration(List<Users> users, String login, int password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            if (users.size() >= 100) {
                System.out.println("User limit reached, cannot register more users.");
                return;
            }

            users.add(new Users(login, password));
            writer.write(login + " " + password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error opening file for registration");
        }
    }

    public static void main(String[] args) {
        List<Users> users = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello, are you already registered in our system?");
        System.out.println("[1] - Yes\n[2] - No\nEnter: ");
        int op = scanner.nextInt();

        while (op < 1 || op > 2) {
            System.out.println("Invalid option, type again: ");
            op = scanner.nextInt();
        }

        String login;
        int password;
        readUsers(users);

        if (op == 1) {
            System.out.print("Login: ");
            login = scanner.next();

            System.out.print("Password: ");
            password = scanner.nextInt();

            while (!verifyUser(users, login, password)) {
                System.out.println("Incorrect login or password, try again");
                System.out.print("Login: ");
                login = scanner.next();

                System.out.print("Password: ");
                password = scanner.nextInt();
            }
        } else {
            System.out.print("Login Registration: ");
            login = scanner.next();

            System.out.print("Password Registration: ");
            password = scanner.nextInt();

            while (verifyUser(users, login, password)) {
                System.out.println("Login or Password are already in use, please try again");
                System.out.print("Login Registration: ");
                login = scanner.next();

                System.out.print("Password Registration: ");
                password = scanner.nextInt();
            }

            userRegistration(users, login, password);
        }

        scanner.close();
    }
}