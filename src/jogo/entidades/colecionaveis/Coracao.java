/*
 * Define os corações que existem no jogo.
 */
package jogo.entidades.colecionaveis;

import java.util.Random;
import jogo.entidades.*;
import jogo.entidades.personagens.Jogador;
import jogo.tela.*;

/**
 * A classe Coracao, que manuseia os corações do jogo.
 */
public class Coracao extends Entidade {
    
    /** Posicionamento vetores x, y e z */
    private double xa, ya, za;
    
    /** Variável z. */
    private double z;
    
    /** A vida do coração, se o valor for zero ele é removido. */
    private int tempoPermanencia;
    
    /** Tempo de espera da animação. */
    private int tempoDeEspera = 0;
    
    /** Tempo de animação */
    private int tempoAnimacao = 0;
    
    /** Determina se o coração é coletável */
    private boolean coletavel;
    
    /** Variável aleatório. */
    private Random aleatorio = new Random();

    /** Carregar sprites. */
    private static Bitmap[][] sprites = Arte.coracao;
    
    /**
     * Instancia um novo coração.
     *
     * @param x
     * @param y
     * @param xa
     * @param ya
     * @param val
     */
    public Coracao(double x, double y, double xa, double ya) {
        pos.set(x, y);
        coletavel = true;
        moverComColisao = false;
        
        double pow = aleatorio.nextDouble() * 1 + 1;
        this.xa = xa * pow;
        this.ya = ya * pow;
        this.za = aleatorio.nextDouble() * 2 + 1.0;
        this.tamanho(2, 2);
        tempoPermanencia = aleatorio.nextInt(100) + 600;
        tempoAnimacao = aleatorio.nextInt(3);
    }

    /**
     * A cada frame 
     */
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
        
        if (--tempoPermanencia < 0) 
        	remover();
        
        if (!coletavel && tempoPermanencia < 70) {
        	coletavel = true;
        	
        } else if(coletavel) {
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
                    }
                } else {
                    double xd = e.pos.x - pos.x;
                    double yd = e.pos.y - pos.y;
                    double distLocal = (dist - 40) + 40;
                    if (xd * xd + yd * yd < distLocal * distLocal) {
                        double dd = Math.sqrt(xd * xd + yd * yd);
                        if (dd < 16) {
                            aoColetar(p);
                            return;
                        }
                        xd /= dd;
                        yd /= dd;
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
        coletor.flash();
        coletor.recuperarVida(this);
    }

    protected boolean bloquear(Entidade e) {
        return false;
    }

    @Override
    public void manipulaColisao(Entidade entidade, double xa, double ya) {
        if (coletavel && entidade instanceof Jogador) {
            ((Jogador) entidade).recuperarVida(this);
        }
    }
    
    public void naoColetavel() {
        coletavel = false;
        tempoPermanencia = 90;
    }

    public void renderizar(Tela tela) {
        Bitmap[][] bm = sprites;
        if (tempoPermanencia > 60 * 3 || tempoPermanencia / 2 % 2 == 0) {
            int frame = tempoAnimacao / 3 % bm.length;
            Bitmap bmp = bm[frame][0];
            if (z > 0) {
                tela.plotar(Arte.sombra, pos.x - 2, pos.y);
            }
            tela.plotar(bmp, pos.x - bmp.base / 2, pos.y - bmp.altura / 2 - 2 - z);
        }
    }
}