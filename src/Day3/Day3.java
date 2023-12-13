package Day3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Coordinates {
    public int x;
    public int y;

    Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinates copyOf(){
        return new Coordinates(this.x,this.y);
    }

}

public class Day3 {

    static List<char[]> loadedFile;

     static {
        String userDirectory = new File("").getAbsolutePath();

         try {
             loadedFile = Files.lines(Paths.get(userDirectory + "/src/Day3/input.txt"))
                     .map((String line) -> line.toCharArray())
                     .collect(Collectors.toList());
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }

    public static void main(String[] args) {
        // Create Day3 Object
        Day3 day3 = new Day3();

        List<Integer> integerList = new ArrayList<>();

        // For loop on loaded file
        for (int verticalIndex = 0; verticalIndex < loadedFile.size(); verticalIndex++){

            // Filter for every line in the txt document
            integerList.add(day3.filterLine(loadedFile.get(verticalIndex), verticalIndex));
        }

        Integer finalSum = integerList.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println(finalSum);
    }

    // Coordinates do not have to keep track of the first index, since there is no existing in the txt file
    private Coordinates[] retrieveCoordinatesInAreaOfSymbol(int horizontalIndex, int verticalIndex){
        return new Coordinates[]{
            new Coordinates(horizontalIndex - 1, verticalIndex - 1),new Coordinates(horizontalIndex, verticalIndex - 1), new Coordinates(horizontalIndex + 1, verticalIndex - 1),
            new Coordinates(horizontalIndex - 1, verticalIndex), new Coordinates(horizontalIndex, verticalIndex), new Coordinates(horizontalIndex + 1, verticalIndex),
            new Coordinates(horizontalIndex - 1, verticalIndex + 1), new Coordinates(horizontalIndex, verticalIndex + 1), new Coordinates(horizontalIndex + 1, verticalIndex + 1)
        };
    }

    private Integer retrieveWholeNumber(Coordinates coordinates){
         List<Integer> list = new ArrayList<>();

         Coordinates copyOfCoordinates = coordinates.copyOf();

         isDigit(coordinates).ifPresent((Integer number) -> list.add(number));

         if (copyOfCoordinates.x != loadedFile.get(0).length - 1){
             copyOfCoordinates.x += 1;
             // Going right
             while (isDigit(copyOfCoordinates).isPresent()){
                 // Add found number
                 list.add(retrieveNumberInFile(copyOfCoordinates));

                 if (copyOfCoordinates.x == loadedFile.get(0).length - 1){
                     break;
                 }

                 copyOfCoordinates.x += 1;
             }
         }

         copyOfCoordinates = coordinates.copyOf();

         if (copyOfCoordinates.x != 0){
             copyOfCoordinates.x -= 1;
             // Going left
             while (isDigit(copyOfCoordinates).isPresent()){
                 list.add(0, retrieveNumberInFile(copyOfCoordinates));

                 if (copyOfCoordinates.x == 0){
                     break;
                 }
                 copyOfCoordinates.x -= 1;
             }
         }

        String combinedNumber = String.join("", list.stream()
                .map((Integer number) -> Integer.toString(number)).collect(Collectors.toList()));;

         return Integer.parseInt(combinedNumber);
    }

    private Integer retrieveNumberInFile(Coordinates coordinates){
         return Character.getNumericValue(loadedFile.get(coordinates.y)[coordinates.x]);
    }

    private List<Integer> retrieveNumbersByCoordinates(Coordinates[] arrayOfCoordinates){
        List<Integer> listOfAdjacentNumbers = new ArrayList<>();
         // Retrieve numbers on location + adjacent to that to retrieve the whole number
        for (Coordinates coordinates: arrayOfCoordinates){
            // For char on coordinate, check if digit
            if (isDigit(coordinates).isPresent()){
                // Retrieve the whole number
                Integer wholeNumber = retrieveWholeNumber(coordinates);
                listOfAdjacentNumbers.add(wholeNumber);
            }
        }

        return listOfAdjacentNumbers;
    }

    /*
    Function which checks if the char at the given coordinate is a valid digit
     */
    private Optional<Integer> isDigit(Coordinates coordinates){
         char item = loadedFile.get(coordinates.y)[coordinates.x];

         if (Character.isDigit(item)){
             return Optional.of(Character.getNumericValue(item));
         }

         return Optional.empty();
    }

    private static boolean isSymbol(char potentialSymbol){
        return !(potentialSymbol == '.' || Character.isDigit(potentialSymbol));
    }

    /*
    Function which returns the sum of all the gathered numbers
     */
    private int filterLine(char[] line, int verticalIndex){
        List<Integer> listOfNumbers = new ArrayList<>();

        // Loops over every newline in the txt file
        for (int horizontalIndex = 0; horizontalIndex < line.length; horizontalIndex++){

            // Checks if the character is a valid symbol
            if (isSymbol(line[horizontalIndex])){

                // Retrieves the nearby coordinates of the symbol => Good
                Coordinates[] coordinates = retrieveCoordinatesInAreaOfSymbol(horizontalIndex, verticalIndex);

                // Retrieve the numbers on the given coordinates
                List<Integer> adjacentNumbers = retrieveNumbersByCoordinates(coordinates);

                // Sum of all the numbers
                Integer sumOfNumbers = adjacentNumbers.stream()
                        .mapToInt(Integer::intValue)
                        .sum();

                listOfNumbers.add(sumOfNumbers);
            }
        }

        Integer finalSum = listOfNumbers.stream()
                .mapToInt(Integer::intValue)
                .sum();

        return finalSum;
    }
}
