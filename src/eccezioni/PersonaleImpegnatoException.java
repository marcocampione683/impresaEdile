package eccezioni;

/**
 * Questa è un eccezione non controllata, che estende quella di IllegalArgumentException, che viene lanciata se il personale è impegnato.
 * @author marcocampione
 *
 */
public class PersonaleImpegnatoException extends IllegalArgumentException{
	public PersonaleImpegnatoException() {}
	/**
	 * Metodo costruttore.
	 * @param msg è il messaggio d'errore.
	 */
	public PersonaleImpegnatoException(String msg) {
		super(msg);
	}
}