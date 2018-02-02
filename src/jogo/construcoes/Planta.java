/*
 *  Cria uma planta no cenário do jogo
 */

package jogo.construcoes;

import jogo.tela.*;

/**
 * A classe Planta.
 */
public class Planta extends Construcao {
	
    /**
     * Instancia uma nova planta.
     *
     * @param x 
     * @param y 
     */
    public Planta(double x, double y) {
        super(x, y);
        imortal = true;
        bloqueia = false;
		//A planta deverá estar com alinhamento abaixo das outras entidades
        alinhamento = -24;
    }
    
    public boolean usavel() {
        return false;
    }

    public Bitmap getSprite() {
        return Arte.planta;
    }
}
