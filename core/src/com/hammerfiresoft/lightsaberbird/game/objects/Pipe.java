package com.hammerfiresoft.lightsaberbird.game.objects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pipe {

	public static final int SCROLLSPEED = -75;
	public static final int VERTICAL_GAP = 56; // gap entre os tubos de cima e de baixo
	public static final int HORIZONTAL_GAP = 65; // gap entre os tubos adjacentes

	private Vector2 position; // posicao X para o scroll, y para o ponto do meio (altura) entre os tubos
	private Vector2 velocity; // velocidade do scroll
	private boolean scrolledOut; // se saiu da tela pelo lado esquerdo

	private Rectangle upperPipe; // representa o pipe superior (X identico ao position.x)
	private Rectangle lowerPipe; // representa o pipe inferior
	private boolean birdChecked; // se o bird passou por esse pipe. Resetado apos scrolledOut

	public Pipe(float x, float y, float upperTubeWidth, float upperTubeHeight, float lowerTubeWidth, float lowerTubeHeight) {
		this.velocity = new Vector2(SCROLLSPEED, 0);
		this.position = new Vector2(x, y); // y nao importa, usado apenas para fazer scroll no X

		upperPipe = new Rectangle(x, y + VERTICAL_GAP / 2, upperTubeWidth, upperTubeHeight);
		lowerPipe = new Rectangle(x, y - VERTICAL_GAP / 2 - lowerTubeHeight, lowerTubeWidth, lowerTubeHeight);
	}

	public void scrollLeft(float delta) {
		position.add(velocity.cpy().scl(delta));

		upperPipe.x = position.x;
		lowerPipe.x = position.x;

		if (position.x + upperPipe.width < 0) {
			scrolledOut = true;
		}
	}

	public boolean collides(LightSaber bird) {
		if (position.x < bird.getX() + bird.getWidth()) {
			return (Intersector.overlaps(bird.getCollisionBounds(), upperPipe) || Intersector.overlaps(bird.getCollisionBounds(), lowerPipe));
		}
		return false;
	}

	public float getX() {
		return position.x;
	}

	public float getTailX() {
		return position.x + upperPipe.width;
	}

	public boolean isScrolledOut() {
		return scrolledOut;
	}

	/**
	 * @return Pipe superior
	 */
	public Rectangle getUpper() {
		return upperPipe;
	}

	/**
	 * @return Pipe inferior
	 */
	public Rectangle getLower() {
		return lowerPipe;
	}

	public void resetXY(Pipe afterPipe, float newY) {
		scrolledOut = false;
		birdChecked = false;
		resetX(afterPipe);
		resetY(newY);
	}

	/**
	 * Coloca este pipe apos o pipe especificado no parametro
	 * 
	 * @param afterPipe
	 */
	private void resetX(Pipe afterPipe) {
		position.x = afterPipe.getTailX() + HORIZONTAL_GAP;
	}

	/**
	 * Configura nova posicao Y.
	 * 
	 * @param newY
	 */
	private void resetY(float newY) {
		upperPipe.y = newY + VERTICAL_GAP / 2;
		lowerPipe.y = newY - VERTICAL_GAP / 2 - lowerPipe.height;
	}

	/**
	 * Verifica se o passaro passou pelo tubo (checkpoint)
	 * @param bird
	 * @return
	 */
	public boolean hasBirdPassedThroughAndCheckpoint(LightSaber bird) {
		if (!this.scrolledOut && !this.birdChecked && this.position.x < bird.getX()) {
			birdChecked = true;
			return true;
		}
		return false;
	}

}
