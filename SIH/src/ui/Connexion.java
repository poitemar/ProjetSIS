/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javax.swing.JOptionPane;
import nf.Service;
import nf.Sexe;
import nf.Specialite;

/**
 *
 * @author poite
 */
public class Connexion extends javax.swing.JFrame {

    nf.Connexion cx = new nf.Connexion();
    nf.PersonnelMedical personnel;

    /**
     * Creates new form Connexion
     */
    public Connexion() {
        initComponents();
        setSize(900, 800);
        this.setResizable(false);
        this.pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        saisieId = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        saisieMdp = new javax.swing.JPasswordField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(900, 800));
        setSize(new java.awt.Dimension(0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(900, 800));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPHlogo.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel1.setText("Connexion");
        jLabel1.setOpaque(true);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Identifiant : ");

        saisieId.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(saisieId);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Mot de passe :");

        saisieMdp.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        saisieMdp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saisieMdpMouseClicked(evt);
            }
        });
        saisieMdp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saisieMdpActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Valider");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(418, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(400, 400, 400))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(321, 321, 321))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(342, 342, 342))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(358, 358, 358))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saisieMdp, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(371, 371, 371))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3, jScrollPane1, saisieMdp});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saisieMdp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jButton3)
                .addContainerGap(174, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, jScrollPane1, saisieMdp});

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 348, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saisieMdpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saisieMdpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saisieMdpActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Je cree un personnel qui recupere les infos de celui qui se connecte pour le faire passer aux prochaines interfaces par le constructeur 
        nf.SecretaireMedicale secrMed = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);

        if (cx.choixPersonnel(saisieId.getText(), saisieMdp.getText()).equals("Erreur de connexion")) {

            try {
                System.out.println("TEST2    " + cx.seConnecter(saisieId.getText(), saisieMdp.getText(), false));
                if (cx.seConnecter(saisieId.getText(), saisieMdp.getText(), false).equals("ERREUR")) {
                    JOptionPane.showMessageDialog(this, "L'identifiant ou le mot de passe saisi est incorrect, Veuillez réessayer", "ATTENTION", JOptionPane.ERROR_MESSAGE);
                    this.dispose();
                    new Connexion().setVisible(true);
                }
                if (cx.seConnecter(saisieId.getText(), saisieMdp.getText(), false).equals("PATIENT")) {
                    nf.Patient pat = new nf.Patient("null", "null", "null", Sexe.FEMME, "null", "null", "null", "", "", "", "");
                    pat = secrMed.recuperationPatient(cx.choixPatient(saisieId.getText(), saisieMdp.getText()));
                    new Patient(pat).setVisible(true);
                    this.dispose();
                }
            } catch (Exception ex) {
                System.out.println(ex);

            }
        } else {
            personnel = new nf.PersonnelMedical(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText()), cx.nomPersonnel(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText())), cx.prenomPersonnel(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText())), saisieId.getText(), saisieMdp.getText(), cx.spePersonnel(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText())), cx.ServicePersonnel(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText())));

            try {

                if (cx.seConnecter(saisieId.getText(), saisieMdp.getText(), true).equals("ERREUR")) {
                    JOptionPane.showMessageDialog(this, "L'identifiant ou le mot de passe saisi est incorrect, Veuillez réessayer", "ATTENTION", JOptionPane.ERROR_MESSAGE);
                    this.dispose();
                    new Connexion().setVisible(true);
                }
                //connexion d'une infirmiere
                if (cx.seConnecter(saisieId.getText(), saisieMdp.getText(), true).equals("INFIRMIERE")) {
                    new Infirmiere(personnel).setVisible(true);

                    this.dispose();
                }
                //connexion d'une secretaire administrative
                if (cx.seConnecter(saisieId.getText(), saisieMdp.getText(), true).equals("SECRETAIRE_ADMINISTRATIVE")) {
                    new SecretaireAdministrative(personnel).setVisible(true);

                    this.dispose();
                }
                //connexion d'une secretaire medicale
                if (cx.seConnecter(saisieId.getText(), saisieMdp.getText(), true).equals("SECRETAIRE_MEDICALE")) {
                    if (cx.spePersonnel(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText())).toString().equals("URGENCE")) {
                        new SecretaireMedicaleUrgence(personnel).setVisible(true);
                        this.dispose();
                        System.out.println(personnel.getService());
                    } else {
                        new SecretaireMedicale(personnel).setVisible(true);
                        this.dispose();
                        System.out.println(personnel.getService());
                    }
                }
                //connexion d'un ph
                if (cx.seConnecter(saisieId.getText(), saisieMdp.getText(), true).equals("DOCTEUR")) {
                    System.out.println(cx.spePersonnel(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText())).toString());
                    if (cx.spePersonnel(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText())).toString().equals("RADIOLOGIE")) {
                        new Radiologue(personnel).setVisible(true);
                        this.dispose();
                    } else if (cx.spePersonnel(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText())).toString().equals("ANESTHESIE")) {
                        new Anesthesiste(personnel).setVisible(true);
                        this.dispose();
                    } else if (cx.ServicePersonnel(cx.choixPersonnel(saisieId.getText(), saisieMdp.getText())).toString().equals("MEDICO_TECHNIQUE")) {

                        new PH_MT(personnel).setVisible(true);

                        this.dispose();
                    } else {
                        new PH(personnel).setVisible(true);
                        this.dispose();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);

            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void saisieMdpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saisieMdpMouseClicked
        saisieMdp.setText("");
    }//GEN-LAST:event_saisieMdpMouseClicked

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
            java.util.logging.Logger.getLogger(Connexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Connexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Connexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Connexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Connexion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane saisieId;
    private javax.swing.JPasswordField saisieMdp;
    // End of variables declaration//GEN-END:variables
}
