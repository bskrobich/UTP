/**
 *
 *  @author Skrobich Bartosz S27164
 *
 */

package zad4;

import java.io.IOException;

public class Main {
  public static void main(String[] args) {

    new Thread(() -> {
      try {
        Server.start();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }).start();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Client.start();

  }
}
