package com.lnet.frame.web.navigate;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by simin on 2016-07-19.
 */
public class SiteMapFactoryBean implements FactoryBean<SiteMap> {

    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public SiteMap getObject() throws Exception {
        return SiteMapBuilder.getInstance().fromXmlFile(filePath);
    }

    @Override
    public Class<?> getObjectType() {
        return SiteMap.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
