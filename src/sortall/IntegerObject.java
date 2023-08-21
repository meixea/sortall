package sortall;

public class IntegerObject extends ValueObject {
    protected Integer value = null;
    public IntegerObject(){}
    public IntegerObject(Integer initial){
        this.value = initial;
    }
    public ValueObject parseValue(String string) throws WrongFormatException{
        try {
            return new IntegerObject(Integer.valueOf(string));
        }
        catch( NumberFormatException e ){
            throw new WrongFormatException();
        }
    }
    @Override
    public int compareTo(Object o) {

        if( (o == null) || (this.value == null) )
            throw new NullPointerException("Error: IntegerObject compared to null");
        if( o instanceof IntegerObject ){
            IntegerObject other = (IntegerObject) o;
            return this.value.compareTo(other.value);
        }
        if( o instanceof Integer )
            return this.value.compareTo((Integer)o);

        throw new ClassCastException("Error: IntegerObject compares with Integer only");
    }
    @Override
    public String toString(){
        if(value != null)
            return value.toString();
        return null;
    }
}
