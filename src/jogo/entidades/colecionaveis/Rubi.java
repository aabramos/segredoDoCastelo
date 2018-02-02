/*
 * Define os rubis que existem no jogo.
 */

package jogo.entidades.colecionaveis;

import java.util.Random;
import jogo.Main;
import jogo.entidades.*;
import jogo.entidades.personagens.Jogador;
import jogo.tela.*;

/**
 * A classe Rubi, que manuseia os rubis existentes no jogo.
 */
public class Rubi extends Entidade {
    
    /** Posicionamento vetores x, y e z */
    private double xa, ya, za;
    
    /** Variável z. */
    private double z;
    
    /** A vida do rubi, se o valor for zero ele é removido. */
    private int vida;
    
    /** Tempo de animação */
    private int tempoAnimacao = 0;
    
    /**Determina o valor da moeda */
    private int valor = 0;
    
    /** Determina se o rubiu é coletável */
    private boolean coletavel;
    
    /** Variável aleatório. */
    private Random aleatorio = new Random();

    /** Carregar sprites. */
    private static Bitmap[][] sprites = Arte.rubi;
    
    /**
     * Instancia um novo Rubi.
     *
     * @param x
     * @param y
     * @param xa
     * @param ya
     * @param val
     */
    public Rubi(double x, double y, double xa, double ya, int val) {
        pos.set(x, y);
        coletavel = true;

        if (aleatorio.nextInt(3) == 0) valor++;
        if (aleatorio.nextInt(3) == 0) valor++;
        if (valor > 8) 
        	valor = 8;

        double pow = aleatorio.nextDouble() * 1 + 1;
        this.xa = xa * pow;
        this.ya = ya * pow;
        this.za = aleatorio.nextDouble() * 2 + 1.0;
        this.tamanho(2, 2);
        moverComColisao = false;
        vida = aleatorio.nextInt(100) + 600;

        tempoAnimacao = aleatorio.nextInt(sprites[valor].length * 3);
    }

    public void frame() {
        tempoAnimacao++;
        mover(xa, ya);
        z += za;
        if (z < 0) {
            z = 0;
            xa *= 0.8;
            ya *= 0.8;
        } else {
            xa *= 0.98;
            ya *= 0.98;
        }
        za -= 0.2;

        // Se for possível coletar as moedas
        if (coletavel) {
            double dist = 100;
            for (Entidade e : nivel.getEntidades(getFronteiras().aumentar(dist))) {
                if (!(e instanceof ColetarInterface)) continue;
                ColetarInterface p = (ColetarInterface) e;
                if (!p.podeColetar()) {
                    double xd = e.pos.x - pos.x;
                    double yd = e.pos.y - pos.y;
                    double distLocal = 80;
                    if (xd * xd + yd * yd < distLocal * distLocal) {
                        double dd = Math.sqrt(xd * xd + yd * yd);
                        if (dd < 16) {
                            aoColetar(p);
                            return;
                        }
                        xd /= dd;
                        yd /= dd;
                        double poder = (1 - (dd / distLocal)) * 0.1;
                        if (z <= 0) {
                            this.xa -= xd * poder;
                            this.ya -= yd * poder;
                        }
                    }
                } else {
                    double poderDeColeta = p.getPoderColeta();
                    double xd = e.pos.x - pos.x;
                    double yd = e.pos.y - pos.y;
                    double distLocal = (dist - 40) * poderDeColeta + 40;
                    if (xd * xd + yd * yd < distLocal * distLocal) {
                        double dd = Math.sqrt(xd * xd + yd * yd);
                        if (dd < 16) {
                            aoColetar(p);
                            return;
                        }
                        xd /= dd;
                        yd /= dd;
                        double poder = (1 - (dd / distLocal)) * 1.6 * (poderDeColeta * 0.5 + 0.5);
                        if (z <= 0) {
                            this.xa += xd * poder;
                            this.ya += yd * poder;
                        }
                    }
                }
            }
        }
    }

    /**
     * Ao coletar as moedas, fazer o jogador brilhar e reproduzir um som.
     *
     * @param coletor
     */
    private void aoColetar(ColetarInterface coletor) {
        remover();
        nivel.setDialogoTempo(20);
        coletor.flash();
        coletor.coletarRubi(this);
        Main.sons.reproduzirSom("/sons/correto.wav", (float) pos.x, (float) pos.y);
    }

    protected boolean bloquear(Entidade e) {
        return false;
    }

    @Override
    public void manipulaColisao(Entidade entidade, double xa, double ya) {
        if (coletavel && entidade instanceof Jogador) {
            ((Jogador) entidade).coletarRubi(this);
        }
    }

    public void renderizar(Tela tela) {
        Bitmap[][] bm = sprites;
        if (vida > 60 * 3 || vida / 2 % 2 == 0) {
            int frame = tempoAnimacao / 3 % bm.length;
            Bitmap bmp = bm[frame][0];
            if (z > 0) {
                tela.plotar(Arte.sombra, pos.x - 2, pos.y);
            }
            tela.plotar(bmp, pos.x - bmp.base / 2, pos.y - bmp.altura / 2 - 2 - z);
        }
    }
}