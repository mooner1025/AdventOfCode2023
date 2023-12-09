import java.io.File;
import java.util.*;

public class AdventOfCodeDay5 {
    public static void main(String[] args) {
        try {
            File file = new File("./src/input5.txt");
            Scanner sc = new Scanner(file);

            //parsing the file into a list of given seeds
            String line = sc.nextLine();
            List<Long> seeds = Arrays.stream(line.substring(line.indexOf(':') + 2).split(" ")).map(Long::valueOf).toList();
            List<Long> starterSeeds = new ArrayList<>();
            List<Long> seedRanges = new ArrayList<>();
            Integer[] counter = new Integer[1];
            counter[0] = 0;
            seeds.forEach((seed) -> {
                if (counter[0] % 2 == 0) {
                    starterSeeds.add(seed);
                } else {
                    seedRanges.add(seed);
                }
                counter[0] ++;
            });

            //parsing the file into a 2D lists to store the soil-location maps
            List<List<Long>> starts = new ArrayList<>();
            List<List<Long>> conversions = new ArrayList<>();
            List<List<Long>> ranges = new ArrayList<>();
            line = sc.nextLine();
            line = sc.nextLine();
            Integer rowCounter = 0;
            for (int i = 0; i < 7; i++) {
                List<Long> nestedStart = new ArrayList<>();
                List<Long> nestedConversions = new ArrayList<>();
                List<Long> nestedRanges = new ArrayList<>();

                line = sc.nextLine();
                while (!line.isEmpty()) {
                    String[] rowData = line.split(" ");
                    nestedStart.add(Long.parseLong(rowData[1]));
                    nestedConversions.add(Long.parseLong(rowData[0]) - Long.parseLong(rowData[1]));
                    nestedRanges.add(Long.parseLong(rowData[2]));

                    if (!sc.hasNextLine()) {
                        break;
                    }
                    line = sc.nextLine();
                    rowCounter ++;
                }
                starts.add(nestedStart);
                conversions.add(nestedConversions);
                ranges.add(nestedRanges);
                if (sc.hasNextLine()) {
                    line = sc.nextLine();
                }
            }


            //PART I: solving part 1 by taking the given seeds and putting them through the maps and tracking the
            // lowest resulting location
            Long locationLow = null;
            Long input = null;
            Long output = null;
            for (Long seed : seeds) {
                input = seed;
                output = -1L;
                for (int k = 0; k < 7; k++) {
                    List<Long> nestedStarts = starts.get(k);
                    List<Long> nestedConversions = conversions.get(k);
                    List<Long> nestedRanges = ranges.get(k);
                    for (int i = 0; i < nestedStarts.size(); i++) {
                        Long start = nestedStarts.get(i);
                        Long conversion = nestedConversions.get(i);
                        Long range = nestedRanges.get(i);

                        if (input >= start && input <= start + range) {
                            output = input + conversion;
                            break;
                        }
                    }
                    if (output >= 0) {
                        input = output;
                        output = -1L;
                    }
                }
                if (Objects.isNull(locationLow) || input < locationLow) {
                    locationLow = input;
                }
            }


            //PART II: Reversing the logic to plug in 1 in last map and work back towards the start and check if the result is a given seed
            //because brute forcing is not sustainable with that many seeds
            Long initialInput = 0L;
            output = -1L;
            while(true) {
                input = initialInput;
                for (int k = 6; k >= 0; k--) {
                    List<Long> nestedStarts = starts.get(k);
                    List<Long> nestedConversions = conversions.get(k);
                    List<Long> nestedRanges = ranges.get(k);
                    for (int i = 0; i < nestedStarts.size(); i++) {
                        Long start = nestedStarts.get(i);
                        Long conversion = nestedConversions.get(i);
                        Long range = nestedRanges.get(i);

                        if (input >= start + conversion && input <= start + conversion + range) {
                            output = input - conversion;
                            break;
                        }
                    }
                    if (output >= 0) {
                        input = output;
                        output = -1L;
                    }
                }


                int seedCounter = 0;
                boolean found = false;
                for (Long seed : starterSeeds) {
                    if (input >= seed && input <= seed + seedRanges.get(seedCounter)) {
                        found = true;
                        break;
                    }
                    seedCounter ++;
                }
                if (found) {
                    break;
                }
                initialInput ++;
            }

            System.out.println("lowest location part 1: " + locationLow);
            System.out.println("lowest location part 2: " + initialInput);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
