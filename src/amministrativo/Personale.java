package amministrativo;

import java.io.Serializable;

import eccezioni.ArgomentoNonValidoException;
import eccezioni.FailureRetribuzioneException;


/**
 * Questa classe astratta esprime il concetto di personale, cioè un dipendente che lavora in una fabbrica, ente, impresa ecc...
 * @author marcocampione
 *
 */
public abstract class Personale implements Cloneable, Serializable{
	/**
	 * Metodo costruttore che istanzia un oggetto Personale.
	 * @param name è il nome.
	 * @param surname è il cognome
	 * @param retrib è la retribuzione.
	 * @param cc è il conto corrente bancario.
	 * @throws FailureRetribuzioneException è l'eccezione che il metodo lancia in caso la retribuzione è minore o uguale a  zero.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo lancia nel caso in cui name surname o cc siano null o name surname siano stringhe vuote.
	 * (pre-condizione: retrib > 0 e name != null || surname != null || cc != null || !name.equals("") || !surname.equals("") )
	 */
	public Personale(String name, String surname, Conto cc) throws FailureRetribuzioneException, ArgomentoNonValidoException{
		if(name == null || surname == null || cc == null || name.equals("") || surname.equals(""))
			throw new ArgomentoNonValidoException("Personale: Inserire parametri validi!");
		nome = name;
		cognome = surname;
		conto = cc.clone();
		impegnato = false;
		
	}
	/**
	 * Metodo d'accesso che legge il nome del dipendente.
	 * @return il nome del dipendente.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Metodo d'accesso che legge il cognome del dipendente.
	 * @return il cognome del dipendente.
	 */
	public String getCognome() {
		return cognome;
	}
	/**
	 * Metodo astratto d'accesso che legge la retribuzione del dipendente.
	 * @return la retribuzione.
	 * (post-condizione retribuzione > 0).
	 */
	public abstract double getRetribuzione();
	/**
	 * Metodo d'accesso che restituisce il conto corrente.
	 * @return il conto corrente.
	 */
	public Conto getConto() {
		return  conto.clone();
	}
	/**
	 * Metodo predicativo che indica se un dipendente è impegnato o meno.
	 * @return true se è impegnato, false altrimenti.
	 */
	public boolean isImpegnato() {
		return impegnato;
	}
	/**
	 * Metodo modificatore che setta lo stato di impegnato di un dipendente.
	 * @param b è il nuovo stato d'impegnato.
	 */
	public void setImpegnato(boolean b) {
		impegnato = b;
	}
	/**
	 * Metodo modificatore che setta il conto del lavoratore con un nuovo conto.
	 * @param newConto è il nuovo conto corrente bancario.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo lancia se newConto è null.
	 * (pre-condizione: newConto != null).
	 */
	public void setConto(Conto newConto) throws ArgomentoNonValidoException {
		if(newConto == null)
			throw new ArgomentoNonValidoException("Conto: Inserisci parametri validi!");
		conto = newConto.clone();
	}
	/**
	 * Metodo modificatore astratto che setta la retribuzione con una nuova.
	 * @param newRetrib è la nuova retribuzione.
	 * @throws FailureRetribuzioneException è l'eccezione che il metodo può lanciare se newRetrib < 0.
	 * (pre-condizione newRetrib > 0)
	 */
	public abstract void setRetribuzione(double newRetrib) throws FailureRetribuzioneException;
	@Override
	/**
	 * Metodo che restituisce una stringa con le informazioni di un dipendente.
	 */
	public String toString() {
		return getClass().getName() + "[nome = "+nome+", cognome = "+cognome+", conto = "+conto.toString()+"]";
	}
	@Override
	/**
	 * Metodo predicativo che confronta due oggetti personale in ogni istanza restituendo true se uguali,
	 * false altrimenti.
	 */ 
	public boolean equals(Object other) {
		if(other == null || getClass() != other.getClass())
			return false;
		Personale altraPersona = (Personale) other;
		return nome.equals(altraPersona.nome) && cognome.equals(altraPersona.cognome) && conto.equals(altraPersona.conto);
	}
	@Override
	/**
	 * Metodo che clona un oggetto personale e ne restituisce il clone.
	 */
	public Personale clone() {
		try {
			Personale cloned = (Personale) super.clone();
			cloned.conto = conto.clone();
			
			return cloned;
		}
		catch(CloneNotSupportedException c) {
			return null;
		}
	}
	/**
	 * Variabili d'istanza.
	 */
	private String nome, cognome;
//	private double retribuzione;
	private Conto conto;
	private boolean impegnato;
}