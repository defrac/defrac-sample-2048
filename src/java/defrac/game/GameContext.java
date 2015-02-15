package defrac.game;

import defrac.display.Stage;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 */
public final class GameContext {
    @Nonnegative
    public final float width;
    @Nonnegative
    public final float height;
    @Nonnegative
    public final float tileWidth;
    @Nonnegative
    public final float tileHeight;
    @Nonnegative
    public final int tilePadding;
    @Nonnegative
    public final int tileColor;
    @Nonnegative
    public final int tileCorner;
    @Nonnegative
    public final int tileCount;
    @Nonnegative
    public final int backgroundColor;
    @Nonnegative
    public final int backgroundCorner;
    @Nonnegative
    public final int colorLight;
    @Nonnegative
    public final int colorDark;

    @Nonnegative
    public final int messageFontSize;
    @Nonnegative
    public final int messageFontColor;
    @Nonnegative
    public final int messagePadding;

    @Nonnegative
    public final int newGameButtonWidth;
    @Nonnegative
    public final int newGameButtonHeight;
    @Nonnegative
    public final int newGameButtonColor;
    @Nonnegative
    public final int newGameButtonCorner;
    @Nonnegative
    public final int newGameButtonFontSize;
    @Nonnegative
    public final int newGameButtonFontColor;

    @Nonnegative
    public final int durationAlphaTween;
    @Nonnegative
    public final int durationScaleTween;
    @Nonnegative
    public final int durationMoveTween;

    @Nonnegative
    private final float ASPECT_RATIO;

    public GameContext(@Nonnull final Stage stage) {
        this.width = stage.width();
        this.height = stage.height();
        this.tileCount = 4;
        this.tilePadding = 16;
        this.tileWidth = (width - (tileCount + 1) * tilePadding) / tileCount;
        this.tileHeight = (height - (tileCount + 1) * tilePadding) / tileCount;

        this.ASPECT_RATIO = tileWidth / 128f;

        this.backgroundColor = 0xffbbada0;
        this.backgroundCorner = 6;

        this.tileColor = 0xffcdc1b4;
        this.tileCorner = 6;

        this.colorLight = 0xfff9f6f2;
        this.colorDark = 0xff776e65;

        this.messageFontSize = (int) (ASPECT_RATIO * 68);
        this.messageFontColor = colorDark;
        this.messagePadding = (int) (ASPECT_RATIO * 20);

        this.newGameButtonWidth = (int) (ASPECT_RATIO * 160);
        this.newGameButtonHeight = (int) (ASPECT_RATIO * 36);
        this.newGameButtonColor = colorDark;
        this.newGameButtonCorner = tileCorner;
        this.newGameButtonFontSize = (int) (ASPECT_RATIO * 20);
        this.newGameButtonFontColor = colorLight;

        this.durationAlphaTween = 300;
        this.durationMoveTween = 100;
        this.durationScaleTween = 300;
    }

    public float indexToXPosition(int index) {
        return index * (tileWidth + tilePadding) + tilePadding;
    }

    public float indexToYPosition(int index) {
        return index * (tileHeight + tilePadding) + tilePadding;
    }

    public int tileFontColorFromValue(@Nonnegative final int value) {
        return value < 16 ? colorDark : colorLight;
    }

    public int tileFontSizeFromValue(@Nonnegative final int value) {
        return (int) (ASPECT_RATIO * (value < 100 ? 42 : value < 1000 ? 38 : 30));
    }

    public int tileBackgroundColorFromValue(@Nonnegative final int value) {
        switch (value) {
            case 2:
                return 0xffeee4da;
            case 4:
                return 0xffede0c8;
            case 8:
                return 0xfff2b179;
            case 16:
                return 0xfff59563;
            case 32:
                return 0xfff67c5f;
            case 64:
                return 0xfff65e3b;
            case 128:
                return 0xffedcf72;
            case 256:
                return 0xffedcc61;
            case 512:
                return 0xffedc850;
            case 1024:
                return 0xffedc53f;
            case 2048:
                return 0xffedc22e;
            default:
                return 0xffcdc1b4;
        }
    }
}
