package com.lnet.frame.web.navigate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SiteMap implements Serializable {

    private final SiteMapNode rootNode;

    @JsonIgnore
    private transient final List<SiteMapNode> allNodes;

    public SiteMap(SiteMapNode rootNode) {
        this.rootNode = rootNode;
        this.allNodes = new ArrayList<>();
        this.init();
    }

    public SiteMapNode getRootNode() {
        return this.rootNode;
    }

    private void init() {
        Objects.requireNonNull(this.rootNode);
        if (StringUtils.isBlank(this.rootNode.getId())) {
            this.rootNode.setId("root");
        }
        this.allNodes.addAll(initAndGetAll(this.rootNode));
    }

    private static List<SiteMapNode> initAndGetAll(final SiteMapNode node) {
        final List<SiteMapNode> allNodes = new ArrayList<>();
        allNodes.add(node);

        if (node.hasChildren()) {
            List<SiteMapNode> children = node.getChildren();
            for (int i = 0; i < children.size(); i++) {
                final SiteMapNode child = children.get(i);
                child.setParent(node);

                // 设置ID
                if (StringUtils.isBlank(node.getId())) {
                    String childId = String.format("%s-%s", node.getId(), i);
                    child.setId(childId);
                }

                allNodes.addAll(initAndGetAll(child));
            }
        }

        return allNodes;
    }

    public SiteMapNode findNodeById(final String id) {
        return this.allNodes.stream().filter(node -> id.equalsIgnoreCase(node.getId())).findFirst().orElse(SiteMapNode.EMPTY);
    }

    public SiteMapNode findNodeByUrl(final String url) {
        return this.allNodes.stream().filter(node -> node.isMatchUrl(url)).findFirst().orElse(SiteMapNode.EMPTY);
    }

    public boolean isMatchOn(SiteMapNode node, final String targetUrl){
        SiteMapNode target = findNodeByUrl(targetUrl);
        return target.getParents().contains(node);
    }


}
