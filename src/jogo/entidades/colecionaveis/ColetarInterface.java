/*
 * Interface do Tesouro.
 */

package jogo.entidades.colecionaveis;

/**
 * A interface de coletar Tesouro.
 */
public interface ColetarInterface
{
	/**
	* Determina se é possível coletar um tesouro.
	*
	* @return true, se for possível.
	*/
	public boolean podeColetar();
	
	/**
	* Chamada ao coletar um tesouro.
	*
	* @param tesouro
	*/
	public void coletar(Tesouro tesouro);
	
	/**
	* Chamada ao coletar um tesouro.
	*
	* @param rubi
	*/
	public void coletarRubi(Rubi rubi);
	
	/**
	* Chamada ao coletar um coração.
	*
	* @param coracao
	*/
	public void recuperarVida(Coracao coracao);
	
	/**
	* Verifica a capacidade de sugar um tesouro atual do jogador.
	*
	* @return o poder de sugar um tesouro.
	*/
	public double getPoderColeta();
	
	/**
	* Devolve o valor total de moedas que o jogador possui.
	*
	* @return a quantidade de moedas.
	*/
	public int qtdeMoedas();
	
	/**
	* Efeito de flash no jogador ao coletar.
	*/
	public void flash();
}