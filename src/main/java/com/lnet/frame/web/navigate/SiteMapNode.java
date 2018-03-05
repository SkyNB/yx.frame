package com.lnet.frame.web.navigate;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "siteMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class SiteMapNode implements Serializable {

    private static final String PERM_SPLITTER = ",";

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String title;

    @XmlAttribute
    private String description;

    @XmlAttribute
    private String url;

    @XmlAttribute
    private String target;

    @XmlAttribute
    private String css;

    @XmlAttribute
    private boolean hidden;

    @XmlAttribute
    private String requiredPermissions;

    @XmlElement(name = "siteMap")
    private final List<SiteMapNode> children;

    @JsonIgnore
    private transient final List<String> permissions;

    @JsonIgnore
    private transient SiteMapNode parent;

    public SiteMapNode() {
        this.children = new ArrayList<>();
        this.permissions = new ArrayList<>();
    }

    public SiteMapNode(String id, String title, String description, String url, String target, String requiredPermissions) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.target = target;
        this.setRequiredPermissions(requiredPermissions);
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public boolean isHidden() {
        return hidden;
    }

    @JsonIgnore
    @XmlTransient
    @Transient
    public boolean isVisible() {
        return !hidden;
    }


    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getRequiredPermissions() {
        return requiredPermissions;
    }

    /**
     * 权限，多个权限使用逗号分隔
     *
     * @param requiredPermissions
     */
    public void setRequiredPermissions(String requiredPermissions) {
        this.requiredPermissions = requiredPermissions;
        this.permissions.clear();

        if (!StringUtils.isBlank(requiredPermissions)) {
            final List<String> toAddPermissions = Arrays.asList(StringUtils.split(requiredPermissions, PERM_SPLITTER));
            this.permissions.addAll(toAddPermissions);
        }
    }

    @JsonIgnore
    @XmlTransient
    @Transient
    public List<String> getPermissions() {
        return this.permissions;
    }

    public static List<String> getPermissionsIncludeChildren(final SiteMapNode siteMapNode) {
        final List<String> allPermissions = new ArrayList<>();
        allPermissions.addAll(siteMapNode.getPermissions());

        if (siteMapNode.hasChildren()) {
            for (SiteMapNode child : siteMapNode.getChildren()) {
                allPermissions.addAll(getPermissionsIncludeChildren(child));
            }
        }
        return allPermissions;
    }

    /**
     * 判断是否有所有权限
     *
     * @param requiredPermissions
     * @return
     */
    public boolean isPermitted(final String requiredPermissions) {
        final List<String> toCheckPermissions = Arrays.asList(StringUtils.split(requiredPermissions.toLowerCase(), PERM_SPLITTER));
        if (toCheckPermissions.isEmpty()) return true; // 传入权限为空时返回YES

        return this.permissions.containsAll(toCheckPermissions);
    }

    /**
     * 判断是否有任何一项权限
     *
     * @param requiredPermissions
     * @return
     */
    public boolean isPermittedAny(final String requiredPermissions) {
        final List<String> toCheckPermissions = Arrays.asList(StringUtils.split(requiredPermissions.toLowerCase(), PERM_SPLITTER));
        if (toCheckPermissions.isEmpty()) return true; // 传入权限为空时返回YES

        return this.permissions.stream().anyMatch(toCheckPermissions::contains);
    }


    /**
     * 判断是否有任何一项权限，包括子项
     *
     * @param requiredPermissions
     * @return
     */
    public boolean isPermittedAnyIncludedChildren(final String requiredPermissions) {
        final List<String> toCheckPermissions = Arrays.asList(StringUtils.split(requiredPermissions.toLowerCase(), PERM_SPLITTER));
        if (toCheckPermissions.isEmpty()) return true; // 传入权限为空时返回YES

        final List<String> allPermissions = getPermissionsIncludeChildren(this);
        return allPermissions.stream().anyMatch(toCheckPermissions::contains);
    }

    public List<SiteMapNode> getAuthorizedChildren(final String requiredPermissions) {
        return children.stream()
                .filter(node -> node.isVisible() && node.isPermittedAnyIncludedChildren(requiredPermissions))
                .collect(Collectors.toList());
    }


    public List<SiteMapNode> getChildren() {
        return children;
    }

    @JsonIgnore
    @XmlTransient
    @Transient
    public List<SiteMapNode> getVisibleChildren() {
        return children.stream().filter(SiteMapNode::isVisible).collect(Collectors.toList());
    }


    @JsonIgnore
    @XmlTransient
    @Transient
    public SiteMapNode getParent() {
        return parent;
    }

    public void setParent(SiteMapNode parent) {
        if (this != parent) this.parent = parent;
    }

    public void addChildren(List<SiteMapNode> siteMapNodes) {
        children.addAll(siteMapNodes);
    }

    public void addChildren(SiteMapNode siteMapNode) {
        children.add(siteMapNode);
    }

    /**
     * 获取上级列表
     *
     * @return
     */
    @JsonIgnore
    @XmlTransient
    @Transient
    public List<SiteMapNode> getParents() {
        List<SiteMapNode> parents = new ArrayList<>();
        SiteMapNode cursor = this;
        do {
            parents.add(0, cursor);
            cursor = cursor.getParent();
        }
        while (cursor != null);

        return parents;
    }

    /**
     * 检查url是否匹配，"/" match "", "abc/" match "abc"
     *
     * @param url
     * @return
     */
    public boolean isMatchUrl(final String url) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(url)) return false;
        String path1 = StringUtils.stripEnd(url, "/");
        String path2 = StringUtils.stripEnd(this.getUrl(), "/");
        return path1.equalsIgnoreCase(path2);
    }

    public final static SiteMapNode EMPTY = new SiteMapNode("", "", null, null, null, null);


}
