package CUI;

import LanguageResources.Resource;
import static LanguageResources.Resource.labels;
import static LanguageResources.Resource.supportedLocales;
import domein.DomeinController;
import exceptions.KeuzeMenuOutOfRange;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;



public class SokobanApplicatie {

    protected DomeinController dc;
    private static Scanner s = new Scanner(System.in);
    protected AanmeldMenu Am;
    protected RegistratieMenu Rm;
    protected SpelMenu Sm;
    protected InSpel Is;
    protected MaakSpel Ms;
    protected WijzigSpel Ws;
    

    public SokobanApplicatie() {
        
        this.Am = new AanmeldMenu(this);
        this.Rm = new RegistratieMenu(this);
        this.Sm = new SpelMenu(this);
        this.Is = new InSpel(this);
        this.Ms = new MaakSpel(this);
        this.Ws = new WijzigSpel(this);
    }
    
    

    public void start(DomeinController dc) throws IOException {
        this.dc = dc;
        toonTaalMenu();
        toonStartMenu();
        
    }
    
    public void toonStartMenu(){
        boolean blijvenHerhalenFlag = true;
        do {
            try {
                System.out.printf(labels.getString("welkomKeuze") + "%n%n");
                System.out.printf(labels.getString("registreren") + "%n", 1, 2);
                System.out.print(labels.getString("keuze"));

                int keuze = s.nextInt();

                //Exception if not int 
                if (!(keuze <= 2 && keuze > 0)) {
                    throw new KeuzeMenuOutOfRange(labels.getString("foutiefGetal"));
                }

                if (keuze == 1) {
                    Am.toonAanmeldMenu();
                }
                if (keuze == 2) {
                    Rm.toonRegistratieMenu();
                }
                blijvenHerhalenFlag = false;

            } catch (InputMismatchException e) {
                System.out.println(labels.getString("foutieveInput"));
                s.nextLine();

            } catch (KeuzeMenuOutOfRange e) {
                System.out.println(e.getMessage());
                s.nextLine();

            }
        } while (blijvenHerhalenFlag);
    }

    public void toonTaalMenu() {
        boolean flag = true;
        do {
            try{
            System.out.printf("Kies uw taal / Choissisez votre langue / Choose your language:%n%-8dNederlands%n%-8dFrançais%n%-8dEnglish%nNr: ",1,2,3);
            int taal = s.nextInt();
            
            if (taal == 2) {
                supportedLocales[1] = new Locale("fr", "BE");
                labels = ResourceBundle.getBundle("LabelsBundle", supportedLocales[1]);
                
            } else if (taal == 3) {
                supportedLocales[0] = new Locale("en", "US");
                labels = ResourceBundle.getBundle("LabelsBundle", supportedLocales[0]);
            } else if (taal == 1) {
                supportedLocales[2] = new Locale("nl", "BE");
                labels = ResourceBundle.getBundle("LabelsBundle", supportedLocales[2]);
            } else {
                throw new KeuzeMenuOutOfRange("Kies een nummer tussen 1-3.\nChoissez un nombre entre 1-3.\nEnter a number betweem 1-3.\n");
            }
            
            flag = false;
            }
            catch(KeuzeMenuOutOfRange e)
            {
                s.nextLine();
                System.out.println(e.getMessage());
                
            }
            catch (InputMismatchException e) {
                System.out.println("Kies een nummer tussen 1-3.\nChoissez un nombre entre 1-3.\nEnter a number betweem 1-3.\n");
                s.nextLine();
                
            }
            catch(Exception e)
            {
                s.nextLine();
                System.out.println(e.getMessage());
            }
        } while (flag);
    }



    




}
