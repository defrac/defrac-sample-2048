package defrac.game;

import defrac.concurrent.Dispatchers;
import defrac.ui.FrameBuilder;

/**
 *
 */
public final class Main {
  public static void main(String[] args) {
    Dispatchers.FOREGROUND.exec(() ->
            FrameBuilder.
                forScreen(new GameScreen()).
                width(592).height(592).
                title("2048").
                backgroundColor(0xffffffff).
                resizable().
                show()
    );
  }
}
