
package com.test.pets.utils;

import java.text.SimpleDateFormat;

/**
* <p>The Constants class.</p>
* @author Butron Caballero, Paolo Leonel
*/
public class Constants {

    public static final SimpleDateFormat FILE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");
    public static final SimpleDateFormat USER_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static final String INCORRECT_FILE_ROW_FORMAT = "FORMATO INCORRECTO al leer archivo en linea %s";
    public static final String INCORRECT_FIELDS_FORMAT_ERROR_MSG = "FORMATO INCORRECTO: hay campos con el formato incorrecto, en linea: ";
    
    public static final String NAME_SEARCH_VALUE = "NAME";
    public static final String TYPE_SEARCH_VALUE = "TYPE";
    public static final String TYPE_GENDER_SEARCH_VALUE = "TYPE_GENDER";
    
    public static final String INPUT_NAME_SEARCH_VALUE = "name";
    public static final String INPUT_TYPE_SEARCH_VALUE = "type";
    public static final String INPUT_GENDER_SEARCH_VALUE = "gender";
    
    
}
