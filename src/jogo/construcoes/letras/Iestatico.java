/*
 *  Determina o funcionamento da letra I em libras no jogo.
 */

package jogo.construcoes.letras;

import jogo.construcoes.Construcao;
import jogo.tela.*;

/**
 * A classe Iestatico, uma letra I que aparece quando o BotaoI é ativado.
 */
public class Iestatico extends Construcao {
	
    /**
     * Instancia um novo Iestatico.
     *
     * @param x 
     * @param y 
     */
    public Iestatico(double x, double y) {
        super(x, y);
        imortal = true;
        alinhamento = 2;
        tamanho(7, 7);
    }

    public Bitmap getSprite() {
        return Arte.IEstatico;
    }

    public boolean usavel() {
        return false;
    }
}
