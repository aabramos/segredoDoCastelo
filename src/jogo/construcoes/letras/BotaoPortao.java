/*
 * Determina um botão ativado por um pergaminho em Português.
 */

package jogo.construcoes.letras;

import jogo.construcoes.Construcao;
import jogo.tela.Arte;
import jogo.tela.Bitmap;

/**
 * A classe BotaoPorta, que cria um botão ativado por um pergaminho em Português.
 */
public class BotaoPortao extends Construcao {
	
	/**
	 * Instancia um novo botão.
	 *
	 * @param x 
	 * @param y
	 */
	public BotaoPortao(double x, double y) {
		super(x, y);
        imortal = true;
        bloqueia = false;
        // Botao está no chão
        alinhamento = -24;
	}
	
    public boolean usavel() {
        return false;
    }

    public Bitmap getSprite() {
        return Arte.botaoPortao;
    }
}
