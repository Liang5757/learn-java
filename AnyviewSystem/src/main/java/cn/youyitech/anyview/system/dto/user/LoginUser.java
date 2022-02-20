package cn.youyitech.anyview.system.dto.user;

import java.io.Serializable;

public class LoginUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  */
	private Long id;

	/**  */
	private String uno;

	private Long deviceId;

	private Integer deviceStatus;

	/**  */
	private String phone;

	/**
	 * 2-体验用户 1-正常用户
	 */
	private Integer type;

	/**
	 * 0-被禁 1-正常
	 */
	private Integer status;

	/**
	 * 是否为主用户
	 */
	private Integer isMain;

	private String userName;

	private String sessionId;

	private Integer permission;

	private String imgIndoor;

	private String imgOutdoor;

	private String serviceName;

	private String servicePhone;

	private Long createTime;

	public String getImgIndoor() {
		return imgIndoor;
	}

	public void setImgIndoor(String imgIndoor) {
		this.imgIndoor = imgIndoor;
	}

	public String getImgOutdoor() {
		return imgOutdoor;
	}

	public void setImgOutdoor(String imgOutdoor) {
		this.imgOutdoor = imgOutdoor;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUno() {
		return uno;
	}

	public void setUno(String uno) {
		this.uno = uno;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
