package com.twojstary.javagame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.twojstary.javagame.JavaGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(75);
		config.setTitle("java game");
		config.setWindowedMode(1280, 720);
		new Lwjgl3Application(new JavaGame(), config);
	}
}
