package com.test.pets.logic;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.test.pets.caches.PetsCache;
import com.test.pets.exceptions.ProcessException;
import com.test.pets.model.Pet;

public class PetsLogicTest {

	private final InputStream inputStream;
	
	public PetsLogicTest() throws UnsupportedEncodingException {
		String document = "DOG,Driver,F,20130129-080903\r\nCAT,Fluffy,M,20131231-145934";
	    byte[] data = document.getBytes("UTF-8");
	    this.inputStream = new ByteArrayInputStream(data);
	}
	
	
	@Test
	public void test_correctInitialLoadPets() throws ProcessException {
		System.out.println("test_correctInitialLoadPets");
		        
		PetsLogic instance = new PetsLogic();
        instance.initialLoadPets(inputStream);
        
        PetsCache cache = PetsCache.getInstance();
        
        assertNotNull(cache);
        assertEquals(1, cache.getPetsByType("DOG").size());
        assertEquals(2, cache.getAllPets().size());
        
        cache.clearCache();
	}
	
	@Test(expected = ProcessException.class)
	public void test_invalidInitialLoadPets() throws ProcessException, UnsupportedEncodingException {
		System.out.println("test_invalidInitialLoadPets");
		
		String document = "Invalid stream";
	    byte[] data = document.getBytes("UTF-8");
	    InputStream invalidStream = new ByteArrayInputStream(data);
		
		PetsLogic instance = new PetsLogic();
        instance.initialLoadPets(invalidStream);
	}
	
	@Test
	public void test_correctSearchPetsByName() throws ProcessException {
		System.out.println("test_correctSearchPetsByName");
		
		PetsLogic instance = new PetsLogic();
		
		Pet pet1 = new Pet("DOG", "name1", "F", new Date());
		Pet pet2 = new Pet("CAT", "name2", "F", new Date());
		Pet pet3 = new Pet("DOG", "name23", "F", new Date());
        
        PetsCache cache = PetsCache.getInstance();
        cache.putPet(pet1);
        cache.putPet(pet2);
        cache.putPet(pet3);
        
        String[] argsSearch1 = {"file", "name=name1"};
        String[] argsSearch2 = {"file", "name=name2"};
        
        List<Pet> search1 = instance.searchPets(argsSearch1);
        List<Pet> search2 = instance.searchPets(argsSearch2);
        
        assertNotNull(search1);
        assertNotNull(search2);
        assertEquals(1, search1.size());
        assertEquals(2, search2.size());
        
        cache.clearCache();
	}
	
	@Test
	public void test_correctSearchPetsByType() throws ProcessException {
		System.out.println("test_correctSearchPetsByType");
		
		PetsLogic instance = new PetsLogic();
		
		Pet pet1 = new Pet("DOG", "name1", "F", new Date());
		Pet pet2 = new Pet("CAT", "name2", "F", new Date());
		Pet pet3 = new Pet("DOG", "name23", "F", new Date());
        
        PetsCache cache = PetsCache.getInstance();
        cache.putPet(pet1);
        cache.putPet(pet2);
        cache.putPet(pet3);
        
        String[] argsSearch1 = {"file", "type=DOG"};
        String[] argsSearch2 = {"file", "type=CAT"};
        
        List<Pet> search1 = instance.searchPets(argsSearch1);
        List<Pet> search2 = instance.searchPets(argsSearch2);
        
        assertNotNull(search1);
        assertNotNull(search2);
        assertEquals(2, search1.size());
        assertEquals(1, search2.size());
        
        cache.clearCache();
	}
	
	@Test
	public void test_correctSearchPetsByTypeAndGender() throws ProcessException {
		System.out.println("test_correctSearchPetsByTypeAndGender");
		
		PetsLogic instance = new PetsLogic();
		
		Pet pet1 = new Pet("DOG", "name1", "F", new Date());
		Pet pet2 = new Pet("CAT", "name2", "F", new Date());
		Pet pet3 = new Pet("DOG", "name23", "M", new Date());
        
        PetsCache cache = PetsCache.getInstance();
        cache.putPet(pet1);
        cache.putPet(pet2);
        cache.putPet(pet3);
        
        String[] argsSearch1 = {"file", "type=DOG", "gender=Masc"};
        String[] argsSearch2 = {"file", "type=CAT", "gender=Masc"};
        
        List<Pet> search1 = instance.searchPets(argsSearch1);
        List<Pet> search2 = instance.searchPets(argsSearch2);
        
        assertNotNull(search1);
        assertNotNull(search2);
        assertEquals(1, search1.size());
        assertEquals(0, search2.size());
        
        cache.clearCache();
	}
	
	
	

}
