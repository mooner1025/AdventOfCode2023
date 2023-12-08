import java.io.File;
import java.util.*;

public class AdventOfCodeDay2 {

    final static Integer MAX_RED = 12;
    final static Integer MAX_BLUE = 14;
    final static Integer MAX_GREEN = 13;
    final static String RED = "red";
    final static String BLUE = "blue";
    final static String GREEN = "green";

    public static void main(String[] args) {
        try {
            File file = new File("./src/input2.txt");
            Scanner sc = new Scanner(file);

            Integer totalOfValidGameIds = 0;
            Integer totalOfPowerOfMaxSets = 0;

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                //splitting up the left side (game id) and the right side (game data)
                Integer splitIndex = line.indexOf(':');
                String leftSide = line.substring(0, splitIndex);
                String rightSide = line.substring(splitIndex);

                //getting the gameId number from the left side
                String gameId = leftSide.replaceAll("[^0-9]", "");
                System.out.println("gameId " + gameId);

                //getting the rounds of pulls into a list still as strings
                List<String> pullDataAsString = Arrays.stream(rightSide.split(";")).toList();

                //splitting up rounds of pulls to label number of colors in each pull
                List<Map<String, Integer>> pullsMap = new ArrayList<>();
                for (String round : pullDataAsString) {
                    Map<String, Integer> colorToNumberMap = new HashMap<>();
                    colorToNumberMap.put(RED, 0);
                    colorToNumberMap.put(BLUE, 0);
                    colorToNumberMap.put(GREEN, 0);
                    List<String> pulls = Arrays.stream(round.split(",")).toList();
                    for (String pull : pulls) {
                        if (pull.contains(RED)) {
                            colorToNumberMap.put(RED, Integer.parseInt(pull.replaceAll("[^0-9]", "")));
                        } else if (pull.contains(BLUE)) {
                            colorToNumberMap.put(BLUE, Integer.parseInt(pull.replaceAll("[^0-9]", "")));
                        } else if (pull.contains(GREEN)) {
                            colorToNumberMap.put(GREEN, Integer.parseInt(pull.replaceAll("[^0-9]", "")));
                        }
                    }
                    pullsMap.add(colorToNumberMap);
                }

                //verifying all rounds were valid
                Boolean valid = true;
                for (Map<String, Integer> pullMap : pullsMap) {
                    if (pullMap.get(RED) > MAX_RED || pullMap.get(BLUE) > MAX_BLUE || pullMap.get(GREEN) > MAX_GREEN) {
                        valid = false;
                        break;
                    }
                }

                //add gameId to total if valid
                if (valid) {
                    totalOfValidGameIds += Integer.parseInt(gameId);
                }

                //get power of Max set for game
                Integer maxRed = 0;
                Integer maxBlue = 0;
                Integer maxGreen = 0;
                for (Map<String, Integer> pullMap : pullsMap) {
                    if (pullMap.get(RED) > maxRed) {
                        maxRed = pullMap.get(RED);
                    }
                    if (pullMap.get(BLUE) > maxBlue) {
                        maxBlue = pullMap.get(BLUE);
                    }
                    if (pullMap.get(GREEN) > maxGreen) {
                        maxGreen = pullMap.get(GREEN);
                    }
                }
                System.out.println("max red is: " + maxRed + ", " + "max blue is: " + maxBlue + ", " + "max green is: " + maxGreen);
                totalOfPowerOfMaxSets += maxRed*maxBlue*maxGreen;

            }

            System.out.println("total of gameIds " + totalOfValidGameIds);
            System.out.println("total of power of max sets " + totalOfPowerOfMaxSets);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
