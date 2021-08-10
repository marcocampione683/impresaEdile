package eccezioni;

/**
 * Questa è un eccezione non controllata, che estende quella di NullPointerException, che viene lanciata se il parametro ha un valore non valido.
 * @author marcocampione
 *
 */
public class ArgomentoNonValidoException extends NullPointerException{
	public ArgomentoNonValidoException() {}
	/**
	 * Metodo costruttore.
	 * @param msg è il messaggio d'errore.
	 */
	public ArgomentoNonValidoException(String msg) {
		super(msg);
	}
}