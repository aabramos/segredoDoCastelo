/*
 *  Determina o funcionamento da letra A em libras no jogo.
 */

package jogo.construcoes.letras;

import jogo.construcoes.Construcao;
import jogo.tela.*;

/**
 * A classe Aestatico, uma letra A que aparece quando o BotaoA é ativado.
 */
public class Aestatico extends Construcao {
	
    /**
     * Instancia um novo Aestatico.
     *
     * @param x 
     * @param y 
     */
    public Aestatico(double x, double y) {
        super(x, y);
        imortal = true;
        alinhamento = 2;
        tamanho(7, 7);
    }

    public Bitmap getSprite() {
        return Arte.AEstatico;
    }
    
    public boolean usavel() {
        return false;
    }
}
