package CUI;

import static LanguageResources.Resource.labels;
import domein.Spelbord;
import exceptions.NieuwSpelbordException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class WijzigSpel {

    private static Scanner s = new Scanner(System.in);
    private SokobanApplicatie app;

    public WijzigSpel(SokobanApplicatie app) {
        this.app = app;
    }

    public void spelWijzigen() {
        if (app.dc.geefAdminrechten()) {
            String spelNaam = "";
            boolean itemPlaatsenFlag = true;
            boolean flag = true;

            System.out.println(labels.getString("wijzig"));
            int i = 1;
            System.out.printf(labels.getString("spelAanpassen"));
            List<String> lijstSpelnamen = app.dc.geefNaamSpellen();
            for (String naam : lijstSpelnamen) {
                System.out.printf("%-10d%s%n", i++, naam);
            }
            i = 1;

            do {
                try {
                    System.out.println(labels.getString("typSpelNaam"));
                    spelNaam = s.nextLine();
                    if (!spelNaam.equalsIgnoreCase("stop")) {
                        app.dc.selecteerSpel(spelNaam);
                        flag = false;
                    } else {
                        app.Sm.toonSpelMenu();
                    }
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }

            } while (flag);
            System.out.printf(labels.getString("geselecteerdSpelWeergeven"), spelNaam);
            boolean nogEenSpelbordAanpassenFlag = true;
            do {

                ArrayList<char[][]> spelborden = app.dc.geefLijstSpelbordenGeselecteerdSpel();
                int spelbordnr = 1;
                for (char[][] spelborden1 : spelborden) {
                    String spelbord = printSpelbord(spelborden1);
                    System.out.println(labels.getString("spelbordNr") + spelbordnr++ + "\n");
                    System.out.println(spelbord + "\n\n");
                }
                boolean flagSpelbordNrBinnenBereik = true;
                int spelbordNummer = 0;
                do {
                    try {
                        System.out.println(labels.getString("spelbordNrIngeven"));
                        String mogelijkStoppen = s.next();
                        if (mogelijkStoppen.equalsIgnoreCase("stop")) {
                            app.Sm.toonSpelMenu();
                        }
                        spelbordNummer = Integer.parseInt(mogelijkStoppen);
                        if (spelbordNummer <= app.dc.geefTotaalAantalSpelborden() && spelbordNummer > 0) {
                            flagSpelbordNrBinnenBereik = false;
                        } else {
                            System.out.println("nummer lijst hierboven");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(labels.getString("nrformatE"));
                    } catch (InputMismatchException e) {
                        System.out.println(labels.getString("nrformatE"));
                    }
                } while (flagSpelbordNrBinnenBereik);
                app.dc.setSpelSpelbordInGebruik(spelbordNummer);
                char[][] Spelbordkeuze = app.dc.geefGekozenSpelbord(spelbordNummer);
                System.out.println(printSpelbord(Spelbordkeuze) + "\n");

                do {
                    try {
                        System.out.println(labels.getString("keuzeConfig"));
                        String watTeDoen = s.next();
                        if (watTeDoen.equalsIgnoreCase("stop")) {
                            itemPlaatsenFlag = false;
                            app.Sm.toonSpelMenu();
                        } else if (watTeDoen.equalsIgnoreCase("weg")) {
                            itemPlaatsenFlag = false;
                            app.dc.verwijderSpelbord();
                            System.out.println(labels.getString("verwijderdsucces"));
                        } else if (watTeDoen.equalsIgnoreCase("edit")) {
                            boolean flagStoppenBijAanpassen = true;

                            int xcoord = 0, ycoord = 0;
                            System.out.print(labels.getString("geefXCOORD"));
                            if (s.hasNextInt()) {
                                xcoord = s.nextInt();
                            } else {
                                throw new IllegalArgumentException(labels.getString("gelieveGetal"));
                            }
                            System.out.print(labels.getString("geefYCOORD"));
                            if (s.hasNextInt()) {
                                ycoord = s.nextInt();
                            } else {
                                throw new IllegalArgumentException(labels.getString("gelieveGetal"));
                            }

                            if ((xcoord > 9 || ycoord > 9) || (xcoord < 0 || ycoord < 0)) {
                                throw new NieuwSpelbordException(labels.getString("getalg9k0"));
                            }
                            String stoppenBijAanpassen = "";
                            do {
                                System.out.println(labels.getString("jaNeeStoppen"));
                                stoppenBijAanpassen = s.next();
                                if (stoppenBijAanpassen.equalsIgnoreCase("ja") || stoppenBijAanpassen.equalsIgnoreCase("nee")) {
                                    flagStoppenBijAanpassen = false;
                                }
                                if (stoppenBijAanpassen.equalsIgnoreCase("ja")) {
                                    itemPlaatsenFlag = false;
                                }
                            } while (flagStoppenBijAanpassen);

                            if (stoppenBijAanpassen.equalsIgnoreCase("nee")) {
                                System.out.printf(labels.getString("kiesTeKiezenItem"));

                                boolean flagplaaten = true;
                                do {
                                    try {
                                        String item = s.next();
                                        item = item.toLowerCase();
                                        char itemchar = item.charAt(0);
                                        app.dc.plaatsItemWijziging(itemchar, xcoord, ycoord);
                                        flagplaaten = false;
                                    } catch (NieuwSpelbordException e) {
                                        //s.next();
                                        System.out.println("\n" + e.getMessage() + "\n");
                                    }
                                } while (flagplaaten);

                                System.out.println(printSpelbord(app.dc.geefSpelbordInGebruik()) + "\n");
                            }

                        } else if (watTeDoen.equalsIgnoreCase("bevestig")) {
                            boolean flagVerderDoen = true;
                            String verderDoen = "";
                            do {
                                System.out.println(labels.getString("controleBevestiging"));
                                verderDoen = s.next();
                                if (verderDoen.equalsIgnoreCase("ja") || verderDoen.equalsIgnoreCase("nee")) {
                                    flagVerderDoen = false;
                                }
                                if (verderDoen.equalsIgnoreCase("ja")) {
                                    boolean flagZeker = true;
                                    String zeker = "";
                                    do {
                                        System.out.println(labels.getString("cotroleBevestiging2"));
                                        zeker = s.next();
                                        if (zeker.equalsIgnoreCase("ja") || zeker.equalsIgnoreCase("nee")) {
                                            flagZeker = false;

                                        }
                                        if (zeker.equalsIgnoreCase("ja")) {
                                            if (app.dc.IsCorrectControleGewijzigdSpelbord()) {
                                                app.dc.bevestigWijzigingenWegschrijven();
                                            }
                                            System.out.println(labels.getString("spelbordWeggeschreven"));

                                        }
                                    } while (flagZeker);
                                }
                            } while (flagVerderDoen);
                            itemPlaatsenFlag = false;

                        } else {
                            System.out.println("\n" + labels.getString("itemhierboven") + "\n");
                        }

                    } catch (NieuwSpelbordException nse) {
                        System.out.println("\n" + nse.getMessage() + "\n");
                    } catch (IllegalArgumentException iae) {
                        s.next();
                        System.out.println("\n" + iae.getMessage() + "\n");
                    }
                    catch (Exception e) {
                        System.out.println("\n" + e.getMessage() + "\n");
                    }
                } while (itemPlaatsenFlag);

                boolean flagherhalen = true;
                String herhalen = "";
                do {
                    System.out.println(labels.getString("nogEenSpelbordAanpassen"));
                    herhalen = s.next();
                    if (herhalen.equalsIgnoreCase("ja")) {
                        nogEenSpelbordAanpassenFlag = true;
                        flagherhalen = false;
                    } else if (herhalen.equalsIgnoreCase("nee")) {
                        nogEenSpelbordAanpassenFlag = false;
                        flagherhalen = false;
                    } else {
                        System.out.println(labels.getString("typJaNee"));
                    }
                } while (flagherhalen);

            } while (nogEenSpelbordAanpassenFlag);
        } else {
            System.out.println(labels.getString("GUIadmin"));
        }
        app.Sm.toonSpelMenu();
    }

    private String printSpelbord(char[][] spelbordchar) {
        String spelbord = "";
        for (char[] rijen : spelbordchar) {
            for (char kolommen : rijen) {
                spelbord += kolommen + " ";
            }
            spelbord += "\n";
        }
        return spelbord;
    }
}
