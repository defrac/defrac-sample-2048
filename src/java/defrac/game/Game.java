package defrac.game;

import defrac.animation.Animation;
import defrac.animation.property.display.Alpha;
import defrac.animation.property.display.Scale;
import defrac.animation.property.display.Y;
import defrac.display.Canvas;
import defrac.display.DisplayObject;
import defrac.display.DisplayObjectContainer;
import defrac.display.event.UIActionEvent;
import defrac.math.easing.Quadratic;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 */
public final class Game extends DisplayObjectContainer {
  @Nonnull
  private final Context context;
  @Nonnull
  private final Grid grid;
  @Nonnull
  private final Canvas message;
  @Nonnull
  private final Canvas tryAgainButton;

  public Game(@Nonnull final Context context) {
    this.context = context;

    createBackground();
    createScore();
    createHighScore();
    createNewGameButton();
    grid = createGrid();
    message = createMessage();
    tryAgainButton = createTryAgainButton();

    context.add(new Model.Listener() {
      @Override
      public void onEvent(@Nonnegative int event) {
        switch (event) {
          case Context.GAME_OVER_EVENT:
            showMessage("Game Over!");
            break;

          case Context.GAME_WON_EVENT:
            showMessage("Game Won!");
            break;

          case Context.GAME_PLAY_EVENT:
            message.visible(false);
            tryAgainButton.visible(false);
            grid.reset();
            grid.addRandomTile();
            grid.addRandomTile();
            break;
        }
      }
    });
  }

  @Override
  protected void onAttachToStage() {
    super.onAttachToStage();
    stage().animationSystem().add(context.animationSystem);
  }

  @Override
  protected void onDetachFromStage() {
    super.onDetachFromStage();
    stage().animationSystem().remove(context.animationSystem);
  }

  public void start() {
    context.gamePlay();
  }

  private void showMessage(@Nonnull final String text) {
    message.graphics().clearRect(0, 0, message.width(), message.height());

    // draw message text
    GraphicsUtil.drawTextCentered(message, text, context.style.messageFont,
        context.style.messageFontSize, context.style.messageFontColor);

    message.visible(true);
    tryAgainButton.visible(true);

    // fade out grid and fade in message and try again button
    Animation.create(context.animationSystem, context.style.durationFadeTween,
        Alpha.to(message, 1), Alpha.to(tryAgainButton, 1), Alpha.to(grid, 0.6f)).start();
  }

  @Nonnull
  private Canvas createBackground() {
    final Canvas canvas = new Canvas(context.width, context.height);

    GraphicsUtil.drawRect(canvas, context.style.backgroundColor, context.style.backgroundCorner);

    addChild(canvas);

    return canvas;
  }

  @Nonnull
  private Canvas createHighScore() {
    final Canvas canvas = new Canvas(context.style.highScoreWidth, context.style.highScoreHeight);
    final Canvas counter = new Canvas(context.style.highScoreCounterWidth, context.style.highScoreCounterHeight);

    GraphicsUtil.drawRect(canvas, context.style.highScoreColor, context.style.highScoreCorner);
    GraphicsUtil.drawTextCentered(canvas, "HIGH SCORE", context.style.highScoreTitleXOffset, context.style.highScoreTitleYOffset,
        context.style.highScoreTitleFont, context.style.highScoreTitleFontSize, context.style.highScoreTitleFontColor);

    canvas.moveTo(context.style.highScoreX, context.style.highScoreY);
    counter.moveTo(canvas.x() + canvas.width() / 2 + context.style.highScoreCounterXOffset,
        canvas.y() + canvas.height() / 2 + context.style.highScoreCounterYOffset).centerRegistrationPoint();

    context.add(new Model.Listener() {
      @Override
      public void onEvent(@Nonnegative int event) {
        if (event == Context.HIGH_SCORE_EVENT) {
          counter.graphics().clearRect(0f, 0f, counter.width(), counter.height());

          GraphicsUtil.drawTextCentered(counter, String.valueOf(context.highScore),
              context.style.highScoreCounterFont, context.style.highScoreCounterFontSize,
              context.style.highScoreCounterFontColor);
        }
      }
    });

    addChild(canvas);
    addChild(counter);

    return canvas;
  }

  @Nonnull
  private Canvas createScore() {
    final Canvas canvas = new Canvas(context.style.scoreWidth, context.style.scoreHeight);
    final Canvas counter = new Canvas(context.style.scoreCounterWidth, context.style.scoreCounterHeight);
    final Canvas counterPlus = new Canvas(counter.width(), counter.height());

    counterPlus.alpha(0);

    GraphicsUtil.drawRect(canvas, context.style.scoreColor, context.style.scoreCorner);
    GraphicsUtil.drawTextCentered(canvas, "SCORE", context.style.scoreTitleXOffset, context.style.scoreTitleYOffset,
        context.style.scoreTitleFont, context.style.scoreTitleFontSize, context.style.scoreTitleFontColor);

    canvas.moveTo(context.style.scoreX, context.style.scoreY);
    counter.moveTo(canvas.x() + canvas.width() / 2 + context.style.scoreCounterXOffset,
        canvas.y() + canvas.height() / 2 + context.style.scoreCounterYOffset).
        centerRegistrationPoint();
    counterPlus.moveTo(counter.x(), counter.y()).
        centerRegistrationPoint();

    final Animation animation = Animation.create(context.animationSystem, context.style.durationFadeTween,
        Alpha.to(counterPlus, 1f, 0f),
        Scale.to(counterPlus, 1f, 1f, 0.8f, 0.8f),
        Y.to(counterPlus, counter.y(), counter.y() + context.style.scoreCounterAnimationYOffset)
    ).easing(Quadratic.IN);

    context.add(new Model.Listener() {
      @Override
      public void onEvent(@Nonnegative int event) {
        if (event == Context.SCORE_EVENT) {
          counter.graphics().clearRect(0f, 0f, counter.width(), counter.height());

          GraphicsUtil.drawTextCentered(counter, String.valueOf(context.score),
              context.style.scoreCounterFont, context.style.scoreCounterFontSize,
              context.style.scoreCounterFontColor);

          if (context.tileValue == 0) {
            // we do not show +0 animate
            return;
          }

          counterPlus.graphics().clearRect(0f, 0f, counterPlus.width(), counterPlus.height());

          GraphicsUtil.drawTextCentered(counterPlus, "+" + String.valueOf(context.tileValue),
              context.style.scoreCounterAnimationFont, context.style.scoreCounterAnimationFontSize,
              context.style.scoreCounterAnimationFontColor);

          animation.start();
        }
      }
    });

    addChild(canvas);
    addChild(counter);
    addChild(counterPlus);

    return canvas;
  }

  @Nonnull
  private Grid createGrid() {
    final Grid grid = new Grid(context);

    grid.moveTo(context.style.gridX, context.style.gridY);

    addChild(grid);

    return grid;
  }

  @Nonnull
  private Canvas createMessage() {
    final Canvas canvas = new Canvas(context.style.messageWidth, context.style.messageHeight);

    canvas.visible(false);
    canvas.moveTo(context.style.messageX, context.style.messageY);

    addChild(canvas);

    return canvas;
  }

  @Nonnull
  private Canvas createTryAgainButton() {
    final Canvas canvas = new Canvas(context.style.tryAgainButtonWidth, context.style.tryAgainButtonHeight);

    canvas.visible(false);
    canvas.moveTo(context.style.tryAgainButtonX, context.style.tryAgainButtonY);

    GraphicsUtil.drawRect(canvas, context.style.tryAgainButtonColor, context.style.tryAgainButtonCorner);
    GraphicsUtil.drawTextCentered(canvas, "Try again", context.style.tryAgainButtonFont,
        context.style.tryAgainButtonFontSize, context.style.tryAgainButtonFontColor);

    canvas.listener(new SimpleListener() {
      @Override
      public void onPointerDown(@Nonnull DisplayObject target, @Nonnull UIActionEvent event) {
        tryAgainButton.visible(false);
        message.visible(false);

        grid.alpha(1);

        start();
      }
    });

    addChild(canvas);

    return canvas;
  }

  @Nonnull
  private Canvas createNewGameButton() {
    final Canvas canvas = new Canvas(context.style.newGameButtonWidth, context.style.newGameButtonHeight);

    canvas.moveTo(context.style.newGameButtonX, context.style.newGameButtonY);

    GraphicsUtil.drawRect(canvas, context.style.newGameButtonColor, context.style.newGameButtonCorner);
    GraphicsUtil.drawTextCentered(canvas, "New Game", context.style.newGameButtonFont,
        context.style.newGameButtonFontSize, context.style.newGameButtonFontColor);

    canvas.listener(new SimpleListener() {
      @Override
      public void onPointerDown(@Nonnull DisplayObject target, @Nonnull UIActionEvent event) {
        start();
      }
    });

    addChild(canvas);

    return canvas;
  }
}
