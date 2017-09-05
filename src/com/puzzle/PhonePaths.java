package com.puzzle;

import java.util.*;

/**
 * Phone path combinations
 * <p>
 * Created by siva on 9/4/17
 */
public class PhonePaths {

    private int[][] PHONE_MATRIX = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {-1, 0, -1}};
    private int ROW_SIZE = PHONE_MATRIX.length;
    private int COL_SIZE = PHONE_MATRIX[0].length;
    private Map<Integer, Set<Integer>> states = new HashMap<>();

    /**
     * Returns number of possible paths starting from given digit of size n
     *
     * @param digit Starting digit
     * @param n     Path size
     * @return Number of paths
     */
    public int findNumberOfPaths(int digit, int n) {
        return _find(digit, n);
    }

    /**
     * Find number of possible paths starting from given digit of size n
     *
     * @param digit Starting digit
     * @param n     Path size
     * @return Number of paths
     */
    private int _find(int digit, int n) {

        if (n == 0)
            return 0;
        else if (n == 1)
            return 1;
        Set<Stack<Integer>> results = new HashSet<>();

        // start the flow from given digit
        Queue<Stack<Integer>> queue = new LinkedList<>();
        Stack<Integer> path = new Stack<>();
        path.add(digit);
        queue.add(path);

        while (!queue.isEmpty()) {
            path = queue.poll();

            if (path.size() == n && !results.contains(path)) {
                results.add(path);
                continue;
            }

            Integer currentPathDigit = path.peek();
            Set<Integer> states = getStates(currentPathDigit);
            if (states != null && states.size() > 0) {
                for (Integer state : states) {
                    Stack<Integer> newPath = new Stack<>();
                    newPath.addAll(path);
                    newPath.add(state);
                    queue.add(newPath);
                }
            }
        }

        results.forEach(System.out::println);
        return results.size();
    }

    /**
     * Returns possible next states for a given digit; use memoization for optimization
     *
     * @param digit Digit
     * @return All possible states
     */
    private Set<Integer> getStates(int digit) {
        if (states.containsKey(digit)) {
            return states.get(digit);
        }

        int digitRow = -1;
        int digitCol = -1;
        for (int row = 0; digitRow == -1 && row < PHONE_MATRIX.length; ++row) {
            for (int col = 0; digitCol == -1 && col < PHONE_MATRIX[row].length; ++col) {
                if (PHONE_MATRIX[row][col] == digit) {
                    digitRow = row;
                    digitCol = col;
                    break;
                }
            }
        }

        Set<Integer> nextStates = getNextState(digit, digitRow, digitCol);
        states.put(digit, nextStates);
        return states.get(digit);
    }

    /**
     * This method returns the possible states for a given digit
     *
     * @param digit Digit
     * @param row   Row index for the given digit
     * @param col   Column index for the given digit
     * @return List of possible states
     */
    private Set<Integer> getNextState(int digit, int row, int col) {
        Set<Integer> set = new HashSet<>();

        // 1 to 6
        if (row - 2 >= 0 && col - 1 >= 0 && PHONE_MATRIX[row - 2][col - 1] != -1) {
            set.add(PHONE_MATRIX[row - 2][col - 1]);
        }

        // 4 to 9
        if (row + 1 < ROW_SIZE && col + 2 < COL_SIZE && PHONE_MATRIX[row + 1][col + 2] != -1) {
            set.add(PHONE_MATRIX[row + 1][col + 2]);
        }

        // 1 to 8
        if (row + 2 < ROW_SIZE && col + 1 < COL_SIZE && PHONE_MATRIX[row + 2][col + 1] != -1) {
            set.add(PHONE_MATRIX[row + 2][col + 1]);
        }

        // 6 to 7
        if (row + 1 < ROW_SIZE && col - 2 >= 0 && PHONE_MATRIX[row + 1][col - 2] != -1) {
            set.add(PHONE_MATRIX[row + 1][col - 2]);
        }

        // 3 to 4
        if (row - 1 >= 0 && col + 2 < COL_SIZE && PHONE_MATRIX[row - 1][col + 2] != -1) {
            set.add(PHONE_MATRIX[row - 1][col + 2]);
        }

        // 8 to 3
        if (row + 2 < ROW_SIZE && col - 1 >= 0 && PHONE_MATRIX[row + 2][col - 1] != -1) {
            set.add(PHONE_MATRIX[row + 2][col - 1]);
        }

        // 7 to 2
        if (row - 2 >= 0 && col + 1 < COL_SIZE && PHONE_MATRIX[row - 2][col + 1] != -1) {
            set.add(PHONE_MATRIX[row - 2][col + 1]);
        }
        return set;
    }

    public static void main(String... args) {
        PhonePaths pc = new PhonePaths();
        System.out.println(pc.findNumberOfPaths(1, 0));
        System.out.println(pc.findNumberOfPaths(1, 1));
        System.out.println(pc.findNumberOfPaths(1, 4));
    }
}