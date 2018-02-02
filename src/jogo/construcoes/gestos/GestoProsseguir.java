/*
 *  Classe que cuida de um quadro de gestos.
 */

package jogo.construcoes.gestos;

import jogo.construcoes.Construcao;
import jogo.entidades.Entidade;
import jogo.tela.*;

/**
 * Classe que cuida de um quadro de gestos.
 */
public class GestoProsseguir extends Construcao {
	
    /**
     * Classe que cuida de um quadro de gestos.
     *
     * @param x 
     * @param y 
     */
    public GestoProsseguir(double x, double y) {
        super(x, y);
        imortal = true;
        bloqueia = false;
        alinhamento = 2;
        tamanho(7, 7);
    }
    
    public boolean usavel() {
        return true;
    }
    
    public void usar(Entidade jogador) {
		nivel.setDialogo(10, jogador.pos.x, jogador.pos.y);
    }

    public Bitmap getSprite() {
        return Arte.gestoQuadro;
    }
}
