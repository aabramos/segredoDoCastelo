/*
 * Anima��o de determinados sprites.
 */

package jogo.entidades.animacoes;

import jogo.entidades.Entidade;

/**
 * A classe Animacao, que determina a anima��o de determinados sprites.
 */
public class Animacao extends Entidade { 
    /** Define vida da anima��o, ou seja, se a vida acabar, a anima��o para. */
	int vida;
    /** Tempo de dura��o da anima��o. */
    int duracao;

    /**
     * Instancia uma nova animacao.
     *
     * @param x
     * @param y
     * @param duracao
     */
    public Animacao(double x, double y, int duracao) {
    	/** Manuten��o da posi��o que vai executar a anima��o */
    	setPos(x, y);
    	/** A anima��o n�o bloqueia entidades */
    	bloqueia = false;
    	/** Impede que a anima��o seja movimentada com a colis�o de tiros */
    	moverComColisao = false;
        this.duracao = vida = duracao;
    }
    
    /** A cada frame diminuir a vida at� parar a anima��o. */
    public void frame() {
    	/** Remover anima��o quando terminar */
        if (--vida < 0) remover();
    }
}