/*
 *  Determina o funcionamento da letra R em libras no jogo.
 */

package jogo.construcoes.letras;

import jogo.construcoes.Construcao;
import jogo.tela.*;

/**
 * A classe Restatico, uma letra R que aparece quando o BotaoR é ativado.
 */
public class Restatico extends Construcao {
	
    /**
     * Instancia um novo Restatico.
     *
     * @param x 
     * @param y 
     */
    public Restatico(double x, double y) {
        super(x, y);
        imortal = true;
        alinhamento = 2;
        tamanho(7, 7);
    }

    public Bitmap getSprite() {
        return Arte.REstatico;
    }

    public boolean usavel() {
        return false;
    }
}
