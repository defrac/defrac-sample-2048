package defrac.game;

import defrac.ui.PixelUnits;
import defrac.util.Platform;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 */
public final class Style {
  @Nonnegative
  public final float width;
  @Nonnegative
  public final float height;
  @Nonnegative
  public final float padding;
  @Nonnegative
  public final int corner;
  @Nonnull
  public final String font;

  @Nonnegative
  public final float gridX;
  @Nonnegative
  public final float gridY;
  @Nonnegative
  public final float gridWidth;
  @Nonnegative
  public final float gridHeight;
  @Nonnegative
  public final int gridColor;
  @Nonnegative
  public final int gridCorner;

  @Nonnegative
  public final float tileWidth;
  @Nonnegative
  public final float tileHeight;
  @Nonnegative
  public final float tilePadding;
  @Nonnegative
  public final int tileColor;
  @Nonnegative
  public final int tileColor2;
  @Nonnegative
  public final int tileColor4;
  @Nonnegative
  public final int tileColor8;
  @Nonnegative
  public final int tileColor16;
  @Nonnegative
  public final int tileColor32;
  @Nonnegative
  public final int tileColor64;
  @Nonnegative
  public final int tileColor128;
  @Nonnegative
  public final int tileColor256;
  @Nonnegative
  public final int tileColor512;
  @Nonnegative
  public final int tileColor1024;
  @Nonnegative
  public final int tileColor2048;
  @Nonnegative
  public final int tileCorner;
  @Nonnull
  public final String tileFont;

  @Nonnegative
  public final int backgroundColor;
  @Nonnegative
  public final int backgroundCorner;
  @Nonnegative
  public final int color0;
  @Nonnegative
  public final int color1;
  @Nonnegative
  public final int color2;
  @Nonnegative
  public final int color3;

  @Nonnegative
  public final float messageX;
  @Nonnegative
  public final float messageY;
  @Nonnegative
  public final float messageWidth;
  @Nonnegative
  public final float messageHeight;
  @Nonnull
  public final String messageFont;
  @Nonnegative
  public final int messageFontSize;
  @Nonnegative
  public final int messageFontColor;
  @Nonnegative
  public final float messagePadding;

  @Nonnegative
  public final float newGameButtonX;
  @Nonnegative
  public final float newGameButtonY;
  @Nonnegative
  public final float newGameButtonWidth;
  @Nonnegative
  public final float newGameButtonHeight;
  @Nonnegative
  public final int newGameButtonColor;
  @Nonnegative
  public final int newGameButtonCorner;
  @Nonnull
  public final String newGameButtonFont;
  @Nonnegative
  public final int newGameButtonFontSize;
  @Nonnegative
  public final int newGameButtonFontColor;

  @Nonnegative
  public final float tryAgainButtonX;
  @Nonnegative
  public final float tryAgainButtonY;
  @Nonnegative
  public final float tryAgainButtonWidth;
  @Nonnegative
  public final float tryAgainButtonHeight;
  @Nonnegative
  public final int tryAgainButtonColor;
  @Nonnegative
  public final int tryAgainButtonCorner;
  @Nonnull
  public final String tryAgainButtonFont;
  @Nonnegative
  public final int tryAgainButtonFontSize;
  @Nonnegative
  public final int tryAgainButtonFontColor;

  @Nonnegative
  public final float highScoreX;
  @Nonnegative
  public final float highScoreY;
  @Nonnegative
  public final float highScoreWidth;
  @Nonnegative
  public final float highScoreHeight;
  @Nonnegative
  public final int highScoreColor;
  @Nonnegative
  public final int highScoreCorner;

  @Nonnegative
  public final float highScoreCounterXOffset;
  @Nonnegative
  public final float highScoreCounterYOffset;
  @Nonnegative
  public final float highScoreCounterWidth;
  @Nonnegative
  public final float highScoreCounterHeight;
  @Nonnull
  public final String highScoreCounterFont;
  @Nonnegative
  public final int highScoreCounterFontSize;
  @Nonnegative
  public final int highScoreCounterFontColor;

  @Nonnegative
  public final float highScoreTitleXOffset;
  @Nonnegative
  public final float highScoreTitleYOffset;
  @Nonnegative
  public final float highScoreTitleWidth;
  @Nonnegative
  public final float highScoreTitleHeight;
  @Nonnull
  public final String highScoreTitleFont;
  @Nonnegative
  public final int highScoreTitleFontSize;
  @Nonnegative
  public final int highScoreTitleFontColor;

  @Nonnegative
  public final float scoreX;
  @Nonnegative
  public final float scoreY;
  @Nonnegative
  public final float scoreWidth;
  @Nonnegative
  public final float scoreHeight;
  @Nonnegative
  public final int scoreColor;
  @Nonnegative
  public final int scoreCorner;

  @Nonnegative
  public final float scoreCounterXOffset;
  @Nonnegative
  public final float scoreCounterYOffset;
  @Nonnegative
  public final float scoreCounterWidth;
  @Nonnegative
  public final float scoreCounterHeight;
  @Nonnull
  public final String scoreCounterFont;
  @Nonnegative
  public final int scoreCounterFontSize;
  @Nonnegative
  public final int scoreCounterFontColor;

  @Nonnegative
  public final float scoreCounterAnimationYOffset;
  @Nonnull
  public final String scoreCounterAnimationFont;
  @Nonnegative
  public final int scoreCounterAnimationFontSize;
  @Nonnegative
  public final int scoreCounterAnimationFontColor;

  @Nonnegative
  public final float scoreTitleXOffset;
  @Nonnegative
  public final float scoreTitleYOffset;
  @Nonnegative
  public final float scoreTitleWidth;
  @Nonnegative
  public final float scoreTitleHeight;
  @Nonnull
  public final String scoreTitleFont;
  @Nonnegative
  public final int scoreTitleFontSize;
  @Nonnegative
  public final int scoreTitleFontColor;

  @Nonnegative
  public final int durationScaleTween;
  @Nonnegative
  public final int durationMoveTween;
  @Nonnegative
  public final int durationFadeTween;

  @Nonnegative
  public static final int originalWidth = PixelUnits.LP_TO_PX.apply(624);
  @Nonnegative
  public static final int originalHeight = PixelUnits.LP_TO_PX.apply(748);
  @Nonnegative
  private final float ratio;

  public Style(@Nonnull final Context context) {
    this.ratio = Platform.isWeb() ? 1 : Math.min(context.width / originalWidth, context.height / originalHeight);

    this.width = ratio * originalWidth;
    this.height = ratio * originalHeight;

    this.padding = ratio * 16;
    this.corner = 6;
    this.font = "Arial";

    this.backgroundColor = 0xfffaf8ef;
    this.backgroundCorner = 6;

    this.color0 = 0xfff9f6f2;
    this.color1 = 0xffcdc1b4;
    this.color2 = 0xff8f7a66;
    this.color3 = 0xff776e65;

    this.messageWidth = ratio * PixelUnits.LP_TO_PX.apply(400);
    this.messageHeight = ratio * PixelUnits.LP_TO_PX.apply(80);
    this.messageX = (width - messageWidth) / 2;
    this.messageY = (height - messageHeight) / 2;
    this.messageFont = font;
    this.messageFontSize = (int) (ratio * PixelUnits.SP_TO_PX.apply(68));
    this.messageFontColor = color3;
    this.messagePadding = ratio * PixelUnits.LP_TO_PX.apply(20);

    this.tryAgainButtonWidth = ratio * PixelUnits.LP_TO_PX.apply(160);
    this.tryAgainButtonHeight = ratio * PixelUnits.LP_TO_PX.apply(36);
    this.tryAgainButtonX = (width - tryAgainButtonWidth) / 2;
    this.tryAgainButtonY = messageY + messageHeight + padding;
    this.tryAgainButtonColor = color3;
    this.tryAgainButtonCorner = corner;
    this.tryAgainButtonFont = font;
    this.tryAgainButtonFontSize = (int) (ratio * PixelUnits.SP_TO_PX.apply(20));
    this.tryAgainButtonFontColor = color0;

    this.highScoreWidth = ratio * PixelUnits.LP_TO_PX.apply(144);
    this.highScoreHeight = ratio * PixelUnits.LP_TO_PX.apply(60);
    this.highScoreX = width - highScoreWidth - padding;
    this.highScoreY = padding;
    this.highScoreColor = color1;
    this.highScoreCorner = corner;

    this.highScoreTitleXOffset = 0;
    this.highScoreTitleYOffset = -ratio * PixelUnits.LP_TO_PX.apply(12);
    this.highScoreTitleWidth = highScoreWidth;
    this.highScoreTitleHeight = highScoreHeight / 2;
    this.highScoreTitleFont = font;
    this.highScoreTitleFontSize = (int) (ratio * PixelUnits.SP_TO_PX.apply(14));
    this.highScoreTitleFontColor = 0xffeee4da;

    this.highScoreCounterXOffset = 0;
    this.highScoreCounterYOffset = highScoreTitleYOffset * -1;
    this.highScoreCounterWidth = highScoreWidth;
    this.highScoreCounterHeight = highScoreHeight - highScoreTitleHeight;
    this.highScoreCounterFont = font;
    this.highScoreCounterFontSize = (int) (ratio * PixelUnits.SP_TO_PX.apply(28));
    this.highScoreCounterFontColor = color0;

    this.scoreWidth = highScoreWidth;
    this.scoreHeight = highScoreHeight;
    this.scoreX = highScoreX - scoreWidth - padding / 2;
    this.scoreY = padding;
    this.scoreColor = highScoreColor;
    this.scoreCorner = highScoreCorner;

    this.scoreTitleXOffset = highScoreTitleXOffset;
    this.scoreTitleYOffset = highScoreTitleYOffset;
    this.scoreTitleWidth = highScoreTitleWidth;
    this.scoreTitleHeight = highScoreTitleHeight;
    this.scoreTitleFont = highScoreTitleFont;
    this.scoreTitleFontSize = highScoreTitleFontSize;
    this.scoreTitleFontColor = highScoreTitleFontColor;

    this.scoreCounterXOffset = highScoreCounterXOffset;
    this.scoreCounterYOffset = highScoreCounterYOffset;
    this.scoreCounterWidth = highScoreCounterWidth;
    this.scoreCounterHeight = highScoreCounterHeight;
    this.scoreCounterFont = highScoreCounterFont;
    this.scoreCounterFontSize = highScoreCounterFontSize;
    this.scoreCounterFontColor = highScoreCounterFontColor;

    this.scoreCounterAnimationYOffset = -ratio * PixelUnits.LP_TO_PX.apply(40);
    this.scoreCounterAnimationFont = scoreCounterFont;
    this.scoreCounterAnimationFontSize = scoreCounterFontSize;
    this.scoreCounterAnimationFontColor = color3;

    this.newGameButtonWidth = highScoreWidth;
    this.newGameButtonHeight = ratio * PixelUnits.LP_TO_PX.apply(40);
    this.newGameButtonX = width - newGameButtonWidth - padding;
    this.newGameButtonY = highScoreY + scoreHeight + padding / 2;
    this.newGameButtonColor = color2;
    this.newGameButtonCorner = corner;
    this.newGameButtonFont = font;
    this.newGameButtonFontSize = (int) (ratio * PixelUnits.SP_TO_PX.apply(16));
    this.newGameButtonFontColor = color0;

    this.gridWidth = width - padding * 2;
    this.gridHeight = gridWidth;
    this.gridX = padding;
    this.gridY = newGameButtonY + newGameButtonHeight + padding;
    this.gridColor = 0xffbbada0;
    this.gridCorner = corner;

    this.tilePadding = padding;
    this.tileWidth = (gridWidth - (context.tileCount + 1) * tilePadding) / context.tileCount;
    this.tileHeight = (gridHeight - (context.tileCount + 1) * tilePadding) / context.tileCount;
    this.tileColor = color1;
    this.tileColor2 = 0xffeee4da;
    this.tileColor4 = 0xffede0c8;
    this.tileColor8 = 0xfff2b179;
    this.tileColor16 = 0xfff59563;
    this.tileColor32 = 0xfff67c5f;
    this.tileColor64 = 0xfff65e3b;
    this.tileColor128 = 0xffedcf72;
    this.tileColor256 = 0xffedcc61;
    this.tileColor512 = 0xffedc850;
    this.tileColor1024 = 0xffedc53f;
    this.tileColor2048 = 0xffedc22e;
    this.tileCorner = corner;
    this.tileFont = font;

    this.durationMoveTween = 150;
    this.durationScaleTween = 300;
    this.durationFadeTween = 500;
  }

  public int tileFontColorFromValue(@Nonnegative final int value) {
    return value < 16 ? color3 : color0;
  }

  public int tileFontSizeFromValue(@Nonnegative final int value) {
    return PixelUnits.SP_TO_PX.apply((int) (ratio * (value < 100 ? 46 : value < 1000 ? 42 : 34)));
  }

  public int tileBackgroundColorFromValue(@Nonnegative final int value) {
    switch (value) {
      case 2:
        return tileColor2;
      case 4:
        return tileColor4;
      case 8:
        return tileColor8;
      case 16:
        return tileColor16;
      case 32:
        return tileColor32;
      case 64:
        return tileColor64;
      case 128:
        return tileColor128;
      case 256:
        return tileColor256;
      case 512:
        return tileColor512;
      case 1024:
        return tileColor1024;
      case 2048:
        return tileColor2048;
      default:
        return tileColor;
    }
  }
}
