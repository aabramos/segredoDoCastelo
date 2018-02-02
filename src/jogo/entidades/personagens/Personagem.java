/*
 * Classe que define os personagens do jogo.
 */
package jogo.entidades.personagens;

import java.util.Random;
import jogo.Main;
import jogo.entidades.*;
import jogo.entidades.animacoes.AnimacaoMorte;
import jogo.entidades.colecionaveis.Coracao;
import jogo.entidades.colecionaveis.Tesouro;
import jogo.tela.*;

/**
 * Classe que define os personagens do jogo.
 */
public abstract class Personagem extends Entidade {
	
	/** Diração que o personagem está andando */
	double direcao = 0;
    
    /** Aumentar um pouco a velocidade quando o jogador estiver carregando algo */
    private final static double VELOCIDADE_CARREGANDO = 1.2;
    
    /** Velocidade do personagem */
    private double velocidade = 1.0;
    
    /** Tempo que o personagem ficará em um estado ferido */
    public int tempoFerido = 0;
    
    /** Tempo que o personagem ficará paralizado após ser ferido */
    public int tempoParalizado = 0;
    
    /** Vida máxima. */
    public int vidaMaxima = 10;
    
    /** Vida. */
    public int vida = vidaMaxima;
    
    /** Define se um personagem nunca irá morrer. */
    public boolean imortal = false;
    
    /** Coordenadas de colisão */
    public double colisaoX, colisaoY;
    public Personagem carregando = null;
    
    /** Alinhamento */
    public int alinhamento = 8;
    
    /** Coordenadas de deslizamento */
    public double xSlide, ySlide;
    
    /** Dano que o personagem causa */
    public int dano = 0;
    private Random aleatorio = new Random();
  
    /**
     * Instancia um novo personagem
     *
     * @param x 
     * @param y
     */
    public Personagem(double x, double y) {
        setPos(x, y);
    }

    /**
     * Define a vida inicial.
     *
     * @param novaVida 
     */
    public void vidaInicial(int novaVida) {
        vidaMaxima = vida = novaVida;
    }

    public double getVelocidade() {
        return carregando != null ? velocidade * VELOCIDADE_CARREGANDO : velocidade;
    }
    
    public boolean inimigo(Personagem m) {
        return true;
    }

    public void frame() {
        if (tempoFerido > 0) {
            tempoFerido--;
        }

        if (tempoParalizado > 0) {
            deslizar(xSlide, ySlide);
            xSlide *= 0.8;
            ySlide *= 0.8;

            if (colisaoX != 0 || colisaoY != 0) {
                mover(colisaoX, colisaoY);
            }
            tempoParalizado--;
            return;
        } else {
            xSlide = ySlide = 0;
            if (vida <= 0) {
                morrer();
                remover();
                return;
            }
        }
    }

    public void deslizar(double xa, double ya) {
        mover(xa, ya);
    }

    /**
     * Morte do personagem.
     */
    public void morrer() {
        int particles = 8;

        if (getDano() > 0) {
            int tesouro = 4;
            for (int i = 0; i < tesouro; i++) {
                double dir = i * Math.PI * 2 / particles;
                nivel.adicionarEntidade(new Tesouro(pos.x, pos.y, Math.cos(dir), Math.sin(dir), getDano()));
            }
        }

        nivel.adicionarEntidade(new AnimacaoMorte(pos.x, pos.y));
        int chance = aleatorio.nextInt(10);
        if (chance == 9)
        	nivel.adicionarEntidade(new Coracao(pos.x, pos.y,Math.cos(direcao), Math.sin(direcao)));
        
        Main.sons.reproduzirSom(somPadraoMorte(), (float) pos.x, (float) pos.y);
    }

    public String somPadraoMorte() {
        return "/sons/explosao.wav";
    }

    public void renderizar(Tela tela) {
        Bitmap imagem = getSprite();
        if (tempoFerido > 0) {
            if (tempoFerido > 40 - 6 && tempoFerido / 2 % 2 == 0) {
                tela.blitCores(imagem, pos.x - imagem.base / 2, pos.y - imagem.altura / 2 - alinhamento, 0xa0ffffff);
            } else {
                if (vida < 0) vida = 0;
                int col = 180 - vida * 180 / vidaMaxima;
                if (tempoFerido < 10) col = col * tempoFerido / 10;
                tela.blitCores(imagem, pos.x - imagem.base / 2, pos.y - imagem.altura / 2 - alinhamento, (col << 24) + 255 * 65536);
            }
        } else {
            tela.plotar(imagem, pos.x - imagem.base / 2, pos.y - imagem.altura / 2 - alinhamento);
        }
        renderizarCarregando(tela, 0);
    }

    /**
     * Renderizar o sprite de carregar.
     *
     * @param tela 
     * @param yOffs
     */
    protected void renderizarCarregando(Tela tela, int yOffs) {
        if (carregando == null) return;
        Bitmap imagem = carregando.getSprite();
        tela.plotar(imagem, carregando.pos.x - imagem.base / 2, carregando.pos.y - imagem.altura + 8 + yOffs);
    }

    /**
     * Get sprite.
     *
     * @return sprite
     */
    public abstract Bitmap getSprite();

    @Override
    public void ferir(Bala bala) {
        if (imortal) return;

        if (tempoParalizado <= 0) {
            tempoFerido = 40;
            tempoParalizado = 5;
            if (bala.supertiro())
            	vida = 0;
            else
            	vida--;
            
            if (bala != null) {
                colisaoX = bala.xa / 5.0;
                colisaoY = bala.ya / 5.0;
            }
        }
    }

    /**
     * Método para tratar o dano provocado pelo personagem.
     *
     * @param fonte 
     * @param dano 
     */
    public void ferir(Entidade fonte, int dano) {
        if (imortal) return;

        if (tempoParalizado <= 0) {
            tempoFerido = 40;
            tempoParalizado = 5;
            vida -= dano;
            if (vida < 0) {
                vida = 0;
            }

            double dist = fonte.pos.dist(pos);
            colisaoX = (pos.x - fonte.pos.x) / dist * 10;
            colisaoY = (pos.y - fonte.pos.y) / dist * 10;
        }
    }

    @Override
    public void colisao(Entidade entidade, double xa, double ya) {
           	xc += xa * 0.4;
            yc += ya * 0.4;
    }

    public int getDano() {
        return dano;
    }
}
