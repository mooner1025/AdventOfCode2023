import java.io.File;
import java.util.*;

public class AdventOfCodeDay3 {
//I hate everything about this but I started it this way and just wanted to chug through it to get it done. bleh
    private static final List<Character> NUMBERS = List.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0');

    public static void main(String[] args) {
        try {
            File file = new File("./src/input3.txt");
            Scanner sc = new Scanner(file);

            Integer total = 0;
            Integer gearTotal = 0;

            String prevLine = null;
            String curLine = null;
            String nextLine = sc.nextLine();
            while (sc.hasNextLine()) {
                prevLine = curLine;
                curLine = nextLine;
                nextLine = sc.nextLine();

                List<String> numbers = new ArrayList<>();
                List<Integer> numbersIndex = new ArrayList<>();

                //finding all part number locations
                Integer startIndex = 0;
                for (int i = 0; i < curLine.length(); i++) {
                    StringBuilder tempNumber = new StringBuilder();
                    Character character = curLine.charAt(i);
                    if (NUMBERS.contains(character)) {
                        startIndex = i;
                        while (NUMBERS.contains(character) && i < curLine.length()) {
                            tempNumber.append(character);
                            i++;
                            if (i >= curLine.length()){
                                continue;
                            }
                            character = curLine.charAt(i);
                        }
                        numbers.add(tempNumber.toString());
                        numbersIndex.add(startIndex);
                    }
                }

                //finding all * locations
                startIndex = 0;
                for (int i = 0; i < curLine.length(); i++) {
                    if (curLine.charAt(i) == '*') {
                        String gearNum1 = "0";
                        String gearNum2 = "0";
                        Boolean foundNum1 = false;
                        Boolean foundNum2 = false;
                        startIndex = i;
                        Boolean isStartOfRow = startIndex.equals(0);
                        Boolean isEndOfRow = startIndex.equals(curLine.length() - 1);
                        StringBuilder tempNum = new StringBuilder();

                        //checking line above *
                        //does line above exist?
                        if (Objects.nonNull(prevLine)) {
                            //does line above have a part number?
                            if ((!isStartOfRow && NUMBERS.contains(prevLine.charAt(startIndex -1))) ||
                                    (!isEndOfRow && NUMBERS.contains(prevLine.charAt(startIndex +1))) ||
                                    NUMBERS.contains(prevLine.charAt(startIndex))
                            ) {
                                //finding start of part numbers in line of
                                while (startIndex -1 >= 0 && NUMBERS.contains(prevLine.charAt(startIndex - 1))) {
                                    startIndex --;
                                }
                                //getting first part number
                                while (startIndex + 1 <= prevLine.length() && NUMBERS.contains(prevLine.charAt(startIndex))) {
                                    tempNum.append(prevLine.charAt(startIndex));
                                    startIndex ++;
                                }
                                if (!tempNum.isEmpty()) {
                                    foundNum1 = true;
                                    gearNum1 = tempNum.toString();
                                    tempNum = new StringBuilder();
                                }
                                if (startIndex <= i + 1) {
                                    startIndex ++;
                                    tempNum = new StringBuilder();
                                    while (startIndex + 1 <= prevLine.length() && NUMBERS.contains(prevLine.charAt(startIndex))) {
                                        tempNum.append(prevLine.charAt(startIndex));
                                        startIndex ++;
                                    }
                                    if (!tempNum.isEmpty()) {
                                        if (foundNum1) {
                                            foundNum2 = true;
                                            gearNum2 = tempNum.toString();
                                        } else {
                                            foundNum1 = true;
                                            gearNum1 = tempNum.toString();
                                        }
                                        tempNum = new StringBuilder();
                                    }
                                }
                            }
                        }

                        //checking line of *
                        if (!foundNum2) {
                            //checking to the left of *
                            startIndex = i-1;
                            if (NUMBERS.contains(curLine.charAt(startIndex))) {
                                //finding start of part numbers in line of
                                while (startIndex -1 >= 0 && NUMBERS.contains(curLine.charAt(startIndex - 1))) {
                                    startIndex --;
                                }
                                //getting part number
                                while (startIndex + 1 <= curLine.length() && NUMBERS.contains(curLine.charAt(startIndex))) {
                                    tempNum.append(curLine.charAt(startIndex));
                                    startIndex ++;
                                }
                                if (!tempNum.isEmpty()) {
                                    if (foundNum1) {
                                        foundNum2 = true;
                                        gearNum2 = tempNum.toString();
                                    } else {
                                        foundNum1 = true;
                                        gearNum1 = tempNum.toString();
                                    }
                                    tempNum = new StringBuilder();
                                }
                            }
                            //checking to the right of *
                            startIndex = i+1;
                            if (NUMBERS.contains(curLine.charAt(startIndex))) {
                                //getting part number
                                while (startIndex + 1 <= curLine.length() && NUMBERS.contains(curLine.charAt(startIndex))) {
                                    tempNum.append(curLine.charAt(startIndex));
                                    startIndex ++;
                                }
                                if (!tempNum.isEmpty()) {
                                    if (foundNum1) {
                                        foundNum2 = true;
                                        gearNum2 = tempNum.toString();
                                    } else {
                                        foundNum1 = true;
                                        gearNum1 = tempNum.toString();
                                    }
                                    tempNum = new StringBuilder();
                                }
                            }
                        }

                        //checking line below *
                        //does line below exist?
                        if (Objects.nonNull(nextLine)) {
                            //does line above have a part number?
                            startIndex = i;
                            if ((!isStartOfRow && NUMBERS.contains(nextLine.charAt(startIndex -1))) ||
                                    (!isEndOfRow && NUMBERS.contains(nextLine.charAt(startIndex +1))) ||
                                    NUMBERS.contains(nextLine.charAt(startIndex))
                            ) {
                                //finding start of part numbers in line of
                                while (startIndex -1 >= 0 && NUMBERS.contains(nextLine.charAt(startIndex - 1))) {
                                    startIndex --;
                                }
                                //getting first part number
                                while (startIndex + 1 <= nextLine.length() && NUMBERS.contains(nextLine.charAt(startIndex))) {
                                    tempNum.append(nextLine.charAt(startIndex));
                                    startIndex ++;
                                }
                                if (!tempNum.isEmpty()) {
                                    if (foundNum1) {
                                        foundNum2 = true;
                                        gearNum2 = tempNum.toString();
                                    } else {
                                        foundNum1 = true;
                                        gearNum1 = tempNum.toString();
                                    }
                                    tempNum = new StringBuilder();
                                }
                                if (startIndex < i + 1) {
                                    startIndex ++;
                                    tempNum = new StringBuilder();
                                    while (startIndex + 1 <= nextLine.length() && NUMBERS.contains(nextLine.charAt(startIndex))) {
                                        tempNum.append(nextLine.charAt(startIndex));
                                        startIndex ++;
                                    }
                                    if (!tempNum.isEmpty()) {
                                        if (foundNum1) {
                                            foundNum2 = true;
                                            gearNum2 = tempNum.toString();
                                        } else {
                                            foundNum1 = true;
                                            gearNum1 = tempNum.toString();
                                        }
                                        tempNum = new StringBuilder();
                                    }
                                }
                            }
                        }

                        if (foundNum1 && foundNum2) {
                            gearTotal += (Integer.parseInt(gearNum1)*Integer.parseInt(gearNum2));
                        }
                    }
                }

                int i = 0;
                for (String number : numbers) {
                    if (number.equals("")) {
                        i++;
                        continue;
                    }
                    Integer start = numbersIndex.get(i);
                    Integer end = start + number.length() -1;
                    Boolean isStartOfRow = start.equals(0);
                    Boolean isEndOfRow = end.equals(curLine.length() - 1);
                    Boolean isPart = false;
                    if (!isStartOfRow) {
                        start --;
                        isPart = isPart || curLine.charAt(start) != '.';
                    }
                    if (!isEndOfRow) {
                        end ++;
                        isPart = isPart || curLine.charAt(end) != '.';
                    }
                    while (start <= end && !isPart) {
                        if (Objects.nonNull(prevLine)) {
                            isPart = isPart || prevLine.charAt(start) != '.' || prevLine.charAt(end) != '.';
                        }
                        isPart = isPart || nextLine.charAt(start) != '.' || nextLine.charAt(end) != '.';
                        start ++;
                        end --;
                    }
                    if (isPart) {
                        total += Integer.parseInt(number);
                    }
                    i++;
                }
            }

            prevLine = curLine;
            curLine = nextLine;

            List<String> numbers = new ArrayList<>();
            List<Integer> numbersIndex = new ArrayList<>();

            Integer startIndex = 0;
            for (int i = 0; i < curLine.length(); i++) {
                StringBuilder tempNumber = new StringBuilder();
                Character character = curLine.charAt(i);
                if (NUMBERS.contains(character)) {
                    startIndex = i;
                    while (NUMBERS.contains(character) && i < curLine.length() - 1) {
                        tempNumber.append(character);
                        i++;
                        character = curLine.charAt(i);
                    }
                    numbers.add(tempNumber.toString());
                    numbersIndex.add(startIndex);
                }
            }

            int i = 0;
            for (String number : numbers) {
                if (number.equals("")) {
                    i++;
                    continue;
                }
                Integer start = numbersIndex.get(i);
                Integer end = start + number.length() -1;
                Boolean isStartOfRow = start.equals(0);
                Boolean isEndOfRow = end.equals(curLine.length() - 1);
                Boolean isPart = false;
                if (!isStartOfRow) {
                    start --;
                    isPart = isPart || curLine.charAt(start) != '.';
                }
                if (!isEndOfRow) {
                    end ++;
                    isPart = isPart || curLine.charAt(end) != '.';
                }
                while (start <= end && !isPart) {
                    if (Objects.nonNull(prevLine)) {
                        isPart = isPart || prevLine.charAt(start) != '.' || prevLine.charAt(end) != '.';
                    }
                    start ++;
                    end --;
                }
                if (isPart) {
                    total += Integer.parseInt(number);
                }
                i++;
            }

            System.out.println("part 1: " + total);
            System.out.println("part 2: " + gearTotal);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
