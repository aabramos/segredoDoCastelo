/*
 * Animação de explosão em um tile ou entidade.
 */

package jogo.entidades.animacoes;

import java.util.Random;

/**
 * A classe AnimacaoExplosao, que anima uma explosão em um tile ou entidade.
 */
public class AnimacaoExplosao extends Animacao {
	
	/** A variável aleatorio. */
	private static Random aleatorio = new Random();
	
    /**
     * Instancia a animação de explosão em um tile ou entidade.
     *
     * @param x
     * @param y
     */
    public AnimacaoExplosao(double x, double y) {
        super(x, y, aleatorio.nextInt(10) + 20);
    }

    @Override
    public void frame() {
        super.frame();
        // Posição aleatória dos sprites
        double x = pos.x + aleatorio.nextDouble() * 32 - 16;
        double y = pos.y + aleatorio.nextDouble() * 32 - 16;
        double z = aleatorio.nextDouble() * 24;
        nivel.adicionarEntidade(new DinamiteExplosaoPequena(x, y, z));
    }
}
