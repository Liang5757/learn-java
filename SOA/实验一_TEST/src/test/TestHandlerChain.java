package test;

import static org.junit.Assert.*;

import java.net.URL;

import javax.xml.namespace.QName;

import org.junit.Test;

import service.IService;
import service.ServiceImplService;

public class TestHandlerChain {

    @Test
    public void test() throws Exception {
        URL url = new URL("http://localhost:8888/ms?wsdl");
        QName qname = new QName("http://Service/", "ServiceImplService");
        ServiceImplService serviceImpl = new ServiceImplService(url,qname);
        IService service = serviceImpl.getServiceImplPort();
        try {
            service.login("admin", "admin");
        } catch (Exception e) {
            System.out.println("出错了:"+e.getMessage());
        }
    }

}