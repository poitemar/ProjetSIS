/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

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
import nf.RechercherInfo;
import nf.Service;
import nf.Specialite;

/**
 *
 * @author poite
 */
public class Radiologue extends javax.swing.JFrame {
    nf.PersonnelMedical perso;
     nf.SecretaireMedicale secrMed = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);
    nf.Localisation locCourant = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 133, Lit.PORTE);
    nf.Sejour sejourCourant = new nf.Sejour("bluff", "bluff", "bluff", locCourant);
     DefaultListModel DLM = new DefaultListModel();
      ArrayList<Patient> listePatient;
       nf.Patient patient = new nf.Patient("bluff", "bluff");
       String prestationSelection;
        private  DefaultTreeCellRenderer tCellRenderer = new  DefaultTreeCellRenderer();
       String PatientSelection;
      
    /**
     * Creates new form Radiologue
     */
    public Radiologue(nf.PersonnelMedical p) {
         initComponents();
           setSize(700, 600);
           
        this.perso=p;
        String s = "Dr. " + p.getNom() + " " + p.getPrenom();
        System.out.println(s);
        nomRadiologue.setText(s);
        spe.setText(perso.getSpecialite().toString());
        listePatient = perso.afficherListePatientPrestation(perso.getIdMed());
        ArrayList<String> listePrestation = perso.afficherPrestation(perso.getIdMed());
        System.out.println(listePatient.size());
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
                System.out.println("MARGAUX §§§§");
            }
            jList1.setModel(DLM);
            jList1.repaint();
        }}

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
             listeInfos = sejourCourant.listeInfosRadio(listedateSaisie.get(j), listeIdSejours.get(i));
            
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

        tab = new javax.swing.JTabbedPane();
        accueil = new javax.swing.JPanel();
        nomRadiologue = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        spe = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pres = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        DM = new javax.swing.JTree();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textCR = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tab.setPreferredSize(new java.awt.Dimension(700, 600));
        tab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMouseClicked(evt);
            }
        });

        accueil.setBackground(new java.awt.Color(255, 255, 255));

        nomRadiologue.setBackground(new java.awt.Color(0, 153, 153));
        nomRadiologue.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        nomRadiologue.setText("Dr DUPONT Alexandre");
        nomRadiologue.setOpaque(true);
        accueil.add(nomRadiologue);

        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des prestations", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        accueil.add(jScrollPane2);

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Suivant");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        accueil.add(jButton3);

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("Déconnexion");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        accueil.add(jButton4);

        spe.setText(" ");
        accueil.add(spe);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("Prestation à effectuer :");
        accueil.add(jLabel3);

        pres.setText(" ");
        accueil.add(pres);

        tab.addTab("ACCUEIL", accueil);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText(" ");

        jScrollPane3.setViewportView(jTree1);

        jScrollPane4.setViewportView(DM);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Dossier Medical Administratif :");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Dossier Médical :");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 245, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        tab.addTab("INFORMATIONS PATIENT", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        textCR.setColumns(20);
        textCR.setRows(5);
        jScrollPane1.setViewportView(textCR);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Compte rendu radiologique :");

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        tab.addTab("COMPTE RENDU ", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
          new Connexion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
      if(!jList1.isSelectionEmpty()) {
          PatientSelection = jList1.getSelectedValue().toString();
          tab.setSelectedIndex(1);
      }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(!jList1.isSelectionEmpty()){
        PatientSelection = jList1.getSelectedValue().toString();
        System.out.println("HEEEEEE");
        String lecture = jList1.getSelectedValue();
        prestationSelection = perso.prestationPatientListe(lecture);
        System.out.println("help"+prestationSelection);
        nf.PH ph = new nf.PH("","","","","",Specialite.ACCUEIL,Service.ACCUEIL);
        sejourCourant.ajouterCompteRendu(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(),patient.ippPatientListe(PatientSelection),prestationSelection, textCR.getText());
        textCR.setText("");}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
            if( jList1.isSelectionEmpty()){
         
           pres.setText("");
        }
        else {
            String lecture = jList1.getSelectedValue();
              prestationSelection = perso.prestationPatientListe(lecture);
              initRenderer();
              buildTree();
            pres.setText(prestationSelection);
            }
        
           
        
    }//GEN-LAST:event_jList1ValueChanged

    private void tabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMouseClicked
        // TODO add your handling code here:
        DLM.clear();
        ArrayList<String> listePrestation = perso.afficherPrestation(perso.getIdMed());
        System.out.println(listePatient.size());
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
                System.out.println("MARGAUX §§§§");
            }
            jList1.setModel(DLM);
            jList1.repaint();
        }
        
    }//GEN-LAST:event_tabMouseClicked

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
            java.util.logging.Logger.getLogger(Radiologue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Radiologue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Radiologue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Radiologue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             //   new Radiologue().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree DM;
    private javax.swing.JPanel accueil;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel nomRadiologue;
    private javax.swing.JLabel pres;
    private javax.swing.JLabel spe;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTextArea textCR;
    // End of variables declaration//GEN-END:variables
}
