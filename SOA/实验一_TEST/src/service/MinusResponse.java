
package service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>minusResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="minusResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="minusResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "minusResponse", propOrder = {
    "minusResult"
})
public class MinusResponse {

    protected int minusResult;

    /**
     * ��ȡminusResult���Ե�ֵ��
     * 
     */
    public int getMinusResult() {
        return minusResult;
    }

    /**
     * ����minusResult���Ե�ֵ��
     * 
     */
    public void setMinusResult(int value) {
        this.minusResult = value;
    }

}
