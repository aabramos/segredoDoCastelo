/*
 * Carrega sprites de explosão aleatóriamente. 
 */

package jogo.entidades.animacoes;

import java.util.Random;

import jogo.tela.Arte;
import jogo.tela.Bitmap;
import jogo.tela.Tela;

/**
 * A classe DinamiteExplosaoPequena, que carrega sprites de explosão.
 */
public class DinamiteExplosaoPequena extends Animacao {
    
    /** Variável z, referente a posição de uma animação de explosão. */
    private double z;
    
    /** A variável aleatorio. */
    private static Random aleatorio = new Random();

    /**
     * Instancia uma explosão pequena.
     *
     * @param x 
     * @param y 
     * @param z 
     */
    public DinamiteExplosaoPequena(double x, double y, double z) {
        super(x, y, aleatorio.nextInt(10) + 20); 
        this.z = z;
    }

    public void renderizar(Tela tela) {
        Bitmap[][] bmps = Arte.explosaoPequena;
        int anim = bmps.length - vida * bmps.length / duracao - 1;
        tela.plotar(bmps[anim][0], pos.x - bmps[0][0].base / 2, pos.y - bmps[0][0].altura / 2 - 4 - z);
    }
}
