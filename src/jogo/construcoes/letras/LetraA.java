/*
 *  Determina o funcionamento da letra A em libras no jogo.
 */

package jogo.construcoes.letras;

import jogo.construcoes.Construcao;
import jogo.entidades.Entidade;
import jogo.tela.*;

/**
 * A classe LetraA, uma letra que é carregável pelo personagem no jogo.
 */
public class LetraA extends Construcao {
	
    /**
     * Instancia uma nova letraA.
     *
     * @param x
     * @param y
     */
    public LetraA(double x, double y) {
        super(x, y);
        imortal = true;
        alinhamento = 2;
        tamanho(7, 7);
    }
    
    public void usar(Entidade jogador) {
    	if (!nivel.getPrimeiraLetra(15)){
    		nivel.setPrimeiraLetra(15, jogador.pos.x, jogador.pos.y);
    	} else
    		super.usar(jogador);
    }

    public Bitmap getSprite() {
        return Arte.letraA;
    }
}
