package sortall;

import java.io.*;
import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        test_Comparators();
        test_InputFileReader();
        test_IntegerReader();
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
    static void test_IntegerReader(){
        System.out.println("------test IntegerObject------");
        IntegerObject reader = new IntegerObject();
        reader.setFileReader(new InputFileReader("1\n20\nss\n54\n48\n49\n55\n60".getBytes()));
        reader.setComparator(new AscendingComparator());

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(buffer));

        while( true ){
            Comparable value = reader.next();
            if( value == null )
                break;
            ass(value instanceof Integer, "Integer not recognized");
            System.out.print((Integer) value);
        }

        System.setOut(oldOut);
        String testString = "120File test has incorrect data. Skipped 1 line(s).\n54File test has unsorted values. Skipped 2 value(s).\n5560";
        ass(buffer.toString().equals(testString), "values reading not correct.");
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
