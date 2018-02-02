/*
 *  Determina o funcionamento da letra B em libras no jogo.
 */

package jogo.construcoes.letras;

import jogo.construcoes.Construcao;
import jogo.entidades.Entidade;
import jogo.tela.*;

/**
 * A classe LetraB, uma letra que é carregável pelo personagem no jogo.
 */
public class LetraB extends Construcao {
	
    /**
     * Instancia uma nova letraB.
     *
     * @param x 
     * @param y 
     */
    public LetraB(double x, double y) {
        super(x, y);
        imortal = true;
        alinhamento = 2;
        tamanho(7, 7);
    }

    public void usar(Entidade jogador) {
    	if (!nivel.getPrimeiraLetra(16)){
    		nivel.setPrimeiraLetra(16, jogador.pos.x, jogador.pos.y);
    	} else
    		super.usar(jogador);
    }

    public Bitmap getSprite() {
        return Arte.letraB;
    }
}
