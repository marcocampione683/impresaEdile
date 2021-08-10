package principale;

import java.io.Serializable;

import eccezioni.ArgomentoNonValidoException;

/**
 * Questa classe esprime il concetto di impresa edile composta da due reparti: amministrativo e operativo.
 * @author marcocampione
 *
 */
public class ImpresaEdile implements Serializable{
	/**
	 * Metodo costruttore che istanzia un oggetto ImpresaEdile.
	 * @param name è il nome dell'impresa.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se name è null.
	 * (pre-condizione: name != null).
	 */
	public ImpresaEdile(String name)throws ArgomentoNonValidoException {
		if(name == null)
			throw new ArgomentoNonValidoException("ImpresaEdile: Inserire parametri validi!");
		
		nome = name;
		amministrativo = new RepartoAmministrativo();
		operativo = new RepartoOperativo();
	}
	/**
	 * Metodo d'accesso che legge il nome dell'impresa.
	 * @return il nome dell'impresa.
	 */
	public String getNome(){
		return nome;
	}
	/**
	 * Metodo d'accesso che restituisce il reparto amministrativo dell'impresa.
	 * @return il reparto amministrativo
	 */
	public RepartoAmministrativo getAmministrativo() {
		return amministrativo.clone();
	}
	/**
	 * Metodo d'accesso che restitisce il reparto operativo dell'impresa.
	 * @return il reparto operativo.
	 */
	public RepartoOperativo getOperativo() {
		return operativo.clone();
	}
	/**
	 * Metodo modificatore che setta il nome dell'impresa con uno nuovo.
	 * @param newNome è il nuovo nome.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se newNome è null.
	 * (pre-condizione: newNome != null).
	 */
	public void setNome(String newNome) throws ArgomentoNonValidoException {
		if(newNome == null)
			throw new ArgomentoNonValidoException("ImpresaEdile: Inserisci parametri validi!");
		
		nome = newNome;
	}
	/**
	 * Metodo modificatore che setta il reparto amministrativo con uno nuovo.
	 * @param repAmm è il nuovo reparto amministrativo.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se repAmm è null.
	 * (pre-condizione: repAmm != null).
	 */
	public void setAmministrativo(RepartoAmministrativo repAmm) throws ArgomentoNonValidoException {
		if(repAmm == null)
			throw new ArgomentoNonValidoException("ImpresaEdile: Inserisci parametri validi!");
		
		amministrativo = repAmm.clone();
	}
	/**
	 * Metodo modificatore che setta il reparto operativo con uno nuovo.
	 * @param repOp è il nuovo reparto operativo.
	 * @throws ArgomentoNonValidoException è l'eccezione che il metodo può lanciare se repOp è null.
	 * (pre-condizione: repOp != null).
	 */
	public void setOperativo(RepartoOperativo repOp) throws ArgomentoNonValidoException {
		if(repOp == null)
			throw new ArgomentoNonValidoException("ImpresaEdile: Inserisci parametri validi!");
		
		operativo = repOp.clone();
	}
	/**
	 * Variabili d'istanza.
	 */
	private RepartoOperativo operativo;
	private RepartoAmministrativo amministrativo;
	private String nome;
}