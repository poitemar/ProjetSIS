/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author poite
 */
public enum Specialite {
    Sélectionner,
    ACCUEIL,
    CARDIOLOGIE,
    //...
    GYNECOLOGIE,
    PEDIATRIE,
    PNEUMOLOGIE,
    RADIOLOGIE,
    ANESTHESIE,
    ONCOLOGIE;

    private static Specialite[] value() {
        Specialite[] pres = {ANESTHESIE, RADIOLOGIE};

        return pres;
    }

 
    
  
}

