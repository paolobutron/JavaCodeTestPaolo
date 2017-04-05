
package com.test.pets.logic;

import com.test.pets.caches.PetsCache;
import com.test.pets.exceptions.ProcessException;
import com.test.pets.exceptions.ValidationException;
import com.test.pets.model.Pet;
import com.test.pets.utils.Constants;
import com.test.pets.validators.PetValidator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
* <p>The PetsLogic class.</p>
* @author Butron Caballero, Paolo Leonel
*/
public class PetsLogic implements IPetsLogic {

    private final PetValidator validator;
    
    
    public PetsLogic() {
        validator = new PetValidator();
    }
    
    @Override
    public void initialLoadPets(InputStream inputStream) throws ProcessException {
        try {
            int countLine = 1;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "Cp1252"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Pet validatedPet = validator.validatePetRecord(line, countLine);
                registerPet(validatedPet);
                countLine++;
                
            }
        } catch (ValidationException | UnsupportedEncodingException ex) {
            System.out.println("(PetsLogic - initialLoadPets) Error en carga inicial de mascotas: " + ex); //All the System.out.println should be a Log Writer in a regular program
            throw new ProcessException(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("(PetsLogic - initialLoadPets) Error en carga inicial de mascotas: " + ex);
            throw new ProcessException(ex.getMessage());
        }
    }
    
    @Override
    public void registerPet(Pet pet) throws ProcessException {
        try {
            PetsCache cache = PetsCache.getInstance();
            cache.putPet(pet);
        }
        catch (Exception ex) {
            System.out.println("(PetsLogic - registerPet) Error al registrar mascota: " + ex);
            throw new ProcessException(ex.getMessage());
        }
    }

    @Override
    public List<Pet> searchPets(String[] args) throws ProcessException {
        List<Pet> searchResult = new ArrayList<>();
        PetsCache cache = PetsCache.getInstance();
        String firstSearchArg;
        String secondSearchArg;
        try {
            String typeOfSearch = validateParametersOfSearch(args);
            switch(typeOfSearch) {
                case Constants.NAME_SEARCH_VALUE:
                    searchResult = cache.getAllPets();
                    firstSearchArg = getParameterFromArg(args[1], 1);
                    searchResult = searchPetsByName(firstSearchArg, searchResult);
                    searchResult = sortPetsByName(searchResult);
                    break;
                case Constants.TYPE_SEARCH_VALUE:
                    firstSearchArg = getParameterFromArg(args[1], 1);
                    searchResult = cache.getPetsByType(firstSearchArg.toUpperCase());
                    searchResult = sortPetsByDate(searchResult);
                    break;
                case Constants.TYPE_GENDER_SEARCH_VALUE:
                    firstSearchArg = getParameterFromArg(args[1], 1);
                    secondSearchArg = getParameterFromArg(args[2], 1);
                    searchResult = cache.getPetsByType(firstSearchArg.toUpperCase());
                    searchResult = searchPetsByGender(String.valueOf(secondSearchArg.charAt(0)), searchResult);
                    searchResult = sortPetsByDate(searchResult);
                    break;
                default:
                    break;
            }
        } catch(ProcessException ex) {
            System.out.println("(PetsLogic - searchPets) Error al buscar mascotas: " + ex);
            throw new ProcessException(ex.getMessage());
        }
        return searchResult;
    }
    
    private String validateParametersOfSearch(String[] args) throws ProcessException {
        if(args.length < 2) {
            throw new ProcessException("Se debe ingresar al menos un parametro para la busqueda!");
        }
        String firstSearchArg = getParameterFromArg(args[1], 0);
        if(firstSearchArg.equals(Constants.INPUT_NAME_SEARCH_VALUE) && args.length == 2) {
            return Constants.NAME_SEARCH_VALUE;
        }
        if(firstSearchArg.equals(Constants.INPUT_TYPE_SEARCH_VALUE) && args.length == 2) {
            return Constants.TYPE_SEARCH_VALUE;
        }
        if(args.length < 3) {
            throw new ProcessException("Los parametros de busqueda son incorrectos!");
        }
        String secondSearchArg = getParameterFromArg(args[2], 0);
        if(firstSearchArg.equals(Constants.INPUT_TYPE_SEARCH_VALUE) && secondSearchArg.equals(Constants.INPUT_GENDER_SEARCH_VALUE)) {
            return Constants.TYPE_GENDER_SEARCH_VALUE;
        }
        throw new ProcessException("Los parametros de busqueda son incorrectos!");
    }
    
    private String getParameterFromArg(String arg, int index) throws ProcessException {
        String[] splitedArg = arg.split("=");
        if(splitedArg.length - 1 < index) {
            throw new ProcessException("Los parametros de busqueda son incorrectos!");
        }
        return splitedArg[index];
    }
    
    private List<Pet> searchPetsByName(String petName, List<Pet> pets) {
    	List<Pet> result = pets.stream()
    		     .filter(item -> item.getName().toUpperCase().contains(petName.toUpperCase()))
    		     .collect(Collectors.toList());
    	return result;
    }
    
    private List<Pet> searchPetsByGender(String petGender, List<Pet> pets) {
    	List<Pet> result = pets.stream()
    		     .filter(item -> item.getGender().toUpperCase().equals(petGender.toUpperCase()))
    		     .collect(Collectors.toList());
    	return result;
    }
    
    private List<Pet> sortPetsByName(List<Pet> pets) {
    	Comparator<Pet> nameComparator = Comparator.comparing(Pet::getName);
	    Collections.sort(pets, nameComparator.reversed());
    	return pets;
    }
    
    private List<Pet> sortPetsByDate(List<Pet> pets) {
    	Comparator<Pet> nameComparator = Comparator.comparing(Pet::getDate);
    	Collections.sort(pets, nameComparator.reversed());
    	return pets;
    }
    
}
