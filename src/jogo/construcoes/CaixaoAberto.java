/*
 *  Caixão cosmético, que não gera inimigos.
 */

package jogo.construcoes;

import jogo.entidades.colecionaveis.Tesouro;
import jogo.tela.*;

/**
 * Caixão cosmético, que não gera inimigos.
 */
public class CaixaoAberto extends Construcao {
	
    /**
     * Instancia um novo caixão.
     *
     * @param x 
     * @param y 
     */
    public CaixaoAberto(double x, double y) {
        super(x, y);
        imortal = false;
        vidaInicial(5);
        alinhamento = 2;
        tamanho(15, 15);
    }

    public boolean usavel() {
        return false;
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

    public Bitmap getSprite() {
        return Arte.caixaoAberto;
    }
}
