package com.lnet.frame.serialize;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class Jaxb2Serializer implements Serializer, BeanClassLoaderAware, InitializingBean {

    private String[] packagesToScan;
    private volatile JAXBContext jaxbContext;
    private final Object jaxbContextMonitor = new Object();
    private ClassLoader beanClassLoader;

    public void setPackagesToScan(String... packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    public String[] getPackagesToScan() {
        return this.packagesToScan;
    }

    public JAXBContext getJaxbContext() {
        if (this.jaxbContext != null) {
            return this.jaxbContext;
        }
        synchronized (this.jaxbContextMonitor) {
            if (this.jaxbContext == null) {
                try {
                    this.jaxbContext = createJaxbContextFromPackages();
                } catch (JAXBException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return this.jaxbContext;
        }
    }

    private JAXBContext createJaxbContextFromPackages() throws JAXBException {

        ClassPathJaxb2TypeScanner scanner = new ClassPathJaxb2TypeScanner(this.beanClassLoader, this.packagesToScan);
        Class<?>[] jaxb2Classes = scanner.scanPackages();
        return JAXBContext.newInstance(jaxb2Classes);
    }


    @Override
    public byte[] serialize(Object source) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            serialize(source, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void serialize(Object source, OutputStream outputStream) {
        try {
            Marshaller marshaller = getJaxbContext().createMarshaller();
            marshaller.marshal(source, outputStream);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object deserialize(InputStream inputStream) {
        try {
            Unmarshaller unmarshaller = getJaxbContext().createUnmarshaller();
            return unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(InputStream inputStream, Class<T> clazz) {
        return clazz.cast(deserialize(inputStream));
    }

    @Override
    public Object deserialize(byte[] source) {
        try (InputStream inputStream = new ByteArrayInputStream(source)) {
            return deserialize(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] source, Class<T> clazz) {
        try (InputStream inputStream = new ByteArrayInputStream(source)) {
            return deserialize(inputStream, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        getJaxbContext();
    }
}
