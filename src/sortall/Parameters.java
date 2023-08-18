package sortall;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class NoInputFilesException extends RuntimeException{}
class NoOutputFileException extends RuntimeException{}
class NoFormatOptionException extends RuntimeException{}
public class Parameters implements AutoCloseable {
    protected Comparator<Comparable> comparator = new AscendingComparator();
    protected ValueObject valueObject = null;
    protected PrintStream outputStream = null;
    protected List<String> inputFilenames = new ArrayList<>();
    private Parameters(){}
    public static Parameters parseParameters(String[] args) throws FileNotFoundException {

        Parameters parameters = new Parameters();
        List<String> arguments = new ArrayList(Arrays.asList(args));

        parameters.extractOptions(arguments);
        if( parameters.valueObject == null )
            throw new NoFormatOptionException();

        parameters.extractOutputFilename(arguments);

        parameters.extractInputFilenames(arguments);

        return parameters;
    }
    protected void extractOptions(List<String> args){
        while( (args.size() > 0) && args.get(0).startsWith("-") ) {
            String option = args.remove(0);
            if (!recognizeOption(option))
                System.out.println("Unknown option: " + option);
        }
    }
    protected void extractOutputFilename(List<String> arguments) throws FileNotFoundException {
        if(arguments.size() > 0) {
            String filename = arguments.remove(0);
            this.outputStream = new PrintStream(new FileOutputStream(filename));
        }
    }
    protected void extractInputFilenames(List<String> arguments){
        this.inputFilenames.addAll(arguments);
        arguments.clear();
    }
    protected boolean recognizeOption(String option){
        switch( option ){
            case "-a":
                return true;
            case "-d":
                this.comparator = new DescendingComparator();
                return true;
            case "-i":
                this.valueObject = new IntegerObject();
                return true;
            case "-s":
                this.valueObject = new StringObject();
                return true;
        }
        return false;
    }
    public void close(){

    }
}
