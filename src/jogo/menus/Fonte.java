/*
 * Gerar fonte usada nas falas do jogo.
 */

package jogo.menus;

import jogo.tela.*;

/**
 * Gerar fonte usada nas falas do jogo.
 */
public class Fonte {
    
    /** Mapear a ordem das letras */
    private static String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ   " + "0123456789-.!?/%«\\=*+,;:√… ¡\"'";

    /**
     * Retorna a largura da String.
     *
     * @param s 
     * @return largura da string
     */
    private static int getLarguraString(String s) {
        return s.length() * 8;
    }

    /**
     * Desenha a fonte na tela.
     *
     * @param tela 
     * @param msg 
     * @param x 
     * @param y 
     */
    public static void escrever(Tela tela, String msg, int x, int y) {
        // Sempre em mai˙sculo
    	msg = msg.toUpperCase();
    	
        int comprimento = msg.length();
        for (int i = 0; i < comprimento; i++) {
            int c = letras.indexOf(msg.charAt(i));
            if (c < 0) continue;
            tela.plotar(Arte.fonteJogo[c % 29][c / 29], x, y);
            x += 8;
        }
    }

    /**
     * Desenha o texto centralizado.
     *
     * @param tela 
     * @param msg 
     * @param x 
     * @param y 
     */
    public static void centralizar(Tela tela, String msg, int x, int y) {
    	int largura = getLarguraString(msg);
    	escrever(tela, msg, x - largura / 2, y - 4);
    }
}
