package com.hammerfiresoft.lightsaberbird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.hammerfiresoft.lightsaberbird.assets.Assets;
import com.hammerfiresoft.lightsaberbird.game.GameRenderer;
import com.hammerfiresoft.lightsaberbird.game.GameWorld;
import com.hammerfiresoft.lightsaberbird.input.InputHandler;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;
	
	private float runtime;
	public static float gameWidth, gameHeight;
	
	public GameScreen() {
//		float screenWidth = Gdx.graphics.getWidth();
//		float screenHeight = Gdx.graphics.getHeight();
//		gameWidth = 240;
//		float aspectRatio = screenWidth / gameWidth;
//		gameHeight = screenHeight / aspectRatio;
//		Gdx.app.debug("GAME_INFO", "Aspect Ratio -> " + aspectRatio);
		
		gameWidth = 240;
		gameHeight = 320;
		
		world = new GameWorld();
		renderer = new GameRenderer(world);

		Gdx.input.setInputProcessor(new InputHandler(world.getBird()));
		
		Assets.soundStart.play();
	}
	
	public static float convertWidth(float value) {
		return (value / 480) * 320;
	}
	
	public static float convertHeight(float value) {
		return (value / 320) * 240;
	}

	@Override
	public void render(float delta) {
		//runtime += delta;
		world.update(delta);
		renderer.render(runtime);
	}

	@Override
	public void resize(int width, int height) {
//		gameWidth = 163;
//        float aspectRatio = (width / gameWidth);
//        gameHeight = height / aspectRatio;
//        renderer.screenResized(gameWidth, gameHeight);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

}
