package defrac.game;

import defrac.ui.Screen;
import defrac.ui.ScreenController;

import javax.annotation.Nonnull;

/**
 *
 */
public final class MainViewController extends ScreenController {
  @Nonnull
  @Override
  protected Screen createScreen() {
    return new GameScreen();
  }
}
