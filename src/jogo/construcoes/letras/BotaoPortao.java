/*
 * Determina um bot�o ativado por um pergaminho em Portugu�s.
 */

package jogo.construcoes.letras;

import jogo.construcoes.Construcao;
import jogo.tela.Arte;
import jogo.tela.Bitmap;

/**
 * A classe BotaoPorta, que cria um bot�o ativado por um pergaminho em Portugu�s.
 */
public class BotaoPortao extends Construcao {
	
	/**
	 * Instancia um novo bot�o.
	 *
	 * @param x 
	 * @param y
	 */
	public BotaoPortao(double x, double y) {
		super(x, y);
        imortal = true;
        bloqueia = false;
        // Botao est� no ch�o
        alinhamento = -24;
	}
	
    public boolean usavel() {
        return false;
    }

    public Bitmap getSprite() {
        return Arte.botaoPortao;
    }
}
