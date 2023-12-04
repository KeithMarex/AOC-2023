package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day2 {

    static int AMOUNT_OF_POSSIBLE_RED_CUBES = 12;
    static int AMOUNT_OF_POSSIBLE_BLUE_CUBES = 14;
    static int AMOUNT_OF_POSSIBLE_GREEN_CUBES = 13;

    public static void main(String[] args) {
        try {
            String userDirectory = new File("").getAbsolutePath();
            Scanner scanner = new Scanner(new File(userDirectory + "/src/Day2/data.txt"));

            List<Integer> IDLijst = new ArrayList<>();

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();

                Integer ID = Integer.valueOf(line.split(":")[0].split(" ")[1]);
                if (CheckIfValidGame(line.split(": ")[1])){
                    IDLijst.add(ID);
                }
            }

            int sum = IDLijst.stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            System.out.println(sum);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    private static boolean CheckIfValidGame(String nieuweGame){
        String[] opgedeeldeGamePerSubGame = nieuweGame.split(";");

        for (String subGame: opgedeeldeGamePerSubGame) {
            String[] perKleurOpgedeeld = subGame.trim().split(",");

            for (String kleurReeks: perKleurOpgedeeld) {
                String[] aantalPerKleur = kleurReeks.trim().split(" ");
                Integer aantal = Integer.valueOf(aantalPerKleur[0]);
                String kleur = aantalPerKleur[1];

                if (!kleurAantalMogelijk(kleur, aantal)){
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean kleurAantalMogelijk(String kleur, Integer aantal){
        if (kleur.contentEquals("blue")){
            return aantal <= AMOUNT_OF_POSSIBLE_BLUE_CUBES;
        } else if (kleur.contentEquals("red")){
            return aantal <= AMOUNT_OF_POSSIBLE_RED_CUBES;
        } else if (kleur.contentEquals("green")){
            return aantal <= AMOUNT_OF_POSSIBLE_GREEN_CUBES;
        }

        return false;
    }

}
