/*
 * Cria partículas de brilho quando o jogador toca em uma entidade coletável.
 */
package jogo.entidades.particulas;

import java.util.Random;

import jogo.tela.Arte;
import jogo.tela.Tela;

/**
 * Cria partículas de brilho quando o jogador toca em uma entidade coletável.
 */
public class Brilho extends Particula {
    
    /** Duração da partícula. */
    private int duracao;
    
    /** The aleatorio. */
    private Random aleatorio = new Random();

    /**
     * Instancia uma nova partícula de brilho.
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