package de.verygame.surface;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.junit.Before;
import org.junit.Test;

import de.verygame.surface.screen.base.Content;
import de.verygame.surface.screen.base.SubScreenContext;
import de.verygame.surface.screen.base.SubScreenId;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Rico Schrage
 */
public class SubScreenContextTest {

    private enum SubScreenKeys implements SubScreenId { ONE }

    private Viewport view = mock(Viewport.class);
    private de.verygame.surface.screen.base.SubScreenContext context = new SubScreenContext(view);

    @Before
    public void prepare() {
        context.setBatch(mock(PolygonSpriteBatch.class));
    }

    @Test
    public void testSetBatch() throws Exception {
        //given
        PolygonSpriteBatch batch = mock(PolygonSpriteBatch.class);

        //when
        context.setBatch(batch);

        //then
        assertEquals(batch, context.getBatch());
    }

    @Test
    public void testGetViewport() throws Exception {
        //when
        Viewport view = context.getViewport();

        //then
        assertEquals(this.view, view);
    }

    @Test
    public void testAddSubScreen() throws Exception {
        //given
        de.verygame.surface.screen.base.SubScreen subScreen = mock(de.verygame.surface.screen.base.SubScreen.class);
        when(subScreen.getContent()).thenReturn(mock(de.verygame.surface.screen.base.Content.class));

        //when
        context.addSubScreen(SubScreenKeys.ONE, subScreen);
        context.showScreen(SubScreenKeys.ONE);

        //then
        assertEquals(subScreen, context.getActiveSubScreen());
    }

    @Test
    public void testHideScreen() throws Exception {
        //given
        de.verygame.surface.screen.base.SubScreen subScreen = mock(de.verygame.surface.screen.base.SubScreen.class);
        when(subScreen.getContent()).thenReturn(mock(Content.class));

        //when
        context.addSubScreen(SubScreenKeys.ONE, subScreen);
        context.showScreen(SubScreenKeys.ONE);
        context.hideScreen();
        context.renderScreen();

        //then
        verify(subScreen, never()).onRender();
    }
}