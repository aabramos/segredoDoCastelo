/*
 * A classe que define o inimigo Aranha.
 */

package jogo.entidades.personagens;

import java.util.Random;

import jogo.Main;
import jogo.entidades.Entidade;
import jogo.tela.*;

/**
 * A classe que define o inimigo Aranha.
 */
public class Fantasma extends Personagem {
    
    private int olhando;
    private int tempoAndar;
    private int tempoPasso;
    private Random aleatorio = new Random();

    /**
     * Instancia um novo Fantasma.
     *
     * @param x
     * @param y
     */
    public Fantasma(double x, double y) {
        super(x, y);
        setPos(x, y);
        vidaInicial(4);
        direcao = aleatorio.nextDouble() * Math.PI * 2;
        alinhamento = 10;
        olhando = aleatorio.nextInt(4);
        dano = 2;
    }

    // Esquema de movimentação.
    public void frame() {
        super.frame();
        if (tempoParalizado > 0) return;

        double velocidade = 1.5;
        if (nivel.getPause() == false){
	        if (olhando == 0) yc += velocidade;
	        if (olhando == 1) xc -= velocidade;
	        if (olhando == 2) yc -= velocidade;
	        if (olhando == 3) xc += velocidade;
	        tempoAndar += 12;
        }
        if (tempoAndar / 12 % 4 != 0) {
            tempoPasso++;
            if (!mover(xc, yc) || (tempoAndar > 20 && aleatorio.nextInt(50) == 0)) {
                olhando = aleatorio.nextInt(4);
                tempoAndar = 0;
            }
        }
        xc *= 0.2;
        yc *= 0.2;
    }

    public Bitmap getSprite() {
        return Arte.fantasma[((tempoPasso / 6) & 3)][(olhando + 3) & 3];
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

    // Ao morrer, criar um FantasmaTonto
    public void morrer() {
        remover();
        Main.sons.reproduzirSom("/sons/fantasma_tonto.wav", (float) pos.x, (float) pos.y);
        nivel.adicionarEntidade(new FantasmaTonto(pos.x, pos.y));
    }
}
