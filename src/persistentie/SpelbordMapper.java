package persistentie;

import domein.Kist;
import domein.Mannetje;
import domein.Spel;
import domein.Spelbord;
import domein.Veld;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpelbordMapper {

    public SpelbordMapper() {

    }

    public List<Spelbord> geefSpelbord(String Spelnaam) {
        List<Spelbord> spelborden = new ArrayList<>();

        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT * FROM sokoban.spelbord WHERE spel_Spelnaam = ? ORDER BY VolgordeID;");
            query.setString(1, Spelnaam);
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    int spelbordnr = Integer.parseInt(rs.getString("VolgordeID"));
                    spelborden.add(new Spelbord(spelbordnr));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        return spelborden;

    }

    public List<Veld> geefVelden(String spelnaam, int spelbordnr) {
        List<int[]> coordMuren = haalCoordAlleMurenOp(spelnaam, spelbordnr);
        List<int[]> coordKisten = haalCoordAlleKistenOp(spelnaam, spelbordnr);
        int[] coordMannetje = haalCoordMannetjenOp(spelnaam, spelbordnr);
        List<Veld> velden = new ArrayList<>();
        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT * FROM sokoban.veld WHERE Spelbord_spel_Spelnaam = ? and Spelbord_VolgordeID = ?");
            query.setString(1, spelnaam);
            query.setInt(2, spelbordnr);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    boolean heeftDoel = rs.getByte(1) == 1;
                    int x = rs.getInt(2);
                    int y = rs.getInt(3);
                    Veld veld = null;
                    for (int[] muurCoord : coordMuren) {
                        if (muurCoord[0] == x && muurCoord[1] == y) {
                            veld = new Veld(heeftDoel, true, x, y, null, null);
                            break;
                        }
                    }
                    for (int[] kistCoord : coordKisten) {
                        if (kistCoord[0] == x && kistCoord[1] == y) {
                            veld = new Veld(heeftDoel, false, x, y, null, new Kist(null));
                            veld.getKist().setVeld(veld);
                            break;
                        }
                    }
                    if (coordMannetje[0] == x && coordMannetje[1] == y) {
                        veld = new Veld(heeftDoel, false, x, y, new Mannetje(null), null);
                        veld.getHetMannetje().setVeld(veld);
                    }
                    if (veld == null) {
                        veld = new Veld(heeftDoel, false, x, y, null, null);
                    }
                    velden.add(veld);

                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return velden;
    }

    private List<int[]> haalCoordAlleMurenOp(String spelnaam, int spelbordnr) {
        List<int[]> coord = new ArrayList<int[]>();//2d list
        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT Veld_XCoord,Veld_YCoord FROM sokoban.muur WHERE Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
            query.setString(1, spelnaam);
            query.setInt(2, spelbordnr);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    int[] rij = new int[rs.getMetaData().getColumnCount()];
                    for (int i = 0; i < rij.length; i++) {
                        rij[i] = rs.getInt(i + 1);
                    }
                    coord.add(rij);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return coord;
    }

    private List<int[]> haalCoordAlleKistenOp(String spelnaam, int spelbordnr) {
        List<int[]> coord = new ArrayList<int[]>();//2d list
        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT Veld_XCoord,Veld_YCoord FROM sokoban.kist WHERE Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
            query.setString(1, spelnaam);
            query.setInt(2, spelbordnr);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    int[] rij = new int[rs.getMetaData().getColumnCount()];
                    for (int i = 0; i < rij.length; i++) {
                        rij[i] = rs.getInt(i + 1);
                    }
                    coord.add(rij);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return coord;
    }

    public int[] haalCoordMannetjenOp(String spelnaam, int spelbordnr) {
        int[] coord = new int[2];//2d list
        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = connectie.prepareStatement("SELECT Veld_XCoord,Veld_YCoord FROM sokoban.mannetje WHERE Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
            query.setString(1, spelnaam);
            query.setInt(2, spelbordnr);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    coord[0] = rs.getInt(1);//x
                    coord[1] = rs.getInt(2);//y
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return coord;
    }

    public void registreerNieuwSpelbord(String spelnaam, Spelbord huidigSpelbord) {
        StringBuilder mapcode = new StringBuilder("");
        for (Veld[] rij : huidigSpelbord.getSpelbordMap()) {
            for (Veld col : rij) {
                if (col != null) {
                    mapcode.append(col.toChar());
                } else {
                    mapcode.append('+');
                }
            }
        }
        //controle op lengte
        if (mapcode.length() != 100) {
            throw new IllegalArgumentException("Mapvar is geen 100 tekens lang");
        }

        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            int volgorde = huidigSpelbord.getSpelbordNr();
            PreparedStatement sql = connectie.prepareStatement("INSERT INTO sokoban.spelbord (Mapcode, spel_Spelnaam, VolgordeID) values(?,?,?);");
            sql.setString(1, mapcode.toString());
            sql.setString(2, spelnaam);
            sql.setInt(3, volgorde);
            sql.executeUpdate();
            //InsertVeldenQuerry
            int tellerCharAt = 0;
            for (int rij = 0; rij < 10; rij++) {
                for (int kolom = 0; kolom < 10; kolom++) {
                    if (mapcode.charAt(tellerCharAt) == 'o') {
                        PreparedStatement insertDoel = connectie.prepareStatement("INSERT INTO sokoban.veld VALUES(true,?,?,?,?);");
                        insertDoel.setInt(1, kolom);
                        insertDoel.setInt(2, rij);
                        insertDoel.setString(3, spelnaam);
                        insertDoel.setInt(4, volgorde);
                        insertDoel.executeUpdate();
                    }
                    if (mapcode.charAt(tellerCharAt) == '.') {
                        PreparedStatement insertLeegVeld = connectie.prepareStatement("INSERT INTO sokoban.veld VALUES(false,?,?,?,?);");
                        insertLeegVeld.setInt(1, kolom);
                        insertLeegVeld.setInt(2, rij);
                        insertLeegVeld.setString(3, spelnaam);
                        insertLeegVeld.setInt(4, volgorde);
                        insertLeegVeld.executeUpdate();
                    }
                    if (mapcode.charAt(tellerCharAt) == '#') {
                        PreparedStatement insertMuurVeld = connectie.prepareStatement("INSERT INTO sokoban.veld VALUES(false,?,?,?,?);");
                        insertMuurVeld.setInt(1, kolom);
                        insertMuurVeld.setInt(2, rij);
                        insertMuurVeld.setString(3, spelnaam);
                        insertMuurVeld.setInt(4, volgorde);
                        insertMuurVeld.executeUpdate();

                        PreparedStatement insertMuur = connectie.prepareStatement("INSERT INTO sokoban.muur(Veld_XCoord, Veld_Ycoord,Veld_spelbord_spel_spelnaam,Veld_spelbord_spel_volgordeID) VALUES(?,?,?,?);");
                        insertMuur.setInt(1, kolom);
                        insertMuur.setInt(2, rij);
                        insertMuur.setString(3, spelnaam);
                        insertMuur.setInt(4, volgorde);
                        insertMuur.executeUpdate();

                    }
                    if (mapcode.charAt(tellerCharAt) == '@') {
                        PreparedStatement insertVeldMannetje = connectie.prepareStatement("INSERT INTO sokoban.veld VALUES(false,?,?,?,?);");
                        insertVeldMannetje.setInt(1, kolom);
                        insertVeldMannetje.setInt(2, rij);
                        insertVeldMannetje.setString(3, spelnaam);
                        insertVeldMannetje.setInt(4, volgorde);
                        insertVeldMannetje.executeUpdate();
                        PreparedStatement insertMannetje = connectie.prepareStatement("INSERT INTO sokoban.mannetje VALUES(?,?,?,?);");
                        insertMannetje.setInt(1, kolom);
                        insertMannetje.setInt(2, rij);
                        insertMannetje.setString(3, spelnaam);
                        insertMannetje.setInt(4, volgorde);
                        insertMannetje.executeUpdate();
                    }
                    if (mapcode.charAt(tellerCharAt) == 'K') {
                        PreparedStatement insertVeldKist = connectie.prepareStatement("INSERT INTO sokoban.veld VALUES(false,?,?,?,?);");
                        insertVeldKist.setInt(1, kolom);
                        insertVeldKist.setInt(2, rij);
                        insertVeldKist.setString(3, spelnaam);
                        insertVeldKist.setInt(4, volgorde);
                        insertVeldKist.executeUpdate();

                        PreparedStatement insertKist = connectie.prepareStatement("INSERT INTO sokoban.kist VALUES(?,?,?,?);");
                        insertKist.setInt(1, kolom);
                        insertKist.setInt(2, rij);
                        insertKist.setString(3, spelnaam);
                        insertKist.setInt(4, volgorde);
                        insertKist.executeUpdate();
                    }
                    tellerCharAt++;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        //System.out.println("SUCCES WRITING TO DB");
    }

    public void verwijderSpelbord(Spel spel, Spelbord spelbordInGebruik) {
        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement deleteMuur = connectie.prepareStatement("DELETE from sokoban.muur where Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
            deleteMuur.setString(1, spel.getSpelNaam());
            deleteMuur.setInt(2, spelbordInGebruik.getSpelbordNr());
            PreparedStatement deleteKist = connectie.prepareStatement("DELETE from sokoban.kist where Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
            deleteKist.setString(1, spel.getSpelNaam());
            deleteKist.setInt(2, spelbordInGebruik.getSpelbordNr());
            PreparedStatement deleteMannetje = connectie.prepareStatement("DELETE from sokoban.mannetje where Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
            deleteMannetje.setString(1, spel.getSpelNaam());
            deleteMannetje.setInt(2, spelbordInGebruik.getSpelbordNr());
            PreparedStatement deleteVeld = connectie.prepareStatement("DELETE from sokoban.veld where Spelbord_spel_Spelnaam = ? and Spelbord_VolgordeID = ?");
            deleteVeld.setString(1, spel.getSpelNaam());
            deleteVeld.setInt(2, spelbordInGebruik.getSpelbordNr());
            PreparedStatement deleteSpelbord = connectie.prepareStatement("DELETE from sokoban.spelbord where spel_Spelnaam = ? and VolgordeID = ?");
            deleteSpelbord.setString(1, spel.getSpelNaam());
            deleteSpelbord.setInt(2, spelbordInGebruik.getSpelbordNr());
            deleteMuur.executeUpdate();
            deleteKist.executeUpdate();
            deleteMannetje.executeUpdate();
            deleteVeld.executeUpdate();
            deleteSpelbord.executeUpdate();
            if (spel.geefTotaalAantalSpelborden() == 1) {
                PreparedStatement deleteSpel = connectie.prepareStatement("DELETE from sokoban.spel where Spelnaam = ?");
                deleteSpel.setString(1, spel.getSpelNaam());
                deleteSpel.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void bevestigWijzigingenWegschrijven(String spelNaam, Spelbord spelbordInGebruik) {
        StringBuilder mapcode = new StringBuilder("");
        for (Veld[] rij : spelbordInGebruik.getSpelbordMap()) {
            for (Veld col : rij) {
                if (col != null) {
                    mapcode.append(col.toChar());
                } else {
                    mapcode.append('+');
                }
            }
        }
        //controle op lengte
        if (mapcode.length() != 100) {
            throw new IllegalArgumentException("Mapvar is geen 100 tekens lang");
        }

        try (Connection connectie = DriverManager.getConnection(Connectie.JDBC_URL)) {

            PreparedStatement schrijfSpelbordWeg = connectie.prepareStatement("UPDATE sokoban.spelbord SET Mapcode = ? WHERE spel_Spelnaam = ? and VolgordeID = ?");
            schrijfSpelbordWeg.setString(1, mapcode.toString());
            schrijfSpelbordWeg.setString(2, spelNaam);
            schrijfSpelbordWeg.setInt(3, spelbordInGebruik.getSpelbordNr());
            schrijfSpelbordWeg.executeUpdate();

            //eerst de andere vreemde sleutels verwijderen!!
            PreparedStatement delMuren = connectie.prepareStatement("DELETE from sokoban.muur where Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
            delMuren.setString(1, spelNaam);
            delMuren.setInt(2, spelbordInGebruik.getSpelbordNr());
            delMuren.executeUpdate();

            PreparedStatement delKisten = connectie.prepareStatement("DELETE from sokoban.kist where Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
            delKisten.setString(1, spelNaam);
            delKisten.setInt(2, spelbordInGebruik.getSpelbordNr());
            delKisten.executeUpdate();

            PreparedStatement deleteMannetje = connectie.prepareStatement("DELETE from sokoban.mannetje where Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
            deleteMannetje.setString(1, spelNaam);
            deleteMannetje.setInt(2, spelbordInGebruik.getSpelbordNr());

            deleteMannetje.executeUpdate();
            //Er zijn enkel velden-records van velden die geen achtergrond zijn. Als men dus een veld wilt aanpassen dat vroeger een achtergrond was, zal dit niet worden doorgevoerd omdat die record vroeger niet in de database zat.
            //Er moet dus nieuwe records gecreated worden aangezien we anders records gaan updaten die onbestaand zijn.

            PreparedStatement deleteVeld = connectie.prepareStatement("DELETE from sokoban.veld where Spelbord_spel_Spelnaam = ? and Spelbord_VolgordeID = ?");
            deleteVeld.setString(1, spelNaam);
            deleteVeld.setInt(2, spelbordInGebruik.getSpelbordNr());
            deleteVeld.executeUpdate();

            PreparedStatement schrijfVeldenWeg = connectie.prepareStatement("INSERT INTO sokoban.veld VALUES(?,?,?,?,?);");
            Veld[][] sb = spelbordInGebruik.getSpelbordMap();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (sb[i][j] != null) {
                        if (sb[i][j].getHeeftDoel() == false) {
                            schrijfVeldenWeg.setBoolean(1, false);
                            schrijfVeldenWeg.setInt(2, j);
                            schrijfVeldenWeg.setInt(3, i);
                            schrijfVeldenWeg.setString(4, spelNaam);
                            schrijfVeldenWeg.setInt(5, spelbordInGebruik.getSpelbordNr());
                            schrijfVeldenWeg.executeUpdate();
                        } else {
                            schrijfVeldenWeg.setBoolean(1, true);
                            schrijfVeldenWeg.setInt(2, j);
                            schrijfVeldenWeg.setInt(3, i);
                            schrijfVeldenWeg.setString(4, spelNaam);
                            schrijfVeldenWeg.setInt(5, spelbordInGebruik.getSpelbordNr());
                            schrijfVeldenWeg.executeUpdate();
                        }
                        if (sb[i][j].getHetMannetje() != null) {
                            PreparedStatement insertMannetje = connectie.prepareStatement("INSERT INTO sokoban.mannetje VALUES(?,?,?,?);");
                            insertMannetje.setInt(1, j);
                            insertMannetje.setInt(2, i);
                            insertMannetje.setString(3, spelNaam);
                            insertMannetje.setInt(4, spelbordInGebruik.getSpelbordNr());
                            insertMannetje.executeUpdate();
                        }
                    }
                }
            }

//            PreparedStatement schrijfMannetjeWeg = connectie.prepareStatement("UPDATE sokoban.mannetje SET Veld_XCoord = ?, Veld_YCoord = ? WHERE Veld_spelbord_spel_spelnaam = ? and Veld_spelbord_spel_volgordeID = ?");
//            Mannetje mannetje = spelbordInGebruik.getMannetje();
//            Veld veld = mannetje.getVeld();
//            schrijfMannetjeWeg.setInt(1, veld.getX());
//            schrijfMannetjeWeg.setInt(2, veld.getY());
//            schrijfMannetjeWeg.setString(3, spelNaam);
//            schrijfMannetjeWeg.setInt(4, spelbordInGebruik.getSpelbordNr());
//            schrijfMannetjeWeg.executeUpdate();

            PreparedStatement schrijfMurenWeg = connectie.prepareStatement("INSERT INTO sokoban.muur(Veld_XCoord, Veld_YCoord, Veld_spelbord_spel_spelnaam, Veld_spelbord_spel_volgordeID) VALUES(?,?,?,?);");
            int teller = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (mapcode.charAt(teller) == '#') {
                        schrijfMurenWeg.setInt(1, j);
                        schrijfMurenWeg.setInt(2, i);
                        schrijfMurenWeg.setString(3, spelNaam);
                        schrijfMurenWeg.setInt(4, spelbordInGebruik.getSpelbordNr());
                        schrijfMurenWeg.executeUpdate();
                    }
                    teller++;
                }
            }

            PreparedStatement schrijfKistenWeg = connectie.prepareStatement("INSERT INTO sokoban.kist(Veld_XCoord, Veld_YCoord, Veld_spelbord_spel_spelnaam, Veld_spelbord_spel_volgordeID) VALUES(?,?,?,?)");
            int Teller = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (mapcode.charAt(Teller) == 'K') {
                        schrijfKistenWeg.setInt(1, j);
                        schrijfKistenWeg.setInt(2, i);
                        schrijfKistenWeg.setString(3, spelNaam);
                        schrijfKistenWeg.setInt(4, spelbordInGebruik.getSpelbordNr());
                        schrijfKistenWeg.executeUpdate();
                    }
                    Teller++;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
