package amministrativo;

import eccezioni.ArgomentoNonValidoException;
import eccezioni.FailureRetribuzioneException;

/**
 * Questa classe esprime il concetto di Dirigente che estende quello di Personale. Del dirigente si vuole
 * sapere la sua area di competenza.
 * @author marcocampione
 *
 */
public class Dirigente extends Personale{
	/**
	 * Metodo costruttore che istanzia un oggetto di tipo Dirigente.
	 * @param nome è il nome.
	 * @param cognome è il cognome.
	 * @param retribuzione è la retribuzione.
	 * @param conto è il conto corrente bancario.
	 * @param areaComp è l' area di competenza del dirigente.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se areaComp è null o è una stringa vuota.
	 * (pre-condizione: areaComp != null || !areaComp.equals("") ).
	 */
	public Dirigente(String nome, String cognome, double retribuzione, Conto conto, String areaComp) throws ArgomentoNonValidoException,FailureRetribuzioneException{
		super(nome, cognome, conto);
		
		if(retribuzione <= 0 || retribuzione < 2000)
			throw new FailureRetribuzioneException("Inserire una retribuzione corretta per un profilo da dirigente!");
		retrib = retribuzione;
		
		if(areaComp == null || areaComp.equals(""))
			throw new ArgomentoNonValidoException("Personale: Inserisci parametri validi!");
		
		super.setImpegnato(true);
		areaCompetenza = areaComp;
	}
	/**
	 * Metodo d'accesso che legge l' area di competenza.
	 * @return l'area di competenza.
	 */
	public String getAreaCompetenza() {
		return areaCompetenza;
	}
	@Override
	public double getRetribuzione() {
		return retrib;
	}
	/**
	 * Metodo modificatore che setta l'area di competenza con una nuova.
	 * @param newArea è la nuova area di competenza.
	 * @throws ArgomentoNonValidoException è l'eccezione che può essere lanciata dal metodo se newArea è null.
	 * (pre-condizione: newArea != null).
	 */
	public void setAreaCompetenza(String newArea) throws ArgomentoNonValidoException{
		if(newArea == null)
			throw new ArgomentoNonValidoException("Personale: Inserisci parametri validi!");
		areaCompetenza = newArea;
	}
	@Override
	public void setRetribuzione(double newRetrib) throws FailureRetribuzioneException{
		if(newRetrib <= 0 || newRetrib < 2000)
			throw new FailureRetribuzioneException("Inserire una retribuzione corretta per un profilo da dirigente!");
		retrib = newRetrib;
	}
	@Override
	/**
	 * Metodo che restituisce una stringa contenente le informazioni generali del dirigente.
	 */
	public String toString() {
		return super.toString() + "[area di competenza = "+areaCompetenza+", retribuzione = "+retrib+"]";
	}
	@Override
	/**
	 * Metodo che confronta due oggetti della classe Dirigente in ogni istanza. Se sono uguali restituisce true , altrimenti false.
	 */
	public boolean equals(Object other) {
		if(!super.equals(other))
			return false;
		Dirigente altroDirigente = (Dirigente) other;
		return areaCompetenza.equals(altroDirigente.areaCompetenza) && Math.abs(retrib - altroDirigente.retrib) <= EPSILON;
	}
	@Override
	/**
	 * Metodo che clona un oggetto Dirigente restituendo il clone.
	 */
	public Dirigente clone() {
		return (Dirigente) super.clone();
	}
	/**
	 * Variabili d'istanza.
	 */
	private String areaCompetenza;
	private double retrib;
	
	private static final double EPSILON = Math.pow(10, -14);
}