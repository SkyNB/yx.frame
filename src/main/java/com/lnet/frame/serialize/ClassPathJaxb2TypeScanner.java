package com.lnet.frame.serialize;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ClassPathJaxb2TypeScanner {

    private static final String RESOURCE_PATTERN = "/**/*.class";

    private static final TypeFilter[] JAXB2_TYPE_FILTERS = new TypeFilter[]{
            new AnnotationTypeFilter(XmlRootElement.class, false),
            new AnnotationTypeFilter(XmlType.class, false),
            new AnnotationTypeFilter(XmlSeeAlso.class, false),
            new AnnotationTypeFilter(XmlEnum.class, false),
            new AnnotationTypeFilter(XmlRegistry.class, false)};


    private final ResourcePatternResolver resourcePatternResolver;

    private final String[] packagesToScan;


    ClassPathJaxb2TypeScanner(ClassLoader classLoader, String... packagesToScan) {
        Assert.notEmpty(packagesToScan, "'packagesToScan' must not be empty");
        this.resourcePatternResolver = new PathMatchingResourcePatternResolver(classLoader);
        this.packagesToScan = packagesToScan;
    }

    Class<?>[] scanPackages() {
        try {
            List<Class<?>> jaxb2Classes = new ArrayList<>();
            for (String packageToScan : this.packagesToScan) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(packageToScan) + RESOURCE_PATTERN;
                Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
                for (Resource resource : resources) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    if (isJaxb2Class(metadataReader, metadataReaderFactory)) {
                        String className = metadataReader.getClassMetadata().getClassName();
                        Class<?> jaxb2AnnotatedClass = this.resourcePatternResolver.getClassLoader().loadClass(className);
                        jaxb2Classes.add(jaxb2AnnotatedClass);
                    }
                }
            }
            return jaxb2Classes.toArray(new Class<?>[jaxb2Classes.size()]);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to scan classpath for unlisted classes", ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Failed to load annotated classes from classpath", ex);
        }
    }

    private boolean isJaxb2Class(MetadataReader reader, MetadataReaderFactory factory) throws IOException {
        for (TypeFilter filter : JAXB2_TYPE_FILTERS) {
            if (filter.match(reader, factory) && !reader.getClassMetadata().isInterface()) {
                return true;
            }
        }
        return false;
    }

}
