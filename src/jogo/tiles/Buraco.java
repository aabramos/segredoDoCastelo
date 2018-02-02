/*
 * Define os buracos do jogo.
 */

package jogo.tiles;

import jogo.Nivel;
import jogo.entidades.Bala;
import jogo.entidades.Entidade;
import jogo.entidades.colecionaveis.Coracao;
import jogo.entidades.colecionaveis.Tesouro;
import jogo.entidades.personagens.Morcego;
import jogo.entidades.personagens.Vampiro;
import jogo.tela.Arte;
import jogo.tela.Tela;

/**
 * Define os buracos do jogo.
 */
public class Buraco extends Tile {

    public void iniciar(Nivel nivel, int x, int y) {
        super.iniciar(nivel, x, y);
        img = 4;
        corMinimapa = Arte.corPiso[img & 7][img / 8];
    }

    public void renderizar(Tela tela) {
        if (y > 0 && !(nivel.getTile(x, y - 1) instanceof Buraco)) {
            super.renderizar(tela);
        } else {
            tela.preecher(x * Tile.LARGURA, y * Tile.ALTURA, Tile.LARGURA, Tile.ALTURA, 0xff000000);
        }
    }

    public boolean construivel() {
        return false;
    }
    
    public void manipulaColisao(Entidade entidade, double xa, double ya) {
    	// Remover o tesouro se cair em um buraco
    	if (entidade instanceof Tesouro){
    		((Tesouro) entidade).remover();
    	} else if (entidade instanceof Coracao){
    		((Coracao) entidade).remover();
    	}
    }

    // Entidades que podem ultrapassar o buraco
    public boolean ultrapassavel(Entidade e) {
    	if (e instanceof Bala){
    		return (e instanceof Bala);
    	} else if (e instanceof Morcego){
    		return (e instanceof Morcego);
    	} else if (e instanceof Vampiro){
    		return (e instanceof Vampiro);
    	} 
		return false;	
    }
}
