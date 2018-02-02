/*
 * Determina as interações realizada por uma bala disparada pelo jogador.
 */
package jogo.entidades;

import jogo.Main;
import jogo.construcoes.FinalDeJogo2;
import jogo.construcoes.letras.Aestatico;
import jogo.construcoes.letras.Bestatico;
import jogo.construcoes.letras.Iestatico;
import jogo.construcoes.letras.LetraA;
import jogo.construcoes.letras.LetraB;
import jogo.construcoes.letras.LetraI;
import jogo.construcoes.letras.LetraR;
import jogo.construcoes.letras.Restatico;
import jogo.construcoes.portugues.PergaminhoA;
import jogo.construcoes.portugues.PergaminhoO;
import jogo.entidades.personagens.*;
import jogo.tela.*;

/**
 * A classe Bala, que cuida das interações da bala disparada pela arma do jogador.
 */
public class Bala extends Entidade {
    
    /** Coordenadas. */
    public double xa, ya;
    
    /** A origem da bala. */
    public Personagem origem;
    
    /** Indica se a bala acertou algum alvo. */
    private boolean acerto = false;
    
    /** A vida da entidade "bala". */
    private int vida;
    
    /** Direção na qual o jogador está olhando com o personagem. */
    private int olhando;

    /**
     * Instancia uma nova bala.
     *
     * @param p 
     * @param xa 
     * @param ya 
     */
    public Bala(Personagem p, double xa, double ya) {
        this.origem = p;
        pos.set(p.pos.x + xa * 4, p.pos.y + ya * 4 - 3);
        this.xa = xa * 6;
        this.ya = ya * 6;
        this.tamanho(4, 4);
        moverComColisao = false;
        vida = 40;
        double angulo = (Math.atan2(ya, xa) + Math.PI * 1.625);
        olhando = (8 + (int) (angulo / Math.PI * 4)) & 7;
    }

    public void frame() {
        if (--vida <= 0) {
            remover();
            return;
        }
        if (!mover(xa, ya)) {
            acerto = true;
        }
        if (acerto && !removido) {
            remover();
        }
    }

    /** Entidades não bloqueadas por uma bala */
    protected boolean bloquear(Entidade e) {
        
    	if (e instanceof Bala) return false;
        if (e instanceof LetraA) return false;
        if (e instanceof LetraB) return false;
        if (e instanceof LetraR) return false;
        if (e instanceof LetraI) return false;
        if (e instanceof Aestatico) return false;
        if (e instanceof Bestatico) return false;
        if (e instanceof Restatico) return false;
        if (e instanceof Iestatico) return false;
        if (e instanceof PergaminhoA) return false;
        if (e instanceof PergaminhoO) return false;
        if (e instanceof FinalDeJogo2) return false;
        if ((e instanceof Personagem) && !((Personagem) e).inimigo(origem)) return false;
        	
        return e != origem;
    }

    public void renderizar(Tela tela) {
    	if (origem.vida == 10) {
    		tela.plotar(Arte.supertiro[olhando][0], pos.x - 8, pos.y - 10);
    	} else
    		tela.plotar(Arte.bala[olhando][0], pos.x - 8, pos.y - 10);
    }
    
    /** Retorna true se a vida do jogador estiver cheia */
    public boolean supertiro() {
    	if (origem.vida == 10)
    		return true;
    	return false;
    }

    public void colisao(Entidade entidade, double xa, double ya) {
        if (entidade instanceof Personagem) {
            if (((Personagem) entidade).inimigo(origem)) {
                entidade.ferir(this);
                acerto = true;
            }
        } else {
            entidade.ferir(this);
            acerto = true;
        }
        if (acerto) {
            Main.sons.reproduzirSom("/sons/acerto.wav", (float) pos.x, (float) pos.y);
        }
    }
}
