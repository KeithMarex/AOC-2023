package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day3 {
    public static void main(String[] args) {
        try {
            String userDirectory = new File("").getAbsolutePath();
            Scanner scanner = new Scanner(new File(userDirectory + "/src/Day3/input.txt"));


        } catch (FileNotFoundException e){
            System.out.println("File not found.");;
        }


    }
}
