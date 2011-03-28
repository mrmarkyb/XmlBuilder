package uk.co.mrmarkyb.xmlbuild;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import uk.co.mrmarkb.xmlbuild.XmlOutput;
import uk.co.mrmarkb.xmlbuild.XmlTextBuilder;

import static org.junit.Assert.assertThat;
import static uk.co.mrmarkyb.xmlbuild.Matchers.sameNodeAs;

public class XmlTextBuilderTest {

    @Test
    public void buildTextNode() {
        Document someDocument = XmlOutput.createDocument();
        Text expected = someDocument.createTextNode("hello there");
        Node actualText = new XmlTextBuilder("hello there").build(someDocument);
        assertThat(actualText, sameNodeAs(expected));
    }
}
