package defrac.game;

import javax.annotation.Nonnegative;

/**
 */
public final class Cell {
    @Nonnegative
    public final int x;
    @Nonnegative
    public final int y;

    public Cell(@Nonnegative final int x,
                @Nonnegative final int y) {
        this.x = x;
        this.y = y;
    }
}
