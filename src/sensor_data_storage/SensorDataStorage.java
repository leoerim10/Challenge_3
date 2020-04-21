package sensor_data_storage;

import file_persistence.PersistenceException;

import java.io.IOException;


/**
 * We assume: Each sensor gets its own storage engine. There wont be a parameter
 * sensor name.
 */
public interface SensorDataStorage {

    /**
     * This method can be called by a sensor to save a data set.
     * @param time UNIX time when measurement took place
     * @param values sensor data
     * @throws PersistenceException if something unexpected happened. Insufficient right, medium broken, offline..
     */
    void saveData(long time, float[] values) throws PersistenceException;

    /**T
     *This method read the data set from the file
     * @throws IOException if it could not read data from the file
     */
    void readData() throws IOException;



}
