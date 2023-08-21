package sortall;

import java.io.IOException;
import java.util.Comparator;

class WrongFormatException extends RuntimeException{}
public abstract class ValueObject implements Comparable {
    public abstract ValueObject parseValue(String string) throws WrongFormatException;
    @Override
    public abstract String toString();
}
