package defrac.game;

import defrac.animation.Animation;
import defrac.display.Canvas;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;

import static defrac.game.Constants.*;

/**
 * @author Tim Richter
 */
public final class Tile extends Canvas {
    @Nonnegative
    public int x;
    @Nonnegative
    public int y;
    @Nonnegative
    public int value;

    @Nullable
    public Animation animation;

    public Tile(@Nonnegative final int x,
                @Nonnegative final int y,
                @Nonnegative final int value) {
        super(TILE_SIZE, TILE_SIZE);

        this.x = x;
        this.y = y;
        this.value = value;

        DrawUtil.drawRect(this, tileBackgroundColorFromValue(value), TILE_ROUNDED_CORNER);
        DrawUtil.drawTextCentered(this, String.valueOf(value), tileFontSizeFromValue(value), tileFontColorFromValue(value));
    }
}
