package principale;

import java.util.ArrayList;
import interfacce.CheckElement;

/**
 * Questa classe esprime il concetto di Report cioè il resoconto di una generica richiesta.
 */
public class Report<T>{
	/**
	 * Metodo costruttore che istanzia un oggetto Report.
	 */
	public Report() {}
	/**
	 * Metodo che genera un report.
	 * @param lista è la lista di elementi di un generico tipo.
	 * @param tester è un oggetto di un generico tipo che ha implementato l'interfaccia ChechElement che effettua il test su ogni elemento della lista.
	 * @return una lista di elementi di un generico tipo che hanno superato il test e che costituirà il report richiesto.
	 */
	public ArrayList<T> getReport(ArrayList<T> lista, CheckElement<T> tester) {
		ArrayList<T> selection = new ArrayList<T>();
		for(T t: lista) {
			if(tester.testElement(t))
				selection.add(t);
		}
		return selection;
	}
}