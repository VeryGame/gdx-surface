package de.verygame.surface.scene2d.xue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.verygame.surface.resource.ResourceHandler;
import de.verygame.util.math.CoordinateType;
import de.verygame.xue.mapping.GlobalMappings;

/**
 * Implementation of {@link GlobalMappings} for scene 2d.
 *
 * @author Rico Schrage
 */
public class Scene2DMapping implements GlobalMappings<Actor> {
    private final ResourceHandler resourceHandler;

    public Scene2DMapping(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public float calcFromRelativeValue(Actor target, float value, CoordinateType coordFlag) {
        if (coordFlag == CoordinateType.X) {
            if (target.getParent() == null) {
                return 0.01f * Gdx.graphics.getWidth() * value;
            }
            else {
                return 0.01f * target.getParent().getWidth() * value;
            }
        }
        else if (coordFlag == CoordinateType.Y) {
            if (target.getParent() == null) {
                return 0.01f * Gdx.graphics.getHeight() * value;
            }
            else {
                return 0.01f * target.getParent().getHeight() * value;
            }
        }
        return -1;
    }

    @Override
    public String getString(String key) {
        return resourceHandler.getString(key);
    }

    @Override
    public float getDensity() {
        return Gdx.graphics.getDensity();
    }

}