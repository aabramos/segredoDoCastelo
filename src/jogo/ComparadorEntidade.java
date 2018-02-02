/*
 * A classe responsável por comparar a posição entre duas entidades.
 */
package jogo;

import java.util.Comparator;

import jogo.entidades.Entidade;

/**
 * A classe responsável por comparar a posição entre duas entidades.
 */
public class ComparadorEntidade implements Comparator<Entidade> {
	public int compare(Entidade e0, Entidade e1) {
		if (e0.pos.y < e1.pos.y) return -1;
		if (e0.pos.y > e1.pos.y) return +1;
		if (e0.pos.x < e1.pos.x) return -1;
		if (e0.pos.x > e1.pos.x) return +1;
		return 0;
	}
}
