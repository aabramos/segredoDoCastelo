/*
 *  Determina um elemento de cen�rio no jogo.
 */

package jogo.construcoes;

import jogo.tela.*;

/**
 * Determina um elemento de cen�rio no jogo.
 */
public class Maquina extends Construcao {

    public Maquina(double x, double y) {
        super(x, y);
        imortal = true;
        tamanho(91, 38);
    }
    
    public boolean usavel() {
    	return false;
    }

    public Bitmap getSprite() {
        return Arte.maquina;
    }
}
