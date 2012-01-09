package uk.co.mrmarkb.xmlbuild;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import static org.junit.Assert.assertThat;
import static uk.co.mrmarkb.xmlbuild.Matchers.sameNodeAs;

public class XmlTextBuilderTest {

    @Test
    public void buildTextNode() {
        Document someDocument = DocumentHelper.someDocument();
        Text expected = someDocument.createTextNode("hello there");
        Node actualText = new XmlTextBuilder("hello there").build(someDocument);
        assertThat(actualText, sameNodeAs(expected));
    }

    @Test
    public void buildTextNodeReplaceNullWithEmptyString() {
        Document someDocument = DocumentHelper.someDocument();
        Text expected = someDocument.createTextNode("");
        Node actualText = new XmlTextBuilder(null).build(someDocument);
        assertThat(actualText, sameNodeAs(expected));
    }


}
