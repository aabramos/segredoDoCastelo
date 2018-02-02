/*
 *  Determina o funcionamento de um pergaminho no jogo.
 */

package jogo.construcoes.portugues;

import jogo.construcoes.Construcao;
import jogo.tela.*;

/**
 * A classe PergaminhoEstaticoA, um pergaminho que aparece quando o 
 * PergaminhoA � ativado.
 */
public class PergaminhoEstaticoA extends Construcao {
	
    /**
     * Instancia um novo Pergaminho est�tico.
     *
     * @param x 
     * @param y 
     */
    public PergaminhoEstaticoA(double x, double y) {
        super(x, y);
        imortal = true;
        bloqueia = false;
        alinhamento = 2;
        tamanho(7, 7);
    }

    public Bitmap getSprite() {
        return Arte.pergaminhoA;
    }
    
    public boolean usavel() {
        return false;
    }
}
