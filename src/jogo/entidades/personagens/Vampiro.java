/*
 * A classe que define o inimigo Vampiro.
 */

package jogo.entidades.personagens;

import java.util.Random;

import jogo.Main;
import jogo.entidades.Entidade;
import jogo.entidades.animacoes.AnimacaoMorte;
import jogo.entidades.colecionaveis.Coracao;
import jogo.entidades.colecionaveis.Tesouro;
import jogo.tela.*;

/**
 * A classe que define o inimigo Vampiro.
 */
public class Vampiro extends Personagem {
    
    private int olhando;
    private int tempoAndar;
    private int tempoPasso;
    private Random aleatorio = new Random();

    /**
     * Instancia um novo vampiro.
     *
     * @param x 
     * @param y 
     */
    public Vampiro(double x, double y) {
        super(x, y);
        setPos(x, y);
        vidaInicial(10);
        direcao = aleatorio.nextDouble() * Math.PI * 2;
        alinhamento = 10;
        olhando = aleatorio.nextInt(4);
        dano = 4;
    }

 // Esquema de movimentação.
    public void frame() {
        super.frame();
        if (tempoParalizado > 0) 
        	return;

        double speed = 0.4;
        if (nivel.getPause() == false){
	        if (olhando == 0) yc += speed;
	        if (olhando == 1) xc -= speed;
	        if (olhando == 2) yc -= speed;
	        if (olhando == 3) xc += speed;
	        tempoAndar+=6;
        }

        if (tempoAndar / 12 % 4 != 0) {
            tempoPasso++;
            if (!mover(xc, yc) || (tempoAndar > 10 && aleatorio.nextInt(200) == 0)) {
                olhando = aleatorio.nextInt(4);
                tempoAndar = 0;
            }
        }
        xc *= 0.2;
        yc *= 0.2;
    }

    // Ao morrer, criar 5 morcegos
    public void morrer() {
        int particulas = 8;

        if (getDano() > 0) {
            int loots = 4;
            for (int i = 0; i < loots; i++) {
                double dir = i * Math.PI * 2 / particulas;
                nivel.adicionarEntidade(new Tesouro(pos.x, pos.y, Math.cos(dir), Math.sin(dir), getDano()));
            }
        }

        nivel.adicionarEntidade(new AnimacaoMorte(pos.x, pos.y));
        
        int chance = aleatorio.nextInt(11);
        if (chance == 10)
        	nivel.adicionarEntidade(new Coracao(pos.x, pos.y,Math.cos(direcao), Math.sin(direcao)));
        
        Main.sons.reproduzirSom(somPadraoMorte(), (float) pos.x, (float) pos.y);
    
        nivel.adicionarEntidade(new Morcego(pos.x +2, pos.y +2));
        nivel.adicionarEntidade(new Morcego(pos.x +1, pos.y +1));
        nivel.adicionarEntidade(new Morcego(pos.x -1, pos.y -1));
        nivel.adicionarEntidade(new Morcego(pos.x -2, pos.y -2));
        nivel.subInimigo();
    }

    public Bitmap getSprite() {
        return Arte.vampiro[((tempoPasso / 6) & 3)][(olhando + 3) & 3];
    }

    public void colisao(Entidade entity, double xa, double ya) {
    	
        super.colisao(entity, xa, ya);
        if (entity instanceof Jogador) {
            Jogador jogador = (Jogador) entity;
            if (inimigo(jogador)) {
                jogador.ferir(this, dano);
            }
        }
    }

    @Override
    public String somPadraoMorte() {
        return "/sons/vampiro_morre.wav";
    }
}
