package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class XmlRenderer {
    private boolean useHeader = true;
    private boolean usePrettyPrint = true;
    private Document document;

    public XmlRenderer(Document document) {
        this.document = document;
    }

    public static XmlRenderer render(Node node) {
        return new XmlRenderer(wrapNodeInDocument(node));
    }

    public static XmlRenderer render(Document document) {
        return new XmlRenderer(document);
    }

    public static XmlRenderer render(XmlBuilder builder) {
        Document document = createDocument();
        document.appendChild(builder.build(document));
        return new XmlRenderer(document);
    }

    @Override
    public String toString() {

        StringWriter target = new StringWriter();
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            if (!useHeader) {
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            }
            if (usePrettyPrint) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            }

            transformer.transform(new DOMSource(document), new StreamResult(target));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return target.toString();
    }

    private static Document wrapNodeInDocument(Node node) {
        Document document = createDocument();
        document.adoptNode(node);
        document.appendChild(node);
        return document;
    }

    private static Document createDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            return docBuilder.newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public XmlRenderer withXmlHeader(boolean useHeader) {
        this.useHeader = useHeader;
        return this;
    }

    public XmlRenderer withPrettyPrint(boolean usePrettyPrint) {
        this.usePrettyPrint = usePrettyPrint;
        return this;
    }
}
