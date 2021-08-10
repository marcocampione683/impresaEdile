package eccezioni;

/**
 * Questa è un eccezione non controllata, che estende quella di IllegalArgumentException, che viene lanciata se il responsabile di un cantiere non è un Dirigente.
 * @author marcocampione
 *
 */
public class ResponsabileNotDirigenteException extends IllegalArgumentException{
	public ResponsabileNotDirigenteException() {}
	/**
	 * Metodo costruttore.
	 * @param msg è il messaggio d'errore.
	 */
	public ResponsabileNotDirigenteException(String msg) {
		super(msg);
	}
}