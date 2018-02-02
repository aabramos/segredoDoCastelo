/*
 * Manuseio dos sons.
 */

package jogo.sons;

import java.util.*;

import paulscode.sound.*;
import paulscode.sound.codecs.*;
import paulscode.sound.libraries.LibraryJavaSound;

/**
 * Manuseio dos sons.
 *
 */
public class Sons {

    private final Class<? extends Library> tipoBiblioteca;
    private SoundSystem sistemaSom;
    private boolean suportaOGG = true;
    private boolean suportaWAV = true;
    
    /** Constantes */
    private static final String MUSICA_NIVEL = "nivel";
    private static final String MUSICA_MENU = "menu";
    private static final int MAX_FONTES_POR_SOM = 5;
    
    public Sons() {
        tipoBiblioteca = LibraryJavaSound.class;

        try {
            SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
        } catch (SoundSystemException ex) {
            suportaOGG = false;
        }

        try {
            SoundSystemConfig.setCodec("wav", CodecWav.class);
        } catch (SoundSystemException ex) {
            suportaWAV = false;
        }

        try {
            sistemaSom = new SoundSystem(tipoBiblioteca);
        } catch (SoundSystemException ex) {
            sistemaSom = null;
        }
    }

    /**
     * Checa se existe suporte para OGG.
     *
     * @return true, se tiver
     */
    private boolean temSuporteOGG() {
        return suportaOGG && sistemaSom != null;
    }

    /**
     * Checa se existe suporte para WAV.
     *
     * @return true, se tiver
     */
    private boolean temSuporteWAV() {
        return suportaWAV && sistemaSom != null;
    }

    /**
     * Checa se está tocando.
     *
     * @param fonte 
     * @return true, se estiver
     */
    private boolean estaReproduzindo(String fonte) {
        if (temSuporteOGG()) {
            return sistemaSom.playing(fonte);
        }
        return false;
    }

    /**
     * Inicia música de fundo.
     */
    public void iniciarMusicaNivel() {
        String musicaFundo = "/sons/nivel.ogg";
        if (temSuporteOGG() && !estaReproduzindo(MUSICA_NIVEL)) {
            sistemaSom.backgroundMusic(MUSICA_NIVEL, Sons.class.getResource(musicaFundo), musicaFundo, true);
        }
    }
    
    public void iniciarMusicaMenu() {
        String musicaFundo = "/sons/menu.ogg";
        if (temSuporteOGG() && !estaReproduzindo(MUSICA_MENU)) {
            sistemaSom.backgroundMusic(MUSICA_MENU, Sons.class.getResource(musicaFundo), musicaFundo, true);
            sistemaSom.setLooping(MUSICA_MENU, true);
        }
    }

    private Set<String> carregado = new TreeSet<String>();

    /**
     * Set posição da fonte do som.
     *
     * @param x 
     * @param y 
     */
    public void posOuvinte(float x, float y) {
        sistemaSom.setListenerPosition(x, y, 50);
    }

    /**
     * Reproduzir um som no jogo na posição x e y.
     *
     * @param fonte
     * @param x
     * @param y 
     * @return true, se verdadeiro
     */
    public boolean reproduzirSom(String fonte, float x, float y) {
        return reproduzirSom(fonte, x, y, false);
    }

    /**
     * Reproduzir um som no jogo na posição x e y.
     *
     * @param fonte
     * @param x
     * @param y
     * @param bloquear
     * @return true, se verdadeiro
     */
    private boolean reproduzirSom(String fonte, float x, float y, boolean bloquear) {
        return reproduzirSom(fonte, x, y, bloquear, 0);
    }

    /**
     * Reproduzir um som no jogo na posição x e y.
     *
     * @param fonte
     * @param x 
     * @param y 
     * @param bloquear
     * @param indice 
     * @return true, se verdadeiro
     */
    private boolean reproduzirSom(String fonte, float x, float y, boolean bloquear, int indice) {
        if (indice < MAX_FONTES_POR_SOM && temSuporteWAV()) {
            String fonteIndexada = fonte + indice;
            if (!carregado.contains(fonteIndexada)) {
                sistemaSom.newSource(false, fonteIndexada, Sons.class.getResource(fonte), fonte, false, x, y, 0, SoundSystemConfig.ATTENUATION_ROLLOFF,
                        SoundSystemConfig.getDefaultRolloff());
                carregado.add(fonteIndexada);
            } else if (estaReproduzindo(fonteIndexada)) {
                if (bloquear) {
                    return false;
                }
                return reproduzirSom(fonte, x, y, false, indice + 1);
            }

			if (getSistemaSom().playing(fonteIndexada) || tipoBiblioteca.equals(LibraryJavaSound.class) ) {
				getSistemaSom().cull(fonteIndexada);
			}
            getSistemaSom().setPriority(fonteIndexada, false);
            getSistemaSom().setPosition(fonteIndexada, x, y, 0);
            getSistemaSom().setAttenuation(fonteIndexada, SoundSystemConfig.ATTENUATION_ROLLOFF);
            getSistemaSom().setDistOrRoll(fonteIndexada, SoundSystemConfig.getDefaultRolloff());
            getSistemaSom().setPitch(fonteIndexada, 1.0f);
            getSistemaSom().activate(fonteIndexada);
            getSistemaSom().play(fonteIndexada);
            return true;
        }
        return false;
    }

	public void pararMusica() {
		if (temSuporteOGG()) {
			getSistemaSom().stop(MUSICA_MENU);
			getSistemaSom().stop(MUSICA_NIVEL);
		}
	}
	
	public void reiniciarMusicaNivel() {
		getSistemaSom().play(MUSICA_NIVEL);
	}
	
	public void reiniciarMusicaMenu() {
		getSistemaSom().play(MUSICA_MENU);
	}
	
	public SoundSystem getSistemaSom() {
		return sistemaSom;
	}
}