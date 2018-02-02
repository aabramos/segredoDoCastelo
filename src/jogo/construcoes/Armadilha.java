/*
 * Determina uma armadilha no cenário.
 */
package jogo.construcoes;

import java.util.Set;

import jogo.Main;
import jogo.entidades.Entidade;
import jogo.entidades.personagens.Jogador;
import jogo.entidades.personagens.Personagem;
import jogo.tela.Arte;
import jogo.tela.Bitmap;
import jogo.tiles.Tile;

/**
 * A classe BotaoA, que cria um botão ativado por LIBRAS.
 */
public class Armadilha extends Construcao {
	
    /** Mudar sprite se a armadilha for acionada */
    private int status = 0; 
    
	public Armadilha(double x, double y) {
		super(x, y);
        imortal = true;
        bloqueia = false;
        // Botao está no chão
        alinhamento = -22;
	}
    
    @Override
    public boolean inimigo(Personagem m) {
        return true;
    }
    
	public void frame() {

		Set<Entidade> entidades = nivel.getEntidades(pos.x -18.7, pos.y +5, pos.x + Tile.LARGURA -20, pos.y + Tile.ALTURA);
		for (Entidade e : entidades) {
			if (e instanceof Jogador && ((Jogador) e).vida > 0) {
				((Jogador) e).ferir(this, 6);
				status = 1;
				Main.sons.reproduzirSom("/sons/armadilha.wav", (float) pos.x, (float) pos.y);
				nivel.setDialogoTempo(19);
			}
		}
	}
	
    public boolean usavel() {
        return false;
    }

    public Bitmap getSprite() {
        return Arte.armadilha[status][0];
    }
}
