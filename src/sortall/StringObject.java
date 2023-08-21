package sortall;

public class StringObject extends ValueObject {
    protected String value;
    public StringObject(){}
    public StringObject(String initial){
        this.value = initial;
    }
    @Override
    public ValueObject parseValue(String string) {
        return new StringObject(string);
    }
    @Override
    public int compareTo(Object o){

        if( o == null )
            throw new NullPointerException("Error: StringObject compared to null");

        if( o instanceof StringObject ){
            StringObject other = (StringObject) o;
            return this.value.compareTo(other.value);
        }

        if( o instanceof String )
            return this.value.compareTo((String)o);

        throw new ClassCastException("Error: StringObject compares with String only");
    }
    public String toString(){
        return value;
    }
}
