package sortall;

import java.io.IOException;
import java.util.Comparator;

class WrongFormatException extends RuntimeException{}
public abstract class ValueObject implements Comparable {
/*
    public T next(){

        if( this.fileReader == null ){
            this.currentValue = null;
            return null;
        }

        int count_unsorted_values = 0;
        int count_failed_values = 0;
        T value = null;

        try {
            while( this.fileReader.ready() ) {
                try {
                    value = parseValue(fileReader.readLine());
                    if( (this.currentValue != null) && (this.comparator != null) &&
                            (this.comparator.compare(this.currentValue, value) > 0))
                    {
                        count_unsorted_values++;
                    }
                    else
                        break;
                }
                catch( WrongFormatException e ){
                    count_failed_values++;
                }
            }

            if( count_failed_values > 0 )
                System.out.printf("File %s has incorrect data. Skipped %d line(s).\n", fileReader.getFilename(), count_failed_values);
            if( count_unsorted_values > 0 )
                System.out.printf("File %s has unsorted values. Skipped %d value(s).\n", fileReader.getFilename(), count_unsorted_values);

            this.currentValue = value;
            return this.currentValue;
        }
        catch( IOException e ){
            System.out.printf("Error reading file %s. File skipped.\n", this.fileReader.getFilename());
            this.fileReader = null;
        }
        this.currentValue = null;
        return null;
    }
*/
    public abstract ValueObject parseValue(String string) throws WrongFormatException;
}
