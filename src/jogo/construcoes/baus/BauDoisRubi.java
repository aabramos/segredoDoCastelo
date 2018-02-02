/*
 * Determina um baú que solta monstros ao ser aberto.
 */
package jogo.construcoes.baus;

import java.util.Random;
import jogo.Main;
import jogo.construcoes.Construcao;
import jogo.entidades.Entidade;
import jogo.entidades.UsarInterface;
import jogo.entidades.colecionaveis.Tesouro;
import jogo.entidades.personagens.Fantasma;
import jogo.tela.Arte;
import jogo.tela.Bitmap;

/**
 * A classe BauExplosivo, que instancia um baú que solta monstros ao ser aberto.
 */
public class BauDoisRubi extends Construcao implements UsarInterface {
	
    /** Mudar sprite se o baú for aberto */
    private int status = 0; 
    
    /** O tempo de desaparecimento. */
    private int tempoDesaparecimento = 0;
    
    /** O baú está vazio? */
    private boolean aberto = false;
    
    private Random aleatorio = new Random();
    
    private double x = pos.x + (aleatorio.nextFloat() - 0.5) * 5;
    private double y = pos.y + (aleatorio.nextFloat() - 0.5) * 5;
    
    /**
     * Instancia um novo baú com monstros.
     *      
     * @param x 
     * @param y 
     */
    public BauDoisRubi(double x, double y) {
        super(x, y);
        imortal = false;
        vidaInicial(18);
        tamanho(27, 10);
    }

    public Bitmap getSprite() {
        return Arte.bauDoisRubi[status][0];
    }
    
    public void frame()
    {
    	super.frame();
        if(tempoDesaparecimento > 0 && aberto)
        {	
        	tempoDesaparecimento--;
        	if(tempoDesaparecimento == 20){
        		nivel.mensagem(3);
        		nivel.setDialogoTempo(14);
        		Main.sons.reproduzirSom("/sons/bau_incorreto.wav", (float) pos.x, (float) pos.y);
        	} else if(tempoDesaparecimento == 0){
                nivel.adicionarEntidade(new Fantasma(x, y));
                nivel.adicionarEntidade(new Fantasma(x, y));
                nivel.adicionarEntidade(new Fantasma(x, y));
                nivel.adicionarEntidade(new Fantasma(x, y));
            	morrer();
                remover();
        	}
        }
    }
    
    public void morrer() {
    	super.morrer();
    	if (aberto == false) {
    		nivel.setDialogoTempo(14);
			int particulas = 8;
	        int quantidade = 10 + (int)Math.random()*10;
	        for (int i = 0; i < quantidade; i++) {
	        	
	            double dir = i * Math.PI * 2 / particulas;
	            nivel.adicionarEntidade(new Tesouro(pos.x, pos.y, Math.cos(dir), Math.sin(dir), quantidade));
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