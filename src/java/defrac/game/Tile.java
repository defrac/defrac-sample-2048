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
  public final Context context;
  @Nullable
  public Cell cell;
  @Nonnegative
  public int value;
  @Nullable
  public Animation movement;
  @Nullable
  public Animation scaling;

  public Tile(@Nonnull final Context context) {
    super(context.style.tileWidth, context.style.tileHeight);
    this.context = context;
  }

  @Nonnull
  public Cell cell() {
    assert cell != null;
    return cell;
  }

  @Nonnull
  public Tile cell(@Nonnull final Cell cell) {
    this.cell = cell;
    return this;
  }

  @Nonnegative
  public int value() {
    return value;
  }

  @Nonnull
  public Tile value(@Nonnegative final int value) {
    if (this.value != value) {
      this.value = value;

      // redraw tile
      graphics().clearRect(0, 0, width(), height());

      GraphicsUtil.drawRect(this, context.style.tileBackgroundColorFromValue(value), context.style.tileCorner);

      GraphicsUtil.drawTextCentered(this, String.valueOf(value), context.style.tileFont,
          context.style.tileFontSizeFromValue(value), context.style.tileFontColorFromValue(value));
    }
    return this;
  }

  public void moveTo(@Nonnull final Cell cell) {
    moveTo(cell.xCoordinate(), cell.yCoordinate());
  }

  @Nonnull
  public Animation animation() {
    assert movement != null;
    return movement;
  }

  public void animate(@Nonnull final Animation animation) {
    if (this.movement != null) {
      this.movement.stop(true);
    }

    this.movement = animation.start();
  }

  @Nonnull
  public Tile dispose() {
    assert parent != null;

    parent.removeChild(this);

    if(movement != null) {
      movement.stop();
      movement = null;
    }

    if(scaling != null) {
      scaling.stop();
      scaling = null;
    }

    return this;
  }
}
