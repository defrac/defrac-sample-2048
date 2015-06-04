package defrac.game;

import defrac.app.AppDelegate;
import defrac.ios.foundation.NSDictionary;
import defrac.ios.uikit.UIApplication;
import defrac.ios.uikit.UIScreen;
import defrac.ios.uikit.UIWindow;

public final class MainAppDelegate extends AppDelegate {
  @Override
  public boolean applicationDidFinishLaunchingWithOptions(UIApplication application,
                                                          NSDictionary launchOptions) {
    setWindow(new UIWindow(UIScreen.mainScreen().bounds));

    window().rootViewController = new MainViewController();
    window().makeKeyAndVisible();
    window().frame = UIScreen.mainScreen().bounds;

    return true;
  }
}
