package sortall;

public class Main {
    public static void main(String[] args) {
        try( Parameters p = Parameters.parseParameters(args) ){
            new Sorter(p.inputReaders, p.outputStream, p.comparator).sort();
        }
        catch( NoFormatOptionException e ){
            System.out.println("Specify the file format '-i' or '-s'");
        }
        catch( NoOutputFileException e ){
            System.out.println("Specify correct output and input files");
        }
        catch( NoInputFilesException e ){
            System.out.println("Specify correct input file(s)");
        }
    }
}
