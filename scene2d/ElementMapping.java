package de.verygame.square.core.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import de.verygame.square.core.resource.Resource;
import de.verygame.square.core.resource.ResourceHandler;
import de.verygame.square.core.scene2d.glmenu.impl.element.ButtonTag;
import de.verygame.square.core.scene2d.glmenu.impl.element.ContainerTag;
import de.verygame.square.core.scene2d.glmenu.impl.element.ElementTag;
import de.verygame.square.core.scene2d.glmenu.impl.element.ImageTag;
import de.verygame.square.core.scene2d.glmenu.impl.element.LabelTag;
import de.verygame.square.core.scene2d.glmenu.impl.element.PanelTag;
import de.verygame.square.core.scene2d.glmenu.impl.element.SliderTag;
import de.verygame.square.core.scene2d.widget.Panel;
import de.verygame.square.core.scene2d.widget.Switch;
import de.verygame.xue.mapping.TagMapping;
import de.verygame.xue.mapping.tag.XueTag;

/**
 * @author Rico Schrage
 */
public class ElementMapping implements TagMapping<Actor> {
    private final ResourceHandler resourceHandler;
    private final Resource skinResource;

    /**
     * Constructs a mapping for scene2d.
     */
    public ElementMapping(ResourceHandler resourceHandler, Resource skinResource) {
        this.resourceHandler = resourceHandler;
        this.skinResource = skinResource;
    }

    @Override
    public XueTag<? extends Actor> createTag(String name) {
        Skin skin = resourceHandler.get(skinResource, Skin.class);
        switch (name) {
            case "button":
                return new ButtonTag(skin, resourceHandler);
            case "checkbox":
                return new ElementTag<>(new CheckBox("", skin));
            case "dialog":
                return new ElementTag<>(new Dialog("", skin));
            case "imageButton":
                return new ElementTag<>(new ImageButton(skin));
            case "scrollPane":
                return new ElementTag<>(new ScrollPane(null, skin));
            case "splitPane":
                return new ElementTag<>(new SplitPane(null, null, true, skin));
            case "table":
                return new ElementTag<>(new Table(skin));
            case "container":
                return new ContainerTag<>(new Container<>());
            case "panel":
                return new PanelTag(new Panel(skin), resourceHandler);
            case "switch":
                return new ElementTag<>(new Switch(skin));
            case "label":
                return new LabelTag(skin, resourceHandler);
            case "image":
                return new ImageTag(new Image(), resourceHandler);
            case "progressBar":
                return new ElementTag<>(new ProgressBar(0, 100, 1, false, skin));
            case "select":
                return new ElementTag<>(new SelectBox<String>(skin));
            case "slider":
                return new SliderTag(skin);
            case "textArea":
                return new ElementTag<>(new TextArea("", skin));
            case "textField":
                return new ElementTag<>(new TextField("", skin));

            default:
        }
        return null;
    }
}
