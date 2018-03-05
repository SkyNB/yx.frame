package com.lnet.frame.web.navigate;

import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Objects;

public final class SiteMapBuilder implements Serializable {

    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext context = JAXBContext.newInstance(SiteMapNode.class);
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private SiteMapBuilder() {
    }

    public SiteMap fromXmlFile(String filePath) throws FileNotFoundException, JAXBException {
        Objects.requireNonNull(unmarshaller);

        SiteMapNode rootNode = (SiteMapNode) unmarshaller.unmarshal(ResourceUtils.getURL(filePath));
        return new SiteMap(rootNode);
    }

    public static SiteMapBuilder getInstance (){
        return Holder.INSTANCE;
    }

    private static class Holder{
        private final static SiteMapBuilder INSTANCE = new SiteMapBuilder();
    }



}
