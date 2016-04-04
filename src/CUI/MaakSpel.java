package CUI;

import static LanguageResources.Resource.*;
import domein.Spel;
import domein.SpelRepository;
import domein.Spelbord;
import domein.Veld;
import exceptions.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MaakSpel {

    private static Scanner s = new Scanner(System.in);
    private SokobanApplicatie app;

    public MaakSpel(SokobanApplicatie app) {
        this.app = app;
    }

    public void ConfigureerSpel() {
        boolean flag2 = true;
        boolean flagNaam = true;
        boolean flagOpnieuw = true;
        boolean flag3 = true;
        boolean FlagExcepties = true;

        int spelbordNr = 1;

        System.out.printf(labels.getString("spelMaken"));
        System.out.printf(labels.getString("uniekeNaam"));
        String spelNaam = s.next();
        List spelNamen = app.dc.geefNaamSpellen();

        do {
            if (containsCaseInsensitive(spelNaam, spelNamen)) {
                System.out.printf(labels.getString("nietUniekeNaam"));
                spelNaam = s.next();
            } else {
                flagNaam = false;
            }
        } while (flagNaam);

        app.dc.maakSpel(spelNaam);

        do {//dowhile voor nieuw spelbord te maken
            app.dc.maakSpelbord(spelbordNr);
            spelbordNr++;
            do {//dowhile voor excepties op te vangen
                try {
                    do {
                        flag2 = true;
                        do { //dowhile voor items te plaatsen.
                            String spelbord = "";
                            for (char[] rijen : app.dc.geefNieuwSpelbord()) {
                                for (char kolommen : rijen) {
                                    spelbord += kolommen + " ";
                                }
                                spelbord += "\n";
                            }
                            System.out.println(spelbord);

                            try {
                                System.out.printf(labels.getString("kiesItem"));
                                String item = s.next();
                                int xcoord = 0;
                                int ycoord = 0;
                                if (item.equalsIgnoreCase("#")) {
                                    boolean flagMuur = true;
                                    do {
                                        try {
                                            System.out.print(labels.getString("geefXCOORD"));
                                            xcoord = s.nextInt();

                                            System.out.print(labels.getString("geefYCOORD"));
                                            ycoord = s.nextInt();

                                            if ((xcoord > 9 || ycoord > 9) || (xcoord < 0 || ycoord < 0)) {
                                                throw new NieuwSpelbordException(labels.getString("grensVelden"));
                                            }
                                            flagMuur = false;
                                        } catch (InputMismatchException e) {
                                            s.nextLine();
                                            System.out.println(labels.getString("grensVelden"));
                                        }
                                    } while (flagMuur);

                                    app.dc.plaatsItem('#', xcoord, ycoord);
                                } else if (item.equalsIgnoreCase("+")) {
                                    boolean flagAchtergrond = true;
                                    do {
                                        try {
                                            System.out.print(labels.getString("geefXCOORD"));
                                            xcoord = s.nextInt();

                                            System.out.print(labels.getString("geefYCOORD"));
                                            ycoord = s.nextInt();

                                            if ((xcoord > 9 || ycoord > 9) || (xcoord < 0 || ycoord < 0)) {
                                                throw new NieuwSpelbordException(labels.getString("grensVelden"));
                                            }
                                            flagAchtergrond = false;
                                        } catch (InputMismatchException e) {
                                            s.nextLine();
                                            System.out.println(labels.getString("grensVelden"));
                                        }
                                    } while (flagAchtergrond);

                                    app.dc.plaatsItem('+', xcoord, ycoord);
                                } else if (item.equalsIgnoreCase("o")) {
                                    boolean flagdoel = true;
                                    do {
                                        try {
                                            System.out.print(labels.getString("geefXCOORD"));
                                            xcoord = s.nextInt();

                                            System.out.print(labels.getString("geefYCOORD"));
                                            ycoord = s.nextInt();

                                            if ((xcoord > 9 || ycoord > 9) || (xcoord < 0 || ycoord < 0)) {
                                                throw new NieuwSpelbordException(labels.getString("grensVelden"));
                                            }
                                            flagdoel = false;
                                        } catch (InputMismatchException e) {
                                            s.nextLine();
                                            System.out.println(labels.getString("grensVelden"));
                                        }
                                    } while (flagdoel);

                                    app.dc.plaatsItem('o', xcoord, ycoord);
                                } else if (item.equalsIgnoreCase("k")) {
                                    boolean flagKist = true;
                                    do {
                                        try {
                                            System.out.print(labels.getString("geefXCOORD"));
                                            xcoord = s.nextInt();

                                            System.out.print(labels.getString("geefYCOORD"));
                                            ycoord = s.nextInt();

                                            if ((xcoord > 9 || ycoord > 9) || (xcoord < 0 || ycoord < 0)) {
                                                throw new NieuwSpelbordException(labels.getString("grensVelden"));
                                            }
                                            flagKist = false;
                                        }catch (InputMismatchException e) {
                                            s.nextLine();
                                            System.out.println(labels.getString("grensVelden"));
                                        } 
                                    } while (flagKist);

                                    app.dc.plaatsItem('k', xcoord, ycoord);
                                } else if (item.equalsIgnoreCase("@")) {
                                    boolean flagMan = true;
                                    do {
                                        try {
                                            System.out.print(labels.getString("geefXCOORD"));
                                            xcoord = s.nextInt();

                                            System.out.print(labels.getString("geefYCOORD"));
                                            ycoord = s.nextInt();

                                            if ((xcoord > 9 || ycoord > 9) || (xcoord < 0 || ycoord < 0)) {
                                                throw new NieuwSpelbordException(labels.getString("grensVelden"));
                                            }
                                            flagMan = true;
                                        } catch (InputMismatchException e) {
                                            s.nextLine();
                                            System.out.println(labels.getString("grensVelden"));
                                        }
                                    } while (flagMan);

                                    app.dc.plaatsItem('@', xcoord, ycoord);
                                } else if (item.equalsIgnoreCase(".")) {
                                    boolean flag001 = true;
                                    do {
                                        try {
                                            System.out.print(labels.getString("geefXCOORD"));
                                            xcoord = s.nextInt();

                                            System.out.print(labels.getString("geefYCOORD"));
                                            ycoord = s.nextInt();

                                            if ((xcoord > 9 || ycoord > 9) || (xcoord < 0 || ycoord < 0)) {
                                                throw new NieuwSpelbordException(labels.getString("grensVelden"));
                                            }
                                            flag001 = false;
                                        } catch (InputMismatchException e) {
                                            s.nextLine();
                                            System.out.println(labels.getString("grensVelden"));
                                        }
                                    } while (flag001);

                                    app.dc.plaatsItem('.', xcoord, ycoord);
                                } else if (item.equalsIgnoreCase(labels.getString("stop"))) {
                                    flag2 = false;
                                } else {
                                    throw new NieuwSpelbordException(labels.getString("foutiefSymbool"));
                                }
                            } catch (NieuwSpelbordException e) {
                                System.out.println(e.getMessage());
                            }
                        } while (flag2);

                    } while (!app.dc.IsCorrectControleNieuwSpelbord());
                    FlagExcepties = false;
                } catch (NieuwSpelbordException e) {
                    System.out.println(e.getMessage());
                }
            } while (FlagExcepties);

            String opnieuw = "";
            do {
                System.out.println(labels.getString("nogEenSpelbord"));
                opnieuw = s.next();
                if (opnieuw.equalsIgnoreCase(labels.getString("NEE"))) {
                    flagOpnieuw = false;
                    flag3 = false;
                } else if (opnieuw.equalsIgnoreCase(labels.getString("JA"))) {
                    flagOpnieuw = true;
                    flag3 = false;
                } else {
                    System.out.println(labels.getString("jaOfNee"));
                }
            } while (flag3);

            //hier methode aanroepen om weg te schrijven naar de database.
        } while (flagOpnieuw);
        app.dc.registreerNieuwSpel();
        System.out.printf("%s %d %s%n", labels.getString("AantalSpelborden"), app.dc.geefTotaalAantalSpelbordenVanInGuiGekozenSpel(app.dc.geefNiewSpelSpelnaam()), labels.getString("GoedGedaan"));
        app.Sm.toonSpelMenu();

    }

    public boolean containsCaseInsensitive(String strToCompare, List<String> list) {
        for (String str : list) {
            if (str.equalsIgnoreCase(strToCompare)) {
                return (true);
            }
        }
        return (false);
    }

}
