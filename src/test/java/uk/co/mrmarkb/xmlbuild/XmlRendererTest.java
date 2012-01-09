package uk.co.mrmarkb.xmlbuild;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.*;
import static uk.co.mrmarkb.xmlbuild.XmlRenderer.render;

public class XmlRendererTest {

    @Test
    public void rendersNodeWithoutHeaderAndWithoutPrettyPrint() {
        assertThat(render(someW3cNodeWithName("thing")).withXmlHeader(false).withPrettyPrint(false).toString(), is("<thing/>"));
    }

    @Test
    public void rendersXmlBuilderWithoutHeaderAndWithoutPrettyPrint() {
        assertThat(render(element("thing")).withXmlHeader(false).withPrettyPrint(false).toString(), is("<thing/>"));
    }

    @Test
    public void outputWithoutXmlHeader() {
        assertThat(render(someW3cNode()).withXmlHeader(false).toString(), not(containsString("<?xml")));
    }

    @Test
    public void outputWithXmlHeader() {
        assertThat(render(someW3cNode()).withXmlHeader(true).toString(), containsString("<?xml"));
    }

    @Test
    public void outputWithPrettyPrint() {
        String rendered = render(someW3cNode()).withPrettyPrint(true).toString();
        assertThat(rendered, containsString("\n"));
    }

    @Test
    public void outputTextInitialisedWithNull() throws Exception {
        XmlElementBuilder builder = element("foo").with(text(null));
        render(builder).toString();
    }

    @Test
    public void outputAttributeInitialisedWithNull() throws Exception {
        XmlElementBuilder builder = element("foo").with(attribute("att", null));
        render(builder).toString();
    }

    @Test
    public void outputCommentInitialisedWithNull() throws Exception {
        XmlElementBuilder builder = element("foo").with(comment(null));
        render(builder).toString();
    }

    @Test
    public void outputWithoutPrettyPrint() {
        String rendered = render(someW3cNode()).withPrettyPrint(false).toString();
        assertThat(rendered, not(containsString("\n")));
    }

    private Element someW3cNode() {
        return someW3cNodeWithName("thing");
    }

    private Element someW3cNodeWithName(String name) {
        Document document = DocumentHelper.someDocument();
        return document.createElement(name);
    }

}
