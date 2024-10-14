package _4_Loops_to_functional;

import java.util.ArrayList;
import java.util.List;

public class Collections {

    // filter
    public static List<Integer> sumEvenNumbers(List<Integer> numbers) {
        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                evenNumbers.add(number);
            }
        }
        return evenNumbers;
    }

    // map
    public static List<Integer> getLengths(List<String> strings) {
        List<Integer> lengths = new ArrayList<>();
        for (String string : strings) {
            lengths.add(string.length());
        }
        return lengths;
    }

    // fold
    public static String concatenateWithPrefix(List<String> strings, String prefix) {
        String result = prefix;
        for (String str : strings) {
            result += str;
        }
        return result;
    }

    // reduce
    public static int sumOfNumbers(List<Integer> numbers) {
        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }
        return sum;
    }
}