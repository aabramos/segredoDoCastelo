/*
 * Determina o funcionamento de dinamites no jogo.
 */
package jogo.construcoes;

import java.util.Set;





import jogo.Main;
import jogo.entidades.Entidade;
import jogo.entidades.animacoes.DinamiteExplosaoGrande;
import jogo.entidades.personagens.*;
import jogo.tela.*;

/**
 * A classe Dinamite, que determina o funcionamento dos itens de bomba no jogo.
 */
public class Dinamite extends Construcao {

    /** A distância que provoca dano em uma entidade */
    private static final double DISTANCIA_BOMBA = 50;

    /**
     * Instancia uma nova Dinamite.
     *
     * @param x 
     * @param y 
     */
    public Dinamite(double x, double y) {
        super(x, y);
        vidaInicial(5);
        alinhamento = 2;
        tamanho(7, 7);
    }

    /**
     *  Explodir ao morrer e ferir personagens próximos.
     */
    public void morrer() {
        nivel.adicionarEntidade(new DinamiteExplosaoGrande(pos.x, pos.y));
        Main.sons.reproduzirSom("/sons/bomba.wav", (float) pos.x, (float) pos.y);

        Set<Entidade> entidades = nivel.getEntidades(pos.x - DISTANCIA_BOMBA, pos.y - DISTANCIA_BOMBA, pos.x + DISTANCIA_BOMBA, pos.y + DISTANCIA_BOMBA, Personagem.class);
        for (Entidade e : entidades) {
            double distQuadrado = pos.distAoQuadrado(e.pos);
            if (distQuadrado < (DISTANCIA_BOMBA * DISTANCIA_BOMBA)) {
                ((Personagem) e).ferir(this, 5);
            }
        }
    }

    @Override
    public boolean inimigo(Personagem m) {
        return true;
    }

    public void frame() {

        if (vida <= 0) {
            if (--tempoFerido <= 0) {
                morrer();
                remover();
            }
            return;
        }
        super.frame();
        if (--tempoParalizado > 0) return;
    }

    public Bitmap getSprite() {
        return Arte.dinamite;
    }
}
