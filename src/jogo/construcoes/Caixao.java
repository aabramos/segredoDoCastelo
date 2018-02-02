/*
 * Determina o funcionamento do Caixão no jogo, que gera inimigos.
 */
package jogo.construcoes;

import java.util.Random;
import jogo.entidades.Entidade;
import jogo.entidades.animacoes.AnimacaoMorte2;
import jogo.entidades.personagens.*;
import jogo.tela.*;

/**
 * A classe Caixao, que gera inimigos aleatoriamente.
 */
public class Caixao extends Construcao {
    
    /** A constante que determina a base do tempo de criação dos inimigos. */
    private static final int INTERVALO_CRIACAO = 120 * 3;
    
    /** A constante que determina a base do tempo que o caixão dura. */
    private static final int INTERVALO_PERMANENCIA = 120 * 9;
    
    /** Tempo base de criação */
    private int tempoCriacao = 0;
    
    /** Tempo base de permanência no cenário */
    private int tempoPermanencia = INTERVALO_PERMANENCIA;
    
    /** O tipo de inimigo. */
    private int tipoInimigo;
    
    /** A variável aleatorio. */
    private Random aleatorio = new Random();
    
    /**
     * Instancia um novo caixão.
     *
     * @param x
     * @param y
     * @param tipo
     */
    public Caixao(double x, double y, int tipo) {
        super(x, y);
        this.tipoInimigo = tipo;
        vidaInicial(13);
        tempoParalizado = 10;
        tempoCriacao = aleatorio.nextInt(INTERVALO_CRIACAO);
        
        // Determinando o ícone no mapa.
        iconeMapa = 4;
        dano = tipo * 5 + 5;
    }
    
    /* 
     * @see construcoes.Construcao#frame()
     */
    public void frame() {
        super.frame();
        
        if (tempoParalizado > 0) return;

        if (--tempoCriacao <= 0) {
            criacao();
            tempoCriacao = INTERVALO_CRIACAO;
        }
        
        if (--tempoPermanencia <= 0) {
        	nivel.adicionarEntidade(new AnimacaoMorte2(pos.x, pos.y));
            remover();
        }
    }

    /**
     * Criação de inimigos.
     */
    private void criacao() {
    	
        double x = pos.x + (aleatorio.nextFloat() - 0.5) * 5;
        double y = pos.y + (aleatorio.nextFloat() - 0.5) * 5;

        Personagem pe = null;
        if (tipoInimigo == 0) pe = new Morcego(x, y);
        if (tipoInimigo == 1) pe = new Fantasma(x, y);
        if (tipoInimigo == 2) pe = new Vampiro(x, y);
        if (tipoInimigo == 3) pe = new Aranha(x, y);
        if (tipoInimigo == 4) pe = new FantasmaTonto(x, y);
        
        if (nivel.getEntidades(pe.getFronteiras().aumentar(8), pe.getClass()).size() == 0 && !nivel.maxInimigo()) {
            	nivel.adicionarEntidade(pe);
            	nivel.addInimigo();
        }
    }
    
    protected boolean bloquear (Entidade e) {
        if (e instanceof Morcego) 
        	return false;
        if (e instanceof Vampiro) 
        	return false;
        if (e instanceof Fantasma) 
        	return false;
        if (e instanceof FantasmaTonto) 
        	return false;
        if (e instanceof Aranha) 
        	return false;
        return true;
    }

    public void morrer() {
        super.morrer();
    }

    /** Determina o último indice do sprite do caixão. */
    private int ultimoIndice = 0;

    /** 
     * O caixão vai alterando o sprite a medida que o dano provocado
     * pelo jogador aumenta. 
     */
    public Bitmap getSprite() {
        int novoIndice = 3 - (3 * vida) / vidaMaxima;
        if (novoIndice != ultimoIndice) {
        	ultimoIndice = novoIndice;
        }
        return Arte.caixao[novoIndice][0];
    }
    
    public boolean usavel() {
        return false;
    }
}
