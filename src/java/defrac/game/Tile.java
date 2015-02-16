package defrac.game;

import defrac.animation.Animation;
import defrac.display.Canvas;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 */
public final class Tile extends Canvas {
    @Nonnull
    private final GameContext context;

    @Nonnegative
    public int x;
    @Nonnegative
    public int y;
    @Nonnegative
    public int value;

    @Nullable
    public Animation animation;

    public Tile(@Nonnull final GameContext context) {
      super(context.tileWidth, context.tileHeight);
      this.context = context;
    }

    @Nonnull
    public Tile init(@Nonnegative final int x,
                     @Nonnegative final int y,
                     @Nonnegative final int value) {
      this.x = x;
      this.y = y;

      if(value != this.value) {
        this.value = value;

        graphics().clearRect(0.0f, 0.0f, width(), height());

        DrawUtil.drawRect(this, context.tileBackgroundColorFromValue(value),
            context.tileCorner);

        DrawUtil.drawTextCentered(this, String.valueOf(value),
            context.tileFontSizeFromValue(value),
            context.tileFontColorFromValue(value));
      }

      return this;
    }
}
