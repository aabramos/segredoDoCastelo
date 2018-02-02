/*
 *  Determina o funcionamento da letra R em libras no jogo.
 */

package jogo.construcoes.letras;

import jogo.Main;
import jogo.construcoes.Construcao;
import jogo.entidades.Entidade;
import jogo.tela.*;

/**
 * A classe LetraR, uma letra que é carregável pelo personagem no jogo.
 */
public class LetraR extends Construcao {
	
    /**
     * Instancia uma nova letraR.
     *
     * @param x 
     * @param y 
     */
    public LetraR(double x, double y) {
        super(x, y);
        imortal = true;
        alinhamento = 2;
        tamanho(7, 7);
    }

    // A letra R faz parte do tutorial
    public void usar(Entidade jogador) {
    	if (!nivel.getPrimeiraLetra(17) && !Main.tutorial){
    		nivel.setPrimeiraLetra(17, jogador.pos.x, jogador.pos.y);
    	} else if (Main.tutorial && nivel.getDialogo() == 29){
    		super.usar(jogador);
			nivel.setDialogo(30, jogador.pos.x, jogador.pos.y);
    	} else
    		super.usar(jogador);
    }

    public Bitmap getSprite() {
        return Arte.letraR;
    }
}
