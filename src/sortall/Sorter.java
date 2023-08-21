package sortall;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

public class Sorter {
    List<InputFileReader> inputReaders;
    PrintStream outputStream;
    Comparator<Comparable> comparator;
    public Sorter(List<InputFileReader> input_readers, PrintStream output_stream, Comparator<Comparable> comparator){
        this.inputReaders = input_readers;
        this.outputStream = output_stream;
        this.comparator = comparator;
    }
    public void sort(){
        for(InputFileReader i : inputReaders)
            i.getNextValue();
        while( true ){
            InputFileReader targetReader = null;
            for( InputFileReader r : inputReaders )
                targetReader = compareReaderValues(targetReader, r);
            if( targetReader == null)
                break;
            outputStream.println(targetReader.current());
            targetReader.getNextValue();
        }
    }
    private InputFileReader compareReaderValues(InputFileReader r1, InputFileReader r2){
        if( (r2 == null) || (r2.current() == null) )
            return r1;
        if( (r1 == null) || (r1.current() == null) )
            return r2;
        if( comparator.compare(r1.current(), r2.current()) <= 0 )
            return r1;
        return r2;
    }
}
