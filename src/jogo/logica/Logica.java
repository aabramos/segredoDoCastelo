/*
 * Lógica do jogo.
 */

package jogo.logica;

/**
 * Lógica do jogo.
 */
public class Logica {

	/** Coordenadas */
	public double x, y;

	/**
	 * Instancia a lógica do jogo.
	 */
	public Logica() {
		x = y = 0;
	}

	/**
	 * Instancia a lógica do jogo.
	 *
	 * @param x 
	 * @param y
	 */
	public Logica(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Distância ao quadrado.
	 *
	 * @param to 
	 * @return distância ao quadrado.
	 */
	public double distAoQuadrado(Logica to) {
		double xd = x - to.x;
		double yd = y - to.y;
		return xd * xd + yd * yd;
	}

	/**
	 * Cálculo da distância.
	 *
	 * @param pos 
	 * @return distância
	 */
	public double dist(Logica pos) {
		return Math.sqrt(distAoQuadrado(pos));
	}

	public Logica clone() {
		return new Logica(x, y);
	}

	/**
	 * Adiciona duas coordenadas.
	 *
	 * @param c 
	 * @return Coordenadas
	 */
	public Logica adicionar(Logica c) {
		return new Logica(x + c.x, y + c.y);
	}

	/**
	 * Set coordenadas.
	 *
	 * @param x 
	 * @param y 
	 */
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Comprimento elevado ao quadrado.
	 *
	 * @return Comprimento elevado ao quadrado.
	 */
	private double comprimentoAoQuadrado() {
		return x * x + y * y;
	}

	/**
	 * Comprimento.
	 *
	 * @return Comprimento.
	 */
	private double comprimento() {
		return Math.sqrt(comprimentoAoQuadrado());
	}

	/**
	 * Normalizar.
	 *
	 * @return coordenadas normalizadas
	 */
	public Logica normalizar() {
		double nf = 1 / comprimento();
		x *= nf;
		y *= nf;
		return this;
	}

	/**
	 * Escala.
	 *
	 * @param e 
	 * @return Escala
	 */
	public Logica escala(double e) {
		return new Logica(x * e, y * e);
	}
}