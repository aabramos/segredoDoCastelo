/*
 * Gera todos os botões do menu principal.
 */

package jogo.menus;

import java.awt.event.KeyEvent;
import jogo.Main;
import jogo.tela.*;

/**
 * Gera todos os botões do menu principal.
 */
public class TelaMenu extends MenuBotoes {

    /** IDs dos botões do menu */
    public static final int INICIAR_JOGO_ID = 1000;
    public static final int SAIR_JOGO_ID = 1001;

    /** O item selecionado no menu */
    private int itemSelecionado = 0;
    private final int largura;   
    
    /** Define as telas antes do menu */
    private int telas = 0;

    /**
     * Instancia um novo menu principal.
     *
     * @param largura
     */
    public TelaMenu(int largura) {
        super();
        this.largura = largura;
        adicionaBotao(new Botao(INICIAR_JOGO_ID, 0, (largura - 128) / 2, 200));
        adicionaBotao(new Botao(SAIR_JOGO_ID, 1, (largura - 128) / 2, 240));
    }

    public void renderizar(Tela tela) {
    	tela.limpar(0);
		
    	// Exibir telas de créditos no início
    	if (telas == 0)
    		tela.plotar(Arte.creditos1, 0, 0);
    	if (telas == 1)
    		tela.plotar(Arte.creditos2, 0, 0);
    	if (telas == 2)
    		tela.plotar(Arte.creditos3, 0, 0);
        
    	// Inicializar menu principal
        if (Main.prologo == false){
        	tela.plotar(Arte.titulo, 0, 0);
        	super.renderizar(tela);
	        if (itemSelecionado >= 0){
	            tela.plotar(Arte.rubiMenu, (largura - 128) / 2 - 35, 200 + itemSelecionado * 40);
	        }
        }
    }
    
    // Ações ao pressionar um botão do menu
    public void keyPressed(KeyEvent e) {
    	if (Main.prologo == false) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
            	Main.sons.reproduzirSom("/sons/item_menu.wav", 0, 0);
                itemSelecionado--;
                if (itemSelecionado < 0) {
                    itemSelecionado = 1;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            	Main.sons.reproduzirSom("/sons/item_menu.wav", 0, 0);
                itemSelecionado++;
                if (itemSelecionado > 1) {
                    itemSelecionado = 0;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {	
                e.consume();
                Main.sons.pararMusica();
                botoes.get(itemSelecionado).aposClicar();
            }	
    	} else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
            e.consume();
            telas++;
            Main.sons.reproduzirSom("/sons/item_menu.wav", 0, 0);
            if (telas == 3)
            	Main.prologo = false;
        }	
    }

    // Stubs
    @Override
    public void botaoPressionado(Botao button) { }
    public void keyReleased(KeyEvent arg0) { }
    public void keyTyped(KeyEvent arg0) { }
}
