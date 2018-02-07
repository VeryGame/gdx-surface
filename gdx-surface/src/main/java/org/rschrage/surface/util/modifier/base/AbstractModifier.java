package org.rschrage.surface.util.modifier.base;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for a modifier.
 *
 * @author Rico Schrage
 */
public abstract class AbstractModifier implements Modifier {

    /** estimated duration */
    protected final float duration;

    /** remaining duration */
    protected float currentDuration;

    /** target which the modifier will affect */
    protected final ModifierCallback target;

    /** true if currentDuration leq 0 */
    protected boolean finished = false;

    /** list of {@link FinishListener} */
    protected List<FinishListener> finishListenerList;

    /**
     * Constructs an EntityModifier using given target and duration.
     *
     * @param target
     *            target
     * @param duration
     *            estimated duration
     */
    public AbstractModifier(final ModifierCallback target, final float duration) {
        this.duration = duration;
        this.currentDuration = duration;
        this.target = target;
        this.finishListenerList = new ArrayList<>();
    }

    @Override
    public void update() {
        currentDuration -= Gdx.graphics.getDeltaTime();
        if (currentDuration > 0) {
            tick();
        }
        else if (!finished) {
            currentDuration = 0;
            tick();
            finished = true;
        }
    }

    @Override
    public void addFinishListener(FinishListener finishListener) {
        finishListenerList.add(finishListener);
    }

    @Override
    public boolean hasFinished() {
        return finished;
    }

    @Override
    public void onFinish() {
        for (int i = 0; i < finishListenerList.size(); ++i) {
            finishListenerList.get(i).onFinish();
        }
    }

    @Override
    public void kill() {
        finished = true;
    }

    @Override
    public void reset() {
        this.finished = false;
        this.currentDuration = duration;
    }
}