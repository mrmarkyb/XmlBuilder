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
import java.io.Writer;

public class XmlOutput {
    public static String toXml(XmlBuilder builder) {
        Document document = createDocument();
        Node root = builder.build(document);
        document.appendChild(root);
        StringWriter target = new StringWriter();
        writeDocument(document, target);
        return target.toString();
    }

    public static void writeDocument(Document doc, Writer target) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(doc), new StreamResult(target));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Document createDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            return docBuilder.newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
