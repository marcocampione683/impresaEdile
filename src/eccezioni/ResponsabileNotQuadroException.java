package eccezioni;

/**
 * Questa è un eccezione non controllata, che estende quella di IllegalArgumentException, che viene lanciata se il responsabile di un cantiere non è un Quadro.
 * @author marcocampione
 *
 */
public class ResponsabileNotQuadroException extends IllegalArgumentException{
	public ResponsabileNotQuadroException() {}
	/**
	 * Metodo costruttore.
	 * @param msg è il messaggio d'errore.
	 */
	public ResponsabileNotQuadroException(String msg) {
		super(msg);
	}
}