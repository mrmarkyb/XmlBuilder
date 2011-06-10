package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static javax.xml.XMLConstants.XMLNS_ATTRIBUTE;
import static javax.xml.XMLConstants.XMLNS_ATTRIBUTE_NS_URI;

public class XmlDocumentBuilder {
    private String rootName;
    private  XmlBuilderFactory.NamespaceUriPrefixMapping prefixMapping;

    private String defaultNamespaceName;
    private Set<XmlBuilderFactory.NamespaceUriPrefixMapping> namespaceDeclarations = new HashSet<XmlBuilderFactory.NamespaceUriPrefixMapping>();
    private List<XmlStandaloneNodeBuilder> xmlBuilders=new ArrayList<XmlStandaloneNodeBuilder>();

    public XmlDocumentBuilder(String rootName) {
        this.rootName = rootName;
        //this.prefixMapping = new XmlBuilderFactory.NamespaceUriPrefixMapping(NULL_NS_URI,DEFAULT_NS_PREFIX);
    }

    public XmlDocumentBuilder(String rootName, XmlBuilderFactory.NamespaceUriPrefixMapping prefixMapping) {
        this.rootName = rootName;
        this.prefixMapping = prefixMapping;
    }

    public Document build() {
        Document document = getNamespaceAwareDocument();

        Element documentElement;
        if (prefixMapping == null) {
            documentElement = document.createElement(rootName);
        } else {
            documentElement = document.createElementNS(prefixMapping.getUri(), prefixMapping.qualify(rootName));
        }
        
        documentElement.setAttributeNS(XMLNS_ATTRIBUTE_NS_URI, XMLNS_ATTRIBUTE, defaultNamespaceName);
        for (XmlBuilderFactory.NamespaceUriPrefixMapping nsMapping : namespaceDeclarations) {
            documentElement.setAttributeNS(XMLNS_ATTRIBUTE_NS_URI, XMLNS_ATTRIBUTE + ":" + nsMapping.getPrefix(), nsMapping.getUri());
        }

        for (XmlStandaloneNodeBuilder xmlBuilder : xmlBuilders) {
            documentElement.appendChild(xmlBuilder.build(document));
        }

        document.appendChild(documentElement);
        return document;
    }

    public XmlDocumentBuilder withDefaultNamespace(String defaultNamespaceName) {
        this.defaultNamespaceName = defaultNamespaceName;
        return this;
    }

    public XmlDocumentBuilder with(XmlBuilderFactory.NamespaceUriPrefixMapping namespaceUriPrefixMapping) {
        namespaceDeclarations.add(namespaceUriPrefixMapping);
        return this;
    }

    private Document getNamespaceAwareDocument() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);

        DocumentBuilder builder;
        try {
            builder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        return builder.newDocument();
    }

    public XmlDocumentBuilder with(XmlStandaloneNodeBuilder... xmlBuilders) {
        this.xmlBuilders.addAll(asList(xmlBuilders));
        return this;
    }
}
