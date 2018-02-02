/*
 * Nivel do jogo
 */
package jogo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import jogo.construcoes.Armadilha;
import jogo.construcoes.Armario;
import jogo.construcoes.Caixao;
import jogo.construcoes.CaixaoAberto;
import jogo.construcoes.DinamiteEnterrada;
import jogo.construcoes.Espinhos;
import jogo.construcoes.Espirito;
import jogo.construcoes.Madeiras;
import jogo.construcoes.Maquina;
import jogo.construcoes.PegarDinamite;
import jogo.construcoes.FinalDeJogo2;
import jogo.construcoes.Planta;
import jogo.construcoes.PortaMoeda;
import jogo.construcoes.FinalDeJogo1;
import jogo.construcoes.baus.BauAMoedas;
import jogo.construcoes.baus.BauATesouro;
import jogo.construcoes.baus.BauAsMoedas;
import jogo.construcoes.baus.BauDoisRubi;
import jogo.construcoes.baus.BauEuAbrindoBau;
import jogo.construcoes.baus.BauEuAbroOBau;
import jogo.construcoes.baus.BauMoedaDeOuro;
import jogo.construcoes.baus.BauMoedaParaOuro;
import jogo.construcoes.baus.BauOTesouro;
import jogo.construcoes.baus.BauRubi;
import jogo.construcoes.baus.BauUmRubi;
import jogo.construcoes.gestos.GestoDireita;
import jogo.construcoes.gestos.GestoExplodir;
import jogo.construcoes.gestos.GestoIr;
import jogo.construcoes.gestos.GestoPerigo;
import jogo.construcoes.gestos.GestoProsseguir;
import jogo.construcoes.gestos.GestoTodos;
import jogo.construcoes.letras.BotaoA;
import jogo.construcoes.letras.BotaoB;
import jogo.construcoes.letras.BotaoI;
import jogo.construcoes.letras.BotaoPorta;
import jogo.construcoes.letras.BotaoPortao;
import jogo.construcoes.letras.BotaoR;
import jogo.construcoes.letras.BotaoR2;
import jogo.construcoes.letras.LetraA;
import jogo.construcoes.letras.LetraB;
import jogo.construcoes.letras.LetraI;
import jogo.construcoes.letras.LetraR;
import jogo.construcoes.portugues.BotaoInterrogacaoA;
import jogo.construcoes.portugues.BotaoInterrogacaoO;
import jogo.construcoes.portugues.PergaminhoA;
import jogo.construcoes.portugues.PergaminhoO;
import jogo.entidades.Entidade;
import jogo.entidades.personagens.Jogador;
import jogo.logica.Fronteiras;
import jogo.menus.Fonte;
import jogo.tela.Arte;
import jogo.tela.Bitmap;
import jogo.tela.Tela;
import jogo.tiles.AreaInacessivel;
import jogo.tiles.Buraco;
import jogo.tiles.Chao;
import jogo.tiles.ChaoBau;
import jogo.tiles.ChaoLibrasCaixao;
import jogo.tiles.Parede;
import jogo.tiles.ParedeFalsa;
import jogo.tiles.ParedeRachada;
import jogo.tiles.Tile;

/**
 * O nivel do jogo.
 */
public class Nivel {
	
	// Quanto maior o número, menor a frequencia de criação
	private static final int FREQUENCIA_BAU_GERADOR = 45;
	
	// Quanto maior o número, maior a frequencia de criação
	private static final int FREQUENCIA_BAU_FIXO = 140;
	
	// Quanto maior o número, maior a frequencia de criação
	private static final int FREQUENCIA_LIBRAS = 14;
	
	// Tempo que o pop-up aparece para o jogador
	private static final int TEMPO_POP_UP = 600;
	
	// Quantidade máxima de inimigos simultâneos no nível
	private int maximoInimigos = 7;
	
	/** largura e altura do nível */
    public final int larguraNivel, alturaNivel;
    
    /** Tiles do cenário */
    private Tile[] tiles;  
    
    /** Mapeamento de entidades em uma lista*/
    private List<Entidade>[] mapeamentoEntidades;
    
    /** Entidades */
    private List<Entidade> entidades = new ArrayList<Entidade>();
    
    /** Minimapa */
    private Bitmap minimapa;
    
    /** Determina tile já visto pelo jogador.= */
    private boolean visto[];
    
    private final int[] offsetVisinho;
  
    int numMensagem = 0;

    // Contagem de inimigos
	private int qtdeInimigo = 0;

	// Contagem de tempo de permanência
	private int tempoPermanencia = 0;

	// Número da fala do NPC
	private int dialogoID;
	
	Random aleatorio = new Random();

	// Retorna se o jogo está pausado ou não
	private boolean pause;

	// Retorna se o jogo está com a tela de pause ou não
	private boolean telaPause;
	
	// Mudar a linha de um diálogo
	private int proximaLinha = 0;
	
	private boolean telaContinue = false;

	// Booleans que avisam que um objeto está sendo pego pela primeira vez
	private boolean primeiroTesouro = false;
	private boolean primeiraLetraA = false;
	private boolean primeiraLetraB = false;
	private boolean primeiraLetraR = false;
	private boolean primeiraLetraI = false;
	private boolean primeiroPergaminho = false;
	
	// Repetidor para indicador de próxima fala
	private int repetidor = 50;
	
	/**
	 * 
     * Instancia um novo nível
     *
     * @param largura 
     * @param altura 
     */
    @SuppressWarnings("unchecked")
    private Nivel(int largura, int altura) {
        offsetVisinho = new int[] {
                -1, 1, -largura, largura
        };
        this.larguraNivel = largura;
        this.alturaNivel = altura;

        minimapa = new Bitmap(largura, altura);

        tiles = new Tile[largura * altura];
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                Tile tile = new Chao();

                setTile(x, y, tile);
            }
        }

        mapeamentoEntidades = new List[largura * altura];
        for (int i = 0; i < largura * altura; i++) {
            mapeamentoEntidades[i] = new ArrayList<Entidade>();
        }

        visto = new boolean[(largura + 1) * (altura + 1)];
    }

    /**
     * Lendo nivel.bmp para gerar mapa.
     *
     * @param caminho 
     * @return Nivel
     * @throws IOException
     */
    public static Nivel lerArquivo(String caminho) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(Main.class.getResource(caminho));
        int largura = bufferedImage.getWidth() + 16;
        int altura = bufferedImage.getHeight() + 16;

        int[] rgbs = new int[largura * altura];
        Arrays.fill(rgbs, 0xffA8A800);

        for (int y = 0 + 4; y < altura - 4; y++) {
            for (int x = 31 - 3; x < 32 + 3; x++) {
                rgbs[x + y * largura] = 0xff888800;
            }
        }
        for (int y = 0 + 5; y < altura - 5; y++) {
            for (int x = 31 - 1; x < 32 + 1; x++) {
                rgbs[x + y * largura] = 0xffA8A800;
            }
        }

        bufferedImage.getRGB(0, 0, largura - 16, altura - 16, rgbs, 8 + 8 * largura, largura);

        Nivel nivel = new Nivel(altura, largura);
       
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int cor = rgbs[x + y * largura] & 0xffffff;

                // Adicionar tiles conforme cores de nivel.bmp
                Tile tile = new Chao();
                if (cor == 0xA8A800) {
                    tile = new AreaInacessivel();
                } else if (cor == 0x99FFCC) {
                    tile = new ChaoBau();
                } else if (cor == 0xCC9966) {
                    tile = new ChaoLibrasCaixao();
                } else if (cor == 0xff7777) {
                    tile = new ParedeRachada();
                } else if (cor == 0x000000) {
                    tile = new Buraco();
                } else if (cor == 0xff0000) {
                    tile = new Parede();
                } else if (cor == 0x990000) {
                    tile = new ParedeFalsa();
                } else if (cor == 0xFFFF00) {
                	nivel.adicionarEntidade(new DinamiteEnterrada(x * 32 + 16.0, y * 32 + 6.0));
                } else if (cor == 0x9900CC) {
                	nivel.adicionarEntidade(new BauRubi(x * 32 + 16.0, y * 32 + 6.0));
                } else if (cor == 0x996600) {
                	nivel.adicionarEntidade(new Armario(x * 32 + 16.0, y * 32 + 6.0));
                } else if (cor == 0xCCFF00) {
                	nivel.adicionarEntidade(new Planta(x * 32 + 16.0, y * 32 + 6.0));
                } else if (cor == 0xCC00FF) {
                	nivel.adicionarEntidade(new CaixaoAberto(x * 32 + 16.0, y * 32 + 6.0));
                } else if (cor == 0x006633) {
                	nivel.adicionarEntidade(new Espinhos(x * 32 + 16.0, y * 32 + 6.0));
                } else if (cor == 0x663300) {
                	nivel.adicionarEntidade(new Madeiras(x * 32 + 16.0, y * 32 + 6.0));
                }
                nivel.setTile(x, y, tile);
            }
        }

        for (int y = 0; y < altura + 1; y++) {
            for (int x = 0; x < largura + 1; x++) {
                if (x <= 8 || y <= 8 || x >= largura - 8 || y >= altura - 8) {
                    nivel.visto[x + y * (largura + 1)] = true;
                }
            }
        }
        return nivel;
    }

    /**
     * Inicializar o nivel com entidades
     */
    public void iniciar() {
    	      
    	/* Adição de entidades no nível
        * Elementos fixos no mapa:
        */
    	
        // Comprar Bomba
        adicionarEntidade(new PegarDinamite(32 * (larguraNivel / 2 + .5), (alturaNivel - 29.5) * 32));
        
        // Letras
        adicionarEntidade(new LetraA(32 * (larguraNivel / 2 - 12.5), (alturaNivel - 35.5) * 32));
        adicionarEntidade(new LetraB(32 * (larguraNivel / 2 + 8.5), (alturaNivel - 37.17) * 32));
        adicionarEntidade(new LetraR(32 * (larguraNivel / 2 - 21.0), (alturaNivel - 27.5) * 32));
        adicionarEntidade(new LetraI(32 * (larguraNivel / 2 + 21.5), (alturaNivel - 27.5) * 32));
        adicionarEntidade(new LetraR(32 * (larguraNivel / 2 - 4.5), (alturaNivel - 11.5) * 32));
        
        // Espirito		
        adicionarEntidade(new Espirito(32 * (larguraNivel / 2 - .5), (alturaNivel - 12.5) * 32));
        
        // Botoes
        adicionarEntidade(new BotaoR2(32 * (larguraNivel / 2 + 1.48), (alturaNivel - 29.15) *  32));
        adicionarEntidade(new BotaoI(32 * (larguraNivel / 2 + .5), (alturaNivel - 29.15) *  32));
        adicionarEntidade(new BotaoR(32 * (larguraNivel / 2 - 0.52), (alturaNivel - 29.15) * 32));
        adicionarEntidade(new BotaoB(32 * (larguraNivel / 2 - 1.52), (alturaNivel - 29.15) * 32));
        adicionarEntidade(new BotaoA(32 * (larguraNivel / 2 - 2.5), (alturaNivel - 29.15) * 32));
         
        // Armadilhas
        adicionarEntidade(new Armadilha(32 * (larguraNivel / 2 + 19.5), (alturaNivel - 38.17) * 32));
        adicionarEntidade(new Armadilha(32 * (larguraNivel / 2 + 18.5), (alturaNivel - 38.17) * 32));
        adicionarEntidade(new Armadilha(32 * (larguraNivel / 2 - 10.5), (alturaNivel - 47.17) * 32));
        adicionarEntidade(new Armadilha(32 * (larguraNivel / 2 - 14.5), (alturaNivel - 47.17) * 32));
      	
        // Quadros
        adicionarEntidade(new GestoDireita(32 *(larguraNivel / 2 + 20.5), (alturaNivel - 33.30) * 32));
        adicionarEntidade(new GestoDireita(32 *(larguraNivel / 2 + 20.5), (alturaNivel - 40.30) * 32));
        adicionarEntidade(new GestoExplodir(32 *(larguraNivel / 2 + 7.5), (alturaNivel - 25.20) * 32));
        adicionarEntidade(new GestoExplodir(32 *(larguraNivel / 2 - 8.5), (alturaNivel - 25.20) * 32));
        adicionarEntidade(new GestoProsseguir(32 *(larguraNivel / 2 - 16.5), (alturaNivel - 21.20) * 32));
        adicionarEntidade(new GestoPerigo(32 *(larguraNivel / 2 - 16.5), (alturaNivel - 51.20) * 32));
        adicionarEntidade(new GestoPerigo(32 *(larguraNivel / 2 - 11.5), (alturaNivel - 45.20) * 32));
        adicionarEntidade(new GestoPerigo(32 *(larguraNivel / 2 - 13.5), (alturaNivel - 45.20) * 32));
        adicionarEntidade(new GestoIr(32 *(larguraNivel / 2 - 16.5), (alturaNivel - 53.20) * 32));
        adicionarEntidade(new GestoPerigo(32 *(larguraNivel / 2 - 16.5), (alturaNivel - 55.20) * 32)); 
        adicionarEntidade(new GestoTodos(32 * (larguraNivel / 2 - 1.5), (alturaNivel - 30.5) * 32)); 
        
        // Porta Moeda
        adicionarEntidade(new PortaMoeda(32 *(larguraNivel / 2 + 9.5), (alturaNivel - 49.30) * 32));
        
        // Conjunto de elementos dos Pergaminhos
        adicionarEntidade(new PergaminhoO(52*32 + 18.0, 16*32 + 10.0));
        adicionarEntidade(new PergaminhoA(47*32 + 17.5, 16*32 + 10.0));
        adicionarEntidade(new BotaoInterrogacaoA(25.95*32 + 18.0, 15*32 + 10.0));
        adicionarEntidade(new BotaoInterrogacaoO(33.95*32 + 18.0, 15*32 + 10.0));
        adicionarEntidade(new BotaoPorta(27.5*32 + 18.0, 15*32 + 10.0));
        adicionarEntidade(new BotaoPortao(35.5*32 + 18.0, 15*32 + 10.0));
        
        // Finais de jogo
    	adicionarEntidade(new FinalDeJogo1(31*32 + 17.0, 10*32 + 10.0));
    	adicionarEntidade(new FinalDeJogo2(32 * (larguraNivel / 2 - 0.52), (alturaNivel - 24.20) * 32));
    	
    	// Cenário
    	adicionarEntidade(new Maquina(32 * (larguraNivel / 2 - 0.52), (alturaNivel - 25.75) * 32));
    	
    	// Adicionar tesouros aleatorios no inicio do jogo
        for (int i = 0; i < FREQUENCIA_BAU_FIXO; i++) {
            double x = (aleatorio.nextInt(larguraNivel - 16) + 8) * Tile.LARGURA + Tile.LARGURA / 2;
            double y = (aleatorio.nextInt(alturaNivel - 16) + 8) * Tile.ALTURA + Tile.ALTURA / 2 - 4;
            final Tile tile = getTile((int) (x / Tile.LARGURA), (int) (y / Tile.ALTURA));
            
            if (tile instanceof ChaoBau) {
            	switch (aleatorio.nextInt(10)){
            		case 0: 
            			adicionarEntidade(new BauAMoedas(x, y - 5));
            			break;
            		case 1: 
            			adicionarEntidade(new BauAsMoedas(x, y - 5));
            			break;
            		case 2: 
            			adicionarEntidade(new BauATesouro(x, y - 5));
            			break;
            		case 3: 
            			adicionarEntidade(new BauOTesouro(x, y - 5));
            			break;
            		case 4: 
            			adicionarEntidade(new BauUmRubi(x, y - 5));
            			break;
            		case 5: 
            			adicionarEntidade(new BauDoisRubi(x, y - 5));
            			break;
            		case 6: 
            			adicionarEntidade(new BauEuAbrindoBau(x, y - 5));
            			break;
            		case 7: 
            			adicionarEntidade(new BauEuAbroOBau(x, y - 5));
            			break;
            		case 8: 
            			adicionarEntidade(new BauMoedaDeOuro(x, y - 5));
            			break;
            		case 9: 
            			adicionarEntidade(new BauMoedaParaOuro(x, y - 5));
            			break;
            	}
            }
        }

        // Adicionar mãos em LIBRAS no início do jogo
        for (int i = 0; i < FREQUENCIA_LIBRAS; i++) {
            double x = (aleatorio.nextInt(larguraNivel - 16) + 8) * Tile.LARGURA + Tile.LARGURA / 2;
            double y = (aleatorio.nextInt(alturaNivel - 16) + 8) * Tile.ALTURA + Tile.ALTURA / 2 - 4;
            final Tile tile = getTile((int) (x / Tile.LARGURA), (int) (y / Tile.ALTURA));
            
            if (tile instanceof ChaoLibrasCaixao) {
            	switch (aleatorio.nextInt(4)){
            		case 0: 
            			adicionarEntidade(new LetraA(x, y));
            			break;
            		case 1: 
            			adicionarEntidade(new LetraB(x, y));
            			break;
            		case 2: 
            			adicionarEntidade(new LetraR(x, y));
            			break;
            		case 3: 
            			adicionarEntidade(new LetraI(x, y));
            			break;
            	}                	
            }
        }
        
        // Iniciar tutorial
        setDialogo(26, 0.0, 0.0);
        proximaLinha = 1;
    }

    /**
     * Set tile
     *
     * @param x 
     * @param y 
     * @param tile
     */
    public void setTile(int x, int y, Tile tile) {
        final int index = x + y * larguraNivel;
        tiles[index] = tile;
        tile.iniciar(this, x, y);
        for (int of : offsetVisinho) {
            final int nbIndex = index + of;
            if (nbIndex >= 0 && nbIndex < larguraNivel * alturaNivel) {
                final Tile vizinho = tiles[nbIndex];
                if (vizinho != null) vizinho.vizinhoMudou(tile);
            }
        }
    }

    /**
     * Get tile
     *
     * @param x 
     * @param y the y
     * @return tile
     */
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= larguraNivel || y >= alturaNivel) return null;
        return tiles[x + y * larguraNivel];
    }
    
    // Adiciona um inimigo quando o caixão gerar
    public void addInimigo () {
    	qtdeInimigo++;
    }
    
    // Subtrai um imigo quando morto
    public void subInimigo () {
    	qtdeInimigo--;
    }

    public boolean maxInimigo () {
    	if (qtdeInimigo >= maximoInimigos) {
    		return true;
    	} else {
    		return false;
    	}
    }

    /**
     * Inserindo entidades no mapeamento
     *
     * @param e 
     */
    private void inserirMapeamentoEntidade(Entidade e) {
        e.xto = (int) (e.pos.x - e.raio.x) / Tile.LARGURA;
        e.yto = (int) (e.pos.y - e.raio.y) / Tile.ALTURA;

        int x1 = e.xto + (int) (e.raio.x * 2 + 1) / Tile.LARGURA;
        int y1 = e.yto + (int) (e.raio.y * 2 + 1) / Tile.ALTURA;

        for (int y = e.yto; y <= y1; y++) {
            if (y < 0 || y >= alturaNivel) continue;
            for (int x = e.xto; x <= x1; x++) {
                if (x < 0 || x >= larguraNivel) continue;
                mapeamentoEntidades[x + y * larguraNivel].add(e);
            }
        }
    }
    
    // Gets e Sets
    public void setPause (boolean pause){
    	this.pause = pause;
    }
    
    public boolean getPause(){
    	return pause;
    }
    
    public void setTelaPause (boolean tela){
    	this.telaPause = tela;
    }
    
    public boolean getTelaPause (){
    	return telaPause;
    }
    
    public void setTelaContinue (boolean tela){
    	this.telaContinue = tela;
    }
    
    public boolean getTelaContinue (){
    	return telaContinue;
    }

    /**
     * Remove do mapeamento de entidades
     *
     * @param e 
     */
    private void removeMapeamentoEntidade(Entidade e) {
        int x1 = e.xto + (int) (e.raio.x * 2 + 1) / Tile.LARGURA;
        int y1 = e.yto + (int) (e.raio.y * 2 + 1) / Tile.ALTURA;

        for (int y = e.yto; y <= y1; y++) {
            if (y < 0 || y >= alturaNivel) continue;
            for (int x = e.xto; x <= x1; x++) {
                if (x < 0 || x >= larguraNivel) continue;
                mapeamentoEntidades[x + y * larguraNivel].remove(e);
            }
        }
    }

    /**
     * Get entidade
     *
     * @param bb 
     * @return entidades
     */
    public Set<Entidade> getEntidades(Fronteiras bb) {
        return getEntidades(bb.x0, bb.y0, bb.x1, bb.y1);
    }

    /**
     * Get entidades
     *
     * @param xx0
     * @param yy0
     * @param xx1
     * @param yy1
     * @return entidades
     */
    public Set<Entidade> getEntidades(double xx0, double yy0, double xx1, double yy1) {
        int x0 = (int) (xx0) / Tile.LARGURA;
        int x1 = (int) (xx1) / Tile.LARGURA;
        int y0 = (int) (yy0) / Tile.ALTURA;
        int y1 = (int) (yy1) / Tile.ALTURA;

        Set<Entidade> resultado = new TreeSet<Entidade>(new ComparadorEntidade());

        for (int y = y0; y <= y1; y++) {
            if (y < 0 || y >= alturaNivel) continue;
            for (int x = x0; x <= x1; x++) {
                if (x < 0 || x >= larguraNivel) continue;
                List<Entidade> entidades = mapeamentoEntidades[x + y * larguraNivel];
                for (int i = 0; i < entidades.size(); i++) {
                    Entidade e = entidades.get(i);
                    if (e.removido) continue;
                    if (e.intercepta(xx0, yy0, xx1, yy1)) {
                        resultado.add(e);
                    }
                }
            }
        }
        return resultado;
    }

    /**
     * Get entidades
     *
     * @param bb 
     * @param c 
     * @return entidades
     */
    public Set<Entidade> getEntidades(Fronteiras bb, Class<? extends Entidade> c) {
        return getEntidades(bb.x0, bb.y0, bb.x1, bb.y1, c);
    }

    /**
     * Get entidades
     *
     * @param xx0 
     * @param yy0 
     * @param xx1
     * @param yy1 
     * @param c 
     * @return entidades
     */
    public Set<Entidade> getEntidades(double xx0, double yy0, double xx1, double yy1, Class<? extends Entidade> c) {
        int x0 = (int) (xx0) / Tile.LARGURA;
        int x1 = (int) (xx1) / Tile.LARGURA;
        int y0 = (int) (yy0) / Tile.ALTURA;
        int y1 = (int) (yy1) / Tile.ALTURA;

        Set<Entidade> resultado = new TreeSet<Entidade>(new ComparadorEntidade());

        for (int y = y0; y <= y1; y++) {
            if (y < 0 || y >= alturaNivel) continue;
            for (int x = x0; x <= x1; x++) {
                if (x < 0 || x >= larguraNivel) continue;
                List<Entidade> entidades = mapeamentoEntidades[x + y * larguraNivel];
                for (int i = 0; i < entidades.size(); i++) {
                    Entidade e = entidades.get(i);
                    if (!c.isInstance(e)) continue;
                    if (e.removido) continue;
                    if (e.intercepta(xx0, yy0, xx1, yy1)) {
                        resultado.add(e);
                    }
                }
            }
        }

        return resultado;
    }

    /**
     * Get entidades
     *
     * @param xx0
     * @param yy0
     * @param xx1
     * @param yy1 
     * @param c 
     * @return entidades
     */
    public Set<Entidade> getEntidadesDevagar(double xx0, double yy0, double xx1, double yy1, Class<? extends Entidade> c) {
        Set<Entidade> resultado = new TreeSet<Entidade>(new ComparadorEntidade());

        List<Entidade> entidades = this.entidades;
        for (int i = 0; i < entidades.size(); i++) {
            Entidade e = entidades.get(i);
            if (!c.isInstance(e)) continue;
            if (e.removido) continue;
            if (e.intercepta(xx0, yy0, xx1, yy1)) {
                resultado.add(e);
            }
        }

        return resultado;
    }

    /**
     * Adicionar entidade.
     *
     * @param e 
     */
    public void adicionarEntidade(Entidade e) {
        e.iniciar(this);
        entidades.add(e);
        inserirMapeamentoEntidade(e);
    }

    /**
     * Remover entidade.
     *
     * @param e 
     */
    public void removerEntidade(Entidade e) {
        e.removido = true;
    }

    /**
     * Frame a frame do jogo
     */
    public void frame() {
    	Random aleatorio = new Random();

    	if (qtdeInimigo < 0) {
    		qtdeInimigo = 0;
    	} else if (qtdeInimigo > maximoInimigos) {
    		qtdeInimigo = maximoInimigos;
    	}
    	
    	// Adiciona caixões aleatoriamente ao jogo	
        for (int i = 0; i < 1; i++) {
            double x = (aleatorio.nextInt(larguraNivel - 16) + 8) * Tile.LARGURA + Tile.LARGURA / 2;
            double y = (aleatorio.nextInt(alturaNivel - 16) + 8) * Tile.ALTURA + Tile.ALTURA / 2 - 4;
            final Tile tile = getTile((int) (x / Tile.LARGURA), (int) (y / Tile.ALTURA));
            
            if (tile instanceof ChaoLibrasCaixao) {
                double r = 32 * 8;
                if (getEntidades(new Fronteiras(null, x - r, y - r, x + r, y + r), Jogador.class).size() == 0) {
                    r = 32 * 11;
                    if (getEntidades(new Fronteiras(null, x - r, y - r, x + r, y + r), Caixao.class).size() == 0) {
                    	adicionarEntidade(new Caixao(x, y, aleatorio.nextInt(5)));
                    }
                }
            }
        }
        
     // Adiciona tesouros aleatoriamente ao jogo	
        for (int i = 0; i < 1; i++) {
            double x = (aleatorio.nextInt(larguraNivel - 16) + 8) * Tile.LARGURA + Tile.LARGURA / 2;
            double y = (aleatorio.nextInt(alturaNivel - 16) + 8) * Tile.ALTURA + Tile.ALTURA / 2 - 4;
            final Tile tile = getTile((int) (x / Tile.LARGURA), (int) (y / Tile.ALTURA));
            
            if (tile instanceof ChaoBau) {
                double r = 32 * FREQUENCIA_BAU_GERADOR;
                if (getEntidades(new Fronteiras(null, x - r, y - r, x + r, y + r), Jogador.class).size() == 0) {
                    r = 32 * FREQUENCIA_BAU_GERADOR;
                    if (getEntidades(new Fronteiras(null, x - r, y - r, x + r, y + r), BauAMoedas.class).size() == 0) {
                    	switch (aleatorio.nextInt(10)){
                		case 0: 
                			adicionarEntidade(new BauAMoedas(x, y - 5));
                			break;
                		case 1: 
                			adicionarEntidade(new BauAsMoedas(x, y - 5));
                			break;
                		case 2: 
                			adicionarEntidade(new BauATesouro(x, y - 5));
                			break;
                		case 3: 
                			adicionarEntidade(new BauOTesouro(x, y - 5));
                			break;
                		case 4: 
                			adicionarEntidade(new BauUmRubi(x, y - 5));
                			break;
                		case 5: 
                			adicionarEntidade(new BauDoisRubi(x, y - 5));
                			break;
                		case 6: 
                			adicionarEntidade(new BauEuAbrindoBau(x, y - 5));
                			break;
                		case 7: 
                			adicionarEntidade(new BauEuAbroOBau(x, y - 5));
                			break;
                		case 8: 
                			adicionarEntidade(new BauMoedaDeOuro(x, y - 5));
                			break;
                		case 9: 
                			adicionarEntidade(new BauMoedaParaOuro(x, y - 5));
                			break;
                    	}
                    }
                }
            }
        }
        
        for (int i = 0; i < entidades.size(); i++) {
            Entidade e = entidades.get(i);
            if (!e.removido) {
                e.frame();

                int xtn = (int) (e.pos.x - e.raio.x) / Tile.LARGURA;
                int ytn = (int) (e.pos.y - e.raio.y) / Tile.ALTURA;
                if (xtn != e.xto || ytn != e.yto) {
                    removeMapeamentoEntidade(e);
                    inserirMapeamentoEntidade(e);
                }
            }
            if (e.removido) {
                entidades.remove(i--);
                removeMapeamentoEntidade(e);
            }
        }
    }

    /**
     * Checa se uma área já foi vista pelo jogador
     *
     * @param x 
     * @param y 
     * @return true, se verdadeiro
     */
    private boolean foiVisto(int x, int y) {
        return visto[x + y * (larguraNivel + 1)] || visto[(x + 1) + y * (larguraNivel + 1)] || visto[x + (y + 1) * (larguraNivel + 1)] || visto[(x + 1) + (y + 1) * (larguraNivel + 1)];
    }

    /**
     * Renderizar
     *
     * @param tela 
     * @param xScroll 
     * @param yScroll 
     */
    public void renderizar(Tela tela, int xScroll, int yScroll) {
        int x0 = xScroll / Tile.LARGURA;
        int y0 = yScroll / Tile.ALTURA;
        int x1 = (xScroll + tela.base) / Tile.LARGURA;
        int y1 = (yScroll + tela.altura) / Tile.ALTURA;
        if (xScroll < 0) x0--;
        if (yScroll < 0) y0--;

        Set<Entidade> entidadesVisiveis = getEntidades(xScroll - Tile.LARGURA, yScroll - Tile.ALTURA, xScroll + tela.base + Tile.LARGURA, yScroll + tela.altura + Tile.ALTURA);

        tela.setAlinhamento(-xScroll, -yScroll);

        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) {
            	
                // Saída superior
                int xt = x - 28;
                int yt = y - 4;
                if (xt >= 0 && yt >= 0 && xt < 7 && yt < 4) {
                    tela.plotar(Arte.piso[5][0], x * Tile.LARGURA, y * Tile.ALTURA);
                    continue;
                }
                if (xt >= 3 && yt >= 4 && xt < 4 && yt < 5) {
                    tela.plotar(Arte.piso[5][0], x * Tile.LARGURA, y * Tile.ALTURA);
                    continue;
                }

                // Saída inferior
                yt = y - (64 - 8);
                if (xt >= 0 && yt >= 0 && xt < 7 && yt < 8) {
                	tela.plotar(Arte.piso[5][0], x * Tile.LARGURA, y * Tile.ALTURA);
                    continue;
                }
                
                // Renderizar o Chao
                if (podeVer(x, y)) {
                    tiles[x + y * larguraNivel].renderizar(tela);
                }
            }
        }

        for (Entidade e : entidadesVisiveis) {
            e.renderizar(tela);
        }

        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) {
                if (x < 0 || x >= larguraNivel || y < 0 || y >= alturaNivel) {
                    continue;
                }
                if (podeVer(x, y)) {
                    tiles[x + y * larguraNivel].renderizarTopo(tela);
                }
            }
        }

        for (int y = y0; y <= y1; y++) {
            if (y < 0 || y >= alturaNivel) continue;
            for (int x = x0; x <= x1; x++) {
                if (x < 0 || x >= larguraNivel) continue;
                boolean c0 = !visto[x + y * (larguraNivel + 1)];
                boolean c1 = !visto[(x + 1) + y * (larguraNivel + 1)];
                boolean c2 = !visto[x + (y + 1) * (larguraNivel + 1)];
                boolean c3 = !visto[(x + 1) + (y + 1) * (larguraNivel + 1)];

                if (!(c0 || c1 || c2 || c3)) continue;

                int cont = 0;
                if (c0) cont++;
                if (c1) cont++;
                if (c2) cont++;
                if (c3) cont++;
                int yo = -16;

                // Renderizar uma névoa indicando áreas ainda não exploradas
                if (cont == 4) {
                    tela.plotar(Arte.nevoa[1][1], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                } else if (cont == 3) {
                    if (!c0) tela.plotar(Arte.nevoa[1][4], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (!c1) tela.plotar(Arte.nevoa[0][4], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (!c2) tela.plotar(Arte.nevoa[1][3], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (!c3) tela.plotar(Arte.nevoa[0][3], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                } else if (cont == 1) {
                    if (c0) tela.plotar(Arte.nevoa[2][2], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (c1) tela.plotar(Arte.nevoa[0][2], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (c2) tela.plotar(Arte.nevoa[2][0], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (c3) tela.plotar(Arte.nevoa[0][0], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                } else {
                    if (c0 && c3) tela.plotar(Arte.nevoa[2][4], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (c1 && c2) tela.plotar(Arte.nevoa[2][3], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (c0 && c1) tela.plotar(Arte.nevoa[1][2], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (c2 && c3) tela.plotar(Arte.nevoa[1][0], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (c0 && c2) tela.plotar(Arte.nevoa[2][1], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                    if (c1 && c3) tela.plotar(Arte.nevoa[0][1], x * Tile.LARGURA, y * Tile.LARGURA + yo);
                }
            }
        }
        tela.setAlinhamento(0, 0);

        // Atualizar minimapa
        for (int y = 0; y < alturaNivel; y++) {
            for (int x = 0; x < larguraNivel; x++) {
                int i = x + y * larguraNivel;
                if (foiVisto(x, y)) {
                    minimapa.pixels[i] = tiles[i].corMinimapa;
                } else {
                    minimapa.pixels[i] = 0xff000000;
                }
            }
        }

        for (int i = 0; i < entidades.size(); i++) {
            Entidade e = entidades.get(i);
            if (!e.removido) {
                if (e.iconeMapa >= 0) {
                    int x = (int) (e.pos.x / Tile.LARGURA);
                    int y = (int) (e.pos.y / Tile.LARGURA);
                    if (x >= 0 && y >= 0 && x < larguraNivel && y < alturaNivel) {
                        if (foiVisto(x, y)) {
                            minimapa.plotar(Arte.iconesMapa[e.iconeMapa % 4][e.iconeMapa / 4], x - 2, y - 2);
                        }
                    }
                }
            }
        }
        // Manuseio de mensagens e pop-ups
        if (tempoPermanencia <= 0){
        	numMensagem = 0;
        }
        
        if (getTelaPause()){
        	tela.plotar(Arte.pause, 140, tela.altura - 240);
        }
        
        if (getTelaContinue()){
        	tela.plotar(Arte.telaContinue, 0, 0);
        	setPause(true);
        	dialogoID = 23;
        }
        
        if (proximaLinha <= 0 && dialogoID == 24)
        	proximaLinha = 1;

        // Exibir mensagens de correção com tesouros errados
        if (numMensagem == 1 && tempoPermanencia > 0) {
        	tela.plotar(Arte.correcaoAMoedas, 140, tela.altura - 121);
            tempoPermanencia--;
        }
        if (numMensagem == 2 && tempoPermanencia > 0) {
            tela.plotar(Arte.correcaoATesouro, 140, tela.altura - 121);
            tempoPermanencia--;
        }
        if (numMensagem == 3 && tempoPermanencia > 0) {
            tela.plotar(Arte.correcaoDoisRubi, 140, tela.altura - 121);
            tempoPermanencia--;
        }
        if (numMensagem == 4 && tempoPermanencia > 0) {
            tela.plotar(Arte.correcaoEuAbrindoOBau, 140, tela.altura - 121);
            tempoPermanencia--;
        }
        if (numMensagem == 5 && tempoPermanencia > 0) {
            tela.plotar(Arte.correcaoMoedaParaOuro, 140, tela.altura - 121);
            tempoPermanencia--;
        }
        if (numMensagem == 6 && tempoPermanencia > 0) {
            tela.plotar(Arte.concordanciaCorreta, 140, tela.altura - 121);
            tempoPermanencia--;
        }
        if (numMensagem == 7 && tempoPermanencia > 0) {
            tela.plotar(Arte.supertiroMensagem, 140, tela.altura - 121);
            tempoPermanencia--;
        }
        
        tela.plotar(Arte.painel, 0, tela.altura - 80);
        tela.plotar(minimapa, 429, tela.altura - 80 + 5);
        
        // Plotar seta para próxima linha do diálogo
        if (dialogoID > 0 && getPause() && !getTelaContinue() && !getTelaPause()){
        	repetidor--;
        	if (repetidor > 25)
        		tela.plotar(Arte.enter, 270, tela.altura - 29);
        	else if (repetidor > 0 && repetidor <= 25)
        		tela.plotar(Arte.enter, 270, tela.altura - 27);
        	else {
        		tela.plotar(Arte.enter, 270, tela.altura - 27);
        		repetidor = 50;
        	}
        }
        
        // Frase padrão quando idle
        if (dialogoID == 0) 
        	Fonte.escrever(tela, "	 O Segredo do Castelo" , 110, tela.altura - 25);
        	
        /*
         * Sequencia dos diálogos do jogo
         */
        if (dialogoID == 1) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Esta sou eu!" , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(2);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Eu\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Preciso de 8 rubis" , 110, tela.altura - 33);
                Fonte.escrever(tela, "para conseguir acordar!" , 110, tela.altura - 20);  
        	} else if (proximaLinha == 4) { 	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 2) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "A porta está fechada." , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(16);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Porta\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Colete 10.000 moedas" , 110, tela.altura - 33);
                Fonte.escrever(tela, "para conseguir entrar!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) { 	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 3) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Tome cuidado com" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "este tesouro." , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(17);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Com\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Observe o texto" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "escrito em cima dele." , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
            	Main.tipoVideo(0);
                Fonte.escrever(tela, "Caso esteja certo," , 110, tela.altura - 33);    
                Fonte.escrever(tela, "abra o tesouro!" , 110, tela.altura - 20); 
        	} else if (proximaLinha == 5) {
            	Main.tipoVideo(0);
                Fonte.escrever(tela, "Caso esteja errado," , 110, tela.altura - 33);    
                Fonte.escrever(tela, "destrua o tesouro!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 6) { 	
            	setPause(false);
            	primeiroTesouro = true;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 4) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Tem dinamite aqui." , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(3);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Aqui\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Colete 5.000 moedas e" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "pegue quantas quiser." , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 5) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Tem uma dinamite " , 110, tela.altura - 33);
        		Fonte.escrever(tela, "enterrada no solo." , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(4);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Ter\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "A parede ao lado" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "parece rachada." , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Atire na dinamite para" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "destruir a parede." , 110, tela.altura - 20);
            } else if (proximaLinha == 5) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 6) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Este quadro tem um" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "gesto em libras:" , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(5);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "..." , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "O que este gesto" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "significa?" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 7) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Este quadro tem um" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "gesto em libras:" , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(6);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "..." , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "O que este gesto" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "significa?" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 8) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Este quadro tem um" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "gesto em libras:" , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(7);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "..." , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "O que este gesto" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "significa?" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 9) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Este quadro tem um" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "gesto em libras:" , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(8);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "..." , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "O que este gesto" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "significa?" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 10) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Este quadro tem um" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "gesto em libras:" , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(9);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "..." , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "O que este gesto" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "significa?" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 11) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Estas portas precisam" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "ser destrancadas." , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(16);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Porta\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Ative os botoes abaixo" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "para abrir as portas." , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 12) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Este armário pode" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "ser destruido." , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(10);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Armário\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Basta atirar nele." , 110, tela.altura - 33);    
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 13) {
        	proximaLinha = 0;
        	if (tempoPermanencia > 0) {
        		tempoPermanencia--;
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Muito bem!" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "O texto estava correto!" , 110, tela.altura - 20);
        	} else {	
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 14) {
        	proximaLinha = 0;
        	if (tempoPermanencia > 0) {
        		tempoPermanencia--;
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Este tesouro tinha" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "um texto incorreto." , 110, tela.altura - 20);
        	} else {	
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 15) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Esta letra em libras" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "representa a letra..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(11);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"A\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Ache um botão que" , 110, tela.altura - 33);
                Fonte.escrever(tela, "tenha esta letra	" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "E ponha este objeto" , 110, tela.altura - 33);
                Fonte.escrever(tela, "nele." , 110, tela.altura - 20);
        	} else if (proximaLinha == 5) {	
            	setPause(false);
            	primeiraLetraA = true;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 16) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Esta letra em libras" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "representa a letra..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(12);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"B\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Ache um botão que" , 110, tela.altura - 33);
                Fonte.escrever(tela, "tenha esta letra" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "E ponha este objeto" , 110, tela.altura - 33);
                Fonte.escrever(tela, "nele." , 110, tela.altura - 20);
        	} else if (proximaLinha == 5) {	
            	setPause(false);
            	primeiraLetraB  = true;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 17) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Esta letra em libras" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "representa a letra..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(13);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"R\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Ache um botão que" , 110, tela.altura - 33);
                Fonte.escrever(tela, "tenha esta letra" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "E ponha este objeto" , 110, tela.altura - 33);
                Fonte.escrever(tela, "nele." , 110, tela.altura - 20);
        	} else if (proximaLinha == 5) {	
            	setPause(false);
            	primeiraLetraR = true;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 18) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Esta letra em libras" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "representa a letra..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(14);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"I\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Ache um botão que" , 110, tela.altura - 33);
                Fonte.escrever(tela, "tenha esta letra" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "E ponha este objeto" , 110, tela.altura - 33);
                Fonte.escrever(tela, "nele." , 110, tela.altura - 20);
        	} else if (proximaLinha == 5) {	
            	setPause(false);
            	primeiraLetraI = true;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 19) {
        	proximaLinha = 0;
        	if (tempoPermanencia > 0) {
        		tempoPermanencia--;
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Cuidado! Armadilha!" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "Olhe as placas!" , 110, tela.altura - 20);
        	} else {	
            	dialogoID = 0;	
            }
        }
        
        if (dialogoID == 20) {
        	proximaLinha = 0;
        	if (tempoPermanencia > 0) {
        		tempoPermanencia--;
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Você achou um rubi!" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "Muito obrigada!" , 110, tela.altura - 20);
        	} else {	
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 21) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Esta letra foi escrita" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "em Portugues." , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Ache um botão que" , 110, tela.altura - 33);
                Fonte.escrever(tela, "tenha esta letra" , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "E ponha este pergaminho" , 110, tela.altura - 33);
                Fonte.escrever(tela, "nele." , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	primeiroPergaminho = true;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 22) {
        	proximaLinha = 0;
        	if (tempoPermanencia > 0) {
        		tempoPermanencia--;
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Ouvi um barulho de" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "porta se abrindo." , 110, tela.altura - 20);
        	} else {	
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 23) {
    		Main.tipoVideo(0);
    		Fonte.escrever(tela, "Aperte ESC para sair," , 110, tela.altura - 33);
    		Fonte.escrever(tela, "ou ENTER para voltar." , 110, tela.altura - 20);
        }
      
        if (dialogoID == 24) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Voce conseguiu" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "todos os rubis!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Venha me ver! Estou" , 110, tela.altura - 33);
                Fonte.escrever(tela, "no centro do castelo!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Nao use os rubis na" , 110, tela.altura - 33);
                Fonte.escrever(tela, "porta ao norte!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 25) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Esta porta vai" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "para fora do castelo" , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(15);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Castelo\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "São necessários 8 rubis" , 110, tela.altura - 33);
                Fonte.escrever(tela, "para conseguir abrir!" , 110, tela.altura - 20);  
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 26) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Que bom!" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "Você acordou!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Não se assuste!" , 110, tela.altura - 33);
                Fonte.escrever(tela, "Não vou te machucar!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Meu nome é Sara!" , 110, tela.altura - 33);
                Fonte.escrever(tela, "Sou uma exploradora!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Eu estava examinando" , 110, tela.altura - 33);
                Fonte.escrever(tela, "este castelo..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 5) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Quando achei um" , 110, tela.altura - 33);
                Fonte.escrever(tela, "tesouro!" , 110, tela.altura - 20); 
        	} else if (proximaLinha == 6) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Tinha 8 rubis dentro," , 110, tela.altura - 33);
                Fonte.escrever(tela, "mas..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 7) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Do nada eles" , 110, tela.altura - 33);
                Fonte.escrever(tela, "brilharam muito..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 8) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "E eu virei um" , 110, tela.altura - 33);
                Fonte.escrever(tela, "fantasma...!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 9) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Por favor, me ajude!" , 110, tela.altura - 33);
        	} else if (proximaLinha == 10) {	
        		setDialogoTempo(27);
            }
        }
        
        if (dialogoID == 27) {
        	proximaLinha = 0;
        	if (!getTelaPause())
        		setPause(false);
    		Main.tipoVideo(0);
    		Fonte.escrever(tela, "Aperte as teclas" , 110, tela.altura - 33);
    		Fonte.escrever(tela, "W,A,S e D para andar." , 110, tela.altura - 20);
	        if(Main.tutorialAbaixo && Main.tutorialAcima &&
	        		Main.tutorialEsquerda && Main.tutorialDireita){
	        	proximaLinha = 1;
	        	setDialogo(28, larguraNivel * Tile.LARGURA / 2 - 16,
	    				(alturaNivel - 5) * Tile.ALTURA - 176);
	        }
        }
        
        if (dialogoID == 28) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Muito bem!" , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "As setas do teclado" , 110, tela.altura - 33);
                Fonte.escrever(tela, "também fazem andar!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Para examinar objetos" , 110, tela.altura - 33);
                Fonte.escrever(tela, "ou carregá-los..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Pressione a tecla \"E\"" , 110, tela.altura - 33);
                Fonte.escrever(tela, "do teclado!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 5) {	
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Examine aquele objeto" , 110, tela.altura - 33);
                Fonte.escrever(tela, "em forma de mão!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 6) {	
            	setDialogoTempo(29);
            }
        }
        
        if (dialogoID == 29) {
        	proximaLinha = 0;
        	if (!getTelaPause())
        		setPause(false);
    		Main.tipoVideo(0);
    		Fonte.escrever(tela, "Examine a mão usando" , 110, tela.altura - 33);
    		Fonte.escrever(tela, "a tecla \"E\" do teclado" , 110, tela.altura - 20);
        }
        
        if (dialogoID == 30) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Muito bem!" , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "O ponteiro de seu mouse" , 110, tela.altura - 33);
                Fonte.escrever(tela, "é uma mira." , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Atire com sua arma" , 110, tela.altura - 33);
                Fonte.escrever(tela, "mirando com o mouse..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "E segurando o botão" , 110, tela.altura - 33);
                Fonte.escrever(tela, "para disparar!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 5) {	
            	setDialogoTempo(31);
            }
        }
        
        if (dialogoID == 31) {
        	proximaLinha = 0;
        	if (!getTelaPause())
        		setPause(false);
    		Main.tipoVideo(0);
    		Fonte.escrever(tela, "Mire e atire segurando" , 110, tela.altura - 33);
    		Fonte.escrever(tela, "o botão do mouse" , 110, tela.altura - 20);
        }
        
        if (dialogoID == 32) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Muito bem!" , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Use também a barra de" , 110, tela.altura - 33);
                Fonte.escrever(tela, "espaço para atirar!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Sempre atire e examine" , 110, tela.altura - 33);
                Fonte.escrever(tela, "tudo que ver." , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Também há tesouros" , 110, tela.altura - 33);
                Fonte.escrever(tela, "nesse castelo!" , 110, tela.altura - 20);           
        	} else if (proximaLinha == 5) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Observe o texto" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "escrito em cima dele." , 110, tela.altura - 20);
        	} else if (proximaLinha == 6) {
            	Main.tipoVideo(0);
            	tela.plotar(Arte.tesouroMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "Caso esteja certo," , 110, tela.altura - 33);    
                Fonte.escrever(tela, "abra o tesouro!" , 110, tela.altura - 20); 
        	} else if (proximaLinha == 7) {
            	Main.tipoVideo(0);
            	tela.plotar(Arte.tesouroMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "Caso esteja errado," , 110, tela.altura - 33);    
                Fonte.escrever(tela, "destrua o tesouro!" , 110, tela.altura - 20);   
        	} else if (proximaLinha == 8) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Agora, por favor," , 110, tela.altura - 33);
                Fonte.escrever(tela, "me ajude!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 9) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Somente quando você" , 110, tela.altura - 33);
                Fonte.escrever(tela, "encontrar os 8 rubis..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 10) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "É que eu irei voltar" , 110, tela.altura - 33);
                Fonte.escrever(tela, "ao normal!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 11) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Fale comigo se" , 110, tela.altura - 33);
                Fonte.escrever(tela, "quiser dicas!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 12) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "E use o mapa do canto" , 110, tela.altura - 33);
                Fonte.escrever(tela, "direito para se guiar!" , 110, tela.altura - 20);
            } else if (proximaLinha == 13) {
            	Main.sons.iniciarMusicaNivel();
            	Main.tempoRestante = Main.TEMPO_MUSICA_NIVEL;
            	setPause(false);
            	Main.tutorial = false;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 33) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(1);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
        		Fonte.escrever(tela, "\"Oi!\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Eu te achei jogado" , 110, tela.altura - 33);
                Fonte.escrever(tela, "no chão do castelo!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Você não se lembra de" , 110, tela.altura - 33);
                Fonte.escrever(tela, "como veio parar aqui?" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Que estranho..." , 110, tela.altura - 33);
        	} else if (proximaLinha == 5) {	
            	setPause(false);
            	Espirito.dialogo++;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 34) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(1);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
        		Fonte.escrever(tela, "\"Oi!\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Enquanto eu examinava" , 110, tela.altura - 33);
                Fonte.escrever(tela, "o castelo..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Achei escrituras" , 110, tela.altura - 33);
                Fonte.escrever(tela, "dizendo que:" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Existem paredes" , 110, tela.altura - 33);
                Fonte.escrever(tela, "falsas no castelo..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 5) {	
            	setPause(false);
            	Espirito.dialogo++;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 35) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(1);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
        		Fonte.escrever(tela, "\"Oi!\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Tem alguns monstros" , 110, tela.altura - 33);
                Fonte.escrever(tela, "andando por ai!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Destrua-os atirando" , 110, tela.altura - 33);
                Fonte.escrever(tela, "neles!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {	
            	setPause(false);
            	Espirito.dialogo++;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 36) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(1);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
        		Fonte.escrever(tela, "\"Oi!\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Você viu o mapa" , 110, tela.altura - 33);
                Fonte.escrever(tela, "no canto direito?" , 110, tela.altura - 20);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "As áreas escuras" , 110, tela.altura - 33);
                Fonte.escrever(tela, "indicam..." , 110, tela.altura - 20);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Que você não passou" , 110, tela.altura - 33);
                Fonte.escrever(tela, "por aquela área!" , 110, tela.altura - 20);
        	} else if (proximaLinha == 5) {	
            	setPause(false);
            	Espirito.dialogo = 33;
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 37) {
        	proximaLinha = 0;
        	if (tempoPermanencia > 0) {
        		tempoPermanencia--;
        		Main.tipoVideo(0);
        		Fonte.escrever(tela, "Sua arma foi melhorada!" , 110, tela.altura - 33);
        	} else {	
            	dialogoID = 0;
            }
        }
        
        if (dialogoID == 38) {
        	if (proximaLinha == 1) {
        		Main.tipoVideo(0);
           		Fonte.escrever(tela, "Este quadro várias" , 110, tela.altura - 33);
        		Fonte.escrever(tela, "palavras em libras:" , 110, tela.altura - 20);
        	} else if (proximaLinha == 2) {
        		Main.tipoVideo(6);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Direita\"" , 110, tela.altura - 33);
        	} else if (proximaLinha == 3) {
        		Main.tipoVideo(5);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
                Fonte.escrever(tela, "\"Explodir\"", 110, tela.altura - 33);
        	} else if (proximaLinha == 4) {
        		Main.tipoVideo(7);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
        		Fonte.escrever(tela, "\"Ir\"", 110, tela.altura - 33);
        	} else if (proximaLinha == 5) {
        		Main.tipoVideo(8);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
        		Fonte.escrever(tela, "\"Perigo\"", 110, tela.altura - 33);
        	} else if (proximaLinha == 6) {
        		Main.tipoVideo(9);
        		tela.plotar(Arte.gestoMensagem, 140, tela.altura - 240);
        		Fonte.escrever(tela, "\"Prosseguir\"", 110, tela.altura - 33);
        	} else if (proximaLinha == 7) {
        		Main.tipoVideo(0);
                Fonte.escrever(tela, "Por que este" , 110, tela.altura - 33);    
                Fonte.escrever(tela, "quadro está aqui?" , 110, tela.altura - 20);
        	} else if (proximaLinha == 8) {	
            	setPause(false);
            	proximaLinha = 0;
            	dialogoID = 0;
            }
        }
    }
    
    public void setMaxInimigos (int qtde){
    	maximoInimigos = qtde;
    }
    
    public void setPrimeiraLetra (int num, double x, double y){
    	if (!primeiraLetraA && num == 15)		setDialogo(num, x, y);
    	else if (!primeiraLetraB && num == 16)	setDialogo(num, x, y);
    	else if (!primeiraLetraR && num == 17)  setDialogo(num, x, y);
    	else if (!primeiraLetraI && num == 18)  setDialogo(num, x, y);
    }
    
    public boolean getPrimeiraLetra (int num){
    	if (num == 15)		return primeiraLetraA;
    	else if (num == 16)	return primeiraLetraB;
    	else if (num == 17) return primeiraLetraR;
    	else if (num == 18) return primeiraLetraI;
		return false;
    }
    
    public void setPrimeiroPergaminho (double x, double y){
    	if (!primeiroPergaminho ){
    		setDialogo(21, x, y);
    	}	
    }
    
    public boolean getPrimeiroPergaminho (){
    	return primeiroPergaminho;
    }
    
    public void setPrimeiroTesouro (double x, double y){
    	if (!primeiroTesouro){
    		setDialogo(3, x, y);
    	}	
    }
    
    public boolean getPrimeiroTesouro (){
    	return primeiroTesouro;
    }
    
    public void mensagem (int num) {
    	tempoPermanencia = TEMPO_POP_UP;
    	numMensagem = num;
    }
    
    public void setDialogo (int num, double x, double y) {
    	if (!getPause()){
    		Main.sons.reproduzirSom("/sons/sara.wav", (float)x, (float)y); 		
    	}
    	tempoPermanencia = 0;
    	setPause(true);
    	dialogoID = num;
    }
    
    public void setDialogoTempo (int num){
    	tempoPermanencia = TEMPO_POP_UP;
    	dialogoID = num;
    }
    
    public int getLinha () {
    	return proximaLinha;
    }
    
    public int getDialogo () {
    	return dialogoID;
    }
    
    public int getTempoPermanencia () {
    	return tempoPermanencia;
    }
    
    public void proximaLinha () {
    	proximaLinha++;
    }
    
    /**
     * Quando o jogador puder ver uma área não vista
     *
     * @param x 
     * @param y 
     * @return true, se verdadeiro
     */
    private boolean podeVer(int x, int y) {
        if (x < 0 || y < 1 || x >= larguraNivel || y >= alturaNivel) return true;
        return visto[x + (y - 1) * (larguraNivel + 1)] || visto[(x + 1) + (y - 1) * (larguraNivel + 1)] || visto[x + y * (larguraNivel + 1)] || visto[(x + 1) + y * (larguraNivel + 1)] || visto[x + (y + 1) * (larguraNivel + 1)]
                || visto[(x + 1) + (y + 1) * (larguraNivel + 1)];
    }

    public List<Fronteiras> getFronteiras(Entidade e) {
        List<Fronteiras> resultado = new ArrayList<Fronteiras>();
        Fronteiras bb = e.getFronteiras().aumentar(Tile.LARGURA);

        int x0 = (int) (bb.x0 / Tile.LARGURA);
        int x1 = (int) (bb.x1 / Tile.LARGURA);
        int y0 = (int) (bb.y0 / Tile.ALTURA);
        int y1 = (int) (bb.y1 / Tile.ALTURA);

        resultado.add(new Fronteiras(null, 0, 0, 0, alturaNivel * Tile.ALTURA));
        resultado.add(new Fronteiras(null, 0, 0, larguraNivel * Tile.LARGURA, 0));
        resultado.add(new Fronteiras(null, larguraNivel * Tile.LARGURA, 0, larguraNivel * Tile.LARGURA, alturaNivel * Tile.ALTURA));
        resultado.add(new Fronteiras(null, 0, alturaNivel * Tile.ALTURA, larguraNivel * Tile.LARGURA, alturaNivel * Tile.ALTURA));

        for (int y = y0; y <= y1; y++) {
            if (y < 0 || y >= alturaNivel) {
                continue;
            }
            for (int x = x0; x <= x1; x++) {
                if (x < 0 || x >= larguraNivel) continue;
                tiles[x + y * larguraNivel].adicionaColisao(resultado, e);
            }
        }

        Set<Entidade> entidadesVisiveis = getEntidades(bb);
        for (Entidade ee : entidadesVisiveis) {
            if (ee != e && ee.bloqueia(e)) {
                resultado.add(ee.getFronteiras());
            }
        }
        return resultado;
    }

    /**
     * Linha de revelação
     *
     * @param x 
     * @param y 
     * @param raio
     */
    public void revelar(int x, int y, int raio) {
        for (int i = 0; i < raio * 2 + 1; i++) {
            linhaRevelacao(x, y, x - raio + i, y - raio, raio);
            linhaRevelacao(x, y, x - raio + i, y + raio, raio);
            linhaRevelacao(x, y, x - raio, y - raio + i, raio);
            linhaRevelacao(x, y, x + raio, y - raio + i, raio);
        }
    }

    private void linhaRevelacao(int x0, int y0, int x1, int y1, int raio) {
        for (int i = 0; i <= raio; i++) {
            int xx = x0 + (x1 - x0) * i / raio;
            int yy = y0 + (y1 - y0) * i / raio;
            if (xx < 0 || yy < 0 || xx >= larguraNivel || yy >= alturaNivel) return;
            int xd = xx - x0;
            int yd = yy - y0;
            if (xd * xd + yd * yd > raio * raio) return;
            Tile tile = getTile(xx, yy);
            if (tile instanceof Parede) return;
            visto[xx + yy * (larguraNivel + 1)] = true;
            visto[(xx + 1) + yy * (larguraNivel + 1)] = true;
            visto[xx + (yy + 1) * (larguraNivel + 1)] = true;
            visto[(xx + 1) + (yy + 1) * (larguraNivel + 1)] = true;
        }
    }
}