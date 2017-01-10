package com.hammerfiresoft.lightsaberbird;

import com.badlogic.gdx.Game;
import com.hammerfiresoft.lightsaberbird.assets.Assets;
import com.hammerfiresoft.lightsaberbird.screens.GameScreen;

public class LightSaberBird extends Game {

	@Override
	public void create() {
		Assets.loadAllAssets();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
	}

}
