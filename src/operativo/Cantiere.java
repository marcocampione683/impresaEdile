package operativo;

import java.io.Serializable;
import java.util.ArrayList;

import amministrativo.Operaio;
import amministrativo.Personale;
import eccezioni.ArgomentoNonValidoException;
import eccezioni.NotPersonaleException;
import eccezioni.ResponsabileNotDirigenteException;
import eccezioni.ResponsabileNotQuadroException;
import eccezioni.ValueNegativeException;

/**
 * Questa classe esprime il concetto di cantiere edile il quale ha un responsabile, un valore economico ed una squadra.
 * @author marcocampione
 *
 */
public class Cantiere implements Cloneable, Serializable{
	/**
	 * Metodo costruttore che istanzia un oggetto di tipo Cantiere.
	 * @param value è il valore economico del cantiere.
	 * @param p è il responsabile.
	 * @param s è la squadra.
	 * @throws ValueNegativeException è l'eccezione che il metodo può lanciare se value è minore o uguale a zero.
	 * @throws ResponsabileNotDirigenteException è l'eccezione che il metodo può lanciare se il valore è maggiore o uguale a 500.000€ e il responsabile non è un dirigente.
	 * @throws ResponsabileNotQuadroException è l'eccezione che il metodo può lanciare se il valore è minore di 500.000€ e il responsabile non è un quadro.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se p è null.
	 * (pre-condizione: value > 0 && p != null && ((value < 500.000 && p è quadro)||(value >= 500.000 && p è dirigente))).
	 */
	public Cantiere(int value, Personale p, Squadra s) throws ValueNegativeException, ResponsabileNotDirigenteException,ResponsabileNotQuadroException,ArgomentoNonValidoException{
		if(value <= 0)
			throw new ValueNegativeException("Valore negativo!");
		
		valore = value;
		
		if(p == null)
			throw new ArgomentoNonValidoException("Cantiere: Inserire parametri validi!");
		
		if(value < 500000) {
			if(p.getClass().getName().equals("amministrativo.Quadro"))
					responsabile = p.clone();
			else
				throw new ResponsabileNotQuadroException("Il responsabile non è un quadro!");
		}
		else if(value >= 500000) {
			if(p.getClass().getName().equals("amministrativo.Dirigente"))
					responsabile = p.clone();
			else
				throw new ResponsabileNotDirigenteException("Il responsabile non è un dirigente!");
		}
		
		squadra = s.clone();
	}
	/**
	 * Metodo d'accesso che legge il valore del cantiere.
	 * @return il valore del cantiere.
	 * (post-condizione valore > 0)
	 */
	public int getValore() {
		return valore;
	}
	/**
	 * Metodo d'accesso che legge il responsabile di cantiere.
	 * @return un Quadro se valore < 500.000, altrimenti un dirigente.
	 */
	public Personale getResponsabile(){
		return responsabile.clone();
	}
	/**
	 * Metodo d'accesso che legge la squadra di lavoro al cantiere.
	 * @return la squadra di lavoro.
	 */
	public Squadra getSquadra() {
		return squadra.clone();
	}
	/**
	 * Metodo modificatore che setta il responsabile con un nuovo responsabile.
	 * @param newResp è un nuovo responsabile.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newResp è null.
	 * @throws ResponsabileNotQuadroException è l'eccezione che il metodo può lanciare se il valore è minore di 500.000€ e newResp non è un quadro.
	 * @throws ResponsabileNotDirigenteException è l'eccezione che il metodo può lanciare se il valore è maggiore o uguale a 500.000€ e newResp non è un dirigente.
	 * (pre-condizione: newResp != null && ((valore < 500.000 && newResp è quadro)||(valore >= 500.000 && newResp è dirigente)) ).
	 */
	public void setResponsabile(Personale newResp) throws ArgomentoNonValidoException, ResponsabileNotQuadroException,ResponsabileNotDirigenteException{
		if(newResp == null)
			throw new ArgomentoNonValidoException("Cantiere-Responsabile: Inserire parametri validi!");
		if(valore < 500000 && !newResp.getClass().getName().equals("amministrativo.Quadro"))
			throw new ResponsabileNotQuadroException("Il responsabile non è un Quadro!");
		else if(valore >= 500000 && !newResp.getClass().getName().equals("amministrativo.Dirigente"))
			throw new ResponsabileNotDirigenteException("Il responsabile non è un Dirigente!");
		
		responsabile = newResp.clone();
	}
	/**
	 * Metodo modificatore che setta una squadra di lavoro con una nuova.
	 * @param newSqua è la nuova squadra di lavoro.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newSqua è null.
	 * (pre-condizione: newSqua != null).
	 */
	public void setSquadra(Squadra newSqua) throws ArgomentoNonValidoException{
		if(newSqua == null)
			throw new ArgomentoNonValidoException("Cantiere-Squadra: Inserire parametri validi!");
		
		squadra.getCapoSquadra().setImpegnato(false);
		
		for(Operaio op: squadra.getOperai())
			op.setImpegnato(false);
			
		squadra = newSqua.clone();
	}
	@Override
	/**
	 * Metodo che restituisce una stringa contenente le informazioni generali del cantiere.
	 */
	public String toString() {
			return getClass().getName() + "[Responsabile cantiere = "+responsabile.toString()+", valore cantiere = "+valore+", squadra = "+squadra.toString()+"]";
	}
	@Override
	/**
	 * Metodo che confronta due cantieri in ogni istanza e restituisce true se uguali, false altrimenti.
	 */
	public boolean equals(Object other) {
		if(other == null || getClass()!=other.getClass())
			return false;
		Cantiere altroCantiere = (Cantiere) other;
		return valore == altroCantiere.valore && responsabile.equals(altroCantiere.responsabile) && squadra.equals(altroCantiere.squadra);
	}
	@Override
	/**
	 * Metodo che clona un oggetto cantiere e restituisce il clone.
	 */
	public Cantiere clone() {
		try {
			Cantiere cloned = (Cantiere) super.clone();
			cloned.responsabile = responsabile.clone();
			cloned.squadra = squadra.clone();
			
			return cloned;
		}
		catch(CloneNotSupportedException c) {
			return null;
		}
	}
	/**
	 * Variabili d'istanza
	 */
	private int valore;
	private Squadra squadra;
	private Personale responsabile;
}