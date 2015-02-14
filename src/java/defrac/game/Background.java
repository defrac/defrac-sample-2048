package defrac.game;

import defrac.display.Canvas;

import static defrac.game.Constants.*;

/**
 * @author Tim Richter
 */
public final class Background extends Canvas {
    public Background() {
        super(WIDTH, HEIGHT);

        paint();
    }

    private void paint() {
        DrawUtil.drawRect(this, BACKGROUND_COLOR, BACKGROUND_ROUNDED_CORNER);

        final int padding = TILE_SIZE + TILE_PADDING;

        for (int i = 0, x = TILE_PADDING; i < TILE_COUNT; ++i, x += padding) {
            for (int j = 0, y = TILE_PADDING; j < TILE_COUNT; ++j, y += padding) {
                DrawUtil.drawRect(this, x, y, TILE_SIZE, TILE_SIZE,
                        TILE_BACKGROUND_COLOR_DEFAULT, TILE_ROUNDED_CORNER);
            }
        }
    }
}
