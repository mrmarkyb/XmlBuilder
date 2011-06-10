package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import static uk.co.mrmarkb.xmlbuild.utils.YetAnotherStringUtils.string;

public class XmlTextBuilder implements XmlStandaloneNodeBuilder {
    private final String text;

    public XmlTextBuilder(String text) {
        this.text = string(text);
    }

    @Override
    public Node build(Document document) {
        return document.createTextNode(text);
    }
}
