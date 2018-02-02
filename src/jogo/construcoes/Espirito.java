/*
 * NPC do espírito de Sara.
 */
package jogo.construcoes;

import jogo.Main;
import jogo.construcoes.Construcao;
import jogo.entidades.*;
import jogo.entidades.personagens.Jogador;
import jogo.tela.*;

/**
 * A classe Espírito.
 */
public class Espirito extends Construcao implements UsarInterface {
	
	// Definindo o diálogo no início do jogo
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
        
    // Não será possível falar com o espírito no tutorial
    public boolean usavel() {
    	if (Main.tutorial)
    		return false;
    	else
    		return true;
    }
}