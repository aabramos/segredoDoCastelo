/*
 * Cria uma porta capaz de ser aberta quando o jogador ativar botões em LIBRAS
 */
package jogo.construcoes;

import jogo.entidades.Entidade;
import jogo.tela.*;

/**
 * A classe Porta.
 */
public class Porta extends Construcao {

    /**
     * Instancia uma nova porta.
     *
     * @param x 
     * @param y
     */
    public Porta(double x, double y) {
        super(x, y);
        imortal = true;
    }
    
    public void usar(Entidade jogador) {
		nivel.setDialogo(11, jogador.pos.x, jogador.pos.y);
    }
       
    public Bitmap getSprite() {
        return Arte.porta;
    }
}
