/*
 *  Determina um armário no jogo.
 */

package jogo.construcoes;

import jogo.Main;
import jogo.entidades.Entidade;
import jogo.entidades.colecionaveis.Tesouro;
import jogo.tela.*;

/**
 * A classe Armário.
 */
public class Armario extends Construcao {

    public Armario(double x, double y) {
        super(x, y);
        imortal = false;
        vidaInicial(4);
        alinhamento = 2;
        tamanho(19, 17);
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
    	if (Main.tutorial)
    		return false;
    	else
    		return true;
    }
    
    @Override
    public void usar(Entidade jogador) {
		nivel.setDialogo(12, jogador.pos.x, jogador.pos.y);
    }

    public Bitmap getSprite() {
        return Arte.armario;
    }
}
