package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;

import static uk.co.mrmarkb.xmlbuild.utils.YetAnotherStringUtils.isBlank;

public class XmlElementBuilder implements XmlStandaloneNodeBuilder {
    private final QName qName;
    private final String value;
    private XmlStandaloneNodeBuilder[] children = new XmlStandaloneNodeBuilder[0];
    private XmlAttributeBuilder[] attributes = new XmlAttributeBuilder[0];

    public XmlElementBuilder(QName qName) {
        this(qName, "");
    }

    public XmlElementBuilder(QName qName, String value) {
        this.qName = qName;
        this.value = value;
    }

    public XmlElementBuilder with(XmlAttributeBuilder... attributes) {
        this.attributes = attributes;
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
            if (attributeBuilder != null) {
                element.setAttributeNode(attributeBuilder.build(document));
            }
        }
        for (XmlBuilder child : children) {
            element.appendChild(child.build(document));
        }
        return element;
    }

    boolean isEmpty() {
        return !hasAttributes()
                && !hasChildren()
                && isBlank(value);
    }

    String elementRootName() {
        return qName.getLocalPart();
    }

    private boolean hasChildren() {
        return children.length > 0;
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

    private boolean hasAttributes() {
        for (XmlAttributeBuilder attribute : attributes) {
            if (attribute != null) {
                return true;
            }
        }
        return false;
    }
}
