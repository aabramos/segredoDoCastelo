/*
 *  Determina o funcionamento de um pergaminho no jogo.
 */

package jogo.construcoes.portugues;

import jogo.construcoes.Construcao;
import jogo.tela.*;

/**
 * A classe PergaminhoEstaticoO, um pergaminho que aparece quando 
 * o PergaminhoO � ativado.
 */
public class PergaminhoEstaticoO extends Construcao {
	
    /**
     * Instancia um novo Pergaminho Est�tico.
     *
     * @param x 
     * @param y 
     */
    public PergaminhoEstaticoO(double x, double y) {
        super(x, y);
        imortal = true;
        bloqueia = false;
        alinhamento = 2;
        tamanho(7, 7);
    }

    public Bitmap getSprite() {
        return Arte.pergaminhoO;
    }
    
    public boolean usavel() {
        return false;
    }
}
