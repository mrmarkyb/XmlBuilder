package uk.co.mrmarkb.xmlbuild;

import org.junit.Test;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import static org.junit.Assert.assertThat;
import static uk.co.mrmarkb.xmlbuild.Matchers.sameNodeAs;

public class XmlCommentBuilderTest {

    @Test
    public void createsAComment() {
        Document someDocument = DocumentHelper.someDocument();
        Comment expected = someDocument.createComment("a comment");
        Node actualComment = new XmlCommentBuilder("a comment").build(someDocument);
        assertThat(actualComment, sameNodeAs(expected));
    }

    @Test
    public void createsAnEmptyCommentForNullString() {
        Document someDocument = DocumentHelper.someDocument();
        Comment expected = someDocument.createComment("");
        Node actualComment = new XmlCommentBuilder(null).build(someDocument);
        assertThat(actualComment, sameNodeAs(expected));
    }
}
