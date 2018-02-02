/*
 *  Determina um elemento de cenário no jogo.
 */

package jogo.construcoes;

import jogo.entidades.colecionaveis.Tesouro;
import jogo.tela.*;

/**
 * Determina um elemento de cenário no jogo.
 */
public class Madeiras extends Construcao {

    public Madeiras(double x, double y) {
        super(x, y);
        imortal = false;
        vidaInicial(3);
    }

    public void morrer() {
    	super.morrer();
	    int particulas = 8;
	    int quantidade = 2 + (int)Math.random()*5;
	    for (int i = 0; i < quantidade; i++) {
	    	
	        double dir = i * Math.PI * 2 / particulas;
	        nivel.adicionarEntidade(new Tesouro(pos.x, pos.y, Math.cos(dir), Math.sin(dir), quantidade));
	    }
    }
    
    public boolean usavel() {
    	return false;
    }

    public Bitmap getSprite() {
        return Arte.madeiras;
    }
}
