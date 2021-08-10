package amministrativo;

import eccezioni.ArgomentoNonValidoException;
import eccezioni.FailureRetribuzioneException;

/**
 * Questa classe esprime il concetto di operaio che estende quello di personale. Indica un operaio con una mansione specifica
 * oltre ad avere tutte le informazioni di personale.
 * 
 * @author marcocampione
 */
public class Operaio extends Personale {
	/**
	 * Metodo costruttore che istanzia un operaio.
	 * @param nome è il nome.
	 * @param cognome è il cognome.
	 * @param retribuzione è la retribuzione.
	 * @param conto è il conto corrente bancario.
	 * @param mans è la mansione che svolge.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo lancia se mans è null o è una stringa vuota.
	 * (pre-condizione: mans != null || !mans.equals("") ). 
	 */
	public Operaio(String nome, String cognome, double retribuzione,Conto conto, String mans) throws ArgomentoNonValidoException, FailureRetribuzioneException{
		super(nome,cognome,conto);
		
		if(retribuzione <= 0 || retribuzione < 800)
			throw new FailureRetribuzioneException("Inserire una retribuzione corretta per un profilo da operaio!");
		retrib = retribuzione;
		
		if(mans == null || mans.equals(""))
			throw new ArgomentoNonValidoException("Personale: Inserisci parametri validi!");
		
		mansione = mans;
	}
	/**
	 * Metodo d'accesso che legge la mansione dell'operaio.
	 * @return la mansione svolta.
	 */
	public String getMansione() {
		return mansione;
	}
	@Override
	public double getRetribuzione() {
		return retrib;
	}
	/**
	 * Metodo modificatore che setta la mansione dell'operaio con una nuova.
	 * @param newMans è la nuova mansione.
	 * @throws ArgomentoNonValidoException è l'eccezione che può essere lanciata dal metodo se newMans è null.
	 *(pre-condizione: newMans != null).
	 */
	public void setMansione(String newMans)throws ArgomentoNonValidoException {
		if(newMans == null)
			throw new ArgomentoNonValidoException("Personale: Inserisci parametri validi!");
		mansione = newMans;
	}
	@Override
	public void setRetribuzione(double newRetrib) throws FailureRetribuzioneException{
		if(newRetrib <= 0 || newRetrib < 800)
			throw new FailureRetribuzioneException("Inserire una retribuzione corretta per un profilo da operaio!");
		retrib = newRetrib;
	}
	@Override
	/**
	 * Metodo che restituisce una stringa con le informazioni generali dell'operaio.
	 */
	public String toString() {
		return super.toString() + "[mansione = "+mansione+", retribuzione = "+retrib+"]";
	}
	@Override
	/**
	 * Metodo che confronta due oggetti operaio in ogni istanza. Se sono uguali restituisce true, altrimenti false.
	 */
	public boolean equals(Object other) {
		if(!super.equals(other))
			return false;
		Operaio altroOperaio = (Operaio) other;
		return mansione.equals(altroOperaio.mansione) && Math.abs(retrib - altroOperaio.retrib) <= EPSILON;
	}
	@Override
	/**
	 * Metodo che clona un oggetto operaio e ne restituisce il clone.
	 */
	public Operaio clone() {
		return (Operaio) super.clone();
	}
	/**
	 * Variabili d'istanza.
	 */
	private String mansione;
	private double retrib;
	
	private static final double EPSILON = Math.pow(10, -14);
}