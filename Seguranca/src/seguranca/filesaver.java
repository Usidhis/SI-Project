package seguranca;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class filesaver {
    public static void savedafilete(Object obj, String nome){
        try {
		ObjectOutputStream save = new ObjectOutputStream(
		new FileOutputStream("C:/Users/User/Desktop/" + nome));
		save.writeObject(obj);
		save.close();
            } catch (Exception e) {
		System.out.print("nao guardou");
		e.printStackTrace();
            }
    }
    public static Object getdafilete(String nome) {
		ObjectInputStream stream;
		Object obj = new Object();
		try {
			stream = new ObjectInputStream(
					new FileInputStream("C:/Users/User/Desktop/" + nome));
			obj = (ArrayList<Secret2>) stream.readObject();
			stream.close();
		} catch (Exception e) {

		}
		return obj;

    }
}
