
package com.test.pets.logic;

import com.test.pets.exceptions.ProcessException;
import com.test.pets.model.Pet;
import java.io.InputStream;
import java.util.List;

/**
* <p>The IPetsLogic class.</p>
* @author Butron Caballero, Paolo Leonel
*/
public interface IPetsLogic {

    void initialLoadPets(InputStream inputStream) throws ProcessException;
    
    void registerPet(Pet pet) throws ProcessException;
    
    List<Pet> searchPets(String[] args) throws ProcessException;
}
