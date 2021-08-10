package amministrativo;

import java.io.Serializable;

import eccezioni.ArgomentoNonValidoException;

/**
 * Questa classe esprime il concetto di cliente che rappresenta una persona fisica.
 * @author marcocampione
 *
 */
public class Clienti implements Cloneable, Serializable{
	/**
	 * Metodo costruttore che istanzia un oggetto Clienti.
	 * @param name è il nome del cliente.
	 * @param surname è il cognome.
	 * @param address è l'indirizzo.
	 * @param phone è il numero di telefono.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se tutte le istanze di clienti sono null o strinhe vuote.
	 * (pre-condizione: name != null || surname != null || address != null || phone != null ||
	 *	   				!name.equals("") || !surname.equals("") || !address.equals("") || !phone.equals("")).
	 */
	public Clienti(String name, String surname, String address, String phone) throws ArgomentoNonValidoException{
		if(name == null || surname == null || address == null || phone == null ||
		   name.equals("") || surname.equals("") || address.equals("") || phone.equals(""))
			throw new ArgomentoNonValidoException("Clienti: Inserire parametri validi!");
		nome = name;
		cognome = surname;
		indirizzo = address;
		telefono = phone;
	}
	/**
	 * Metodo d'accesso che permette di leggere il nome del cliente.
	 * @return il nome del cliente.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Metodo d'accesso che permette di leggere il cognome del cliente.
	 * @return il cognome del cliente.
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * Metodo d'accesso che permette di leggere l'indirizzo del cliente.
	 * @return l'indirizzo del cliente.
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	/**
	 * Metodo d'accesso che permette di leggere il numero di telefono del cliente.
	 * @return il numero di telefono.
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * Metodo mdificatore che setta l'indirizzo del cliente con uno nuovo.
	 * @param newAddress è un nuovo indirizzo.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newAddress è null.
	 * (pre-condizione: newAddress != null).
	 */
	public void setIndirizzo(String newAddress) throws ArgomentoNonValidoException {
		if(newAddress == null)
			throw new ArgomentoNonValidoException("Clienti: Inserire parametri validi!");
		indirizzo = newAddress;
	}
	/**
	 * Metodo modificatore che setta il numero di telefono del cliente con uno nuovo.
	 * @param newPhone è un nuovo numero di telefono.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newPhone è null.
	 * (pre-condizione: newPhone != null).
	 */
	public void setTelefono(String newPhone) throws ArgomentoNonValidoException{
		if(newPhone == null)
			throw new ArgomentoNonValidoException("Clienti: Inserire parametri validi!");
		telefono = newPhone;
	}
	@Override
	/**
	 * Metodo che restituisce una stringa contenente le informazioni generali di un clienti.
	 */
	public String toString() {
		return getClass().getName() + "[nome = "+nome+", cognome = "+cognome+", indirizzo = "+indirizzo+", numero telefonico = "+telefono+"]";
	}
	@Override
	/**
	 * Metodo predicativo che confronto un oggetto Clienti con un altro oggetto Clienti in ogni istanza.
	 * Restituisce true se uguale, false altrimenti.
	 */
	public boolean equals(Object other) {
		if(other == null || getClass() != other.getClass())
			return false;
		Clienti altroCliente = (Clienti) other;
		return nome.equals(altroCliente.nome) && cognome.equals(altroCliente.cognome) && 
				indirizzo.equals(altroCliente.indirizzo) && telefono.equals(altroCliente.telefono);
	}
	@Override
	/**
	 * Metodo che clona un oggetto Clienti restituendo il clone.
	 */
	public Clienti clone() {
		try {
			return (Clienti) super.clone();
		}
		catch(CloneNotSupportedException c) {
			return null;
		}
	}
	/**
	 * Variabili d'istanza.
	 */
	private String nome, cognome, indirizzo, telefono;
}