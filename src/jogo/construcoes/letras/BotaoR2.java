/*
 * Determina um botão ativado por LIBRAS. 
 */

package jogo.construcoes.letras;

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
 * A classe BotaoR2, que cria um botão ativado por LIBRAS.
 */
public class BotaoR2 extends Construcao {

	/** A entidade porta. */
	private Entidade porta = null;
	
	/** Status é 1 quando porta é aberta. */
	private int status = 0;
	
	/**
	 * Instancia um segundo botão da letra R.
	 *
	 * @param x 
	 * @param y 
	 */
	public BotaoR2(double x, double y) {
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
			porta = (new Porta(32 * (nivel.larguraNivel / 2 - .5), (nivel.alturaNivel - 37.5) * 32));
			nivel.adicionarEntidade(porta);
		}
		super.frame();
        if (--tempoParalizado > 0) 
        	return;

		tempoParalizado = 10;
		Set<Entidade> entities = nivel.getEntidades(pos.x -16.7, pos.y, pos.x + Tile.LARGURA -20, pos.y + Tile.ALTURA);
		// Quando o botão for ativado pela letra LIBRAS respectiva, destruir o botão
		// e a porta correspondente e criar uma letra estática.
		for (Entidade e : entities) {
			if (e instanceof LetraR && status == 0) {
				nivel.removerEntidade(porta);
				nivel.removerEntidade(e);
				Main.sons.reproduzirSom("/sons/botao.wav", (float) pos.x, (float) pos.y);
				nivel.adicionarEntidade(new Restatico(32 * (nivel.larguraNivel / 2 + 1.48), (nivel.alturaNivel - 28.75) * 32));
				nivel.setDialogoTempo(22);
				status = 1;
			}
		}
	}
	
    public boolean usavel() {
    	return false;
    }

    public Bitmap getSprite() {
        return Arte.botaoR;
    }
}
