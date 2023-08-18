package sortall;

import java.util.Comparator;

class AscendingComparator implements Comparator<Comparable> {
    @Override
    public int compare(Comparable v1, Comparable v2) {
        return v1.compareTo(v2);
    }
}
class DescendingComparator implements Comparator<Comparable> {
    @Override
    public int compare(Comparable v1, Comparable v2) {
        return v2.compareTo(v1);
    }
}
