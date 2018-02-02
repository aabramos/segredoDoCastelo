/*
 * Torna aleat�ria a anima��o de sprites de explos�es ao redor da bomba.
 */
package jogo.entidades.animacoes;

import java.util.Random;
import jogo.entidades.Entidade;
import jogo.tiles.Tile;

/**
 * A classe DinamiteExplosaoGrande, que randomiza a anima��o de uma explos�o pequena.
 */
public class DinamiteExplosaoGrande extends Animacao {
	
	/** A vari�vel aleatorio. */
	private static Random aleatorio = new Random();
	
    /**
     * Instancia uma explos�o de uma dinamite grande.
     *
     * @param x 
     * @param y 
     */
    public DinamiteExplosaoGrande(double x, double y) {
        super(x, y, aleatorio.nextInt(10) + 30);
    }

    /** Determina a posi��o aleat�ria da anima��o das explos�es. */
    @Override
    public void frame() {
        super.frame();
        
        double dir = aleatorio.nextDouble() * Math.PI * 2;
        
        double dist = aleatorio.nextDouble() * ((32 - vida * 32 / duracao) + 16);

        double x = pos.x + Math.cos(dir) * dist;
        double y = pos.y + Math.sin(dir) * dist;

        if (duracao > vida)
        	nivel.adicionarEntidade(new DinamiteExplosao(x, y));

        if (vida == 25) {
            for (Entidade e : nivel.getEntidades(getFronteiras().aumentar(4 * 32))) {
                e.dinamite(this);
            }
            int xt = (int) (pos.x / Tile.LARGURA);
            int yt = (int) (pos.y / Tile.LARGURA);
            int r = 2;
            for (int yy = yt - r; yy <= yt + r; yy++) {
                for (int xx = xt - r; xx <= xt + r; xx++) {
                    nivel.getTile(xx, yy).dinamite(this);
                }
            }
        }
    }
}
