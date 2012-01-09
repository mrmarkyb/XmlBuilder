package uk.co.mrmarkb.xmlbuild;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.mrmarkb.xmlbuild.Namespaces.BA;
import static uk.co.mrmarkb.xmlbuild.Namespaces.FU;
import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.document;
import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.textElement;

public class XmlTextElementBuilderTest {

    private Document someDocument;

    @Before
    public void setUp() throws Exception {
        someDocument = document("blah").withDefaultNamespace(BA).build();
    }

    @Test
    public void canCreateTextElement() throws Exception {
        String name = "name";
        String value = "value";
        Node actual = textElement(FU, name, value).build(someDocument);

        assertThat(actual.getNodeName(), is(name));
        assertThat(actual.getTextContent(), is(value));
    }
}
