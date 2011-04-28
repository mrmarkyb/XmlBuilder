package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XmlTextBuilder implements XmlStandaloneNodeBuilder {
    private final String text;

    public XmlTextBuilder(String text) {
            this.text = text!= null?text:"";
    }

    public Node build(Document document) {
        return document.createTextNode(text);
    }
}
