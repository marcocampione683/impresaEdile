package popolamento;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import amministrativo.Clienti;
import amministrativo.Conto;
import amministrativo.Dirigente;
import amministrativo.EntiLocali;
import amministrativo.Fornitori;
import amministrativo.Impiegato;
import amministrativo.Operaio;
import amministrativo.Quadro;
import eccezioni.ArgomentoNonValidoException;
import eccezioni.FailureRetribuzioneException;
import eccezioni.PersonaleImpegnatoException;
import eccezioni.ResponsabileNotDirigenteException;
import eccezioni.ResponsabileNotQuadroException;
import eccezioni.SaldoNegativeException;
import eccezioni.ValueNegativeException;
import operativo.Cantiere;
import operativo.Squadra;
import principale.ImpresaEdile;
import principale.RepartoAmministrativo;
import principale.RepartoOperativo;

public class ImpresaEdileFile{
	public static void main(String[] args) throws IOException {
		//try/catch per catturare tutte le eventuali eccezioni lanciate
		try {
				//Dichiaro e inizializzo variabili
				ImpresaEdile impresaEdile = new ImpresaEdile("Impresa Edile Marco s.a.s");
				
				File fileImpresa = new File("impresa_edile.dat");
				
				RepartoAmministrativo amministrativo = impresaEdile.getAmministrativo();
				RepartoOperativo operativo = impresaEdile.getOperativo();
				
				//CREO 10 OPERAI
				Operaio o1,o2,o3,o4,o5,o6,o7,o8,o9,o10;
				
				o1 = new Operaio("Giorgio", "Mastelloni", 800, new Conto(), "Piastrellista");
				o2 = new Operaio("Simone", "Capozzoli", 800, new Conto(), "Muratore");
				o3 = new Operaio("Francesco", "Plizzari", 800, new Conto(), "Stuccatore");
				o4 = new Operaio("Carlo", "Gommini", 900, new Conto(), "Muratore");
				o5 = new Operaio("Saverio", "Barra", 1000, new Conto(), "Piastrellista");
				o6 = new Operaio("Antonio", "Mancuso", 800, new Conto(), "Muratore");
				o7 = new Operaio("Angelo", "Carraro", 800, new Conto(), "Pittore");
				o8 = new Operaio("Marco", "D'Angelo", 900, new Conto(), "Muratore");
				o9 = new Operaio("Giorgio", "Meloni", 800, new Conto(), "Idraulico");
				o10 = new Operaio("Matteo", "Orilia", 800, new Conto(), "Piastrellista");
				
				//CREO 4 QUADRI
				Quadro q1,q2,q3,q4;
				
				q1 = new Quadro("Valerio", "Martufello", 1720, new Conto(), "Capo muratore","secondo");
				q2 = new Quadro("Ugo", "Spera", 1800, new Conto(), "Capo squadra","secondo");
				q3 = new Quadro("Mirko", "Vorgade", 1500, new Conto(), "Capo costruttore","primo");
				q4 = new Quadro("Antonio", "Trezza", 1500, new Conto(), "Capo sondatore","primo");
				
				//CREO 4 IMPIEGATI
				Impiegato i1,i2,i3,i4;
				
				i1 = new Impiegato("GianVito", "Moliterno", 1300, new Conto(), "Geometra");
				i2 = new Impiegato("Stefano", "Antonelli", 1200, new Conto(), "Logistica");
				i3 = new Impiegato("Federica", "Angrisani", 1250, new Conto(), "Commerciale");
				i4 = new Impiegato("Alessia", "Remo", 1250, new Conto(), "Commerciale");
				
				//CREO 4 DIRIGENTI
				Dirigente d1,d2,d3,d4;
				
				d1 = new Dirigente("Claudia", "Basso", 2400, new Conto(), "Conduttore di lavori edili");
				d2 = new Dirigente("Stefania", "De Cristoforo", 3000, new Conto(), "Impresario costruttore");
				d3 = new Dirigente("Aldo", "Rampulla", 2800, new Conto(), "Ingegnere civile");
				d4 = new Dirigente("Alberto", "Loia", 3000, new Conto(), "impresario costruttore");
				
				//AGGIUNGI PERSONALE DI TUTTE LE FASCE ALL'IMPRESA
				
				//10 Operai
				amministrativo.assumiPersonale(o1);
				amministrativo.assumiPersonale(o2);
				amministrativo.assumiPersonale(o3);
				amministrativo.assumiPersonale(o4);
				amministrativo.assumiPersonale(o5);
				amministrativo.assumiPersonale(o6);
				amministrativo.assumiPersonale(o7);
				amministrativo.assumiPersonale(o8);
				amministrativo.assumiPersonale(o9);
				amministrativo.assumiPersonale(o10);

				//4 Quadri
				amministrativo.assumiPersonale(q1);
				amministrativo.assumiPersonale(q2);
				amministrativo.assumiPersonale(q3);
				amministrativo.assumiPersonale(q4);
				
				//4 Impiegati
				amministrativo.assumiPersonale(i1);
				amministrativo.assumiPersonale(i2);
				amministrativo.assumiPersonale(i3);
				amministrativo.assumiPersonale(i4);
				
				//4 Dirigenti
				amministrativo.assumiPersonale(d1);
				amministrativo.assumiPersonale(d2);
				amministrativo.assumiPersonale(d3);
				amministrativo.assumiPersonale(d4);
				
				//RAPPORTI ESTERNI
				
				//Aggiungi fornitori
				amministrativo.aggiungiFornitore(new Fornitori("EdilMattonelle & co. s.a.s", "Fornitura mattonelle"));
				amministrativo.aggiungiFornitore(new Fornitori("EdilCostruzioni & co. s.a.s", "Materiali da costruzione"));
				amministrativo.aggiungiFornitore(new Fornitori("EdilCantiere & co. s.a.s", "Cantiere e macchine"));
				amministrativo.aggiungiFornitore(new Fornitori("Tutto Cantiere s.a.s", "Cantiere e macchine"));
				amministrativo.aggiungiFornitore(new Fornitori("Servizi Cantiere s.a.s", "Cantiere e macchine"));
				amministrativo.aggiungiFornitore(new Fornitori("EdilPorte & co. s.a.s", "Porte e finestre"));
				amministrativo.aggiungiFornitore(new Fornitori("EdilImpianti & co. s.a.s", "Impianti elettrici"));
				
				//Aggiungi enti
				amministrativo.aggiungiEnte(new EntiLocali("Comune di Salerno", "Campania", "Via Salerno,15", "089 765412"));
				amministrativo.aggiungiEnte(new EntiLocali("Comune di Napoli", "Campania", "Via Napoli,15", "089 765412"));
				amministrativo.aggiungiEnte(new EntiLocali("Comune di Avellino", "Campania", "Via Avellino,15", "089 765412"));
				amministrativo.aggiungiEnte(new EntiLocali("Comune di Cava de' Tirreni", "Campania", "Via Cava,15", "089 765412"));
				amministrativo.aggiungiEnte(new EntiLocali("Comune di Benevento", "Campania", "Via Benevento,15", "089 765412"));
				
				//Aggiungi Clienti
				amministrativo.aggiungiCliente(new Clienti("Luca", "Buono", "Via magnolia,23", "345192735"));
				amministrativo.aggiungiCliente(new Clienti("Sergio", "Anselmi", "Via Tre case,3", "345192735"));
				amministrativo.aggiungiCliente(new Clienti("Roberto", "Salemi", "Via Degli Angeli,13", "345192735"));
				amministrativo.aggiungiCliente(new Clienti("Simone", "Rosso", "Via Ugo Foscolo,43", "345192735"));
				amministrativo.aggiungiCliente(new Clienti("Ettore", "Paglia", "Via Roccaforte,8", "345192735"));
		
		
				//GESTIONE DEL REPARTO OPERATIVO
				
				//Aggiungi cantiere
				Cantiere c = new Cantiere(650000,amministrativo.getPersonale().get(18),new Squadra((Quadro) amministrativo.getPersonale().get(13)));
				operativo.aggiungiCantiere(c);
				//aggiuno operai alla squadra di cantiere
				c.getSquadra().AggiungiOperaio((Operaio) amministrativo.getPersonale().get(0));
				c.getSquadra().AggiungiOperaio((Operaio) amministrativo.getPersonale().get(1));
				c.getSquadra().AggiungiOperaio((Operaio) amministrativo.getPersonale().get(2));
				

				//Aggiungo cantiere
				Cantiere c2 = new Cantiere(350000,amministrativo.getPersonale().get(10),new Squadra((Quadro) amministrativo.getPersonale().get(11)));
				operativo.aggiungiCantiere(c2);
				//aggiungo operai alla squadra di cantiere
				c2.getSquadra().AggiungiOperaio((Operaio) amministrativo.getPersonale().get(3));
				c2.getSquadra().AggiungiOperaio((Operaio) amministrativo.getPersonale().get(4));
				c2.getSquadra().AggiungiOperaio((Operaio) amministrativo.getPersonale().get(5));
				
				//SETTO I DUE REPARTI DELL IMPRESA CON I DUE REPARTI PRECEDENTEMENTE CREATI E POPOLATI
				impresaEdile.setAmministrativo(amministrativo);
				impresaEdile.setOperativo(operativo);
				
				//SCRIVO SU FILE "impresa edile.dat" TUTTE LE INFO DELL'IMPRESA
				ObjectOutputStream scriviSuFile = new ObjectOutputStream(new FileOutputStream(fileImpresa));
				scriviSuFile.writeObject(impresaEdile);
				scriviSuFile.close();
		}
		catch(FailureRetribuzioneException fe) {
			System.out.println(fe.getMessage());
		}
		catch(SaldoNegativeException se) {
			System.out.println(se.getMessage());
		}
		catch(ArgomentoNonValidoException ne) {
			System.out.println(ne.getMessage());
		}
		catch(PersonaleImpegnatoException pie) {
			System.out.println(pie.getMessage());
		}
		catch(ClassCastException cle) {
			System.out.println("Errore cast!");
		}
		catch(ValueNegativeException ve) {
			System.out.println(ve.getMessage());
		}
		catch(ResponsabileNotDirigenteException rnde) {
			System.out.println(rnde.getMessage());
		}
		catch(ResponsabileNotQuadroException rnqe) {
			System.out.println(rnqe.getMessage());
		}
	}
}