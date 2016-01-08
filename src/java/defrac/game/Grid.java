package defrac.game;

import defrac.animation.Animation;
import defrac.animation.property.display.Move;
import defrac.animation.property.display.Scale;
import defrac.display.Canvas;
import defrac.display.DisplayObjectContainer;
import defrac.lang.Procedure;
import defrac.lang.Supplier;
import defrac.pool.ObjectPool;
import defrac.pool.ObjectPools;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 */
public final class Grid extends DisplayObjectContainer {
  @Nonnull
  private final Context context;
  @Nonnull
  private final Tile[][] tiles;
  @Nonnull
  private final Cell[][] cells;

  private boolean moved;

  private int value;

  @Nonnull
  public final ObjectPool<Tile> tilePool;

  public Grid(@Nonnull final Context context) {
    this.context = context;
    this.tiles = new Tile[context.tileCount][context.tileCount];
    this.cells = new Cell[context.tileCount][context.tileCount];
    this.tilePool = ObjectPools.newMaxSizePool(
        0,
        context.tileCount + context.tileCount * context.tileCount,
        new Supplier<Tile>() {
          @Override
          public Tile get() {
            return new Tile(context);
          }
        },
        new Procedure<Tile>() {
          @Override
          public void apply(Tile tile) {
          }
        }
    );

    final Canvas background = new Canvas(context.style.gridWidth, context.style.gridHeight);

    GraphicsUtil.drawRect(background, context.style.gridColor, context.style.gridCorner);

    addChild(background);

    float x = context.style.padding;

    for (int i = 0; i < cells.length; ++i, x += context.style.tileWidth + context.style.tilePadding) {
      float y = context.style.padding;

      for (int j = 0; j < cells.length; ++j, y += context.style.tileHeight + context.style.tilePadding) {
        // draw tile background
        GraphicsUtil.drawRect(background, x, y, context.style.tileWidth, context.style.tileHeight,
            context.style.tileColor, context.style.tileCorner);

        // init cells
        cells[i][j] = new Cell(context, i, j);
      }
    }

    context.add(new Model.Listener() {
      @Override
      public void onEvent(@Nonnegative final int event) {
        switch (event) {
          case Context.MOVE_LEFT_EVENT:
            moveLeft();
            break;
          case Context.MOVE_RIGHT_EVENT:
            moveRight();
            break;
          case Context.MOVE_DOWN_EVENT:
            moveDown();
            break;
          case Context.MOVE_UP_EVENT:
            moveUp();
            break;
        }
      }
    });
  }

  public void reset() {
    for (int x = 0; x < tiles.length; ++x) {
      for (int y = 0; y < tiles.length; ++y) {
        final Tile tile = tiles[x][y];

        if (tile != null) {
          tilePool.ret(tile.dispose());

          tiles[x][y] = null;
        }
      }
    }
  }

  public boolean moveable() {
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
          // check tile to the bottom
          final Tile down = tiles[x][y + 1];

          if (down == null || down.value == tile.value) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public void moveRight() {
    if (context.gameTerminated()) {
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
          moveTile(tile, cells[nx - 1][y]);
        }
      }
    }

    validateMove();
  }

  public void moveDown() {
    if (context.gameTerminated()) {
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
          moveTile(tile, cells[x][ny - 1]);
        }
      }
    }

    validateMove();
  }

  public void moveUp() {
    if (context.gameTerminated()) {
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
          moveTile(tile, cells[x][ny + 1]);
        }
      }
    }

    validateMove();
  }

  public void moveLeft() {
    if (context.gameTerminated()) {
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
          moveTile(tile, cells[nx + 1][y]);
        }
      }
    }

    validateMove();
  }

  private void prepareMove() {
    moved = false;
    value = 0;

    for (int x = 0; x < cells.length; ++x) {
      for (int y = 0; y < cells.length; ++y) {
        cells[x][y].merged = false;
      }
    }
  }

  private void validateMove() {
    if (moved) {
      if (value != 0) {
        context.updateScore(value);
      }

      addRandomTile();

      if (!moveable()) {
        context.gameOver();
      }
    }
  }

  public void addRandomTile() {
    final List<Cell> cells = availableCells();

    if (cells.isEmpty()) {
      return;
    }

    final Cell cell = cells.get((int) Math.floor(Math.random() * cells.size()));
    final Tile tile = tilePool.get();
    tile.cell(cell);
    tile.value(Math.random() < 0.9 ? 2 : 4);
    tile.moveTo(cell);

    tiles[cell.x][cell.y] = tile;

    addChild(tile);

    animateCreation(tile, cell);
  }

  private boolean mergeTiles(@Nonnull final Tile tile, @Nullable final Tile next) {
    if (next == null || tile.value != next.value || next.cell().merged) {
      return false;
    }

    // create merged tile
    final Tile merge = tilePool.get();
    merge.cell(next.cell());
    merge.value(tile.value * 2);
    merge.moveTo(merge.cell());
    merge.cell().merged = true;

    tiles[tile.cell().x][tile.cell().y] = null;
    tiles[merge.cell().x][merge.cell().y] = merge;

    addChild(merge);

    animateMovement(tile, next.cell());
    animateCreation(merge, merge.cell());

    tile.animation().listener(new Animation.SimpleListener() {
      @Override
      public void onComplete(@Nonnull Animation animation) {
        tilePool.ret(tile.dispose());
        tilePool.ret(next.dispose());
      }
    });

    // update the score with the merged value
    value += merge.value;

    return moved = true;
  }

  private boolean moveTile(@Nonnull final Tile tile, @Nonnull final Cell cell) {
    if (tile.cell == cell) {
      return false;
    }

    tiles[tile.cell().x][tile.cell().y] = null;
    tiles[cell.x][cell.y] = tile;

    tile.cell = cell;

    animateMovement(tile, cell);

    return moved = true;
  }

  private void animateMovement(@Nonnull final Tile tile, @Nonnull final Cell cell) {
    final float xPos = cell.xCoordinate();
    final float yPos = cell.yCoordinate();

    if (tile.movement != null) {
      tile.movement.stop(true);
    }

    tile.movement = Animation.create(context.animationSystem, context.style.durationMoveTween, Move.to(tile, xPos, yPos));
    tile.movement.start();
  }

  private void animateCreation(@Nonnull final Tile tile, @Nonnull final Cell cell) {
    tile.scaleTo(0);

    final float xPos = cell.xCoordinate();
    final float yPos = cell.yCoordinate();

    tile.scaling = Animation.create(context.animationSystem, context.style.durationScaleTween, Scale.to(tile, 0f, 0f, 1f, 1f));
    tile.scaling.start();

    tile.movement = Animation.create(context.animationSystem, context.style.durationScaleTween, Move.to(tile, xPos + tile.width() / 2, yPos + tile.height() / 2, xPos, yPos));
    tile.movement.start();
  }

  @Nonnull
  private List<Cell> availableCells() {
    final List<Cell> list = new ArrayList<>();

    for (int x = 0; x < cells.length; ++x) {
      for (int y = 0; y < cells.length; ++y) {
        if (tiles[x][y] == null) {
          list.add(cells[x][y]);
        }
      }
    }

    return list;
  }
}
