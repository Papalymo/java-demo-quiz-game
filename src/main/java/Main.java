// import necessary libraries


import com.kenzie.app.data.CluesDTO;
import com.kenzie.app.errorHandling.CustomEmptyStringException;
import com.kenzie.app.http.CustomHttpClient;

import java.util.*;


public class Main {
    //Write main execution code here
    public final static String URL = "https://jservice.kenzie.academy/api/clues";
    static int score = 0;
    public static final String plainText = "\033[0;0m";
    public static final String bold = "\033[0;1m";
    public static final String yellow = "\u001B[33m";
    public static final String white = "\u001B[37m";
    public static final String cyan = "\u001B[36m";
    public static final String red = "\u001B[31m";

    public static final String reset = "\u001B[0m";
    public static final String black = "\u001B[30m";
    public static final String green = "\u001B[32m";

    public static void main(String[] args) {

        boolean needInput = true;

        while (needInput)
            try {
                String response = CustomHttpClient.sendGET(URL);
                List<CluesDTO> cluesList = CustomHttpClient.getCluesList(response);

                //random of 100 questions was giving the same questions too often, so collection shuffle is the better solution
                //i would like to use https://jservice.kenzie.academy/api/clues/:id to get a random out of all 355237 clues but
                // having trouble getting the id links
                List<Integer> randomNumberList = new ArrayList<>();
                for (int j = 1; j < 101; j++ ) {
                    randomNumberList.add( j );
                }
                Collections.shuffle( randomNumberList );
                int index = 0;

                for (CluesDTO clues : cluesList) {
                    for (int i = 0; i < 10; i++) {
                        try {

                            String category = clues.getClues().get(randomNumberList.get(index)).getCategory().getTitle();
                            String question = clues.getClues().get(randomNumberList.get(index)).getQuestion();
                            String answer = clues.getClues().get(randomNumberList.get(index)).getAnswer();

                            System.out.println(bold + yellow + "Category: " + reset + category);
                            System.out.println(bold + yellow + "Question: " + reset + question);
                            // uncomment the line below to help with testing purposes
                            // System.out.println("**  Answer for testing purposes: **   " + answer);
                            System.out.println(bold + yellow + "Your answer: " + plainText + white);

                            Scanner scanner = new Scanner(System.in);
                            String answerInput = scanner.nextLine();
                            checkForEmptyString(answerInput); //empty string/null check; retry question if occurs

                            if (answerInput.trim().equalsIgnoreCase(answer)) {
                                System.out.print(bold + cyan + "Correct! " + reset);
                                positiveMessage();
                                score++;
                            } else {
                                System.out.println(bold + red + "Incorrect." + reset + " Correct Answer is: " + answer);
                            }
                            index++;
                            System.out.println(bold + yellow + "Current Score: " + reset + score + "\n");

                        } catch (CustomEmptyStringException empty) {
                            i--;
                            System.out.println(red + "Error:" + reset + " Empty string entered. Please try again.");
                            System.out.println(bold + yellow + "Current Score: " + reset + score + "\n");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    needInput = false;
                    System.out.println(bold + green + "*************************" + reset);
                    System.out.println(bold + green + "*  Your Final Score: " + score + " *" + reset);
                    System.out.println(bold + green + "*************************" + reset);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    public static void checkForEmptyString(String input) {

        if (input.equals("") || input.equals(" ")) {
            throw new CustomEmptyStringException("Invalid input: Empty string entered.\n");
        }
    }

    public static void positiveMessage() {

        Random r = new Random();
        int message = r.nextInt(5) + 1;
        switch (message) {
            case 1:
                System.out.println("You're amazing!");
                break;
            case 2:
                System.out.println("Well done!");
                break;
            case 3:
                System.out.println("No pressure, no diamonds. Keep going!");
                break;
            case 4:
                System.out.println("Great job!!");
                break;
        }
    }
}





