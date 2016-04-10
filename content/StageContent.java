package de.verygame.square.core.content;

import com.badlogic.gdx.Gdx;

import de.verygame.square.core.Content;
import de.verygame.square.core.ScreenContext;
import de.verygame.square.core.ScreenId;
import de.verygame.square.core.event.EventEmitter;
import de.verygame.square.resource.Resource;
import de.verygame.square.resource.ResourceHandler;
import de.verygame.square.scene2d.glmenu.impl.GLMenuStage;

/**
 * @author Rico Schrage
 */
public abstract class StageContent extends EventEmitter implements Content {

    /** Resource handler which contains all ui resources */
    protected final ResourceHandler res;

    /** Stage, which loads the xml */
    protected GLMenuStage stage;

    /** Context of the screen */
    protected ScreenContext context;

    /**
     * Creates the user interface of the game.
     *
     *  @param res handler, which contains an applicable skin, the markup file and the language pack.
     */
    public StageContent(ResourceHandler res) {
        this.res = res;
    }

    /**
     * Should return the xml resource, which describes the xml of the glmenuStage.
     *
     * @return Resource of the type XML
     */
    protected abstract Resource defineXML();

    /**
     * Should return the skin which will be used by all stage elements.
     *
     * @return Resource of the type skin
     */
    protected abstract Resource defineSkin();

    @Override
    public void onBind(ScreenContext context) {
        this.context = context;

        this.stage = new GLMenuStage(context.getBatch(), res.getXMLAsStream(defineXML()), res, defineSkin());
        this.stage.setViewport(context.getViewport());
        this.stage.bind(this);
        this.stage.loadMenu();
    }

    @Override
    public void onActivate(ScreenId predecessor) {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onUpdate() {
        stage.act();
    }

    @Override
    public void onRender() {
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void onDeactivate(ScreenId successor) {
        //default: do nothing
    }

    @Override
    public void onResize(int width, int height) {
        //default: do nothing
    }

    @Override
    public void onPause() {
        //default: do nothing
    }

    @Override
    public void onResume() {
        //default: do nothing
    }
}