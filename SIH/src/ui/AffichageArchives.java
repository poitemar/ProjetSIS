/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.List;
import java.util.TreeMap;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.DefaultListModel;
import nf.Lit;
import nf.Orientation;
import nf.Service;
import nf.Specialite;

/**
 *
 * @author poite
 */
public class AffichageArchives extends javax.swing.JFrame {
    nf.Archivage archive = new nf.Archivage();
    /**
     * Creates new form AffichageArchives
     */
   nf.PH ph = new nf.PH("null", "null", "null", "null", "null", nf.Specialite.ONCOLOGIE, nf.Service.CLINIQUE);
    nf.Patient patient = new nf.Patient("bluff", "bluff");
    nf.SecretaireMedicale secrMed = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);
    nf.Localisation locCourant = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 133, Lit.PORTE);
    nf.Sejour sejourFini = new nf.Sejour("bluff", "bluff", "bluff", locCourant);
    private boolean phref = false;
    String PatientSelection ="";
    private DefaultTreeCellRenderer tCellRenderer = new DefaultTreeCellRenderer();
       DefaultListModel DLM = new DefaultListModel();
    ArrayList<nf.Patient> listePatient = new ArrayList<nf.Patient>();
    
    
    
    public AffichageArchives(){
        initComponents();
        this.setSize(700, 600);
        listePatient = archive.afficherListePatientMort();

        for (int i = 0; i < listePatient.size(); i++) {

            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();

            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, ERROR, ABORT, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.CLINIQUE);
           
            
                DLM.addElement(element);
                
            
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

    /**
     *
     */
    public void buildTree() {
        DM.setCellRenderer(this.tCellRenderer);
        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();

        }
        List<String> listeIdSejours = sejourFini.listeSejour(patient.ippPatientListearchive(PatientSelection));
        List<String> listedateSaisiearchive;
        List<String> listeDateLoc;
        List<String> listeLoc;
        TreeMap<String, String> listeInfosarchive;
        String dateS = "";
        java.util.Date date1 = new java.util.Date();
        java.util.Date date2 = new java.util.Date();
        String dateS2 = "";
        javax.swing.tree.DefaultMutableTreeNode racine = new javax.swing.tree.DefaultMutableTreeNode("Mme/M." + patient.patientListe(PatientSelection));
         javax.swing.tree.DefaultMutableTreeNode mort = new javax.swing.tree.DefaultMutableTreeNode(archive.infoMort(patient.ippPatientListearchive(PatientSelection)));
       racine.add(mort);
        for (int i = 0; i < listeIdSejours.size(); i++) {
            listedateSaisiearchive = sejourFini.listedateSaisiearchive(listeIdSejours.get(i));
            listeDateLoc = sejourFini.listeLoc(listeIdSejours.get(i));
            System.out.println("REGARDE PAR IC ++++++++++++++++++++++++++++     ");
            System.out.println(listedateSaisiearchive);
            System.out.println(listeDateLoc);
            javax.swing.tree.DefaultMutableTreeNode sejour = new javax.swing.tree.DefaultMutableTreeNode(sejourFini.listeSejourArchivetoString(listeIdSejours.get(i)));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            int isaisie = 0, iloc = 0;

            String dsaisie = null, loc = null;

            javax.swing.tree.DefaultMutableTreeNode localisation, saisie;

            while (isaisie < listedateSaisiearchive.size() && iloc < listeDateLoc.size()) {

                dsaisie = listedateSaisiearchive.get(isaisie);
                loc = listeDateLoc.get(iloc);

                localisation = new javax.swing.tree.DefaultMutableTreeNode(sejourFini.listeLoctoString(loc, listeIdSejours.get(i)));
                saisie = new javax.swing.tree.DefaultMutableTreeNode(sejourFini.listeSaisieArchivetoString(dsaisie, listeIdSejours.get(i)));

                try {
                    date1 = format.parse(dsaisie);
                    date2 = format.parse(loc);
                } catch (ParseException ex) {

                   
                }

                if (date1.compareTo(date2) > 0) {

                    sejour.add(localisation);

                    iloc++;
                } else {
                    sejour.add(saisie);

                    listeInfosarchive = sejourFini.listeInfosarchive(dsaisie, listeIdSejours.get(i));

                    for (Map.Entry mapentry : listeInfosarchive.entrySet()) {
                        String[] tab = mapentry.getKey().toString().split("X");
                        for (int k = 1; k < tab.length; k++) {
                            javax.swing.tree.DefaultMutableTreeNode info = new javax.swing.tree.DefaultMutableTreeNode(tab[k] + " : " + mapentry.getValue());
                            saisie.add(info);
                        }
                    }

                    isaisie++;
                }

            }

            if (isaisie == listedateSaisiearchive.size()) {
                while (iloc < listeDateLoc.size()) {
                    loc = listeDateLoc.get(iloc);
                    localisation = new javax.swing.tree.DefaultMutableTreeNode(sejourFini.listeLoctoString(loc, listeIdSejours.get(i)));
                    sejour.add(localisation);
                    iloc++;
                }
            } else if (iloc == listeDateLoc.size()) {
                while (isaisie < listedateSaisiearchive.size()) {
                    dsaisie = listedateSaisiearchive.get(isaisie);
                    saisie = new javax.swing.tree.DefaultMutableTreeNode(sejourFini.listeSaisieArchivetoString(dsaisie, listeIdSejours.get(i)));
                    sejour.add(saisie);

                    listeInfosarchive = sejourFini.listeInfosarchive(dsaisie, listeIdSejours.get(i));

                    for (Map.Entry mapentry : listeInfosarchive.entrySet()) {
                        String[] tab = mapentry.getKey().toString().split("X");
                        for (int k = 1; k < tab.length; k++) {
                            javax.swing.tree.DefaultMutableTreeNode info = new javax.swing.tree.DefaultMutableTreeNode(tab[k] + " : " + mapentry.getValue());
                            saisie.add(info);
                        }
                    }

                    isaisie++;
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

        jPanel1 = new javax.swing.JPanel();
        jList1 = new javax.swing.JList<>();
        DM = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });

        if(jList1.isSelectionEmpty()){
            javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Selectionnez un patient");
            DM.setModel(new javax.swing.tree.DefaultTreeModel(treeNode3));
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jList1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(DM, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DM, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jList1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        
         if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();
            initRenderer();
            buildTree();}
         
    }//GEN-LAST:event_jList1ValueChanged

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
            java.util.logging.Logger.getLogger(AffichageArchives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AffichageArchives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AffichageArchives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AffichageArchives.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AffichageArchives().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree DM;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
