package Service;

import javax.xml.ws.Endpoint;

public class Service {

    public static void main(String[] args) {
        String address = "http://localhost:8888/?wsdl";
        System.out.println(address);
        Endpoint.publish(address, new ServiceImpl());
    }

}
