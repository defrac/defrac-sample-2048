package defrac.game;

import defrac.display.DisplayObject;
import defrac.display.Stage;
import defrac.display.event.UIKeyboardEvent;
import defrac.display.event.raw.SwipeEvent;
import defrac.event.EventListener;
import defrac.lang.Procedure;
import defrac.ui.*;
import defrac.util.KeyCode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 */
public final class GameApp extends ContentScreen {
  @Nullable
  private DisplayList displayList;

  @Override
  protected void onCreate() {
    super.onCreate();

    final Context context = new Context(width(), height());

    final LinearLayout layout = LinearLayout.horizontal().gravity(Gravity.CENTER);
    layout.backgroundColor(context.style.backgroundColor);

    displayList = new DisplayList();
    displayList.backgroundColor(context.style.backgroundColor);
    displayList.layoutConstraints(
        new LinearLayout.LayoutConstraints(
            (int) context.style.width, (int) context.style.height).
            gravity(Gravity.CENTER));

    layout.addView(displayList);

    rootView(layout);

    displayList.onStageReady(new Procedure<Stage>() {
      @Override
      public void apply(@Nonnull Stage stage) {
        final Game game = new Game(context);

        stage.globalEvents().onSwipe.add(new EventListener<SwipeEvent>() {
          @Override
          public void onEvent(@Nonnull SwipeEvent event) {
            switch (event.direction) {
              case SwipeEvent.DIRECTION_LEFT:
                context.moveLeft();
                break;
              case SwipeEvent.DIRECTION_RIGHT:
                context.moveRight();
                break;
              case SwipeEvent.DIRECTION_DOWN:
                context.moveDown();
                break;
              case SwipeEvent.DIRECTION_UP:
                context.moveUp();
                break;
            }
          }
        });

        stage.listener(new DisplayObject.SimpleListener() {
          @Override
          public void onKeyDown(@Nonnull DisplayObject target, @Nonnull UIKeyboardEvent event) {
            switch (event.keyCode) {
              case KeyCode.LEFT:
                context.moveLeft();
                break;
              case KeyCode.RIGHT:
                context.moveRight();
                break;
              case KeyCode.DOWN:
                context.moveDown();
                break;
              case KeyCode.UP:
                context.moveUp();
                break;
            }
          }
        });

        stage.addChild(game);

        game.start();
      }
    });
  }

  @Override
  protected void onPause() {
    super.onPause();

    if (displayList != null) {
      displayList.onPause();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    if (displayList != null) {
      displayList.onResume();
    }
  }
}
