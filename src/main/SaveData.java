package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import utils.Utils;

public class SaveData {
	private String saveDataPath;
	private String[] saveData;
	private boolean gameStarted;
	private int currentLevelIndex;
	private int playerHealth;
	private int totalScore;

	public SaveData() {
		saveDataPath = "/Maps/saveData.saveData";
		saveData = Utils.loadFileAsString(saveDataPath).split("\\R");		
		gameStarted = Boolean.parseBoolean(saveData[0]);
		currentLevelIndex = Integer.parseInt(saveData[1]);
		playerHealth = Integer.parseInt(saveData[2]);
		totalScore = Integer.parseInt(saveData[3]);
	}

	public void reset() {
		gameStarted = false;
		currentLevelIndex = 0;
		playerHealth = 6;
		totalScore = 0;
		
		writeSaveData();
	}

	public void writeSaveData() {
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			FileWriter fileWriter = new FileWriter(classLoader.getResource("Maps/saveData.saveData").getFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			System.out.println("FILE IS WRITING");
			bufferedWriter.write(Boolean.toString(gameStarted));
			bufferedWriter.newLine();
			bufferedWriter.write(Integer.toString(currentLevelIndex));
			bufferedWriter.newLine();
			bufferedWriter.write(Integer.toString(playerHealth));
			bufferedWriter.newLine();
			bufferedWriter.write(Integer.toString(totalScore));
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setGameStarted(boolean gameStarted) { this.gameStarted = gameStarted; }
	public void setCurrentLevelIndex(int index) { currentLevelIndex = index; }
	public void setPlayerHealth(int health) { playerHealth = health; }
	public void setTotalScore(int score) { totalScore = score; }

	public boolean getGameStarted() { return gameStarted; }
	public int getCurrentLevelIndex() { return currentLevelIndex; }
	public int getPlayerHealth() { return playerHealth; }
	public int getTotalScore() { return totalScore; }

}
