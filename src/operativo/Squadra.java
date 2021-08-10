package operativo;

import java.io.Serializable;
import java.util.ArrayList;
import amministrativo.Quadro;
import eccezioni.ArgomentoNonValidoException;
import eccezioni.EmptyListException;
import eccezioni.PersonaleImpegnatoException;
import amministrativo.Operaio;
/**
 * Questa classe esprime il concetto di squadra. Una squadra è composta da un caposquadra che è un quadro e un 
 * gruppo di operai. Tra le varie funzionalità è possibile leggere le informazioni sulla squadra, aggiungere, 
 * rimuovere e cercare gli operai che la compongono.
 * @author marcocampione
 *
 */
public class Squadra implements Cloneable, Serializable{
	/**
	 * Metodo costruttore che istanzia un oggetto di tipo Squadra. Il caposquadra una volta assegnatagli la squadra risulta essere impegnato.
	 * @param capo è il caposquadra.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se capo è null.
	 * (pre-condizione: capo != null).
	 */
	public Squadra(Quadro capo) throws ArgomentoNonValidoException{
		if(capo == null)
			throw new ArgomentoNonValidoException("Squadra: Inserire parametri validi!");
		capo.setImpegnato(true);
		capoSquadra = capo.clone();
		listaOperai = new ArrayList<Operaio>();
	}
	/**
	 * Metodo d'accesso che legge il caposquadra.
	 * @return il caposquadra.
	 */
	public Quadro getCapoSquadra() {
		return capoSquadra.clone();
	}
	/**
	 * Metodo d'accesso che legge una lista di operai.
	 * @return la lista di operai che compongono la squadra.
	 */
	public ArrayList<Operaio> getOperai(){
		return (ArrayList<Operaio>) listaOperai.clone();
	}
	/**
	 * Metodo modificatore che setta il capo squadra con uno nuovo.
	 * @param newCapo è il nuovo capo squadra.
	 * @throws ArgomentoNonValidoException è l'eccezione che può lanciare il metodo se newCapo è null.
	 * (pre-condizione: newCapo != null).
	 */
	public void setCapoSquadra(Quadro newCapo) throws ArgomentoNonValidoException {
		if(newCapo == null)
			throw new ArgomentoNonValidoException("Squadra-caposquadra: Inserisci parametri validi!");
		
		capoSquadra.setImpegnato(false);
		
		capoSquadra = newCapo.clone();
		capoSquadra.setImpegnato(true);
	}
	/**
	 * Metodo modificatore che setta il gruppo di operai con un nuovo gruppo.
	 * @param newOperai è il nuovo gruppo di operai.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newOperai è null.
	 * (pre-condizione: newOperai != null).
	 */
	public void setOperai(ArrayList<Operaio> newOperai) throws ArgomentoNonValidoException {
		if(newOperai == null)
			throw new ArgomentoNonValidoException("Squadra-operai: Inserisci parametri validi!");
		
		for(Operaio o: listaOperai)
			o.setImpegnato(false);
		
		listaOperai = (ArrayList<Operaio>) newOperai.clone();
		for(Operaio o: listaOperai)
			o.setImpegnato(true);
	}
	/**
	 * Metodo modificatore che aggiunge un operaio alla squadra.
	 * @param op è l'operaio che verrà aggiunto alla squadra.
	 * @throws PersonaleImpegnatoException è l'eccezione che il metodo può lanciare se l'operaio è già impegnato con un altra squadra.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se op è null.
	 * (pre-condizione: op != null && !op.isImpegnato()).
	 */
	public void AggiungiOperaio(Operaio op) throws PersonaleImpegnatoException, ArgomentoNonValidoException {
		if(op == null)
			throw new ArgomentoNonValidoException("Squadra-Operaio: Inserire parametri validi!");
		
		if(!op.isImpegnato()) {
			op.setImpegnato(true);
			listaOperai.add(op.clone());
		}
		else
			throw new PersonaleImpegnatoException("L'operaio risulta già impegnato con una squadra!");
	}
	/**
	 * Metodo modificatore che rimuove un operaio dalla squadra.
	 * @param op è l'operaio che si vuole rimuovere.
	 * @return true se è stato eliminato dalla lista, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista degli operai è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se op è null.
	 * (pre-condizione: op != null && listaOperai.size() > 0).
	 */
	public boolean removeOperaio(Operaio op) throws EmptyListException, ArgomentoNonValidoException  {
		if(op == null)
			throw new ArgomentoNonValidoException("Squadra-Operaio: Inserire parametri validi!");
		if(listaOperai.isEmpty())
			throw new EmptyListException("Lista operai vuota!");
		
		for(Operaio o: listaOperai)
			if(o.equals(op)) {
				op.setImpegnato(false);
				return listaOperai.remove(o);
			}
		return false;
			
	}
	/**
	 * Metodo predicativo che controlla se un operaio è in squadra.
	 * @param op è l'operaio da controllare in lista.
	 * @return true se è presente, false altrimenti.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se op è null.
	 * (pre-condizione: op != null && listaOperai.size() > 0).
	 */
	public boolean isOperaioInSquadra(Operaio op) throws EmptyListException, ArgomentoNonValidoException {
		if(op == null)
			throw new ArgomentoNonValidoException("Squadra-Operaio: Inserire parametri validi!");
		if(listaOperai.isEmpty())
			throw new EmptyListException("Lista operai vuota!");
		
		for(Operaio o: listaOperai)
			if(o.equals(op))
				return true;
		return false;
	}
	/**
	 * Metodo che cerca un operaio in squadra.
	 * @param op è l'operaio da cercare in lista.
	 * @return l'indice in cui è posizionato in lista, altrimenti -1.
	 * @throws EmptyListException è l'eccezione che il metodo può lanciare se la lista degli operai è vuota.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se op è null.
	 * (pre-condizione: op != null && listaOperai.size() > 0).
	 */
	public int cercaOperaioInSquadra(Operaio op) throws EmptyListException, ArgomentoNonValidoException {
		if(op == null)
			throw new ArgomentoNonValidoException("Squadra-Operaio: Inserire parametri validi!");
		if(listaOperai.isEmpty())
			throw new EmptyListException("Lista operai vuota!");
		
		for(int i=0;i<listaOperai.size();i++)
			if(listaOperai.get(i).equals(op))
				return i;
		return -1;
	}
	@Override
	/**
	 * Metodo che restituisce la stringa contenente le informazioni generali della squadra.
	 */
	public String toString() {
		return getClass().getName() + "[capo squadra = "+capoSquadra+", gruppo operei = "+listaOperai.toString()+"]";
	}
	@Override
	/**
	 * Metodo predicativo che confronta due oggetti Squadra in ogni istanza.
	 * Restiuisce true se sono uguali, false altrimenti. 
	 */
	public boolean equals(Object other) {
		if(other == null || getClass() != other.getClass())
			return false;
		Squadra altraSquadra = (Squadra) other;
		return capoSquadra.equals(altraSquadra.capoSquadra);
	}
	@Override
	/**
	 * Metodo che clona una squadra e ne restituisce il clone.
	 */
	public Squadra clone() {
		try {
			Squadra cloned = (Squadra) super.clone();
			cloned.capoSquadra = capoSquadra.clone();
			
			return cloned;
		}
		catch(CloneNotSupportedException c) {
			return null;
		}
	}
	/**
	 * Variabili d'istanza.
	 */
	private Quadro capoSquadra;
	private ArrayList<Operaio> listaOperai;
	
}