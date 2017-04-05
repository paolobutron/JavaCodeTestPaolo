
package com.test.pets.model;

import java.util.Date;

/**
* <p>The Pet class.</p>
* @author Butron Caballero, Paolo Leonel
*/
public class Pet implements Comparable<Pet>{

    private final String type;
    private final String name;
    private final String gender;
    private final Date date;
    
    public Pet(String type, String name, String gender, Date date) {
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public Date getDate() {
        return date;
    }
    
    @Override
    public int compareTo(Pet o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pet other = (Pet) obj;
        if (!this.getType().equals(other.getType())) {
            return false;
        }
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        if (!this.getGender().equals(other.getGender())) {
            return false;
        }
        return this.getDate().equals(other.getDate());
    }
    
    
    
}
