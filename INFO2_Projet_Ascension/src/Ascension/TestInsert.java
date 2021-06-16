/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ascension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marc
 */
public class TestInsert {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs2_tp1_ascension?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("INSERT INTO joueur (id,pseudo,personnage,x,y) VALUES (2,'invité',4,'140','-1300')");
            requete.executeUpdate();
            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(TestInsert.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
