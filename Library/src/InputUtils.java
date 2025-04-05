import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class InputUtils {


    /**
     * Prompts the user for input and validates it against a list of valid options.
     * Continues prompting the user until a valid option is entered or the user decides to quit.
     *
     * @param prompt the message to display to the user, asking them to provide input
     * @param validOptions a list of valid input options that the user can select from
     * @param scanner a Scanner object to read the user's input from the console
     * @return the user's input if it is valid; otherwise, returns null if the user chooses to quit
     */
    public static String getValidInput(String prompt, List<String> validOptions, Scanner scanner) {
        String input;
        while (true) {
            System.out.println(prompt + " (or type 'q' to go back):");
            input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                return null; // Indicate that the user wants to quit
            }

            //Another use of predicate.test()
            Predicate<String> checkValidOptionsContains = validOptions::contains;

            if (checkValidOptionsContains.test(input.toLowerCase())) {
                return input; // Return valid input
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
