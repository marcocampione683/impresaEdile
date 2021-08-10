package amministrativo;

import eccezioni.ArgomentoNonValidoException;
import eccezioni.FailureRetribuzioneException;

/**
 * Questa classe esprime il concetto di Quadro estendendo quello di Personale. Ad un Quadro viene assegnata una 
 * qualifica e un livello.
 * @author marcocampione
 *
 */
public class Quadro extends Personale{
	/**
	 * Metodo costruttore che istanzia un oggetto di tipo Quadro.
	 * @param nome è il nome.
	 * @param cognome è il cognome.
	 * @param retribuzione è la retribuzione.
	 * @param conto è il conto corrente bancario.
	 * @param quali è la qualifica.
	 * @param level è il livello.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se qualifica e livello hanno valore null o stringa vuota.
	 * (pre-condizione: quali != null or level != null or !quali.equals("") or !level.equals("") ) 
	 */
	public Quadro(String nome, String cognome, double retribuzione, Conto conto, String quali, String level)throws ArgomentoNonValidoException, FailureRetribuzioneException {
		super(nome, cognome,conto);
		
		if(retribuzione <= 0 || retribuzione < 1500)
			throw new FailureRetribuzioneException("Inserire una retribuzione corretta per un profilo da quadro!");
		retrib = retribuzione;
		
		if(quali == null || level == null || quali.equals("") || level.equals(""))
			throw new ArgomentoNonValidoException("Personale: Inserisci parametri validi!");
		
		qualifica = quali;
		livello = level;
	}
	/**
	 * Metodo d'accesso che legge la qualifica del quadro.
	 * @return la qualifica.
	 */
	public String getQualifica() {
		return qualifica;
	}
	/**
	 * Metdo d'accesso che legge il livello.
	 * @return il livello.
	 */
	public String getLivello() {
		return livello;
	}
	@Override
	public double getRetribuzione() {
		return retrib;
	}
	/**
	 * Metodo modificatore che setta la qualifica con una nuova qualifica.
	 * @param newQualifica è la nuova qualifica.
	 * @throws ArgomentoNonValidoException è l'eccezione che può lanciare il metodo se newQualifica è null.
	 * (pre-condizione: newQualifica != null).
	 */
	public void setQualifica(String newQualifica) throws ArgomentoNonValidoException {
		if(newQualifica == null)
			throw new ArgomentoNonValidoException("Personale: Inserisci parametri validi!");
		qualifica = newQualifica;
	}
	/**
	 * Metodo modificatore che setta il livello con uno nuovo.
	 * @param newLivello è il nuovo livello.
	 * @throws ArgomentoNonValidoException è l'eccezione che può lanciare il metodo se newLivello è null.
	 * (pre-condizione: newLivello != null).
	 */
	public void setLivello(String newLivello) throws ArgomentoNonValidoException {
		if(newLivello == null)
			throw new ArgomentoNonValidoException("Personale: Inserisci parametri validi!");
		livello = newLivello;
	}
	@Override
	public void setRetribuzione(double newRetrib) throws FailureRetribuzioneException{
		if(newRetrib <= 0 || newRetrib < 1500)
			throw new FailureRetribuzioneException("Inserire una retribuzione corretta per un profilo da quadro!");
		retrib = newRetrib;
	}
	@Override
	/**
	 * Metodo che restituisce una stringa contenente le informazioni del quadro.
	 */
	public String toString() {
		return super.toString() + "[qualifica = "+qualifica+", livello = "+livello+", retribuzione = "+retrib+"]";
	}
	@Override
	/**
	 * Metodo che confronta due oggetti Quadro in ogni istanza e restituisce true se sono uguali, false altrimenti.
	 */
	public boolean equals(Object other) {
		if(!super.equals(other))
			return false;
		Quadro altroQuadro = (Quadro) other;
		return qualifica.equals(altroQuadro.qualifica) && livello.equals(altroQuadro.livello) && Math.abs(retrib - altroQuadro.retrib) <= EPSILON;
	}
	@Override
	/**
	 * Metodo che clona un oggetto Quadro e lo restituisce.
	 */
	public Quadro clone() {
		return (Quadro) super.clone();
	}
	/**
	 * Variabili d'istanza.
	 */
	private String qualifica, livello;
	private double retrib;
	
	private static final double EPSILON = Math.pow(10, -14);
}