import java.io.File;
import java.util.*;

public class AdventOfCodeDay4 {
    public static void main(String[] args) {
        try {
            File file = new File("./src/input4.txt");
            Scanner sc = new Scanner(file);

            int totalPart1 = 0;
            long totalPart2 = 0;
            long[] cardCounts = new long[206];
            Arrays.fill(cardCounts, 1);
            int rowCount = -1;
            while (sc.hasNextLine()) {
                rowCount ++;
                String line = sc.nextLine();

                List<String> winningNumbers = Arrays.stream(line.substring(line.indexOf(':') + 1).substring(0, line.substring(line.indexOf(':') + 1).indexOf('|')).split(" ")).toList();
                List<String> myNumbers = Arrays.stream(line.substring(line.indexOf(':') + 1).substring(line.substring(line.indexOf(':') + 1).indexOf('|') + 1).split(" ")).toList();

                int winCount = 0;
                int winCount2 = 0;
                for (String number : myNumbers) {
                    if (number.isEmpty()) {
                        continue;
                    }
                    if (winningNumbers.contains(number)) {
                        winCount2++;
                        if (winCount == 0) {
                            winCount = 1;
                        } else {
                            winCount = winCount*2;
                        }
                    }
                }
                totalPart1 += winCount;
                for (int i = 1; i < winCount2 + 1; i++) {
                    if (rowCount + i <= cardCounts.length) {
                        cardCounts[rowCount + i] = cardCounts[rowCount + i] + cardCounts[rowCount];
                    } else {
                        break;
                    }
                }
            }

            for (long count : cardCounts) {
                totalPart2 += count;
            }
            System.out.println("part 1 total: " + totalPart1);
            System.out.println("part 2 total: " + totalPart2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
