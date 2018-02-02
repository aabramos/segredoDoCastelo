/*
 * Determina um botão ativado por um Pergaminho com a letra O.
 */

package jogo.construcoes.portugues;

import java.util.Set;

import jogo.Main;
import jogo.construcoes.Construcao;
import jogo.construcoes.Porta;
import jogo.entidades.Entidade;
import jogo.entidades.personagens.Personagem;
import jogo.tela.Arte;
import jogo.tela.Bitmap;
import jogo.tiles.Tile;

/**
 * A classe BotaoInterrogacaoO, que cria um botão ativado 
 * por um Pergaminho com a letra O.
 */
public class BotaoInterrogacaoO extends Construcao {

	/** A entidade porta. */
	private Entidade porta = null;
	
	/**
	 * Instancia um novo botão A.
	 *
	 * @param x 
	 * @param y
	 */
	public BotaoInterrogacaoO(double x, double y) {
		super(x, y);
        imortal = true;
        bloqueia = false;
        // Botao está no chão
        alinhamento = -24;
	}

    @Override
    public boolean inimigo(Personagem m) {
        return false;
    }
    
	public void frame() {

		// Adicionar uma porta vinculada ao botão.
		if (porta == null){
			porta = (new Porta(35.95*32 + 18.0, 14*32 + 10));
			nivel.adicionarEntidade(porta);
		}
		super.frame();
        if (--tempoParalizado > 0) 
        	return;

		tempoParalizado = 10;
		Set<Entidade> entities = nivel.getEntidades(pos.x -16.7, pos.y, pos.x + Tile.LARGURA -20, pos.y + Tile.ALTURA);
		for (Entidade e : entities) {
			// Quando o botão for ativado pelo pergaminho respectivo, destruir o botão
			// e a porta correspondente e criar um pergaminho estático.
			if (e instanceof PergaminhoO) {
				nivel.removerEntidade(porta);
				nivel.removerEntidade(e);
				Main.sons.reproduzirSom("/sons/botao.wav", (float) pos.x, (float) pos.y);
				nivel.adicionarEntidade(new PergaminhoEstaticoO(34*32 + 18.0, 15.7*32 + 10));
				nivel.setDialogoTempo(22);
			}
		}
	}

    public boolean usavel() {
        return false;
    }

    public Bitmap getSprite() {
        return Arte.botaoInterrogacao;
    }
}
