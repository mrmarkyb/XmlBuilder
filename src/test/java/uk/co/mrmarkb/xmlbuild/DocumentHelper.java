package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DocumentHelper {

    public static Document someDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            return docBuilder.newDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
