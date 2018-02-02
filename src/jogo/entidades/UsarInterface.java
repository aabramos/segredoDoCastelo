/*
 * Interface que define entidades us�veis no jogo.
 */
package jogo.entidades;

/**
 * A interface UsarInterface.
 */
public interface UsarInterface {

    /**
     * Trata entidades us�veis.
     *
     * @param usuario
     */
    public void usar(Entidade usuario);

    /**
     * Set se uma entidade � destac�vel.
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
