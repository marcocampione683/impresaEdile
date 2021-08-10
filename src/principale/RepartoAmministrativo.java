package principale;

import java.io.Serializable;
import java.util.ArrayList;

import amministrativo.Clienti;
import amministrativo.Conto;
import amministrativo.EntiLocali;
import amministrativo.Fornitori;
import amministrativo.Personale;
import eccezioni.ArgomentoNonValidoException;
import eccezioni.EmptyListException;
/**
 * Questa classe esprime il concetto di reparto ammministrativo di un impresa edile. Esso gestisce i rapporti
 * esterni dell'impresa in particolare con fornitori, enti locali, clienti e gestisce il personale occupandosi
 * di assunzioni, licenziamenti e pagamento stipendi.
 * @author marcocampione
 *
 */
public class RepartoAmministrativo implements Serializable, Cloneable{
	/**
	 * Metodo costruttore che istanzia un reparto amministrativo.
	 */
	public RepartoAmministrativo() {
		fornitori = new ArrayList<Fornitori>();
		enti = new ArrayList<EntiLocali>();
		clienti = new ArrayList<Clienti>();
		personale = new ArrayList<Personale>();
	}
	/**
	 * Metodo d'accesso che legge la lista di fornitori.
	 * @return la lista di fornitori.
	 */
	public ArrayList<Fornitori> getFornitori(){
		return (ArrayList<Fornitori>) fornitori.clone();
	}
	/**
	 * Metodo d'accesso che legge la lista di enti locali.
	 * @return la lista di enti locali.
	 */
	public ArrayList<EntiLocali> getEntiLocali(){
		return (ArrayList<EntiLocali>) enti.clone();
	}
	/**
	 * Metodo d'accesso che legge la lista di clienti.
	 * @return la lista di clienti.
	 */
	public ArrayList<Clienti> getClienti(){
		return (ArrayList<Clienti>) clienti.clone();
	}
	/**
	 * Metodo d'accesso che legge la lista del personale a disposizione.
	 * @return la lista del personale.
	 */
	public ArrayList<Personale> getPersonale(){
		return (ArrayList<Personale>) personale.clone();
	}
	/**
	 * Metodo modificatore che setta la lista dei fornitori con una nuova.
	 * @param newFornitori è la nuova lista fornitori.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newFornitori è null.
	 * (pre-condizione: newFornitori != null).
	 */
	public void setListFornitori(ArrayList<Fornitori> newFornitori) throws ArgomentoNonValidoException{
		if(newFornitori == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Fornitori: inerire parametri validi!");
		
		fornitori = (ArrayList<Fornitori>) newFornitori.clone();
	}
	/**
	 * Metodo modificatore che setta la lista degli enti con una nuova.
	 * @param newEnti è la nuova lista degli enti locali.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newEnti è null.
	 * (pre-condizione: newEnti != null).
	 */
	public void setListEnti(ArrayList<EntiLocali> newEnti) throws ArgomentoNonValidoException{
		if(newEnti == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Enti Locali: inerire parametri validi!");
		
		enti = (ArrayList<EntiLocali>) newEnti.clone();
	}
	/**
	 * Metodo modificatore che setta la lista dei clienti con una nuova.
	 * @param newClienti è la nuova lista dei clienti.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newClienti è null.
	 * (pre-condizione: newClienti != null).
	 */
	public void setListClienti(ArrayList<Clienti> newClienti) throws ArgomentoNonValidoException{
		if(newClienti == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Clienti: inerire parametri validi!");
		
		clienti = (ArrayList<Clienti>) newClienti.clone();
	}
	/**
	 * Metodo modificatore che setta la lista del personale con una nuova.
	 * @param newPersonale è la nuova lista del personale.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newPersonale è null.
	 * (pre-condizione: newPersonale != null).
	 */
	public void setListPersonale(ArrayList<Personale> newPersonale) throws ArgomentoNonValidoException{
		if(newPersonale == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Personale: inerire parametri validi!");
		
		personale = (ArrayList<Personale>) newPersonale.clone();
	}
	/**
	 * Metodo modificatore che permette di assumere un nuovo dipendente.
	 * @param lav è il nuovo lavoratore dipendente.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se lav è null.
	 * (pre-codizione: lav != null).
	 */
	public void assumiPersonale(Personale lav) throws ArgomentoNonValidoException{
		if(lav == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Personale: inerire parametri validi!");
		
		personale.add(lav.clone());
	}
	/**
	 * Metodo modificatore che aggiunge un fornitore alla lista fornitori.
	 * @param fornitore è un nuovo fornitore dell'impresa.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se fornitore è null.
	 * (pre-condizione: fornitore != null).
	 */
	public void aggiungiFornitore(Fornitori fornitore) throws ArgomentoNonValidoException{
		if(fornitore == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Fornitori: inerire parametri validi!");
		
		fornitori.add(fornitore.clone());
	}
	/**
	 * Metodo modificatore che aggiunge un ente alla lista degli enti locali. 
	 * @param ente è un nuovo ente locale.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se ente è null.
	 * (pre-condizione: ente != null).
	 */
	public void aggiungiEnte(EntiLocali ente) throws ArgomentoNonValidoException{
		if(ente == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Enti Locali: inerire parametri validi!");
		
		enti.add(ente.clone());
	}
	/**
	 * Metodo modificatore che aggiunge un cliente alla lista dei clienti. 
	 * @param cliente è un nuovo cliente.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se cliente è null.
	 * (pre-condizione: cliente != null).
	 */
	public void aggiungiCliente(Clienti cliente) throws ArgomentoNonValidoException{
		if(cliente == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Clienti: inerire parametri validi!");
		
		clienti.add(cliente.clone());
	}
	/**
	 * Metodo modificatore che elimina un lavoratore dipendente, licenziandolo.
	 * @param per è il lavoratore da eliminare.
	 * @return true se è stato eliminato, altrimenti false.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista del personale è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se per è null.
	 * (pre-condizione: per != null && personale.size()>0).
	 */
	public boolean licenziaPersonale(Personale per) throws EmptyListException, ArgomentoNonValidoException {
		if(per == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Personale: inerire parametri validi!");
		if(personale.isEmpty())
			throw new EmptyListException("Lista personale vuota!");

		for(Personale p: personale) {
			if(p.equals(per)) {
				personale.remove(p);
				return true;
			}
		}
		return false;
	}
	/**
	 * Metodo modificatore che elimina un fornitore dalla list dei fornitori.
	 * @param fornitore è l'elemento da rimuovere.
	 * @return true se è stato rimosso, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista fornitori è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se fornitore è null.
	 * (pre-condizione: fornitore != null && fornitori.size()>0).
	 */
	public boolean rimuoviFornitore(Fornitori fornitore) throws EmptyListException, ArgomentoNonValidoException{
		if(fornitore == null) 
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Fornitori: inerire parametri validi!");
		if(fornitori.isEmpty())
			throw new EmptyListException("Lista fornitori vuota!");

		for(Fornitori f: fornitori)
			if(f.equals(fornitore)) {
				fornitori.remove(f);
				return true;
			}
		return false;
	}
	/**
	 * Metodo modificatore che elimina un ente dalla lista degli enti locali.
	 * @param ente è l'elemento da rimuovere.
	 * @return true se è stato rimosso, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista degli enti locali è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se ente è null.
	 * (pre-condizione: ente != null && enti.size()>0).
	 */
	public boolean rimuoviEnte(EntiLocali ente) throws EmptyListException, ArgomentoNonValidoException{
		if(ente == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Enti Locali: inerire parametri validi!");
		if(enti.isEmpty())
			throw new EmptyListException("Lista enti vuota!");
		
		for(EntiLocali e: enti)
			if(e.equals(ente)) {
				enti.remove(e);
				return true;
			}
		return false;
	}
	/**
	 * Metodo modificatore che rimuove un cliente dalla lista dei clienti.
	 * @param cliente è l'elemento da rimuovere.
	 * @return true se è stato rimosso, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista clienti è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se cliente è null.
	 * (pre-condizione: cliente != null && clienti.size()>0).
	 */
	public boolean rimuoviCliente(Clienti cliente) throws EmptyListException,ArgomentoNonValidoException{
		if(cliente == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Clienti: inerire parametri validi!");
		if(clienti.isEmpty())
			throw new EmptyListException("Lista clienti è vuota!");
		
		for(Clienti c: clienti)
			if(c.equals(cliente)) {
				clienti.remove(c);
				return true;
			}
		return false;
	}
	/**
	 * Metodo predicativo che controlla se un lavoratore lavora per l'impresa.
	 * @param lav è il lavoratore da controllare.
	 * @return true se lavora nell'impresa, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista del personale è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se lav è null.
	 *(pre-condizione: lav != null && personale.size()>0).
	 */
	public boolean isPersonale(Personale lav) throws EmptyListException,ArgomentoNonValidoException {
		if(lav == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Personale: inerire parametri validi!");
		if(personale.isEmpty())
			throw new EmptyListException("Lista personale vuota. Ricerca annullata!");
		
		for(Personale p: personale)
			if(p.equals(lav))
				return true;
		return false;
	}
	/**
	 * Metodo predicativo che controlla se un fornitore è in rapporti con l'impresa.
	 * @param forn è il fornitore da controllare.
	 * @return true se è in rapporti con l'impresa, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista dei fornitori è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se forn è null.
	 * (pre-condizione: forn != null && fornitori.size()>0).
	 */
	public boolean isFornitore(Fornitori forn) throws EmptyListException,ArgomentoNonValidoException {
		if(forn == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Fornitori: inerire parametri validi!");
		if(fornitori.isEmpty())
			throw new EmptyListException("Lista fornitori vuota. Ricerca annullata!");
		
		for(Fornitori f: fornitori)
			if(f.equals(forn))
				return true;
		return false;
	}
	/**
	 * Metodo predicativo che controlla se un ente è in rapporti con l'impresa.
	 * @param en è l'ente da controllare.
	 * @return true se l'ente è in rapporti con l'impresa, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista degli enti è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se en è null.
	 * (pre-condizione: en != null && enti.size()>0).
	 */
	public boolean isEnte(EntiLocali en) throws EmptyListException, ArgomentoNonValidoException {
		if(en == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Enti Locali: inerire parametri validi!");
		if(enti.isEmpty())
			throw new EmptyListException("Lista enti locali vuota. Ricerca annullata!");
		
		for(EntiLocali e: enti)
			if(e.equals(en))
				return true;
		return false;
	}
	/**
	 * Metodo predicativo che controlla se il cliente è in rapporti con l'impresa.
	 * @param cl è il cliente da controllare.
	 * @return true se il cliente è in rapporti con l'impresa, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista dei clienti è null.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se cl è null.
	 * (pre-condizione: cl != null && clienti.size()>0).
	 */
	public boolean isCliente(Clienti cl) throws EmptyListException,ArgomentoNonValidoException{
		if(cl == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Clienti: inerire parametri validi!");
		if(clienti.isEmpty())
			throw new EmptyListException("Lista clienti vuota. Ricerca annullata!");
		
		for(Clienti c: clienti)
			if(c.equals(cl))
				return true;
		return false;
	}
	/**
	 * Metodo predicativo che cerca un dipendente.
	 * @param lav è il dipendente da cercare.
	 * @return l'indice della posizione nella lista personale, -1 altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista del personale è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se lav è null.
	 * (pre-condizione: lav != null && personale.size()>0).
	 */
	public int cercaPersonale(Personale lav) throws EmptyListException,ArgomentoNonValidoException {
		if(lav == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Personale: inerire parametri validi!");
		if(personale.isEmpty())
			throw new EmptyListException("Lista personale vuota. Ricerca annullata!");
		
		for(int i=0;i<personale.size();i++)
			if(personale.get(i).equals(lav))
				return i;
		return -1;
	}
	/**
	 * Metodo predicativo che cerca un fornitore.
	 * @param forn è il fornitore da cercare.
	 * @return l'indice della posizione nella lista fornitori, -1 altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista dei fornitori è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se forn è null.
	 * (pre-condizione: forn != null && fornitori.size()>0).
	 */
	public int cercaFornitore(Fornitori forn) throws EmptyListException,ArgomentoNonValidoException {
		if(forn == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Fornitori: inerire parametri validi!");
		if(fornitori.isEmpty())
			throw new EmptyListException("Lista fornitori vuota. Ricerca annullata!");
		
		for(int i=0;i<fornitori.size();i++)
			if(fornitori.get(i).equals(forn))
				return i;
		return -1;
	}
	/**
	 * Metodo predicativo che cerca un ente locale.
	 * @param en è l'ente da cercare.
	 * @return l'indice della posizione nella lista enti, -1 altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista degli enti è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se en è null.
	 * (pre-condizione: en != null && enti.size()>0).
	 */
	public int cercaEnte(EntiLocali en) throws EmptyListException, ArgomentoNonValidoException {
		if(en == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Enti Locali: inerire parametri validi!");
		if(enti.isEmpty())
			throw new EmptyListException("Lista enti locali vuota. Ricerca annullata!");
		
		for(int i=0;i<enti.size();i++)
			if(enti.get(i).equals(en))
				return i;
		return -1;
	}
	/**
	 * Metodo predicativo che cerca un cliente locale.
	 * @param cl è il cliente da cercare.
	 * @return l'indice della posizione nella lista clienti, -1 altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista dei clienti è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se cl è null.
	 * (pre-condizione: cl != null && clienti.size()>0).
	 */
	public int cercaCliente(Clienti cl) throws EmptyListException,ArgomentoNonValidoException{
		if(cl == null)
			throw new ArgomentoNonValidoException("Reparto Amministrativo-Clienti: inerire parametri validi!");
		if(clienti.isEmpty())
			throw new EmptyListException("Lista clienti vuota. Ricerca annullata!");
		
		for(int i=0;i<clienti.size();i++)
			if(clienti.get(i).equals(cl))
				return i;
		return -1;
	}
	/**
	 * Metodo modificatore che permette il pagamento degli stipendi del personale.
	 */
	public void pagaStipendi() {
		for(Personale p: personale) {
			Conto cc = p.getConto();
			cc.deposita(p.getRetribuzione());
			p.setConto(cc);
		}
	}
	@Override
	/**
	 * Metodo che clona un reparto amministrativo restituendone il clone.
	 */
	public RepartoAmministrativo clone() {
		try {
			RepartoAmministrativo cloned = (RepartoAmministrativo) super.clone();
			cloned.personale = (ArrayList<Personale>) personale.clone();
			cloned.fornitori = (ArrayList<Fornitori>) fornitori.clone();
			cloned.clienti = (ArrayList<Clienti>) clienti.clone();
			cloned.enti = (ArrayList<EntiLocali>) enti.clone();
			
			return cloned;	
		}
		catch(CloneNotSupportedException ce) {
			return null;
		}
	}
	/**
	 * Variabili d'istanza.
	 */
	private ArrayList<Fornitori> fornitori;
	private ArrayList<EntiLocali> enti;
	private ArrayList<Clienti> clienti;
	private ArrayList<Personale> personale;
}