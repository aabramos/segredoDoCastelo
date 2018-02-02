/*
 * A classe que define o inimigo Aranha.
 */

package jogo.entidades.personagens;

import java.util.Random;

import jogo.Main;
import jogo.entidades.Entidade;
import jogo.entidades.animacoes.AnimacaoMorte2;
import jogo.entidades.colecionaveis.Coracao;
import jogo.entidades.colecionaveis.Tesouro;
import jogo.tela.*;

/**
 * A classe que define o inimigo Aranha.
 */
public class Aranha extends Personagem {
    
    private int olhando;
    private int tempoAndar;
    private int tempoPasso;
    private Random aleatorio = new Random();

    /**
     * Instancia uma nova Aranha.
     *
     * @param x
     * @param y
     */
    public Aranha(double x, double y) {
        super(x, y);
        setPos(x, y);
        vidaInicial(6);
        direcao = aleatorio.nextDouble() * Math.PI * 2;
        alinhamento = 10;
        olhando = aleatorio.nextInt(4);
        dano = 3;
    }

    // Esquema de movimentação.
    public void frame() {
        super.frame();
        if (tempoParalizado > 0) return;

        double velocidade = 1.0;
        
        if (nivel.getPause() == false){
        	if (olhando == 0){
            	yc += velocidade;
            	xc -= velocidade;
            }
            if (olhando == 1){
            	yc -= velocidade;
            	xc -= velocidade;
            }
            if (olhando == 2){
            	yc -= velocidade;
            	xc += velocidade;
            }
            if (olhando == 3){
            	yc += velocidade;
            	xc += velocidade;
            }
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
        return Arte.aranha[((tempoPasso / 6) & 3)][(olhando + 3) & 3];
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

    public void morrer() {
        int particles = 8;
        
        if (getDano() > 0) {
            int loots = 4;
            for (int i = 0; i < loots; i++) {
                double dir = i * Math.PI * 2 / particles;
                nivel.adicionarEntidade(new Tesouro(pos.x, pos.y, Math.cos(dir), Math.sin(dir), getDano()));
            }
        }
        nivel.adicionarEntidade(new AnimacaoMorte2(pos.x, pos.y));
        
        int chance = aleatorio.nextInt(11);
        if (chance == 10)
        	nivel.adicionarEntidade(new Coracao(pos.x, pos.y,Math.cos(direcao), Math.sin(direcao)));
        
        Main.sons.reproduzirSom("/sons/aranha_morre.wav", (float) pos.x, (float) pos.y);
        nivel.subInimigo();
    }
}
