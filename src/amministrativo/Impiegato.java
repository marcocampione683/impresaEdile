package amministrativo;

import eccezioni.ArgomentoNonValidoException;
import eccezioni.FailureRetribuzioneException;

/**
 * Questa classe esprime il concetto di Impiegato estendendo quello di Personale. Ad un impiegato 
 * viene assegnao un incarico di lavoro.
 * @author marcocampione
 *
 */
public class Impiegato extends Personale{
	/**
	 * Metodo costruttore che istanzia un oggetto Impiegato.
	 * @param nome è il nome.
	 * @param cognome è il cognome.
	 * @param retrib è la retribuzione.
	 * @param conto è il conto corrente bancario.
	 * @param inc è l'incarico di lavoro che deve svolgere.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se inc è null o è una stringa vuota.
	 * (pre-condizione: inc != null || !inc.equals("") ).
	 */
	public Impiegato(String nome, String cognome, double retribuzione, Conto conto, String inc) throws ArgomentoNonValidoException, FailureRetribuzioneException{
		super(nome, cognome, conto);
		super.setImpegnato(true);
		
		if(retribuzione <= 0 || retribuzione < 1200)
			throw new FailureRetribuzioneException("Inserire una retribuzione corretta per un profilo da impiegato!");
		retrib = retribuzione;
		
		if(inc == null || inc.equals(""))
			throw new ArgomentoNonValidoException("Personale: Inserisci parametri validi!");
		
		incarico = inc;
	}
	/**
	 * Metodo d'accesso che legge l'incarico.
	 * @return l'incarico affidato all'impiegato.
	 */
	public String getIncarico() {
		return incarico;
	}
	@Override
	public double getRetribuzione() {
		return retrib;
	}
	/**
	 * Metodo modificatore che setta l'incarico con uno nuovo.
	 * @param newIncarico è il nuovo incarico.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newIncarico è null.
	 * (pre-condizione: newIncarico != null).
	 */
	public void setIncarico(String newIncarico) throws ArgomentoNonValidoException {
		if(newIncarico == null)
			throw new ArgomentoNonValidoException("Personale: Inserisci parametri validi!");
		incarico = newIncarico;
	}
	@Override
	public void setRetribuzione(double newRetrib) throws FailureRetribuzioneException{
		if(newRetrib <= 0 || newRetrib < 1200)
			throw new FailureRetribuzioneException("Inserire una retribuzione corretta per un profilo da impiegato!");
		retrib = newRetrib;
	}
	@Override
	/**
	 * Metodo che restituisce una stringa contenenti le informazioni dell'impiegato.
	 */
	public String toString() {
		return super.toString() + "[incarico = "+incarico+", retribuzione = "+retrib+"]";
	}
	@Override
	/**
	 * Metodo che confronta due oggetti impiegato in ogni istanza. Restituisce true se sono uguali, false altrimenti.
	 */
	public boolean equals(Object other) {
		if(!super.equals(other))
			return false;
		Impiegato altroImpiegato = (Impiegato) other;
		return incarico.equals(altroImpiegato.incarico) && Math.abs(retrib - altroImpiegato.retrib) <= EPSILON;
	}
	@Override
	/**
	 * Metodo che clona un oggetto Impiegato e lo restituisce.
	 */
	public Impiegato clone() {
		return (Impiegato) super.clone();
	}
	/**
	 * Variabili d'istanza.
	 */
	private String incarico;
	private double retrib;
	
	private static final double EPSILON = Math.pow(10, -14);
}