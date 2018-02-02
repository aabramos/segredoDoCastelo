/*
 * Adiciona um menu com botões na tela inicial.
 */

package jogo.menus;

import java.awt.event.KeyListener;
import java.util.*;


import jogo.BotoesMouse;
import jogo.tela.Tela;

/**
 * Adiciona um menu com botões na tela inicial.
 */
public abstract class MenuBotoes implements MouseInterface, KeyListener {

    protected List<Botao> botoes = new ArrayList<Botao>();

    /**
     * Adiciona os botões.
     *
     * @param botao 
     */
    protected void adicionaBotao(Botao botao) {
        botoes.add(botao);
        botao.addListener(this);
    }

    public void renderizar(Tela tela) {
        for (Botao button : botoes) {
            button.renderizar(tela);
        }
    }

    public void frame(BotoesMouse mouseButtons) {
        for (Botao button : botoes) {
            button.frame(mouseButtons);
        }
    }

    /**
     * Adiciona o listener.
     *
     * @param listener 
     */
    public void adicionarListener(MouseInterface listener) {
        for (Botao button : botoes) {
            button.addListener(listener);
        }
    }
}
