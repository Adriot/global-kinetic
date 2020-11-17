package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Text File Reader.
 */
public class FlatFileReader {
    private String filePath;

    public FlatFileReader() {
    }

    public FlatFileReader(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<List<String>> getDataSet() throws IOException{
        List<List<String>> dataSet = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    dataSet.add(Arrays.asList(line.split(",")));
                }
            } while (line != null);
            reader.close();
        } catch (Exception e) {
            // TODO: Handle Errors
            e.printStackTrace();
        }
        return dataSet;
    }
}
