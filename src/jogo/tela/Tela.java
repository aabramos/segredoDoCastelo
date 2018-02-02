/*
 * A Classe Tela.
 */

package jogo.tela;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * A Classe Tela.
 */
public class Tela extends Bitmap {
    
    public BufferedImage imagem;
    private int alinhamentoX, alinhamentoY;

    /**
     * Instancia uma nova tela.
     *
     * @param c 
     * @param a 
     */
    public Tela(int c, int a) {
        super(c, a);
        imagem = new BufferedImage(c, a, BufferedImage.TYPE_INT_ARGB);
        pixels = ((DataBufferInt) imagem.getRaster().getDataBuffer()).getData();
    }

    /**
     * Set no alinhamento
     *
     * @param alinhamentoX 
     * @param alinhamentoY
     */
    public void setAlinhamento(int alinhamentoX, int alinhamentoY) {
        this.alinhamentoX = alinhamentoX;
        this.alinhamentoY = alinhamentoY;
    }

    /**
     * Plotando o bitmap.
     *
     * @param bitmap 
     * @param x
     * @param y 
     */
    public void plotar(Bitmap bitmap, double x, double y) {
        plotar(bitmap, (int) x, (int) y);
    }

    public void plotar(Bitmap bitmap, int x, int y) {
        super.plotar(bitmap, x + alinhamentoX, y + alinhamentoY);
    }

    public void blit(Bitmap bitmap, int x, int y, int w, int h) {
        super.blit(bitmap, x + alinhamentoX, y + alinhamentoY, w, h);
    }

    public void blitCores(Bitmap bitmap, double x, double y, int cor) {
        blitDeCores(bitmap, (int) x, (int) y, cor);
    }

    public void blitDeCores(Bitmap bitmap, int x, int y, int color) {
        super.blitDeCores(bitmap, x + alinhamentoX, y + alinhamentoY, color);
    }

    public void preecher(int x, int y, int width, int height, int color) {
        super.preecher(x + alinhamentoX, y + alinhamentoY, width, height, color);
    }
}