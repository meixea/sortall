package sortall;

import java.io.*;

public class InputFileReader extends BufferedReader {
    protected String fileName = "test";
    protected ValueObject valueInstance;
    protected ValueObject currentValue = null;
    protected boolean isWorking = true;
    protected InputFileReader(byte[] bytes, ValueObject value_instance){
        super(new InputStreamReader(new ByteArrayInputStream(bytes)));
        this.valueInstance = value_instance;
    }
    public InputFileReader(String filename, ValueObject value_instance) throws FileNotFoundException {
        super(new FileReader(filename));
        this.fileName = filename;
        this.valueInstance = value_instance;
    }
    public ValueObject current(){
        return this.currentValue;
    }
    public ValueObject getNextValue(){

        if( !this.isWorking )
            return null;

        try {
            int wrong_format_counter = 0;
            ValueObject next_value = null;
            while (ready()) {
                String line = readLine();
                if( line.equals("") )
                    continue;
                try{
                    next_value = valueInstance.parseValue(line);
                    break;
                }
                catch( WrongFormatException e ){
                    wrong_format_counter++;
                }
            }
            if( wrong_format_counter > 0 )
                System.out.printf("%d wrong format values in file %s skipped\n", wrong_format_counter, fileName);
            this.currentValue = next_value;
        }
        catch( IOException e ){
            System.out.printf("File %s: reading error. Skipped\n", fileName);
            isWorking = false;
            this.currentValue = null;
        }
        return this.currentValue;
    }
    public String getFilename(){
        return this.fileName;
    }
}
