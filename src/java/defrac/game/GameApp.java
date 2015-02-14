package defrac.game;

import defrac.app.Bootstrap;
import defrac.app.GenericApp;

import javax.annotation.Nonnull;

/**
 * @author Tim Richter
 */
public final class GameApp extends GenericApp {
    public static void main(@Nonnull final String[] args) {
        Bootstrap.run(new GameApp());
    }

    @Nonnull
    private final Game game = new Game();

    @Override
    protected void onCreate() {
        game.start();

        stage().addChild(game);
    }
}
