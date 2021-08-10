package eccezioni;

/**
 * Questa è un eccezione non controllata, che estende quella di IllegalArgumentException, che viene lanciata se un valore è negativo.
 * @author marcocampione
 *
 */
public class ValueNegativeException extends IllegalArgumentException{
	public ValueNegativeException() {}
	/**
	 * Metodo costruttore
	 * @param msg è il messaggio d'errore.
	 */
	public ValueNegativeException(String msg) {
		super(msg);
	}
}