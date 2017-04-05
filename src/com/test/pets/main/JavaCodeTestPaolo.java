
package com.test.pets.main;

import com.test.pets.exceptions.ProcessException;
import com.test.pets.logic.IPetsLogic;
import com.test.pets.logic.PetsLogic;
import com.test.pets.model.Pet;
import com.test.pets.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
* <p>The JavaCodeTestPaolo class.</p>
* @author Butron Caballero, Paolo Leonel
*/
public class JavaCodeTestPaolo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String baseFile = args[0];
        IPetsLogic logic = new PetsLogic();
        File initialFile = new File(baseFile);
        try {
            InputStream stream = new FileInputStream(initialFile);
            logic.initialLoadPets(stream);
            List<Pet> searchPets = logic.searchPets(args);
            System.out.println("Mascotas encontradas en su busqueda: " + searchPets.size());
            for(Pet pet : searchPets) {
                System.out.println(pet.getType() + " - " + pet.getName() + " - " 
                        + pet.getGender() + " - " + Constants.USER_DATE_FORMAT.format(pet.getDate()));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se encontro el archivo inicial, ex: " + ex.getMessage());
        } catch (ProcessException ex) {
            System.out.println("Error al procesar archivo inicial, ex: " + ex.getMessage());
        }
    }
    
}
