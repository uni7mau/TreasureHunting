package com.treasurehunting.java.utils;

import java.io.*;

public class ScoreSave {

    private static final String FILE_NAME = "highscore.txt";

    public static int score = 0;
    public static int highScore = 0;
    public static double playTime;

    static {
        loadHighScore();
    }

    public static void gainScore(int point) {
        score = point;
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }
    }

    private static void loadHighScore() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null) {
                    highScore = Integer.parseInt(line.trim());
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Lỗi khi đọc high score: " + e.getMessage());
                highScore = 0;
            }
        } else {
            highScore = 0;
        }
    }

    private static void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi high score: " + e.getMessage());
        }
    }

    public static int getScore() {
        return score;
    }

    public static int getHighScore() {
        return highScore;
    }

}
