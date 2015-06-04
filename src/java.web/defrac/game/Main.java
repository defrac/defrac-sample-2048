package defrac.game;

import defrac.ui.FrameBuilder;
import defrac.web.HTMLBodyElement;
import defrac.web.HTMLCanvasElement;
import defrac.web.HTMLDocument;

import static defrac.lang.Preconditions.checkNotNull;
import static defrac.web.Toplevel.window;

/**
 *
 */
public final class Main {
  public static void main(String[] args) {
    final HTMLDocument document = checkNotNull((HTMLDocument)window().document);
    final HTMLBodyElement body = checkNotNull((HTMLBodyElement)document.body);

    body.style.backgroundColor = "#000";

    FrameBuilder.
        forScreen(new GameScreen()).
        show();

    final HTMLCanvasElement canvas =
        checkNotNull((HTMLCanvasElement) document.querySelector("canvas"));

    checkNotNull(canvas).focus();
  }
}
