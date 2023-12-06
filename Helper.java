import java.util.Arrays;

public class Helper {
  public Helper() {
    
  }

  public static void typeWrite(String text, int intervals) {
    System.out.print(text.substring(0, 1));
    for (int i = 1; i < text.length(); i++) {
      wait(intervals);
      System.out.print(text.substring(i, i + 1));
    }
  }

  public static void wait(int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    }
    catch(InterruptedException ex) {

    }
  }

  public static void wait(Thread t) {
    try {
      t.wait();
    } catch(InterruptedException ex) {

    }
  }

  public static int indexOf(Object[] arr, Object o) {
    return Arrays.asList(arr).indexOf(o);
  }
}