package sortall;

import java.io.*;
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
    protected List<InputFileReader> inputReaders = new ArrayList<>();
    private Parameters(){}
    public static Parameters parseParameters(String[] args) {

        Parameters parameters = new Parameters();
        List<String> arguments = new ArrayList(Arrays.asList(args));

        parameters.extractOptions(arguments);
        if( parameters.valueObject == null )
            throw new NoFormatOptionException();

        try{
            parameters.extractOutputStream(arguments);
        }
        catch( FileNotFoundException e ) {
            throw new NoOutputFileException();
        }

        parameters.extractInputReaders(arguments);
        if( parameters.inputReaders.size() == 0 )
            throw new NoInputFilesException();

        return parameters;
    }
    protected void extractOptions(List<String> args){
        while( (args.size() > 0) && args.get(0).startsWith("-") ) {
            String option = args.remove(0);
            if (!recognizeOption(option))
                System.out.println("Unknown option: " + option);
        }
    }
    protected void extractOutputStream(List<String> arguments) throws FileNotFoundException {
        if(arguments.size() > 0) {
            String filename = arguments.remove(0);
            this.outputStream = new PrintStream(new FileOutputStream(filename));
        }
        else
            throw new FileNotFoundException();
    }
    protected void extractInputReaders(List<String> arguments){
        while( arguments.size() > 0 ) {
            String filename = arguments.remove(0);
            try {
                this.inputReaders.add(new SortedInputFileReader(filename, valueObject, this.comparator));
            }
            catch( FileNotFoundException e ){
                System.out.printf("Can't open file %s. Skipping.", filename);
            }
        }
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
        if( this.outputStream != null )
            this.outputStream.close();
        try {
            for (InputFileReader i : inputReaders)
                i.close();
        }
        catch( IOException e ){}
    }
}
