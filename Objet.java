/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import outils.OutilsJDBC;

/**
 *
 * @author ngarreau
 */
public class Objet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/tp_jdbc?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("SELECT id, description, latitude, longitude, visible, dateCreation, proprietaire FROM objet;");
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) {
                String id = resultat.getString("id");
                String description = resultat.getString("description");
                double latitude = resultat.getDouble("latitude");
                double longitude = resultat.getDouble("longitude");
                boolean visible = resultat.getBoolean("visible");
                //int dateCreation = resultat.getDate();
                String proprietaire = resultat.getString("proprietaire"); 
                System.out.println(id + " " + description + " = (" + latitude + "; " + longitude + ")" + " " + visible + "   " + proprietaire);
            }

            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
