package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day1 {

    public static void main(String[] args) {
        try {
            String userDirectory = new File("").getAbsolutePath();
            Scanner scanner = new Scanner(new File(userDirectory + "/src/Day1/input.txt"));

            List<Integer> listOfNumbers = new ArrayList<>();

            while (scanner.hasNextLine()){

               String line = scanner.nextLine();
               List<Integer> arrayOfNumbers = line.chars()
                       .mapToObj(c -> (char) c)
                       .filter(Character::isDigit)
                       .mapToInt(Character::getNumericValue)
                       .boxed().toList();

                listOfNumbers.add(Integer.valueOf(String.valueOf(arrayOfNumbers.get(0)) + (arrayOfNumbers.size() == 1 ? arrayOfNumbers.get(0) : arrayOfNumbers.get(arrayOfNumbers.size() - 1))));
            }

            int sum = listOfNumbers.stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            System.out.println(sum);

        } catch (FileNotFoundException e){
            System.out.println("File has not been found.");
        }
    }

}
