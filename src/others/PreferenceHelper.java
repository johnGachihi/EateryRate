package others;

import java.io.*;

public class PreferenceHelper {

    public static byte[] getByteArrayFromObject(Object obj){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
        } catch (IOException e){
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static Object getObjectFromByteArray(byte[] b){
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = null;
        Object obj = null;
        try{
            ois = new ObjectInputStream(bais);
            obj = ois.readByte();
        } catch (IOException e){
            e.printStackTrace();
        }
        return obj;
    }
}
