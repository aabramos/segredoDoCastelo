/*
 * Instancia a anima��o de morte.
 */

package jogo.entidades.animacoes;

import jogo.tela.Arte;
import jogo.tela.Tela;

/**
 * A classe AnimacaoMorte, que anima o efeito ap�s morte de alguma entidade.
 */
public class AnimacaoMorte extends Animacao {
    
    /**
     * Instancia uma anima��o de morte.
     *
     * @param x
     * @param y
     */
    public AnimacaoMorte(double x, double y) {
        super(x, y, 40);
    }

    public void renderizar(Tela tela) {
        int anim = Arte.inimigoMorre.length - vida * Arte.inimigoMorre.length / duracao - 1;
        tela.plotar(Arte.inimigoMorre[anim][0], pos.x - 32, pos.y - 32 - 4);
    }
}
