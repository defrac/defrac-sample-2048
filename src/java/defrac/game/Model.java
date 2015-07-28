package defrac.game;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 */
public class Model {
  public interface Listener {
    void onEvent(@Nonnegative final int event);
  }

  @Nonnull
  private Listener[] listeners = new Listener[1];
  @Nonnegative
  private int listenerSize;

  public void notifyListener(@Nonnegative final int event) {
    for (int i = 0, n = listenerSize; i < n; ++i) {
      listeners[i].onEvent(event);
    }
  }

  public void add(@Nonnull final Listener listener) {
    if (listeners.length == listenerSize) {
      System.arraycopy(listeners, 0, listeners = new Listener[listenerSize << 1], 0, listenerSize);
    }
    listeners[listenerSize++] = listener;
  }
}
