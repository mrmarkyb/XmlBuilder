package uk.co.mrmarkb.xmlbuild;

import javax.xml.namespace.QName;

import static javax.xml.XMLConstants.NULL_NS_URI;
import static uk.co.mrmarkb.xmlbuild.utils.YetAnotherStringUtils.isBlank;

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

    public static class NamespaceUriPrefixMapping {

        private String uri;
        private String prefix;

        public NamespaceUriPrefixMapping(String uri, String prefix) {
            this.uri = uri;
            this.prefix = prefix;
        }

        public String getUri() {
            return uri;
        }

        public String getPrefix() {
            return prefix;
        }

        public String qualify(String name) {
            return isBlank(prefix) ? name : prefix + ":" + name;
        }

        public static NamespaceUriPrefixMapping namespace(String uri, String prefix) {
            return new NamespaceUriPrefixMapping(uri, prefix);
        }
    }
}
