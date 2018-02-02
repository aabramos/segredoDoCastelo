/*
 * Cria um objeto que é capaz de gerar dinamites infinitas quando o jogador atingir
 * uma determinada quantidade de moedas.
 */
package jogo.construcoes;

import jogo.entidades.*;
import jogo.entidades.personagens.Jogador;
import jogo.menus.Fonte;
import jogo.tela.*;

/**
 * A classe PegarDinamite Cria um objeto que é capaz de gerar dinamites infinitas 
 * quando o jogador atingir uma determinada quantidade de moedas.
 */
public class PegarDinamite extends Construcao {

    /** O valor necessário para retirar dinamites */
    private static final int VALOR_NECESSARIO = 5000;

    /**
     * Instancia um novo objeto que gera dinamites.
     *
     * @param x 
     * @param y 
     */
    public PegarDinamite(double x, double y) {
        super(x, y);
        imortal = true;
    }

    @Override
    public void renderizar(Tela tela) {
        super.renderizar(tela);
        Fonte.centralizar(tela, "" + VALOR_NECESSARIO, (int) (pos.x), (int) (pos.y + 10));
    }

    public void frame() {
        super.frame();
    }

    public Bitmap getSprite() {
        return Arte.dinamite;
    }

    @Override
    public void usar(Entidade user) {
        if (user instanceof Jogador) {
            Jogador jogador = (Jogador) user;
            if (jogador.carregando == null && jogador.qtdeMoedas() >= VALOR_NECESSARIO) {
                Construcao item = null;
                item = new Dinamite(pos.x, pos.y);
                nivel.adicionarEntidade(item);
                jogador.carregar(item);
            } else {
            	nivel.setDialogo(4, jogador.pos.x, jogador.pos.y);
            }
        }
    }
}
