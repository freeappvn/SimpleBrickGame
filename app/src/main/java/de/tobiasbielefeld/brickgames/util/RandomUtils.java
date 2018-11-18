package de.tobiasbielefeld.brickgames.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    /**
     * Get random value in range
     *
     * @param start include
     * @param end   exclude
     * @return
     */
    public static int getRandomNumber(int start, int end) {
        return start + getRandomNumber(end - start);
    }

    /**
     * Get random value in range form 0 to bound - 1
     *
     * @param bound exclude
     * @return
     */
    public static int getRandomNumber(int bound) {
        Random random = new Random(Calendar.getInstance().getTimeInMillis());
        int value = random.nextInt(bound);
        return value;
    }

    /**
     * Lay cac gia tri nam trong khoang nay
     *
     * @param quantity
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    public static List<Integer> getRandomNumber(int quantity, int start, int end) throws Exception {
        List<Integer> numbers = new ArrayList<>();
        if (quantity > end - start)
            throw new Exception("khong the lay nhieu gia tri trong khoang nay ");
        while (numbers.size() < quantity) {
            int value = getRandomNumber(start, end);
            if (!numbers.contains(value)) {
                numbers.add(value);
            }
        }
        return numbers;
    }


}
