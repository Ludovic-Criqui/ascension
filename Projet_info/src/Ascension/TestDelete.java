package Ascension;

import Outils.OutilsJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestDelete{

    public static void main(String[] args) {

        try {

            Connection connexion = DriverManager.getConnection("jdbc:mysql://nemrod.ens2m.fr:3306/20202021_s2_vs2_tp1_ascension?serverTimezone=UTC", "etudiant", "YTDTvj9TR3CDYCmP");

            PreparedStatement requete = connexion.prepareStatement("DELETE FROM joueur ");
            requete.executeUpdate();
            requete.close();
            connexion.close();

        } catch (SQLException ex) {
            Logger.getLogger(TestDelete.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}