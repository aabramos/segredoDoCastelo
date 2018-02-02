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
public class GestoIr extends Construcao {
	
    /**
     * Instancia uma classe que cuida de um quadro de gestos.
     *
     * @param x 
     * @param y 
     */
    public GestoIr(double x, double y) {
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
		nivel.setDialogo(8, jogador.pos.x, jogador.pos.y);
    }

    public Bitmap getSprite() {
        return Arte.gestoQuadro;
    }
}
