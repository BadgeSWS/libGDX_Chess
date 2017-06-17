package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ChessGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 144;
		config.backgroundFPS = 60;
		config.resizable = false;
		config.width = ChessGame.WIDTH;
		config.height = ChessGame.HEIGHT;
		new LwjglApplication(new ChessGame(), config);
	}
}
