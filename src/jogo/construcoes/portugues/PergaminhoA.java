/*
 *  Determina o funcionamento de um pergaminho no jogo.
 */

package jogo.construcoes.portugues;

import jogo.construcoes.Construcao;
import jogo.entidades.Entidade;
import jogo.tela.*;

/**
 * A classe PergaminhoA, um pergaminho que � carreg�vel pelo personagem no jogo.
 */
public class PergaminhoA extends Construcao {
	
    /**
     * Instancia um novo Pergaminho.
     *
     * @param x
     * @param y
     */
    public PergaminhoA(double x, double y) {
        super(x, y);
        imortal = true;
        alinhamento = 2;
        tamanho(7, 7);
    }
    
    public void usar(Entidade jogador) {
    	if (!nivel.getPrimeiroPergaminho()){
    		nivel.setPrimeiroPergaminho(jogador.pos.x, jogador.pos.y);
    	} else
    		super.usar(jogador);
    }

    public Bitmap getSprite() {
        return Arte.pergaminhoA;
    }
}
