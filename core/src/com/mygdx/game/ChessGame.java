package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.GameScreen;
import screens.MainMenuScreen;

public class ChessGame extends Game {

	public static final int WIDTH = 720;
	public static final int HEIGHT = 720;

	private SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {}

	public SpriteBatch getBatch() {
		return batch;
	}
}
