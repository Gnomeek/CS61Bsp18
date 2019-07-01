public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int n = 0;
        while (n < 10) {
            System.out.print(x + " ");
            n += 1;
            x += n;
        }
    }
}