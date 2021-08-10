package principale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import amministrativo.Clienti;
import amministrativo.Conto;
import amministrativo.Dirigente;
import amministrativo.EntiLocali;
import amministrativo.Fornitori;
import amministrativo.Impiegato;
import amministrativo.Operaio;
import amministrativo.Personale;
import amministrativo.Quadro;
import eccezioni.ArgomentoNonValidoException;
import eccezioni.EmptyListException;
import eccezioni.FailureRetribuzioneException;
import eccezioni.NotPersonaleException;
import eccezioni.PersonaleImpegnatoException;
import eccezioni.ResponsabileNotDirigenteException;
import eccezioni.ResponsabileNotQuadroException;
import eccezioni.SaldoNegativeException;
import eccezioni.ValueNegativeException;
import operativo.Cantiere;
import operativo.Squadra;

public class ImpresaEdileTester2{
	public static void main(String[] args) throws NotPersonaleException{
		//Dichiaro e inizializzo variabii
		ImpresaEdile impresa;
		
		RepartoAmministrativo amministrativo;
		RepartoOperativo operativo;
		
		Operaio o1=null,o2=null,o3=null,o4=null,o5=null,o6=null,o7=null,o8=null,o9=null,o10=null;
		Quadro q1=null,q2=null,q3=null,q4=null,q5=null,q6=null,q7=null;
		Impiegato i1=null,i2=null,i3=null,i4=null;
		Dirigente d1=null,d2=null,d3=null,d4=null;
		Fornitori f1=null, f2=null, f3=null,f4=null, f5=null, f6=null, f7=null;
		EntiLocali e1=null, e2=null, e3=null, e4=null, e5=null;
		Clienti cl1=null, cl2=null, cl3=null, cl4=null, cl5=null;
		
		//Clausola try/catch per catturare le eccezioni
		try {
			impresa = new ImpresaEdile("Marco & co. s.p.a");
			amministrativo = impresa.getAmministrativo();
			operativo = impresa.getOperativo();
			
			//REPARTO AMMINISTRATIVO
			System.out.println("REPARTO AMMINISTRATIVO\n");
			
			//Creo il personale dell impresa. Nell'ordine 10 operai, 7 Quadri, 4 impiegati e 4 dirigenti.
			//CREO 10 OPERAI
			o1 = new Operaio("Giorgio", "Mastelloni", 800, new Conto(), "Piastrellista");
			o2 = new Operaio("Simone", "Capozzoli", 800, new Conto(), "Muratore");
			o3 = new Operaio("Francesco", "Plizzari", 800, new Conto(), "Stuccatore");
			o4 = new Operaio("Carlo", "Gommini", 900, new Conto(), "Muratore");
			o5 = new Operaio("Saverio", "Barra", 1000, new Conto(), "Piastrellista");
			o6 = new Operaio("Antonio", "Mancuso", 800, new Conto(), "Muratore");
			o7 = new Operaio("Angelo", "Carraro", 850, new Conto(), "Pittore");
			o8 = new Operaio("Marco", "D'Angelo", 900, new Conto(), "Muratore");
			o9 = new Operaio("Giorgio", "Meloni", 950, new Conto(), "Idraulico");
			o10 = new Operaio("Matteo", "Orilia", 800, new Conto(), "Piastrellista");
			
			//CREO 7 QUADRI
			q1 = new Quadro("Valerio", "Martufello", 1720, new Conto(), "Capo muratore","secondo");
			q2 = new Quadro("Ugo", "Spera", 1800, new Conto(), "Capo squadra","secondo");
			q3 = new Quadro("Mirko", "Vorgade", 1500, new Conto(), "Capo costruttore","primo");
			q4 = new Quadro("Antonio", "Trezza", 1600, new Conto(), "Capo sondatore","primo");
			q5 = new Quadro("Valerio", "Luongo", 1600, new Conto(), "Capo cantiere", "primo");
			q6 = new Quadro("Christian", "Longo", 1500, new Conto(), "Capo operaio", "terzo");
			q7 = new Quadro("Flavio", "Bani", 1500, new Conto(), "Capo muratore", "secondo");
			
			//CREO 4 IMPIEGATI
			i1 = new Impiegato("GianVito", "Moliterno", 1300, new Conto(), "Geometra");
			i2 = new Impiegato("Stefano", "Antonelli", 1200, new Conto(), "Logistica");
			i3 = new Impiegato("Federica", "Angrisani", 1250, new Conto(), "Commerciale");
			i4 = new Impiegato("Alessia", "Remo", 1250, new Conto(), "Commerciale");
			
			//CREO 4 DIRIGENTI
			d1 = new Dirigente("Claudia", "Basso", 2400, new Conto(), "Conduttore di lavori edili");
			d2 = new Dirigente("Stefania", "De Cristoforo", 3000, new Conto(), "Impresario costruttore");
			d3 = new Dirigente("Aldo", "Rampulla", 2800, new Conto(), "Ingegnere civile");
			d4 = new Dirigente("Alberto", "Loia", 3000, new Conto(), "impresario costruttore");
			
			System.out.println("Personale Creato.");
			
			//Creo i fornitori dell'impresa.
			f1 = new Fornitori("EdilMattonelle & co. s.a.s", "Fornitura mattonelle");
			f2 = new Fornitori("EdilCostruzioni & co. s.a.s", "Materiali da costruzione");
			f3 = new Fornitori("EdilCantiere & co. s.a.s", "Cantiere e macchine");
			f4 = new Fornitori("Tutto Cantiere s.a.s", "Cantiere e macchine");
			f5 = new Fornitori("Servizi Cantiere s.a.s", "Cantiere e macchine");
			f6 = new Fornitori("EdilPorte & co. s.a.s", "Porte e finestre");
			f7 = new Fornitori("EdilImpianti & co. s.a.s", "Impianti elettrici");
			
			System.out.println("Fornitori creati.");
			
			//Creo gli enti locali.
			e1 = new EntiLocali("Comune di Salerno", "Salerno", "Via Salerno,15", "089 765412");
			e2 = new EntiLocali("Comune di Napoli", "Napoli", "Via Napoli,15", "089 765412");
			e3 = new EntiLocali("Comune di Avellino", "Avellino", "Via Avellino,15", "089 765412");
			e4 = new EntiLocali("Comune di Cava de' Tirreni", "Cava de' Tirreni", "Via Cava,15", "089 765412");
			e5 = new EntiLocali("Comune di Benevento", "Benevento", "Via Benevento,15", "089 765412");
			
			System.out.println("Enti locali creati.");
			
			//Creo i clienti.
			cl1 = new Clienti("Luca", "Buono", "Via magnolia,23", "345192735");
			cl2 = new Clienti("Sergio", "Anselmi", "Via Tre case,3", "345192735");
			cl3 = new Clienti("Roberto", "Salemi", "Via Degli Angeli,13", "345192735");
			cl4 = new Clienti("Simone", "Rosso", "Via Ugo Foscolo,43", "345192735");
			cl5 = new Clienti("Ettore", "Paglia", "Via Roccaforte,8", "345192735");
			
			System.out.println("Clienti creati.");
			
			//Aggiungo il personale all'impresa.
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
	
			//7 Quadri
			amministrativo.assumiPersonale(q1);
			amministrativo.assumiPersonale(q2);
			amministrativo.assumiPersonale(q3);
			amministrativo.assumiPersonale(q4);
			amministrativo.assumiPersonale(q5);
			amministrativo.assumiPersonale(q6);
			amministrativo.assumiPersonale(q7);
			
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
			
			//Stampo la lista del personale.
			System.out.println("\nPersonale aggiunto all'impresa.\n"
							 + "Lista del Personale:");
			for(Personale p: amministrativo.getPersonale())
				System.out.println(p);
			
			//Aggiungo i fornitori.
			amministrativo.aggiungiFornitore(f1);
			amministrativo.aggiungiFornitore(f2);
			amministrativo.aggiungiFornitore(f3);
			amministrativo.aggiungiFornitore(f4);
			amministrativo.aggiungiFornitore(f5);
			amministrativo.aggiungiFornitore(f6);
			amministrativo.aggiungiFornitore(f7);
			
			//Stampo la lista dei fornitori.
			System.out.println("\nFornitori aggiunti all'impresa.\n"
							 + "Lista dei fornitori:");
			for(Fornitori forn: amministrativo.getFornitori())
				System.out.println(forn);
			
			//Aggiungo gli enti locali
			amministrativo.aggiungiEnte(e1);
			amministrativo.aggiungiEnte(e2);
			amministrativo.aggiungiEnte(e3);
			amministrativo.aggiungiEnte(e4);
			amministrativo.aggiungiEnte(e5);
			
			System.out.println("\nEnti locali aggiunti all'impresa.\n"
							 + "Lista degli enti:");
			for(EntiLocali enti: amministrativo.getEntiLocali())
				System.out.println(enti);
			
			//Aggiungo i clienti
			amministrativo.aggiungiCliente(cl1);
			amministrativo.aggiungiCliente(cl2);
			amministrativo.aggiungiCliente(cl3);
			amministrativo.aggiungiCliente(cl4);
			amministrativo.aggiungiCliente(cl5);
			
			System.out.println("\nClienti aggiunti all'impresa.\n"
							 + "Lista dei clienti:");
			for(Clienti clienti: amministrativo.getClienti())
				System.out.println(clienti);
			
			
			//Pago stipendio dipendenti
			amministrativo.pagaStipendi();
			System.out.println("\nPagamento stipendi effettuato.");
			
			System.out.println("Saldo dipendenti aggiornato:");
			for(Personale p: amministrativo.getPersonale())
				System.out.println(p.getNome()+" "+p.getCognome()+" "+p.getConto().getSaldo());
			
			
			
			//REPARTO OPERATIVO
			System.out.println("\nREPARTO OPERATIVO\n");
			
			//Creazione dei cantieri. Ogni cantiere ha un valore, un responsabile e una squadra. Il responsabile viene chiesto in base al valore del cantiere.
			//Se valore>=500.000 ==> il responsabile è un dirigente, altrimenti è un quadro.
			//Ogni squadra è composta da un caposquadra, che è un quadro, e un gruppo di operai. La squadra deve essere composta da lavoratori non impegnati su altri cantieri.
			//Prelevo un Quadro dalla lista del personale e controllo se il quadro risulta essere impegnato o meno in altri cantieri. Se non è impegnato esso sarà il caposquadra.
			
			//Inserisco valore cantiere.
			int valore1 = 650000;
			
			//Prelevo un responsabile dalla lista del personale, in questo caso un dirigente. Il controllo sul responsabile lo faccio nel metodo costruttore di cantiere.
			//Controllo se il responsabile è un dipendente se mi restituisce un valore negativo significa che il dipendente cercato non è un dipendente dell'impresa
			//e lancio l'eccezione controllata NotPersonaleException.
			Dirigente responsabile1 = (Dirigente)amministrativo.getPersonale().get(21);
			if(amministrativo.cercaPersonale(responsabile1) == -1)
				throw new NotPersonaleException("Il responsabile non è un dipendente dell'impresa!");
			
			//Dichiaro le variabili che costituiscono il caposquadra e la squadra.
			Quadro capoSquadra1 = (Quadro)amministrativo.getPersonale().get(10); 
			if(amministrativo.cercaPersonale(capoSquadra1) == -1)
				throw new NotPersonaleException("Il caposquadra non è un dipendente dell'impresa!");
			
			Squadra squadra1;
			
			//controllo se non è impegnato. Se restituisce true allora non è impegnato e può essere il caposquadra di questa squadra.
			if(!capoSquadra1.isImpegnato()) {
				//Creo la squadra
				squadra1 = new Squadra(capoSquadra1);
			}
			//Se è impegnato lancio l'eccezione non controllata PersonaleImpegnatoException.
			else
				throw new PersonaleImpegnatoException("Il quadro risulta già impegnato!");
			
			//Aggiungo 3 operai alla squadra. In questo caso il controllo se gli operai sono impegnati viene fatta nel metodo.
			Operaio operaio1 = (Operaio)amministrativo.getPersonale().get(0);
			if(amministrativo.cercaPersonale(operaio1) == -1)
				throw new NotPersonaleException("L'operaio non è un dipendente dell'impresa!");
			
			Operaio operaio2 = (Operaio)amministrativo.getPersonale().get(1); 
			if(amministrativo.cercaPersonale(operaio2) == -1)
				throw new NotPersonaleException("L'operaio non è un dipendente dell'impresa!");
			
			Operaio operaio3 = (Operaio)amministrativo.getPersonale().get(2);
			if(amministrativo.cercaPersonale(operaio3) == -1)
				throw new NotPersonaleException("L'operaio non è un dipendente dell'impresa!");
			
			squadra1.AggiungiOperaio(operaio1);
			squadra1.AggiungiOperaio(operaio2);
			squadra1.AggiungiOperaio(operaio3);
			
			//Creo primo cantiere
			Cantiere cantiere1 = new Cantiere(valore1, responsabile1, squadra1);
			System.out.println("Cantiere creato.");
			
			//Aggiungo il cantiere all'impresa
			operativo.aggiungiCantiere(cantiere1);
			System.out.println("Cantiere aggiunto all'impresa.");
			
			
			//Inserisco valore secondo cantiere
			int valore2 = 350000;
			
			//Prelevo un responsabile dalla lista del personale, in questo caso un quadro. Il controllo sul responsabile lo faccio nel metodo costruttore di cantiere.
			Quadro responsabile2 = (Quadro)amministrativo.getPersonale().get(11);
			if(amministrativo.cercaPersonale(responsabile2) == -1)
				throw new NotPersonaleException("Il responsabile non è un dipendente dell'impresa!");
			
			//Dichiaro le variabili che costituiscono il caposquadra e la squadra.
			Quadro capoSquadra2 = (Quadro)amministrativo.getPersonale().get(12);
			if(amministrativo.cercaPersonale(capoSquadra2) == -1)
				throw new NotPersonaleException("Il caposquadra non è un dipendente dell'impresa!");
			
			Squadra squadra2;
			
			//controllo se non è impegnato. Se restituisce true allora non è impegnato e può essere il caposquadra di questa squadra.
			if(!capoSquadra2.isImpegnato()) {
				//Creo la squadra
				squadra2 = new Squadra(capoSquadra2);
			}
			//Se è impegnato lancio l'eccezione non controllata PersonaleImpegnatoException.
			else
				throw new PersonaleImpegnatoException("Il quadro risulta già impegnato!");
			
			//Aggiungo 3 operai alla squadra. In questo caso il controllo, se gli operai sono impegnati, viene fatta nel metodo.
			Operaio operaio4 = (Operaio)amministrativo.getPersonale().get(3);
			if(amministrativo.cercaPersonale(operaio4) == -1)
				throw new NotPersonaleException("L'operaio non è un dipendente dell'impresa!");
			
			Operaio operaio5 = (Operaio)amministrativo.getPersonale().get(4);
			if(amministrativo.cercaPersonale(operaio5) == -1)
				throw new NotPersonaleException("L'operaio non è un dipendente dell'impresa!");
			
			Operaio operaio6 = (Operaio)amministrativo.getPersonale().get(5);
			if(amministrativo.cercaPersonale(operaio6) == -1)
				throw new NotPersonaleException("L'operaio non è un dipendente dell'impresa!");
			
			squadra2.AggiungiOperaio(operaio4);
			squadra2.AggiungiOperaio(operaio5);
			squadra2.AggiungiOperaio(operaio6);
			
			//Creo cantiere
			Cantiere cantiere2 = new Cantiere(valore2, responsabile2, squadra2);
			System.out.println("\nCantiere creato.");
			
			//Aggiungo il secondo cantiere all'impresa
			operativo.aggiungiCantiere(cantiere2);
			System.out.println("Cantiere aggiunto all'impresa.");
			
			
			//Inserisco valore terzo cantiere
			int valore3 = 800000;
			
			//Prelevo un responsabile dalla lista del personale, in questo caso un dirigente. Il controllo sul responsabile lo faccio nel metodo costruttore di cantiere.
			Dirigente responsabile3 = (Dirigente)amministrativo.getPersonale().get(22);
			if(amministrativo.cercaPersonale(responsabile3) == -1)
				throw new NotPersonaleException("Il responsabile non è un dipendente dell'impresa!");
			
			//Dichiaro le variabili che costituiscono il caposquadra e la squadra.
			Quadro capoSquadra3 = (Quadro)amministrativo.getPersonale().get(13);
			if(amministrativo.cercaPersonale(capoSquadra3) == -1)
				throw new NotPersonaleException("Il caposquadra non è un dipendente dell'impresa!");
			
			Squadra squadra3;
			
			//controllo se non è impegnato. Se restituisce true allora non è impegnato e può essere il caposquadra di questa squadra.
			if(!capoSquadra3.isImpegnato()) {
				//Creo la squadra
				squadra3 = new Squadra(capoSquadra3);
			}
			//Se è impegnato lancio l'eccezione non controllata PersonaleImpegnatoException.
			else
				throw new PersonaleImpegnatoException("Il quadro risulta già impegnato!");
			
			//Aggiungo un operaio alla squadra. In questo caso il controllo, se gli operai sono impegnati, viene fatta nel metodo.
			Operaio operaio7 = (Operaio)amministrativo.getPersonale().get(6);
			if(amministrativo.cercaPersonale(operaio7) == -1)
				throw new NotPersonaleException("L'operaio non è un dipendente dell'impresa!");
			
			squadra3.AggiungiOperaio(operaio7);
			
			//Creo cantiere
			Cantiere cantiere3 = new Cantiere(valore3, responsabile3, squadra3);
			System.out.println("\nCantiere creato.");
			
			//Aggiungo il terzo cantiere all'impresa
			operativo.aggiungiCantiere(cantiere3);
			System.out.println("Cantiere aggiunto all'impresa.");
			
			
			//Controllo se all'impresa risultano presenti i tre cantieri creati.
			System.out.println("\nControllo cantieri in impresa.");
			if(operativo.isCantiere(cantiere1))
				System.out.println("Cantiere1 è un cantiere dell'impresa.");
			else
				System.out.println("Cantiere1 non è un cantiere dell'impresa.");
			
			if(operativo.isCantiere(cantiere2))
				System.out.println("Cantiere2 è un cantiere dell'impresa.");
			else
				System.out.println("Cantiere2 non è un cantiere dell'impresa.");
			
			if(operativo.isCantiere(cantiere3))
				System.out.println("Cantiere3 è un cantiere dell'impresa.");
			else
				System.out.println("Cantiere3 non è un cantiere dell'impresa.");
			
			//Lista cantieri
			System.out.println("\nLista cantieri in impresa:");
			for(Cantiere c: operativo.getCantieri())
				System.out.println(c);
			
			
			//REPORT
			System.out.println("\nREPORT\n");
			
			//Eseguire un report sui fornitori
			System.out.println("1.Report dei fornitori con servizio: Cantiere e macchine");
			
			Report<Fornitori> report = new Report<Fornitori>();
			Scanner input = new Scanner(System.in);
			
			ArrayList<Fornitori> repFor = report.getReport(amministrativo.getFornitori(), (f) -> f.getServizio().equals("Cantiere e macchine"));
			Collections.sort(repFor, (fo1,fo2) -> fo1.getNome().compareTo(fo2.getNome()));
			
			System.out.print("In che ordine vuoi visualizzare gli elementi(crescente/decrescente): ");
			while(true) {
				String ordine = input.next();
				if(ordine.equalsIgnoreCase("crescente")) {
					for(Fornitori f: repFor)
						System.out.println(f);
					break;
				}
				else if(ordine.equalsIgnoreCase("decrescente")) {
					Collections.reverse(repFor);
					for(Fornitori f: repFor)
						System.out.println(f);
					break;
				}
				else
					System.out.println("Parametro errato: Inserisci crescente/decrescente");
			}
			
			//Eseguire un report sui cantieri.
			System.out.println("\n2.Report su cantieri con valore compreso tra 300.000€ e 700.000€");
			
			Report<Cantiere> reportC = new Report<Cantiere>();
			
			ArrayList<Cantiere> repCant = reportC.getReport(operativo.getCantieri(), (ca) -> ca.getValore() >= 300000 && ca.getValore() <= 700000);
			Collections.sort(repCant, (c1,cc2) -> c1.getValore() - cc2.getValore());
			
			System.out.print("In che ordine vuoi visualizzare gli elementi(crescente/decrescente): ");
			while(true) {
				String ordineC = input.next();
				if(ordineC.equalsIgnoreCase("crescente")) {
					for(Cantiere can: repCant)
						System.out.println(can);
					break;
				}
				else if(ordineC.equalsIgnoreCase("decrescente")) {
					Collections.reverse(repCant);
					for(Cantiere can: repCant)
						System.out.println(can);
					break;
				}
				else
					System.out.println("Parametro errato: Inserisci crescente/decrescente");
			}
			
			//Eseguire un report sui lavoratori non impegnati.
			System.out.println("\n3.Report su lavoratori non impegnati in nessun cantiere");
			
			Report<Personale> repL = new Report<Personale>();
			
			ArrayList<Personale> repLav = repL.getReport(amministrativo.getPersonale(), (p) -> !p.isImpegnato());
			Collections.sort(repLav, (l1,l2) -> l1.getNome().compareTo(l2.getNome()));
			
			System.out.print("In che ordine vuoi visualizzare gli elementi(crescente/decrescente): ");
			while(true) {
				String ordineL = input.next();
				if(ordineL.equalsIgnoreCase("crescente")) {
					for(Personale p: repLav)
						System.out.println(p);
					break;
				}
				else if(ordineL.equalsIgnoreCase("decrescente")) {
					Collections.reverse(repLav);
					for(Personale p: repLav)
						System.out.println(p);
					break;
				}
				else
					System.out.println("Parametro errato: Inserisci crescente/decrescente");
			}
			
			//Elimino un dipendente.
			System.out.println("\nElimino il seguente dipendente\n"+amministrativo.getPersonale().get(8));
			if(amministrativo.licenziaPersonale(amministrativo.getPersonale().get(8))) {
				System.out.println("Dipendente rimosso.");
				System.out.println("\nLista personale aggiornata:");
				for(Personale p: amministrativo.getPersonale())
					System.out.println(p);
			}
			else
				System.out.println("Impossibile rimuovere dipendente.");
			
			//Elimino un fornitore
			System.out.println("\nElimino il seguente fornitore\n"+amministrativo.getFornitori().get(4));
			if(amministrativo.rimuoviFornitore(amministrativo.getFornitori().get(4))) {
				System.out.println("Fornitore rimosso.");
				System.out.println("\nLista fornitori aggiornata:");
				for(Fornitori f: amministrativo.getFornitori())
					System.out.println(f);
			}
			else
				System.out.println("Impossibile rimuovere fornitore.");
			
			//Elimino ente
			System.out.println("\nElimino il seguente Ente locale:\n"+amministrativo.getEntiLocali().get(2));
			if(amministrativo.rimuoviEnte(amministrativo.getEntiLocali().get(2))) {
				System.out.println("Ente rimosso.\n"
								 + "Lista enti locali aggiornata:");
				for(EntiLocali e: amministrativo.getEntiLocali())
					System.out.println(e);
			}
			else
				System.out.println("Impossibile rimuovere l'ente.");
			
			//Elimino cliente
			System.out.println("\nElimino il seguente cliente\n"+amministrativo.getClienti().get(1));
			if(amministrativo.rimuoviCliente(amministrativo.getClienti().get(1))) {
				System.out.println("Cliente rimosso.\n"
								 + "Lista clienti aggiornata:");
				for(Clienti cl: amministrativo.getClienti())
					System.out.println(cl);
			}
			else
				System.out.println("Impossibile rimuovere il cliente.");
			
			//Elimino cantiere
			System.out.println("\nElimino il seguente cantiere\n"+operativo.getCantieri().get(1));
			if(operativo.rimuoviCantiere(operativo.getCantieri().get(1))) {
				System.out.println("Cantiere rimosso.\n"
								 + "Lista cantieri aggiornata:");
				for(Cantiere c: operativo.getCantieri())
					System.out.println(c);
			}
			else
				System.out.println("Impossibile rimuovere il cantiere.");
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
		catch(NotPersonaleException npe) {
			System.out.println(npe.getMessage());
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
		catch(EmptyListException ee) {
			System.out.println(ee.getMessage());
		}
	}
}