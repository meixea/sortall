package sortall;

import java.io.*;

public class InputFileReader extends BufferedReader {
    protected String fileName = "test";
    protected InputFileReader(byte[] bytes){
        super(new InputStreamReader(new ByteArrayInputStream(bytes)));
    }
    public InputFileReader(String filename) throws FileNotFoundException {
        super(new FileReader(filename));
        this.fileName = filename;
    }
    public String getFilename(){
        return this.fileName;
    }
}
