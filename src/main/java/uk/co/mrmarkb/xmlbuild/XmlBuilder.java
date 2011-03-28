package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface XmlBuilder {
    Node build(Document document);
}
