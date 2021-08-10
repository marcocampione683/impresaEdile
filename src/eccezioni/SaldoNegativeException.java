package eccezioni;

/**
 * Questa è un eccezione non controllata, che estende quella di IllegalArgumentException, che viene lanciata se il saldo di un conto è negativo.
 * @author marcocampione
 *
 */
public class SaldoNegativeException extends IllegalArgumentException{
	public SaldoNegativeException() {}
	/**
	 * Metodo costruttore.
	 * @param msg è il messaggio d'errore.
	 */
	public SaldoNegativeException(String msg) {
		super(msg);
	}
}