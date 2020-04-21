package sensor;

import file_persistence.PersistenceException;
import org.junit.Assert;
import org.junit.Test;
import sensor_data_storage.SensorDataStorage;
import sensor_data_storage.SensorDataStorageImpl;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class SensorTest {


    public SensorTest() throws FileNotFoundException {
    }

    /**
     * if the invalid timestamp is given
     * @throws IllegalArgumentException
     */
    @Test (expected=IllegalArgumentException.class)
    public void testInvalidTime() throws PersistenceException {
        SensorDataStorage dataset = new SensorDataStorageImpl();
        dataset.saveData((long)50, new float[] {5.5f, 2.3f});
    }

    /**
     * if any elements in the array is given wrong
     * @throws IllegalArgumentException
     */
    @Test (expected=IllegalArgumentException.class)
    public void testInvalidTemperature() throws PersistenceException {
        SensorDataStorage dataset = new SensorDataStorageImpl();
        dataset.saveData((long)System.currentTimeMillis(), new float[] {5.5f, 1001.1f, 33.5f});
    }

    /**
     * to test the valid time input
     * @throws PersistenceException
     */
    @Test
    public void testValidTime() throws PersistenceException {
        {
            try {
                InputStream is = new FileInputStream("testFile.txt");
                DataInputStream dis = new DataInputStream(is);
            SensorDataStorage dataset = new SensorDataStorageImpl();
            long input_time = System.currentTimeMillis();
            dataset.saveData(input_time, new float[] {5.5f, 2.3f});
            long time_output = dis.readLong();
            Assert.assertEquals(input_time, time_output);
            } catch (FileNotFoundException e) {
                System.err.println("couldn’t open file - fatal");
            } catch (IOException e) {
                System.out.println("IO exception: couldn't read from file");
            }
        }
    }


    /**
     * to test the  valid input array of the measures
     * @throws PersistenceException
     */
    @Test
    public void testValidTemperature() throws PersistenceException {
        {
            try {
                InputStream is = new FileInputStream("testFile.txt");
                DataInputStream dis = new DataInputStream(is);
                SensorDataStorage dataset = new SensorDataStorageImpl();
                float [] temp_input = new float[] {9.3f, 2.7f,3.9f};
                dataset.saveData(System.currentTimeMillis(), temp_input );
                int to_read = dis.readInt();
                float [] temp_output = new float[to_read];
                for( int i = 0; i<to_read; i++){
                    temp_output[i] = dis.readFloat();
                }
                Assert.assertArrayEquals(temp_input, temp_output, 0 );
            } catch (FileNotFoundException e) {
                System.err.println("couldn’t open file - fatal");
            } catch (IOException e) {
                System.out.println("IO exception: couldn't read from file");
            }
        }
    }

}
