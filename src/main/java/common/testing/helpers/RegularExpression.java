package common.testing.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {
    private String pattern;
    private String input;
    private Matcher matcher;

    public RegularExpression() {
    }

    public RegularExpression(String pattern) {
        this.pattern = pattern;
    }

    public RegularExpression(String pattern, String input) {
        this.pattern = pattern;
        this.input = input;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public Matcher runMatch() {
        Pattern matchPattern = Pattern.compile(pattern);
        matcher = matchPattern.matcher(input);
        return matcher;
    }

    public Matcher runMatch(String input) {
        Pattern matchPattern = Pattern.compile(pattern);
        matcher = matchPattern.matcher(input);
        return matcher;
    }

    public Matcher runMatch(String pattern, String input) {
        Pattern matchPattern = Pattern.compile(pattern);
        matcher = matchPattern.matcher(input);
        return matcher;
    }
}
