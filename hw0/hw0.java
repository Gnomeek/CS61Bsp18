public class Exercise1 {
  public static void drawTriangleA(String[] args){
    int col = 1;
    while (col <= 5){
      int star = 1;
      while (star <= col){
        System.out.print('*');
        star += 1;
      }
      System.out.println();
      col += 1;
    }
  }
  public static void drawTriangleB(int N){
    int col = 1;
    while (col <= N){
      int star = 1;
      while (star <= col){
        System.out.print('*');
        star += 1;
      }
      System.out.println();
      col += 1;
    }
  }
  
  
  public static void main(String[] args) {
    drawTriangleB(10);  
  }
}

public class Exercise2 {
   public static int max(int[] m) {
    int i = 0;
    int max = m[0];
    while (i < m.length){
      int cur = m[i];
      if (cur > max){
        max = cur;
      }
      i += 1;
    }
    return max;     
   }
   public static void main(String[] args) {
      int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
      max(numbers);
   }
}

public class Exercise3 {
   public static int max(int[] m) {
    int max = m[0];
    for(int i = 0; i < m.length; i += 1){
      int cur = m[i];
      if (cur > max){
        max = cur;
      }
    }
    return max;     
   }
   public static void main(String[] args) {
      int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
      max(numbers);
   }
}

public class Exercise4{
  public static void windowPosSum(int[] a, int n) {
    for (int i = 0; i < a.length; i += 1){
      if (a[i] < 0){
        continue;
      }
      if (i == a.length -1){
        break;
      }
      int inner_len = i + n;
      if (inner_len >= (a.length - 1)){
        inner_len = a.length - 1;
      }
      while (inner_len > i){
        a[i] += a[inner_len];
        inner_len -= 1;
      }
    }
  }

  public static void main(String[] args) {
    int[] a = {1, 2, -3, 4, 5, 4};
    int n = 3;
    windowPosSum(a, n);

    // Should print 4, 8, -3, 13, 9, 4
    System.out.println(java.util.Arrays.toString(a));
  }
}