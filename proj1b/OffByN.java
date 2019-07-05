public class OffByN implements CharacterComparator {
    private int DIFFER;

    public OffByN(int N) {
        DIFFER = N;
    }

    public boolean equalChars(char x, char y) {
        if (x - y == DIFFER || y - x == DIFFER){
            return true;
        }
        return false;
    }
}
