
package com.test.pets.validators;

import com.test.pets.exceptions.ValidationException;
import com.test.pets.model.Pet;
import com.test.pets.utils.Constants;
import java.text.ParseException;
import java.util.Date;

/**
* <p>The PetValidator class.</p>
* @author Butron Caballero, Paolo Leonel
*/
public class PetValidator {
    
    public static final int PET_TYPE_IDX = 0;
    public static final int PET_NAME_IDX = 1;
    public static final int PET_GENDER_IDX = 2;
    public static final int PET_DATE_IDX = 3;
    
    public Pet validatePetRecord(String line, int lineId)
            throws ValidationException {
        line += " ";
        line = line.replace(",,", ", ,").replace(",,", ", ,");
        String[] splitedLine = line.split(",");

        if (splitedLine.length != 4) {
            throw new ValidationException(String.format(Constants.INCORRECT_FILE_ROW_FORMAT, lineId));
        }

        try {
            return getPetInformation(splitedLine, lineId);
        } catch (ValidationException vEx) {
            System.out.println("(PetValidator - validatePetRecord) Error al validar archivo en linea " + line);
            throw vEx;
        }
    }
    
    private Pet getPetInformation(String[] splitedLine, int lineId) throws ValidationException {
        Pet createdPet = new Pet(splitedLine[PET_TYPE_IDX], splitedLine[PET_NAME_IDX], splitedLine[PET_GENDER_IDX], 
                getValidDate(splitedLine[PET_DATE_IDX], lineId));
        return createdPet;
    }
    
    protected Date getValidDate(String dateToValidate, int lineId) throws ValidationException {
        if (dateToValidate == null || dateToValidate.trim().isEmpty()) {
            return null;
        }
        Date result = null;
        try {
            result = Constants.FILE_DATE_FORMAT.parse(dateToValidate);
        } catch (ParseException e) {
            System.out.println("(PetValidator - getValidDate) Error al parsear fecha " + dateToValidate);
            throw new ValidationException(Constants.INCORRECT_FIELDS_FORMAT_ERROR_MSG + lineId);
        }
        return result;
    }

}
