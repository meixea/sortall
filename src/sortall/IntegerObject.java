package sortall;

public class IntegerObject extends ValueObject {
    public static ValueObject parseValue(String string) throws WrongFormatException{
        try {
            return Integer.valueOf(string);
        }
        catch( NumberFormatException e ){
            throw new WrongFormatException();
        }
    }
}
