/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;

/**
 *
 * @author Marine
 */
public class Impression implements Printable {
        
    private JTextArea textArea;
    private boolean zoneAImprimer;
    private boolean fitIntoPage;
    private boolean wrapComponent;
    private PageFormat pageFormat;
    private PrinterJob printJob;
    private List taillePage;
    private String documentTitre;

    /**
     *
     */
    public static final int PORTRAIT = 1;

    /**
     *
     */
    public static final int LANDSCAPE = 0;

    /**
     *
     * @param ta
     */
    public Impression(JTextArea ta) {

        documentTitre = "";
        this.textArea = ta;
        initPrintablePanel();
    }

    /**
     *
     */
    public void initPrintablePanel() {

        zoneAImprimer = false;
        fitIntoPage = false;
        wrapComponent = false;
        printJob = PrinterJob.getPrinterJob();
        pageFormat = printJob.defaultPage();
        pageFormat.setOrientation(1);
    }

    /*Permet d'orienter la page a imprimer
     * @param orientation prend un int */

    /**
     *
     * @param orientation
     */

    public void setOrientation(int orientation) {
        pageFormat.setOrientation(orientation);
    }

    /**
     *
     * @param status
     */
    public void setPrintZoneVisible(boolean status) {
        zoneAImprimer = status;
    }

    /**
     *
     * @param status
     */
    public void setWrapComponent(boolean status) {
        wrapComponent = status;
    }

    /**
     *
     * @param status
     */
    public void setFitIntoPage(boolean status) {
        fitIntoPage = status;
    }

    /*Permet d'obtenir la largeur de la page
     * @return un int, la largeur */

    /**
     *
     * @return
     */

    public int getPageWidth() {
        return (int) pageFormat.getImageableWidth();
    }

    /**Permet d'obtenir la taille de la marge en haut
     * @return un double, la taille */
    public double getMarginTop() {
        return pageFormat.getImageableY();
    }

    /*Permet d'obtenir la taille de la marge gauche
     * @return un double, la taille */

    /**
     *
     * @return
     */

    public double getMarginLeft() {
        return pageFormat.getImageableX();
    }

    /**Permet de definir la taille des marges du document, a gauche et a droite
     * @param margin prend un int, la taille des marges*/
    public void setLRMargins(int margin) {
        Paper paper = pageFormat.getPaper();
        paper.setImageableArea(paper.getImageableX() - (double) (margin / 2), paper.getImageableY(), paper.getImageableWidth() + (double) (margin / 2), paper.getImageableHeight());
        pageFormat.setPaper(paper);
    }

    /*Permet de definir la taille des marges du document, en haut et en bas
     * @param margin prend un int, la taille des marges */

    /**
     *
     * @param margin
     */

    public void setTBMargins(int margin) {
        Paper paper = pageFormat.getPaper();
        paper.setImageableArea(paper.getImageableX(), paper.getImageableY() - (double) (margin / 2), paper.getImageableWidth(), paper.getImageableHeight() + (double) (margin / 2));
        pageFormat.setPaper(paper);
    }

    /**Permet de definir le titre d'un document
     * @param title prend un String, le titre */
    public void setDocumentTitle(String title) {
        documentTitre = title;
    }

    /** Methode permettant d'imprimer
     * @param graph
     * @param g prend des Graphics
     * @param pf prend un PageFormat
     * @param pageIndex prend un int
     * @return un int, 0
     * @throws PrinterException ?? */
    @Override
    public int print(Graphics graph, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Dimension tailleDoc = textArea.getSize();
        double hauteurDocument = tailleDoc.getHeight();
        double hauteurPage = pageFormat.getImageableHeight();
        double largeurDocument = tailleDoc.getWidth();
        double largeurPage = pageFormat.getImageableWidth();
        int totalNumPages = (int) Math.ceil(hauteurDocument / hauteurPage);
        if (wrapComponent) {
            totalNumPages = taillePage.size();
        } else if (fitIntoPage) {
            totalNumPages = 1;
        }
        double scaleX = largeurPage / largeurDocument;
        double scaleY = hauteurPage / hauteurDocument;
        if (pageIndex >= totalNumPages) {
            return 1;
        }
        Graphics2D g2d = (Graphics2D) graph;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        if (fitIntoPage) {
            double ratio = Math.min(scaleX, scaleY);
            g2d.scale(ratio, ratio);
        } else if (wrapComponent) {
            if (pageIndex > 0) {
                g2d.translate(0.0D,-((Double) taillePage.get(pageIndex - 1)).doubleValue());
            }
        } else {
            g2d.translate(0.0D, (double) (-pageIndex) * hauteurPage);
        }
        textArea.paint(g2d);
        if (wrapComponent) {
            double hauteurBlanc = ((Double) taillePage.get(pageIndex)).doubleValue();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, (int) hauteurBlanc, (int) largeurPage, (int) hauteurBlanc + (int) hauteurPage);
        }
        if (wrapComponent) {
            if (pageIndex > 0) {
                g2d.translate(0.0D, ((Double) taillePage.get(pageIndex - 1)).doubleValue());
            }
        } else {
            g2d.translate(0.0D, (double) pageIndex * hauteurPage);
        }
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Verdanna", 2, 10));
        g2d.drawString(documentTitre + " - [" + (pageIndex + 1) + "/" + totalNumPages + "]", 0,
                (int) pageFormat.getImageableHeight() - 20);
        return 0;
    }

    /**
     * Methode lancant l'impression
     * @return 
     */
    public boolean print() {

        printJob.setPrintable(this, pageFormat);
        try {
            if (printJob.printDialog()) {
                if (wrapComponent) {
                    calculatePages();
                }
                Paper paper = pageFormat.getPaper();
                Paper save = pageFormat.getPaper();
                paper.setImageableArea(paper.getImageableX(), paper.getImageableY(), 
                        paper.getWidth() - paper.getImageableX(), paper.getHeight() - paper.getImageableY());
                pageFormat.setPaper(paper);
                printJob.setPrintable((Printable) this, pageFormat);
                printJob.print();
                pageFormat.setPaper(save);
            }
            return true;
        } catch (PrinterException pe) {
            System.out.println("Erreur lors de l'impression du document : " + toString());
            return false;
        }
        
    }

    /**
     * Calcule le nombre de pages a imprimer
     */
    private void calculatePages() {
        taillePage = new ArrayList();
        double hauteurPage = pageFormat.getImageableHeight();
        double hauteurTotal = 0.0D;
        double hauteurCumul = 0.0D;
        for (int i = 0; i < textArea.getComponentCount(); i++) {
            int gridBagInsets = 0;
            if (textArea.getLayout() instanceof GridBagLayout) {
                gridBagInsets = ((GridBagLayout) textArea.getLayout())
                        .getConstraints(textArea.getComponent(i)).insets.bottom 
                        + ((GridBagLayout) textArea.getLayout()).getConstraints(textArea.getComponent(i)).insets.top;
            }
            double hauteurComponent = textArea.getComponent(i).getSize().getHeight() + (double) gridBagInsets;
            if (hauteurComponent > hauteurPage) {
                wrapComponent = false;
                return;
            }
            hauteurTotal += hauteurComponent;
            if (hauteurTotal > hauteurPage) {
                hauteurTotal -= hauteurComponent;
                hauteurCumul += hauteurTotal;
                taillePage.add(hauteurCumul);
                hauteurTotal = hauteurComponent;
            }
        }

        hauteurCumul += hauteurTotal;
        taillePage.add(hauteurCumul);
    }

}

