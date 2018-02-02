/*
 * A classe BotoesMouse, que gerencia o estado dos botões do mouse e sua
 * posição atual.
 */

package jogo;

import java.awt.Point;

/**
 * A classe BotoesMouse, que gerencia o estado dos botões do mouse e sua posição
 * atual.
 */
public class BotoesMouse {

	/** O estado atual do botao. */
	private boolean[] estadoAtual = new boolean[4];

	/** O proximo estado do botao. */
	private boolean[] proximoEstado = new boolean[4];

	/** Coordenada X do mouse. */
	private int x;

	/** Coordenada Y do mouse. */
	private int y;

	/**
	 * Set para o próximo estado.
	 * 
	 * @param botao
	 * @param valor
	 */
	public void proximoEstado(int botao, boolean valor) {
		proximoEstado[botao] = valor;
	}

	/**
	 * Verifica se o botão foi pressionado.
	 * 
	 * @param botao
	 * @return true, se foi pressionado
	 */
	public boolean estaPressionado(int botao) {
		return estadoAtual[botao];
	}

	/**
	 * Verifica se o botão foi liberado
	 * 
	 * @param botao
	 * @return true, se foi liberado
	 */
	public boolean estaLiberado(int botao) {
		return estadoAtual[botao] && !proximoEstado[botao];
	}

	/**
	 * Capturando os estados frame a frame no jogo.
	 */
	public void frame() {
		for (int i = 0; i < estadoAtual.length; i++) {
			estadoAtual[i] = proximoEstado[i];
		}
	}

	/**
	 * Liberar todos os estados.
	 */
	public void liberarTudo() {
		for (int i = 0; i < proximoEstado.length; i++) {
			proximoEstado[i] = false;
		}
	}

	/**
	 * Get na posição X do ponteiro do mouse.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get na posição Y do ponteiro do mouse.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set na posição do mouse.
	 * 
	 * @param posicaoMouse
	 */
	public void setPosicao(Point posicaoMouse) {
		if (posicaoMouse != null) {
			x = posicaoMouse.x;
			y = posicaoMouse.y;
		}
	}
}
