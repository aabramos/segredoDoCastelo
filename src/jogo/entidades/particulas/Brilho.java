/*
 * Cria part�culas de brilho quando o jogador toca em uma entidade colet�vel.
 */
package jogo.entidades.particulas;

import java.util.Random;

import jogo.tela.Arte;
import jogo.tela.Tela;

/**
 * Cria part�culas de brilho quando o jogador toca em uma entidade colet�vel.
 */
public class Brilho extends Particula {
    
    /** Dura��o da part�cula. */
    private int duracao;
    
    /** The aleatorio. */
    private Random aleatorio = new Random();

    /**
     * Instancia uma nova part�cula de brilho.
     *
     * @param x
     * @param y 
     * @param xa
     * @param ya
     */
    public Brilho(double x, double y, double xa, double ya) {
        super(x, y, xa, ya);
        duracao = (vida = aleatorio.nextInt(10) + 20) + 1;
    }

    public void renderizar(Tela tela) {
        int anim = Arte.brilho.length - vida * Arte.brilho.length / duracao - 1;
        tela.plotar(Arte.brilho[anim][0], pos.x - 8, pos.y - 8 - 4 - z);
    }
}