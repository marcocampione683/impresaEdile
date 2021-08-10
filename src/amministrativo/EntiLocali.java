package amministrativo;

import java.io.Serializable;

import eccezioni.ArgomentoNonValidoException;

/**
 * Questa classe esprime il concetto di Ente locale.
 * @author marcocampione
 *
 */
public class EntiLocali implements Cloneable, Serializable{
	/**
	 * Metodo costruttore che istanzia un oggetto EntiLocali.
	 * @param name  è il nome.
	 * @param place è la regione in cui è locato.
	 * @param address è l'indirizzo.  
	 * @param phone è il numero di telefono.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se almeno una delle componenti è null o è una stringa vuota.
	 * (pre-condizione: name != null || place != null || address != null || phone != null ||
	 * 					!name.equals("") || !place.equals("") || !address.equals("") || !phone.equals("")).
	 */
	public EntiLocali(String name, String place, String address, String phone) throws ArgomentoNonValidoException{
		if(name == null || place == null || address == null || phone == null ||
		   name.equals("") || place.equals("") || address.equals("") || phone.equals(""))
			throw new ArgomentoNonValidoException("Enti Locali: Inserire parametri validi!");
		nome = name;
		luogo = place;
		indirizzo = address;
		telefono = phone;
	}
	/**
	 * Metodo d'accesso che legge il nome dell'ente.
	 * @return il nome dell'ente.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Metodo d'accesso che legge la regione in cui è locato l'ente.
	 * @return la regione in cui è locato.
	 */
	public String getLuogo() {
		return luogo;
	}
	/**
	 * Metodo d'accesso che legge l'indirizzo di residenza.
	 * @return l'indirizzo.
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	/**
	 * Metodo d'accesso che legge il numero di telefono dell'ente.
	 * @return il numero di telefono.
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * Metodo modificatore che setta l'indirizzo dell'ente con uno nuovo.
	 * @param newAddress è il nuovo indirizzo.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newAddress è null.
	 * (pre-condizione; newAddress != null).
	 */
	public void setIndirizzo(String newAddress) throws ArgomentoNonValidoException {
		if(newAddress == null)
			throw new ArgomentoNonValidoException("Enti Locali: Inserisci parametri validi!");
		indirizzo = newAddress;
	}
	/**
	 * Metodo modificatore che setta il numero di telefono dell'ente.
	 * @param newPhone è il nuovo numero telefonico.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newPhone è null.
	 * (pre-condizione; newPhone != null).
	 */
	public void setTelefono(String newPhone) throws ArgomentoNonValidoException {
		if(newPhone == null)
			throw new ArgomentoNonValidoException("Enti Locali: Inserisci parametri validi!");
		telefono = newPhone;
	}
	@Override
	/**
	 * Metodo che restituisce una stringa contenenti le informazioni generali dell'ente. 
	 */
	public String toString() {
		return getClass().getName() + "[nome ente = "+nome+", Comune = "+luogo+", indirizzo = "+indirizzo+", numero telefonico = "+telefono+"]";
	}
	@Override
	/**
	 * Metodo predicativo che confronta un ente con un altro ente in ogni istanza
	 * restituendo true se uguali, false altrimenti.
	 */
	public boolean equals(Object other) {
		if(other == null || getClass() != other.getClass())
			return false;
		EntiLocali altroEnte = (EntiLocali) other;
		return nome.equals(altroEnte.nome) && luogo.equals(altroEnte.luogo) && 
				indirizzo.equals(altroEnte.indirizzo) && telefono.equals(altroEnte.telefono);
	}
	@Override
	/**
	 * Metodo che clona un oggetto ente e retituisce il clone.
	 */
	public EntiLocali clone() {
		try {
			return (EntiLocali) super.clone();
		}
		catch(CloneNotSupportedException c) {
			return null;
		}
	}
	/**
	 * Variabili d'istanza.
	 */
	private String nome, luogo, indirizzo, telefono;
}