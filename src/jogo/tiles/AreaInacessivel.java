/*
 * Define uma área inacessível pelo jogador.
 */

package jogo.tiles;

import jogo.Nivel;
import jogo.entidades.Entidade;
import jogo.tela.Arte;
import jogo.tela.Tela;

/**
 * Define uma área inacessível pelo jogador.
 */
public class AreaInacessivel extends Tile {
    
    public void iniciar(Nivel nivel, int x, int y) {
        super.iniciar(nivel, x, y);
        img = 5;
        corMinimapa = Arte.corPiso[img&7][img/8];        
    }
    
    public void renderizar(Tela tela) {
        super.renderizar(tela);
    }

    public boolean ultrapassavel(Entidade e) {
    	return false;
    }
}