/*
 * Main do jogo.
 */

package jogo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.*;
import java.awt.Robot;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;

import jogo.entidades.personagens.Jogador;
import jogo.menus.*;
import jogo.sons.Sons;
import jogo.tela.Bitmap;
import jogo.tela.Tela;
import jogo.tiles.Tile;

/**
 * Main do jogo.
 */
public class Main extends Canvas implements Runnable, MouseMotionListener,
		MouseListener, MouseInterface, KeyListener {

	/** Serial Version ID. */
	private static final long serialVersionUID = 1L;

	/** A largura da janela do jogo. */
	public static final int LARGURA_JOGO = 512;

	/** A altura da janela do jogo. */
	public static final int ALTURA_JOGO = LARGURA_JOGO * 3 / 4;

	/** Tempo de frames necessários para a música do menu dar loop. */
	public static final double TEMPO_MUSICA_MENU = 1135;

	/** Tempo de frames necessários para a música do nível dar loop. */
	public static final double TEMPO_MUSICA_NIVEL = 2060;

	/** Tempo de espera após o usuário dar start no jogo. */
	private static final double TEMPO_CONFIRMA_MENU = 100;
	
	/** Tempo de espera para loop em video. */
	private static final double TEMPO_LOOP_VIDEO = 25;
	
	/** Tempo de espera para filme de final do jogo 1. */
	private static final double TEMPO_FINAL_1 = 600;
	
	/** Tempo de espera para filme de final do jogo 2. */
	private static final double TEMPO_FINAL_2 = 1750;

	/** A escala do jogo. */
	private static int escala = 2;
	
	/** Variável que indica que o jogo saiu do menu. */
	private boolean iniciado = false;

	/** Variável para esconder o mouse original. */
	private Cursor esconderMouse;

	/** Limitação do framerate do jogo. */
	private double framerate = 60;

	/** Criação de uma nova tela para iniciar o jogo. */
	private Tela tela = new Tela(LARGURA_JOGO, ALTURA_JOGO);

	/** Criação do nível do jogo. */
	private Nivel nivel;

	/** JFrame que irá conter o jogo em si. */
	private static JFrame frameJogo;

	/** JFrame que irá conter os videos do jogo. */
	private static JFrame frameVideo;
	
	/** JFrame que irá conter os filmes do jogo. */
	private static JFrame frameFilme;

	/** Cria um painel para reprodução de videos dentro do frameVideo. */
	private static JFXPanel painelVideo = new JFXPanel();
	
	/** Cria um painel para reprodução de filmes dentro do frameFilme. */
	private static JFXPanel painelFilme = new JFXPanel();

	/** Inicializando os videos. */
	public static MediaView mediaViewVideo = null;
	public static MediaView mediaViewFilme = null;
	
	private static MediaPlayer video0 = null;
	private static MediaPlayer video1 = null;
	private static MediaPlayer video2 = null;
	private static MediaPlayer video3 = null;
	private static MediaPlayer video4 = null;
	private static MediaPlayer video5 = null;
	private static MediaPlayer video6 = null;
	private static MediaPlayer video7 = null;
	private static MediaPlayer video8 = null;
	private static MediaPlayer video9 = null;
	private static MediaPlayer video10 = null;
	private static MediaPlayer video11 = null;
	private static MediaPlayer video12 = null;
	private static MediaPlayer video13 = null;
	private static MediaPlayer video14 = null;
	private static MediaPlayer video15 = null;
	private static MediaPlayer video16 = null;
	private static MediaPlayer video17 = null;
	private static MediaPlayer filme0 = null;
	private static MediaPlayer filme1 = null;
	private static MediaPlayer filme2 = null;
	
	/** Loop do video. */
	private static boolean videoLoop = false;
	
	/** Stack de botões do menu. */
	private Stack<MenuBotoes> menuBotoes = new Stack<MenuBotoes>();

	/** Botões do mouse */
	public static BotoesMouse botoesMouse = new BotoesMouse();

	/** Teclas de entrada */
	public Teclas teclas = new Teclas();

	/** Instanciar o jogador. */
	public Jogador jogador;

	/** Os sons do jogo. */
	public static Sons sons = new Sons();

	/** Robô awt responsável pelos controles do mouse. */
	private Robot robo;

	/** Cria um menu principal. */
	private TelaMenu menu = new TelaMenu(LARGURA_JOGO);

	/** O jogador irá atirar para o alvo do mouse se essa variável for true. */
	private boolean mirandoAlvo;
	
	private static int tipoVideo = 0;

	/** contador de frames para o tempo restante de músicas. */
	public static double tempoRestante = TEMPO_MUSICA_MENU;
	
	/** contador de frames para o tempo restante do loop dos videos. */
	private static double tempoLoop = TEMPO_LOOP_VIDEO;

	/** Variável que informa o jogo se o filme de abertura já executou ou não */
	private boolean filmeAbertura = false;
	
	/** Variável que informa o jogo se o filme de final 1 já executou ou não */
	private static boolean filmeFinal1 = false;
	
	/** Variável que informa o jogo se o filme de final 2 já executou ou não */
	private static boolean filmeFinal2 = false;
	
	public static boolean prologo = true;
	
	/** Booleanos responsáveis por indicar o término de etapas do tutorial */
	public static boolean tutorial = false;
	public static boolean tutorialAcima = false;
	public static boolean tutorialAbaixo = false;
	public static boolean tutorialEsquerda = false;
	public static boolean tutorialDireita = false;

	/**
	 * Instancia um novo Main.
	 */
	public Main() {

		// Criação da janela, manuseio de entrada e menu
		Dimension dim = new Dimension(LARGURA_JOGO * escala, ALTURA_JOGO * escala);
		
		this.setPreferredSize(new Dimension(dim));
		this.setMinimumSize(new Dimension(dim));
		this.setMaximumSize(new Dimension(dim));
		this.setSize(dim);
		this.addKeyListener(new ManuseioEntrada(teclas));
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		adicionarMenu(menu);
		addKeyListener(this);
		
		// Vamos criar um robot para manuseio do tiro através do mouse
		if (robo == null)
			try {
				robo = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
		}
	}

	public void mouseDragged(MouseEvent arg0) {	}
	public void mouseMoved(MouseEvent arg0) { }
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }

	public void mouseExited(MouseEvent e) {
		botoesMouse.liberarTudo();
	}

	/*
	 * Atirar quando o jogador pressionar um botão do mouse. Ação equivale a
	 * pressionar a barra de espaço
	 * 
	 */
	public void mousePressed(MouseEvent e) {
		botoesMouse.proximoEstado(e.getButton(), true);

		/*
		 * A variável abaixo garante que o jogador só mire ao pressionar o 
		 * botão do mouse.
		 */
		mirandoAlvo = true;
		robo.keyPress(KeyEvent.VK_SPACE);
	}

	// Ação de soltar um botão pressionado
	public void mouseReleased(MouseEvent e) {
		botoesMouse.proximoEstado(e.getButton(), false);
		mirandoAlvo = false;
		robo.keyRelease(KeyEvent.VK_SPACE);
	}

	/**
	 * Variável chamada após criação do Jframe que contém o jogo.
	 */
	private void iniciar() {
		iniciado = true;
		// Iniciar Thread
		Thread thread = new Thread(this);
		thread.start();
		
	}

	/**
	 * Inicializa um cursor personalizado para o jogo.
	 */
	private void init() {
		try {
			esconderMouse = Toolkit.getDefaultToolkit().createCustomCursor(
					new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB),
					new Point(0, 0), "empty");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		setFocusTraversalKeysEnabled(false);
		requestFocus();

		// Vamos esconder o mouse do sistema, já que estamos criando o nosso
		setCursor(esconderMouse);
	}

	/**
	 * Cria o nivel com base em no arquivo de imagem nivel.bmp
	 */
	public void criarNivel() {

		try {
			nivel = Nivel.lerArquivo("/nivel/nivel.bmp");
		} catch (Exception ex) {
			throw new RuntimeException("Erro: Nao foi possível acessar nivel.bmp", ex);
		}
		nivel.iniciar();

		// Inicializar o jogador e sua posicao
		jogador = new Jogador(teclas, nivel.larguraNivel * Tile.LARGURA / 2 - 16,
				(nivel.alturaNivel - 5) * Tile.ALTURA - 176);
		jogador.setDirecao(4);
		nivel.adicionarEntidade(jogador);
		jogador.visao(true);
		frameVideo.setVisible(true);
		frameJogo.setVisible(true);
		
		// Ativar tutorial
		tutorial  = true;
	}
	
	public static void finalJogo1 (){
		frameFilme.setVisible(true);
		frameVideo.setVisible(false);
		tipoFilme(1);
		filmeFinal1 = true;
		tempoRestante = TEMPO_FINAL_1;
	}
	
	public static void finalJogo2 (){
		frameFilme.setVisible(true);
		frameVideo.setVisible(false);
		tipoFilme(2);
		filmeFinal2 = true;
		tempoRestante = TEMPO_FINAL_2;
	}

	/**
	 * Renderizar o jogo.
	 */
	public void run() {

		long ultimoTempo = System.nanoTime();
		double naoProcessado = 0;
		long ultimoTempo1 = System.currentTimeMillis();

		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		int paraFrame = 0;

		long ultimoTempoRenderizacao = System.nanoTime();
		int min = 999999999;
		int max = 0;

		while (iniciado) {
			if (!this.hasFocus()) {
				teclas.liberar();
			}

			double nsPorFrame = 1000000000.0 / framerate;
			boolean deveRenderizar = false;
			while (naoProcessado >= 1) {
				paraFrame++;
				naoProcessado -= 1;
			}

			int contadorFrame = paraFrame;
			if (paraFrame > 0 && paraFrame < 3) {
				contadorFrame = 1;
			}
			if (paraFrame > 20) {
				paraFrame = 20;
			}

			for (int i = 0; i < contadorFrame; i++) {
				paraFrame--;
				frame();
				deveRenderizar = true;
			}

			// Buffer
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(2);
				continue;
			}
			if (deveRenderizar) {
				Graphics g = bs.getDrawGraphics();
				renderizar(g);
				long tempoRenderizacao = System.nanoTime();
				int tempoPassou = (int) (tempoRenderizacao - ultimoTempoRenderizacao);
				if (tempoPassou < min) {
					min = tempoPassou;
				}
				if (tempoPassou > max) {
					max = tempoPassou;
				}
				ultimoTempoRenderizacao = tempoRenderizacao;
			}

			long tempoAtual = System.nanoTime();
			naoProcessado += (tempoAtual - ultimoTempo) / nsPorFrame;
			ultimoTempo = tempoAtual;

			if (deveRenderizar) {
				if (bs != null) {
					bs.show();
				}
			}

			if (System.currentTimeMillis() - ultimoTempo1 > 1000) {
				ultimoTempo1 += 1000;
			}
		}
	}

	/**
	 * Renderizar gráficos.
	 * 
	 * @param g
	 */
	private void renderizar(Graphics g) {
		if (nivel != null) {
			int xScroll = (int) (jogador.pos.x - tela.base / 2);
			int yScroll = (int) (jogador.pos.y - (tela.altura - 24) / 2);
			sons.posOuvinte((float) jogador.pos.x,
					(float) jogador.pos.y);
			nivel.renderizar(tela, xScroll, yScroll);
		}
		
		if (!menuBotoes.isEmpty()) {
			menuBotoes.peek().renderizar(tela);
		}

		if (jogador != null && menuBotoes.size() == 0) {
			// Contador de Vida
			Fonte.escrever(tela, jogador.vida + "", 340, tela.altura - 19);
			// Contador de Moedas
			Fonte.escrever(tela, "" + jogador.moedas, 340, tela.altura - 33);
			// Contador de Rubi
			Fonte.escrever(tela, "" + jogador.rubi + "/8", 385, tela.altura - 19);
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		/** Determinar dimensão do tamanho da tela */
		Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
		// Tamanho da tela dos filmes
		frameFilme.setSize(tamanhoTela.width, tamanhoTela.height);
		
		// Modificar a tela do jogo dependendo da resolução do monitor
		// Para resoluções altas
		if (tamanhoTela.width >= 1600) {
			g.translate((getWidth() - LARGURA_JOGO * (escala + 1)) / 2,
					(getHeight() - tamanhoTela.height) / 2);
			g.clipRect(0, 0, LARGURA_JOGO * (escala + 1), 
					tamanhoTela.height);

			if (!menuBotoes.isEmpty() || nivel != null) {
				// renderizar mouse
				renderizarMouse(tela, botoesMouse);
				g.drawImage(tela.imagem, 0, 0, LARGURA_JOGO * (escala + 1), tamanhoTela.height, null);
			}
		}
		else if (tamanhoTela.width < 1600 && tamanhoTela.width > 800) {
			
			g.translate((getWidth() - LARGURA_JOGO * escala) / 2,
					(getHeight() - tamanhoTela.height) / 2);
			g.clipRect(0, 0, LARGURA_JOGO * escala, 
					tamanhoTela.height);

			if (!menuBotoes.isEmpty() || nivel != null) {
				// renderizar mouse
				renderizarMouse(tela, botoesMouse);
				g.drawImage(tela.imagem, 0, 0, LARGURA_JOGO * escala, tamanhoTela.height, null);
			}
		}
		// Para o restante das resoluções
		else {
			
			g.translate((getWidth() - LARGURA_JOGO * (escala - 1)) / 2,
					(getHeight() - tamanhoTela.height) / 2);
			g.clipRect(0, 0, LARGURA_JOGO * (escala - 1), 
					tamanhoTela.height);

			if (!menuBotoes.isEmpty() || nivel != null) {
				// renderizar mouse
				renderizarMouse(tela, botoesMouse);
				g.drawImage(tela.imagem, 0, 0, LARGURA_JOGO * (escala - 1), tamanhoTela.height, null);
			}
		}
	}

	/**
	 * Renderiza mouse na tela
	 * 
	 * @param tela
	 * @param botoesMouse
	 */
	private void renderizarMouse(Tela tela, BotoesMouse botoesMouse) {

		int tamanhoCrosshair = 17;
		int metadeCrosshair = tamanhoCrosshair / 2;

		Bitmap marcador = new Bitmap(tamanhoCrosshair, tamanhoCrosshair);

		// Linha horizontal
		for (int i = 0; i < tamanhoCrosshair; i++) {
			if (i >= metadeCrosshair - 3 && i <= metadeCrosshair + 3)
				continue;
			
			// Crosshair terá a cor branca
			marcador.pixels[metadeCrosshair + i * tamanhoCrosshair] = 0xffffffff;
			marcador.pixels[i + metadeCrosshair * tamanhoCrosshair] = 0xffffffff;
		}
		tela.plotar(marcador, botoesMouse.getX() / escala - metadeCrosshair
				- 2, botoesMouse.getY() / escala - metadeCrosshair - 2);
	}

	/**
	 * Executar ações a cada frame do jogo.
	 */
	private void frame() {
		
    	if (getVideoLoop() == true) {
    		// O video10 é longo e possui tempo diferenciado
    		if (tempoLoop < 0 && tipoVideo == 10){
    			video10.seek(video10.getStartTime());
    			tempoLoop = TEMPO_LOOP_VIDEO;
    		}
    		else if (tempoLoop < 0 && tipoVideo != 10){
        		video1.seek(video1.getStartTime());
        		video2.seek(video2.getStartTime());
        		video3.seek(video3.getStartTime());
        		video4.seek(video4.getStartTime());
        		video5.seek(video5.getStartTime());
        		video6.seek(video6.getStartTime());
        		video7.seek(video7.getStartTime());
        		video8.seek(video8.getStartTime());
        		video9.seek(video9.getStartTime());  
        		video10.seek(video10.getStartTime());
        		video11.seek(video11.getStartTime());
        		video12.seek(video12.getStartTime());
        		video13.seek(video13.getStartTime());
        		video14.seek(video14.getStartTime());
        		video15.seek(video15.getStartTime());
        		video16.seek(video16.getStartTime());
        		video17.seek(video17.getStartTime());
        		tempoLoop = TEMPO_LOOP_VIDEO;
    		}
    	}
    	    	
		// A tela de video sempre deve estar na frente da JFrame do jogo
		if (frameVideo.isFocused())
			frameJogo.setVisible(true);

		// Quando estiver na tela de pause, pressionar enter encerra o jogo
		if (teclas.enter.foiPressionado() && nivel.getTelaPause()) {
				System.exit(0);
		}
		
		// Quando o jogador tiver atirado durante o tutorial
		if (teclas.atirar.foiPressionado() && tutorial && nivel.getDialogo() == 31) {
			nivel.setDialogo(32, jogador.pos.x, jogador.pos.y);
		}
		
		// Capturar se o jogador pressionou qualquer tecla direcional no tutorial
		if (teclas.acima.foiPressionado() && tutorial && nivel.getDialogo() == 27) {
			tutorialAcima  = true;
		}
		if (teclas.abaixo.foiPressionado() && tutorial && nivel.getDialogo() == 27) {
			tutorialAbaixo  = true;
		}
		if (teclas.esquerda.foiPressionado() && tutorial && nivel.getDialogo() == 27) {
			tutorialEsquerda  = true;
		}
		if (teclas.direita.foiPressionado() && tutorial && nivel.getDialogo() == 27) {
			tutorialDireita  = true;
		}
		
		// Reiniciar musica e retirar tela de continue se o jogador reiniciar
		if (teclas.enter.foiPressionado() && nivel.getTelaContinue()){
			sons.reproduzirSom("/sons/correto.wav", (float)jogador.pos.x, (float)jogador.pos.y);
			sons.reiniciarMusicaNivel();
			tempoRestante = TEMPO_MUSICA_NIVEL;
			nivel.setDialogoTempo(0);
			nivel.setTelaContinue(false);
			nivel.setPause(false);
		}

		// Finalizando o jogo
		if (teclas.esc.foiPressionado() && nivel.getTelaContinue())
			System.exit(0);
		
		// Mudar o texto da fala quando falando com alguém
		if ((teclas.enter.foiPressionado() || teclas.atirar.foiPressionado()
				|| teclas.usar.foiPressionado()) && nivel.getDialogo() > 0) {
			if (nivel.getLinha() > 0 && nivel.getTempoPermanencia() == 0)
				sons.reproduzirSom("/sons/item_menu.wav", (float)jogador.pos.x, (float)jogador.pos.y);
			nivel.proximaLinha();
		}
		
		// Pausando o jogo
		if (teclas.esc.foiPressionado()) {
			if (!nivel.getPause()) {
				Main.sons.reproduzirSom("/sons/pause.wav", (float)jogador.pos.x, (float)jogador.pos.y);
				nivel.setPause(true);
				if (nivel.getTelaPause())
					nivel.setTelaPause(false);
				else
					nivel.setTelaPause(true);
			} else if (nivel.getPause()){
				Main.sons.reproduzirSom("/sons/pause.wav", (float)jogador.pos.x, (float)jogador.pos.y);
				if (nivel.getLinha() == 0)
					nivel.setPause(false);
				if (nivel.getTelaPause())
					nivel.setTelaPause(false);
				else
					nivel.setTelaPause(true);
			}
		}

		// Timer
		tempoRestante -= 0.2;
		
		// Timer para loop do video
		if (getVideoLoop() == true)
			tempoLoop -= 0.2;

		// Reiniciando musica do menu
		if (tempoRestante <= 0 && nivel == null && filmeAbertura == false) {
			sons.pararMusica();
			sons.reiniciarMusicaMenu();
			tempoRestante = TEMPO_MUSICA_MENU;
		}
		
		// Manuseio de filme de abertura
		if (filmeAbertura && tempoRestante <= 0) {
			criarNivel();
			frameFilme.setVisible(false);
			filmeAbertura = false;
		}
		
		// Finalizar o jogo quando terminar o filme de final do jogo 1
		if (tempoRestante <= 0 && filmeFinal1 == true) {
			System.exit(0);
		}
		
		// Finalizar o jogo quando terminar o filme de final do jogo 2
		if (tempoRestante <= 0 && filmeFinal2 == true) {
			System.exit(0);
		}
		
		// Reiniciando musica do nível
		if (tempoRestante <= 0 && iniciado == true && !tutorial) {
			sons.pararMusica();
			sons.reiniciarMusicaNivel();
			tempoRestante = TEMPO_MUSICA_NIVEL;
		}

		if (nivel != null) {
			teclas.frame();
			// Atualizar posição do mouse
			if (mirandoAlvo)
				jogador.miraMouse(
						((botoesMouse.getX() / escala) - (tela.base / 2)),
						(((botoesMouse.getY() / escala) + 24) - (tela.altura / 2)));
			nivel.frame();
		}

		botoesMouse.setPosicao(getMousePosition());
		if (!menuBotoes.isEmpty()) {
			menuBotoes.peek().frame(botoesMouse);
		}
		botoesMouse.frame();
	}

	/**
	 * Inicializar Jframe do video
	 */
	private static void inicializarVideo() {
		frameVideo = new JFrame();
		frameVideo.setAlwaysOnTop(true);
		frameVideo.add(painelVideo);
		frameVideo.setResizable(false);
		// Esconder janela
		frameVideo.dispose();
		// Tirar decoracoes
		frameVideo.setUndecorated(true);
		// Exibir janela
		frameVideo.setVisible(false);
		frameVideo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				reproduzirVideo(painelVideo);
			}
		});
	}
	
	/**
	 * Inicializar Jframe dos filmes
	 */
	private static void inicializarFilme() {
		frameFilme = new JFrame();
		frameFilme.setAlwaysOnTop(true);
		frameFilme.add(painelFilme);
		// Esconder janela
		frameFilme.dispose();
		// Tirar decoracoes
		frameFilme.setUndecorated(true);
		// Exibir janela
		frameFilme.setVisible(false);
		frameFilme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameFilme.setLocation(0, 0);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				reproduzirFilme(painelFilme);
			}
		});
	}
	
	/**
	 * Determinar o tipo de video a reproduzir na chamada
	 * @param tipo
	 */
	public static void tipoVideo (int tipo){
		tipoVideo = tipo;
		
		if (tipoVideo == 0){
			mediaViewVideo.setMediaPlayer(video0);
			video0.play();
			setVideoLoop(false);
			tempoLoop = 0;
			
			video0.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video0.stop();
	            }
	        });
		}	
		if (tipoVideo == 1){
			mediaViewVideo.setMediaPlayer(video1);
			video1.play();
			setVideoLoop(true);
			
			video1.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video1.stop();
	            }
	        });

		}	
		if (tipoVideo == 2){
			mediaViewVideo.setMediaPlayer(video2);
        	video2.play();
        	setVideoLoop(true);
        	
			video2.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	            	video2.stop();
	            }
	        });
			
		}	
		if (tipoVideo == 3){
			mediaViewVideo.setMediaPlayer(video3);
			video3.play();
			setVideoLoop(true);
			
			video3.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video3.stop();
	            }
	        });

		}
		if (tipoVideo == 4){
			mediaViewVideo.setMediaPlayer(video4);
			video4.play();
			setVideoLoop(true);
			
			video4.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video4.stop();
	            }
	        });

		}	
		if (tipoVideo == 5){
			mediaViewVideo.setMediaPlayer(video5);
			video5.play();
			setVideoLoop(true);
			
			video5.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video5.stop();
	            }
	        });

		}	
		if (tipoVideo == 6){
			mediaViewVideo.setMediaPlayer(video6);
			video6.play();
			setVideoLoop(true);
			
			video6.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video6.stop();
	            }
	        });

		}	
		if (tipoVideo == 7){
			mediaViewVideo.setMediaPlayer(video7);
			video7.play();
			setVideoLoop(true);
			
			video7.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video7.stop();
	            }
	        });

		}	
		if (tipoVideo == 8){
			mediaViewVideo.setMediaPlayer(video8);
			video8.play();
			setVideoLoop(true);
			
			video8.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video8.stop();
	            }
	        });

		}
		if (tipoVideo == 9){
			mediaViewVideo.setMediaPlayer(video9);
			video9.play();
			setVideoLoop(true);
			
			video9.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video9.stop();
	            }
	        });

		}
		if (tipoVideo == 10){
			mediaViewVideo.setMediaPlayer(video10);
			video10.play();
			setVideoLoop(true);
			
			video10.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video10.stop();
	            }
	        });

		}
		if (tipoVideo == 11){
			mediaViewVideo.setMediaPlayer(video11);
			video11.play();
			setVideoLoop(true);
			
			video11.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video11.stop();
	            }
	        });

		}
		if (tipoVideo == 12){
			mediaViewVideo.setMediaPlayer(video12);
			video12.play();
			setVideoLoop(true);
			
			video12.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video12.stop();
	            }
	        });

		}
		if (tipoVideo == 13){
			mediaViewVideo.setMediaPlayer(video13);
			video13.play();
			setVideoLoop(true);
			
			video13.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video13.stop();
	            }
	        });

		}
		if (tipoVideo == 14){
			mediaViewVideo.setMediaPlayer(video14);
			video14.play();
			setVideoLoop(true);
			
			video14.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video14.stop();
	            }
	        });

		}
		if (tipoVideo == 15){
			mediaViewVideo.setMediaPlayer(video15);
			video15.play();
			setVideoLoop(true);
			
			video15.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video15.stop();
	            }
	        });

		}
		if (tipoVideo == 16){
			mediaViewVideo.setMediaPlayer(video16);
			video16.play();
			setVideoLoop(true);
			
			video16.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video16.stop();
	            }
	        });

		}
		if (tipoVideo == 17){
			mediaViewVideo.setMediaPlayer(video17);
			video17.play();
			setVideoLoop(true);
			
			video17.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                video17.stop();
	            }
	        });

		}
	}
	
	/**
	 * Determinar o tipo de filme a reproduzir na chamada
	 * @param tipo
	 */
	public static void tipoFilme (int tipo){		
		if (tipo == 0){
			mediaViewFilme.setMediaPlayer(filme0);
			filme0.play();
			
			filme0.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                filme0.stop();
	            }
	        });
		}	
		if (tipo == 1){
			mediaViewFilme.setMediaPlayer(filme1);
			filme1.play();
			
			filme1.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                filme1.stop();
	            }
	        });
		}
		if (tipo == 2){
			mediaViewFilme.setMediaPlayer(filme2);
			filme2.play();
			
			filme2.setOnEndOfMedia(new Runnable() {
	            @Override public void run() {
	                filme2.stop();
	            }
	        });
		}
	}	

	private static void reproduzirVideo(JFXPanel painelJFX) {
		
		video0 = new MediaPlayer(new Media(Main.class.getResource("/videos/rosto.mp4").toString()));	
		video1 = new MediaPlayer(new Media(Main.class.getResource("/videos/oi.mp4").toString()));
		video2 = new MediaPlayer(new Media(Main.class.getResource("/videos/eu.mp4").toString()));
		video3 = new MediaPlayer(new Media(Main.class.getResource("/videos/aqui.mp4").toString()));
		video4 = new MediaPlayer(new Media(Main.class.getResource("/videos/ter.mp4").toString()));
		video5 = new MediaPlayer(new Media(Main.class.getResource("/videos/explodir.mp4").toString()));
		video6 = new MediaPlayer(new Media(Main.class.getResource("/videos/direita.mp4").toString()));
		video7 = new MediaPlayer(new Media(Main.class.getResource("/videos/ir.mp4").toString()));
		video8 = new MediaPlayer(new Media(Main.class.getResource("/videos/perigo.mp4").toString()));
		video9 = new MediaPlayer(new Media(Main.class.getResource("/videos/prosseguir.mp4").toString()));
		video10 = new MediaPlayer(new Media(Main.class.getResource("/videos/armario.mp4").toString()));
		video11 = new MediaPlayer(new Media(Main.class.getResource("/videos/a.mp4").toString()));
		video12 = new MediaPlayer(new Media(Main.class.getResource("/videos/b.mp4").toString()));
		video13 = new MediaPlayer(new Media(Main.class.getResource("/videos/r.mp4").toString()));
		video14 = new MediaPlayer(new Media(Main.class.getResource("/videos/i.mp4").toString()));
		video15 = new MediaPlayer(new Media(Main.class.getResource("/videos/castelo.mp4").toString()));
		video16 = new MediaPlayer(new Media(Main.class.getResource("/videos/porta.mp4").toString()));
		video17 = new MediaPlayer(new Media(Main.class.getResource("/videos/com.mp4").toString()));
		
		// Video inicial
		mediaViewVideo = new MediaView(video0);
	
		final DoubleProperty largura = mediaViewVideo.fitWidthProperty();
		final DoubleProperty altura = mediaViewVideo.fitHeightProperty();

		largura.bind(Bindings.selectDouble(mediaViewVideo.sceneProperty(), "width"));
		altura.bind(Bindings.selectDouble(mediaViewVideo.sceneProperty(), "height"));

		mediaViewVideo.setPreserveRatio(false);

		Group root = new Group();
		root.getChildren().add(mediaViewVideo);
		final Scene scene = new Scene(root, 960, 540);
		painelJFX.setScene(scene);
	}
	
	private static void reproduzirFilme(JFXPanel painelJFX) {
		
		filme0 = new MediaPlayer(new Media(Main.class.getResource("/videos/entrada.mp4").toString()));
		filme1 = new MediaPlayer(new Media(Main.class.getResource("/videos/final1.mp4").toString()));
		filme2 = new MediaPlayer(new Media(Main.class.getResource("/videos/final2.mp4").toString()));
		
		// Filme inicial
		mediaViewFilme = new MediaView(filme0);
	
		final DoubleProperty largura = mediaViewFilme.fitWidthProperty();
		final DoubleProperty altura = mediaViewFilme.fitHeightProperty();

		largura.bind(Bindings.selectDouble(mediaViewFilme.sceneProperty(), "width"));
		altura.bind(Bindings.selectDouble(mediaViewFilme.sceneProperty(), "height"));

		mediaViewFilme.setPreserveRatio(false);
		
		Group root = new Group();
		root.getChildren().add(mediaViewFilme);
		Scene scene = new Scene(root, 960, 540);
		painelJFX.setScene(scene);
	}

	private static Component video() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				inicializarVideo();
			}
		});
		return painelVideo;
	}
	
	private static Component filme() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				inicializarFilme();
			}
		});
		return painelVideo;
	}

	/**
	 * O método Main do jogo.
	 * 
	 * @param args
	 * @throws IOException	 * @throws NoPlayerException
	 */
	public static void main(String[] args) {
		Main jogo = new Main();
		frameJogo = new JFrame();
		JPanel painelJogo = new JPanel(new BorderLayout());
		painelJogo.add(jogo);
		frameJogo.add(filme());
		frameJogo.add(video());
		frameJogo.add(painelJogo);
		frameJogo.pack();
		frameJogo.setResizable(false);
		frameJogo.setSize(new Dimension(frameJogo.getInsets().left + frameJogo.getInsets().right + LARGURA_JOGO * Main.escala,
				frameJogo.getInsets().top + frameJogo.getInsets().bottom + ALTURA_JOGO * Main.escala));
		// Esconder janela
		frameJogo.dispose();
		frameJogo.setLocationRelativeTo(null);
		frameJogo.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Tirar decoracoes
		frameJogo.setUndecorated(true);
		// Tela cheia
		frameJogo.setExtendedState(Frame.MAXIMIZED_BOTH);
		// Exibir janela
		frameJogo.setLocationRelativeTo(null);
		frameJogo.setVisible(true);
		frameJogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Iniciar jogo
		jogo.iniciar();
		
		// Iniciar música
		sons.iniciarMusicaMenu(); 
	}

	// Procedimentos a tomar quando o jogador selecionar um dos botões do menu 
	public void botaoPressionado(Botao button) {
		// Iniciar jogo
		if (button.getId() == TelaMenu.INICIAR_JOGO_ID && !prologo) {
			filmeAbertura = true;
			sons.pararMusica();
			sons.reproduzirSom("/sons/confirma.wav", 0, 0);
			tempoRestante = TEMPO_CONFIRMA_MENU;
			frameFilme.setVisible(true);
			tipoFilme(0);
			// Posição do frameVideo na tela
			posicionaVideo();
			apagarMenu();
		}
		// Sair do jogo
		else if (button.getId() == TelaMenu.SAIR_JOGO_ID && !prologo) {
			System.exit(0);
		}
	}

	/**
	 * Apaga o menu.
	 */
	private void apagarMenu() {
		while (!menuBotoes.isEmpty()) {
			menuBotoes.pop();
		}
	}
	
	private void posicionaVideo() {
		Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
		
		// A posição da Jframe de video se adapta conforme a resolução da tela
		if (tamanhoTela.width == 1152 && tamanhoTela.height == 864) {
			frameVideo.setSize(125, 138);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 104, (tamanhoTela.height) - 167);
		}
		else if (tamanhoTela.width == 1920 && tamanhoTela.height == 1080) {
			frameVideo.setSize(186, 173);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 251, (tamanhoTela.height) - 208);
		}
		else if (tamanhoTela.width == 1680 && tamanhoTela.height == 1050) {
			frameVideo.setSize(187, 165);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 132, (tamanhoTela.height) - 201);
		}
		else if (tamanhoTela.width == 1600 && tamanhoTela.height == 1024) {
			frameVideo.setSize(187, 163);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 188, (tamanhoTela.height) - 197);
		}
		else if (tamanhoTela.width == 1600 && tamanhoTela.height == 900) {
			frameVideo.setSize(187, 144);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 92, (tamanhoTela.height) - 173);
		}
		else if (tamanhoTela.width == 1440 && tamanhoTela.height == 900) {
			frameVideo.setSize(124, 144);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 248, (tamanhoTela.height) - 173);
		}
		else if (tamanhoTela.width == 1400 && tamanhoTela.height == 1050) {
			frameVideo.setSize(124, 165);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 228, (tamanhoTela.height) - 201);
		}
		else if (tamanhoTela.width == 1280 && tamanhoTela.height == 1024) {
			frameVideo.setSize(124, 163);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 168, (tamanhoTela.height) - 197);
		}
		else if (tamanhoTela.width == 1280 && tamanhoTela.height == 960) {
			frameVideo.setSize(124, 152);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 168, (tamanhoTela.height) - 184);
		}
		else if (tamanhoTela.width == 1280 && tamanhoTela.height == 800) {
			frameVideo.setSize(124, 127);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 168, (tamanhoTela.height) - 153);
		}
		else if (tamanhoTela.width == 1280 && tamanhoTela.height == 768) {
			frameVideo.setSize(124, 124);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 168, (tamanhoTela.height) - 149);
		}
		else if (tamanhoTela.width == 1024 && tamanhoTela.height == 768) {
			frameVideo.setSize(124, 124);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 40, (tamanhoTela.height) - 149);
		}
		else if (tamanhoTela.width == 1368 && tamanhoTela.height == 768) {
			frameVideo.setSize(126, 124);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 211, (tamanhoTela.height) - 149);
		}		
		else if (tamanhoTela.width == 1360 && tamanhoTela.height == 768) {
			frameVideo.setSize(124, 124);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 208, (tamanhoTela.height) - 149);
		}
		else if (tamanhoTela.width == 1280 && tamanhoTela.height == 720) {
			frameVideo.setSize(124, 117);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 168, (tamanhoTela.height) - 140);
		}
		else if (tamanhoTela.width == 1280 && tamanhoTela.height == 600) {
			frameVideo.setSize(124, 95);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 168, (tamanhoTela.height) - 115);
		} 
		else if (tamanhoTela.width == 800 && tamanhoTela.height == 600) {
			frameVideo.setSize(62, 96);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 164, (tamanhoTela.height) - 116);
		}
		// Resolução 1366 x 768
		else {
			frameVideo.setSize(124, 124);
			frameVideo.setLocation((tamanhoTela.width-frameJogo.getContentPane().getWidth())/2 + 211, (tamanhoTela.height) - 149);
		}
	}

	/**
	 * Adiciona o menu no jogo.
	 * 
	 * @param menu
	 * 
	 */
	private void adicionarMenu(MenuBotoes menu) {
		menuBotoes.add(menu);
		menu.adicionarListener(this);
	}

	public void keyPressed(KeyEvent e) {
		if (!menuBotoes.isEmpty()) {
			menuBotoes.peek().keyPressed(e);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (!menuBotoes.isEmpty()) {
			menuBotoes.peek().keyReleased(e);
		}
	}

	public void keyTyped(KeyEvent e) {
		if (!menuBotoes.isEmpty()) {
			menuBotoes.peek().keyTyped(e);
		}
	}

	public static boolean getVideoLoop() {
		return videoLoop;
	}

	public static void setVideoLoop(boolean videoLoop) {
		Main.videoLoop = videoLoop;
	}
}