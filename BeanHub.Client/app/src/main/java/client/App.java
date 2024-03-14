/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import client.util.PostRecipe;
import client.util.ViewPastRecipes;
import client.util.Colors;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import client.util.Auth;
public class App {

    private static final String BASE_URL = System.getenv("BEANHUB_API_URL");
    private static final Auth Authentication =  new Auth(System.getenv("BEANHUB_CLIENT_ID"));
    private static final PostRecipe postRecipe = new PostRecipe();

    private static boolean hasLoggedIn=false;



    public static void main(String[] args) throws IOException, InterruptedException {
        Colors.printColor(Colors.GREEN, "\r\n" + //
        " __          __         _                                       _           \r\n" + //
        " \\ \\        / /        | |                                     | |          \r\n" + //
        "  \\ \\  /\\  / /    ___  | |   ___    ___    _ __ ___     ___    | |_    ___  \r\n" + //
        "   \\ \\/  \\/ /    / _ \\ | |  / __|  / _ \\  | '_ ` _ \\   / _ \\   | __|  / _ \\ \r\n" + //
        "    \\  /\\  /    |  __/ | | | (__  | (_) | | | | | | | |  __/   | |_  | (_) |\r\n" + //
        "     \\/  \\/      \\___| |_|  \\___|  \\___/  |_| |_| |_|  \\___|    \\__|  \\___/ \r\n" + //
        "");
        Colors.printColor(Colors.GREEN, "\r\n" + //
        "  ____                           _    _           _     \r\n" + //
        " |  _ \\                         | |  | |         | |    \r\n" + //
        " | |_) |   ___    __ _   _ __   | |__| |  _   _  | |__  \r\n" + //
        " |  _ <   / _ \\  / _` | | '_ \\  |  __  | | | | | | '_ \\ \r\n" + //
        " | |_) | |  __/ | (_| | | | | | | |  | | | |_| | | |_) |\r\n" + //
        " |____/   \\___|  \\__,_| |_| |_| |_|  |_|  \\__,_| |_.__/ \r\n" + //
        "                                                        \r\n" + //
        "                                                        \r\n" + //
        "");
        Scanner scanner = new Scanner(System.in);

        // Log in here
        List<String> userOptions = Arrays.asList("Log in", "Exit");
        boolean hasLoggedIn = false;
        while (!hasLoggedIn) {
            System.out.println("Select what you want to do:");
            for (int i = 0; i < userOptions.size(); i++) {
                System.out.println((i + 1) + ": " + userOptions.get(i));
            }
            String temp = scanner.nextLine();
            int userOption = 0;
            try {
                userOption = Integer.parseInt(temp);
                if (userOption>userOptions.size()){
                    Integer.parseInt("q");
                }
            } catch (NumberFormatException e) {
                Colors.printColor(Colors.RED, "Select a valid option!!!");
                continue;
            }

            switch (userOption) {
                case 1:
                    //Log in
                    hasLoggedIn = Authentication.loginFlow();
                    break;
                default:
                    scanner.close();
                    System.exit(0);
                    break;
            }
        }

        userOptions = Arrays.asList("View your feed", "View your recipes", "View explore page", "Post a new recipe", "Exit");
        while (true) {

            System.out.println("Select what you want to do:");
            for (int i = 0; i < userOptions.size(); i++) {
                System.out.println((i + 1) + ": " + userOptions.get(i));
            }

            String temp = scanner.nextLine();
            int userOption = 0;
            try {
                userOption = Integer.parseInt(temp);
                if (userOption>userOptions.size()){
                    Integer.parseInt("q");
                }
            } catch (NumberFormatException e) {
                Colors.printColor(Colors.RED, "Select a valid option!!!");
                continue;
            }

            switch (userOption) {
                case 1:
                    // View personalised feed
                    break;
                case 2:
                    // View my recipes
                    ViewPastRecipes oldRecipeView = new ViewPastRecipes(Authentication.getUsername(), Authentication.getAccessToken()); // Make sure this changes after auth is sorted.
                    oldRecipeView.UserInteraction();
                    break;
                case 3:
                    // View my explore page
                    break;
                case 4:
                    // Post a new recipe
                    // System.out.println(postRecipe.post());
                    postRecipe.post(Authentication.getUsername(), Authentication.getAccessToken());
                    break;
                default:
                    // Log out and then kill the program
                    scanner.close();
                    System.exit(0);
                    break;
            }
        }
    }
}
