import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdventOfCodeDay1 {

    public static void main(String[] args) {

        try {
            File file = new File("./src/input.txt");
            Scanner sc = new Scanner(file);

            //map of string numbers to digit numbers
            Map<String, Character> numWordToDigitMap = Map.of("zero", '0', "one", '1', "two", '2', "three", '3', "four", '4', "five", '5', "six", '6', "seven", '7', "eight", '8', "nine", '9');
            //list of digits
            List<Character> charNumList = numWordToDigitMap.values().stream().toList();
            Integer total = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                Integer end = line.length() - 1;
                Integer start = 0;
                Boolean stillSearchingFirst = true;
                Boolean stillSearchingLast = true;
                char first = '0';
                char last = '0';
                String numForLine = "00";

                //starting checking at beginning and end of line and quit when both first and last digits are found or the iterators meet
                while (end >= start && (stillSearchingFirst || stillSearchingLast)) {

                    //if we havent found the first digit...
                    if (stillSearchingFirst) {
                        //if character is a digit store it and mark first as found
                        if (charNumList.contains(line.charAt(start))) {
                            first = line.charAt(start);
                            stillSearchingFirst = false;
                        } else {
                            //check for the string to the left of our first iterator for a spelled out number and store and mark first as found if it does
                            for (Map.Entry<String, Character> entry : numWordToDigitMap.entrySet()) {
                                String word = entry.getKey();
                                Character digit = entry.getValue();
                                if (line.substring(0, start+1).contains(word)) {
                                    first = digit;
                                    stillSearchingFirst = false;
                                }
                            }
                            start++;
                        }
                    }

                    //if we havent found the second digit...
                    if (stillSearchingLast) {
                        //if character is a digit store it and mark second as found
                        if (charNumList.contains(line.charAt(end))) {
                            last = line.charAt(end);
                            stillSearchingLast = false;
                        } else {
                            //check for the string to the right of our second iterator for a spelled out number and store and mark second as found if it does
                            for (Map.Entry<String, Character> entry : numWordToDigitMap.entrySet()) {
                                String word = entry.getKey();
                                Character digit = entry.getValue();
                                if (line.substring(end-1).contains(word)) {
                                    last = digit;
                                    stillSearchingLast = false;
                                }
                            }
                            end--;
                        }
                    }
                }
                numForLine = String.valueOf(first) + String.valueOf(last);
                total += Integer.parseInt(numForLine);
            }
            System.out.println(total);
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
