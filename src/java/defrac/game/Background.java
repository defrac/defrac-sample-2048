package defrac.game;

import defrac.display.Canvas;

import javax.annotation.Nonnull;

/**
 */
public final class Background extends Canvas {
    public Background(@Nonnull final GameContext context) {
        super(context.width, context.height);

        DrawUtil.drawRect(this, context.backgroundColor, context.backgroundCorner);

        for (int i = 0, x = context.tilePadding; i < context.tileCount; ++i, x += context.tileWidth + context.tilePadding) {
            for (int j = 0, y = context.tilePadding; j < context.tileCount; ++j, y += context.tileHeight + context.tilePadding) {
                DrawUtil.drawRect(this, x, y, context.tileWidth, context.tileHeight, context.tileColor, context.tileCorner);
            }
        }
    }
}
