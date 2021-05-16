package util;

import java.util.Random;

public class RandomUtil {

    public static int randomInt(int start, int end) {
        Random random = new Random(System.nanoTime());
        return start + random.nextInt(end - start);
    }

    public static boolean randomBoolean(double probability) {
        Random random = new Random(System.nanoTime());
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
