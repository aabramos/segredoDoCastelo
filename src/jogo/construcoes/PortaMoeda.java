/*
 * Cria uma porta capaz de ser aberta quando o jogador atingir uma determinada
 * quantidade de moedas.
 */
package jogo.construcoes;

import jogo.Main;
import jogo.entidades.*;
import jogo.entidades.personagens.Jogador;
import jogo.menus.Fonte;
import jogo.tela.*;

/**
 * A classe PortaMoeda cria uma porta capaz de ser aberta quando o jogador 
 * atingir uma determinada quantidade de moedas.
 */
public class PortaMoeda extends Construcao {

    /** O custo para abrir a porta. */
    private static final int VALOR_NECESSARIO = 10000;

    /**
     * Instancia uma nova porta.
     *
     * @param x 
     * @param y
     */
    public PortaMoeda(double x, double y) {
        super(x, y);
        imortal = true;
    }

    @Override
    public void renderizar(Tela tela) {
        super.renderizar(tela);
        Fonte.centralizar(tela, "" + VALOR_NECESSARIO, (int) (pos.x), (int) (pos.y + 30));
    }

    public Bitmap getSprite() {
            return Arte.porta;
    }

	 /**
     * Remover a porta ao usá-la quando o jogador tiver a quantidade igual ou acima
	 * do valor necessário.
     */
    @Override
    public void usar(Entidade usuario) {
        if (usuario instanceof Jogador) {
            Jogador jogador = (Jogador) usuario;
            if (jogador.carregando == null && jogador.qtdeMoedas() >= VALOR_NECESSARIO) {
				nivel.removerEntidade(this);
				Main.sons.reproduzirSom("/sons/botao.wav", (float) pos.x, (float) pos.y);
            } else {
        		nivel.setDialogo(2, jogador.pos.x, jogador.pos.y);
            }
        }
    }
}
