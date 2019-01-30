package ohjelmointi.mapping;

import java.lang.reflect.Field;
import ohjelmointi.*;

/**
 * Maps object to JSON string and other way around.
 *
 * @author  Samu Koivulahti
 * @version 2018.1215
 * @since   1.8
 */
public class JSONMapper {

    /**
    * Loads mapping information.
    *
    * @param object Instanse of class
    * @param container stores JSON information
    * @return object new instanse with the information
    */
    public static <T> T loadMapping(T object, JSONObject container) {
        try {
            //Storages the information of object class type
            Class<?> classInfo = object.getClass();

            //Checks if the annotation of the class exists
            if (!isValidJSONMappableClass(classInfo)) {
                throw new IllegalStateException();
            }

            //Loops through all fields, public and private
            for (Field field : classInfo.getDeclaredFields()) {
                String fieldName = field.getName();
                //if field has JSONMap annotation, object type and value is set 
                if (field.getAnnotation(JSONMap.class) != null) {
                    if (container.containsKey(fieldName)) {
                        Object value = container.get(fieldName);
                        field.setAccessible(true);
                        field.set(object, value);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("JSONMapper cannot load this object.");
        }
        
        return object;
    }

    /**
    * Saves mapping information.
    *
    * @param object Instanse of class
    * @return container contains JSON information
    */
    public static <T> JSONObject saveMapping(T object) {
        JSONObject container = new JSONObject();

        try {
            Class<?> classInfo = object.getClass();

            if (!isValidJSONMappableClass(classInfo)) {
                throw new IllegalStateException();
            }

            for (Field field : classInfo.getDeclaredFields()) {
                String fieldName = field.getName();

                if (field.getAnnotation(JSONMap.class) != null) {
                    field.setAccessible(true);
                    Object result = field.get(object);
                    container.put(fieldName, result);
                }
            }

        } catch (Exception e) {
            throw new IllegalStateException("JSONMapper cannot save this object.");
        }

        return container;
    }

    /**
    * Checks if JSONMap annotation is present.
    *
    * @param classType type of class to check
    * @return true if JSONMap annotation is found
    */
    public static boolean isValidJSONMappableClass(Class<?> classType) {
        return classType.getAnnotation(JSONMap.class) != null;
    }
}