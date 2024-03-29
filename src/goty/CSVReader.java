package goty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Author: Delsin Zhang
 * Created on 10/04/2016.
 */
public final class CSVReader {

    /**
     * Read entity data from csv file
     */
    public static ArrayList<String[]> read(String filePath){
        ArrayList<String[]> entityData = new ArrayList<>();
        BufferedReader reader = null;
        String line;
        String splitBy = ",";

        try{
            reader = new BufferedReader(new FileReader(filePath));
            while((line = reader.readLine()) != null){
                entityData.add(line.split(splitBy));
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if(reader != null){
                try{
                    reader.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return entityData;
    }
}
