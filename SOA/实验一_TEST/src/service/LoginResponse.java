
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>loginResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="loginResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="loginUser" type="{http://Service/}user" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loginResponse", propOrder = {
    "loginUser"
})
public class LoginResponse {

    protected User loginUser;

    /**
     * ��ȡloginUser���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getLoginUser() {
        return loginUser;
    }

    /**
     * ����loginUser���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setLoginUser(User value) {
        this.loginUser = value;
    }

}
