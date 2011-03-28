package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;

public class XmlElementBuilder implements XmlStandaloneNodeBuilder {
    private QName qName;
    private String value = "";
    private XmlStandaloneNodeBuilder[] children = new XmlStandaloneNodeBuilder[0];
    private XmlAttributeBuilder[] attributes = new XmlAttributeBuilder[0];

    public XmlElementBuilder(QName qName) {
        this.qName = qName;
    }

    public XmlElementBuilder with(XmlAttributeBuilder... children) {
        this.attributes = children;
        return this;
    }

    public XmlElementBuilder with(XmlStandaloneNodeBuilder... children) {
        this.children = children;
        return this;
    }

    public Element build(Document document) {
        Element element = document.createElementNS(qName.getNamespaceURI(), qName.getLocalPart());
        element.setTextContent(value);
        for (XmlAttributeBuilder attributeBuilder : attributes) {
            element.setAttributeNode(attributeBuilder.build(document));
        }
        for (XmlBuilder child : children) {
            element.appendChild(child.build(document));
        }
        return element;
    }
}
