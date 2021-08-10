package eccezioni;

/**
 * Questa è un eccezione non controllata, che estende quella di IllegalArgumentException, che viene lanciata se la retribuzione è negativo.
 * @author marcocampione
 *
 */
public class FailureRetribuzioneException extends IllegalArgumentException{
	public FailureRetribuzioneException() {}
	/**
	 * Metodo costruttore.
	 * @param msg è il messaggio d'errore.
	 */
	public FailureRetribuzioneException(String msg) {
		super(msg);
	}
}