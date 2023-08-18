package sortall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class NoInputFilesException extends RuntimeException{}
class NoOutputFileException extends RuntimeException{}
public class Parameters {
    protected Comparator<Comparable> comparator = new AscendingComparator();
    protected ValueObject reader = null;
    protected String outputFilename = null;
    protected List<String> inputFilenames = new ArrayList<>();
    private Parameters(){}
    public static Parameters parseParameters(String[] args){

        Parameters parameters = new Parameters();
        List<String> arguments = new ArrayList(Arrays.asList(args));

        parameters.extractOptions(arguments);

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
    protected void extractOutputFilename(List<String> arguments){
        if(arguments.size() > 0)
            this.outputFilename = arguments.remove(0);
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
                this.reader = new IntegerObject();
                return true;
            case "-s":
                this.reader = new StringObject();
                return true;
        }
        return false;
    }
}
