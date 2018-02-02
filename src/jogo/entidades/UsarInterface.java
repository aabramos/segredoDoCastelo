/*
 * Interface que define entidades usáveis no jogo.
 */
package jogo.entidades;

/**
 * A interface UsarInterface.
 */
public interface UsarInterface {

    /**
     * Trata entidades usáveis.
     *
     * @param usuario
     */
    public void usar(Entidade usuario);

    /**
     * Set se uma entidade é destacável.
     *
     * @param dest
     */
    public void destacar(boolean dest);

    /**
     * Usavel.
     *
     * @return true, se verdadeiro
     */
    public boolean usavel();   
}
