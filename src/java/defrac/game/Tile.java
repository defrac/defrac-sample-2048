package defrac.game;

import defrac.animation.Animation;
import defrac.display.Canvas;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
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

    public Tile(@Nonnull final GameContext context,
                @Nonnegative final int x,
                @Nonnegative final int y,
                @Nonnegative final int value) {
        super(context.tileWidth, context.tileHeight);

        this.x = x;
        this.y = y;
        this.value = value;

        DrawUtil.drawRect(this, context.tileBackgroundColorFromValue(value),
                context.tileCorner);

        DrawUtil.drawTextCentered(this, String.valueOf(value),
                context.tileFontSizeFromValue(value),
                context.tileFontColorFromValue(value));
    }
}
