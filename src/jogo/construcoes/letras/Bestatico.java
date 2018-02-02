/*
 *  Determina o funcionamento da letra B em libras no jogo.
 */

package jogo.construcoes.letras;

import jogo.construcoes.Construcao;
import jogo.tela.*;

/**
 * A classe Bestatico, uma letra B que aparece quando o BotaoB é ativado.
 */
public class Bestatico extends Construcao {
	
    /**
     * Instancia um novo Bestatico.
     *
     * @param x 
     * @param y 
     */
    public Bestatico(double x, double y) {
        super(x, y);
        imortal = true;
        alinhamento = 2;
        tamanho(7, 7);
    }

    public Bitmap getSprite() {
        return Arte.BEstatico;
    }
    
    public boolean usavel() {
        return false;
    }
}
