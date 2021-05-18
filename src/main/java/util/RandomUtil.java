package util;

import java.util.Random;

public class RandomUtil {
    private final static Random random = new Random();

    public static int randomInt(int start, int end) {
        return start + random.nextInt(end - start);
    }

    public static boolean randomBoolean(double probability) {
        return random.nextDouble() < probability;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.print(randomInt(0, 10) + ", ");
        }
        System.out.println(randomInt(0, 10));
        for (int i = 0; i < 10; i++) {
            System.out.println(randomBoolean(0.5));
        }
    }
}
