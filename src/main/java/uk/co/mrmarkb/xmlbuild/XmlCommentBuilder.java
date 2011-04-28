package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XmlCommentBuilder implements XmlStandaloneNodeBuilder {
    private final String comment;

    public XmlCommentBuilder(String comment) {
        this.comment = comment!=null?comment:"";
    }

    public Node build(Document document) {
        return document.createComment(comment);
    }
}
