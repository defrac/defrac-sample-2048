package defrac.game;

import defrac.ui.FrameBuilder;

/**
 * @author Tim Richter
 */
public final class Main {
  public static void main(String[] args) {
    FrameBuilder.forScreen(new GameApp()).show();
  }
}
