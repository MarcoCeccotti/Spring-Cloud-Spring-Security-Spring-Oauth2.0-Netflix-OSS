package it.marco.marco.cloud.session;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import it.marco.marco.cloud.service.http.HttpService;
import it.marco.marco.utils.utilities.HeaderUtils;

@Component
public class Session_infos {

	private String username;
	
	private String access_token;
	
	private String session;
	
	private String company_id;
	
	private String client_ip;
	private String remote_host;
	
	private String user_agent;
	
	private Timestamp time_st;
	
	private HttpServletRequest request;
	
	@Inject
	private HttpService httpService;
	
	/**
	 * Sets the value on its fields based on the current request.
	 * @param request the current HttpServletRequest request
	 */
	public void setSessionFields(HttpServletRequest request) {

		this.request = request;
		this.access_token = this.httpService.checkAccessTokenOnRequest(request);
		
		this.username = this.httpService.getUsernameFromRequest(request);
		
		this.session = this.request.getSession().getId();
		this.company_id = (this.request.getHeader(HeaderUtils.COMPANY_ID)) != null ? this.request.getHeader(HeaderUtils.COMPANY_ID) : "";
		
		this.client_ip = this.httpService.getClientIP(request);
		this.remote_host = this.request.getRemoteHost();
		
		this.user_agent = this.request.getHeader(HeaderUtils.USER_AGENT);
		
		this.time_st = new Timestamp(System.currentTimeMillis());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	public String getRemote_host() {
		return remote_host;
	}

	public void setRemote_host(String remote_host) {
		this.remote_host = remote_host;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public Timestamp getTime_st() {
		return time_st;
	}

	public void setTime_st(Timestamp time_st) {
		this.time_st = time_st;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
