/*
 *  Determina o funcionamento da letra I em libras no jogo.
 */

package jogo.construcoes.letras;

import jogo.construcoes.Construcao;
import jogo.entidades.Entidade;
import jogo.tela.*;

/**
 * A classe LetraI, uma letra que é carregável pelo personagem no jogo.
 */
public class LetraI extends Construcao {
	
    /**
     * Instancia uma nova letraI.
     *
     * @param x 
     * @param y 
     */
    public LetraI(double x, double y) {
        super(x, y);
        imortal = true;
        alinhamento = 2;
        tamanho(7, 7);
    }

    public void usar(Entidade jogador) {
    	if (!nivel.getPrimeiraLetra(18)){
    		nivel.setPrimeiraLetra(18, jogador.pos.x, jogador.pos.y);
    	} else
    		super.usar(jogador);
    }
    
    public Bitmap getSprite() {
        return Arte.letraI;
    }
}
