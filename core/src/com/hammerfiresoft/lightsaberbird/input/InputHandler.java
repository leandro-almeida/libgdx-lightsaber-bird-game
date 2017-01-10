package com.hammerfiresoft.lightsaberbird.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.hammerfiresoft.lightsaberbird.game.objects.LightSaber;
import com.hammerfiresoft.lightsaberbird.screens.GameScreen;

public class InputHandler extends InputAdapter {

	private LightSaber bird;
	
	public InputHandler(LightSaber bird) {
		this.bird = bird;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (Keys.SPACE == keycode){
			bird.doFlap();
		} else if (Keys.R == keycode) {
			((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
		} else if (Keys.ESCAPE == keycode) {
			((Game)Gdx.app.getApplicationListener()).dispose();
			Gdx.app.exit();
		} else if (Keys.F1 == keycode) {
			
			if (Application.LOG_DEBUG == Gdx.app.getLogLevel()) {
				Gdx.app.setLogLevel(Application.LOG_ERROR);
			} else {
				Gdx.app.setLogLevel(Application.LOG_DEBUG);
			}
		}
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (bird.isAlive()) {
			bird.doFlap();
		} else {
			((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
		}
		return true;
	}

}
