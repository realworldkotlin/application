package _5_Resources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Util {
    public String writeFile(String path, String content) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(content);
            writer.close();
            return "Success";
        } catch (IOException e) {
            return "Error";
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
