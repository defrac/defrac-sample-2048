package defrac.game;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 */
public final class Cell {
  @Nonnull
  public final Context context;
  @Nonnegative
  public final int x;
  @Nonnegative
  public final int y;

  public boolean merged;

  public Cell(@Nonnull final Context context,
              @Nonnegative final int x,
              @Nonnegative final int y) {
    this.context = context;
    this.x = x;
    this.y = y;
  }

  @Nonnegative
  public float xCoordinate() {
    return x * (context.style.tileWidth + context.style.tilePadding) + context.style.tilePadding;
  }

  @Nonnegative
  public float yCoordinate() {
    return y * (context.style.tileHeight + context.style.tilePadding) + context.style.tilePadding;
  }
}
