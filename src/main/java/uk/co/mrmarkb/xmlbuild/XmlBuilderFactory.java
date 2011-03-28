package uk.co.mrmarkb.xmlbuild;

import javax.xml.namespace.QName;

public class XmlBuilderFactory {

    public static XmlElementBuilder element(String name) {
        return new XmlElementBuilder(new QName("", name));
    }

    public static XmlElementBuilder element(String uri, String name) {
        return new XmlElementBuilder(new QName(uri, name));
    }

    public static XmlStandaloneNodeBuilder comment(String comment) {
        return new XmlCommentBuilder(comment);
    }

    public static XmlStandaloneNodeBuilder text(String text) {
        return new XmlTextBuilder(text);
    }

    public static XmlAttributeBuilder attribute(String uri, String name, String value) {
        return new XmlAttributeBuilder(new QName(uri,name),value);
    }

    public static XmlAttributeBuilder attribute(String anAttribute, String value) {
        return attribute("", anAttribute, value);
    }
}
