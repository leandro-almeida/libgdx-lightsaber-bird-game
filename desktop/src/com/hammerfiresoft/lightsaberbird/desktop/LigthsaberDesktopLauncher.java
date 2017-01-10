package com.hammerfiresoft.lightsaberbird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hammerfiresoft.lightsaberbird.LightSaberBird;

public class LigthsaberDesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Light Saber Bird";
		config.width = 480;
		config.height = 850;
		config.vSyncEnabled = true;
		config.allowSoftwareMode = true;
		new LwjglApplication(new LightSaberBird(), config);
	}
}