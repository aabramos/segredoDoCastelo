/*
 * Entidade que ativa um dos finais do jogo.
 */
package jogo.construcoes;

import jogo.Main;
import jogo.construcoes.Construcao;
import jogo.entidades.*;
import jogo.entidades.personagens.Jogador;
import jogo.tela.*;

/**
 * A classe FinalDeJogo1, que ativa um dos finais do jogo.
 */
public class FinalDeJogo1 extends Construcao implements UsarInterface {
	
    public FinalDeJogo1(double x, double y) {
        super(x, y); 
        imortal = true;
    }
    
    public Bitmap getSprite() {
        return Arte.porta;
    }
   
    public void usar(Entidade usuario)
    {
    	// Quando o jogador atingir 8 rubis, rodar o final.
    	Jogador jogador = (Jogador) usuario;
    	if (jogador.rubi == 8){
    		nivel.setPause(true);
    		Main.finalJogo1();
    	} else {
			nivel.setDialogo(25, jogador.pos.x, jogador.pos.y);
		}
    }
        
    public boolean usavel() {
        return true;
    }
}