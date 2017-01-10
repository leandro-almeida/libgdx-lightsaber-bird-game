package com.hammerfiresoft.lightsaberbird.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.hammerfiresoft.lightsaberbird.assets.Assets;
import com.hammerfiresoft.lightsaberbird.game.objects.LightSaber;
import com.hammerfiresoft.lightsaberbird.game.objects.Pipe;
import com.hammerfiresoft.lightsaberbird.screens.GameScreen;

public class GameWorld {

	public static final float upperTubeW = 26;// Assets.upperTubeSprite01.getWidth();
	public static final float upperTubeH = 134;// Assets.upperTubeSprite01.getHeight();
	public static final float lowerTubeW = 26;// Assets.lowerTubeSprite01.getWidth();
	public static final float lowerTubeH = 119;// Assets.lowerTubeSprite01.getHeight();

	private LightSaber bird;
	private Pipe pipe1, pipe2, pipe3;
	private int points;

	public GameWorld() {
	}

	public void createBird(float w, float h) {
		bird = new LightSaber(GameScreen.gameWidth * 0.2f, GameScreen.gameHeight * 0.6f, w, h);
	}

	public void createPipes() {
		Gdx.app.debug("PIPE_INFO", "UppperTube WxH -> " + upperTubeW + " x " + upperTubeH);
		Gdx.app.debug("PIPE_INFO", "LowerTube WxH -> " + lowerTubeW + " x " + lowerTubeH);

		pipe1 = new Pipe(GameScreen.gameWidth + upperTubeW, MathUtils.random(GameRenderer.minY, GameRenderer.maxY), upperTubeW, upperTubeH, lowerTubeW, lowerTubeH);
		pipe2 = new Pipe(pipe1.getTailX() + Pipe.HORIZONTAL_GAP, MathUtils.random(GameRenderer.minY, GameRenderer.maxY), upperTubeW, upperTubeH, lowerTubeW, lowerTubeH);
		pipe3 = new Pipe(pipe2.getTailX() + Pipe.HORIZONTAL_GAP, MathUtils.random(GameRenderer.minY, GameRenderer.maxY), upperTubeW, upperTubeH, lowerTubeW, lowerTubeH);

		Gdx.app.debug("PIPE_INFO", "Upper1 -> (" + pipe1.getUpper().x + "," + pipe1.getUpper().y + ") | Lower1 -> (" + pipe1.getLower().x + "," + pipe1.getLower().y + ")");
		Gdx.app.debug("PIPE_INFO", "Upper2 -> (" + pipe2.getUpper().x + "," + pipe2.getUpper().y + ") | Lower2 -> (" + pipe2.getLower().x + "," + pipe2.getLower().y + ")");
		Gdx.app.debug("PIPE_INFO", "Upper3 -> (" + pipe3.getUpper().x + "," + pipe3.getUpper().y + ") | Lower3 -> (" + pipe3.getLower().x + "," + pipe3.getLower().y + ")");
	}

	public void update(float delta) {
		bird.update(delta);

		// enquanto o passaro estiver voando
		if (bird.isAlive()) {
			pipe1.scrollLeft(delta);
			pipe2.scrollLeft(delta);
			pipe3.scrollLeft(delta);

			checkCollisions();
			checkPoints();
			checkPipesScrolled();
		}
	}

	/**
	 * Verifica se o passaro colidiu em algo
	 */
	private void checkCollisions() {
		if (pipe1.collides(bird) | pipe2.collides(bird) | pipe3.collides(bird) | Intersector.overlaps(bird.getCollisionBounds(), Assets.groundSprite.getBoundingRectangle())) {
			gameOver();
		}
	}

	/**
	 * Verifica se os tubos sairam da tela pela esquerda
	 */
	private void checkPipesScrolled() {
		// Uso else if pois somente um vai sair por vez da tela
		if (pipe1.isScrolledOut()) {
			pipe1.resetXY(pipe3, MathUtils.random(GameRenderer.minY, GameRenderer.maxY));
		} else if (pipe2.isScrolledOut()) {
			pipe2.resetXY(pipe1, MathUtils.random(GameRenderer.minY, GameRenderer.maxY));
		} else if (pipe3.isScrolledOut()) {
			pipe3.resetXY(pipe2, MathUtils.random(GameRenderer.minY, GameRenderer.maxY));
		}
	}

	/**
	 * Verifica quando o passaro atravessa um pipe, somando +1 ponto.
	 */
	private void checkPoints() {
		if (pipe1.hasBirdPassedThroughAndCheckpoint(bird) || pipe2.hasBirdPassedThroughAndCheckpoint(bird) || pipe3.hasBirdPassedThroughAndCheckpoint(bird)) {
			points++;
			Assets.soundPoint.play();
		}
	}

	/**
	 * Passaro colidiu.
	 */
	private void gameOver() {
		bird.die();
		Assets.soundHit.play();
	}

	public LightSaber getBird() {
		return bird;
	}

	public Pipe getPipe1() {
		return pipe1;
	}

	public Pipe getPipe2() {
		return pipe2;
	}

	public Pipe getPipe3() {
		return pipe3;
	}

	public int getPoints() {
		return points;
	}

}
