/*
 * A classe que define o inimigo Morcego.
 */

package jogo.entidades.personagens;

import java.util.Random;
import jogo.entidades.Entidade;
import jogo.entidades.personagens.Morcego;
import jogo.tela.*;

/**
 * A classe que define o inimigo Morcego.
 */
public class Morcego extends Personagem {
    
    private int frame = 0;
    private Random aleatorio = new Random();

    /**
     * Instancia um novo Morcego.
     *
     * @param x
     * @param y
     */
    public Morcego(double x, double y) {
        super(x, y);
        setPos(x, y);
        vidaInicial(1);
        direcao = aleatorio.nextDouble() * Math.PI * 2;
        alinhamento = 5;
        dano = 1;
    }

    // O movimento é senoidal e cossenoidal.
    public void frame() {
        super.frame();
        if (tempoParalizado > 0) 
        	return;

        frame++;
        direcao += (aleatorio.nextDouble() - aleatorio.nextDouble()) * 0.2;
        if (nivel.getPause() == false){
	        xc += Math.cos(direcao) * 1;
	        yc += Math.sin(direcao) * 1;
	        if (!mover(xc, yc)) {
	            direcao += (aleatorio.nextDouble() - aleatorio.nextDouble()) * 0.8;
	        }
	        xc *= 0.2;
	        yc *= 0.2;
        }
    }

    public Bitmap getSprite() {
        return Arte.morcego[(frame / 3) & 3][0];
    }
    
    public void morrer () {
    	super.morrer();
    	nivel.subInimigo();
    }

    @Override
    public void renderizar(Tela tela) {
        if (frame % 2 == 0) tela.plotar(Arte.sombraMorcego, pos.x - Arte.sombraMorcego.base / 2, pos.y - Arte.sombraMorcego.altura / 2 - alinhamento + 16);
        super.renderizar(tela);
    }
    
    public void colisao(Entidade entidade, double xa, double ya) {
        super.colisao(entidade, xa, ya);

        if (entidade instanceof Jogador) {
            Jogador jogador = (Jogador) entidade;
            if (inimigo(jogador)) {
                jogador.ferir(this, dano);
            }
        }
    }
}
