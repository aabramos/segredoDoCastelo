// Interface da colisao entre personagens

package jogo.logica;

import jogo.entidades.Entidade;

/**
 * Interface da colisao entre personagens
 */
public interface OrigemColisao {
	
	/**
	 * Manipula colisões.
	 *
	 * @param entidade
	 * @param xa
	 * @param ya
	 */
	void manipulaColisao(Entidade entidade, double xa, double ya);
}