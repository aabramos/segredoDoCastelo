/*
 * A classe constru��o � respons�vel por manusear qualquer constru��o 
 * existente no jogo.
 */
package jogo.construcoes;

import java.util.Random;

import jogo.entidades.*;
import jogo.entidades.personagens.Jogador;
import jogo.entidades.personagens.Personagem;
import jogo.logica.Fronteiras;
import jogo.tela.*;

/**
 * A classe constru��o, respons�vel pelas constru��es existentes no jogo. 
 * As outras classes do pacote constru��es herdam dela.
 */
public class Construcao extends Personagem implements UsarInterface {
    
    /** A constante de cria��o de monstros */
    private static final int INTERVALO_CRIACAO = 60;

    /** A vari�vel aleatorio. */
    private Random aleatorio = new Random();
    
    /** Vari�vel respons�vel pelo destaque visual em itens us�veis */
    private boolean destaque = false;

    /**
     * Instancia uma nova constru��o.
     *
     * @param x 
     * @param y 
     */
    public Construcao(double x, double y) {
        super(x, y);
        vidaInicial(20);
        tempoParalizado = 10;
        aleatorio.nextInt(INTERVALO_CRIACAO);
    }

    @Override
    public void renderizar(Tela tela) {
        super.renderizar(tela);
        marcadorRenderizacao(tela);
    }
    
    public Construcao getConstrucao() {
    	return this;
    }
    
    /**
     * Renderizar marcador de item us�vel
     *
     * @param tela
     */
    private void marcadorRenderizacao(Tela tela) {
        if (destaque) {
            Fronteiras bb = getFronteiras();
            bb = bb.aumentar((getSprite().base - (bb.x1 - bb.x0)) / (3 + Math.sin(System.currentTimeMillis() * .01)));
            int largura = (int) (bb.x1 - bb.x0);
            int altura = (int) (bb.y1 - bb.y0);
            Bitmap marcador = new Bitmap(largura, altura);
            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    if ((x < 2 || x > largura - 3 || y < 2 || y > altura - 3) && (x < 5 || x > largura - 6) && (y < 5 || y > altura - 6)) {
                        int i = x + y * largura;
                        marcador.pixels[i] = 0xffffffff;
                    }
                }
            }
            tela.plotar(marcador, bb.x0, bb.y0 - 4);
        }
    }

    public void frame() {
        super.frame();
        if (tempoParalizado > 0) {
            return;
        }
        if (tempoFerido <= 0) 
        	vida = vidaMaxima;

        xc = 0.0;
        yc = 0.0;
    }

    public boolean mover(double colisaoX, double colisaoY) {
        return false;
    }

    public void deslizar(double xa, double ya) {
        super.mover(xa, ya);
    }

    public void usar(Entidade jogador) {
        if (jogador instanceof Jogador) {
            ((Jogador) jogador).carregar(this);
        }
    }

    public boolean usavel() {
        return true;
    }

    public void destacar(boolean dest) {
        destaque = dest;
    }

	@Override
	public Bitmap getSprite() {
		return null;
	}
}
