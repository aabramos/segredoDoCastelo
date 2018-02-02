/*
 * Animação de determinados sprites.
 */

package jogo.entidades.animacoes;

import jogo.entidades.Entidade;

/**
 * A classe Animacao, que determina a animação de determinados sprites.
 */
public class Animacao extends Entidade { 
    /** Define vida da animação, ou seja, se a vida acabar, a animação para. */
	int vida;
    /** Tempo de duração da animação. */
    int duracao;

    /**
     * Instancia uma nova animacao.
     *
     * @param x
     * @param y
     * @param duracao
     */
    public Animacao(double x, double y, int duracao) {
    	/** Manutenção da posição que vai executar a animação */
    	setPos(x, y);
    	/** A animação não bloqueia entidades */
    	bloqueia = false;
    	/** Impede que a animação seja movimentada com a colisão de tiros */
    	moverComColisao = false;
        this.duracao = vida = duracao;
    }
    
    /** A cada frame diminuir a vida até parar a animação. */
    public void frame() {
    	/** Remover animação quando terminar */
        if (--vida < 0) remover();
    }
}