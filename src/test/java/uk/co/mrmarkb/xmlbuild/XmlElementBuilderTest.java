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

import static javax.xml.XMLConstants.NULL_NS_URI;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;
import static uk.co.mrmarkb.xmlbuild.Matchers.sameNodeAs;
import static uk.co.mrmarkb.xmlbuild.Namespaces.BA;
import static uk.co.mrmarkb.xmlbuild.Namespaces.FU;
import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.*;
import static uk.co.mrmarkb.xmlbuild.XmlRenderer.render;

public class XmlElementBuilderTest {

    private Document someDocument;

    @Before
    public void setUp() throws Exception {
        someDocument = document("blah").withDefaultNamespace(BA.getUri()).build();
    }

    @Test
    public void createsEmptyElement() throws ParserConfigurationException, TransformerException, IOException {
        Element expected = someDocument.createElementNS(NULL_NS_URI, "elem");
        Element actual = element("elem").build(someDocument);
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
        Element expectedChild = someDocument.createElement("child");
        Element actual = element(FU, "elem").with(
                element("child")).build(someDocument);
        Node actualChild = actual.getChildNodes().item(0);
        assertThat(actualChild, sameNodeAs(expectedChild));
    }

    @Test
    public void canHaveOptionalTextElement() throws Exception {
        String expectedValue = "someValue";
        Element element = element(FU, "elem").with(
                optional(element(BA, "child", expectedValue))
        ).build(someDocument);

        Node childNode = element.getChildNodes().item(0);
        assertThat(childNode.getTextContent(), is(expectedValue));
    }

    @Test
    public void rendersOptionalTextElementAsCommentWhenEmpty() throws Exception {
        Element element = element(FU, "elem").with(
                optional(element(BA, "child", ""))
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
                .withDefaultNamespace(BA.getUri())
                .with(element("anElement").with(text("value"))).build();
        assertThat(render(document).toString(), containsString("<anElement>value</anElement>"));
    }

    @Test
    public void isNotEmptyWhenAttributesPresent() throws Exception {
        XmlElementBuilder elementBuilder = element("element").with(attribute("anAttribute", "value"));

        assertThat(elementBuilder.isEmpty(), is(false));
    }

    @Test
    public void isNotEmptyWhenChildrenPresent() throws Exception {
        XmlElementBuilder elementBuilder = element("element").with(element("anElement"));

        assertThat(elementBuilder.isEmpty(), is(false));
    }

    @Test
    public void isNotEmptyWhenValuePresent() throws Exception {
        XmlElementBuilder elementBuilder = element(BA, "element", "value");

        assertThat(elementBuilder.isEmpty(), is(false));
    }

    @Test
    public void isEmptyWhenEmpty() throws Exception {
        XmlElementBuilder elementBuilder = element(BA, "element", "");

        assertThat(elementBuilder.isEmpty(), is(true));
    }

    @Test
    public void isEmptyWhenOnlyEmptyOptionalAttributesPresent() throws Exception {
        XmlElementBuilder elementBuilder = element(BA, "element", "").with(optional(attribute("empty", "")));

        assertThat(elementBuilder.isEmpty(), is(true));
    }
}