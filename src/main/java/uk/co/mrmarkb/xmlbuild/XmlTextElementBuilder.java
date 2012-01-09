package uk.co.mrmarkb.xmlbuild;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;

import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.element;
import static uk.co.mrmarkb.xmlbuild.utils.YetAnotherStringUtils.isBlank;

public class XmlTextElementBuilder implements XmlStandaloneNodeBuilder {

    private final QName qName;
    private final String value;

    public XmlTextElementBuilder(QName qName, String value) {
        this.qName = qName;
        this.value = value;
    }

    @Override
    public Node build(Document document) {
        return new XmlElementBuilder(qName).with(new XmlTextBuilder(value)).build(document);
    }

    public boolean isEmpty() {
        return isBlank(value);
    }

    public XmlStandaloneNodeBuilder comment() {
        return new XmlCommentBuilder(String.format("Optional element [%s] not present", qName.getLocalPart()));
    }
}
