package eccezioni;

/**
 * Questa è un eccezione non controllata, che estende quella di RunTimeException, che viene lanciata se una lista è vuota.
 * @author marcocampione
 *
 */
public class EmptyListException extends RuntimeException{
	public EmptyListException() {}
	/**
	 * Metodo costruttore
	 * @param msg è il messaggio d'errore.
	 */
	public EmptyListException(String msg) {
		super(msg);
	}
}