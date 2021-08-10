package eccezioni;

import java.io.IOException;

/**
 * Questa è un eccezione controllata, che estende IOException, che viene lanciata se un oggetto Personale non è un dipendente del personale.
 * @author marcocampione
 *
 */
public class NotPersonaleException extends IOException{
	public NotPersonaleException() {}
	/**
	 * Metodo costruttore.
	 * @param msg è il messaggio d'errore.
	 */
	public NotPersonaleException(String msg) {
		super(msg);
	}
}