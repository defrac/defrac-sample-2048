package defrac.game;

import defrac.display.Canvas;
import defrac.display.graphics.TextAlign;
import defrac.display.graphics.TextBaseline;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 */
public final class GraphicsUtil {
  public static void drawRect(@Nonnull final Canvas canvas,
                              @Nonnegative final float x,
                              @Nonnegative final float y,
                              @Nonnegative final float w,
                              @Nonnegative final float h,
                              @Nonnegative final int color,
                              @Nonnegative final int corner) {
    canvas.graphics().beginPath().fillStyle(color).roundRect(x, y, w, h, corner).fill();
  }

  public static void drawRect(@Nonnull final Canvas canvas,
                              @Nonnegative final int color,
                              @Nonnegative final int corner) {
    drawRect(canvas, 0f, 0f, canvas.width(), canvas.height(), color, corner);
  }

  public static void drawTextCentered(@Nonnull final Canvas canvas,
                                      @Nonnull final String text,
                                      @Nonnull final String font,
                                      @Nonnegative final int size,
                                      @Nonnegative final int color) {
    drawTextCentered(canvas, text, 0f, 0f, font, size, color);
  }

  public static void drawTextCentered(@Nonnull final Canvas canvas,
                                      @Nonnull final String text,
                                      @Nonnegative final float offsetX,
                                      @Nonnegative final float offsetY,
                                      @Nonnull final String font,
                                      @Nonnegative final int size,
                                      @Nonnegative final int color) {
    canvas.graphics().beginPath().font(size + "px " + font).
        textBaseline(TextBaseline.MIDDLE).
        textAlign(TextAlign.CENTER).fillStyle(color).
        fillText(text, canvas.width() / 2 + offsetX, canvas.height() / 2 + offsetY);
  }

  private GraphicsUtil() {
  }
}
