package de.verygame.surface.resource;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.I18NBundle;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Locale;

import de.verygame.surface.event.Event;
import de.verygame.surface.event.EventListener;
import de.verygame.surface.util.test.LibGdxTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Rico Schrage
 */
public class ResourceHandlerTest extends LibGdxTest {

    private ResourceHandler resourceHandler;

    @Before
    public void setUp() throws Exception {
        resourceHandler = new ResourceHandler();
    }

    @Test
    public void testLoadResource() throws Exception {
        //when
        resourceHandler.loadResource(TestResource.TEST_RESOURCE);
        resourceHandler.waitForAssets();

        //then
        assertEquals(Texture.class, resourceHandler.get(TestResource.TEST_RESOURCE, Texture.class).getClass());
    }

    @Test
    public void testAddResource() throws Exception {
        //when
        resourceHandler.addResource(de.verygame.surface.resource.ResourceType.TEX, "tex/circle.png");
        resourceHandler.waitForAssets();

        //then
        assertEquals(Texture.class, resourceHandler.get("tex/circle.png", Texture.class).getClass());
    }

    @Test
    public void testCreateFont() throws Exception {
        //when
        FreeTypeFontGenerator.FreeTypeFontParameter para = new FreeTypeFontGenerator.FreeTypeFontParameter();
        resourceHandler.loadResource(TestResource.TEST_FONT);
        resourceHandler.waitForAssets();

        //then
        assertEquals(BitmapFont.class, resourceHandler.createFont(TestUnit.TEST_BITMAP, para).getClass());
    }

    @Test
    public void testLoadResourceExcept() {
        //when
        resourceHandler.loadResourceExcept(TestResource.values(), TestResource.TEST_FONT);
        resourceHandler.waitForAssets();

        //then
        assertEquals(Texture.class, resourceHandler.get(TestResource.TEST_RESOURCE, Texture.class).getClass());
    }

    @Test(expected = GdxRuntimeException.class)
    public void testLoadResourceExceptException() {
        //when
        resourceHandler.loadResourceExcept(TestResource.values(), TestResource.TEST_FONT);
        resourceHandler.waitForAssets();

        //then
        resourceHandler.get(TestResource.TEST_FONT, FreeTypeFontGenerator.class);
    }

    @Test
    public void testLoadLanguage() {
        //given
        resourceHandler.loadResource(TestResource.TEST_LANG);
        EventListener l = mock(EventListener.class);
        resourceHandler.register(Event.EventType.RESOURCE, l);
        resourceHandler.waitForAssets();

        //when
        resourceHandler.loadLanguage(Locale.ENGLISH);
        resourceHandler.waitForAssets();

        //then
        verify(l).handleEvent(Event.OPTION_CHANGED);
        assertTrue(resourceHandler.get(TestResource.TEST_LANG, I18NBundle.class).getLocale() == Locale.ENGLISH);
    }

    @Test(expected = IllegalStateException.class)
    public void testLoadLanguageException() {
        //then
        resourceHandler.loadLanguage(Locale.ENGLISH);
    }

    @Test
    public void testGetString() {
        //given
        resourceHandler.loadResource(TestResource.TEST_LANG);
        resourceHandler.waitForAssets();

        //when
        String result = resourceHandler.getString("test");

        //then
        assertEquals("super", result);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetStringException() {
        //then
        resourceHandler.getString("test");
    }

    @Test
    public void testGetXML() {
        //given
        resourceHandler.loadResource(TestResource.TEST_XML);
        resourceHandler.waitForAssets();

        //when
        String xmlContent = resourceHandler.getXML("background.xml");

        //then
        assertEquals("ABC", xmlContent);
    }

    @Test
    public void testGetXMLResource() {
        //given
        resourceHandler.loadResource(TestResource.TEST_XML);
        resourceHandler.waitForAssets();

        //when
        String xmlContent = resourceHandler.getXML(TestResource.TEST_XML);

        //then
        assertEquals("ABC", xmlContent);
    }

    @Test(expected = GdxRuntimeException.class)
    public void testGetXMLResourceException() {
        //when
        String xmlContent = resourceHandler.getXML(TestResource.TEST_XML);
    }

    @Test
    public void testGetXMLNull() {
        //when
        String xml = resourceHandler.getXML("background.xml");

        //then
        assertTrue(xml == null);
    }

    @Test
    public void testGetXMLAsStream() throws Exception {
        //given
        resourceHandler.loadResource(TestResource.TEST_XML);
        resourceHandler.waitForAssets();

        //when
        InputStream xmlContent = resourceHandler.getXMLAsStream(TestResource.TEST_XML);

        //then
        assertTrue(xmlContent != null);
        assertTrue(xmlContent.available() > 0);
    }

    @Test
    public void testGetRegion() {
        //given
        resourceHandler.loadResource(TestResource.TEST_ATLAS);
        resourceHandler.waitForAssets();

        //when
        TextureRegion textureRegion = resourceHandler.getRegion(TestUnit.TEST_REGION);
        TextureRegion textureRegionSecond = resourceHandler.getRegion(TestUnit.TEST_REGION);

        //then
        assertTrue(textureRegion != null);
        assertTrue(textureRegionSecond != null);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void testGetRegionException() {
        resourceHandler.getRegion(TestUnit.TEST_BITMAP);
    }

    @Test
    public void testGetFont() {
        //given
        FreeTypeFontGenerator.FreeTypeFontParameter para = new FreeTypeFontGenerator.FreeTypeFontParameter();
        resourceHandler.loadResource(TestResource.TEST_FONT);
        resourceHandler.waitForAssets();
        resourceHandler.createFont(TestUnit.TEST_BITMAP, para).getClass();

        //when
        BitmapFont font = resourceHandler.getFont(TestUnit.TEST_BITMAP);

        //then
        assertTrue(font != null);
    }

    @Test
    public void testDispose() {
        resourceHandler.dispose();
    }

}