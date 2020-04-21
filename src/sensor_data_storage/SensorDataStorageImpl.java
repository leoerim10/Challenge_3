package sensor_data_storage;

import file_persistence.PersistenceException;

import java.io.*;

public class SensorDataStorageImpl implements SensorDataStorage {

    String filename = "testFile.txt";
    OutputStream os;

    {
        try {
            os = new FileOutputStream(filename);
            DataOutputStream dos = new DataOutputStream(os);

        } catch (FileNotFoundException e) {
            System.err.println("couldn’t open file - fatal");
        }
    }

    DataOutputStream dos = new DataOutputStream(os);

    public void saveData(long time, float[] values) throws PersistenceException {
        try {
            if((time<System.currentTimeMillis() - 5000) ) { // as timestamp is in millisecond, time older than a day isnt valid
                throw new IllegalArgumentException();
            }else{
                dos.writeLong(time);

                dos.writeInt(values.length);
                for (int j = 0; j < values.length; j++) {

                    if( values[j]> 1000 || values[j]<-200){  // can measure temp between 1000 at maximum and -200 minimum
                        throw new IllegalArgumentException();
                    }else{
                        dos.writeFloat(values[j]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("couldn’t write data (fatal)");
        }
    }






    InputStream is;

    {
        try {
            is = new FileInputStream("testFile.txt");
        } catch (FileNotFoundException e) {
            System.err.println("couldn’t open file - fatal");
        }
    }
    DataInputStream dis = new DataInputStream(is);

    public void readData() throws IOException {
        long timeStampReceived = dis.readLong();
        System.out.println("timeStampReceived == " + timeStampReceived);

        int numberReceived = dis.readInt();
        System.out.println("numberReceived=" + numberReceived);
        float[] valuesReceived = new float[numberReceived];
        for (int i = 0; i < numberReceived; i++) {
            valuesReceived[i] = dis.readFloat();
            System.out.print("value[" + i + "]" + valuesReceived[i]);
        }
        System.out.println(" ");
    }




}

