package de.verygame.surface.screen.base;

/**
 * @author Rico Schrage
 */
public interface SubScreen extends Screen {

    /**
     * If set to true the screen won't consume touch events.
     *
     * @param modal true if the subscreen should consume the touch events.
     */
    void setModal(boolean modal);

}
