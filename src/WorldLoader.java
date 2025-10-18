import org.json.JSONObject;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class WorldLoader {

    public static JSONObject readWorldJson(String path) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return new JSONObject(sb.toString());
    }
}
