package sortall;

public class Main {
    public static void main(String[] args) {
        Parameters p = Parameters.parseParameters(args);
        System.out.println(p.reader.fileReader);
    }
}
