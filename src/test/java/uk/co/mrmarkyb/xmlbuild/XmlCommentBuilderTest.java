package uk.co.mrmarkyb.xmlbuild;

import org.junit.Test;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import uk.co.mrmarkb.xmlbuild.XmlCommentBuilder;
import uk.co.mrmarkb.xmlbuild.XmlOutput;

import static org.junit.Assert.assertThat;
import static uk.co.mrmarkyb.xmlbuild.Matchers.sameNodeAs;

public class XmlCommentBuilderTest {

    @Test
    public void createsAComment() {
        Document someDocument = XmlOutput.createDocument();
        Comment expected = someDocument.createComment("a comment");
        Node actualComment = new XmlCommentBuilder("a comment").build(someDocument);
        assertThat(actualComment, sameNodeAs(expected));
    }
}
