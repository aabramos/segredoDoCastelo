/*
 * Instancia um tesouro.
 */
package jogo.construcoes.baus;

import java.util.Set;
import jogo.Main;
import jogo.construcoes.Construcao;
import jogo.entidades.*;
import jogo.entidades.animacoes.DinamiteExplosaoGrande;
import jogo.entidades.colecionaveis.Tesouro;
import jogo.entidades.personagens.Personagem;
import jogo.tela.*;

/**
 * A classe BauAmoedas, que instancia um baú que explode ao ser aberto.
 */
public class BauAMoedas extends Construcao implements UsarInterface {
	
	 /** A distancia que a explosao pode atingir um personagem */
 	private static final double DISTANCIA_EXPLOSAO = 65;
    
    /** Mudar o status do sprite se o baú for aberto */
    private int status = 0; 
    
    /** O tempo de desaparecimento. */
    private int tempoDesaparecimento = 0;
    
    /** O baú está vazio? */
    private boolean aberto = false;
    
    /**
     * Instancia um novo baú explosivo.
     *      
     * @param x 
     * @param y 
     */
    public BauAMoedas(double x, double y) {
        super(x, y); 
        imortal = false;
        vidaInicial(18);
        tamanho(27, 10);
    }
    
    /** 
     * Explodir ao morrer.
     */
    public void morrer() {
    	if (aberto){
            nivel.adicionarEntidade(new DinamiteExplosaoGrande(pos.x, pos.y));
            Main.sons.reproduzirSom("/sons/bomba.wav", (float) pos.x, (float) pos.y);
            
            Set<Entidade> entidades = nivel.getEntidades(pos.x - DISTANCIA_EXPLOSAO, pos.y - DISTANCIA_EXPLOSAO, pos.x + DISTANCIA_EXPLOSAO, pos.y + DISTANCIA_EXPLOSAO, Personagem.class);
            for (Entidade e : entidades) {
                double distQuadrado = pos.distAoQuadrado(e.pos);
                if (distQuadrado < (DISTANCIA_EXPLOSAO * DISTANCIA_EXPLOSAO)) {
                    ((Personagem) e).ferir(this, 5);
                }
            }
    	} else {
    		super.morrer();
    		int particulas = 8;
            int quantidade = 5 + (int)Math.random()*10;
            for (int i = 0; i < quantidade; i++) {
            	
                double dir = i * Math.PI * 2 / particulas;
                nivel.adicionarEntidade(new Tesouro(pos.x, pos.y, Math.cos(dir), Math.sin(dir), quantidade));
            }
    	}
    	nivel.setDialogoTempo(14);
    }
    
    public Bitmap getSprite() {
        return Arte.bauAMoedas[status][0];
    }
    
    public void frame()
    {
    	super.frame();
        if(tempoDesaparecimento > 0 && aberto)
        {	
        	tempoDesaparecimento--;
        	if(tempoDesaparecimento == 20){
        		nivel.mensagem(1);
        		Main.sons.reproduzirSom("/sons/bau_incorreto.wav", (float) pos.x, (float) pos.y);
        	} else if(tempoDesaparecimento == 0){
            	morrer();
                remover();
        	} 
        }
    }

    public void usar(Entidade usuario)
    {
    	nivel.setPrimeiroTesouro(usuario.pos.x, usuario.pos.y);
    	if (status == 0 && nivel.getPrimeiroTesouro()) {
            aberto = true;
            tempoDesaparecimento = 40;
            Main.sons.reproduzirSom("/sons/abrir_bau.wav", (float) pos.x, (float) pos.y);
            status = 1;
    	}
    }
    
    public boolean usavel() {    	
        if (status == 1) return false;
        return true;
    }
}