/*
 * Implementa as entidades existentes no jogo.
 */

package jogo.entidades;

import java.util.List;

import jogo.Nivel;
import jogo.entidades.animacoes.DinamiteExplosaoGrande;
import jogo.logica.Logica;
import jogo.logica.Fronteiras;
import jogo.logica.OrigemColisao;
import jogo.tela.Tela;

/**
 * A classe Entidade, responsável pelas entidades existentes no jogo.
 */
public abstract class Entidade implements OrigemColisao {

	/** Referente ao nível do jogo. */
	public Nivel nivel;

	/** Indica se uma entidade do nível foi removida. */
	public boolean removido;

	/** A posição da colisão. */
	public Logica pos = new Logica(0, 0);

	/** O raio da colisão. */
	public Logica raio = new Logica(10, 10);

	/** Se a entidade bloqueia. */
	public boolean bloqueia = true;

	/** Se a entidade se move ao sofrer uma colisão. */
	public boolean moverComColisao = true;

	/** Posição x. */
	public int xto;

	/** Posição y. */
	public int yto;

	/** Coordenadas de colisão. */
	public double xc, yc;

	/** O icone do mapa de tal entidade. */
	public int iconeMapa = -1;

	/**
	 * Posição da entidade.
	 * 
	 * @param x
	 * @param y
	 */
	public void setPos(double x, double y) {
		pos.set(x, y);
	}

	/**
	 * Tamanho da entidade.
	 * 
	 * @param xr
	 * @param yr
	 */
	public void tamanho(int xr, int yr) {
		raio.set(xr, yr);
	}

	/**
	 * Iniciar entidade no nível.
	 * 
	 * @param nivel
	 */
	public final void iniciar(Nivel nivel) {
		this.nivel = nivel;
	}

	/** Frame */
	public void frame() { }

	/**
	 * Manuseio de intercepção de entidades.
	 * 
	 * @param xx0
	 * @param yy0
	 * @param xx1
	 * @param yy1
	 * @return true, se for bem sucedida.
	 */
	public boolean intercepta(double xx0, double yy0, double xx1, double yy1) {
		return getFronteiras().intersecao(xx0, yy0, xx1, yy1);
	}

	/**
	 * Deslocamento
	 * 
	 * @return Deslocamento
	 */
	public Fronteiras getFronteiras() {
		return new Fronteiras(this, pos.x - raio.x, pos.y - raio.y, pos.x
				+ raio.x, pos.y + raio.y);
	}

	/**
	 * Renderizar.
	 * 
	 * @param tela
	 */
	public void renderizar(Tela tela) { }

	/**
	 * Move.
	 * 
	 * @param xa
	 *            the xa
	 * @param ya
	 *            the ya
	 * @return true, if successful
	 */
	protected boolean mover(double xa, double ya) {
		List<Fronteiras> desloc = nivel.getFronteiras(this);
		if (moverComColisao) {
			boolean movido = false;
			if (!removido)
				movido |= fronteirasParciais(desloc, xa, 0);
			if (!removido)
				movido |= fronteirasParciais(desloc, 0, ya);
			return movido;
		} else {
			boolean movido = true;
			if (!removido)
				movido &= fronteirasParciais(desloc, xa, 0);
			if (!removido)
				movido &= fronteirasParciais(desloc, 0, ya);
			return movido;
		}
	}

	/**
	 * Instancia cada orientação de fronteiras dos objetos.
	 * 
	 * @param fronteira
	 * @param xa
	 * @param ya
	 * @return true, caso verdadeiro
	 */
	private boolean fronteirasParciais(List<Fronteiras> fronteira, double xa, double ya) {
		double oxa = xa;
		double oya = ya;
		Fronteiras desde = getFronteiras();

		Fronteiras maisProximo = null;
		double epsilon = 0.01;
		for (int i = 0; i < fronteira.size(); i++) {
			Fronteiras to = fronteira.get(i);
			if (desde.seInterceptar(to))
				continue;

			if (ya == 0) {
				if (to.y0 >= desde.y1 || to.y1 <= desde.y0)
					continue;
				if (xa > 0) {
					double xrd = to.x0 - desde.x1;
					if (xrd >= 0 && xa > xrd) {
						maisProximo = to;
						xa = xrd - epsilon;
						if (xa < 0)
							xa = 0;
					}
				} else if (xa < 0) {
					double xld = to.x1 - desde.x0;
					if (xld <= 0 && xa < xld) {
						maisProximo = to;
						xa = xld + epsilon;
						if (xa > 0)
							xa = 0;
					}
				}
			}

			if (xa == 0) {
				if (to.x0 >= desde.x1 || to.x1 <= desde.x0)
					continue;
				if (ya > 0) {
					double yrd = to.y0 - desde.y1;
					if (yrd >= 0 && ya > yrd) {
						maisProximo = to;
						ya = yrd - epsilon;
						if (ya < 0)
							ya = 0;
					}
				} else if (ya < 0) {
					double yld = to.y1 - desde.y0;
					if (yld <= 0 && ya < yld) {
						maisProximo = to;
						ya = yld + epsilon;
						if (ya > 0)
							ya = 0;
					}
				}
			}
		}
		if (maisProximo != null && maisProximo.origem != null) {
			maisProximo.origem.manipulaColisao(this, oxa, oya);
		}
		if (xa != 0 || ya != 0) {
			pos.x += xa;
			pos.y += ya;
			return true;
		}
		return false;
	}

	/**
	 * Retorna verdadeiro se o objeto não é ultrapassável.
	 * 
	 * @param e
	 * @return true, se verdadeiro
	 */
	public final boolean bloqueia(Entidade e) {
		return bloqueia && e.bloqueia && bloquear(e) && e.bloquear(this);
	}

	/**
	 * Checa se uma entidade é bloqueável ou não.
	 * 
	 * @param e
	 * @return true, se verdadeiro
	 */
	protected boolean bloquear(Entidade e) {
		return true;
	}

	/**
	 * Remover entidade.
	 */
	public void remover() {
		removido = true;
	}

	public void manipulaColisao(Entidade entidade, double xa, double ya) {
		if (this.bloqueia(entidade)) {
			this.colisao(entidade, xa, ya);
			entidade.colisao(this, -xa, -ya);
		}
	}

	/**
	 * Colisao.
	 * 
	 * @param entidade
	 * @param xa
	 * @param ya
	 */
	public void colisao(Entidade entidade, double xa, double ya) { }

	/**
	 * Ferir.
	 * 
	 * @param bala
	 */
	public void ferir(Bala bala) { }

	/**
	 * Dinamite.
	 * 
	 * @param dinamiteExplosaoGrande
	 */
	public void dinamite(DinamiteExplosaoGrande dinamiteExplosaoGrande) { }
}