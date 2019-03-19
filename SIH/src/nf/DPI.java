/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nf;

/**
 *
 * @author poite
 */

public class DPI {
 
    private Patient patient;
    private DMA dma;
    private DM dm;
    
    /**
     *
     * @param p
     * @param dm
     * @param dma
     */
    public DPI(Patient p, DM dm, DMA dma){
        this.patient =patient;
        this.dma=dma;
        this.dm=dm;
    }
    
    
    
    
}
