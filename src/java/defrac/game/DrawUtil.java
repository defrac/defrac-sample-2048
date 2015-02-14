package defrac.game;

import defrac.display.Canvas;
import defrac.display.graphics.TextAlign;
import defrac.display.graphics.TextBaseline;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author Tim Richter
 */
public final class DrawUtil {
    public static void drawRect(@Nonnull final Canvas canvas,
                                @Nonnegative final int x,
                                @Nonnegative final int y,
                                @Nonnegative final float w,
                                @Nonnegative final float h,
                                @Nonnegative final int color,
                                @Nonnegative final int corner) {
        canvas.graphics().beginPath().
                fillStyle(color).
                roundRect(x, y, w, h, corner).
                fill();
    }

    public static void drawRect(@Nonnull final Canvas canvas,
                                @Nonnegative final int color,
                                @Nonnegative final int corner) {
        drawRect(canvas, 0, 0, canvas.width(), canvas.height(), color, corner);
    }

    public static void drawTextCentered(@Nonnull final Canvas canvas,
                                        @Nonnull final String text,
                                        @Nonnegative final int size,
                                        @Nonnegative final int color) {
        drawTextCentered(canvas, text, 0f, 0f, size, color);
    }

    public static void drawTextCentered(@Nonnull final Canvas canvas,
                                        @Nonnull final String text,
                                        @Nonnegative final float offsetX,
                                        @Nonnegative final float offsetY,
                                        @Nonnegative final int size,
                                        @Nonnegative final int color) {
        canvas.graphics().beginPath().
                font(size + "px Arial").
                textBaseline(TextBaseline.MIDDLE).
                textAlign(TextAlign.CENTER).
                fillStyle(color).
                fillText(text, canvas.width() / 2 + offsetX, canvas.height() / 2 + offsetY);
    }

    private DrawUtil() {
    }
}
