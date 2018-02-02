/*
 * Definição das teclas do teclado que serão usadas no jogo.
 */
package jogo;

import java.util.*;

/**
 * A classe teclas, que define as teclas usadas no jogo.
 */
public class Teclas {
    
    public final class Tecla {
        
        public boolean proxEstado = false;
        private boolean estavaPressionado = false;
        public boolean pressionado = false;

        /**
         * Instanciando
         */
        public Tecla() {
            teclas.add(this);
        }

        public void frame() {
            estavaPressionado = pressionado;
            pressionado = proxEstado;
        }

        public boolean foiPressionado() {
            return !estavaPressionado && pressionado;
        }
        
        public void liberar() {
            proxEstado = false;
        }
    }

    private List<Tecla> teclas = new ArrayList<Tecla>();
    public Tecla acima = new Tecla();
    public Tecla abaixo = new Tecla();
    public Tecla esquerda = new Tecla();
    public Tecla direita = new Tecla();
    public Tecla atirar = new Tecla();
    public Tecla usar = new Tecla();
    public Tecla esc = new Tecla();
    public Tecla enter = new Tecla();

    public void frame() {
        for (Tecla tecla : teclas)
            tecla.frame();
    }

    public void liberar() {
        for (Tecla key : teclas)
            key.liberar();
    }
}
