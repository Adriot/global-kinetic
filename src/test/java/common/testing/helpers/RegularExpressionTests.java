package common.testing.helpers;

import org.testng.annotations.Test;

import java.util.regex.Matcher;

public class RegularExpressionTests {
    @Test
    public void regexTest() {
        String pattern = "\\d+";
        String input = "551972";
        RegularExpression regularExpression = new RegularExpression(pattern, input);
        Matcher matcher = regularExpression.runMatch();
        boolean matches = matcher.find();
        if (matches) {
            String group = matcher.group();
            System.out.println(group);
        }
    }
}
