/*
 * Anima��o de explos�o da dinamite.
 */

package jogo.entidades.animacoes;

import java.util.Random;

import jogo.tela.Arte;
import jogo.tela.Bitmap;
import jogo.tela.Tela;

/**
 * A classe BombaExplosao, que anima a explos�o de uma dinamite.
 */
public class DinamiteExplosao extends Animacao {
	
	/** A vari�vel aleatorio. */
	private static Random aleatorio = new Random();
	
    /**
     * Instancia uma nova anima��o de explosao de uma bomba.
     * aleatorio.nextInt determina que as posi��es das explos�es ser�o aleat�rias.
     *
     * @param x 
     * @param y
     */
    public DinamiteExplosao(double x, double y) {
        super(x, y, aleatorio.nextInt(10) + 20);
    }

    public void renderizar(Tela tela) {
        Bitmap[][] bitmap = Arte.explosao;
        int anim = bitmap.length - vida * bitmap.length / duracao - 1;
        tela.plotar(bitmap[anim][0], pos.x - bitmap[0][0].base/2, pos.y - bitmap[0][0].altura/2 - 4);
    }
}
