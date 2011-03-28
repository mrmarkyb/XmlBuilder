package uk.co.mrmarkyb.xmlbuild;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static uk.co.mrmarkb.xmlbuild.XmlRenderer.render;


public class Matchers {
    public static boolean safeEquals(Object lhs, Object rhs) {
        if (lhs == null) {
            return lhs == rhs;
        }
        return lhs.equals(rhs);
    }

    public static Matcher<Node> sameNodeAs(final Node lhs) {
        return new TypeSafeMatcher<Node>() {
            private Node rhs;
            @Override
            public boolean matchesSafely(Node rhs) {
                this.rhs = rhs;
                return nodesAreSame(lhs, rhs);
            }

            public void describeTo(Description description) {
                description.appendText("expected:" +
                        render(lhs).withXmlHeader(false).toString() +
                        "but got:" +
                        render(rhs).withXmlHeader(false).toString());
            }
        };
    }

    private static boolean nodesAreSame(Node lhs, Node rhs) {
        NodeList lhsNodes = lhs.getChildNodes();
        NodeList rhsNodes = rhs.getChildNodes();
        if (lhsNodes.getLength() != rhsNodes.getLength()) {
            return false;
        }
        for (int i = 0; i < lhsNodes.getLength(); i++) {
            if (!nodesAreSame(lhsNodes.item(i), rhsNodes.item(i))) {
                return false;
            }
        }
        return safeEquals(lhs.getNamespaceURI(), rhs.getNamespaceURI()) &&
                safeEquals(lhs.getTextContent(), rhs.getTextContent()) &&
                safeEquals(lhs.getNodeValue(), rhs.getNodeValue()) &&
                safeEquals(lhs.getNodeName(), rhs.getNodeName()) &&
                safeEquals(lhs.getNodeType(), rhs.getNodeType());
    }
}
