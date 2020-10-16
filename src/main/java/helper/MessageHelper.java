package helper;

import model.Commands;
import model.Show;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static helper.TextHelper.toTitleCase;

public class MessageHelper {
    private MessageHelper() {
        // private constructor to avoid object instantiation
    }

    public static boolean isBotMessage(String msg) {
        return msg.split(Commands.BOT_SEPARATOR)[0].equals(Commands.BOT_PREFIX);
    }

    public static String extractShowName(String msg) {
        // show name should be surrounded in quotation marks
        String regex = "\"([^\"]*)\"|(\\S+)";

        Matcher m = Pattern.compile(regex).matcher(msg);
        while (m.find()) {
            if (m.group(1) != null) {
                return m.group(1).toLowerCase();
            }
        }

        // return null of msg does not contain show name
        return null;
    }

    /*
     * Split the given user argument and return a list of them
     */
    public static List<String> splitArguments(String msg) {
        String[] splits = msg.split(" ");
        List<String> argumentList = new ArrayList<>();
        StringBuilder token = null;

        for(String s : splits) {
            if(s.startsWith("\"") ) {
                token = new StringBuilder("" + s);
            } else if (s.endsWith("\"")) {
                assert token != null;
                token.append(" ").append(s);
                argumentList.add(token.toString());
                token = null;
            } else {
                if (token != null) {
                    token.append(" ").append(s);
                } else {
                    argumentList.add(s);
                }
            }
        }

        return argumentList.subList(2, argumentList.size());
    }

    public static String buildShowListString(List<Show> showList) {
        StringBuilder sb = new StringBuilder();
        sb.append("```").append(System.getProperty("line.separator"));
        int counter = 1;

        for (Show show : showList) {
            sb.append(counter).append(") ");
            sb.append(toTitleCase(show.getShowName())).append(" - ");
            sb.append("Episode ").append(show.getCurrEpisode());
            counter++;
        }

        sb.append("```");

        return sb.toString();
    }
}
