package Ascension;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AcceuilFrame extends javax.swing.JFrame {

    private SalonAttente salon;
    private Connection c;
    private ChoixPerso choixPerso;
    private int persoAffiche = 1;

    /**
     * Creates new form AcceuilleFrame
     */
    public AcceuilFrame() throws IOException {

        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Icon icon = new ImageIcon("background1.png");
        this.salon = new SalonAttente(1); 
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - this.getWidth()/2, dim.height/2 - this.getHeight()/2-170);
        this.jLabel2.setVisible(false);
        this.choixPerso = new ChoixPerso(this.salon.getFenetreJeu().getJeu());
        
//      Icon icon = new ImageIcon("background1.png");
//      this.jButton2(icon);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jButton1.setText("Créer une partie");
        jButton1.setPreferredSize(new java.awt.Dimension(133, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Rejoindre une partie");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(((new javax.swing.ImageIcon("mario.png")).getImage()).getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 50, 255));
        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 2, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(155, 155, 0));
        jLabel1.setText("Ascension");
        jLabel1.setIconTextGap(10);

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    static void initialisationjButton3() {
        if (ChoixPerso.getChoixPersonnage() == 1){
            jButton3.setIcon(new javax.swing.ImageIcon(((new javax.swing.ImageIcon("mario.png")).getImage()).getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH)));
        }
        if (ChoixPerso.getChoixPersonnage() == 2){
            jButton3.setIcon(new javax.swing.ImageIcon(((new javax.swing.ImageIcon("steve.png")).getImage()).getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH)));
        }
        if (ChoixPerso.getChoixPersonnage() == 3){
            jButton3.setIcon(new javax.swing.ImageIcon(((new javax.swing.ImageIcon("amongus.png")).getImage()).getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH)));
        }
        if (ChoixPerso.getChoixPersonnage() == 4){
            jButton3.setIcon(new javax.swing.ImageIcon(((new javax.swing.ImageIcon("ratchet.png")).getImage()).getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH)));
        }
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int compteur = 0;
        PreparedStatement requete;
        this.jLabel2.setVisible(false);
        try {
            requete = this.salon.getFenetreJeu().getJeu().getC().prepareStatement("SELECT id FROM joueur");
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) {
                compteur += 1;
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(AcceuilFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (compteur == 0) {
            
            this.salon.setId(1);
            this.salon.getFenetreJeu().getJeu().avatar.setId(1);
//            SalonAttente.getFrames()[2].setVisible(true);
            this.salon.setBoutonVisible(true);
            this.setVisible(false);
            try {
                requete = this.salon.getFenetreJeu().getJeu().getC().prepareStatement("INSERT INTO joueur (id,pseudo,personnage,x,y) VALUES ('1','hote',?,'140','100')");
                requete.setInt(1, this.salon.getFenetreJeu().getJeu().avatar.getPersonnage());
                requete.executeUpdate();
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(AcceuilFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.setVisible(false);
            this.salon.setVisible(true);
        }
        else {
            this.jLabel2.setVisible(true);
            this.jLabel2.setText("Une partie est déjà créée");
        }
//        try {
//            requete = this.jeu.getC().prepareStatement("DELETE FROM joueur");
//            requete.executeUpdate();
//            requete.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(AcceuilFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        this.salon.setId(1);
//        SalonAttente.getFrames()[2].setVisible(true);
//        this.salon.setBoutonVisible(true);
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.choixPerso.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int compteur = 0;
        PreparedStatement requete;
        try {
            requete = this.salon.getFenetreJeu().getJeu().getC().prepareStatement("SELECT id FROM joueur");
            ResultSet resultat = requete.executeQuery();
            while (resultat.next()) {
                compteur += 1;
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(AcceuilFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (compteur != 0) {
//            this.jButton2.setEnabled(false);
            // this.salon.setId(compteur+1);
            System.out.println("compteur");
            System.out.println(compteur);
            this.salon.getFenetreJeu().getJeu().avatar.setId(compteur+1);
            System.out.println(this.salon.getFenetreJeu().getJeu().avatar.getId());
//            SalonAttente.getFrames()[2].setVisible(true);
            this.salon.setBoutonVisible(false);
            this.setVisible(false);
            this.jLabel2.setVisible(false);
            String idVal = String.valueOf(compteur+1);
            try {
                requete = this.salon.getFenetreJeu().getJeu().getC().prepareStatement("INSERT INTO joueur (id,pseudo,personnage,x,y) VALUES (?,'invité',?,'140','100')");
                requete.setString(1, idVal);
                System.out.println(this.salon.getFenetreJeu().getJeu().avatar.getPersonnage());
                requete.setInt(2, this.salon.getFenetreJeu().getJeu().avatar.getPersonnage());
                requete.executeUpdate();
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(AcceuilFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.salon.setVisible(true);
        }
        else {
            this.jLabel2.setVisible(true);
            this.jLabel2.setText("Aucune partie n'est créée");
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AcceuilFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AcceuilFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AcceuilFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AcceuilFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AcceuilFrame().setVisible(true);
            }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                final ImageIcon icon = new ImageIcon("mario.png");
                Image img = icon.getImage();
                try {
                    new AcceuilFrame().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(AcceuilFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private static javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
