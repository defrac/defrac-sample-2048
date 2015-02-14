package defrac.game;

import javax.annotation.Nonnegative;

/**
 * @author Tim Richter
 */
public final class Constants {
    @Nonnegative
    public static final int TILE_SIZE = 128;
    @Nonnegative
    public static final int TILE_PADDING = 16;
    @Nonnegative
    public static final int TILE_BACKGROUND_COLOR_DEFAULT = 0xffcdc1b4;
    @Nonnegative
    public static final int TILE_ROUNDED_CORNER = 6;
    @Nonnegative
    public static final int TILE_COUNT = 4;
    @Nonnegative
    public static final int WIDTH = indexToCoordinate(TILE_COUNT);
    @Nonnegative
    public static final int HEIGHT = WIDTH;

    @Nonnegative
    private static final float ASPECT_RATIO = TILE_SIZE / 128.0f; // normalized to 128px

    @Nonnegative
    public static final int GAME_FONT_COLOR_LIGHT = 0xfff9f6f2;
    @Nonnegative
    public static final int GAME_FONT_COLOR_DARK = 0xff776e65;

    @Nonnegative
    public static final int MESSAGE_FONT_SIZE = (int) (ASPECT_RATIO * 68);
    @Nonnegative
    public static final int MESSAGE_FONT_COLOR = GAME_FONT_COLOR_DARK;
    @Nonnegative
    public static final int MESSAGE_PADDING = (int) (ASPECT_RATIO * 20);

    @Nonnegative
    public static final int NEWGAME_BUTTON_WIDTH = (int) (ASPECT_RATIO * 160);
    @Nonnegative
    public static final int NEWGAME_BUTTON_HEIGHT = (int) (ASPECT_RATIO * 36);
    @Nonnegative
    public static final int NEWGAME_BUTTON_FONT_SIZE = (int) (ASPECT_RATIO * 20);
    @Nonnegative
    public static final int NEWGAME_BUTTON_FONT_COLOR = GAME_FONT_COLOR_LIGHT;

    @Nonnegative
    public static final int NEWGAME_BUTTON_BACKGROUND_COLOR = GAME_FONT_COLOR_DARK;
    @Nonnegative
    public static final int NEWGAME_BUTTON_ROUNDED_CORNER = TILE_ROUNDED_CORNER;

    @Nonnegative
    public static final int BACKGROUND_COLOR = 0xffbbada0;
    @Nonnegative
    public static final int BACKGROUND_ROUNDED_CORNER = TILE_ROUNDED_CORNER;

    @Nonnegative
    public static final int TWEEN_MOVE_DURATION = 100;
    @Nonnegative
    public static final int TWEEN_SCALE_DURATION = 300;
    @Nonnegative
    public static final int TWEEN_ALPHA_DURATION = 300;

    public static int indexToCoordinate(int index) {
        return index * (TILE_PADDING + TILE_SIZE) + TILE_PADDING;
    }

    public static int tileFontColorFromValue(@Nonnegative final int value) {
        return value < 16 ? GAME_FONT_COLOR_DARK : GAME_FONT_COLOR_LIGHT;
    }

    public static int tileFontSizeFromValue(@Nonnegative final int value) {
        return (int) (ASPECT_RATIO * (value < 100 ? 42 : value < 1000 ? 38 : 30));
    }

    public static int tileBackgroundColorFromValue(@Nonnegative final int value) {
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
