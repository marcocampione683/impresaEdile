package interfacciagrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
import eccezioni.PersonaleImpegnatoException;
import eccezioni.ResponsabileNotDirigenteException;
import eccezioni.ResponsabileNotQuadroException;
import eccezioni.FailureRetribuzioneException;
import eccezioni.ValueNegativeException;
import operativo.Cantiere;
import operativo.Squadra;
import principale.ImpresaEdile;
import principale.RepartoAmministrativo;
import principale.RepartoOperativo;
import principale.Report;

public class ImpresaEdileFrame extends JFrame{
////////////////////////////////////FRAME PRINCIPALE CONTENENTE TUTTE LE COMPONENTI DELL'INTERFACCIA GRAFICA/////////////////////////////////////////////////////
	public ImpresaEdileFrame() {
		//Creazione della barra dei menù
		JMenuBar barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);
		//Aggiungiamo nella barra dei menù i menù principali
		barraMenu.add(creaMenuFile());
		barraMenu.add(creaMenuSeleziona());
		barraMenu.add(creaMenuVisualizza());
		//Creazione della area di testo con annesso scroll in cui sarà solo possibie visualizzare l'output e verrà posta al centro del frame principale.
		textArea = new JTextArea(AREA_ROWS,AREA_COLUMS);
		textArea.setEditable(false);
		scroll = new JScrollPane(textArea);
		add(scroll, BorderLayout.CENTER);
		//Aggiungiamo il pannello centrale che contiene tutti gli aspetti grafici delle funzionalità di ogni componente dell'impresa.
		//Il pannello viene posto nella parte sud del layout del frame.
		JPanel pannelloCentrale = pannelloDiControllo();
		add(pannelloCentrale, BorderLayout.SOUTH);
		//settaggio della grandezza del frame principale.
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
///////////////////////////////////////////////////////////////////BARRA DEI MENU/////////////////////////////////////////////////////////////////////////////	
														  /////COMPONENTI DELLA BARRA///////

///////PRIMO MENU'////////////////////
	
	//Menù File
	public JMenu creaMenuFile() {
		//Creazione del menù
		JMenu file = new JMenu("File");
		//Aggiungiamo al menù i sottomenù
		file.add(creaMenuCarica());
		file.add(creaMenuSalva());
		file.add(creaMenuEsci());
		
		return file;
	}
	//Sottomenù Carica(Menù File)
	public JMenuItem creaMenuCarica() {	
		//Creazione sottomenù Carica
		JMenuItem carica = new JMenuItem("Carica");
		//classe interna che crea il tipo ricevitore di eventi "CaricaListener" che creerà il ricevitore che sarà poi associato al menù Carica.
		class CaricaListener implements ActionListener{
			//Sovrascriviamo il metodo dell'interfaccia ActionListener
			public void actionPerformed(ActionEvent e) {
				//Creiamo un nuovo frame che comparirà quando selezioniamo Carica. Questo frame conterra una JTextField in cui andremo a scrivere il file
				//che vogliamo caricare. In sintesi il frame ha lo scopo di caricare il nostro file per poter leggere le informazioni in esso contenute.
				JFrame frameCarica = new JFrame();
				JLabel file = new JLabel("File");
				JTextField fileField = new JTextField(FIELD_WIDTH);
				//Il bottone carica è il sorgente di eventi che ci permetterà di caricare il file
				JButton bCarica = new JButton("Carica");
				//Creiamo il pannello in cui aggiungeremo le componenti del frame.
				JPanel pannelloCarica = new JPanel();
				pannelloCarica.setLayout(new GridLayout(1, 1));
				pannelloCarica.add(file);
				pannelloCarica.add(fileField);
				pannelloCarica.add(bCarica);
				frameCarica.add(pannelloCarica);
				frameCarica.setSize(200, 50);
				//Quando selezioneremo il menù carica rendiamo visibile il frame.
				frameCarica.setVisible(true);
				//Classe interna che crea il tipo di ricevitore di questo evento "Bcarica" che creerà il nuovo ricevitore che sarà poi associato al bottone Carica.
				class Bcarica implements ActionListener{
					//Sovrascriviamo il metodo dell'interfaccia ActionListener
					public void actionPerformed(ActionEvent e) {
						//Dichiaro un oggetto che ci permetta di leggere flussi di oggetti in input 
						ObjectInputStream fileInput;
						//catturo possibili eccezioni controllate
						try {
							//assegno alla variabile d'istanza il nome del file che voglio aprire.
							fileCorrente = fileField.getText();
							//Creo l'oggetto precedentemente dichiarato che mi permetterà di leggere gli oggetti 
							//presenti sul file di input passato come parametro
							fileInput = new ObjectInputStream(new FileInputStream(fileCorrente));
							//assegno ad impresa l'oggetto ImpresaEdile letto da file.
							impresa = (ImpresaEdile) fileInput.readObject();
							//una volta letto rendo invisibile il frame di carica file.
							frameCarica.setVisible(false);
							//assegno ad amministrativo il RepartoAmministrativo di impresa
							amministrativo = impresa.getAmministrativo();
							//assegno ad operativo il RepartoOperativo di impresa
							operativo = impresa.getOperativo();
							//output su area di testo un messaggio di avvenuto caricamento
							textArea.append("Caricamento effettuato.\n");
							//abilito le altre componenti della barra del menù.
							seleziona.setEnabled(true);
							visualizzaM.setEnabled(true);
							//chiudo il flusso di input
							fileInput.close();
						}
						catch(ClassNotFoundException cl) {
							textArea.append("Classe non trovata!\n");
							frameCarica.setVisible(false);
						} 
						catch (IOException e1) {
							textArea.append("File non trovato!\n");
							frameCarica.setVisible(false);
						}
					}
				}
				//creo il ricevitore e lo associo al bottone carica
				listener = new Bcarica();
				bCarica.addActionListener(listener);
			}
		}
		//creo il ricevitore e lo associo al menù carica
		listener = new CaricaListener();
		carica.addActionListener(listener);
		
		return carica;
	}
	//Sottomenù Salva(Menù File)
	public JMenuItem creaMenuSalva() {
		//Creo il sottomenù salva
		JMenuItem salva = new JMenuItem("Salva");
		//Classe interna che Crea il tipo ricevitore di eventi "SalvaListener" che creerà il nuovo ricevitore che sarà poi associato al menù Salva
		class SalvaListener implements ActionListener{
			//Sovrascrivo il metodo dell'interfaccia ActionListener
			public void actionPerformed(ActionEvent e) {
				//Dichiaro un oggetto che mi renda possibile scrivere flussi di oggetti
				ObjectOutputStream scriviSuFile;
				//Catturo le eventuali eccezioni
				try {
					impresa.setAmministrativo(amministrativo);
					impresa.setOperativo(operativo);
					//Creo l'oggetto prima dichiarato e scrivo l'oggetto sul file passato come parametro
					scriviSuFile = new ObjectOutputStream(new FileOutputStream(fileCorrente));
					//gli passo l'oggetto da scrivere
					scriviSuFile.writeObject(impresa);
					//chiudo il flusso di output
					scriviSuFile.close();
					//scrivo sull'area di testo il messaggio seguente
					textArea.append("Salvataggio avvenuto con successo su "+fileCorrente+"\n");
				}
				catch(IOException ex) {
					textArea.append(ex.getMessage()+"\n");
				}
				catch(NullPointerException ne) {
					textArea.append("Nessun file su cui salvare!\n");
				}
			}
		}
		//creo il ricevitore e lo associo al sottomenù Salva
		listener = new SalvaListener();
		salva.addActionListener(listener);
		
		return salva;
	}
	//Sottomenù Esci(Menù File)
	public JMenuItem creaMenuEsci() {
		//Creo il sottomenù Esci
		JMenuItem esci = new JMenuItem("Esci");
		//Classe interna che crea il tipo del ricevitore "EsciListener" che creerà il ricevitore che verrà associatò al menù Esci
		class EsciListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		//creo il nuovo ricevitore e lo associo al menù Esci
		listener = new EsciListener();
		esci.addActionListener(listener);
		
		return esci;
	}
	
//////SECONDO MENU'///////////////////////////////////
	
	//Menù Seleziona
	public JMenu creaMenuSeleziona() {
		//Creo il Menù seleziona e aggiungo i sottomenù
		seleziona = new JMenu("Seleziona");
		seleziona.add(creaMenuRepartoAmministrativo());
		seleziona.add(creaMenuRepartoOperativo());
		
		seleziona.setEnabled(false);
		return seleziona;
	}
	
	//Sottomenù Reparto Amministrativo(Menù Seleziona)
	public JMenu creaMenuRepartoAmministrativo() {
		//Creo sottomenù Reparto Amministrativo e aggiungo altri sottomenù
		JMenu amministrativo = new JMenu("Reparto Amministrativo");
		amministrativo.add(creaPersonale());
		amministrativo.add(creaFornitori());
		amministrativo.add(creaEnti());
		amministrativo.add(creaClienti());
		amministrativo.add(creaPagaStipendi());
		
		return amministrativo;
	}
	//Sottomenù Personale(Sottomenù Reparto Amministrativo)
	//Questo sottomenù non fa altro che disabilitare tutte le componenti grafice che non fanno parte di personale e abilita le proprie.
	public JMenuItem creaPersonale() {
		//Creo il sottomenù Personale
		JMenuItem personale = new JMenuItem("Personale");
		//Classe interna che crea il tipo ricevitore "PersonaleListener" che creerà un ricevitore che sarà associato al sottomenù Personale.
		class PersonaleListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
		//Disabilito tutte le componenti grafiche che non sono di Personale
				if(nomeFornField.isEnabled() && servizioFornField.isEnabled()) {
					nomeFornField.setEnabled(false);
					servizioFornField.setEnabled(false);
					nomeForn.setEnabled(false);
					servizioForn.setEnabled(false);
				}
				if(nomeEnteField.isEnabled()) {
					nomeEnteField.setEnabled(false);
					luogoEnteField.setEnabled(false);
					indirizzoEnteField.setEnabled(false);
					telefonoEnteField.setEnabled(false);
					nomeEnte.setEnabled(false);
					luogoEnte.setEnabled(false);
					indirizzoEnte.setEnabled(false);
					telefonoEnte.setEnabled(false);
				}
				if(nomeClienteField.isEnabled()) {
					nomeClienteField.setEnabled(false); 
					cognomeClienteField.setEnabled(false);
					indirizzoClienteField.setEnabled(false);
					telefonoClienteField.setEnabled(false);
					nomeCliente.setEnabled(false);
					cognomeCliente.setEnabled(false);
					indirizzoCliente.setEnabled(false);
					telefonoCliente.setEnabled(false);
				}
				if(reportCombo.isEnabled()) {
					reportCombo.setEnabled(false);
					crescente.setEnabled(false);
					decrescente.setEnabled(false);
					visualizza.setEnabled(false);
				}
				if(valoreField.isEnabled()) {
					valore.setEnabled(false);
					valoreField.setEnabled(false);
					responsabile.setEnabled(false);
					capoSquadra.setEnabled(false);
					responsabileField.setEnabled(false);
					capoSquadraField.setEnabled(false);
				}
				if(caposquadra.isEnabled()) {
					caposquadra.setEnabled(false);
					caposquadraField.setEnabled(false);
					nomeOperaio.setEnabled(false);
					nomeOperaioField.setEnabled(false);
					cognomeOperaio.setEnabled(false);
					cognomeOperaioField.setEnabled(false);
				}
			//Abilito i bottoni aggiungi rimuovi e cerca	
				aggiungi.setEnabled(true);
				rimuovi.setEnabled(true);
				cerca.setEnabled(true);
			//Abilito tutte le componenti del sottomenù Personale settando ogni volta la JComboBox personale su Operaio.	
				personaleCombo.setEnabled(true);
				personaleCombo.setSelectedItem("Operaio");
				nome.setEnabled(true);
				nomeField.setEnabled(true);
				cognome.setEnabled(true);
				cognomeField.setEnabled(true);
				retribuzione.setEnabled(true);
				retribuzioneField.setEnabled(true);
				mansione.setEnabled(true);
				mansioneField.setEnabled(true);
			}
		}
		//Creo il ricevitore e lo associo al sottomenù Personale
		listener = new PersonaleListener();
		personale.addActionListener(listener);
		
		return personale;
	}
	//Sottomenù Fornitori(Sottomenù Reparto Amministrativo)
	//Non fa altro che disabilitare le componenti grafiche che non fanno parte di fornitori e abilita le proprie.
	public JMenuItem creaFornitori() {
		//Creo il sottomenù Fornitori
		JMenuItem fornitori = new JMenuItem("Fornitori");
		class FornitoriListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
		//Disabilito le componenti che non sono di fornitori
				if(nomeField.isEnabled()) {
					personaleCombo.setEnabled(false);
					nome.setEnabled(false);
					nomeField.setEnabled(false);
					cognome.setEnabled(false);
					cognomeField.setEnabled(false);
					retribuzione.setEnabled(false);
					retribuzioneField.setEnabled(false);
					mansione.setEnabled(false);
					mansioneField.setEnabled(false);
					incaricoField.setEnabled(false);
					qualificaField.setEnabled(false);
					livelloField.setEnabled(false);
					areaField.setEnabled(false);
					incarico.setEnabled(false);
					qualifica.setEnabled(false);
					livello.setEnabled(false);
					area.setEnabled(false);
				}
				if(nomeEnteField.isEnabled()) {
					nomeEnteField.setEnabled(false);
					luogoEnteField.setEnabled(false);
					indirizzoEnteField.setEnabled(false);
					telefonoEnteField.setEnabled(false);
					nomeEnte.setEnabled(false);
					luogoEnte.setEnabled(false);
					indirizzoEnte.setEnabled(false);
					telefonoEnte.setEnabled(false);
				}
				if(nomeClienteField.isEnabled()) {
					nomeClienteField.setEnabled(false); 
					cognomeClienteField.setEnabled(false);
					indirizzoClienteField.setEnabled(false);
					telefonoClienteField.setEnabled(false);
					nomeCliente.setEnabled(false);
					cognomeCliente.setEnabled(false);
					indirizzoCliente.setEnabled(false);
					telefonoCliente.setEnabled(false);
				}
				if(reportCombo.isEnabled()) {
					reportCombo.setEnabled(false);
					crescente.setEnabled(false);
					decrescente.setEnabled(false);
					visualizza.setEnabled(false);
				}
				if(valoreField.isEnabled()) {
					valore.setEnabled(false);
					valoreField.setEnabled(false);
					responsabile.setEnabled(false);
					capoSquadra.setEnabled(false);
					responsabileField.setEnabled(false);
					capoSquadraField.setEnabled(false);
				}
				if(caposquadra.isEnabled()) {
					caposquadra.setEnabled(false);
					caposquadraField.setEnabled(false);
					nomeOperaio.setEnabled(false);
					nomeOperaioField.setEnabled(false);
					cognomeOperaio.setEnabled(false);
					cognomeOperaioField.setEnabled(false);
				}
				//Abilito i tre pulsanti
				aggiungi.setEnabled(true);
				rimuovi.setEnabled(true);
				cerca.setEnabled(true);
				//Abilito le componenti grafiche di fornitori
				nomeFornField.setEnabled(true);
				servizioFornField.setEnabled(true);
				nomeForn.setEnabled(true);
				servizioForn.setEnabled(true);
			}
		}
		//creo il ricevitore e gli associo il sottomenù fornitori
		listener = new FornitoriListener();
		fornitori.addActionListener(listener);
		
		return fornitori;
	}
	//Sottomenù Enti Locali(Sottomenù Reparto Amministrativo)
	//Non fa altro che disabilitare le componenti grafiche che non sono di enti locali e abilita le proprie
	public JMenuItem creaEnti(){
		//Creo il sottomenù Enti Locali
		JMenuItem enti = new JMenuItem("Enti Locali");
		class EntiListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
		//Disabilito le compponenti che non fanno parte di enti locali
				if(nomeField.isEnabled()) {
					personaleCombo.setEnabled(false);
					nome.setEnabled(false);
					nomeField.setEnabled(false);
					cognome.setEnabled(false);
					cognomeField.setEnabled(false);
					retribuzione.setEnabled(false);
					retribuzioneField.setEnabled(false);
					mansione.setEnabled(false);
					mansioneField.setEnabled(false);
					incaricoField.setEnabled(false);
					qualificaField.setEnabled(false);
					livelloField.setEnabled(false);
					areaField.setEnabled(false);
					incarico.setEnabled(false);
					qualifica.setEnabled(false);
					livello.setEnabled(false);
					area.setEnabled(false);
				}
				if(nomeFornField.isEnabled() && servizioFornField.isEnabled()) {
					nomeFornField.setEnabled(false);
					servizioFornField.setEnabled(false);
					nomeForn.setEnabled(false);
					servizioForn.setEnabled(false);
				}
				if(nomeClienteField.isEnabled()) {
					nomeClienteField.setEnabled(false); 
					cognomeClienteField.setEnabled(false);
					indirizzoClienteField.setEnabled(false);
					telefonoClienteField.setEnabled(false);
					nomeCliente.setEnabled(false);
					cognomeCliente.setEnabled(false);
					indirizzoCliente.setEnabled(false);
					telefonoCliente.setEnabled(false);
				}
				if(reportCombo.isEnabled()) {
					reportCombo.setEnabled(false);
					crescente.setEnabled(false);
					decrescente.setEnabled(false);
					visualizza.setEnabled(false);
				}
				if(valoreField.isEnabled()) {
					valore.setEnabled(false);
					valoreField.setEnabled(false);
					responsabile.setEnabled(false);
					capoSquadra.setEnabled(false);
					responsabileField.setEnabled(false);
					capoSquadraField.setEnabled(false);
				}
				if(caposquadra.isEnabled()) {
					caposquadra.setEnabled(false);
					caposquadraField.setEnabled(false);
					nomeOperaio.setEnabled(false);
					nomeOperaioField.setEnabled(false);
					cognomeOperaio.setEnabled(false);
					cognomeOperaioField.setEnabled(false);
				}
				//Abilito i tre bottoni
				aggiungi.setEnabled(true);
				rimuovi.setEnabled(true);
				cerca.setEnabled(true);
				//Abilito le componenti grafiche di enti locali
				nomeEnteField.setEnabled(true);
				luogoEnteField.setEnabled(true);
				indirizzoEnteField.setEnabled(true);
				telefonoEnteField.setEnabled(true);
				nomeEnte.setEnabled(true);
				luogoEnte.setEnabled(true);
				indirizzoEnte.setEnabled(true);
				telefonoEnte.setEnabled(true);
			}
		}
		//creo il ricevitore e lo associo al sottomenù enti
		listener = new EntiListener();
		enti.addActionListener(listener);
		
		return enti;
	}
	//Sottomenù Clienti(Sottomenù Reparto Amministrativo)
	//Non fa altro che disabilitare le componenti grafiche che non sono di Clienti e abilita le proprie
	public JMenuItem creaClienti() {
		//Crea il sottomenù Clienti
		JMenuItem clienti = new JMenuItem("Clienti");
		class ClientiListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
		//Disabilito l e componenti grafiche che non sono di Clienti
				if(nomeField.isEnabled()) {
					personaleCombo.setEnabled(false);
					nome.setEnabled(false);
					nomeField.setEnabled(false);
					cognome.setEnabled(false);
					cognomeField.setEnabled(false);
					retribuzione.setEnabled(false);
					retribuzioneField.setEnabled(false);
					mansione.setEnabled(false);
					mansioneField.setEnabled(false);
					incaricoField.setEnabled(false);
					qualificaField.setEnabled(false);
					livelloField.setEnabled(false);
					areaField.setEnabled(false);
					incarico.setEnabled(false);
					qualifica.setEnabled(false);
					livello.setEnabled(false);
					area.setEnabled(false);
				}
				if(nomeFornField.isEnabled() && servizioFornField.isEnabled()) {
					nomeFornField.setEnabled(false);
					servizioFornField.setEnabled(false);
					nomeForn.setEnabled(false);
					servizioForn.setEnabled(false);
				}
				if(nomeEnteField.isEnabled()) {
					nomeEnteField.setEnabled(false);
					luogoEnteField.setEnabled(false);
					indirizzoEnteField.setEnabled(false);
					telefonoEnteField.setEnabled(false);
					nomeEnte.setEnabled(false);
					luogoEnte.setEnabled(false);
					indirizzoEnte.setEnabled(false);
					telefonoEnte.setEnabled(false);
				}
				if(reportCombo.isEnabled()) {
					reportCombo.setEnabled(false);
					crescente.setEnabled(false);
					decrescente.setEnabled(false);
					visualizza.setEnabled(false);
				}
				if(valoreField.isEnabled()) {
					valore.setEnabled(false);
					valoreField.setEnabled(false);
					responsabile.setEnabled(false);
					capoSquadra.setEnabled(false);
					responsabileField.setEnabled(false);
					capoSquadraField.setEnabled(false);
				}
				if(caposquadra.isEnabled()) {
					caposquadra.setEnabled(false);
					caposquadraField.setEnabled(false);
					nomeOperaio.setEnabled(false);
					nomeOperaioField.setEnabled(false);
					cognomeOperaio.setEnabled(false);
					cognomeOperaioField.setEnabled(false);
				}
				//Abilito i bottoni
				aggiungi.setEnabled(true);
				rimuovi.setEnabled(true);
				cerca.setEnabled(true);
				//Abilito le componenti grafiche di Clienti
				nomeClienteField.setEnabled(true); 
				cognomeClienteField.setEnabled(true);
				indirizzoClienteField.setEnabled(true);
				telefonoClienteField.setEnabled(true);
				nomeCliente.setEnabled(true);
				cognomeCliente.setEnabled(true);
				indirizzoCliente.setEnabled(true);
				telefonoCliente.setEnabled(true);
			}
		}
		//Creo il ricevitore e lo associo al sottomenù Clienti
		listener = new ClientiListener();
		clienti.addActionListener(listener);
		
		return clienti;
	}
	//Sottomenù Pago Stipendi(Sottomenù Reparto Amministrativo)
	//Effettua il pagamento degli stipendi ai dipendenti dell'impresa
	public JMenuItem creaPagaStipendi() {
		//Creo il sottomenù Pago Stipendi
		JMenuItem pagaStipendi = new JMenuItem("Paga stipendi");
		//classe interna che crea il tipo ricevitore "PagoStipendiListener" che creerà un ricevitore che verrà associato al sottomenù Pago Stipendi 
		class PagaStipendiListener implements ActionListener{
			//Sovrascrivo
			public void actionPerformed(ActionEvent e) {
				//amministrativo chiama il metodo pagaStipendi()
				amministrativo.pagaStipendi();
				//output su area di testo
				textArea.append("Pagamento degli stipendi effettuato.\n");
			}
		}
		//Creo il ricevitore e lo associo al sottomenù Paga Stipendio
		listener = new PagaStipendiListener();
		pagaStipendi.addActionListener(listener);
		
		return pagaStipendi;
	}
	
	//Sottomenù Reparto Operativo(Menù Selziona)
	public JMenu creaMenuRepartoOperativo() {
		//Creo il sottomenù e gli aggiungo altri sottomenù
		JMenu operativo = new JMenu("Reparto Operativo");
		operativo.add(creaCantieri());
		operativo.add(creaOperaioSquadra());
		
		return operativo;
	}
	//Sottomenù Cantieri(Sottomenù Reparto Operativo)
	//Non fa altro che disabilitare tutti i componenti grafici che non sono di cantieri e abilita i propri.
	public JMenuItem creaCantieri() {
		//Creo il sottomenù
		JMenuItem cantieri = new JMenuItem("Cantieri");
		//Classe interna che crea il tipo ricevitore "CantieriListener" che creerà il ricevitore che verrà associato al sottomenù cantieri
		class CantieriListener implements ActionListener{
			//Sovrascrivo
			public void actionPerformed(ActionEvent e) {
		//Disabilito componenti grafiche che non sono di cantieri
				if(nomeField.isEnabled()) {
					personaleCombo.setEnabled(false);
					nome.setEnabled(false);
					nomeField.setEnabled(false);
					cognome.setEnabled(false);
					cognomeField.setEnabled(false);
					retribuzione.setEnabled(false);
					retribuzioneField.setEnabled(false);
					mansione.setEnabled(false);
					mansioneField.setEnabled(false);
					incaricoField.setEnabled(false);
					qualificaField.setEnabled(false);
					livelloField.setEnabled(false);
					areaField.setEnabled(false);
					incarico.setEnabled(false);
					qualifica.setEnabled(false);
					livello.setEnabled(false);
					area.setEnabled(false);
				}
				if(nomeFornField.isEnabled() && servizioFornField.isEnabled()) {
					nomeFornField.setEnabled(false);
					servizioFornField.setEnabled(false);
					nomeForn.setEnabled(false);
					servizioForn.setEnabled(false);
				}
				if(nomeEnteField.isEnabled()) {
					nomeEnteField.setEnabled(false);
					luogoEnteField.setEnabled(false);
					indirizzoEnteField.setEnabled(false);
					telefonoEnteField.setEnabled(false);
					nomeEnte.setEnabled(false);
					luogoEnte.setEnabled(false);
					indirizzoEnte.setEnabled(false);
					telefonoEnte.setEnabled(false);
				}
				if(nomeClienteField.isEnabled()) {
					nomeClienteField.setEnabled(false); 
					cognomeClienteField.setEnabled(false);
					indirizzoClienteField.setEnabled(false);
					telefonoClienteField.setEnabled(false);
					nomeCliente.setEnabled(false);
					cognomeCliente.setEnabled(false);
					indirizzoCliente.setEnabled(false);
					telefonoCliente.setEnabled(false);
				}
				if(reportCombo.isEnabled()) {
					reportCombo.setEnabled(false);
					crescente.setEnabled(false);
					decrescente.setEnabled(false);
					visualizza.setEnabled(false);
				}
				if(caposquadra.isEnabled()) {
					caposquadra.setEnabled(false);
					caposquadraField.setEnabled(false);
					nomeOperaio.setEnabled(false);
					nomeOperaioField.setEnabled(false);
					cognomeOperaio.setEnabled(false);
					cognomeOperaioField.setEnabled(false);
				}
				//Abilito i bottoni
				aggiungi.setEnabled(true);
				rimuovi.setEnabled(true);
				cerca.setEnabled(true);
				//Abilito componenti grafiche di cantiere
				valore.setEnabled(true);
				valoreField.setEnabled(true);
				responsabile.setEnabled(true);
				capoSquadra.setEnabled(true);
				responsabileField.setEnabled(true);
				capoSquadraField.setEnabled(true);
				
			}
		}
		//Creo il ricevitore e lo associo al sottomenù Cantieri
		listener = new CantieriListener();
		cantieri.addActionListener(listener);
		
		return cantieri;
	}
	//Sottomenù Operai squadra
	//Non fa altro che disabilitare tutte le componenti grafiche che non sono di Operai squadra e abilita le proprie
	public JMenuItem creaOperaioSquadra() {	
		//Creo sottomenù
		JMenuItem addOperaio = new JMenuItem("Operai squadra");
		//classe interna che crea tipo ricevitore "AddOperaioListener" che creerà il ricevitore che srà associato a operai squadra
		class AddOperaioListener implements ActionListener{
			//Sovrascrivo
			public void actionPerformed(ActionEvent e) {
		//Disabilito tutte le componenti grafiche che non sono di operai squadra 
				if(nomeField.isEnabled()) {
					personaleCombo.setEnabled(false);
					nome.setEnabled(false);
					nomeField.setEnabled(false);
					cognome.setEnabled(false);
					cognomeField.setEnabled(false);
					retribuzione.setEnabled(false);
					retribuzioneField.setEnabled(false);
					mansione.setEnabled(false);
					mansioneField.setEnabled(false);
					incaricoField.setEnabled(false);
					qualificaField.setEnabled(false);
					livelloField.setEnabled(false);
					areaField.setEnabled(false);
					incarico.setEnabled(false);
					qualifica.setEnabled(false);
					livello.setEnabled(false);
					area.setEnabled(false);
				}
				if(nomeFornField.isEnabled() && servizioFornField.isEnabled()) {
					nomeFornField.setEnabled(false);
					servizioFornField.setEnabled(false);
					nomeForn.setEnabled(false);
					servizioForn.setEnabled(false);
				}
				if(nomeEnteField.isEnabled()) {
					nomeEnteField.setEnabled(false);
					luogoEnteField.setEnabled(false);
					indirizzoEnteField.setEnabled(false);
					telefonoEnteField.setEnabled(false);
					nomeEnte.setEnabled(false);
					luogoEnte.setEnabled(false);
					indirizzoEnte.setEnabled(false);
					telefonoEnte.setEnabled(false);
				}
				if(nomeClienteField.isEnabled()) {
					nomeClienteField.setEnabled(false); 
					cognomeClienteField.setEnabled(false);
					indirizzoClienteField.setEnabled(false);
					telefonoClienteField.setEnabled(false);
					nomeCliente.setEnabled(false);
					cognomeCliente.setEnabled(false);
					indirizzoCliente.setEnabled(false);
					telefonoCliente.setEnabled(false);
				}
				if(reportCombo.isEnabled()) {
					reportCombo.setEnabled(false);
					crescente.setEnabled(false);
					decrescente.setEnabled(false);
					visualizza.setEnabled(false);
				}
				if(valoreField.isEnabled()) {
					valore.setEnabled(false);
					valoreField.setEnabled(false);
					responsabile.setEnabled(false);
					capoSquadra.setEnabled(false);
					responsabileField.setEnabled(false);
					capoSquadraField.setEnabled(false);
				}
				//Abilito i bottoni
				aggiungi.setEnabled(true);
				rimuovi.setEnabled(true);
				cerca.setEnabled(true);
				//Abilito le componenti grafiche di operai squadra
				caposquadra.setEnabled(true);
				caposquadraField.setEnabled(true);
				nomeOperaio.setEnabled(true);
				nomeOperaioField.setEnabled(true);
				cognomeOperaio.setEnabled(true);
				cognomeOperaioField.setEnabled(true);
				
				
			}
		}
		//creo il ricevitore e lo associo al sottomenù operai squadra
		listener = new AddOperaioListener();
		addOperaio.addActionListener(listener);
		
		return addOperaio;
	}
	
/////////////////////////TERZO MENU'//////////////////////////////
	
	//Menù Visualizza
	public JMenu creaMenuVisualizza() {
		//Creo il menù e gli aggiungo i relativi sottomenù
		visualizzaM = new JMenu("Visualizza");
		
		visualizzaM.add(creaReport());
		visualizzaM.add(creaListaPersonale());
		visualizzaM.add(creaListaFornitori());
		visualizzaM.add(creaListaEnti());
		visualizzaM.add(creaListaClienti());
		visualizzaM.add(creaListaCantieri());
		
		visualizzaM.setEnabled(false);
		return visualizzaM;
	}
	//Sottomenù Report(Menù Visualizza)
	//Abilita le proprie componenti grafiche e disabilita le altre
	public JMenuItem creaReport(){
		//Creo il sottomenù
		JMenuItem report = new JMenuItem("Report");
		//Classe interna che crea il tipo ricevitore "reportListener" che creerà il ricevitore che verrà associato al sottomenù report.
		class ReportListener implements ActionListener{
			//sovrascrivo
			public void actionPerformed(ActionEvent e) {
		//Disabilito le componenti che non sono di report
				if(nomeField.isEnabled()) {
					personaleCombo.setEnabled(false);
					nome.setEnabled(false);
					nomeField.setEnabled(false);
					cognome.setEnabled(false);
					cognomeField.setEnabled(false);
					retribuzione.setEnabled(false);
					retribuzioneField.setEnabled(false);
					mansione.setEnabled(false);
					mansioneField.setEnabled(false);
					incaricoField.setEnabled(false);
					qualificaField.setEnabled(false);
					livelloField.setEnabled(false);
					areaField.setEnabled(false);
					incarico.setEnabled(false);
					qualifica.setEnabled(false);
					livello.setEnabled(false);
					area.setEnabled(false);
				}
				if(nomeFornField.isEnabled() && servizioFornField.isEnabled()) {
					nomeFornField.setEnabled(false);
					servizioFornField.setEnabled(false);
					nomeForn.setEnabled(false);
					servizioForn.setEnabled(false);
				}
				if(nomeEnteField.isEnabled()) {
					nomeEnteField.setEnabled(false);
					luogoEnteField.setEnabled(false);
					indirizzoEnteField.setEnabled(false);
					telefonoEnteField.setEnabled(false);
					nomeEnte.setEnabled(false);
					luogoEnte.setEnabled(false);
					indirizzoEnte.setEnabled(false);
					telefonoEnte.setEnabled(false);
				}
				if(nomeClienteField.isEnabled()) {
					nomeClienteField.setEnabled(false); 
					cognomeClienteField.setEnabled(false);
					indirizzoClienteField.setEnabled(false);
					telefonoClienteField.setEnabled(false);
					nomeCliente.setEnabled(false);
					cognomeCliente.setEnabled(false);
					indirizzoCliente.setEnabled(false);
					telefonoCliente.setEnabled(false);
				}
				if(valoreField.isEnabled()) {
					valore.setEnabled(false);
					valoreField.setEnabled(false);
					responsabile.setEnabled(false);
					capoSquadra.setEnabled(false);
					responsabileField.setEnabled(false);
					capoSquadraField.setEnabled(false);
				}
				if(caposquadra.isEnabled()) {
					caposquadra.setEnabled(false);
					caposquadraField.setEnabled(false);
					nomeOperaio.setEnabled(false);
					nomeOperaioField.setEnabled(false);
					cognomeOperaio.setEnabled(false);
					cognomeOperaioField.setEnabled(false);
				}
				//Abilito le componenti grafiche di report
				reportCombo.setEnabled(true);
				crescente.setEnabled(true);
				decrescente.setEnabled(true);
				visualizza.setEnabled(true);
			}
		}
		//creo il ricevitore e lo associo al sottomenù report
		listener = new ReportListener();
		report.addActionListener(listener);
		
		return report;
	}
	//Sottomenù lista Personale(Menù visualizza)
	//Stampa la lista del personale dell'impresa
	public JMenuItem creaListaPersonale() {
		//Creo il sottomenù
		JMenuItem listPersonale = new JMenuItem("Lista personale");
		//classe interna per il tipo ricevitore
		class ListPersListener implements ActionListener{
			//sovrascrivo
			public void actionPerformed(ActionEvent e) {
				textArea.append("LISTA PERSONALE IMPRESA EDILE:\n");
				//Stampo il personale
				for(Personale p: amministrativo.getPersonale()) {
					if(p.getClass().getName().equals("amministrativo.Operaio")) {
						Operaio o = (Operaio) p;
						textArea.append("Nome: "+o.getNome()+"\nCognome: "+o.getCognome()+"\nMansione: "+o.getMansione()+"\nRetribuzione: "+o.getRetribuzione()+"€\nSaldo: "+o.getConto().getSaldo()+"€\n\n");
					}
					else if(p.getClass().getName().equals("amministrativo.Impiegato")) {
						Impiegato i = (Impiegato) p;
						textArea.append("Nome: "+i.getNome()+"\nCognome: "+i.getCognome()+"\nIncarico: "+i.getIncarico()+"\nRetribuzione: "+i.getRetribuzione()+"€\nSaldo: "+i.getConto().getSaldo()+"€\n\n");
					}
					else if(p.getClass().getName().equals("amministrativo.Quadro")) {
						Quadro q = (Quadro) p;
						textArea.append("Nome: "+q.getNome()+"\nCognome: "+q.getCognome()+"\nQualifica: "+q.getQualifica()+"\nLivello: "+q.getLivello()+"\nRetribuzione: "+q.getRetribuzione()+"€\nSaldo: "+q.getConto().getSaldo()+"€\n\n");
					}
					else if(p.getClass().getName().equals("amministrativo.Dirigente")) {
						Dirigente d = (Dirigente) p;
						textArea.append("Nome: "+d.getNome()+"\nCognome: "+d.getCognome()+"\nArea di competenza: "+d.getAreaCompetenza()+"\nRetribuzione: "+d.getRetribuzione()+"€\nSaldo: "+d.getConto().getSaldo()+"€\n\n");
					}
				}
			}
		}
		//creo un ricevitore e lo associo al sottomenù lista personale
		listener = new ListPersListener();
		listPersonale.addActionListener(listener);
		
		return listPersonale;
	}
	//Sottomenù lista Fornitori(Menù Visualizza)
	//Stampa i fornitori
	public JMenuItem creaListaFornitori() {
		//creo sottomenù
		JMenuItem listFornitori = new JMenuItem("Lista fornitori");
		//classe interna per il tipo ricevitore
		class ListFornitoriListener implements ActionListener{
			//sovrascrivo
			public void actionPerformed(ActionEvent e) {
				textArea.append("LISTA FORNITORI:\n");
				//Stampo fornitori
				for(Fornitori f: amministrativo.getFornitori()) {
					textArea.append("Nome: "+f.getNome()+"\nServizio: "+f.getServizio()+"\n\n");
				}
			}
		}
		//creo ricevitore e lo associo al sottomenù lista fornitori
		listener = new ListFornitoriListener();
		listFornitori.addActionListener(listener);
		
		return listFornitori;
	}
	//sottomenù lista enti(Menù Visualizza)
	//Stampa gli enti 
	public JMenuItem creaListaEnti() {
		//creo sottomenù
		JMenuItem listEnti = new JMenuItem("Lista enti locali");
		//classe interna che crea tipo ricevitore
		class ListEntiListener implements ActionListener{
			//sovrascrivo
			public void actionPerformed(ActionEvent e) {
				textArea.append("LISTA ENTI LOCALI: \n");
				//Stampo enti
				for(EntiLocali en: amministrativo.getEntiLocali()) {
					textArea.append("Nome ente: "+en.getNome()+"\nRegione: "+en.getLuogo()+"\nIndirizzo: "+en.getIndirizzo()+"\nTelefono: "+en.getTelefono()+"\n\n");
				}
			}
		}
		//creo ricevitore e lo associo al sottomenù lista enti
		listener = new ListEntiListener();
		listEnti.addActionListener(listener);
		
		return listEnti;
	}
	//Sottomenù lista clienti(Menù Visualizza)
	//Stampa i clienti
	public JMenuItem creaListaClienti() {
		//creo sottomenù
		JMenuItem listClienti = new JMenuItem("Lista clienti");
		//classe interna che crea tipo ricevitore
		class ListClientiListener implements ActionListener{
			//sovrascrivo
			public void actionPerformed(ActionEvent e) {
				//stampo clienti
				textArea.append("LISTA CLIENTI: \n");
				for(Clienti c: amministrativo.getClienti()) {
					textArea.append("Nome: "+c.getNome()+"\nCognome: "+c.getCognome()+"\nIndirizzo: "+c.getIndirizzo()+"\nTelefono: "+c.getTelefono()+"\n\n");
				}
			}
		}
		//creo ricevitore e lo associo al sottomenù lista clienti
		listener = new ListClientiListener();
		listClienti.addActionListener(listener);
		
		return listClienti;
	}
	//Sottomenù lista cantieri(Menù Visualizza)
	//Stampa i cantieri dell'impresa
	public JMenuItem creaListaCantieri() {
		//creo sottomenù
		JMenuItem listCantieri = new JMenuItem("Lista cantieri");
		//classe interna che crea il tipo ricevitore
		class ListCantieriListener implements ActionListener{
			//sovrscrivo
			public void actionPerformed(ActionEvent e) {
				//stampo info cantieri 
				textArea.append("LISTA CANTIERI: \n");
				for(Cantiere c: operativo.getCantieri()) {
					textArea.append("Valore: "+c.getValore()+"€\nResponsabile: "+c.getResponsabile().getNome()+" "+c.getResponsabile().getCognome()
									+"\nCapo squadra: "+c.getSquadra().getCapoSquadra().getNome()+" "+c.getSquadra().getCapoSquadra().getCognome()
									+"\nOperai in squadra: ");
					for(Operaio o: c.getSquadra().getOperai()) {
						textArea.append(o.getNome()+" "+o.getCognome()+" "+o.getMansione()+" ");
					}
					textArea.append("\n\n");
				}
			}
		}
		//creo il ricevitore e lo associo al sottomenù lista cantieri
		listener = new ListCantieriListener();
		listCantieri.addActionListener(listener);
		
		return listCantieri;
	}
	
/////////////////////////////////////////////////////////////////////PANNELLO DI CONTROLLO PRINCIPALE///////////////////////////////////////////////////////////////////////////////
	
	//Pannello di controllo che contiene tutte le componenti del frame principale
	public JPanel pannelloDiControllo() {
		//Creo il pannello principale che conterrà le componenti dell' impresa e il pannello dei bottoni comuni
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		//creo i bordi di ogni componente
		personale = creaBordoPersonale();
		fornitori = creaBordoFornitori();
		enti = creaBordoEnte();
		clienti = creaBordoClienti();
		report = creaBordoReport();
		cantieri = creaBordoCantieri();
		squad = creaBordoSquadra();
		//creo il pannello che contiene i tre bottoni comuni a tutte le componenti
		JPanel pannelloBottoni = new JPanel();
		pannelloBottoni.setLayout(new GridLayout(5, 3));
		pannelloBottoni.add(new JLabel());
		pannelloBottoni.add(creaBottoneAggiungi());
		pannelloBottoni.add(new JLabel());
		pannelloBottoni.add(new JLabel());
		pannelloBottoni.add(creaBottoneRimuovi());
		pannelloBottoni.add(new JLabel());
		pannelloBottoni.add(new JLabel());
		pannelloBottoni.add(creaBottoneCerca());
		pannelloBottoni.add(new JLabel());
		pannelloBottoni.add(new JLabel());
		pannelloBottoni.add(new JLabel());
		pannelloBottoni.add(new JLabel());
		pannelloBottoni.add(new JLabel());
		//aggiungo tutte le componenti e il pannello dei bottoni nel pannello principale
		panel.add(personale);
		panel.add(enti);
		panel.add(fornitori);
		panel.add(pannelloBottoni);
		panel.add(clienti);
		panel.add(report);
		panel.add(cantieri);
		panel.add(squad);
		
		return panel;
	}
	//Bordo Personale...Contiene tutte le istanze di Personale e delle classi che la estendono
	public JPanel creaBordoPersonale() {
		//Creo JComboBox per scegliere il tipo di dipendente.
		personaleCombo = new JComboBox();
		personaleCombo.addItem("Operaio");
		personaleCombo.addItem("Impiegato");
		personaleCombo.addItem("Quadro");
		personaleCombo.addItem("Dirigente");
		personaleCombo.setSelectedItem("Operaio");
		//Classe interna che crea il tipo ricevitore "PersonaleComboListener" che creerà un ricevitore che verrà associato a personaleCombo
		//Questo mi permette di abilitre o disabilitare le istanze del personale in base al tipo di dipendente.
		class PersonaleComboListener implements ActionListener{
			//sovrascrivo
			public void actionPerformed(ActionEvent e) {
			//Abilita e disabilita in base all'item selezionato da personaleCombo
				if(personaleCombo.getSelectedItem().equals("Operaio")) {
					mansioneField.setEnabled(true);
					incaricoField.setEnabled(false);
					qualificaField.setEnabled(false);
					livelloField.setEnabled(false);
					areaField.setEnabled(false);
					mansione.setEnabled(true);
					incarico.setEnabled(false);
					qualifica.setEnabled(false);
					livello.setEnabled(false);
					area.setEnabled(false);
					
				}
				else if(personaleCombo.getSelectedItem().equals("Impiegato")) {
					incaricoField.setEnabled(true);
					mansioneField.setEnabled(false);
					qualificaField.setEnabled(false);
					livelloField.setEnabled(false);
					areaField.setEnabled(false);
					mansione.setEnabled(false);
					incarico.setEnabled(true);
					qualifica.setEnabled(false);
					livello.setEnabled(false);
					area.setEnabled(false);
				}
				else if(personaleCombo.getSelectedItem().equals("Quadro")) {
					qualificaField.setEnabled(true);
					livelloField.setEnabled(true);
					mansioneField.setEnabled(false);
					incaricoField.setEnabled(false);
					areaField.setEnabled(false);
					mansione.setEnabled(false);
					incarico.setEnabled(false);
					qualifica.setEnabled(true);
					livello.setEnabled(true);
					area.setEnabled(false);
				}
				else if(personaleCombo.getSelectedItem().equals("Dirigente")) {
					areaField.setEnabled(true);
					mansioneField.setEnabled(false);
					incaricoField.setEnabled(false);
					qualificaField.setEnabled(false);
					livelloField.setEnabled(false);
					mansione.setEnabled(false);
					incarico.setEnabled(false);
					qualifica.setEnabled(false);
					livello.setEnabled(false);
					area.setEnabled(true);
				}
			}
		}
		//creo il ricevitore e lo associo a personaleCombo
		listener = new PersonaleComboListener();
		personaleCombo.addActionListener(listener);
		//JLABEL
		nome = new JLabel("Nome");
		cognome = new JLabel("Cognome");
		retribuzione = new JLabel("Retribuzione");
		mansione = new JLabel("Mansione");
		incarico = new JLabel("Incarico");
		qualifica = new JLabel("Qualifica");
		livello = new JLabel("Livello");
		area = new JLabel("Area competenza");
		//JTEXTFIELD
		nomeField = new JTextField(FIELD_WIDTH);
		cognomeField = new JTextField(FIELD_WIDTH);
		retribuzioneField = new JTextField(FIELD_WIDTH);
		mansioneField = new JTextField(FIELD_WIDTH);
		incaricoField = new JTextField(FIELD_WIDTH);
		qualificaField = new JTextField(FIELD_WIDTH);
		livelloField = new JTextField(FIELD_WIDTH);
		areaField = new JTextField(FIELD_WIDTH);
		//DISABILITO JTFIELD
		personaleCombo.setEnabled(false);
		nomeField.setEnabled(false);
		cognomeField.setEnabled(false);
		retribuzioneField.setEnabled(false);
		mansioneField.setEnabled(false);
		incaricoField.setEnabled(false);
		qualificaField.setEnabled(false);
		livelloField.setEnabled(false);
		areaField.setEnabled(false);
		//DISABILITO JLABEL
		nome.setEnabled(false);
		cognome.setEnabled(false);
		retribuzione.setEnabled(false);
		mansione.setEnabled(false);
		incarico.setEnabled(false);
		qualifica.setEnabled(false);
		livello.setEnabled(false);
		area.setEnabled(false);
		//Creo il pannello personale settandogli il bordo che conterrà tutte le componenti di personale
		JPanel personale = new JPanel();
		personale.setLayout(new GridLayout(9, 2));
		personale.add(personaleCombo);
		personale.add(new JLabel());
		personale.add(nome);
		personale.add(nomeField);
		personale.add(cognome);
		personale.add(cognomeField);
		personale.add(retribuzione);
		personale.add(retribuzioneField);
		personale.add(mansione);
		personale.add(mansioneField);
		personale.add(incarico);
		personale.add(incaricoField);
		personale.add(qualifica);
		personale.add(qualificaField);
		personale.add(livello);
		personale.add(livelloField);
		personale.add(area);
		personale.add(areaField);
		personale.setBorder(new TitledBorder(new EtchedBorder(), "Personale"));
		
		return personale;
	}
	//Bottone Aggiungi
	//Questo bottone è condiviso tra tutte le componenti dell'impresa e quando si genererà l'evento esso agirà sulla componente abilitata ignorando le altre
	public JButton creaBottoneAggiungi() {
		//creo il bottone aggiungi
		aggiungi = new JButton("Aggiungi");
		//Classe interna che crea il tipo ricevitore "AggiungiListener" che creerà un ricevitore che verrà associato al bottone aggiungi
		class AggiungiListener implements ActionListener{
			//Sovrascrivo
			public void actionPerformed(ActionEvent e) {
				//Controllo se almeno una componente del bordo personale è abilitata, se è abilitata allora si andrà ad agire su Personale
				//altrimenti ignoro
				if(personaleCombo.isEnabled()) {
					//Controllo su quale tipo di personale si deve agire prendendo il riferimento dal item selezionato da personaleCombo...e così per tutti
					//i tipi di personale
					if(personaleCombo.getSelectedItem().equals("Operaio")) {
						//Operaio è l'item selezionato
						//try/catch che cattura le opportune eccezioni
						try {
							//aggiungo operaio alla lista personale
							amministrativo.assumiPersonale(new Operaio(nomeField.getText(), cognomeField.getText(), 
															Integer.parseInt(retribuzioneField.getText()),
															new Conto(), mansioneField.getText()));
							
							
							textArea.append("Aggiunto nuovo operaio.\n");
							//Setto le JTField interessate in modo da "cancellare" quello che si era digitato
							//e disabilito le componenti operaio
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
						catch(ArgomentoNonValidoException ne) {
							textArea.append(ne.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
						catch(FailureRetribuzioneException re) {
							textArea.append(re.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
						catch(NumberFormatException nu) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
					}
					else if(personaleCombo.getSelectedItem().equals("Impiegato")) {
						//Impiegato è l'item selezionato
						//try/catch per catturare le opportune eccezioni
						try {
							amministrativo.assumiPersonale(new Impiegato(nomeField.getText(), cognomeField.getText(), 
																				  Integer.parseInt(retribuzioneField.getText()),
																				  new Conto(),incaricoField.getText()));
							
							
							textArea.append("Aggiunto nuovo impiegato.\n");
							//setto le JTField interessate per "cancellare" ciò che si era digitato
							//e disabilito le componenti di impiegato
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
						catch(ArgomentoNonValidoException ne) {
							textArea.append(ne.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
						catch(FailureRetribuzioneException re) {
							textArea.append(re.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
						catch(NumberFormatException nu) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
					}
					else if(personaleCombo.getSelectedItem().equals("Quadro")) {
						//Quadro è l'item selezionato
						//try/catch per catturare le opportune eccezioni
						try {
							amministrativo.assumiPersonale(new Quadro(nomeField.getText(), cognomeField.getText(), 
																				Integer.parseInt(retribuzioneField.getText()), 
																				new Conto(), qualificaField.getText(), 
																				livelloField.getText()));
						
							
							textArea.append("Aggiunto nuovo quadro.\n");
							//Setto le JTField interessate per "cancellare" ciò che si era digitato
							//e disabilito le componenti di quadro
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
						catch(ArgomentoNonValidoException ne) {
							textArea.append(ne.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
						catch(FailureRetribuzioneException re) {
							textArea.append(re.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
						catch(NumberFormatException nu) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
					}
					else if(personaleCombo.getSelectedItem().equals("Dirigente")) {
						//Dirigente è l'item selezionato
						try {
							amministrativo.assumiPersonale(new Dirigente(nomeField.getText(), cognomeField.getText(),
																				  Integer.parseInt(retribuzioneField.getText()), 
																				  new Conto(), areaField.getText()));
						
							textArea.append("Aggiunto nuovo dirigente.\n");
							//setto le JTField interessate per "cancellare" ciò che era stato digitato
							//e disabilito le componenti di dirigente
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
						catch(ArgomentoNonValidoException ne) {
							textArea.append(ne.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
						catch(FailureRetribuzioneException re) {
							textArea.append(re.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
						catch(NumberFormatException nu) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
					}
					//Disabilito le componenti di personale
					personaleCombo.setEnabled(false);
					nome.setEnabled(false);
					nomeField.setEnabled(false);
					cognomeField.setEnabled(false);
					cognome.setEnabled(false);
					retribuzioneField.setEnabled(false);
					retribuzione.setEnabled(false);
				}
				//Controllo se le componenti del bordo fornitori sono abilitate, se sono abilitate si va ad agire
				//altrimenti si ignora
				else if(nomeFornField.isEnabled() && servizioFornField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//aggiungi fornitore
						amministrativo.aggiungiFornitore(new Fornitori(nomeFornField.getText(), servizioFornField.getText()));
						textArea.append("E' stato aggiunto un nuovo fornitore.\n");
						//setto le JTField per "cancellare" ciò che è stato digitato
						//e disabilito le componenti di fornitori
						nomeFornField.setText(null);
						servizioFornField.setText(null);
						nomeFornField.setEnabled(false);
						servizioFornField.setEnabled(false);
						nomeForn.setEnabled(false);
						servizioForn.setEnabled(false);
					}
					catch(ArgomentoNonValidoException ne) {
						textArea.append(ne.getMessage()+'\n');
						nomeFornField.setText(null);
						servizioFornField.setText(null);
						nomeFornField.setEnabled(false);
						servizioFornField.setEnabled(false);
						nomeForn.setEnabled(false);
						servizioForn.setEnabled(false);
					}
				}
				//Controllo se le componenti del bordo enti locali sono abilitate, se sono abilitate si va ad agire
				//altrimenti si ignora
				else if(nomeEnteField.isEnabled() && luogoEnteField.isEnabled() && indirizzoEnteField.isEnabled() && telefonoEnteField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//aggiungi ente
						amministrativo.aggiungiEnte(new EntiLocali(nomeEnteField.getText(), luogoEnteField.getText(), indirizzoEnteField.getText(), telefonoEnteField.getText()));
						textArea.append("E' stato aggiunto un nuovo ente locale.\n");
						//setto le JTField per "cancellare" ciò che è stato digitato
						//e disabilito le componenti di enti locali
						nomeEnteField.setText(null);
						luogoEnteField.setText(null);
						indirizzoEnteField.setText(null);
						telefonoEnteField.setText(null);
						nomeEnteField.setEnabled(false);
						luogoEnteField.setEnabled(false);
						indirizzoEnteField.setEnabled(false);
						telefonoEnteField.setEnabled(false);
						nomeEnte.setEnabled(false);
						luogoEnte.setEnabled(false);
						indirizzoEnte.setEnabled(false);
						telefonoEnte.setEnabled(false);
					}
					catch(ArgomentoNonValidoException ne) {
						textArea.append(ne.getMessage()+"\n");
						nomeEnteField.setText(null);
						luogoEnteField.setText(null);
						indirizzoEnteField.setText(null);
						telefonoEnteField.setText(null);
						nomeEnteField.setEnabled(false);
						luogoEnteField.setEnabled(false);
						indirizzoEnteField.setEnabled(false);
						telefonoEnteField.setEnabled(false);
						nomeEnte.setEnabled(false);
						luogoEnte.setEnabled(false);
						indirizzoEnte.setEnabled(false);
						telefonoEnte.setEnabled(false);
					}
				}
				//Controllo se almeno una componente del bordo clienti è abilitata, se lo è si va ad agire
				//altrimenti si ignora
				else if(nomeClienteField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//aggiungi cliente
						amministrativo.aggiungiCliente(new Clienti(nomeClienteField.getText(), cognomeClienteField.getText(), indirizzoClienteField.getText(), telefonoClienteField.getText()));
						textArea.append("E' stato aggiunto un nuovo cliente.\n");
						//setto le JTField per "cancellare" ciò che è stato digitato
						//e disabilito le componenti di clienti
						nomeClienteField.setText(null);
						cognomeClienteField.setText(null);
						indirizzoClienteField.setText(null);
						telefonoClienteField.setText(null);
						nomeClienteField.setEnabled(false); 
						cognomeClienteField.setEnabled(false);
						indirizzoClienteField.setEnabled(false);
						telefonoClienteField.setEnabled(false);
						nomeCliente.setEnabled(false);
						cognomeCliente.setEnabled(false);
						indirizzoCliente.setEnabled(false);
						telefonoCliente.setEnabled(false);
					}
					catch(ArgomentoNonValidoException ne) {
						textArea.append(ne.getMessage()+"\n");
						nomeClienteField.setText(null);
						cognomeClienteField.setText(null);
						indirizzoClienteField.setText(null);
						telefonoClienteField.setText(null);
						nomeClienteField.setEnabled(false); 
						cognomeClienteField.setEnabled(false);
						indirizzoClienteField.setEnabled(false);
						telefonoClienteField.setEnabled(false);
						nomeCliente.setEnabled(false);
						cognomeCliente.setEnabled(false);
						indirizzoCliente.setEnabled(false);
						telefonoCliente.setEnabled(false);
					}
				}
				//Controllo se almeno una componente del bordo cantieri è abilitata, se lo è si va ad agire
				//altrimenti si ignora
				else if(valoreField.isEnabled()) {
					Personale respon = null;
					Quadro capoS = null;
					Cantiere c = null;
					String nomeR,cognomeR,resp,capo,nomeC,cognomeC;
					int val;
					//try/catch per catturare le opportune eccezioni
					try {
						//vado a recuperare tutte le informazioni dalle JTField salvandole nelle
						//rispettive variabili prima dichiarate
						val = Integer.parseInt(valoreField.getText());
						resp = responsabileField.getText();
						nomeR = resp.substring(0, resp.indexOf(' '));
						cognomeR = resp.substring(resp.indexOf(' ')+1);

						capo = capoSquadraField.getText();
						nomeC = capo.substring(0, capo.indexOf(' '));
						cognomeC = capo.substring(capo.indexOf(' ')+1);
						//Dalle info precedentemente salvate mi vado a cercare se nel personale risulta un dipendente con il nome e 
						//il cognome del candidato responsabile e lo stesso faccio per il candidato caposquadra
						for(Personale p: amministrativo.getPersonale()) {
							if(p.getNome().equalsIgnoreCase(nomeR) && p.getCognome().equalsIgnoreCase(cognomeR))
								respon = p;
							if(p.getClass().getName().equals("amministrativo.Quadro"))
								if(p.getNome().equalsIgnoreCase(nomeC) && p.getCognome().equalsIgnoreCase(cognomeC))
									capoS = (Quadro)p;
						}
						//Vado a controllare se il caposquadra scelto risulta già essere impegnato, se lo è lancio eccezione altrimenti si va
						//a creare il cantiere e lo si aggiunge alla lista cantieri stampando le info del antiere appena aggiunto.
						if(!capoS.isImpegnato()) {
							c = new Cantiere(val, respon,new Squadra(capoS));
							operativo.aggiungiCantiere(c);
							textArea.append("E' stato aggiunto il seguente cantiere:\n"
									+ "Valore: "+c.getValore()+"€\n"
									+ "Responsabile: "+c.getResponsabile().getNome()+" "+c.getResponsabile().getCognome()+"\n"
									+ "Capo squadra: "+c.getSquadra().getCapoSquadra().getNome()+" "+c.getSquadra().getCapoSquadra().getCognome()+"\n");
						}
						else
							throw new PersonaleImpegnatoException();
						
						
						//Setto le JTField per "cancellare" ciò che si era digitato
						//e si disabilitano tutte le componenti di cantiere
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(NumberFormatException nu) {
						textArea.append("Cantiere: Inserire parametri validi!\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ValueNegativeException ve) {
						textArea.append(ve.getMessage()+"\n");
						//se si cattura questa eccezione il caposquadra risulterà essere comunque
						//impegnato e ciò creerà delle informazioni non veritiere per cui si andrà
						//a settare impegnato del caposquadra.
						capoS.setImpegnato(false);
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ResponsabileNotDirigenteException re) {
						textArea.append(re.getMessage()+"\n");
						//se si cattura questa eccezione il caposquadra risulterà essere comunque
						//impegnato e ciò creerà delle informazioni non veritiere per cui si andrà
						//a settare impegnato del caposquadra.
						capoS.setImpegnato(false);
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ResponsabileNotQuadroException qe) {
						textArea.append(qe.getMessage()+"\n");
						//se si cattura questa eccezione il caposquadra risulterà essere comunque
						//impegnato e ciò creerà delle informazioni non veritiere per cui si andrà
						//a settare impegnato del caposquadra.
						capoS.setImpegnato(false);
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(PersonaleImpegnatoException pe) {
						textArea.append("Squadra: Il quadro risulta già essere impegnato!\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(StringIndexOutOfBoundsException se) {
						textArea.append("Cantiere: Inserire parametri validi!\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ArgomentoNonValidoException nu) {
						textArea.append("Cantiere: Inserire parametri validi!\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
				}
				//Controllo se almeno una componente del bordo squadra è abilitata, se lo è si va ad agire
				//altrimenti si ignora
				else if(caposquadraField.isEnabled()) {
					String capo,nomeC,cognomeC,nomeO,cognomeO;
					Quadro capoS = null;
					Operaio operaioS = null;
					Cantiere can = null;
					//try/catch per catturare le opportune eccezioni
					try {
						//vado a recuperare tutte le informazioni dalle JTField salvandole nelle
						//rispettive variabili prima dichiarate
						capo = caposquadraField.getText();
						nomeC = capo.substring(0, capo.indexOf(' '));
						cognomeC = capo.substring(capo.indexOf(' ')+1);
						
						nomeO = nomeOperaioField.getText();
						cognomeO = cognomeOperaioField.getText();
						//Dalle info precedentemente salvate vado a cercare un cantiere avente il caposquadra con il nome e 
						//il cognome del caposquadra inserito
						for(Cantiere c: operativo.getCantieri()) {
							if(c.getSquadra().getCapoSquadra().getNome().equalsIgnoreCase(nomeC) &&
								c.getSquadra().getCapoSquadra().getCognome().equalsIgnoreCase(cognomeC)) {
								
								can = c;
								break;
							}
						}
						//vado a cercare nella lista del personale un operaio con nome e cognome dell' operaio inserito
						for(Personale p: amministrativo.getPersonale()) {
							if(p.getClass().getName().equals("amministrativo.Operaio")) {
								if(p.getNome().equalsIgnoreCase(nomeO) && p.getCognome().equalsIgnoreCase(cognomeO)) {
									operaioS = (Operaio) p;
									break;
								}
							}
						}
						//controllo se il cantiere con il caposquadra scelto è stato trovato
						//se è stato trovato aggiungo operaio e stampo le info di tutto il cantiere
						//con squadra annessa
						if(can != null) {
							can.getSquadra().AggiungiOperaio(operaioS);
							textArea.append("Operaio aggiunto alla squadra del seguente cantiere:\n"
										+"Valore: "+can.getValore()+"€\n"
										+"Responsabile: "+can.getResponsabile().getNome()+" "+can.getResponsabile().getCognome()+"\n"
										+"Capo squadra: "+can.getSquadra().getCapoSquadra().getNome()+" "+can.getSquadra().getCapoSquadra().getCognome()+"\n"
										+"Operai in squadra: ");
							for(Operaio o: can.getSquadra().getOperai()) {
								textArea.append(o.getNome()+" "+o.getCognome()+" "+o.getMansione()+" ");
							}
							textArea.append("\n\n");
						}
						else
							throw new ArgomentoNonValidoException("Squadra: Nessun cantiere con il capo squadra scelto è stato trovato!");
					
						//setto tutte le JTField per "cancellare" ciò che è stato digitato
						//e disabilito tutte le componenti del bordo squadra
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
					catch(ArgomentoNonValidoException nu) {
						textArea.append(nu.getMessage()+"\n");
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
					catch(PersonaleImpegnatoException pe) {
						textArea.append(pe.getMessage()+"\n");
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
					catch(StringIndexOutOfBoundsException se) {
						textArea.append("Squadra: Inserire parametri validi!\n");
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
					
				}
			}
		}
		//creo il ricevitore e lo associo al bottone aggiungi
		listener = new AggiungiListener();
		aggiungi.addActionListener(listener);
		
		
		return aggiungi;
	}
	//Bordo Fornitori...contiene tutte le componenti che riguardano i fornitori
	public JPanel creaBordoFornitori() {
		//Jlabel
		nomeForn = new JLabel("Nome");
		servizioForn = new JLabel("Servizio");
		//JTField
		nomeFornField = new JTextField(FIELD_WIDTH);
		servizioFornField = new JTextField(FIELD_WIDTH);
		//disabilito le componenti
		nomeFornField.setEnabled(false);
		servizioFornField.setEnabled(false);
		nomeForn.setEnabled(false);
		servizioForn.setEnabled(false);
		//creo il pannello setto il bordo fornitori e aggiungo tutte le componenti
		JPanel fornitori = new JPanel();
		fornitori.setLayout(new GridLayout(8, 1));
		fornitori.add(nomeForn);
		fornitori.add(nomeFornField);
		fornitori.add(new JLabel());
		fornitori.add(new JLabel());
		fornitori.add(servizioForn);
		fornitori.add(servizioFornField);
		fornitori.add(new JLabel());
		fornitori.add(new JLabel());
		fornitori.setBorder(new TitledBorder(new EtchedBorder(), "Fornitori"));
		
		return fornitori;
	}
	//Bottone Rimuovi
	//Questo bottone è condiviso tra tutte le componenti dell'impresa e quando si genererà l'evento esso agirà sulla componente abilitata ignorando le altre
	public JButton creaBottoneRimuovi() {
		//creo il bottone rimuovi
		rimuovi = new JButton("Rimuovi");
		 //Classe interna che crea il tipo ricevitore "RimuoviListener" che creerà un ricevitore che verrà associato al bottone rimuovi
		class RimuoviListener implements ActionListener{
			//Sovrascrivo
			public void actionPerformed(ActionEvent e) {
				//Controllo se almeno una componente del bordo personale è abilitata, se è abilitata allora si andrà ad agire su Personale
				//altrimenti ignoro
				if(personaleCombo.isEnabled()) {
					//Controllo su quale tipo di personale si deve agire prendendo il riferimento dall'item selezionato da personaleCombo...e così per tutti
					//i tipi di personale
					if(personaleCombo.getSelectedItem().equals("Operaio")) {
						//Operaio è l'item selezionato
						//try/catch che cattura le opportune eccezioni
						try {
							String nOp = nomeField.getText(),
									coOp =  cognomeField.getText(),
									mOp = mansioneField.getText();
							int rOp = Integer.parseInt(retribuzioneField.getText());
							Operaio oper = null;
							//vado a prelevare l'operaio richiesto dalla lista del personale
							for(Personale p: amministrativo.getPersonale()) {
								if(p.getClass().getName().equals("amministrativo.Operaio")) {
									Operaio o = (Operaio) p;
									if(o.getNome().equalsIgnoreCase(nOp) && o.getCognome().equalsIgnoreCase(coOp) && o.getRetribuzione() == rOp && o.getMansione().equalsIgnoreCase(mOp)) {
										oper = o;
										break;
									}
								}
							}
							//controllo se l'operaio che si vuole rimuovere risulta essere impegnato
							//su un cantiere, se lo è allora si rimuove anche dalla squadra di cantiere
							for(Cantiere c: operativo.getCantieri()) {
								if(c.getSquadra().isOperaioInSquadra(oper)) {
									c.getSquadra().removeOperaio(oper);
									break;
								}
							}
							//rimuovo l'operaio e controllo se ciò è avvenuto
							//se è stato rimosso stampo sull'area di testo l'avvenuta eliminazione
							//altrimenti stampo il messaggio dell'else
							if(amministrativo.licenziaPersonale(oper)) {
								textArea.append("L'operaio "+nomeField.getText()+" "+cognomeField.getText()+" è stato rimosso.\n");
							}
							else {
								textArea.append("L'operaio non può essere rimosso. Non risulta essere un dipendente dell'impresa!");
							}
							//setto le jtfield interessate e disabilito
							//le componenti interessate
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
						catch(NumberFormatException nu) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
						catch(ArgomentoNonValidoException ne) {
							textArea.append(ne.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
						catch(EmptyListException ee) {
							textArea.append(ee.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
					}
					else if(personaleCombo.getSelectedItem().equals("Impiegato")) {
						//Impiegato è l'item selezionato
						//try/catch che cattura le opportune eccezioni
						try {
							String nIm = nomeField.getText(),
									cgIm = cognomeField.getText(),
									inIm = incaricoField.getText();
							int rIm = Integer.parseInt(retribuzioneField.getText());
							Impiegato imp = null;
							//vado a prelevare l'impiegato richiesto dalla lista del personale
							for(Personale p: amministrativo.getPersonale()) {
								if(p.getClass().getName().equals("amministrativo.Impiegato")) {
									Impiegato i = (Impiegato) p;
									if(i.getNome().equalsIgnoreCase(nIm) && i.getCognome().equalsIgnoreCase(cgIm) && i.getRetribuzione() == rIm && i.getIncarico().equalsIgnoreCase(inIm)) {
										imp = i;
										break;
									}	
								}
							}
							//rimuovo l'impiegato e controllo se ciò è avvenuto
							//se è stato rimosso stampo sull'area di testo l'avvenuta eliminazione
							//altrimenti stampo il messaggio dell'else
							if(amministrativo.licenziaPersonale(imp))
								textArea.append("L'impiegato "+nomeField.getText()+" "+cognomeField.getText()+" è stato rimosso.\n");
							else
								textArea.append("L'impiegato non può essere rimosso poichè non è un dipendendte dell'impresa!");
							//setto le jtfield interessate e disabilito
							//le componenti interessate
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
						catch(NumberFormatException nu) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
						catch(ArgomentoNonValidoException ne) {
							textArea.append(ne.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
						catch(EmptyListException ee) {
							textArea.append(ee.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
					}
					else if(personaleCombo.getSelectedItem().equals("Quadro")) {
						//Quadro è l'item selezionato
						//try/catch che cattura le opportune eccezioni
						try {
							String qNo = nomeField.getText(),
								   qCg = cognomeField.getText(),
							  	   qQua = qualificaField.getText(),
							  	   qLiv = livelloField.getText();
							double qRet = Double.parseDouble(retribuzioneField.getText());
							Quadro quad = null;
							//vado a prelevare il quadro richiesto dalla lista del personale
							for(Personale p: amministrativo.getPersonale()) {
								if(p.getClass().getName().equals("amministrativo.Quadro")) {
									Quadro q = (Quadro)p;
									if(q.getNome().equalsIgnoreCase(qNo) && q.getCognome().equalsIgnoreCase(qCg) && Math.abs(q.getRetribuzione()-qRet)<=Math.pow(10, -14) && q.getQualifica().equalsIgnoreCase(qQua)
											&& q.getLivello().equalsIgnoreCase(qLiv)) {
										quad = q;
										break;
									}
								}
							}
							//controllo se il quadro scelto è un responsabile o un caposquadra
							//se è almeno una delle due bisogna prima cambiare responsabile al cantiere o
							//cambiare caposquadra alla squadra e poi rimuovere il quadro
							boolean trovato = false;
							for(Cantiere c: operativo.getCantieri()) {	
								if(c.getValore() < 500000) {
									if(c.getResponsabile().equals(quad)) {
										textArea.append("Non puoi eliminare questo quadro perchè risulta essere un responsabile di cantiere!\n"
														+ "Per rimuoverlo cambia responsabile al cantiere.\n");
										trovato = true;
										break;	
									}
								}
								if(c.getSquadra().getCapoSquadra().equals(quad)){
									textArea.append("Non puoi eliminare questo quadro perchè risulta essere un capo squadra di cantiere!"
											+ "Per rimuoverlo cambia capo squadra al cantiere.\n");
									trovato = true;
									break;
								}
							}
							//se il quadro non è ne responsabile ne caposquadra si procede ad eliminarlo
							if(!trovato) {
								//se la rimozione è avvenuta con successo stampa il messaggio
								//altrimenti stampa messaggio else
								if(amministrativo.licenziaPersonale(quad))
									textArea.append("Il quadro "+nomeField.getText()+" "+cognomeField.getText()+" è stato rimosso.\n");
								else
									textArea.append("Il quadro non può essere eliminato poichè non è un dipendente dell'impresa!");
							}
							//setto le jtfield interessate e disabilito
							//le componenti interessate
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
						catch(NumberFormatException nu) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
						catch(ArgomentoNonValidoException ne) {
							textArea.append(ne.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
						catch(EmptyListException ee) {
							textArea.append(ee.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
					}
					else if(personaleCombo.getSelectedItem().equals("Dirigente")) {
						//Dirigente è l'item selezionato
						//try/catch che cattura le opportune eccezioni
						try {
							String  dNo = nomeField.getText(),
									dCgn = cognomeField.getText(),
									dAr = areaField.getText();
							int dRet = Integer.parseInt(retribuzioneField.getText());
							Dirigente dir = null;
							//vado a prelevare il dirigente richiesto dalla lista del personale
							for(Personale p: amministrativo.getPersonale()) {
								if(p.getClass().getName().equals("amministrativo.Dirigente")) {
									Dirigente d = (Dirigente) p;
									if(d.getNome().equalsIgnoreCase(dNo) && d.getCognome().equalsIgnoreCase(dCgn) && d.getRetribuzione() == dRet && d.getAreaCompetenza().equalsIgnoreCase(dAr)) {
										dir = d;
										break;
									}
								}
							}
							//controllo se il dirigente è un responsabile
							boolean trovato = false;
							for(Cantiere c: operativo.getCantieri()) {
								if(c.getValore() >= 500000) {
									if(c.getResponsabile().equals(dir)) {
										textArea.append("Non puoi eliminare questo dirigente perchè risulta essere un responsabile di cantiere!"
												+ "Per rimuoverlo cambia responsabile al cantiere.\n");
										trovato = true;
										break;
									}
								}
							}
							//se non è un responsabile si procede con l'eliminazione
							if(!trovato) {
								//se la rimozione è avvenuta con successo stampo il messaggio
								//altrimenti stampa messaggio else
								if(amministrativo.licenziaPersonale(dir))
									textArea.append("Il dirigente "+nomeField.getText()+" "+cognomeField.getText()+" è stato rimosso.\n");
								else
									textArea.append("Il dirigente non può essere rimosso poichè non risulta essere un dipendente dell'impresa\n");
						
							}
							//setto le jtfield interessate e disabilito
							//le componenti interessate
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
						catch(NumberFormatException nu) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
						catch(ArgomentoNonValidoException ne) {
							textArea.append(ne.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
						catch(EmptyListException ee) {
							textArea.append(ee.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
					}
					//disabilito componenti personale
					personaleCombo.setEnabled(false);
					nome.setEnabled(false);
					nomeField.setEnabled(false);
					cognome.setEnabled(false);
					cognomeField.setEnabled(false);
					retribuzione.setEnabled(false);
					retribuzioneField.setEnabled(false);
				}
				//se le componenti di fornitori sono abilitate si agisce su bordo fornitori altrimenti la ignora
				else if(nomeFornField.isEnabled() && servizioFornField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//fornitore interessato
						Fornitori f = new Fornitori(nomeFornField.getText(), servizioFornField.getText());
						//rimuovo e controllo se ciò è avvenuto
						if(amministrativo.rimuoviFornitore(f))
							textArea.append("Il fornitore "+nomeFornField.getText()+" che rifornisce "+servizioFornField.getText()+" è stato rimosso.\n");
						else
							textArea.append("Il fornitore non può essere rimosso poichè non fa parte dei fornitori dell'impresa!\n");
						//setto e disabilito le componenti di fornitori
						nomeFornField.setText(null);
						servizioFornField.setText(null);
						nomeFornField.setEnabled(false);
						servizioFornField.setEnabled(false);
						nomeForn.setEnabled(false);
						servizioForn.setEnabled(false);
					}
					catch(ArgomentoNonValidoException ne) {
						textArea.append(ne.getMessage()+"\n");
						nomeFornField.setText(null);
						servizioFornField.setText(null);
						nomeFornField.setEnabled(false);
						servizioFornField.setEnabled(false);
						nomeForn.setEnabled(false);
						servizioForn.setEnabled(false);
					}
				}
				//se le componenti di enti sono abilitate si agisce su bordo enti altrimenti la ignora
				else if(nomeEnteField.isEnabled() && luogoEnteField.isEnabled() && indirizzoEnteField.isEnabled() && telefonoEnteField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//ente interessato
						EntiLocali ent = new EntiLocali(nomeEnteField.getText(), luogoEnteField.getText(), indirizzoEnteField.getText(), telefonoEnteField.getText());
						//rimuovo e controllo se cio è avvenuto
						if(amministrativo.rimuoviEnte(ent))
							textArea.append("L' ente "+nomeEnteField.getText()+" è stato rimosso.\n");
						else
							textArea.append("L' ente non può essere rimosso poichè non fa parte degli enti dell'impresa.\n");
						//setto e disabilito le componenti di enti
						nomeEnteField.setText(null);
						luogoEnteField.setText(null);
						indirizzoEnteField.setText(null);
						telefonoEnteField.setText(null);
						nomeEnteField.setEnabled(false);
						luogoEnteField.setEnabled(false);
						indirizzoEnteField.setEnabled(false);
						telefonoEnteField.setEnabled(false);
						nomeEnte.setEnabled(false);
						luogoEnte.setEnabled(false);
						indirizzoEnte.setEnabled(false);
						telefonoEnte.setEnabled(false);
					}
					catch(ArgomentoNonValidoException ne) {
						textArea.append(ne.getMessage()+"\n");
						nomeEnteField.setText(null);
						luogoEnteField.setText(null);
						indirizzoEnteField.setText(null);
						telefonoEnteField.setText(null);
						nomeEnteField.setEnabled(false);
						luogoEnteField.setEnabled(false);
						indirizzoEnteField.setEnabled(false);
						telefonoEnteField.setEnabled(false);
						nomeEnte.setEnabled(false);
						luogoEnte.setEnabled(false);
						indirizzoEnte.setEnabled(false);
						telefonoEnte.setEnabled(false);
					}
				}
				//se almeno una componente di clienti è abilitata si agisce su bordo enti altrimenti la ignora
				else if(nomeClienteField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//ente interessato
						Clienti cli = new Clienti(nomeClienteField.getText(), cognomeClienteField.getText(), indirizzoClienteField.getText(), telefonoClienteField.getText());
						//rimuovo ente e controllo se ciò è stato atto
						if(amministrativo.rimuoviCliente(cli))
							textArea.append("Il cliente "+nomeClienteField.getText()+" "+cognomeClienteField.getText()+" è stato rimosso.\n");
						else
							textArea.append("Il cliente non può essere rimosso poichè nmon è un cliente dell'impresa.\n");
						//setto e disabilito le componenti del bordo cliente
						nomeClienteField.setText(null);
						cognomeClienteField.setText(null);
						indirizzoClienteField.setText(null);
						telefonoClienteField.setText(null);
						nomeClienteField.setEnabled(false); 
						cognomeClienteField.setEnabled(false);
						indirizzoClienteField.setEnabled(false);
						telefonoClienteField.setEnabled(false);
						nomeCliente.setEnabled(false); 
						cognomeCliente.setEnabled(false);
						indirizzoCliente.setEnabled(false);
						telefonoCliente.setEnabled(false);
					}
					catch(ArgomentoNonValidoException ne) {
						textArea.append(ne.getMessage()+"\n");
						nomeClienteField.setText(null);
						cognomeClienteField.setText(null);
						indirizzoClienteField.setText(null);
						telefonoClienteField.setText(null);
						nomeClienteField.setEnabled(false); 
						cognomeClienteField.setEnabled(false);
						indirizzoClienteField.setEnabled(false);
						telefonoClienteField.setEnabled(false);
						nomeCliente.setEnabled(false); 
						cognomeCliente.setEnabled(false);
						indirizzoCliente.setEnabled(false);
						telefonoCliente.setEnabled(false);
					}
				}
				//se almeno una componente di cantieri è abilitata si agisce su bordo cantieri altrimenti la ignora
				else if(valoreField.isEnabled()) {
					Personale respon = null;
					Quadro capoS = null;
					String nomeR,cognomeR,resp,capo,nomeC,cognomeC;
					Cantiere c = null;
					int val,index;
					//try/catch per catturare opportune eccezioni
					try {
						//vado a recuperare tutte le informazioni dalle JTField salvandole nelle
						//rispettive variabili prima dichiarate
						val = Integer.parseInt(valoreField.getText());
						resp = responsabileField.getText();
						nomeR = resp.substring(0, resp.indexOf(' '));
						cognomeR = resp.substring(resp.indexOf(' ')+1);

						capo = capoSquadraField.getText();
						nomeC = capo.substring(0, capo.indexOf(' '));
						cognomeC = capo.substring(capo.indexOf(' ')+1);
						//Dalle info precedentemente salvate mi vado a cercare se nel personale risulta un dipendente con il nome e 
						//il cognome del candidato responsabile e lo stesso faccio per il candidato caposquadra
						for(Personale p: amministrativo.getPersonale()) {
							if(p.getNome().equalsIgnoreCase(nomeR) && p.getCognome().equalsIgnoreCase(cognomeR))
								respon = p;
							if(p.getClass().getName().equals("amministrativo.Quadro"))
								if(p.getNome().equalsIgnoreCase(nomeC) && p.getCognome().equalsIgnoreCase(cognomeC))
									capoS = (Quadro)p;
						}
						//vado a cercare il cantiere richiesto nella lista cantieri
						index = operativo.cercaCantiere(new Cantiere(val, respon,new Squadra(capoS)));	
						//controllo se il cantiere è stato trovato
						if(index != -1) {
							//prelevo il cantiere trovato
							c = operativo.getCantieri().get(index);
							//elimino il cantiere settando lo stato di impegnato del quadro caposquadra e di tutti
							//gli operai impegnati per il cantiere e stampo il messaggio di avvenuta cancellazione
							if(operativo.rimuoviCantiere(c)) {
								capoS.setImpegnato(false);
								if(!c.getSquadra().getOperai().isEmpty()) {
									for(Personale p: amministrativo.getPersonale()) {
										if(p.getClass().getName().equals("amministrativo.Operaio")) {
											Operaio o = (Operaio)p;
											if(c.getSquadra().isOperaioInSquadra(o))
												o.setImpegnato(false);
										}
									}
								}
								textArea.append("E' stato rimosso il seguente cantiere:\n"
												+ "Valore: "+c.getValore()+"€\n"
												+ "Responsabile: "+c.getResponsabile().getNome()+" "+c.getResponsabile().getCognome()+"\n"
												+ "Capo squadra: "+c.getSquadra().getCapoSquadra().getNome()+" "+c.getSquadra().getCapoSquadra().getCognome()+"\n");
							}
						}
						else
							textArea.append("Impossibile rimuovere. Cantiere inesistente!\n");
					
						//setto e disabilito le componenti
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(NumberFormatException nu) {
						textArea.append("Cantiere: Inserire parametri validi!\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ValueNegativeException ve) {
						textArea.append(ve.getMessage()+"\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ResponsabileNotDirigenteException re) {
						textArea.append(re.getMessage()+"\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ResponsabileNotQuadroException qe) {
						textArea.append(qe.getMessage()+"\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ArgomentoNonValidoException nu) {
						textArea.append(nu.getMessage()+"\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(StringIndexOutOfBoundsException se) {
						textArea.append("Cantiere: Inserire parametri validi!\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
				}
				//se almeno una componente di squadra è abilitata agisco altrimenti la ignoro
				else if(caposquadraField.isEnabled()) {
					String capo,nomeC,cognomeC,nomeO,cognomeO;
					Quadro capoS = null;
					Operaio operaioS = null;
					Cantiere can = null;
					//try/catch per catturare le possibili eccezioni
					try {
						//vado a recuperare tutte le informazioni dalle JTField salvandole nelle
						//rispettive variabili prima dichiarate
						capo = caposquadraField.getText();
						nomeC = capo.substring(0, capo.indexOf(' '));
						cognomeC = capo.substring(capo.indexOf(' ')+1);
						
						nomeO = nomeOperaioField.getText();
						cognomeO = cognomeOperaioField.getText();
						//Dalle info precedentemente salvate vado a cercare un cantiere avente il caposquadra con il nome e 
						//il cognome del caposquadra inserito
						for(Cantiere c: operativo.getCantieri()) {
							if(c.getSquadra().getCapoSquadra().getNome().equalsIgnoreCase(nomeC) &&
								c.getSquadra().getCapoSquadra().getCognome().equalsIgnoreCase(cognomeC)) {
								
								can = c;
								break;
							}
						}	
						//vado a cercare nella lista del personale un operaio con nome e cognome dell' operaio inserito
						for(Personale p: amministrativo.getPersonale()) {
							if(p.getClass().getName().equals("amministrativo.Operaio")) {
								if(p.getNome().equalsIgnoreCase(nomeO) && p.getCognome().equalsIgnoreCase(cognomeO)) {
									operaioS = (Operaio) p;
									break;
								}
							}
						}
						//controllo se il cantiere con il caposquadra scelto è stato trovato
						//se è stato trovato rimuovo operaio e stampo le info di tutto il cantiere
						//con squadra annessa
						if(can != null) {
							if(can.getSquadra().isOperaioInSquadra(operaioS)) {
								can.getSquadra().removeOperaio(operaioS);
								textArea.append("Operaio rimosso dalla squadra del seguente cantiere:\n"
												+ "Valore: "+can.getValore()+"€\n"
												+ "Responsabile: "+can.getResponsabile().getNome()+" "+can.getResponsabile().getCognome()+"\n"
												+ "Capo squadra: "+can.getSquadra().getCapoSquadra().getNome()+" "+can.getSquadra().getCapoSquadra().getCognome()+"\n"
												+"Operai in squadra: ");
								for(Operaio o: can.getSquadra().getOperai()) {
									textArea.append(o.getNome()+" "+o.getCognome()+" "+o.getMansione()+" ");
								}
								textArea.append("\n\n");
							}
							else
								textArea.append("Non puoi rimuovere questo operaio poichè non è in questa squadra!\n");
						}
						else
							throw new ArgomentoNonValidoException("Squadra: Nessun cantiere con il capo squadra scelto è stato trovato!");
						//setto e disabilito le componenti di squadra
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
					catch(ArgomentoNonValidoException nu) {
						textArea.append(nu.getMessage()+"\n");
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
					catch(StringIndexOutOfBoundsException se) {
						textArea.append("Squadra: Inserire parametri validi!\n");
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
				}
			}
		}
		//creo il ricevitore e lo associo al bottone rimuovi
		listener = new RimuoviListener();
		rimuovi.addActionListener(listener);
		
		return rimuovi;
	}
	//Bordo ente....contiene tutte le componenti che rappresentano un ente locale
	public JPanel creaBordoEnte() {
		//JLabel
		nomeEnte = new JLabel("Nome");
		luogoEnte = new JLabel("Regione");
		indirizzoEnte = new JLabel("Indirizzo");
		telefonoEnte = new JLabel("Recapito telefonico");
		//JTField
		nomeEnteField = new JTextField(FIELD_WIDTH);
		luogoEnteField = new JTextField(FIELD_WIDTH);
		indirizzoEnteField = new JTextField(FIELD_WIDTH);
		telefonoEnteField = new JTextField(FIELD_WIDTH);
		//disabilito le componenti
		nomeEnteField.setEnabled(false);
		luogoEnteField.setEnabled(false);
		indirizzoEnteField.setEnabled(false);
		telefonoEnteField.setEnabled(false);
		nomeEnte.setEnabled(false);
		luogoEnte.setEnabled(false);
		indirizzoEnte.setEnabled(false);
		telefonoEnte.setEnabled(false);
		//creo il pannello ente settando il bordo e inserendo tutte le componenti
		JPanel ente = new JPanel();
		ente.setLayout(new GridLayout(10, 2));
		ente.add(nomeEnte);
		ente.add(nomeEnteField);
		ente.add(new JLabel());
		ente.add(new JLabel());
		ente.add(new JLabel());
		ente.add(new JLabel());
		ente.add(luogoEnte);
		ente.add(luogoEnteField);
		ente.add(new JLabel());
		ente.add(new JLabel());
		ente.add(new JLabel());
		ente.add(new JLabel());
		ente.add(indirizzoEnte);
		ente.add(indirizzoEnteField);
		ente.add(new JLabel());
		ente.add(new JLabel());
		ente.add(new JLabel());
		ente.add(new JLabel());
		ente.add(telefonoEnte);
		ente.add(telefonoEnteField);
		ente.setBorder(new TitledBorder(new EtchedBorder(), "Enti locali"));
		
		return ente;
	}
	//Bottone cerca
	//Questo bottone è condiviso con tutte le componenti dell'impresa e quando verrà creato l'evento si agirà
	//solo sulle componenti abilitate ignorando il resto
	public JButton creaBottoneCerca() {
		//creo il bottone cerca
		cerca = new JButton("Cerca");
		//classe interna per il tipo ricevitore CercaListener che creerà un ricevitore che verrà associato al bottone cerca
		class CercaListener implements ActionListener{
			//sovrascrivo
			public void actionPerformed(ActionEvent e) {
				//controllo quale componente bordo è abilitata
				if(personaleCombo.isEnabled()) {
					//controllo quale tipo personale e selezionato
					if(personaleCombo.getSelectedItem().equals("Operaio")) {
						//try/catch per catturare opportine eccezioni
						try {
							//assegno alle variabili tutti i valori delle JTField
							//Ad oper assegno improbabili valori per evitare oggetto null
							String nOp = nomeField.getText(),
									coOp =  cognomeField.getText(),
									mOp = mansioneField.getText();
							int rOp = Integer.parseInt(retribuzioneField.getText());
							Operaio oper = new Operaio("aa", "aa", 800, new Conto(), "aa");
							//prelevo l'operaio scelto dalla lista del personale
							for(Personale p: amministrativo.getPersonale()) {
								if(p.getClass().getName().equals("amministrativo.Operaio")) {
									Operaio o = (Operaio) p;
									if(o.getNome().equalsIgnoreCase(nOp) && o.getCognome().equalsIgnoreCase(coOp) && o.getRetribuzione() == rOp && o.getMansione().equalsIgnoreCase(mOp)) {
										oper = o;
										break;
									}
								}
							}
							//controllo se è un dipendente
							if(amministrativo.isPersonale(oper))
								textArea.append("L' operaio cercato è un dipendente di quest'impresa."+'\n');
							else
								textArea.append("L' operaio cercato non è un dipendente di quest'impresa."+'\n');
							//setto e disabilito tutte le componenti di operaio
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
						catch(NumberFormatException ne) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
						catch(ArgomentoNonValidoException nu){
							textArea.append(nu.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							mansioneField.setText(null);
							mansioneField.setEnabled(false);
							mansione.setEnabled(false);
						}
					
					}
					else if(personaleCombo.getSelectedItem().equals("Impiegato")) {
						//try/catch per catturare tutte le oppportune eccezioni
						try {
							//assegno alle variabili i rispettivi valori delle JTField
							//ad imp assegno improbabili valori per evitare null
							String nIm = nomeField.getText(),
									cgIm = cognomeField.getText(),
									inIm = incaricoField.getText();
							int rIm = Integer.parseInt(retribuzioneField.getText());
							Impiegato imp = new Impiegato("aa", "aa", 1200, new Conto(), "aa");
							//prelevo impiegato dalla lista del personale
							for(Personale p: amministrativo.getPersonale()) {
								if(p.getClass().getName().equals("amministrativo.Impiegato")) {
									Impiegato i = (Impiegato) p;
									if(i.getNome().equalsIgnoreCase(nIm) && i.getCognome().equalsIgnoreCase(cgIm) && i.getRetribuzione() == rIm && i.getIncarico().equalsIgnoreCase(inIm)) {
										imp = i;
										break;
									}		
								}
							}
							//controllo se è un dipendente
							if(amministrativo.isPersonale(imp))
								textArea.append("L' impiegato cercato è un dipendente di quest'impresa."+'\n');
							else
								textArea.append("L' impiegato cercato non è un dipendente di quest'impresa."+'\n');
						
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
						catch(NumberFormatException ne) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
						catch(ArgomentoNonValidoException nu){
							textArea.append(nu.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							incaricoField.setText(null);
							incaricoField.setEnabled(false);
							incarico.setEnabled(false);
						}
					}
					else if(personaleCombo.getSelectedItem().equals("Quadro")) {
						//try/catch per catturare le opportune eccezioni
						try {
							//assegno alle variabili i rispettivi valori delle JTField
							//a quad assegno improbabili valori per evitare il null
							String qNo = nomeField.getText(),
								   qCg = cognomeField.getText(),
								   qQua = qualificaField.getText(),
								   qLiv = livelloField.getText();
							int qRet = Integer.parseInt(retribuzioneField.getText());
							Quadro quad = new Quadro("aa", "aa", 1500, new Conto(), "aa", "aa");
							//prelevo il quadro dalla lista personale	
							for(Personale p: amministrativo.getPersonale()) {
								if(p.getClass().getName().equals("amministrativo.Quadro")) {
									Quadro q = (Quadro)p;
									if(q.getNome().equalsIgnoreCase(qNo) && q.getCognome().equalsIgnoreCase(qCg) && q.getRetribuzione() == qRet && q.getQualifica().equalsIgnoreCase(qQua)
										&& q.getLivello().equalsIgnoreCase(qLiv)) {
										quad = q;
										break;
									}
								}
							}
							//controllo se è un dipendente
							if(amministrativo.isPersonale(quad))
								textArea.append("Il quadro cercato è un dipendente di quest'impresa."+'\n');
							else
								textArea.append("Il quadro cercato non è un dipendente di quest'impresa."+'\n');
							//setto e diasabilito le componenti di quadro
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
						catch(NumberFormatException ne) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
						catch(ArgomentoNonValidoException nu){
							textArea.append(nu.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							qualificaField.setText(null);
							livelloField.setText(null);
							qualificaField.setEnabled(false);
							livelloField.setEnabled(false);
							qualifica.setEnabled(false);
							livello.setEnabled(false);
						}
					}
					else if(personaleCombo.getSelectedItem().equals("Dirigente")) {
						//try/catch per catturare le opportune eccezioni
						try {
							//assegno alle variabili i rispettivi valori delle JTField 
							String dNo = nomeField.getText(),
								   dCgn = cognomeField.getText(),
								   dAr = areaField.getText();
							int dRet = Integer.parseInt(retribuzioneField.getText());
							Dirigente dir = new Dirigente("aa", "aa", 2000, new Conto(), "aa");
							//prelevo il dirigente interessato	
							for(Personale p: amministrativo.getPersonale()) {
								if(p.getClass().getName().equals("amministrativo.Dirigente")) {
									Dirigente d = (Dirigente) p;
									if(d.getNome().equalsIgnoreCase(dNo) && d.getCognome().equalsIgnoreCase(dCgn) && d.getRetribuzione() == dRet && d.getAreaCompetenza().equalsIgnoreCase(dAr)) {
										dir = d;
										break;
									}
								}
							}
							//controlo se è un dipendente
							if(amministrativo.isPersonale(dir))
								textArea.append("Il dirigente cercato è un dipendente di quest'impresa."+'\n');
							else
								textArea.append("Il dirigente cercato non è un dipendente di quest'impresa."+'\n');
							//setto e disabilito le componenti di dirigente
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
						catch(NumberFormatException ne) {
							textArea.append("Personale: Inserire parametri validi!\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
						catch(ArgomentoNonValidoException nu){
							textArea.append(nu.getMessage()+"\n");
							nomeField.setText(null);
							cognomeField.setText(null);
							retribuzioneField.setText(null);
							areaField.setText(null);
							areaField.setEnabled(false);
							area.setEnabled(false);
						}
					}
					//disabilito le componenti di personale
					personaleCombo.setEnabled(false);
					nome.setEnabled(false);
					nomeField.setEnabled(false);
					cognome.setEnabled(false);
					cognomeField.setEnabled(false);
					retribuzione.setEnabled(false);
					retribuzioneField.setEnabled(false);
				}
				else if(nomeFornField.isEnabled() && servizioFornField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//controllo se è un fornitore dell'impresa.
						if(amministrativo.isFornitore(new Fornitori(nomeFornField.getText(), servizioFornField.getText())))
							textArea.append("Il fornitore cercato è un fornitore dell'impresa.\n");
						else
							textArea.append("Il fornitore cercato non è un fornitore dell'impresa.\n");
						//setto e disabilito le componenti di fornitori
						nomeFornField.setText(null);
						servizioFornField.setText(null);
						nomeFornField.setEnabled(false);
						servizioFornField.setEnabled(false);
						nomeForn.setEnabled(false);
						servizioForn.setEnabled(false);
					}
					catch(ArgomentoNonValidoException nu) {
						textArea.append(nu.getMessage()+"\n");
						nomeFornField.setText(null);
						servizioFornField.setText(null);
						nomeFornField.setEnabled(false);
						servizioFornField.setEnabled(false);
						nomeForn.setEnabled(false);
						servizioForn.setEnabled(false);
					}
				}
				else if(nomeEnteField.isEnabled() && luogoEnteField.isEnabled() && indirizzoEnteField.isEnabled() && telefonoEnteField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//controllo se è un ente dell'impresa
						if(amministrativo.isEnte(new EntiLocali(nomeEnteField.getText(), luogoEnteField.getText(), indirizzoEnteField.getText(), telefonoEnteField.getText())))
							textArea.append("L' ente cercato è un cliente dell'impresa.\n");
						else
							textArea.append("L' ente cercato non è un cliente dell'impresa.\n");
						//setto e disabilito le componenti di ente
						nomeEnteField.setText(null);
						luogoEnteField.setText(null);
						indirizzoEnteField.setText(null);
						telefonoEnteField.setText(null);
						nomeEnteField.setEnabled(false);
						luogoEnteField.setEnabled(false);
						indirizzoEnteField.setEnabled(false);
						telefonoEnteField.setEnabled(false);
						nomeEnte.setEnabled(false);
						luogoEnte.setEnabled(false);
						indirizzoEnte.setEnabled(false);
						telefonoEnte.setEnabled(false);
					}
					catch(ArgomentoNonValidoException nu) {
						textArea.append(nu.getMessage()+"\n");
						nomeEnteField.setText(null);
						luogoEnteField.setText(null);
						indirizzoEnteField.setText(null);
						telefonoEnteField.setText(null);
						nomeEnteField.setEnabled(false);
						luogoEnteField.setEnabled(false);
						indirizzoEnteField.setEnabled(false);
						telefonoEnteField.setEnabled(false);
						nomeEnte.setEnabled(false);
						luogoEnte.setEnabled(false);
						indirizzoEnte.setEnabled(false);
						telefonoEnte.setEnabled(false);
					}
				}
				else if(nomeClienteField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//controllo se è un clientye dell'impresa
						if(amministrativo.isCliente(new Clienti(nomeClienteField.getText(), cognomeClienteField.getText(), indirizzoClienteField.getText(), telefonoClienteField.getText())))
							textArea.append("Il cliente cercato è un cliente dell'impresa.\n");
						else
							textArea.append("Il cliente cercato non è un cliente dell'impresa.\n");
						//setto e disabilito le componenti di cliente
						nomeClienteField.setText(null); 
						cognomeClienteField.setText(null);
						indirizzoClienteField.setText(null);
						telefonoClienteField.setText(null);
						nomeClienteField.setEnabled(false); 
						cognomeClienteField.setEnabled(false);
						indirizzoClienteField.setEnabled(false);
						telefonoClienteField.setEnabled(false);
						nomeCliente.setEnabled(false); 
						cognomeCliente.setEnabled(false);
						indirizzoCliente.setEnabled(false);
						telefonoCliente.setEnabled(false);
					}
					catch(ArgomentoNonValidoException nu) {
						textArea.append(nu.getMessage()+"\n");
						nomeClienteField.setText(null); 
						cognomeClienteField.setText(null);
						indirizzoClienteField.setText(null);
						telefonoClienteField.setText(null);
						nomeClienteField.setEnabled(false); 
						cognomeClienteField.setEnabled(false);
						indirizzoClienteField.setEnabled(false);
						telefonoClienteField.setEnabled(false);
						nomeCliente.setEnabled(false); 
						cognomeCliente.setEnabled(false);
						indirizzoCliente.setEnabled(false);
						telefonoCliente.setEnabled(false);
					}
				}
				else if(valoreField.isEnabled()) {
					//try/catch per catturare le opportune eccezioni
					try {
						//assegno alle variabili i rispettivi valori delle JTField
						//a respon e capoS dò improbabili valori per evitare il null
						int val = Integer.parseInt(valoreField.getText());
						Personale respon = null;
						Quadro capoS = new Quadro("aa", "aa", 1500, new Conto(), "aa", "aa");
						String nomeR,cognomeR,resp,capo,nomeC,cognomeC;
						resp = responsabileField.getText();
						nomeR = resp.substring(0, resp.indexOf(' '));
						cognomeR = resp.substring(resp.indexOf(' ')+1);
	
						capo = capoSquadraField.getText();
						nomeC = capo.substring(0, capo.indexOf(' '));
						cognomeC = capo.substring(capo.indexOf(' ')+1);
						//prelevo dalla lista di personale il responsabile e il caposquadra del cantiere richiesti
						for(Personale p: amministrativo.getPersonale()) {
							if(p.getNome().equals(nomeR) && p.getCognome().equals(cognomeR))
								respon = p;
							if(p.getClass().getName().equals("amministrativo.Quadro"))
								if(p.getNome().equals(nomeC) && p.getCognome().equals(cognomeC))
									capoS = (Quadro)p;
						}
						//creo il cantiere richiesto
						Cantiere c = new Cantiere(val, respon,new Squadra(capoS));
						//controllo se il cantiere è un cantiere dell'impresa
						if(operativo.isCantiere(c))
							textArea.append("Il cantiere cercato è presente nella lista.\n");
						else
							textArea.append("Il cantiere cercato non è presente nella lista.\n");
						//setto e disabilito le componenti di cantieri
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(NumberFormatException nu) {
						textArea.append("Cantiere: Inserire parametri validi!\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ValueNegativeException ve) {
						textArea.append(ve.getMessage()+"\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ResponsabileNotDirigenteException re) {
						textArea.append(re.getMessage()+"\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ResponsabileNotQuadroException qe) {
						textArea.append(qe.getMessage()+"\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(ArgomentoNonValidoException nu) {
						textArea.append("Cantiere-"+nu.getMessage()+"\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
					catch(StringIndexOutOfBoundsException se) {
						textArea.append("Cantiere: Inserire parametri validi!\n");
						valoreField.setText(null);
						responsabileField.setText(null);
						capoSquadraField.setText(null);
						valore.setEnabled(false);
						valoreField.setEnabled(false);
						responsabile.setEnabled(false);
						responsabileField.setEnabled(false);
						capoSquadra.setEnabled(false);
						capoSquadraField.setEnabled(false);
					}
				}
				else if(caposquadraField.isEnabled()) {
						String capo,nomeC,cognomeC,nomeO,cognomeO;
						Quadro capoS = null;
						Operaio operaioS = null;
						Cantiere can = null;
					//try/catch per catturare le possibili eccezioni
					try {
						//assegno alle variabili i rispettivi valori delle JTField
						capo = caposquadraField.getText();
						nomeC = capo.substring(0, capo.indexOf(' '));
						cognomeC = capo.substring(capo.indexOf(' ')+1);
						
						nomeO = nomeOperaioField.getText();
						cognomeO = cognomeOperaioField.getText();
						
						//Dalle info precedentemente salvate vado a cercare un cantiere avente il caposquadra con il nome e 
						//il cognome del caposquadra inserito
						for(Cantiere c: operativo.getCantieri()) {
							if(c.getSquadra().getCapoSquadra().getNome().equalsIgnoreCase(nomeC) &&
								c.getSquadra().getCapoSquadra().getCognome().equalsIgnoreCase(cognomeC)) {
								
								can = c;
								break;
							}
						}	
						//vado a cercare nella lista del personale un operaio con nome e cognome dell' operaio inserito
						for(Personale p: amministrativo.getPersonale()) {
							if(p.getClass().getName().equals("amministrativo.Operaio")) {
								if(p.getNome().equalsIgnoreCase(nomeO) && p.getCognome().equalsIgnoreCase(cognomeO)) {
									operaioS = (Operaio) p;
									break;
								}
							}
						}
						//controllo se il cantiere con il caposquadra scelto è stato trovato
						//se è stato trovato cerco operaio in squadra e stampo messaggio
						//altrimenti stampo messaggio else
						if(can != null) {
							if(can.getSquadra().isOperaioInSquadra(operaioS))
								textArea.append("L'operaio è presente in questa squadra.\n");
							else
								textArea.append("L'operaio non è presente in questa squadra.\n");
						}
						else
							throw new ArgomentoNonValidoException("Squadra: Nessun cantiere con il capo squadra scelto è stato trovato!");
						//setto e disabilito le componenti di squadra
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
					catch(ArgomentoNonValidoException nu) {
						textArea.append(nu.getMessage()+"\n");
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
					catch(StringIndexOutOfBoundsException se) {
						textArea.append("Squadra: Inserire parametri validi!\n");
						caposquadraField.setText(null);
						nomeOperaioField.setText(null);
						cognomeOperaioField.setText(null);
						caposquadra.setEnabled(false);
						caposquadraField.setEnabled(false);
						nomeOperaio.setEnabled(false);
						nomeOperaioField.setEnabled(false);
						cognomeOperaio.setEnabled(false);
						cognomeOperaioField.setEnabled(false);
					}
				}
			}
		}
		//creo il ricevitore e lo associo al bottone cerca
		listener = new CercaListener();
		cerca.addActionListener(listener);
		
		return cerca;
	}
	//Bordo clienti....contiene tutte le componenti che rappresentano il cliente
	public JPanel creaBordoClienti() {
		//JLabel
		nomeCliente = new JLabel("Nome");
		cognomeCliente = new JLabel("Cognome");
		indirizzoCliente = new JLabel("Indirizzo");
		telefonoCliente = new JLabel("Telefono");
		//JTField
		nomeClienteField = new JTextField(FIELD_WIDTH);
		cognomeClienteField = new JTextField(FIELD_WIDTH);
		indirizzoClienteField = new JTextField(FIELD_WIDTH);
		telefonoClienteField = new JTextField(FIELD_WIDTH);
		//disabilito componenti clienti
		nomeClienteField.setEnabled(false); 
		cognomeClienteField.setEnabled(false);
		indirizzoClienteField.setEnabled(false);
		telefonoClienteField.setEnabled(false);
		nomeCliente.setEnabled(false);
		cognomeCliente.setEnabled(false);
		indirizzoCliente.setEnabled(false);
		telefonoCliente.setEnabled(false);
		//creo pannello clienti settando il bordo e inserendo le componenti
		JPanel clienti = new JPanel();
		clienti.setLayout(new GridLayout(10, 2));
		clienti.add(nomeCliente);
		clienti.add(nomeClienteField);
		clienti.add(new JLabel());
		clienti.add(new JLabel());
		clienti.add(new JLabel());
		clienti.add(new JLabel());
		clienti.add(cognomeCliente);
		clienti.add(cognomeClienteField);
		clienti.add(new JLabel());
		clienti.add(new JLabel());
		clienti.add(new JLabel());
		clienti.add(new JLabel());
		clienti.add(indirizzoCliente);
		clienti.add(indirizzoClienteField);
		clienti.add(new JLabel());
		clienti.add(new JLabel());
		clienti.add(new JLabel());
		clienti.add(new JLabel());
		clienti.add(telefonoCliente);
		clienti.add(telefonoClienteField);
		clienti.setBorder(new TitledBorder(new EtchedBorder(), "Clienti"));
		
		return clienti;
	}
	//Bordo Report
	public JPanel creaBordoReport() {
		//creo JComboBox reportCombo che contiene tutti i report dell'impresa
		reportCombo = new JComboBox();
		reportCombo.addItem("Lavoratori non impegnati");
		reportCombo.addItem("Cantieri");
		reportCombo.addItem("Fornitori");
		reportCombo.setSelectedItem("Lavoratori non impegnati");
		//creo due JRadioButton che indicano l'ordine con cui verranno stampati gli elementi del report
		//settiamo come valore di default selezionato crescente
		crescente = new JRadioButton("crescente");
		decrescente = new JRadioButton("decrescente");
		crescente.setSelected(true);
		//disabilito le componenti di report
		reportCombo.setEnabled(false);
		crescente.setEnabled(false);
		decrescente.setEnabled(false);
		//inseriamo i bottoni nel gruppo
		ButtonGroup gruppo = new ButtonGroup();
		gruppo.add(crescente);
		gruppo.add(decrescente);
		//creiamo il pannello report settando il bordo e inerendo le componenti
		JPanel report = new JPanel();
		report.setLayout(new GridLayout(4, 1));
		report.add(reportCombo);
		report.add(crescente);
		report.add(decrescente);
		report.add(creaBottoneVisualizza());
		report.setBorder(new TitledBorder(new EtchedBorder(), "Report"));
		
		return report;
	}
	//Bottone Visualizza
	//Genera i report e li visualizza sulla area di testo
	public JButton creaBottoneVisualizza() {
		//creo il bottone visualizza
		visualizza = new JButton("Visualizza");
		//classe interna che crea il tipo ricevitore VisualizzaListener che creerà un ricevitore che sarà associato al bottone visualizza
		class VisualizzaListener implements ActionListener{
			//Sovrascrivo
			public void actionPerformed(ActionEvent e) {
				//In base all'item selezionato nella reportCombo si genererà un report e poi verrà stampato
				if(reportCombo.getSelectedItem().equals("Lavoratori non impegnati")) {
					//creo il report lavoratori non impegnati
					Report<Personale> repL = new Report<Personale>();
					//assegno a repLav tutti i lavoratori non impegnati che costituiscono il report
					ArrayList<Personale> repLav = repL.getReport(amministrativo.getPersonale(),(p) -> !p.isImpegnato());
					//ordino repLav secondo il nome dei lavoratori
					Collections.sort(repLav, (l1,l2) -> l1.getNome().compareTo(l2.getNome()));
					//controllo in che ordine vuole essere visualizzato il report
					if(crescente.isSelected()) {
						//Stampo report in ordine crescente
						textArea.append("REPORT LAVORATORI NON IMPEGNATI IN ORDINE CRESCENTE RISPETTO AL NOME: \n\n");
						for(Personale p: repLav) {
							String posizione = p.getClass().getName().substring(p.getClass().getName().indexOf('.')+1);
							textArea.append(p.getNome()+" "+p.getCognome()+" "+posizione+"\n");
						}
					}
					else if(decrescente.isSelected()) {
						//stampo report in ordine decrescente
						textArea.append("REPORT LAVORATORI NON IMPEGNATI IN ORDINE DECRESCENTE RISPETTO AL NOME: \n\n");
						Collections.reverse(repLav);
						for(Personale p: repLav) {
							String posizione = p.getClass().getName().substring(p.getClass().getName().indexOf('.')+1);
							textArea.append(p.getNome()+" "+p.getCognome()+" "+posizione+"\n");
						}
					}
					//disabilito componenti report
					reportCombo.setEnabled(false);
					crescente.setEnabled(false);
					decrescente.setEnabled(false);
					visualizza.setEnabled(false);
				}
				else if(reportCombo.getSelectedItem().equals("Cantieri")) {
					//creo il report cantieri
					Report<Cantiere> reportC = new Report<Cantiere>();
					//assegno a repCant tutti i cantieri che rientrano nel range seguente che costituiscono il report
					ArrayList<Cantiere> repCant = reportC.getReport(operativo.getCantieri(), (ca) -> ca.getValore() >= 300000 && ca.getValore() <= 700000);
					//ordino repCant in base al valore dei cantieri
					Collections.sort(repCant, (c1,cc2) -> c1.getValore() - cc2.getValore());
					//controllo in che ordine vuole essere visualizzato il report
					if(crescente.isSelected()) {
						//Stampo report in ordine crescente
						textArea.append("REPORT DEI CANTIERI IN ORDINE CRESCENTE RISPETTO AL VALORE CON VALORE COMPRESO TRA 300.000€ E 700.000€: \n\n");
						for(Cantiere c: repCant) {
							textArea.append("Valore: "+c.getValore()+"€\nResponsabile: "+c.getResponsabile().getNome()+" "+c.getResponsabile().getCognome()
											+"\nCapo squadra: "+c.getSquadra().getCapoSquadra().getNome()+" "+c.getSquadra().getCapoSquadra().getCognome()
											+"\nOperai in squadra: ");
							for(Operaio o: c.getSquadra().getOperai()) {
								textArea.append(o.getNome()+" "+o.getCognome()+" "+o.getMansione()+" ");
							}
							textArea.append("\n\n");
						}
					}
					else if(decrescente.isSelected()) {
						//stampo report in ordine decrescente
						textArea.append("REPORT DEI CANTIERI IN ORDINE DECRESCENTE RISPETTO AL VALORE CON VALORE COMPRESO TRA 300.000€ E 700.000€: \n\n");
						Collections.reverse(repCant);
						for(Cantiere c: repCant) {
							textArea.append("Valore: "+c.getValore()+"€\nResponsabile: "+c.getResponsabile().getNome()+" "+c.getResponsabile().getCognome()
											+"\nCapo squadra: "+c.getSquadra().getCapoSquadra().getNome()+" "+c.getSquadra().getCapoSquadra().getCognome()
											+"\nOperai in squadra: ");
							for(Operaio o: c.getSquadra().getOperai()) {
								textArea.append(o.getNome()+" "+o.getCognome()+" "+o.getMansione()+" ");
							}
							textArea.append("\n\n");
						}
					}
					//disabilito le componenti di report
					reportCombo.setEnabled(false);
					crescente.setEnabled(false);
					decrescente.setEnabled(false);
					visualizza.setEnabled(false);
				}
				else if(reportCombo.getSelectedItem().equals("Fornitori")) {
					//creo il report fornitori
					Report<Fornitori> report = new Report<Fornitori>();
					//assegno a repFor i fornitori che hanno il servizio seguente che costituiscono il report
					ArrayList<Fornitori> repFor = report.getReport(amministrativo.getFornitori(), (f) -> f.getServizio().equals("Cantiere e macchine"));
					//ordino repFor in base al nome dei fornitori
					Collections.sort(repFor, (f1,f2) -> f1.getNome().compareTo(f2.getNome()));
					//controllo in che ordine vuole essere visualizzato il report
					if(crescente.isSelected()) {
						//Stampo report in ordine crescente
						textArea.append("REPORT DEI FORNITORI IN ORDINE CRESCENTE RISPETTO AL NOME CON SERVIZIO: Cantiere e macchine \n\n");
						for(Fornitori f: repFor)
							textArea.append("Nome: "+f.getNome()+"\nServizio: "+f.getServizio()+"\n\n");
					}
					else if(decrescente.isSelected()) {
						//stampo report in ordine decrescente
						textArea.append("REPORT DEI FORNITORI IN ORDINE DECRESCENTE RISPETTO AL NOME CON SERVIZIO: Cantiere e macchine \n\n");
						Collections.reverse(repFor);
						for(Fornitori f: repFor)
							textArea.append("Nome: "+f.getNome()+"\nServizio: "+f.getServizio()+"\n\n");
					}
					//disabilito le componenti di report
					reportCombo.setEnabled(false);
					crescente.setEnabled(false);
					decrescente.setEnabled(false);
					visualizza.setEnabled(false);
				}
			}
		}
		//creo il ricevitore e lo assegno al bottone visualizza
		listener = new VisualizzaListener();
		visualizza.addActionListener(listener);
		
		visualizza.setEnabled(false);
		return visualizza;
	}
	//Bordo cantieri...contiene le componenti riguardanti i cantieri										
	public JPanel creaBordoCantieri() {
		//crea jlabel e jtfield di valore
		valore = new JLabel("Valore");
		valoreField = new JTextField(FIELD_WIDTH);
		//crea altre jlabel
		responsabile = new JLabel("Responsabile");
		capoSquadra = new JLabel("Capo squadra");
		//crea field per responsabile / capo squadra/ operai
		responsabileField = new JTextField(FIELD_WIDTH);
		capoSquadraField = new JTextField(FIELD_WIDTH);
		//disabilito tutti i componenti
		valore.setEnabled(false);
		valoreField.setEnabled(false);
		responsabile.setEnabled(false);
		capoSquadra.setEnabled(false);
		responsabileField.setEnabled(false);
		capoSquadraField.setEnabled(false);
		
		//aggiungi nel pannello tutte le componenti e setto il bordo
		JPanel cantiere = new JPanel();
		cantiere.setLayout(new GridLayout(10, 2));
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.add(valore);
		cantiere.add(valoreField);
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.add(responsabile);
		cantiere.add(responsabileField);
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.add(capoSquadra);
		cantiere.add(capoSquadraField);
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.add(new JLabel());
		cantiere.setBorder(new TitledBorder(new EtchedBorder(), "Cantiere"));
		
		return cantiere;
	}
	//Bordo Squadra......contiene tutte le componenti che rappresentano la squadra
	public JPanel creaBordoSquadra() {
		//creo JLabel e JTField
		caposquadra = new JLabel("Capo Squadra");
		caposquadraField = new JTextField(FIELD_WIDTH);
		nomeOperaio = new JLabel("Nome");
		nomeOperaioField = new JTextField(FIELD_WIDTH);
		cognomeOperaio = new JLabel("Cognome");
		cognomeOperaioField = new JTextField(FIELD_WIDTH);
		//disabilito le componenti
		caposquadra.setEnabled(false);
		caposquadraField.setEnabled(false);
		nomeOperaio.setEnabled(false);
		nomeOperaioField.setEnabled(false);
		cognomeOperaio.setEnabled(false);
		cognomeOperaioField.setEnabled(false);
		//creo il pannello setto il bordo e aggiungo le componenti
		JPanel squadra = new JPanel();
		squadra.setLayout(new GridLayout(10, 2));
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.add(caposquadra);
		squadra.add(caposquadraField);
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.add(nomeOperaio);
		squadra.add(nomeOperaioField);
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.add(cognomeOperaio);
		squadra.add(cognomeOperaioField);
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.add(new JLabel());
		squadra.setBorder(new TitledBorder(new EtchedBorder(), "Squadra"));
		
		return squadra;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//VARIABILI D'ISTANZA
	private JTextArea textArea;
	private JScrollPane scroll;
	private JLabel nome, cognome, retribuzione, 
				   mansione, incarico, qualifica, livello, area,
				   nomeForn, servizioForn,
				   nomeEnte, luogoEnte, indirizzoEnte, telefonoEnte,
				   nomeCliente, cognomeCliente, indirizzoCliente, telefonoCliente,
				   valore, responsabile, capoSquadra,
				   caposquadra,nomeOperaio,cognomeOperaio;
	private JTextField nomeField, cognomeField, retribuzioneField,
					   mansioneField, incaricoField, qualificaField, livelloField, areaField,
					   nomeFornField, servizioFornField,
					   nomeEnteField, luogoEnteField, indirizzoEnteField, telefonoEnteField,
					   nomeClienteField, cognomeClienteField, indirizzoClienteField, telefonoClienteField,
					   valoreField, responsabileField, capoSquadraField,
					   caposquadraField,nomeOperaioField,cognomeOperaioField;
	private JComboBox reportCombo, personaleCombo;
	private JRadioButton crescente, decrescente;
	private JButton aggiungi, rimuovi, cerca, visualizza;
	private ActionListener listener;
	private ImpresaEdile impresa;
	private RepartoAmministrativo amministrativo;
	private RepartoOperativo operativo;
	private JPanel personale, enti, clienti, fornitori, report, cantieri,squad;
	private String fileCorrente;
	private JMenu seleziona, visualizzaM;
	
	private static final int AREA_ROWS = 300, AREA_COLUMS = 300;
	private static final int FRAME_WIDTH = 1000, FRAME_HEIGHT = 800;
	private static final int FIELD_WIDTH = 50;
}