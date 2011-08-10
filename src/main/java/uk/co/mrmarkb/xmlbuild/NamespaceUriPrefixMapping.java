package uk.co.mrmarkb.xmlbuild;

import static uk.co.mrmarkb.xmlbuild.utils.YetAnotherStringUtils.isBlank;

public class NamespaceUriPrefixMapping {

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
