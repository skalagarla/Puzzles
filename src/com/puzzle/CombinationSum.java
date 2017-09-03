package com.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Print combinations for a given array for a given sum.
 *
 * Created by siva on 9/3/17.
 */
public class CombinationSum {

    private static LinkedList<LinkedList<Integer>> combinationSum(List<Integer> numbers, int sum) {
        Collections.sort(numbers);

        numbers = numbers.stream().distinct().collect(Collectors.toList());

        LinkedList<Integer> r = new LinkedList<>();
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();

        findNumbers(numbers, sum, result, r, 0);

        return result;

    }

    private static void findNumbers(List<Integer> numbers, int sum, LinkedList<LinkedList<Integer>> result, LinkedList<Integer> r, int i) {

        if (sum < 0)
            return;

        if (sum == 0) {
            result.addLast(new LinkedList<>(r));
            r = new LinkedList<>();
            return;
        }

        while (i < numbers.size() && sum - numbers.get(i) >= 0) {
            r.push(numbers.get(i));

            findNumbers(numbers, sum - numbers.get(i), result, r, i);
            ++i;

            r.pop();
        }
    }

    public static void main(String... args) {

        List<Integer> numbers = new ArrayList<>();
        numbers.add(2);
        numbers.add(4);
        numbers.add(6);
        numbers.add(8);

        int sum = 10;


        LinkedList<LinkedList<Integer>> result = combinationSum(numbers, sum);

        for (int i = 0; i < result.size(); ++i) {
            if (result.get(i).size() > 0) {
                System.out.print("(");
                List<Integer> r = result.get(i);
                for (int j = 0; j < r.size(); ++j) {
                    System.out.print(r.get(j) + " ");
                }
                System.out.println(")");
            }
        }
    }
}
