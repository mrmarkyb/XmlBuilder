package uk.co.mrmarkyb.xmlbuild;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static javax.xml.XMLConstants.XMLNS_ATTRIBUTE;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static uk.co.mrmarkb.xmlbuild.NamespaceUriPrefixMapping.namespace;
import static uk.co.mrmarkb.xmlbuild.XmlBuilderFactory.*;
import static uk.co.mrmarkb.xmlbuild.XmlRenderer.render;
import static uk.co.mrmarkyb.xmlbuild.Namespaces.BA;
import static uk.co.mrmarkyb.xmlbuild.Namespaces.FU;

public class XmlDocumentBuilderTest {

    @Test
    public void buildsDocumentWithNoChildElements() throws Exception {
        Document document = document("root").build();
        assertThat(document.getDocumentElement(), allOf(hasName("root"), hasNoChildren()));
    }

    @Test
    public void buildsDocumentWithManyChildElements() throws Exception {
        Document document = document("root").with(
                element("foo"),
                element("bar"),
                element("baz")
        ).build();
        assertThat(document.getDocumentElement(), hasChildElementCountOf(3));
    }

    @Test
    public void rootElementHasADefaultNamespace() throws Exception {
        String defaultNamespaceName = "default.ns";
        Document document = document("root")
                .withDefaultNamespace(defaultNamespaceName)
                .build();
        assertThat(document.getDocumentElement(), hasDefaultNamespaceDeclarationOf(defaultNamespaceName));
    }

    @Test
    public void buildsDocumentWithRootElementInDefaultNamespaceOneNestedNodeInOtherNamespace() throws Exception {
        Document document = document("anElement")
                .withDefaultNamespace(FU)
                .with(namespace(BA, "ba"))
                .with(
                        element(namespace(BA, "ba"), "otherElement").with(
                                text("someText")
                        )
                ).build();

        assertThat(stringified(document), containsString("<ba:otherElement>"));
    }

    @Test
    public void buildsDocumentWithRootElementInDefaultNamespaceOneNestedNodeInDefaultNamespace() throws Exception {
        Document document = document("anElement")
                .withDefaultNamespace(FU)
                .with(
                        element("otherElement").with(
                                text("someText")
                        )
                ).build();

        assertThat(stringified(document), containsString("<otherElement>"));
    }

    @Test
    public void buildDocumentWithAttributesInRootElement() throws Exception {
        Document document = document("rootElement")
                .with(attribute("attr1", "value1"),
                        attribute("attr2", "value2")
                ).build();
        assertThat(document.getDocumentElement(), hasAttribute("attr1", "value1"));
        assertThat(document.getDocumentElement(), hasAttribute("attr2", "value2"));
    }

    @Test
    public void buildDocumentWithAttributesAndNamespaceInRootElement() throws Exception {
        Document document = document("rootElement")
                .withDefaultNamespace(BA)
                .with(attribute("attr1", "value1"),
                        attribute("attr2", "value2")
                ).build();
        assertThat(document.getDocumentElement(), hasDefaultNamespaceDeclarationOf(BA));
        assertThat(document.getDocumentElement(), hasAttribute("attr1", "value1"));
        assertThat(document.getDocumentElement(), hasAttribute("attr2", "value2"));
    }

    private Matcher<Element> hasAttribute(final String name, final String value) {
        return new TypeSafeMatcher<Element>() {
            @Override
            public boolean matchesSafely(Element element) {
                return element.hasAttribute(name) && element.getAttribute(name).equals(value);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("attribute named [%s] with value [%s]", name, value));
            }
        };
    }

    private String stringified(Document document) {
        return render(document).toString();
    }

    private static Matcher<Element> hasDefaultNamespaceDeclarationOf(final String defaultNamespaceName) {
        return new TypeSafeMatcher<Element>() {
            @Override
            public boolean matchesSafely(Element element) {
                return equalTo(defaultNamespaceName).matches(element.getAttribute(XMLNS_ATTRIBUTE));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("default namespace [%s]", defaultNamespaceName));
            }
        };
    }

    private static Matcher<Element> hasChildElementCountOf(final int expectedCount) {
        return new TypeSafeMatcher<Element>() {
            @Override
            public boolean matchesSafely(Element element) {
                return equalTo(expectedCount).matches(element.getChildNodes().getLength());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("expected child node count [%d]", expectedCount));
            }
        };
    }

    public static Matcher<Element> hasNoChildren() {
        return new TypeSafeMatcher<Element>() {
            @Override
            public boolean matchesSafely(Element element) {
                return (!element.hasChildNodes());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("no child nodes");
            }
        };
    }


    public static Matcher<Element> hasName(final String expectedElementName) {
        return new TypeSafeMatcher<Element>() {
            @Override
            public boolean matchesSafely(Element element) {
                return equalTo(expectedElementName).matches(element.getNodeName());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("element name [%s]", expectedElementName));
            }
        };
    }


}
