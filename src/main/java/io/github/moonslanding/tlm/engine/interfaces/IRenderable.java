package io.github.moonslanding.tlm.engine.interfaces;

import io.github.moonslanding.tlm.engine.WrappedGraphic;

public interface IRenderable {

    void render(WrappedGraphic graphics);

    void render(WrappedGraphic graphics, int x, int y);

}
