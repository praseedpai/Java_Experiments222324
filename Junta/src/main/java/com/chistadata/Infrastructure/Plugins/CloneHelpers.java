package com.chistadata.Infrastructure.Plugins;
import java.io.*;

public class CloneHelpers {

    public static <T, SerializableClass> T deepClone(T obj) throws IOException, ClassNotFoundException {
        //Serialization of object
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);

        //De-serialization of object
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        SerializableClass copied = (SerializableClass) in.readObject();

        return (T) copied;
    }
}