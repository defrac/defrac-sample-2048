package defrac.game;

import defrac.ui.*;

import javax.annotation.Nullable;

/**
 */
public final class GameScreen extends Screen {
    @Nullable
    private DisplayList displayList;

    @Override
    protected void onCreate() {
        super.onCreate();

        final LinearLayout layout =
            LinearLayout.horizontal().gravity(Gravity.CENTER);
        final LayoutConstraints layoutConstraints =
            new LinearLayout.LayoutConstraints(592, 592).gravity(Gravity.CENTER);

        displayList = new DisplayList();
        displayList.layoutConstraints(layoutConstraints);

        displayList.root().onSuccess(stage -> {
            final Game game = new Game(stage);
            game.start();

            stage.addChild(game);
        });

        layout.addView(displayList);

        rootView(layout);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(displayList != null) {
            displayList.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(displayList != null) {
            displayList.onResume();
        }
    }
}
