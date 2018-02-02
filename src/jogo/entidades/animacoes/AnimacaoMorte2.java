/*
 * Instancia a anima��o de morte.
 */

package jogo.entidades.animacoes;

import jogo.tela.Arte;
import jogo.tela.Tela;


/**
 * A classe AnimacaoMorte2, que anima o efeito ap�s morte de entidades
 * como fantasmas.
 */
public class AnimacaoMorte2 extends Animacao {
    
    /**
     * Instancia uma anima��o de morte.
     *
     * @param x
     * @param y
     */
    public AnimacaoMorte2(double x, double y) {
        super(x, y, 40);
    }

    public void renderizar(Tela tela) {
        int anim = Arte.inimigoMorre2.length - vida * Arte.inimigoMorre2.length / duracao - 1;
        tela.plotar(Arte.inimigoMorre2[anim][0], pos.x - 16, pos.y - 32 - 4);
    }
}
