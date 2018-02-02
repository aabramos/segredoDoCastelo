/*
 * Define as partículas no jogo.
 */
package jogo.entidades.particulas;

import java.util.Random;
import jogo.entidades.Entidade;

/**
 * A classe Partícula, que define as partículas do jogo.
 */
public class Particula extends Entidade {
    
	/** Variável z. */
    public double z;
    
    /** Variável vida, que define a duração da partícula */
    public int vida;
    
    /** Posicionamento vetores x, y e z */
    private double xa, ya, za;
    
    /** Variável aleatório. */
    private Random aleatorio = new Random();

    /**
     * Instancia uma nova partícula.
     *
     * @param x 
     * @param y 
     * @param xa 
     * @param ya
     */
    public Particula(double x, double y, double xa, double ya) {
        pos.set(x, y);
        double pow = aleatorio.nextDouble() * 1 + 1;
        this.xa = xa * pow;
        this.ya = ya * pow;
        this.za = aleatorio.nextDouble() * 2 + 1.0;
        this.tamanho(2, 2);
        moverComColisao = false;
        bloqueia = false;
        vida = aleatorio.nextInt(20) + 50;
    }

    public void frame() {
        mover(xa, ya);
        z += za;
        if (z < 0) {
            z = 0;
            xa *= 0.8;
            ya *= 0.8;
        } else {
            xa *= 0.98;
            ya *= 0.98;
        }
        za -= 0.2;
        if (--vida < 0) remover();
    }
}