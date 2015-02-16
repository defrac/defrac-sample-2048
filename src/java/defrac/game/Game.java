package defrac.game;

import defrac.animation.Animation;
import defrac.animation.property.display.*;
import defrac.display.Canvas;
import defrac.display.DisplayObject;
import defrac.display.DisplayObjectContainer;
import defrac.display.Stage;
import defrac.display.event.UIActionEvent;
import defrac.event.EventListener;
import defrac.event.Events;
import defrac.event.KeyboardEvent;
import defrac.event.SwipeEvent;
import defrac.lang.Procedure;
import defrac.lang.Supplier;
import defrac.pool.ObjectPool;
import defrac.pool.ObjectPools;
import defrac.util.KeyCode;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 */
public final class Game extends DisplayObjectContainer {
    @Nonnegative
    private static final int GAME_PLAY = 1;
    @Nonnegative
    private static final int GAME_WON = 2;
    @Nonnegative
    private static final int GAME_OVER = 3;

    @Nonnull
    private final GameContext context;
    @Nonnull
    private final Tile[][] tiles;
    @Nonnull
    private boolean[][] merged;
    @Nonnegative
    private int state;
    @Nonnegative
    private int score;

    private boolean moved;

    @Nonnull
    private final ObjectPool<Tile> tilePool;

    @Nonnull
    private final Canvas newGameOverlay;

    @Nonnull
    private final Canvas newGameButton;

    public Game(@Nonnull final Stage stage) {
        this.context = new GameContext(stage);
        this.tiles = new Tile[context.tileCount][context.tileCount];
        this.merged = new boolean[context.tileCount][context.tileCount];
        this.tilePool = ObjectPools.newMaxSizePool(
            context.tileCount + context.tileCount * context.tileCount,
            new Supplier<Tile>() {
                @Override
                public Tile get() {
                    return new Tile(context);
                }
            },
            new Procedure<Tile>() {
                @Override
                public void apply(final Tile tile) {
                    removeChild(tile);
                }
            });
        this.newGameOverlay = new Canvas(context.width, context.height);
        this.newGameButton = createNewGameButton();

        addChild(new Background(context));

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
            public void onEvent(KeyboardEvent e) {
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

    @Nonnull
    private Canvas createNewGameButton() {
        Canvas button = new Canvas(context.newGameButtonWidth, context.newGameButtonHeight);

        DrawUtil.drawRect(button, context.newGameButtonColor, context.newGameButtonCorner);
        DrawUtil.drawTextCentered(button, "New Game", context.newGameButtonFontSize, context.newGameButtonFontColor);

        button.moveTo(context.width / 2, context.height / 2 + context.messagePadding).centerRegistrationPoint();

        button.listener(new SimpleListener() {
            @Override
            public void onPointerDown(@Nonnull DisplayObject target, @Nonnull UIActionEvent event) {
                Animation.create(context.durationAlphaTween, Alpha.to(newGameOverlay, 0), Alpha.to(newGameButton, 0)).
                    listener(new Animation.SimpleListener() {
                        @Override
                        public void onComplete(@Nonnull Animation animation) {
                            removeChild(newGameOverlay);
                            removeChild(newGameButton);
                            start();
                        }
                    }).start();
            }
        });
        return button;
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
        merged = new boolean[context.tileCount][context.tileCount];

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

        finish("You won!", "Score: "+ score);
    }

    private void gameOver() {
        assert state != GAME_OVER;

        state = GAME_OVER;

        finish("Game over!", "Score: "+ score);
    }

    private void finish(@Nonnull final String message, @Nonnull final String score) {
        newGameOverlay.graphics().clearRect(0.0f, 0.0f, newGameOverlay.width(), newGameOverlay.height());

        DrawUtil.drawRect(newGameOverlay, 0x9fffffff, context.backgroundCorner);
        DrawUtil.drawTextCentered(newGameOverlay, message, 0,
            -newGameButton.height() - context.messagePadding - context.scorePadding - context.scoreFontSize,
            context.messageFontSize, context.messageFontColor);
        DrawUtil.drawTextCentered(newGameOverlay, score, 0, -newGameButton.height() - context.scorePadding,
            context.scoreFontSize, context.scoreFontColor);

        addChild(newGameOverlay);
        addChild(newGameButton);

        Animation.create(context.durationAlphaTween, Alpha.to(newGameOverlay, 0f, 1f), Alpha.to(newGameButton, 0f, 1f)).start();
    }

    private void right() {
        if (isGameTerminated()) {
            return;
        }

        prepareMove();

        // traverse each row
        for (int y = 0; y < tiles.length; ++y) {

            // from right to left
            for (int x = tiles.length - 2; x >= 0; --x) {
                final Tile tile = tiles[x][y];

                if (tile == null) {
                    continue;
                }

                Tile next = null;
                int nx = x + 1;

                // now search for the next occupied tile
                while (nx < tiles.length) {
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
        for (int x = 0; x < tiles.length; ++x) {

            // from bottom to top
            for (int y = tiles.length - 2; y >= 0; --y) {
                final Tile tile = tiles[x][y];

                if (tile == null) {
                    continue;
                }

                Tile next = null;
                int ny = y + 1;

                // now search for the next occupied tile
                while (ny < tiles.length) {
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
        for (int x = 0; x < tiles.length; ++x) {

            // from top to bottom
            for (int y = 1; y < tiles.length; ++y) {
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
        for (int y = 0; y < tiles.length; ++y) {

            // from right to left
            for (int x = 1; x < tiles.length; ++x) {
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
        for (int x = 0; x < tiles.length; ++x) {
            for (int y = 0; y < tiles.length; ++y) {
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

        for (int x = 0; x < tiles.length; ++x) {
            for (int y = 0; y < tiles.length; ++y) {
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

        final Tile tile = tilePool.get().init(cell.x, cell.y, Math.random() < 0.9 ? 2 : 4);

        tile.moveTo(context.indexToXPosition(tile.x), context.indexToYPosition(tile.y));

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

        final Tile merge = tilePool.get().init(next.x, next.y, value);
        merge.moveTo(context.indexToXPosition(merge.x), context.indexToYPosition(merge.y));

        addChild(merge);

        animateScale(merge, 0, 1f).start();

        updateTile(merge, tile.x, tile.y, next.x, next.y, true);

        updateScore(value);

        // create animation
        animateMove(tile, next.x, next.y).listener(new Animation.SimpleListener() {
            @Override
            public void onComplete(@Nonnull Animation animation) {
                tilePool.ret(tile);
                tilePool.ret(next);
            }
        }).start();

        return true;
    }

    @Nonnull
    private Animation animateMove(@Nonnull final Tile tile, int x, int y) {
        if (tile.animation != null) {
            tile.animation.stop(true);
        }

        final float newX = context.indexToXPosition(x);
        final float newY = context.indexToYPosition(y);

        return tile.animation = Animation.create(context.durationMoveTween, X.to(tile, newX), Y.to(tile, newY));
    }

    @Nonnull
    private Animation animateScale(@Nonnull final Tile tile, float from, float to) {
        if (tile.animation != null) {
            tile.animation.stop(true);
        }

        final float xPos = context.indexToXPosition(tile.x);
        final float yPos = context.indexToYPosition(tile.y);

        return tile.animation = Animation.create(context.durationScaleTween,
                ScaleX.to(tile, from, to), ScaleY.to(tile, from, to),
                X.to(tile, xPos + tile.width() / 2, xPos),
                Y.to(tile, yPos + tile.height() / 2, yPos));
    }
}
