package Service;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {
    /**
     * 客户端与服务端都会先执行本方法处理消息，再发送消息
     */
    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        //在发送soap消息的时候，加入头信息
        try {
            //判断消息是发送还是接收：true 往外推送  false 接收数据
            Boolean outbound = (Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
            if(outbound) {
                SOAPMessage message = context.getMessage();
                //判断message中是否存在header
                SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();
                if(header==null) {
                    header = envelope.addHeader();
                }
                String ns = "http://soap.hqh.com/";
                QName qName = new QName(ns, "license", "nn");
                header.addHeaderElement(qName).setValue("abc123");
            }
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 出现异常本方法执行
     */
    @Override
    public boolean handleFault(SOAPMessageContext context) {
        System.out.println("error!");
        return false;
    }

    @Override
    public void close(MessageContext context) {
        // TODO Auto-generated method stub
    }

    @Override
    public Set<QName> getHeaders() {
        // TODO Auto-generated method stub
        return null;
    }

}
