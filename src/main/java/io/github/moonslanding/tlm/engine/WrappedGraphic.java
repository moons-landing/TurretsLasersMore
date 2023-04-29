package io.github.moonslanding.tlm.engine;

import java.awt.*;

public class WrappedGraphic {

    private int zoomFactor;
    private Graphics graphic;

    public WrappedGraphic(Graphics graphic, int zoomFactor) {
        this.zoomFactor = zoomFactor;
        this.graphic = graphic;
    }

    public int getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(int zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public Graphics getGraphic() {
        return graphic;
    }
}
