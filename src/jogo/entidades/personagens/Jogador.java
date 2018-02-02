/*
 * Define o jogador controlado pelo usuário.
 */

package jogo.entidades.personagens;

import java.util.Random;
import jogo.*;
import jogo.construcoes.Construcao;
import jogo.entidades.Bala;
import jogo.entidades.Entidade;
import jogo.entidades.UsarInterface;
import jogo.entidades.animacoes.FumacaAnimacao;
import jogo.entidades.colecionaveis.*;
import jogo.entidades.particulas.Brilho;
import jogo.logica.Logica;
import jogo.tela.*;
import jogo.tiles.*;

/**
 * Define o jogador controlado pelo usuário.
 */
public class Jogador extends Personagem implements ColetarInterface {

	private Teclas teclas;
	private Logica vetorMouse;
	private int atrasoTiro = 0;
	private double xd, yd;

	// Delay do tiro
	private int delay = 0;
	private int tempoFlash = 0;

	private int sugarRaio = 0;
	private boolean atirando;
	public int moedas;
	private int olhando = 0;
	private int tempo = 0;
	private int tempoAndar = 0;
	private Entidade selecionado = null;
	private final static int DISTANCIA_INTERACAO = 20 * 20;
	private boolean vendo;

	// Variáveis da posição de início do jogador 
	private int inicioX;
	private int inicioY;

	// Flash do tiro da arma
	private int flashArmaFrame = 0;
	private double flashArmaX = 0;
	private double flashArmaY = 0;
	private int flashArmaImg = 0;
	
	private int fumaca = 0;
	private Random aleatorio = new Random();
	private boolean morte = false;
	private boolean carregandoSom = true;
	private int esperarReviver = 0;
	public int rubi;

	public Jogador(Teclas teclas, int x, int y) {
		super(x, y);
		this.teclas = teclas;
		vida = 8;
		inicioX = x;
		inicioY = y;
		moedas = 0;
		rubi = 0;
		vetorMouse = new Logica(0, 1);
	}

	public void frame() {
		tempo++;
		iconeMapa = tempo / 3 % 4;
		if (iconeMapa == 3) {
			iconeMapa = 1;
		}

		// Timer após a morte do jogador
		if (morte)
			esperarReviver++;

		if (esperarReviver == 110)
			Main.sons.reproduzirSom("/sons/morte.wav", (float) pos.x, (float) pos.y);

		if (esperarReviver >= 320) {
			pos.set(inicioX, inicioY);
			morte = false;
			vida = 8;
			esperarReviver = 0;
			nivel.setTelaContinue(true);
		}
		
		if (nivel.getDialogo() == 31 && carregando!= null){
			jogarObjeto();
		}
			
		double xa = 0;
		double ya = 0;

		if (!morte) {
			if (tempoFlash > 0) {
				tempoFlash = 0;
			}
			if (tempoFerido > 0) {
				tempoFerido--;
			}
			if (tempoParalizado > 0) {
				tempoParalizado--;
			}
			if (flashArmaFrame > 0) {
				flashArmaFrame--;
			}
			if (nivel.getPause() == false) {
				if (teclas.acima.pressionado) {
					ya--;
				}
				if (teclas.abaixo.pressionado) {
					ya++;
				}
				if (teclas.esquerda.pressionado) {
					xa--;
				}
				if (teclas.direita.pressionado) {
					xa++;
				}

				if (!teclas.atirar.pressionado && xa * xa + ya * ya != 0) {
					vetorMouse.set(xa, ya);
					vetorMouse.normalizar();
					atualizarOlhando();
				}

				if (xa != 0 || ya != 0) {
					int olhando2 = (int) ((Math.atan2(-xa, ya) * 8
							/ (Math.PI * 2) + 8.5)) & 7;
					int dif = olhando - olhando2;
					if (dif >= 4) {
						dif -= 8;
					}
					if (dif < -4) {
						dif += 8;
					}
					if (carregando != null) {
						if (dif > 2 || dif < -4) {
							tempoAndar--;
						} else {
							tempoAndar++;
						}
					}
					if (dif > 2 || dif < -4) {
						tempoAndar--;
					} else {
						tempoAndar++;
					}
					if (tempoAndar >= fumaca) {
						nivel.adicionarEntidade(new FumacaAnimacao(this,
								Arte.poeira, 35 + aleatorio.nextInt(10)));
						fumaca += (15 + aleatorio.nextInt(15));
					}
					if (aleatorio.nextDouble() < 0.02f)
						nivel.adicionarEntidade(new FumacaAnimacao(this,
								Arte.poeira, 35 + aleatorio.nextInt(10)));
					double dd = Math.sqrt(xa * xa + ya * ya);
					double velocidade = getVelocidade() / dd;
					xa *= velocidade;
					ya *= velocidade;
					xd += xa;
					yd += ya;
				}
				if (tempoParalizado > 0) {
					mover(colisaoX, colisaoY);
				} else {
					mover(xd + colisaoX, yd + colisaoY);
				}
				xd *= 0.4;
				yd *= 0.4;
				colisaoX *= 0.8;
				colisaoY *= 0.8;
				flashArmaImg = (flashArmaImg + 1) & 3;
				
				// Jogar um objeto no chão automaticamente quando atirar
				if (carregando != null && teclas.atirar.pressionado) {
					jogarObjeto();
					
				// Impedir que o jogador atire em certas falas do tutorial
				} else if (carregando == null && teclas.atirar.pressionado 
						&& !(nivel.getDialogo() == 27 || nivel.getDialogo() == 29)) {
					atirando = true;
					if (delay > 0) {
						delay--;
					}
					if (atrasoTiro-- <= 0) {
						double dir = Math.atan2(vetorMouse.y, vetorMouse.x)
								+ (aleatorio.nextFloat() - aleatorio
										.nextFloat()) * 0.1;
						xa = Math.cos(dir);
						ya = Math.sin(dir);
						xd -= xa;
						yd -= ya;
						Entidade bala = new Bala(this, xa, ya);
						nivel.adicionarEntidade(bala);
						flashArmaFrame = 3;
						flashArmaX = bala.pos.x + 7 * xa - 8;
						flashArmaY = bala.pos.y + 5 * ya - 8 + 1;
						atrasoTiro = 5;
						if (((Bala) bala).supertiro())
							Main.sons.reproduzirSom("/sons/supertiro.wav",
									(float) pos.x, (float) pos.y);
						else
							Main.sons.reproduzirSom("/sons/tiro.wav",
									(float) pos.x, (float) pos.y);
					}
				} else {
					if (atirando) {
						sugarRaio = 60;
					}
					atirando = false;
					if (sugarRaio > 0) {
						sugarRaio--;
					}
					if (morte) {
						sugarRaio = 0;
					}
					delay = 15;
					atrasoTiro = 0;
				}
			}
			int x = (int) pos.x / Tile.LARGURA;
			int y = (int) pos.y / Tile.ALTURA;

			// Se o jogador estiver carregando algo
			if (carregando != null) {
				// Som ao pegar item.
				if (carregandoSom == true) {
					Main.sons.reproduzirSom("/sons/carregar.wav", (float) pos.x,
							(float) pos.y);
					carregandoSom = false;
				}
				// Posição de carregar.
				carregando.setPos(pos.x, pos.y - 14);
				carregando.frame();

				if (teclas.usar.foiPressionado()) {

					// Colocar algo que está sendo carregado no chão
					if (!(carregando instanceof UsarInterface)
							|| (carregando instanceof UsarInterface)) {
						jogarObjeto();
					}
				}
			} else {
				Entidade maisProximo = null;
				double distanciaMaisProx = Double.MAX_VALUE;
				for (Entidade e : nivel.getEntidadesDevagar(pos.x
						- DISTANCIA_INTERACAO, pos.y - DISTANCIA_INTERACAO, pos.x
						+ DISTANCIA_INTERACAO, pos.y + DISTANCIA_INTERACAO,
						Construcao.class)) {
					double dist = e.pos.distAoQuadrado(getPosInteracao());
					if (dist <= DISTANCIA_INTERACAO && dist < distanciaMaisProx) {
						distanciaMaisProx = dist;
						maisProximo = e;
					}
				}
				if (selecionado != null) {
					((UsarInterface) selecionado).destacar(false);
				}
				if (maisProximo != null && ((UsarInterface) maisProximo).usavel()
						&& !nivel.getPause()) {
					selecionado = maisProximo;
					((UsarInterface) selecionado).destacar(true);
				}
				if (selecionado != null) {
					if (selecionado.pos.distAoQuadrado(getPosInteracao()) > DISTANCIA_INTERACAO) {
						((UsarInterface) selecionado).destacar(false);
						selecionado = null;
					} else if (selecionado instanceof UsarInterface
							&& teclas.usar.foiPressionado() && !nivel.getTelaPause()) {
						((UsarInterface) selecionado).usar(this);
					}
				}
			}
			if (vendo) {
				nivel.revelar(x, y, 5);
			}
		}
	}

	/** Ao morrer, animar moedas e parar música */
	private void animacaoMorte() {
		int animacaoMoedas = 70;

		while (animacaoMoedas > 0) {
			double dir = aleatorio.nextDouble() * Math.PI * 2;
			Tesouro tesouro = new Tesouro(pos.x, pos.y, Math.cos(dir),
					Math.sin(dir), animacaoMoedas / 5);
			tesouro.naoColetavel();
			nivel.adicionarEntidade(tesouro);
			animacaoMoedas--;
		}
		Main.sons.pararMusica();
		Main.sons.reproduzirSom("/sons/jogador_morre.wav", (float) pos.x,
				(float) pos.y);
	}

	public void renderizar(Tela tela) {
		Bitmap[][] jogador = Arte.cesar;

		if (morte) {
			// Não renderizar nada quando jogador está morto.
			return;
		}

		int frame = (tempoAndar / 4 % 6 + 6) % 6;
		int olhando = this.olhando + (carregando != null ? 8 : 0);
		double xFlash = flashArmaX + ((olhando == 0) ? 4 : 0);
		double yFlash = flashArmaY - ((olhando == 0) ? 4 : 0);
		boolean atras = (olhando >= 3 && olhando <= 5);

		if (flashArmaFrame > 0 && atras) {
			tela.plotar(Arte.flashArma[flashArmaImg][0], xFlash, yFlash);
		}

		if (tempoFerido % 2 != 0) {
			tela.blitCores(jogador[frame][olhando], pos.x - Tile.LARGURA / 2,
					pos.y - Tile.ALTURA / 2 - 8, 0x80ff0000);
		} else if (tempoFlash > 0) {
			tela.blitCores(jogador[frame][olhando], pos.x - Tile.LARGURA / 2,
					pos.y - Tile.ALTURA / 2 - 8, 0x80ffff80);
		} else {
			tela.plotar(jogador[frame][olhando], pos.x - Tile.LARGURA / 2, pos.y
					- Tile.ALTURA / 2 - 8);
		}

		if (flashArmaFrame > 0 && !atras) {
			tela.plotar(Arte.flashArma[flashArmaImg][0], xFlash, yFlash);
		}

		renderizarCarregando(tela, (frame == 0 || frame == 3) ? -1 : 0);
	}

	public void colisao(Entidade entity, double xa, double ya) {
		xd += xa * 0.4;
		yd += ya * 0.4;
	}

	public void coletar(Tesouro tesouro) {
		if (!morte) {
			tesouro.remover();
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, -1, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, +1, 0));
			moedas += tesouro.getValorMoedas();
		}
	}

	// Partículas ao coletar rubis
	public void coletarRubi(Rubi tesouro) {
		if (!morte) {
			tesouro.remover();
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, -4, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, -3, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, -2, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, -1, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, +1, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, +2, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, +3, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, +4, 0));
			rubi++;
			
			// Aumentar a quantidade de inimigos no quarto rubi
			if (rubi == 4)
				nivel.setMaxInimigos(12);
			
			// Exibir fala ao coletar oitavo rubi
			if (rubi == 8){
				Main.sons.pararMusica();
				nivel.setDialogo(24, pos.x, pos.y);
			}
				
		}
	}

	public void recuperarVida(Coracao coracao) {
		if (!morte) {
			if (vida < 9) {
				Main.sons.reproduzirSom("/sons/coracao.wav", (float) pos.x,
						(float) pos.y);
			} else if (vida == 9) {
				Main.sons.reproduzirSom("/sons/supertiro_ativado.wav",
						(float) pos.x, (float) pos.y);
				nivel.mensagem(7);
				nivel.setDialogoTempo(37);
			} else if (vida == 10) {
				Main.sons.reproduzirSom("/sons/supertiro_ativado.wav",
						(float) pos.x, (float) pos.y);
			}

			coracao.remover();
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, -1, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, -2, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, -3, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, +2, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, +3, 0));
			nivel.adicionarEntidade(new Brilho(pos.x, pos.y, +1, 0));
			
			if (vida < vidaMaxima)
				vida++;
		}
	}
	
	//Gets e Sets
	
	public double getPoderColeta() {
		return sugarRaio / 60.0;
	}

	public boolean podeColetar() {
		return delay > 0;
	}

	public void flash() {
		tempoFlash = 20;
	}

	public int qtdeMoedas() {
		return moedas;
	}

	public Bitmap getSprite() {
		return null;
	}

	/**
	 * Get na posição possível de interagir com uma entidade.
	 * 
	 * @return Posição
	 */
	private Logica getPosInteracao() {
		return pos.adicionar(new Logica(Math.cos((olhando) * (Math.PI) / 4 + Math.PI
				/ 2), Math.sin((olhando) * (Math.PI) / 4 + Math.PI / 2))
				.escala(30));
	}

	public void carregar(Construcao b) {
		nivel.removerEntidade(b);
		carregando = b;
	}

	public void setDirecao(int olhando) {
		this.olhando = olhando;
	}

	@Override
	protected boolean bloquear(Entidade e) {
		if (carregando != null && e instanceof Bala
				&& ((Bala) e).origem == carregando) {
			return false;
		}
		return true;
	}
	
	public void visao(boolean v) {
		this.vendo = v;
	}

	@Override
	public void ferir(Entidade fonte, int dano) {
		if (imortal) {
			return;
		}

		if (tempoFerido == 0) {
			tempoFerido = 25;
			tempoParalizado = 15;
			vida -= dano;
			Main.sons.reproduzirSom("/sons/dano.wav", (float) pos.x, (float) pos.y);

			double dir = aleatorio.nextDouble() * Math.PI * 2;
			Coracao coracao = new Coracao(pos.x + 1, pos.y + 1, Math.cos(dir),
					Math.sin(dir));
			coracao.naoColetavel();
			if (dano > 2) {
				nivel.adicionarEntidade(coracao);
				dir = aleatorio.nextDouble() * Math.PI * 2;
				coracao = new Coracao(pos.x + 1, pos.y + 1, Math.cos(dir),
						Math.sin(dir));
				coracao.naoColetavel();
				coracao.naoColetavel();
				nivel.adicionarEntidade(coracao);
			} else if (dano > 1 && dano <= 2) {
				nivel.adicionarEntidade(coracao);
			}
			/*
			 * Se o jogador estiver carregando algo quando for ferido, ele
			 * deixará cair no chão
			 */
			if (carregando != null) {
				jogarObjeto();
			}

			if (vida <= 0) {
				vida = 0;
				/*
				 * Se o jogador morrer o que ele estiver segurando irá cair no
				 * chão
				 */
				if (carregando != null) {
					jogarObjeto();
				}

				animacaoMorte();
				morte = true;
			} else {

				double dist = fonte.pos.dist(pos);
				colisaoX = (pos.x - fonte.pos.x) / dist * 10;
				colisaoY = (pos.y - fonte.pos.y) / dist * 10;
			}
		}
	}

	// Dano que o jogador pode provocar com uma única bala
	@Override
	public void ferir(Bala bala) {
		ferir(bala, 1);
	}

	// Jogar objeto carregado no chao
	private void jogarObjeto() {
		Logica buildPos = pos.clone();
		carregando.removido = false;
		carregando.xSlide = vetorMouse.x * 5;
		carregando.ySlide = vetorMouse.y * 5;
		carregando.tempoParalizado = 10;
		carregando.setPos(buildPos.x, buildPos.y);
		nivel.adicionarEntidade(carregando);
		Main.sons.reproduzirSom("/sons/colocar.wav", (float) pos.x, (float) pos.y);
		carregandoSom = true;
		carregando = null;
	}

	public void miraMouse(int x, int y) {
		// Quando o jogo não estiver pausado, olhar para a mira do mouse
		if (!nivel.getPause()) {
			vetorMouse.set(x, y);
			vetorMouse.normalizar();
			atualizarOlhando();
		}
	}

	private void atualizarOlhando() {
		olhando = (int) ((Math.atan2(-vetorMouse.x, vetorMouse.y) * 8
				/ (Math.PI * 2) + 8.5)) & 7;
	}
}