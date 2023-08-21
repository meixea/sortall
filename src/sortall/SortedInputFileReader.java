package sortall;

import java.io.FileNotFoundException;
import java.util.Comparator;

public class SortedInputFileReader extends InputFileReader {
    Comparator<Comparable> comparator;
    public SortedInputFileReader(String filename, ValueObject value_instance, Comparator<Comparable> comparator) throws FileNotFoundException {
        super(filename, value_instance);
        this.comparator = comparator;
    }
    @Override
    public ValueObject getNextValue(){

        ValueObject old_value = current();

        if( (old_value == null) || (this.comparator == null) )
            return super.getNextValue();

        int unsorted_values_counter = 0;
        ValueObject next_value;
        while( true ){
            next_value = super.getNextValue();
            if( next_value == null )
                break;
            if( comparator.compare(old_value, next_value) > 0 )
                unsorted_values_counter++;
            else
                break;
        }
        if( unsorted_values_counter > 0 )
            System.out.printf("%d unsorted values in file %s skipped\n", unsorted_values_counter, fileName);
        return next_value;
    }
}
