/*
 * Determina o funcionamento de um baú no jogo 
 */

package jogo.construcoes.baus;

import jogo.Main;
import jogo.construcoes.Construcao;
import jogo.entidades.*;
import jogo.entidades.colecionaveis.*;
import jogo.tela.*;

/**
 * A classe Baú, que manuseia baús com tesouro no jogo.
 */
public class BauUmRubi extends Construcao implements UsarInterface {
    
    /** Mudar sprite se o baú for aberto */
    private int status = 0; 
    
    /** O tempo de desaparecimento. */
    private int tempoDesaparecimento = 0;
    
    /** O baú está vazio? */
    private boolean aberto = false;
    
    /**
     * Instancia um novo baú.
     *
     * @param x 
     * @param y
     */
    public BauUmRubi(double x, double y) {
        super(x, y);
        imortal = false;
        vidaInicial(18);
        tamanho(27, 10);
    }
    
    public Bitmap getSprite() {
        return Arte.bauUmRubi[status][0];
    }
    
    /** 
     * Depois de aberto o baú deverá desaparecer após determinado tempo 
     */
    public void frame()
    {
    	super.frame();
        if(tempoDesaparecimento > 0 && aberto)
        {	
        	tempoDesaparecimento--;
        	if(tempoDesaparecimento == 95){
        		Main.sons.reproduzirSom("/sons/correto.wav", (float) pos.x, (float) pos.y);
        		nivel.mensagem(6);
        		nivel.setDialogoTempo(13);
        		int particulas = 8;
                int quantidade = 30 + (int)Math.random()*20 + (int)Math.random()*50;
                for (int i = 0; i < quantidade; i++) {
                	
                    double dir = i * Math.PI * 2 / particulas;
                    nivel.adicionarEntidade(new Tesouro(pos.x, pos.y, Math.cos(dir), Math.sin(dir), quantidade));
                }
        	} else if(tempoDesaparecimento == 0){
            	morrer();
                remover();
        	}
        }
    }

    /** 
     * Cria o tesouro do baú após aberto. 
     */
    public void usar(Entidade usuario)
    {
    	nivel.setPrimeiroTesouro(usuario.pos.x, usuario.pos.y);
    	if (status == 0 && nivel.getPrimeiroTesouro()) {
            aberto = true;
            tempoDesaparecimento = 150;
            Main.sons.reproduzirSom("/sons/abrir_bau.wav", (float) pos.x, (float) pos.y);
            status = 1;
        }
    }
    
    public boolean usavel() {
        if (status == 1) return false;
        return true;
    }
}