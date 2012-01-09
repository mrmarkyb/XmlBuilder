package uk.co.mrmarkb.xmlbuild;

import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import static org.junit.Assert.assertThat;
import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.attribute;
import static uk.co.mrmarkb.xmlbuild.Matchers.sameNodeAs;

public class XmlAttributeBuilderTest {

    @Test
    public void buildAttribute() {
        Document someDocument = DocumentHelper.someDocument();
        Attr expected = someDocument.createAttributeNS("www.foo.com", "anAttribute");
        expected.setValue("myValue");
        Node actualAttribute = attribute("www.foo.com", "anAttribute", "myValue").build(someDocument);
        assertThat(actualAttribute, sameNodeAs(expected));
    }
}
