package com.simulate.data;

import java.io.Serializable;

/**
 * @ClassName: XHSession
 * @Description: Session实体类
 * @author: bioFish
 * @date: 2019年4月1日 下午5:52:45
 */
public class XHSession implements Serializable {
	private String username;				//用户名
	private String token;					//Token
	private String deviceID;				//设备ID
	private String deviceResource;			//设备资源
    private String loginResource;           //登录资源
	private String deviceHost;				//设备IP
	private String deviceModel;				//设备型号
	private String devicePlatforms;			//设备平台版本


    private String pushAlias;				//推送别名
	private String pushToken;				//推送Token
	private String active;					//有效状态
	private long changeDate;				//修改时间
    private boolean notice;                 //新消息通知
    private boolean noticedetail;           //通知显示发送人信息
    private boolean voipnotice;             //语音聊天通知
    private String voipToken;              //voiptoken
    private String description;
    private String clientVersion;
    private String lang;
    private String authenticationText;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getClientVersion() {
        return clientVersion;
    }
    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
    public String getVoipToken() {
        return voipToken;
    }
    public void setVoipToken(String voipToken) {
        this.voipToken = voipToken;
    }
    public long getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(long changeDate) {
		this.changeDate = changeDate;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getDeviceResource() {
		return deviceResource;
	}
	public void setDeviceResource(String deviceResource) {
		this.deviceResource = deviceResource;
	}
	public String getDeviceHost() {
		return deviceHost;
	}
	public void setDeviceHost(String deviceHost) {
		this.deviceHost = deviceHost;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDevicePlatforms() {
		return devicePlatforms;
	}
	public void setDevicePlatforms(String devicePlatforms) {
		this.devicePlatforms = devicePlatforms;
	}
	public String getPushAlias() {
		return pushAlias;
	}
	public void setPushAlias(String pushAlias) {
		this.pushAlias = pushAlias;
	}
	public String getPushToken() {
		return pushToken;
	}
	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

    public String getLoginResource() {
        return loginResource;
    }

    public void setLoginResource(String loginResource) {
        this.loginResource = loginResource;
    }
    public boolean isNotice() {
        return notice;
    }
    public void setNotice(boolean notice) {
        this.notice = notice;
    }
    public boolean isNoticedetail() {
        return noticedetail;
    }
    public void setNoticedetail(boolean noticedetail) {
        this.noticedetail = noticedetail;
    }
    public boolean isVoipnotice() {
        return voipnotice;
    }
    public void setVoipnotice(boolean voipnotice) {
        this.voipnotice = voipnotice;
    }

    public XHSession() {}

    public XHSession(String username, String token, String deviceID, String deviceResource, String loginResource,
                     String deviceHost, String deviceModel, String devicePlatforms, String pushAlias, String pushToken,
                     String active, long changeDate, int notice, int voipnotice, int noticedetail) {
        this.username = username;
        this.token = token;
        this.deviceID = deviceID;
        this.deviceResource = deviceResource;
        this.loginResource = loginResource;
        this.deviceHost = deviceHost;
        this.deviceModel = deviceModel;
        this.devicePlatforms = devicePlatforms;
        this.pushAlias = pushAlias;
        this.pushToken = pushToken;
        this.active = active;
        this.changeDate = changeDate;
        this.notice = notice == 1? true:false;
        this.voipnotice = voipnotice == 1? true:false;
        this.noticedetail = noticedetail == 1? true:false;
    }

    public String getAuthenticationText() {
        return authenticationText;
    }

    public void setAuthenticationText(String authenticationText) {
        this.authenticationText = authenticationText;
    }
}
