package uk.co.mrmarkb.xmlbuild;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;
import static uk.co.mrmarkb.xmlbuild.Matchers.sameNodeAs;
import static uk.co.mrmarkb.xmlbuild.Namespaces.BA;
import static uk.co.mrmarkb.xmlbuild.Namespaces.FU;
import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.*;
import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.textElement;
import static uk.co.mrmarkb.xmlbuild.XmlRenderer.render;

public class XmlElementBuilderTest {

    private Document someDocument;

    @Before
    public void setUp() throws Exception {
        someDocument = document("blah").withDefaultNamespace(BA).build();
    }

    @Test
    public void createsEmptyElement() throws ParserConfigurationException, TransformerException, IOException {
        Element expected = someDocument.createElementNS(FU, "elem");
        Element actual = element(FU, "elem").build(someDocument);
        assertThat(actual, sameNodeAs(expected));
    }

    @Test
    public void canHaveAttributes() {
        String expectedValue = "myValue";
        Element element = element(FU, "anElement").with(attribute("anAttribute", expectedValue)).build(someDocument);
        String actualValue = element.getAttributeNode("anAttribute").getValue();
        assertThat(actualValue, is(expectedValue));
    }

    @Test
    public void canHaveEmptyAttribute() throws Exception {
        String expectedValue = null;
        String attributeName = "anAttribute";
        Element element = element(FU, "anElement").with(
                attribute(attributeName, expectedValue)
        ).build(someDocument);
        String actualValue = element.getAttributeNode(attributeName).getValue();

        assertThat(actualValue, is(""));
    }

    @Test
    public void canHaveOptionalAttribute() throws Exception {
        String expectedValue = null;
        String attributeName = "anAttribute";
        Element element = element(FU, "anElement").with(
                optional(
                        attribute(attributeName, expectedValue)
                )
        ).build(someDocument);
        Attr actualAttribute = element.getAttributeNode(attributeName);

        assertThat(actualAttribute, is(nullValue()));
    }

    @Test
    public void canHaveText() {
        String expectedValue = "my text";
        Element element = element(FU, "anElement").with(text(expectedValue)).build(someDocument);
        String actualValue = element.getTextContent();
        assertThat(actualValue, is(expectedValue));
    }


    @Test
    public void canHaveNestedElement() throws Exception {
        Element expectedChild = someDocument.createElementNS("www.no.com", "child");
        Element actual = element(FU, "elem").with(
                element("www.no.com", "child")).build(someDocument);
        Node actualChild = actual.getChildNodes().item(0);
        assertThat(actualChild, sameNodeAs(expectedChild));
    }

    @Test
    public void canHaveOptionalTextElement() throws Exception {
        String expectedValue = "someValue";
        Element element = element(FU, "elem").with(
                optional(textElement("www.no.com", "child", expectedValue))
        ).build(someDocument);

        Node childNode = element.getChildNodes().item(0);
        assertThat(childNode.getTextContent(), is(expectedValue));
    }

    @Test
    public void rendersOptionalTextElementAsCommentWhenEmpty() throws Exception {
        Element element = element(FU, "elem").with(
                optional(textElement("www.no.com", "child", null))
        ).build(someDocument);

        Node childNode = element.getChildNodes().item(0);
        assertThat(childNode.getNodeName(), is("#comment"));
    }

    @Test
    public void canHaveComment() {
        String expectedValue = "my comment";
        Element element = element(FU, "anElement").with(comment(expectedValue)).build(someDocument);
        String actualValue = element.getChildNodes().item(0).getNodeValue();
        assertThat(actualValue, is(expectedValue));
    }

    @Test
    public void canHaveNoNamespace() throws Exception {
        Document document = document("blah")
                .withDefaultNamespace(BA)
                .with(element("anElement").with(text("value"))).build();
        assertThat(render(document).toString(), containsString("<anElement>value</anElement>"));
    }
}
