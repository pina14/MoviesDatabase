package Model;

import java.util.Scanner;

/**
 * Created by hugo on 14/04/2017.
 */
public class UserInteraction {
    Scanner in = new Scanner(System.in);

    public String question(String question) {
        System.out.println(question);
        return in.nextLine();
    }

    public void show(String message) {
        System.out.println(message);
    }
}
