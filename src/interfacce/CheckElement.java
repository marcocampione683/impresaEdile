package interfacce;

/**
 * Questa è un interfaccia che ci permette di controllare la veridicità di determinate proprietà di generici oggetti che la implementano tramite test su di essi.
 * @author marcocampione
 *
 * @param <T> è il tipo.
 */
public interface CheckElement<T>{
	/**
	 * Metodo predicativo che effettua il test sugli oggetti.
	 * @param t è l'oggetto generico.
	 * @return true se supera il test, false altrimenti.
	 */
	boolean testElement(T t);
}