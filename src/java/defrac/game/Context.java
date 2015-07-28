package defrac.game;

import defrac.lang.Procedure;
import defrac.lang.Supplier;
import defrac.pool.ObjectPool;
import defrac.pool.ObjectPools;
import defrac.ui.Display;
import defrac.ui.PixelUnits;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 */
public final class Context extends Model {
  @Nonnegative
  public static final int SCORE_EVENT = 1;
  @Nonnegative
  public static final int HIGH_SCORE_EVENT = 2;
  @Nonnegative
  public static final int GAME_PLAY_EVENT = 3;
  @Nonnegative
  public static final int GAME_OVER_EVENT = 4;
  @Nonnegative
  public static final int GAME_WON_EVENT = 5;
  @Nonnegative
  public static final int MOVE_LEFT_EVENT = 6;
  @Nonnegative
  public static final int MOVE_RIGHT_EVENT = 7;
  @Nonnegative
  public static final int MOVE_UP_EVENT = 8;
  @Nonnegative
  public static final int MOVE_DOWN_EVENT = 9;

  @Nonnegative
  public final float width;
  @Nonnegative
  public final float height;
  @Nonnegative
  public final int tileCount;
  @Nonnull
  public final Style style;
  @Nonnegative
  public int tileValue;
  @Nonnegative
  public int score;
  @Nonnegative
  public int highScore;

  public boolean gameOver;
  public boolean gameWon;

  public Context(@Nonnegative final float width,
                 @Nonnegative final float height) {
    this.width = width;
    this.height = height;

    this.tileCount = 4;
    this.style = new Style(this);
  }

  public void moveLeft() {
    notifyListener(MOVE_LEFT_EVENT);
  }

  public void moveRight() {
    notifyListener(MOVE_RIGHT_EVENT);
  }

  public void moveUp() {
    notifyListener(MOVE_UP_EVENT);
  }

  public void moveDown() {
    notifyListener(MOVE_DOWN_EVENT);
  }

  public void updateScore(@Nonnegative final int value) {
    tileValue = value;

    score += value;

    notifyListener(SCORE_EVENT);

    if (score > highScore) {
      highScore = score;

      notifyListener(HIGH_SCORE_EVENT);
    }

    if (value == 2048) {
      gameWon();
    }
  }

  public void gameOver() {
    gameOver = true;

    notifyListener(GAME_OVER_EVENT);
  }

  public void gameWon() {
    gameWon = true;

    notifyListener(GAME_WON_EVENT);
  }

  public void gamePlay() {
    gameWon = false;
    gameOver = false;

    tileValue = 0;
    score = 0;

    notifyListener(GAME_PLAY_EVENT);
    notifyListener(SCORE_EVENT);
    notifyListener(HIGH_SCORE_EVENT);
  }

  public boolean gameTerminated() {
    return gameOver || gameWon;
  }
}
