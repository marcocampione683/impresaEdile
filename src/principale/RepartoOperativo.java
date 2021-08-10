package principale;

import java.io.Serializable;
import java.util.ArrayList;

import eccezioni.ArgomentoNonValidoException;
import eccezioni.EmptyListException;
import operativo.Cantiere;

/**
 * Questa classe esprime il concetto di reparto operativo. Esso si occupa di gestire l'attività di cantiere.
 * Può gestire più cantieri contemporaneamente.
 * @author marcocampione
 *
 */
public class RepartoOperativo implements Serializable, Cloneable{
	/**
	 * Metodo costruttore che istanzia un reparto operativo.
	 */
	public RepartoOperativo() {
		cantieri = new ArrayList<Cantiere>();
	}
	/**
	 * Metodo d'accesso ai cantieri.
	 * @return la lista dei cantieri.
	 */
	public ArrayList<Cantiere> getCantieri() {
		return (ArrayList<Cantiere>) cantieri.clone();
	}
	/**
	 * Metodo modificatore che setta la lista di cantieri con una nuova.
	 * @param newCantieri è la nuova lista cantieri.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newCantieri è null.
	 * (pre-condizione: newCantieri != null).
	 */
	public void setCantieri(ArrayList<Cantiere> newCantieri) throws ArgomentoNonValidoException{
		if(newCantieri == null)
			throw new ArgomentoNonValidoException("Reprto operativo: Inserire parametri validi!");
		
		cantieri = (ArrayList<Cantiere>)newCantieri.clone();
	}
	/**
	 * Metodo modificatore che aggiunge un nuovo cantiere su cui lavorare.
	 * @param nuovoCantiere è il nuovo cantiere che si sta aprendo.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se nuovoCantiere è null.
	 * (pre-ondizione: nuovoCantiere != null).
	 */
	public void aggiungiCantiere(Cantiere nuovoCantiere) throws ArgomentoNonValidoException {
		if(nuovoCantiere == null)
			throw new ArgomentoNonValidoException("Reparto Operativo: Inserire parametri validi!");
		
		cantieri.add(nuovoCantiere.clone());
	}
	/**
	 * Metodo modificatore che elimina un cantiere dalla lista.
	 * @param cantiere è il cantiere che si vuole eliminare.
	 * @return true se il cantiere è eliminato, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista cantieri è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se cantiere è null.
	 * (pre-condizione: cantiere != null && cantieri.size()>0).
	 */
	public boolean rimuoviCantiere(Cantiere cantiere) throws EmptyListException,ArgomentoNonValidoException {
		if(cantiere == null)
			throw new ArgomentoNonValidoException("Reparto Operativo: Inserire parametri validi!");
		if(cantieri.isEmpty())
			throw new EmptyListException("Lista cantieri vuota!");
		
		for(Cantiere c: cantieri)
			if(c.equals(cantiere)) {
				cantieri.remove(c);
				return true;
			}
		return false;
	}
	/**
	 * Metodo predicativo che controlla se un cantiere è un cantiere dell'impresa.
	 * @param cantiere è il cantiere da controllare.
	 * @return true se il cantiere è un cantiere dell'impresa, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista cantieri è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se cantiere è null.
	 * (pre-condizione: cantiere != null && cantieri.size()>0).
	 */
	public boolean isCantiere(Cantiere cantiere) throws EmptyListException,ArgomentoNonValidoException{
		if(cantiere == null)
			throw new ArgomentoNonValidoException("Reparto Operativo: Inserire parametri validi!");
		if(cantieri.isEmpty())
			throw new EmptyListException("Lista cantieri vuota!");
		
		for(Cantiere c: cantieri)
			if(c.equals(cantiere)) {
				return true;
			}
		return false;
	}
	/**
	 * Metodo che cerca un cantiere all'interno della lista cantieri. 
	 * @param cantiere è il cantiere da cercare.
	 * @return l'indice in cui è posizionato nella lista cantieri, -1 altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista cantieri è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se cantiere è null.
	 * (pre-condizione: cantiere != null && cantieri.size()>0).
	 */
	public int cercaCantiere(Cantiere cantiere) throws EmptyListException,ArgomentoNonValidoException{
		if(cantiere == null)
			throw new ArgomentoNonValidoException("Reparto Operativo: Inserire parametri validi!");
		if(cantieri.isEmpty())
			throw new EmptyListException("Lista cantieri vuota!");
		
		for(int i=0;i<cantieri.size();i++)
			if(cantieri.get(i).equals(cantiere)) {
				return i;
			}
		return -1;
	}
	@Override
	/**
	 * Metodo che clona il reparto operativo e ne restituisce il clone.
	 */
	public RepartoOperativo clone() {
		try {
			RepartoOperativo cloned = (RepartoOperativo)super.clone();
			cloned.cantieri = (ArrayList<Cantiere>) cantieri.clone();
			
			return cloned;
		}
		catch(CloneNotSupportedException ce) {
			return null;
		}
	}
	/**
	 * Variabili d'istanza
	 */
	private ArrayList<Cantiere> cantieri;
	
}