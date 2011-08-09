package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;

import static uk.co.mrmarkb.xmlbuild.utils.YetAnotherStringUtils.isBlank;

public class XmlElementBuilder implements XmlStandaloneNodeBuilder {
    private final QName qName;
    private final String value = "";
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

    @Override
    public Element build(Document document) {
        Element element = createElement(document);
        element.setTextContent(value);
        for (XmlAttributeBuilder attributeBuilder : attributes) {
            element.setAttributeNode(attributeBuilder.build(document));
        }
        for (XmlBuilder child : children) {
            element.appendChild(child.build(document));
        }
        return element;
    }

    private Element createElement(Document document) {
        if (hasNamespace()) {
            Element element = document.createElementNS(qName.getNamespaceURI(), qName.getLocalPart());
            element.setPrefix(qName.getPrefix());
            return element;
        } else {
            return document.createElement(qName.getLocalPart());
        }
    }

    private boolean hasNamespace() {
        return !isBlank(qName.getNamespaceURI());
    }
}
