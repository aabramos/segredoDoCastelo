/*
 * Define fronteiras de colisão para um objeto.
 */

package jogo.logica;

/**
 * Define fronteiras de colisão para um objeto.
 */
public class Fronteiras {
	
	public double x0, y0;
	public double x1, y1;
	
	// Objeto origem a ser calculada a fronteira.
	public OrigemColisao origem;

	/**
	 * Instancia uma nova fronteira.
	 *
	 * @param origem 
	 * @param x0
	 * @param y0 
	 * @param x1 
	 * @param y1 
	 */
	public Fronteiras(OrigemColisao origem, double x0, double y0, double x1, double y1) {
		this.origem = origem;
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}

	/**
	 * Interseção entre objetos.
	 *
	 * @param xx0 the xx0
	 * @param yy0 the yy0
	 * @param xx1 the xx1
	 * @param yy1 the yy1
	 * @return true, se houver interseção
	 */
	public boolean intersecao(double xx0, double yy0, double xx1, double yy1) {
		if (xx0 >= x1 || yy0 >= y1 || xx1 <= x0 || yy1 <= y0)
			return false;
		return true;
	}

	public Fronteiras aumentar(double s) {
		return new Fronteiras(origem, x0 - s, y0 - s, x1 + s, y1 + s);
	}

	/**
	 * Retorna se as fronteiras dos objetos se interceptam.
	 *
	 * @param fronteira
	 * @return true, se verdadeiro
	 */
	public boolean seInterceptar(Fronteiras fronteira) {
		if (fronteira.x0 >= x1 || fronteira.y0 >= y1 || fronteira.x1 <= x0 || fronteira.y1 <= y0)
			return false;
		return true;
	}
}