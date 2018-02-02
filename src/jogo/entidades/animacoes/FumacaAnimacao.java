/*
 * Anima a fuma�a que sai do p� do protagonista quando ele anda.
 */

package jogo.entidades.animacoes;

import jogo.entidades.personagens.Personagem;
import jogo.tela.Bitmap;
import jogo.tela.Tela;

/**
 * A classe FumacaAnimacao, que anima uma fuma�a ao andar do personagem.
 */
public class FumacaAnimacao extends Animacao {
	
	/** Respons�vel por carregar o sprite. */
	private Bitmap[][] bitmap;
	
	/** A largura. */
	private int largura;
	
	/** Quantidade de frames. */
	private int qtdeFrames;
	
	/** Vari�veis de posi��o. */
	private double xa, ya;
	
	/**
	 * Instancia uma anima��o de fuma�a ao andar.
	 *
	 * @param personagem
	 * @param bitmap
	 * @param duracao
	 */
	public FumacaAnimacao(Personagem personagem, Bitmap[][] bitmap, int duracao) {
		super(personagem.pos.x, personagem.pos.y, duracao);
		xa = personagem.xc * 0.5f;
		ya = personagem.yc * 0.5f;
		pos.x -= xa + 6;
		pos.y -= ya;
		this.bitmap = bitmap;
		largura = bitmap.length;
		qtdeFrames = largura * bitmap[0].length;
	}
	
	public void frame() {
		super.frame();
		mover(xa, ya);
		xa *= 0.9f;
		ya *= 0.9f;
	}
	
    public void renderizar(Tela tela) {
        int frame = qtdeFrames - (vida * qtdeFrames / duracao) - 1;
        tela.plotar(bitmap[frame % largura][frame / largura], pos.x, pos.y);
    }
}
