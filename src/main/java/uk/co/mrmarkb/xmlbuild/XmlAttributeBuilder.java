package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;

public class XmlAttributeBuilder implements XmlBuilder {
    private final String value;
    private final QName qName;

    public XmlAttributeBuilder(QName qName, String value) {
        this.qName = qName;
        this.value = value;
    }

    @Override
    public Attr build(Document document) {
        Attr attribute = document.createAttributeNS(qName.getNamespaceURI(), qName.getLocalPart());
        attribute.setPrefix(qName.getPrefix());
        attribute.setValue(value);
        return attribute;
    }
}
