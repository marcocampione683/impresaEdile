package eccezioni;

/**
 * Questa è un eccezione non controllata, che estende quella di IllegalArgumentException, che viene lanciata se la somma di un conto è gestita in modo errata.
 * @author marcocampione
 *
 */
public class IllegalSumException extends IllegalArgumentException{
	public IllegalSumException() {}
	/**
	 * Metodo costruttore.
	 * @param msg è il messaggio d'errore.
	 */
	public IllegalSumException(String msg) {
		super(msg);
	}
}