package defrac.game;

import android.content.Intent;
import android.os.Build;
import defrac.dni.Activity;
import defrac.dni.IntentFilter;
import defrac.ui.Screen;
import defrac.ui.ScreenActivity;

import javax.annotation.Nonnull;

/**
 * @author Tim Richter
 */
@Activity(label = "2048", filter = @IntentFilter(action = Intent.ACTION_MAIN, category = Intent.CATEGORY_LAUNCHER))
public final class Main extends ScreenActivity {
  @Nonnull
  @Override
  protected Screen createScreen() {
    return new GameApp();
  }
}
