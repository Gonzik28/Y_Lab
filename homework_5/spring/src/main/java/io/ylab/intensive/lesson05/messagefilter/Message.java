package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class Message {
    private final DataSource dataSource = new Config().dataSource();

    public void fillObsceneLanguage(File file) throws SQLException {
        ArrayList<String> lines = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(x -> lines.add(x));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String sqlInsert = "INSERT INTO obscene_language (word) VALUES (?)";
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlInsert);
        for (String line : lines) {
            preparedStatement.setString(1, line);
            preparedStatement.executeUpdate();
        }
        preparedStatement.close();
        dataSource.getConnection().close();
    }

    private String findWord(String word) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM obscene_language WHERE word = ?")) {
            stmt.setString(1, word.toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    char[] incorrect = word.toCharArray();
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < incorrect.length; i++) {
                        if (i == 0 | i == incorrect.length - 1) {
                            buffer.append(incorrect[i]);
                        } else {
                            if (isEnglishLetter(incorrect[i]) || isRussianLetter(incorrect[i])) {
                                buffer.append('*');
                            } else {
                                buffer.append(incorrect[i]);
                            }
                        }
                    }
                    stmt.close();
                    conn.close();
                    return buffer.toString();
                }
                stmt.close();
                conn.close();
                return word;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String pullMessage(String message) {
        ArrayList<String> words = splitWords(message);
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            int length = word.length() - 1;

            if (length >= 1) {
                String changeWord;

                if (!isLetterOrPunctuationMark(word.charAt(length))) {
                    String punctuationMark = String.valueOf(word.charAt(length));
                    changeWord = findWord(word.substring(0, length));
                    buffer.append(changeWord);
                    buffer.append(punctuationMark);
                } else {
                    changeWord = findWord(word);
                    if (!word.equals(changeWord)) {
                        buffer.append(changeWord);
                        i++;
                    } else {
                        buffer.append(word);
                    }
                }
            } else {
                buffer.append(findWord(words.get(i)));
            }
        }

        return buffer.toString();
    }

    private ArrayList<String> splitWords(String message) {
        ArrayList<String> words = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char character = message.charAt(i);

            if (isSeparator(character)) {
                stringBuilder.append(character);
                words.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            } else {
                if (i == message.length() - 1) {
                    stringBuilder.append(character);
                    words.add(stringBuilder.toString());
                } else {
                    stringBuilder.append(character);
                }
            }
        }

        return words;
    }

    private boolean isSeparator(char character) {
        return (character == ' ') || (character == '.') || (character == '\t') || (character == '\n')
                || (character == '\r') || (character == ',') || (character == ';') || (character == '?')
                || (character == '!');
    }

    private boolean isLetterOrPunctuationMark(char character) {
        return isRussianLetter(character) || isEnglishLetter(character) || isPunctuationMark(character);
    }

    private boolean isRussianLetter(char character) {
        return ((int) character >= 1040) && ((int) character <= 1105);
    }

    private boolean isEnglishLetter(char character) {
        return ((int) character >= 65) &&
                ((int) character <= 90) ||
                ((int) character >= 97) &&
                        ((int) character <= 122);
    }

    private boolean isPunctuationMark(char character) {
        return (character == '.') ||
                (character == ',') ||
                (character == ';') ||
                (character == '?') ||
                (character == '!') ||
                (character == ':') ||
                (character == '-');
    }

}
