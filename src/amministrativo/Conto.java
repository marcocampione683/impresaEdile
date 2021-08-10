package amministrativo;

import java.io.Serializable;

import eccezioni.IllegalSumException;
import eccezioni.SaldoNegativeException;

/**
 * Questa classe esprime il concetto di conto corrente bancario su cui è possibile eseguire operazioni di
 * prelievo deposito e visualizzare il saldo.
 * @author marcocampione
 *
 */
public class Conto implements Cloneable, Serializable{
	/**
	 * Metodo Costruttore che istanzia un oggetto di tipo conto impostando il saldo a zero.
	 */
	public Conto() {
		saldo = 0;
	}
	/**
	 * Metodo costruttore che istanzia un oggetto conto impostando un saldo predefinito.
	 * @param saldoIniziale è il saldo di partenza.
	 * @throws SaldoNegativeException è l'eccezione che il metodo può lanciare se il saldo iniziale è negativo.
	 * (pre-condizione saldoIniziale >= 0).
	 */
	public Conto(double saldoIniziale) throws SaldoNegativeException {
		if(saldoIniziale < 0)
			throw new SaldoNegativeException("Saldo negativo!");
		saldo = saldoIniziale;
	}
	/**
	 * Metodo modificatore che deposita sul conto una determinata somma.
	 * @param somma è la somma da depositare.
	 * @throws IllegalSumException è l'eccezione che il metodo può lanciare se somma è minore o uguale a zero.
	 * (pre-condizione somma > 0).
	 * (post-condizione saldo > 0).
	 */
	public void deposita(double somma) throws IllegalSumException {
		if(somma <= 0)
			throw new IllegalSumException("Inserire una somma positiva da depositare!");
		double nuovoSaldo = saldo + somma;
		saldo = nuovoSaldo;
	}
	/**
	 * Metodo modificatore che preleva dal conto una determinata somma.
	 * @param somma è la somma da prelevare.
	 * @throws IllegalSumException è l'eccezione che il metodo può lanciare se somma è minore o uguale a zero o è maggiore del saldo.
	 * (pre-condizione somma > 0 && somma <= saldo).
	 * (post-condizione saldo >= 0).
	 */
	public void preleva(double somma) throws IllegalSumException {
		if(somma <= 0 || somma > saldo)
			throw new IllegalSumException("Inserire una somma corretta da prelevare!");
		double nuovoSaldo = saldo - somma;
		saldo = nuovoSaldo;
	}
	/**
	 * Metodo d'accesso che legge il saldo del conto.
	 * @return il saldo.
	 * (post-condizione saldo >= 0).
	 */
	public double getSaldo() {
		return saldo;
	}
	@Override
	/**
	 * Metodo che restituisce una stringa contenente le informazioni generali 
	 * del conto bancario.
	 */
	public String toString() {
		return getClass().getName() + "[saldo = "+saldo+"]";
	}
	@Override
	/**
	 * Metodo predicativo che confronta due oggetti conto in base al saldo.
	 * Se il saldo dei conti è ugauale restituisce true, altrimenti false.
	 */
	public boolean equals(Object other) {
		if(other == null || getClass() != other.getClass())
			return false;
		Conto altroConto = (Conto) other;
		return Math.abs(saldo - altroConto.saldo) <= EPSILON;
	}
	@Override
	/**
	 * Metodo d'accesso che clona un oggetto conto e lo restituisce.
	 */
	public Conto clone() {
		try {
			return (Conto) super.clone();
		}
		catch(CloneNotSupportedException c) {
			return null;
		}
	}
	/**
	 * Variabili d'istanza.
	 */
	private double saldo;
	
	private static final double EPSILON = Math.pow(10, -14);
}