package common.testing.helpers;

import org.testng.annotations.Test;

public class RandomNumbersTests {
    @Test
    public void randomNumbersTest() {
        int randomNumber = RandomNumbers.getRandomNumber(0, 9);
        System.out.println("Random Number: " + randomNumber);
    }
}
