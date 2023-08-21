package sortall;

import java.io.*;
import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        test_Comparators();
        test_IntegerObject();
        test_StringObject();
        test_InputFileReader();
        test_SortedInputFileReader();
        test_Parameters();

    }
    static void test_Comparators(){
        System.out.println("------test AscendingComparator------");
        Comparator<Comparable> comp = new AscendingComparator();
        ass(comp.compare("aaaaaaaaaa", "bbbbbb") < 0, "Strings compare error");
        ass(comp.compare("faaaaaaaaa", "bbbbbb") > 0, "Strings compare error");
        ass(comp.compare(Integer.valueOf(37), Integer.valueOf(50)) < 0, "Integers compare error");
        ass(comp.compare(Integer.valueOf(37), Integer.valueOf(0)) > 0, "Integers compare error");
        System.out.println("------test DescendingComparator------");
        comp = new DescendingComparator();
        ass(comp.compare("aaaaaaaaaa", "bbbbbb") > 0, "Strings compare error");
        ass(comp.compare("faaaaaaaaa", "bbbbbb") < 0, "Strings compare error");
        ass(comp.compare(Integer.valueOf(37), Integer.valueOf(50)) > 0, "Integers compare error");
        ass(comp.compare(Integer.valueOf(37), Integer.valueOf(0)) < 0, "Integers compare error");
    }
    static void test_IntegerObject(){
        System.out.println("------test IntegerObject------");

        IntegerObject i1 = new IntegerObject();
        IntegerObject i2 = new IntegerObject(17);

        try{
            i1.compareTo(i2);
            ass(false, "comparing to null");
        }
        catch( NullPointerException e ){}

        try{
            i2.compareTo(i1);
            ass(false, "comparing to null");
        }
        catch( NullPointerException e ){}

        try{
            i2.compareTo("11");
            ass(false, "comparing with String");
        }
        catch( ClassCastException e ){}

        ass(i2.compareTo(18) < 0, "Bad comparing for less");
        ass(i2.compareTo(1) > 0, "Bad comparing for greater");
        ass(i2.compareTo(17) == 0, "Bad comparing for equal");
        i1 = new IntegerObject(40);
        ass(i2.compareTo(i1) < 0, "Bad comparing to IntegerObject");
    }
    static void test_StringObject(){
        System.out.println("------test StringObject------");

        StringObject s1 = new StringObject();
        StringObject s2 = new StringObject("dddd");

        try{
            s1.compareTo(s2);
            ass(false, "comparing to null");
        }
        catch( NullPointerException e ){}

        try{
            s2.compareTo(s1);
            ass(false, "comparing to null");
        }
        catch( NullPointerException e ){}

        try{
            s2.compareTo(11);
            ass(false, "comparing with int");
        }
        catch( ClassCastException e ){}

        ass(s2.compareTo("tt") < 0, "Bad comparing for less");
        ass(s2.compareTo("aa") > 0, "Bad comparing for greater");
        ass(s2.compareTo("dddd") == 0, "Bad comparing for equal");
    }
    static void test_InputFileReader(){
        System.out.println("------test InputFileReader------");
        try( InputFileReader reader = new InputFileReader("not_existing_file", null)){
            ass(false, "Not existing file not recognized");
        }
        catch( FileNotFoundException e ){}
        catch( IOException e ){
            ass(false, e.getMessage());
        }

        try( InputFileReader reader = new InputFileReader("test_in2.txt", new IntegerObject())){

            String test_log = "182 wrong format values in file test_in2.txt skipped\n27";

            PrintStream oldOut = System.out;
            ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));

            while ( true ) {
                ValueObject value = reader.getNextValue();
                if( value == null )
                    break;
                System.out.print(value.toString());
            }

            System.setOut(oldOut);

            ass(test_log.equals(testOut.toString()), "Test reading from test_in2.txt not working");
        }
        catch( FileNotFoundException e ){
            ass(false, "can't open test input file");
        }
        catch( IOException e ){
            ass(false, e.getMessage());
        }
    }
    static void test_SortedInputFileReader(){
        System.out.println("------test SortedInputFileReader------");
        try( InputFileReader reader = new SortedInputFileReader("test_in4.txt", new IntegerObject(), new AscendingComparator())){

            String test_log = "111 unsorted values in file test_in4.txt skipped\n1234893 unsorted values in file test_in4.txt skipped\n27";

            PrintStream oldOut = System.out;
            ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));

            while ( true ) {
                ValueObject value = reader.getNextValue();
                if( value == null )
                    break;
                System.out.print(value.toString());
            }

            System.setOut(oldOut);

            ass(test_log.equals(testOut.toString()), "Test reading from test_in4.txt not working");
        }
        catch( FileNotFoundException e ){
            ass(false, "can't open test input file");
        }
        catch( IOException e ){
            ass(false, e.getMessage());
        }
    }
    static void test_Parameters(){
        System.out.println("------test Parameters------");

        try( Parameters p = Parameters.parseParameters(new String[]{"-a", "test_out.txt", "test_in1.txt"}) ){
            ass(false, "Working with no format option");
        }
        catch( NoOutputFileException e ){
            ass(false, "Can't create output file");
        }
        catch( NoFormatOptionException e ){}

        try( Parameters p = Parameters.parseParameters(new String[]{"-d", "-i", "test_out.txt", "test_in1.txt"}) ){
            ass(p.comparator instanceof DescendingComparator, "Descending option not working");
            ass(p.valueObject instanceof IntegerObject, "Integer format option not recognized");
        }
        catch( NoOutputFileException e ){
            ass(false, "Can't create output file");
        }
        catch( NoFormatOptionException e ){
            ass(false, "Integer format option not recognized");
        }

        try( Parameters p = Parameters.parseParameters(new String[]{"-s", "-d", "test_out.txt", "test_in1.txt", "test_in2.txt", "test_in3.txt"}) ){
            ass(p.comparator instanceof DescendingComparator, "Descending option not working");
            ass(p.valueObject instanceof StringObject, "String format option not recognized");
            ass(p.inputReaders.size() == 3, "Not all input files recognized");
        }
        catch( NoOutputFileException e ){
            ass(false, "Can't create output file");
        }
        catch( NoFormatOptionException e ){
            ass(false, "String format option not recognized");
        }

        try( Parameters p = Parameters.parseParameters(new String[]{"-s", "-d"}) ){
            ass(false, "Absence of output file not recognized");
        }
        catch( NoOutputFileException e ){}

        try( Parameters p = Parameters.parseParameters(new String[]{"-s", "-d", "test_out.txt"}) ){
            ass(false, "Absence input files not recognized");
        }
        catch( NoOutputFileException e ){
            ass(false, "Can't create output file");
        }
        catch( NoInputFilesException e ){}


    }
    static void ass(boolean condition, String message){
        if( !condition )
            System.out.println("Error: " + message);
    }
}
