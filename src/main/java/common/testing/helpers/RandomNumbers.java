package common.testing.helpers;

public class RandomNumbers {
    public static int getRandomNumber(int max, int min) {
        int range = max - min + 1;

        return (int)(Math.random() * range) + min;
    }
}
