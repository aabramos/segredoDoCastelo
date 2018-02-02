/*
 * Define as moedas que existem no jogo.
 */

package jogo.entidades.colecionaveis;

import java.util.Random;


import jogo.Main;
import jogo.entidades.*;
import jogo.entidades.personagens.Jogador;
import jogo.tela.*;

/**
 * A classe Tesouro, que manuseia as moedas do jogo.
 */
public class Tesouro extends Entidade {
    
    /** Posicionamento vetores x, y e z */
    private double xa, ya, za;
    
    /** Variável z. */
    private double z;
    
    /** A vida do tesouro, se o valor for zero ele é removido. */
    private int vida;
    
    /** Tempo de espera da animação. */
    private int tempoDeEspera = 0;
    
    /** Tempo de animação */
    private int tempoAnimacao = 0;
    
    /**Determina o valor da moeda */
    private int valor = 0;
    
    /** Variável usada em getValorMoedas, caso o jogador não tenha
     * moedas, retornar false 
     */
    private boolean falso = false;
    
    /** Determina se o Tesouro é coletável */
    private boolean coletavel;
    
    /** Variável aleatório. */
    private Random aleatorio = new Random();

    /** Carregar sprites. */
    private static Bitmap[][][] sprites = {
            Arte.moedaBronzePequena, Arte.moedaPrataPequena, 
            Arte.moedaOuroPequena, Arte.moedaBronzeGrande, 
            Arte.moedaPrataGrande, Arte.moedaOuroGrande, 
            Arte.esmeralda, Arte.diamante
    };

    /** Os valores de cada moeda, do menor para o maior. */
    private static int[] valores = {
            1, 2, 5, 10, 20, 50, 100, 200
    };

    /**
     * Instancia um novo Tesouro.
     *
     * @param x
     * @param y
     * @param xa
     * @param ya
     * @param val
     */
    public Tesouro(double x, double y, double xa, double ya, int val) {
        pos.set(x, y);
        coletavel = true;

        valor = 0;
        while (valor < 7 && valores[valor] < val)
            valor++;

        if (aleatorio.nextInt(3) == 0) valor++;
        if (aleatorio.nextInt(3) == 0) valor++;
        if (valor > 7) 
        	valor = 7;

        double pow = aleatorio.nextDouble() * 1 + 1;
        this.xa = xa * pow;
        this.ya = ya * pow;
        this.za = aleatorio.nextDouble() * 2 + 1.0;
        this.tamanho(2, 2);
        moverComColisao = false;
        vida = aleatorio.nextInt(100) + 600;

        tempoAnimacao = aleatorio.nextInt(sprites[valor].length * 3);
    }

    /** Fazer com que o tesouro não seja coletável */
    public void naoColetavel() {
        coletavel = false;
        vida = 270;
    }

    public void frame() {
        tempoAnimacao++;
        if (tempoDeEspera > 0) {
            tempoDeEspera--;
            
            // Remover as moedas do jogo após determinado tempo de espera
            if (tempoDeEspera == 0) {
                remover();
            }
            z += za;
            za += 0.25;
            return;
        }
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
        if (--vida < 0) remover();

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
        coletor.flash();
        coletor.coletar(this);

        if (valor > 6) {
            Main.sons.reproduzirSom("/sons/gema_grande.wav", (float) pos.x, (float) pos.y);
        } else if (valor > 5) {
            Main.sons.reproduzirSom("/sons/gema.wav", (float) pos.x, (float) pos.y);
        } else if (valor > 4) {
            Main.sons.reproduzirSom("/sons/moeda_grande.wav", (float) pos.x, (float) pos.y);
        } else {
            Main.sons.reproduzirSom("/sons/moeda.wav", (float) pos.x, (float) pos.y);
        }
    }

    protected boolean bloquear(Entidade e) {
        return false;
    }

    @Override
    public void manipulaColisao(Entidade entidade, double xa, double ya) {
        if (coletavel && entidade instanceof Jogador) {
            ((Jogador) entidade).coletar(this);
        }
    }

    public void renderizar(Tela tela) {
        Bitmap[][] bm = sprites[valor];
        if (vida > 60 * 3 || vida / 2 % 2 == 0) {
            int frame = tempoAnimacao / 3 % bm.length;
            Bitmap bmp = bm[frame][0];
            if (z > 0) {
                tela.plotar(Arte.sombra, pos.x - 2, pos.y);
            }
            tela.plotar(bmp, pos.x - bmp.base / 2, pos.y - bmp.altura / 2 - 2 - z);
        }
    }

    /**
     * Captura o valor das moedas.
     *
     * @return valor das moedas
     */
    public int getValorMoedas() {
        return falso ? 0 : valores[valor];
    }
}