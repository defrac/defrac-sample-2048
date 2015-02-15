package defrac.game;

import defrac.animation.Animation;
import defrac.animation.property.display.*;
import defrac.display.Canvas;
import defrac.display.DisplayObject;
import defrac.display.DisplayObjectContainer;
import defrac.display.event.UIActionEvent;
import defrac.event.EventListener;
import defrac.event.Events;
import defrac.event.KeyboardEvent;
import defrac.event.SwipeEvent;
import defrac.util.KeyCode;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static defrac.game.Constants.*;

/**
 * @author Tim Richter
 */
public final class Game extends DisplayObjectContainer {
    @Nonnegative
    private static final int GAME_PLAY = 1;
    @Nonnegative
    private static final int GAME_WON = 2;
    @Nonnegative
    private static final int GAME_OVER = 3;

    @Nonnull
    private final Tile[][] tiles = new Tile[TILE_COUNT][TILE_COUNT];
    @Nonnull
    private boolean[][] merged = new boolean[TILE_COUNT][TILE_COUNT];
    @Nonnegative
    private int state;
    @Nonnegative
    private int score;

    private boolean moved;

    public Game() {
        addChild(new Background());

        Events.onSwipe.add(new EventListener<SwipeEvent>() {
            @Override
            public void onEvent(SwipeEvent e) {
                switch (e.direction) {
                    case SwipeEvent.DIRECTION_DOWN:
                        down();
                        break;
                    case SwipeEvent.DIRECTION_LEFT:
                        left();
                        break;
                    case SwipeEvent.DIRECTION_RIGHT:
                        right();
                        break;
                    case SwipeEvent.DIRECTION_UP:
                        up();
                        break;
                }

            }
        });

        Events.onKeyDown.add(new EventListener<KeyboardEvent>() {
            @Override
            public void onEvent(KeyboardEvent e) { System.out.println(score);
                switch (e.keyCode) {
                    case KeyCode.LEFT:
                        left();
                        break;
                    case KeyCode.RIGHT:
                        right();
                        break;
                    case KeyCode.DOWN:
                        down();
                        break;
                    case KeyCode.UP:
                        up();
                        break;
                }
            }
        });
    }

    public boolean isGameTerminated() {
        return state == GAME_OVER || state == GAME_WON;
    }

    public void start() {
        state = GAME_PLAY;

        updateScore(0);

        final Iterator<DisplayObject> iterator = iterator();

        while (iterator.hasNext()) {
            final DisplayObject displayObject = iterator.next();

            if (displayObject instanceof Tile) {
                final Tile tile = (Tile) displayObject;

                iterator.remove();

                tiles[tile.x][tile.y] = null;
            }
        }

        addRandomTile();
        addRandomTile();
    }

    private void prepareMove() {
        merged = new boolean[TILE_COUNT][TILE_COUNT];

        moved = false;
    }

    private void validateMove() {
        if (moved) {
            addRandomTile();

            if (!playable()) {
                gameOver();
            }
        }
    }

    private void gameWon() {
        assert state != GAME_WON;

        state = GAME_WON;

        finish("You won!", "Score: "+ Integer.toString(score));
    }

    private void gameOver() {
        assert state != GAME_OVER;

        state = GAME_OVER;

        finish("Game over", "Score: "+ Integer.toString(score));
    }

    private void finish(@Nonnull final String message, @Nonnull final String score) {
        final Canvas overlay = new Canvas(WIDTH, HEIGHT);
        final Canvas button = new Canvas(NEWGAME_BUTTON_WIDTH, NEWGAME_BUTTON_HEIGHT);

        DrawUtil.drawRect(overlay, 0x9fffffff, BACKGROUND_ROUNDED_CORNER);
        DrawUtil.drawTextCentered(overlay, message, 0, -button.height() - MESSAGE_PADDING - SCORE_PADDING - SCORE_FONT_SIZE, MESSAGE_FONT_SIZE, MESSAGE_FONT_COLOR);
        DrawUtil.drawTextCentered(overlay, score, 0, -button.height() - SCORE_PADDING, SCORE_FONT_SIZE, SCORE_FONT_COLOR);

        DrawUtil.drawRect(button, NEWGAME_BUTTON_BACKGROUND_COLOR, NEWGAME_BUTTON_ROUNDED_CORNER);
        DrawUtil.drawTextCentered(button, "New Game", NEWGAME_BUTTON_FONT_SIZE, NEWGAME_BUTTON_FONT_COLOR);

        addChild(overlay);
        addChild(button).centerRegistrationPoint().moveTo(WIDTH / 2, HEIGHT / 2 + MESSAGE_PADDING);

        Animation.create(TWEEN_ALPHA_DURATION, Alpha.to(overlay, 0f, 1f), Alpha.to(button, 0f, 1f)).start();

        button.listener(new SimpleListener() {
            @Override
            public void onPointerDown(@Nonnull DisplayObject target, @Nonnull UIActionEvent event) {
                Animation.create(TWEEN_ALPHA_DURATION, Alpha.to(overlay, 0), Alpha.to(button, 0)).
                        listener(new Animation.SimpleListener() {
                            @Override
                            public void onComplete(@Nonnull Animation animation) {
                                removeChild(overlay);
                                removeChild(button);
                                start();
                            }
                        }).start();
            }
        });
    }

    private void right() {
        if (isGameTerminated()) {
            return;
        }

        prepareMove();

        // traverse each row
        for (int y = 0; y < TILE_COUNT; ++y) {

            // from right to left
            for (int x = TILE_COUNT - 2; x >= 0; --x) {
                final Tile tile = tiles[x][y];

                if (tile == null) {
                    continue;
                }

                Tile next = null;
                int nx = x + 1;

                // now search for the next occupied tile
                while (nx < TILE_COUNT) {
                    next = tiles[nx][y];

                    if (next != null) {
                        break;
                    }
                    nx++;
                }

                if (!mergeTiles(tile, next)) {
                    moveTile(tile, nx - 1, y);
                }
            }
        }

        validateMove();
    }

    private void down() {
        if (isGameTerminated()) {
            return;
        }

        prepareMove();

        // traverse each column
        for (int x = 0; x < TILE_COUNT; ++x) {

            // from bottom to top
            for (int y = TILE_COUNT - 2; y >= 0; --y) {
                final Tile tile = tiles[x][y];

                if (tile == null) {
                    continue;
                }

                Tile next = null;
                int ny = y + 1;

                // now search for the next occupied tile
                while (ny < TILE_COUNT) {
                    next = tiles[x][ny];

                    if (next != null) {
                        break;
                    }
                    ny++;
                }

                if (!mergeTiles(tile, next)) {
                    moveTile(tile, x, ny - 1);
                }
            }
        }

        validateMove();
    }

    private void up() {
        if (isGameTerminated()) {
            return;
        }

        prepareMove();

        // traverse each column
        for (int x = 0; x < TILE_COUNT; ++x) {

            // from top to bottom
            for (int y = 1; y < TILE_COUNT; ++y) {
                final Tile tile = tiles[x][y];

                if (tile == null) {
                    continue;
                }

                Tile next = null;
                int ny = y - 1;

                // now search for the next occupied tile
                while (ny >= 0) {
                    next = tiles[x][ny];

                    if (next != null) {
                        break;
                    }
                    ny--;
                }

                if (!mergeTiles(tile, next)) {
                    moveTile(tile, x, ny + 1);
                }
            }
        }

        validateMove();
    }

    private void left() {
        if (isGameTerminated()) {
            return;
        }

        prepareMove();

        // traverse each row
        for (int y = 0; y < TILE_COUNT; ++y) {

            // from right to left
            for (int x = 1; x < TILE_COUNT; ++x) {
                final Tile tile = tiles[x][y];

                if (tile == null) {
                    continue;
                }

                Tile next = null;
                int nx = x - 1;

                // now search for the next occupied tile
                while (nx >= 0) {
                    next = tiles[nx][y];

                    if (next != null) {
                        break;
                    }
                    nx--;
                }

                if (!mergeTiles(tile, next)) {
                    moveTile(tile, nx + 1, y);
                }
            }
        }

        validateMove();
    }

    private void updateScore(@Nonnegative final int value) {
        score += value;

        if (value == 2048) {
            gameWon();
        }
    }

    private boolean playable() {
        for (int x = 0; x < TILE_COUNT; ++x) {
            for (int y = 0; y < TILE_COUNT; ++y) {
                final Tile tile = tiles[x][y];

                if (tile == null) {
                    return true;
                }

                if (x != 3) {
                    // check tile to the right
                    final Tile right = tiles[x + 1][y];

                    if (right == null || right.value == tile.value) {
                        return true;
                    }
                }

                if (y != 3) {
                    // check tile to the
                    final Tile down = tiles[x][y + 1];

                    if (down == null || down.value == tile.value) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Nonnull
    private List<Cell> availableCells() {
        final List<Cell> list = new ArrayList<>();

        for (int x = 0; x < TILE_COUNT; ++x) {
            for (int y = 0; y < TILE_COUNT; ++y) {
                if (tiles[x][y] == null) {
                    list.add(new Cell(x, y));
                }
            }
        }

        return list;
    }

    private void addRandomTile() {
        final List<Cell> cells = availableCells();

        if (cells.isEmpty()) {
            return;
        }

        final Cell cell = cells.get((int) Math.floor(Math.random() * cells.size()));

        final Tile tile = new Tile(cell.x, cell.y, Math.random() < 0.9 ? 2 : 4);

        tile.moveTo(indexToCoordinate(tile.x), indexToCoordinate(tile.y));

        addChild(tile);

        tiles[tile.x][tile.y] = tile;

        animateScale(tile, 0f, 1.f).start();
    }

    private void moveTile(@Nonnull final Tile tile,
                          @Nonnegative final int x,
                          @Nonnegative final int y) {
        if (tile.x == x && tile.y == y) {
            return;
        }

        updateTile(tile, tile.x, tile.y, x, y, false);

        animateMove(tile, tile.x, tile.y).start();

        moved = true;
    }

    private void updateTile(@Nonnull final Tile tile,
                            @Nonnegative final int oldX,
                            @Nonnegative final int oldY,
                            @Nonnegative final int newX,
                            @Nonnegative final int newY,
                            final boolean merge) {
        tiles[oldX][oldY] = null;
        tiles[newX][newY] = tile;

        tile.x = newX;
        tile.y = newY;

        if (merge) {
            merged[newX][newY] = true;
        }

        moved = true;
    }

    private boolean mergeTiles(@Nonnull final Tile tile,
                               @Nullable final Tile next) {
        if (next == null || tile.value != next.value || merged[next.x][next.y]) {
            return false;
        }

        final int value = tile.value * 2;

        final Tile merge = new Tile(next.x, next.y, value);
        merge.moveTo(indexToCoordinate(merge.x), indexToCoordinate(merge.y));
        addChild(merge);

        animateScale(merge, 0, 1f).start();

        updateTile(merge, tile.x, tile.y, next.x, next.y, true);

        updateScore(value);

        // create animation
        animateMove(tile, next.x, next.y).listener(new Animation.SimpleListener() {
            @Override
            public void onComplete(@Nonnull Animation animation) {
                removeChild(tile);
                removeChild(next);
            }
        }).start();

        return true;
    }

    @Nonnull
    private Animation animateMove(@Nonnull final Tile tile, int x, int y) {
        if (tile.animation != null) {
            tile.animation.stop(true);
        }

        final int newX = indexToCoordinate(x);
        final int newY = indexToCoordinate(y);

        return tile.animation = Animation.create(TWEEN_MOVE_DURATION, X.to(tile, newX), Y.to(tile, newY));
    }

    @Nonnull
    private Animation animateScale(@Nonnull final Tile tile, float from, float to) {
        if (tile.animation != null) {
            tile.animation.stop(true);
        }

        final int xPos = indexToCoordinate(tile.x);
        final int yPos = indexToCoordinate(tile.y);

        return tile.animation = Animation.create(TWEEN_SCALE_DURATION,
                ScaleX.to(tile, from, to), ScaleY.to(tile, from, to),
                X.to(tile, xPos + TILE_SIZE / 2, xPos),
                Y.to(tile, yPos + TILE_SIZE / 2, yPos));
    }
}
