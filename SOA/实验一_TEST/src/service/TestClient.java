package service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

public class TestClient {

    public static void main(String[] args) throws MalformedURLException {
        //创建访问wsdl服务地址的url
        URL url = new URL("http://localhost:8888/?wsdl");
        //通过Qname指明服务的具体信息
        QName sname = new QName("http://Service/", "ServiceImplService");
        ServiceImplService msis = new ServiceImplService(url,sname);
        IService ms = msis.getServiceImplPort();
        System.out.println(ms.minus(3,1));
    }

}