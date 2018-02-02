/*
 * NPC do esp�rito de Sara.
 */
package jogo.construcoes;

import jogo.Main;
import jogo.construcoes.Construcao;
import jogo.entidades.*;
import jogo.entidades.personagens.Jogador;
import jogo.tela.*;

/**
 * A classe Esp�rito.
 */
public class Espirito extends Construcao implements UsarInterface {
	
	// Definindo o di�logo no in�cio do jogo
	public static int dialogo = 33;
	
    /**
     * Instanciando o NPC de Sara
     *      
     * @param x 
     * @param y 
     */
    public Espirito(double x, double y) {
        super(x, y); 
        imortal = true;
        bloqueia = false;
        tamanho(15, 15);
    }
    
    public Bitmap getSprite() {
        return Arte.espirito;
    }
    
    public void usar(Entidade usar)
    {
    	Jogador jogador = (Jogador) usar;
    	nivel.setDialogo(dialogo, jogador.pos.x, jogador.pos.y);
    }
        
    // N�o ser� poss�vel falar com o esp�rito no tutorial
    public boolean usavel() {
    	if (Main.tutorial)
    		return false;
    	else
    		return true;
    }
}