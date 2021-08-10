package amministrativo;

import java.io.Serializable;

import eccezioni.ArgomentoNonValidoException;

/**
 * Questa classe esprime il concetto di fornitore cioè una società che eroga dei servizi.
 * Dei fornitori è possibile sapere il nome e il servizio ed inoltre è possibile settarli.
 * @author marcocampione
 *
 */
public class Fornitori implements Cloneable, Serializable{
	/**
	 * Metodo costruttore che istanzia un oggetto di tipo fornitori.
	 * @param name è il nome della società.
	 * @param service indica il servizio che dispone.
	 * @throws ArgomentoNonValidoException è l'eccezione che può essere lanciata se name o service sono null o se sono stringhe vuote.
	 * (pre-condizione: name != null || service != null || !name.equals("") || !service.equals("")).
	 */
	public Fornitori(String name, String service) throws ArgomentoNonValidoException {
		if(name == null || service == null || name.equals("") || service.equals(""))
			throw new ArgomentoNonValidoException("Fornitori: Inserire parametri validi!");
		nome = name;
		servizio = service;
	}
	/**
	 * Metodo d'accesso che permette di leggere il nome del fornitore.
	 * @return il nome del fornitore.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Metodo d'accesso che permettere di leggere il servizio che il fornitore eroga.
	 * @return il servizio messo a disposizione dal fornitore.
	 */
	public String getServizio(){
		return servizio;
	}
	/**
	 * Metodo modificatore che setta il nome del fornitore con un nuovo nome.
	 * @param newName nuovo nome del fornitore.
	 * @throws ArgomentoNonValidoException è l'eccezione che può essere lanciata dal metodo se newName è null
	 * (pre-condizione: newName != null).
	 */
	public void setNome(String newName) throws ArgomentoNonValidoException{
		if(newName == null)
			throw new ArgomentoNonValidoException("Fornitori: Inserisci parametri validi!");
		nome = newName;
	}
	/**
	 * Metodo modificatore che setta il servizio che il fornitore eroga con uno nuovo.
	 * @param newService nuovo servizio.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newService è null.
	 * (pre-condizione: newService != null).
	 */
	public void setServizio(String newService) throws ArgomentoNonValidoException {
		if(newService == null)
			throw new ArgomentoNonValidoException("Fornitori: Inserisci parametri validi!");
		servizio = newService;
	}
	@Override
	/**
	 * Metodo che restituisce una stringa contenente le informazioni dei fornitori, quindi nome e servizio.
	 */
	public String toString() {
		return getClass().getName() + "[nome fornitore = "+nome+", servizio = "+servizio+"]";
	}
	@Override
	/**
	 * Metodo predicativo che permette di confrontare ogni istanza di fornitori con un altro oggetto fornitori restituendo true se è uguale, false altrimenti.
	 */
	public boolean equals(Object other) {
		if(other == null || getClass() != other.getClass())
			return false;
		Fornitori altroFornitore = (Fornitori) other;
		return nome.equals(altroFornitore.nome) && servizio.equals(altroFornitore.servizio);
	}
	@Override
	/**
	 * Metodo che permette di clonare un oggetto fornitori.
	 */
	public Fornitori clone() {
		try {
			return (Fornitori) super.clone();
		}
		catch(CloneNotSupportedException c) {
			return null;
		}
	}
	/**
	 * Variabili d'istanza.
	 */
	private String nome, servizio;
}