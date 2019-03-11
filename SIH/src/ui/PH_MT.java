/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import static java.awt.image.ImageObserver.ABORT;
import static java.awt.image.ImageObserver.ERROR;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import nf.Lit;
import nf.Orientation;
import nf.Patient;
import nf.Service;
import nf.Specialite;

/**
 *
 * @author poite
 */
public class PH_MT extends javax.swing.JFrame {

    nf.PersonnelMedical perso;
    ArrayList<Patient> listePatient;
    DefaultListModel DLM = new DefaultListModel();
    nf.Patient patient = new nf.Patient("bluff", "bluff");
    nf.Localisation locCourant = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 133, Lit.PORTE);
    nf.Sejour sejourCourant = new nf.Sejour("bluff", "bluff", "bluff", locCourant);
    String PatientSelection;
     String prestationSelection;
     nf.PH ph = new nf.PH("null", "null", "null", "null", "null", nf.Specialite.ONCOLOGIE, nf.Service.CLINIQUE);
     private  DefaultTreeCellRenderer tCellRenderer = new  DefaultTreeCellRenderer();
    /**
     * Creates new form PH_MT
     */
    public PH_MT(nf.PersonnelMedical p) {

        initComponents();
        setSize(700, 600);

        this.perso = p;
         String s = "Mme/M. " + p.getNom() + " " + p.getPrenom();
        nomMed.setText(s);
        spe.setText(p.getSpecialite().toString());
        listePatient = perso.afficherListePatientPrestation(perso.getIdMed());
        ArrayList<String> listePrestation = perso.afficherPrestation(perso.getIdMed());

        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance()+"         "+listePrestation.get(i);
            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 12, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.URGENCE);
            String ipp = patient.ippPatientListe(element);
            String idDernierSejour = ph.idSejourPatientSelection(ipp);
            System.out.println(listePatient.size());
            if (sejourBluff.sejourEnCours(idDernierSejour) && !sejourCourant.prestationRealisee(listePrestation.get(i))) {
                DLM.addElement(element);
                 System.out.println("OK OK OK ");
            }
          
        }
             jList1.setModel(DLM);
            jList1.repaint();
    }

     private void initRenderer() {
        //Instanciation
       
        this.tCellRenderer.setClosedIcon(null);
        this.tCellRenderer.setOpenIcon(null);
        this.tCellRenderer.setLeafIcon(null);
    }
     public void buildTree(){
         
       if(!jList1.isSelectionEmpty()){
          PatientSelection = jList1.getSelectedValue().toString();
         
      }
        DM.setCellRenderer(this.tCellRenderer);
       List <String> listeIdSejours = sejourCourant.listeSejour(patient.ippPatientListe(PatientSelection));
       List <String> listedateSaisie;
         TreeMap <String,String> listeInfos ;
        
         
     javax.swing.tree.DefaultMutableTreeNode racine = new javax.swing.tree.DefaultMutableTreeNode("Mme/M." + patient.patientListe(PatientSelection));
     
     for (int i =0;i<listeIdSejours.size();i++){
         listedateSaisie = sejourCourant.listeSaisie(listeIdSejours.get(i));
    
         javax.swing.tree.DefaultMutableTreeNode sejour = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeSejourtoString(listeIdSejours.get(i)));
     
         for(int j =0;j<listedateSaisie.size();j++){
             javax.swing.tree.DefaultMutableTreeNode saisie = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeSaisietoString(listedateSaisie.get(j),listeIdSejours.get(i)));
             sejour.add(saisie);
             listeInfos = sejourCourant.listeInfosPHMT(listedateSaisie.get(j), listeIdSejours.get(i));
            
             for(Map.Entry mapentry : listeInfos.entrySet()){
                String[] tab = mapentry.getKey().toString().split("X");
                for(int k =1;k<tab.length;k++){
                javax.swing.tree.DefaultMutableTreeNode info = new javax.swing.tree.DefaultMutableTreeNode(tab[k]+" : "+mapentry.getValue());
                saisie.add(info);
             }
             }
         }
         
         
         racine.add(sejour);
          
     }
     
    
     DefaultTreeModel arbre = new DefaultTreeModel(racine);
     DM.setModel(arbre);
     
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PH_MT = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<String>();
        jLabel3 = new javax.swing.JLabel();
        nomMed = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        labelPrestation = new javax.swing.JLabel();
        nomCo = new javax.swing.JLabel();
        spe = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        DM = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Resultat = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        titrePrestation = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PH_MT.setPreferredSize(new java.awt.Dimension(700, 600));
        PH_MT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PH_MTMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des prestations", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jList1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("Prestation à effectuer :");

        jButton2.setText("Déconnexion");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Suivant");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        labelPrestation.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nomCo, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nomMed, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(98, 98, 98)
                                .addComponent(spe, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(labelPrestation, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(105, 105, 105))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nomCo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(nomMed, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelPrestation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(72, 72, 72))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(81, 81, 81))))
        );

        PH_MT.addTab("ACCUEIL", jPanel1);

        jLabel10.setText("Dossier Médical Administratif :");

        jLabel11.setText("Dossier Médical :");

        jScrollPane3.setViewportView(jTree1);

        jScrollPane4.setViewportView(DM);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        PH_MT.addTab("INFORMATIONS PATIENT", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        Resultat.setColumns(20);
        Resultat.setRows(5);
        Resultat.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Résultats", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jScrollPane2.setViewportView(Resultat);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setText("Prestation :");

        jButton1.setText("Envoyer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(68, 68, 68)
                            .addComponent(titrePrestation, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(titrePrestation))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        PH_MT.addTab("COMPLETER", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PH_MT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PH_MT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        PatientSelection = jList1.getSelectedValue().toString();
        System.out.println("HEEEEEE");
        String lecture = jList1.getSelectedValue();
        prestationSelection = perso.prestationPatientListe(lecture);
        System.out.println("help"+prestationSelection);
        sejourCourant.ajouterResultatPrestation(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(),patient.ippPatientListe(PatientSelection),prestationSelection, Resultat.getText());
        Resultat.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new Connexion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
      
        if( jList1.isSelectionEmpty()){
         
            labelPrestation.setText("");
        }
        else {
            String lecture = jList1.getSelectedValue();
             prestationSelection = perso.prestationPatientListe(lecture);
             initRenderer();
             buildTree();
             labelPrestation.setText(prestationSelection);
        }
       
    }//GEN-LAST:event_jList1ValueChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       if(!jList1.isSelectionEmpty()){
           PatientSelection = jList1.getSelectedValue().toString();
           PH_MT.setSelectedIndex(1);
       }
         
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jPanel1MouseClicked

    private void PH_MTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PH_MTMouseClicked
        // TODO add your handling code here:
         DLM.clear();
        listePatient = perso.afficherListePatientPrestation(perso.getIdMed());
        ArrayList<String> listePrestation = perso.afficherPrestation(perso.getIdMed());

        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance()+"         "+listePrestation.get(i);
            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 12, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.URGENCE);
            String ipp = patient.ippPatientListe(element);
            String idDernierSejour = ph.idSejourPatientSelection(ipp);
           
            if (sejourBluff.sejourEnCours(idDernierSejour) && !sejourCourant.prestationRealisee(listePrestation.get(i))) {
                DLM.addElement(element);
            }
            jList1.setModel(DLM);
            jList1.repaint();
        }
    }//GEN-LAST:event_PH_MTMouseClicked

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
            java.util.logging.Logger.getLogger(PH_MT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PH_MT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PH_MT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PH_MT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //  new PH_MT().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree DM;
    private javax.swing.JTabbedPane PH_MT;
    private javax.swing.JTextArea Resultat;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel labelPrestation;
    private javax.swing.JLabel nomCo;
    private javax.swing.JLabel nomMed;
    private javax.swing.JLabel spe;
    private javax.swing.JLabel titrePrestation;
    // End of variables declaration//GEN-END:variables
}
