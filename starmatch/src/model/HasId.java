package model;

import java.io.Serializable;

/**
 * A functional interface that represents an object with a unique identifier.
 */
public interface HasId extends Serializable {
    /**
     * Gets the unique identifier of the object.
     *
     * @return The unique identifier.
     */
    Integer getId();

    String convertObjectToLine(); // Converts object to CSV line

    static HasId createObjectFromFields(String[] fields) {
        return null; // To be overridden by each implementing class
    }
}

