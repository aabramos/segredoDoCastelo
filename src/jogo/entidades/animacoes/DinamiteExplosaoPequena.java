/*
 * Carrega sprites de explos�o aleat�riamente. 
 */

package jogo.entidades.animacoes;

import java.util.Random;

import jogo.tela.Arte;
import jogo.tela.Bitmap;
import jogo.tela.Tela;

/**
 * A classe DinamiteExplosaoPequena, que carrega sprites de explos�o.
 */
public class DinamiteExplosaoPequena extends Animacao {
    
    /** Vari�vel z, referente a posi��o de uma anima��o de explos�o. */
    private double z;
    
    /** A vari�vel aleatorio. */
    private static Random aleatorio = new Random();

    /**
     * Instancia uma explos�o pequena.
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
