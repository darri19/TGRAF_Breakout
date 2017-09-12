package com.assignment1dv.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.assignment1dv.game.Assignment1Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "The Durr"; // or whatever you like
		config.width = 1024; //experiment with
		config.height = 768; //the window size
		
		new LwjglApplication(new Assignment1Game(), config);
	}
}
