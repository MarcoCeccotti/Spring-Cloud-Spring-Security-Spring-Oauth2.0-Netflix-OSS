package it.marco.marco.bean.mail;

import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Mail_attachment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "mail_server_id")
	private int mailServerId;
	
	@Column
	private String attachment_file_name;
	
	@Column
	@Transient
	private String data;
	
	@Column
	private byte[] file_data;
	
	@Column
	private String data_path;
	
	public Mail_attachment() {}
	
	public Mail_attachment(String attachment_file_name, String data_path, String file_data) {
		this.attachment_file_name = attachment_file_name;
		this.data_path = data_path;
		this.setData(file_data);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMailServerId() {
		return mailServerId;
	}

	public void setMailServerId(int mailServerId) {
		this.mailServerId = mailServerId;
	}

	public String getAttachment_file_name() {
		return attachment_file_name;
	}

	public void setAttachment_file_name(String attachment_file_name) {
		this.attachment_file_name = attachment_file_name;
	}

	public String getData_path() {
		return data_path;
	}

	public void setData_path(String data_path) {
		this.data_path = data_path;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public byte[] getFile_data() {
		this.file_data = javax.xml.bind.DatatypeConverter.parseBase64Binary(this.data);
		return file_data;
	}

	public void setFile_data(byte[] file_data) {
		this.data = Base64.getEncoder().encodeToString(file_data);
		this.file_data = file_data;
	}
}
