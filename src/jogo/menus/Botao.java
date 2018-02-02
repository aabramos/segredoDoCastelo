/*
 * Classe que lida com um botão do menu.
 */

package jogo.menus;

import java.util.*;


import jogo.BotoesMouse;
import jogo.tela.*;

/**
 * Classe que lida com um botão do menu.
 */
public class Botao {

    /** Captura eventos do mouse em uma lista. */
    private List<MouseInterface> evento;

    /** Se um botão for pressionado... */
    private boolean foiPressionado;

    // Coordenadas
    private final int x;
    private final int y;
    private final int l;
    private final int a;
    private final int id;
    private int ix;
    private int iy;
    private boolean clicar = false;

    /**
     * Instancia um novo botão.
     *
     * @param id 
     * @param indiceBotao
     * @param x 
     * @param y 
     */
    public Botao(int id, int indiceBotao, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.l = 128;
        this.a = 24;
        this.ix = indiceBotao % 2;
        this.iy = indiceBotao / 2;
    }

    public void frame(BotoesMouse botoesMouse) {

        int mx = botoesMouse.getX() / 2;
        int my = botoesMouse.getY() / 2;
        foiPressionado = false;
        if (mx >= x && my >= y && mx < (x + l) && my < (y + a)) {
            if (botoesMouse.estaLiberado(1)) {
                aposClicar();
            } else if (botoesMouse.estaPressionado(1)) {
                foiPressionado = true;
            }
        }
        if (clicar) {
            if (evento != null) {
                for (MouseInterface eventos : evento) {
                    eventos.botaoPressionado(this);
                }
            }
            clicar = false;
        }
    }

    /**
     * Após clicar
     */
    public void aposClicar() {
    	clicar = true;
    }

    public void renderizar(Tela tela) {
    	if (clicar == false){
	        if (foiPressionado) {
	            tela.plotar(Arte.botoes[ix][iy * 2 + 1], x, y);
	        } else {
	            tela.plotar(Arte.botoes[ix][iy * 2 + 0], x, y);
	        }
    	}
    }

    /**
     * Adiciona um listener.
     *
     * @param listener
     */
    public void addListener(MouseInterface listener) {
        if (evento == null) {
            evento = new ArrayList<MouseInterface>();
        }
        evento.add(listener);
    }

    /**
     * Retorna o id do botão.
     *
     * @return id
     */
    public int getId() {
        return id;
    }
}
