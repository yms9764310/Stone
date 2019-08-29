package com.jc.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
@Data
public class OrgNodeResponse {

    /**id*/
    private String id;
    /**名称*/
    private String name;
    /**链接-暂时未用到*/
    private String href;
    /**是否展开*/
    private boolean spread;
    /**树路径*/
    private String path;
    /**子节点*/
    private List<OrgNodeResponse> children;

    /**
     * 构造函数
     * */
    public OrgNodeResponse(){
        this.children = new LinkedList<OrgNodeResponse>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<OrgNodeResponse> getChildren() {
        return children;
    }

    public void setChildren(List<OrgNodeResponse> children) {
        this.children = children;
    }
}
