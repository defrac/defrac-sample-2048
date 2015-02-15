package defrac.game;

import defrac.app.Bootstrap;
import defrac.app.GenericApp;

import javax.annotation.Nonnull;

/**
 */
public final class GameApp extends GenericApp {
    public static void main(@Nonnull final String[] args) {
        Bootstrap.run(new GameApp());
    }

    @Override
    protected void onCreate() {
        final Game game = new Game(stage());
        game.start();

        stage().addChild(game);
    }
}
