package uk.co.mrmarkb.xmlbuild;

import javax.xml.namespace.QName;

import static javax.xml.XMLConstants.NULL_NS_URI;

public class XmlBuilderFactory {

    public static XmlElementBuilder element(String name) {
        return new XmlElementBuilder(new QName(name));
    }

    public static XmlElementBuilder element(String uri, String name) {
        return new XmlElementBuilder(new QName(uri, name));
    }

    public static XmlElementBuilder element(NamespaceUriPrefixMapping namespaceUriPrefixMapping, String name) {
        return new XmlElementBuilder(new QName(namespaceUriPrefixMapping.getUri(), name, namespaceUriPrefixMapping.getPrefix()));
    }

    public static XmlTextElementBuilder textElement(NamespaceUriPrefixMapping namespaceUriPrefixMapping, String name, String value) {
        return new XmlTextElementBuilder(new QName(namespaceUriPrefixMapping.getUri(), name, namespaceUriPrefixMapping.getPrefix()), value);
    }

    public static XmlTextElementBuilder textElement(String uri, String name, String value) {
        return new XmlTextElementBuilder(new QName(uri, name), value);
    }

    public static XmlTextElementBuilder textElement(String name, String value) {
        return new XmlTextElementBuilder(new QName(name), value);
    }

    public static XmlStandaloneNodeBuilder comment(String comment) {
        return new XmlCommentBuilder(comment);
    }

    public static XmlStandaloneNodeBuilder text(String text) {
        return new XmlTextBuilder(text);
    }

    public static XmlAttributeBuilder attribute(String uri, String name, String value) {
        return new XmlAttributeBuilder(new QName(uri, name), value);
    }

    public static XmlAttributeBuilder attribute(String anAttribute, String value) {
        return attribute(NULL_NS_URI, anAttribute, value);
    }

    public static XmlDocumentBuilder document(String rootName, NamespaceUriPrefixMapping prefixMapping) {
        return new XmlDocumentBuilder(rootName, prefixMapping);
    }

    public static XmlDocumentBuilder document(String rootName) {
        return new XmlDocumentBuilder(rootName);
    }

    public static XmlAttributeBuilder optional(XmlAttributeBuilder anAttribute) {
        return anAttribute.isEmpty() ? null : anAttribute;
    }

    public static XmlStandaloneNodeBuilder optional(XmlTextElementBuilder child) {
        return child.isEmpty() ? child.comment() : child;
    }

}
