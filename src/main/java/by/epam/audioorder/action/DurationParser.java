package by.epam.audioorder.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurationParser {
    private static final Pattern DURATION_PATTERN = Pattern.compile("(\\d*:)?(\\d)");

    static int parseDuration(String durationString) {
        Matcher matcher = DURATION_PATTERN.matcher(durationString);
        if (matcher.matches()) {
            int duration = 0;
            duration += Integer.parseInt(matcher.group(2));
            if (matcher.group(1) != null) {
                duration += 60 * Integer.parseInt(matcher.group(1));
            }
            return duration;
        } else {
            return 0;
        }
    }
}
