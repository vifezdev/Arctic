package lol.pots.core.filter;

import lol.pots.core.Arctic;
import lol.pots.core.util.config.ConfigFile;


import java.util.List;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import static org.apache.commons.lang3.math.NumberUtils.min;

public class FilterManagement {


    private ConfigFile config = Arctic.getInstance().getFilterConfig();

    public boolean isEnabled() {
        return config.getBoolean("ENABLED");
    }

    public List<String> getDisallowedWords() {
        return config.getStringList("FILTERED_WORDS");
    }

    public List<String> getWhitelistedLinks() {
        return config.getStringList("WHITELISTED_LINKS");
    }

    public void addDisallowedWord(String word) {
        List<String> words = getDisallowedWords();
        if(!words.contains(word)) {
            words.add(word);
            config.set("FILTERED_WORDS", words);
            config.save();
        }
    }

    public boolean shouldFilterMessage(String message) {

        if (!isEnabled()) {
            return false;
        }

        String normalized = message.replaceAll("\\s", "");

        for (String word : getDisallowedWords()) {

            if (normalized.contains(word)) {
                return true;
            }

            for (String msgWord : message.split(" ")) {

                if (levenshteinDistance(msgWord, word) <= 2) {
                    return true;
                }

            }

        }

        // Check for blacklisted links
        Pattern pattern = Pattern.compile("https?://[^\\s]+");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String link = matcher.group();
            if (!getWhitelistedLinks().contains(link)) {
                return true;
            }
        }

        return false;

    }

    public int levenshteinDistance(String s1, String s2) {

        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(
                            dp[i - 1][j - 1]
                                    + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1
                    );
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

}