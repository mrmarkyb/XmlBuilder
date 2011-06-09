package uk.co.mrmarkyb.xmlbuild;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.*;
import static uk.co.mrmarkyb.xmlbuild.Matchers.sameNodeAs;
import static uk.co.mrmarkyb.xmlbuild.Namespaces.FU;

public class XmlElementBuilderTest {

    private Document someDocument;

    @Before
    public void setUp() throws Exception {
        someDocument = DocumentHelper.someDocument();
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
    public void canHaveComment() {
        String expectedValue = "my comment";
        Element element = element(FU, "anElement").with(comment(expectedValue)).build(someDocument);
        String actualValue = element.getChildNodes().item(0).getNodeValue();
        assertThat(actualValue, is(expectedValue));
    }
}
