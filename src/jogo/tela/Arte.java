/*
 * Responsável por carregar a arte do jogo.
 */

package jogo.tela;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import jogo.Main;

/**
 * A classe Arte, responsável por carregar  os sprites do jogo.
 */
public class Arte {
    
	// Carregar todos os sprites
    public static Bitmap[][] piso = recortar("/construcoes/cenario.png", 32, 32);
    public static int[][] corPiso = getCores(piso);
    public static Bitmap[][] paredes = recortar("/construcoes/cenario.png", 32, 56, 0, 104);
    public static int[][] corParedes = getCores(paredes);
    public static Bitmap[][] nevoa = recortar("/nevoa.png", 32, 32);
    public static Bitmap[][] cesar = recortar("/entidades/cesar.png", 32, 32);
    public static Bitmap[][] botoes = recortar("/interface/botoes.png", 128, 24);
    public static Bitmap[][] fonteJogo = recortar("/interface/fontejogo.png", 8, 8);
    public static Bitmap[][] iconesMapa = recortar("/interface/iconesmapa.png", 5, 5);
    public static Bitmap titulo = carregar("/interface/titulo.png");
    public static Bitmap painel = carregar("/interface/painel.png");
    public static Bitmap enter = carregar("/interface/enter.png");
    public static Bitmap sombra = carregar("/sombra.png");
    public static Bitmap[][] vampiro = recortar("/entidades/vampiro.png", 48, 48);
    public static Bitmap[][] fantasma = recortar("/entidades/fantasma.png", 48, 48);
    public static Bitmap[][] fantasmaTonto = recortar("/entidades/fantasma_tonto.png", 48, 48);
    public static Bitmap[][] aranha = recortar("/entidades/aranha.png", 48, 48);
    public static Bitmap[][] morcego = recortar("/entidades/morcego.png", 32, 32);
    public static Bitmap sombraMorcego = carregar("/entidades/sombramorcego.png");
    public static Bitmap pergaminhoO = carregar("/pergaminho_O.png");
    public static Bitmap pergaminhoA = carregar("/pergaminho_A.png");
    public static Bitmap correcaoAMoedas = carregar("/interface/correcao_amoedas.png");
    public static Bitmap correcaoATesouro = carregar("/interface/correcao_atesouro.png");
    public static Bitmap correcaoDoisRubi = carregar("/interface/correcao_doisrubi.png");
    public static Bitmap correcaoEuAbrindoOBau = carregar("/interface/correcao_euabrindoobau.png");
    public static Bitmap correcaoMoedaParaOuro = carregar("/interface/correcao_moedaparaouro.png");
    public static Bitmap concordanciaCorreta = carregar("/interface/concordancia_correta.png");
    public static Bitmap gestoMensagem = carregar("/interface/atencao_gesto.png");
    public static Bitmap tesouroMensagem = carregar("/interface/tesouros.png");
    public static Bitmap pause = carregar("/interface/pause.png");
    public static Bitmap telaContinue = carregar("/interface/continue.png");
    public static Bitmap supertiroMensagem = carregar("/interface/supertiro.png");
    public static Bitmap creditos1 = carregar("/interface/creditos1.png");
    public static Bitmap creditos2 = carregar("/interface/creditos2.png");
    public static Bitmap creditos3 = carregar("/interface/creditos3.png");
    public static Bitmap botaoInterrogacao = carregar("/construcoes/botaoInterrogacao.png");
    public static Bitmap botaoPorta = carregar("/construcoes/botaoPorta.png");
    public static Bitmap botaoPortao = carregar("/construcoes/botaoPortao.png");
    public static Bitmap[][] caixao = recortar("/entidades/caixao.png", 32, 40);
    public static Bitmap caixaoAberto = carregar("/construcoes/caixao_aberto.png");
    public static Bitmap[][] bala = recortar("/bala.png", 16, 16);
    public static Bitmap[][] supertiro = recortar("/supertiro.png", 16, 16);
    public static Bitmap mulher = carregar("/mulher.png");
    public static Bitmap espirito = carregar("/espirito.png");
    public static Bitmap[][] flashArma = recortar("/flash_arma.png", 16, 16);
    public static Bitmap[][] bauAMoedas = recortar("/baus/bau_amoedas.png", 64, 48);
    public static Bitmap[][] bauAsMoedas = recortar("/baus/bau_asmoedas.png", 64, 48);
    public static Bitmap[][] bauATesouro = recortar("/baus/bau_atesouro.png", 64, 48);
    public static Bitmap[][] bauDoisRubi = recortar("/baus/bau_doisrubi.png", 64, 48);
    public static Bitmap[][] bauEuAbrindoBau = recortar("/baus/bau_euabrindobau.png", 64, 48);
    public static Bitmap[][] bauEuAbroOBau = recortar("/baus/bau_euabroobau.png", 64, 48);
    public static Bitmap[][] bauMoedaDeOuro = recortar("/baus/bau_moedadeouro.png", 64, 48);
    public static Bitmap[][] bauMoedaParaOuro = recortar("/baus/bau_moedaparaouro.png", 64, 48);
    public static Bitmap[][] bauOTesouro = recortar("/baus/bau_otesouro.png", 64, 48);
    public static Bitmap[][] bauUmRubi = recortar("/baus/bau_umrubi.png", 64, 48);
    public static Bitmap[][] bauRubi = recortar("/baus/bau_rubi.png", 64, 48);
    public static Bitmap[][] coracao = recortar("/colecionaveis/coracao.png", 16, 16);
    public static Bitmap[][] armadilha = recortar("/construcoes/armadilha.png", 32, 32);
    public static Bitmap gestoQuadro = carregar("/libras/gesto_quadro.png");
    public static Bitmap gestosQuadro = carregar("/libras/gestos_quadro.png");
    public static Bitmap[][] moedaBronzePequena = recortar("/colecionaveis/moeda_bronze_pequena.png", 8, 8);
    public static Bitmap[][] moedaBronzeGrande = recortar("/colecionaveis/moeda_bronze_grande.png", 16, 16);
    public static Bitmap[][] moedaPrataPequena = recortar("/colecionaveis/moeda_prata_pequena.png", 8, 8);
    public static Bitmap[][] moedaPrataGrande = recortar("/colecionaveis/moeda_prata_grande.png", 16, 16);
    public static Bitmap[][] moedaOuroPequena = recortar("/colecionaveis/moeda_ouro_pequena.png", 8, 8);
    public static Bitmap[][] moedaOuroGrande = recortar("/colecionaveis/moeda_ouro_grande.png", 16, 16);
    public static Bitmap[][] esmeralda = recortar("/colecionaveis/esmeralda.png", 16, 16);
    public static Bitmap[][] rubi = recortar("/colecionaveis/rubi.png", 16, 16);
    public static Bitmap[][] diamante = recortar("/colecionaveis/diamante.png", 24, 24);
    public static Bitmap[][] brilho = recortar("/colecionaveis/brilho.png", 13, 13);
    public static Bitmap dinamite = carregar("/dinamite.png");
    public static Bitmap dinamiteEnterrada = carregar("/dinamite_enterrada.png");
    public static Bitmap armario = carregar("/construcoes/armario.png");
    public static Bitmap planta = carregar("/construcoes/planta.png");
    public static Bitmap rubiMenu = carregar("/rubi_menu.png");
    public static Bitmap porta = carregar("/construcoes/porta.png");
    public static Bitmap AEstatico = carregar("/libras/a_estatico.png");
    public static Bitmap BEstatico = carregar("/libras/b_estatico.png");
    public static Bitmap REstatico = carregar("/libras/r_estatico.png");
    public static Bitmap IEstatico = carregar("/libras/i_estatico.png");
    public static Bitmap letraA = carregar("/libras/a.png");
    public static Bitmap letraB = carregar("/libras/b.png");
    public static Bitmap letraR = carregar("/libras/r.png");
    public static Bitmap letraI = carregar("/libras/i.png");
    public static Bitmap botaoA = carregar("/construcoes/botaoA.png");
    public static Bitmap botaoB = carregar("/construcoes/botaoB.png");
    public static Bitmap botaoR = carregar("/construcoes/botaoR.png");
    public static Bitmap botaoI = carregar("/construcoes/botaoI.png");
    public static Bitmap[][] explosao = recortar("/efeitos/explosao_grande.png", 32, 32);
    public static Bitmap[][] inimigoMorre = recortar("/efeitos/morte_inimigo.png", 64, 64);
    public static Bitmap[][] inimigoMorre2 = recortar("/efeitos/morte_inimigo2.png", 32, 32);
    public static Bitmap[][] explosaoPequena = recortar("/efeitos/explosao_pequena.png", 32, 32);
    public static Bitmap[][] poeira = recortar("/efeitos/poeira.png", 12, 12);
    public static Bitmap espinhos = carregar("/construcoes/espinhos.png");
    public static Bitmap madeiras = carregar("/construcoes/madeiras.png");
    public static Bitmap maquina = carregar("/construcoes/maquina.png");
    
    /**
     * Recortar bitmap.
     *
     * @param string
     * @param c
     * @param a
     * @return bitmap[][]
     */
    private static Bitmap[][] recortar(String string, int c, int a) {
        return recortar(string, c, a, 0, 0);
    }

    /**
     * Recortar a figura a fim de exibir uma sequencia de animação.
     *
     * @param string 
     * @param c 
     * @param a 
     * @param bx
     * @param by
     * @return A figura recortada
     */
    private static Bitmap[][] recortar(String string, int c, int a, int bx, int by) {
        try {
            BufferedImage bi = ImageIO.read(Main.class.getResource(string));

            int xTiles = (bi.getWidth() - bx) / c;
            int yTiles = (bi.getHeight() - by) / a;

            Bitmap[][] resultado = new Bitmap[xTiles][yTiles];

            for (int x = 0; x < xTiles; x++) {
                for (int y = 0; y < yTiles; y++) {
                    resultado[x][y] = new Bitmap(c, a);
                    bi.getRGB(bx + x * c, by + y * a, c, a, resultado[x][y].pixels, 0, c);
                }
            }
            return resultado;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cor dos tiles.
     *
     * @param tiles
     * @return cor
     */
    private static int[][] getCores(Bitmap[][] tiles) {
        int[][] resultado = new int[tiles.length][tiles[0].length];
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                resultado[x][y] = getCor(tiles[x][y]);
            }
        }
        return resultado;
    }

    /**
     * Cor da imagem.
     *
     * @param bitmap
     * @return cor
     */
    private static int getCor(Bitmap bitmap) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < bitmap.pixels.length; i++) {
            int cor = bitmap.pixels[i];
            r += (cor >> 16) & 0xff;
            g += (cor >> 8) & 0xff;
            b += (cor) & 0xff;
        }

        r /= bitmap.pixels.length;
        g /= bitmap.pixels.length;
        b /= bitmap.pixels.length;

        return 0xff000000 | r << 16 | g << 8 | b;
    }

    /**
     * Carrega determinada figura para ser exibida no jogo.
     *
     * @param string
     * @return A figura
     */
    private static Bitmap carregar(String string) {
        try {
            BufferedImage bi = ImageIO.read(Main.class.getResource(string));

            int c = bi.getWidth();
            int a = bi.getHeight();

            Bitmap resultado = new Bitmap(c, a);
            bi.getRGB(0, 0, c, a, resultado.pixels, 0, c);

            return resultado;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}