
package com.test.pets.caches;

import com.test.pets.model.Pet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* <p>The PetsCache class.</p>
* @author Butron Caballero, Paolo Leonel
*/
public class PetsCache {

    private final Map<String, List<Pet>> petsCache = new HashMap<>();    
    private static PetsCache instance;

    private PetsCache() {        
    }
    
    public static synchronized PetsCache getInstance(){
        if (instance == null){
            instance = new PetsCache();
        }        
        return instance;
    }
    
    public synchronized void putAll(Map<String, List<Pet>> cache) {
        petsCache.putAll(cache);        
    }
    
    public synchronized List<Pet> getPetsByType(String petType){
    	return (petsCache.containsKey(petType)) ? petsCache.get(petType) : new ArrayList<>();
    }
    
    public synchronized void putPet(Pet pet){
        List<Pet> pets = new ArrayList<>();
        if(petsCache.containsKey(pet.getType())) {
            pets = petsCache.get(pet.getType());
            pets.add(pet);
        } else {
            pets.add(pet);
            
        }
        petsCache.put(pet.getType(), pets);
    }
    
    public synchronized void clearCache() {
    	petsCache.clear();
    }
    
    public synchronized void removePet(String petType, Pet pet){
        if(petsCache.containsKey(petType)) {
            List<Pet> pets = petsCache.get(petType);
            pets.remove(pet);
            petsCache.put(petType, pets);
        }
    }
    
    public synchronized List<Pet> getAllPets(){
        List<Pet> allPets = new ArrayList<>();
        for(String type : petsCache.keySet()) {
            allPets.addAll(petsCache.get(type));
        }
        return allPets;
    }
    
}
