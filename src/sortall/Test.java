package sortall;

import java.io.*;
import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        test_Comparators();
        test_InputFileReader();
        test_IntegerObject();
        test_StringObject();
//        test_Parameters();

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
    static void test_InputFileReader(){
        System.out.println("------test InputFileReader------");
        try( InputFileReader reader = new InputFileReader("not_existing_file")){
            ass(false, "Not existing file not recognized");
        }
        catch( FileNotFoundException e ){}
        catch( IOException e ){
            ass(false, e.getMessage());
        }
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
    static void test_Parameters(){
        System.out.println("------test Parameters------");

        Parameters p = Parameters.parseParameters(new String[]{"-i", "-a", "out.txt"});
        ass(p.comparator instanceof AscendingComparator, "AscendingComparator not assigned");
        ass(p.reader instanceof IntegerObject, "IntegerObject not assigned");
        ass((p.outputFilename != null) && (p.outputFilename.equals("out.txt")), "output filename not assigned");
        ass(p.inputFilenames.size() == 0, "not existing input filenames");

        p = Parameters.parseParameters(new String[]{"-d", "-s", "out.txt", "file1", "file2"});
        ass(p.comparator instanceof DescendingComparator, "DescendingComparator not assigned");
        ass(p.reader instanceof StringObject, "StringObject not assigned");
        ass(p.inputFilenames.size() == 2, "input filenames not correct");

        p = Parameters.parseParameters(new String[]{"-s", "-d", "out.txt", "file1", "file2"});
        ass(p.comparator instanceof DescendingComparator, "DescendingComparator not assigned");
        ass(p.reader instanceof StringObject, "StringObject not assigned");

    }
    static void ass(boolean condition, String message){
        if( !condition )
            System.out.println("Error: " + message);
    }
}
