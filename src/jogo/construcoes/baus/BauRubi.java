/*
 * Determina o funcionamento de um baú no jogo 
 */

package jogo.construcoes.baus;

import jogo.Main;
import jogo.construcoes.Construcao;
import jogo.entidades.*;
import jogo.entidades.colecionaveis.*;
import jogo.tela.*;

/**
 * A classe BauRubi, que manuseia baús com tesouro no jogo.
 */
public class BauRubi extends Construcao implements UsarInterface {

	/** Mudar sprite se o baú for aberto */
	private int status = 0;

	/** O tempo de desaparecimento. */
	private int tempoDesaparecimento = 0;

	/** O baú está vazio? */
	private boolean vazio = false;
	
	public BauRubi(double x, double y) {
		super(x, y);
		imortal = true;
		tamanho(27, 10);
	}
	
	public Bitmap getSprite() {
		return Arte.bauRubi[status][0];
	}

	/**
	 * Depois de aberto o baú deverá desaparecer após determinado tempo
	 */
	public void frame() {
		super.frame();
		if (tempoDesaparecimento > 0) {
			if (--tempoDesaparecimento == 0 && vazio) {
				morrer();
				remover();
			}
		}
	}

	/**
	 * Cria o tesouro do baú após aberto.
	 */
	public void usar(Entidade usuario) {
		if (status == 0) {
			vazio = true;
			tempoDesaparecimento = 150;
			Main.sons.reproduzirSom("/sons/abrir_bau.wav", (float) pos.x,
					(float) pos.y);
			int particulas = 8;

			int quantidade = 1;
			for (int i = 0; i < quantidade; i++) {

				double dir = i * Math.PI * 2 / particulas;
				nivel.adicionarEntidade(new Rubi(pos.x, pos.y, Math.cos(dir),
						Math.sin(dir), quantidade));
			}
			status = 1;
		}
	}

	public boolean usavel() {
		if (status == 1)
			return false;
		return true;
	}
}