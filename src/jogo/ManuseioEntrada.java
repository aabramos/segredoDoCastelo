/*
 *  Mapeamento de variáveis para as teclas
 */

package jogo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.HashMap;
import java.util.Map;

import jogo.Teclas.Tecla;

/**
 * Manuseando a entrada
 */
public class ManuseioEntrada implements KeyListener {
	
	/** The mappings. */
	private Map<Integer, Tecla> mapeamento = new HashMap<Integer, Tecla>();
	
	/**
	 * Instanciando o uso das teclas do teclado
	 *
	 * @param tecla 
	 */
	public ManuseioEntrada(Teclas tecla){
		
		mapeamento.put(KeyEvent.VK_UP, tecla.acima);
		mapeamento.put(KeyEvent.VK_DOWN, tecla.abaixo);
		mapeamento.put(KeyEvent.VK_LEFT, tecla.esquerda);
		mapeamento.put(KeyEvent.VK_RIGHT, tecla.direita);

		mapeamento.put(KeyEvent.VK_W, tecla.acima);
		mapeamento.put(KeyEvent.VK_S, tecla.abaixo);
		mapeamento.put(KeyEvent.VK_A, tecla.esquerda);
		mapeamento.put(KeyEvent.VK_D, tecla.direita);

		mapeamento.put(KeyEvent.VK_SPACE, tecla.atirar);
		mapeamento.put(KeyEvent.VK_ENTER, tecla.enter);
		mapeamento.put(KeyEvent.VK_E, tecla.usar);
		mapeamento.put(KeyEvent.VK_ESCAPE, tecla.esc);
	}

	public void keyPressed(KeyEvent te) {
		toggle(te, true);
	}

	public void keyReleased(KeyEvent te) {
		toggle(te, false);
	}

	private void toggle(KeyEvent te, boolean estado) {
		Tecla key = mapeamento.get(te.getKeyCode());
		if (key != null) {
			key.proxEstado = estado;
		}
	}

	@Override
	public void keyTyped(KeyEvent te) { }
}
