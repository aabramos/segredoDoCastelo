/*
 * Define o chão no jogo.
 */

package jogo.tiles;

import jogo.Nivel;
import jogo.tela.Arte;

/**
 * Define o chão no jogo.
 */
public class Chao extends Tile {

    private int orgImg;
    private int indice = 0;
    
    // Oeste|Norte|Leste Binário
    private static final int[] sombra = new int[] {
            -1, // 000
            3, // 001
            5, // 010
            6, // 011
            2, // 100
            0, // 101
            4, // 110
            1, // 111
    };
    
    public void setIndice(int index) {
    	this.indice = index;
    }

    public void iniciar(Nivel nivel, int x, int y) {
        super.iniciar(nivel, x, y);
        vizinhoMudou(null);
        orgImg = img;
    }

    public void vizinhoMudou(Tile tile) {
        final Tile o = nivel.getTile(x - 1, y);
        final Tile n = nivel.getTile(x, y - 1);
        final Tile s = nivel.getTile(x, y + 1);
        final Tile l = nivel.getTile(x + 1, y);
        if (o != null && o.produzirSombra()) indice |= 0x4;
        if (n != null && n.produzirSombra()) indice |= 0x2;
        if (l != null && l.produzirSombra()) indice |= 0x1;

        img = orgImg;
        if (n instanceof AreaInacessivel) {
            img = 4 + 8;
            indice = 0;
        }
        if (s instanceof AreaInacessivel) {
            img = 5 + 8;
            indice = 0;
        }

        if (indice > 0) {
            int indiceImagem = sombra[indice];
            if (indiceImagem < 4) 
            img = Arte.piso.length + (indiceImagem & 3);
        else 
            img = 2 * Arte.piso.length + (indiceImagem & 3);
        }
        corMinimapa = Arte.corPiso[img & 7][img / 8];
    }

    public boolean construivel() {
        return true;
    }
}
