/*
 * Bitmaps.
 */

package jogo.tela;

import java.util.*;

/**
 * A Classe Bitmap.
 */
public class Bitmap {
    
    public int base, altura;
    public int[] pixels;

    /**
     * Instancia um novo bitmap
     *
     * @param c 
     * @param a 
     */
    public Bitmap(int c, int a) {
        this.base = c;
        this.altura = a;
        pixels = new int[c * a];
    }

    public void limpar(int cor) {
        Arrays.fill(pixels, cor);
    }

    /**
     * Plotar.
     *
     * @param bitmap 
     * @param x 
     * @param y 
     */
    public void plotar(Bitmap bitmap, int x, int y) {
        int x0 = x;
        int x1 = x + bitmap.base;
        int y0 = y;
        int y1 = y + bitmap.altura;
        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x1 > base) x1 = base;
        if (y1 > altura) y1 = altura;
        int ww = x1 - x0;

        for (int yy = y0; yy < y1; yy++) {
            int tp = yy * base + x0;
            int sp = (yy - y) * bitmap.base + (x0 - x);
            tp -= sp;
            for (int xx = sp; xx < sp + ww; xx++) {
                int col = bitmap.pixels[xx];
                if (col < 0) pixels[tp + xx] = col;
            }
        }
    }

    public void blit(Bitmap bitmap, int x, int y, int www, int hhh) {
        int x0 = x;
        int x1 = x + www;
        int y0 = y;
        int y1 = y + hhh;
        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x1 > base) x1 = base;
        if (y1 > altura) y1 = altura;
        int ww = x1 - x0;

        for (int yy = y0; yy < y1; yy++) {
            int tp = yy * base + x0;
            int sp = (yy - y) * bitmap.base + (x0 - x);
            tp -= sp;
            for (int xx = sp; xx < sp + ww; xx++) {
                int col = bitmap.pixels[xx];
                if (col < 0) pixels[tp + xx] = col;
            }
        }
    }

    public void blitDeCores(Bitmap bitmap, int x, int y, int cor) {
        int x0 = x;
        int x1 = x + bitmap.base;
        int y0 = y;
        int y1 = y + bitmap.altura;
        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x1 > base) x1 = base;
        if (y1 > altura) y1 = altura;
        int ww = x1 - x0;

        int a2 = (cor >> 24) & 0xff;
        int a1 = 256 - a2;

        int rr = cor & 0xff0000;
        int gg = cor & 0xff00;
        int bb = cor & 0xff;

        for (int yy = y0; yy < y1; yy++) {
            int tp = yy * base + x0;
            int sp = (yy - y) * bitmap.base + (x0 - x);
            for (int xx = 0; xx < ww; xx++) {
                int col = bitmap.pixels[sp + xx];
                if (col < 0) {
                    int r = (col & 0xff0000);
                    int g = (col & 0xff00);
                    int b = (col & 0xff);

                    r = ((r * a1 + rr * a2) >> 8) & 0xff0000;
                    g = ((g * a1 + gg * a2) >> 8) & 0xff00;
                    b = ((b * a1 + bb * a2) >> 8) & 0xff;
                    pixels[tp + xx] = 0xff000000 | r | g | b;
                }
            }
        }
    }

    /**
     * Preecher.
     *
     * @param x
     * @param y 
     * @param bw 
     * @param bh 
     * @param cor
     */
    public void preecher(int x, int y, int bw, int bh, int cor) {
        int x0 = x;
        int x1 = x + bw;
        int y0 = y;
        int y1 = y + bh;
        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x1 > base) x1 = base;
        if (y1 > altura) y1 = altura;
        int ww = x1 - x0;

        for (int yy = y0; yy < y1; yy++) {
            int tp = yy * base + x0;
            for (int xx = 0; xx < ww; xx++) {
                pixels[tp + xx] = cor;
            }
        }
    }
}