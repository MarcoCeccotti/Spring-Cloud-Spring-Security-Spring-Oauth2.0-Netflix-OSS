package it.marco.stone.bean.stone;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;

import it.marco.marco.utils.serializer.Base64ToByteDeserializer;
import it.marco.marco.utils.serializer.ByteToBase64Serializer;

@Entity
@Table(name = "image")
public class Stone_inspection_image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Transient
	@Expose(serialize = false)
	@JsonSerialize(using = ByteToBase64Serializer.class)
	@JsonDeserialize(using = Base64ToByteDeserializer.class)
    private transient byte[] sketch;
	
	@Column
	@Transient
	@Expose(serialize = false)
	@JsonSerialize(using = ByteToBase64Serializer.class)
	@JsonDeserialize(using = Base64ToByteDeserializer.class)
    private transient byte[] picture;
	
	@Column
	private int stone_inspection_row_id;
	
	@Column
	@JsonProperty(value = "stone_inspection_row_uuid")
	private String stoneInspectionRowUuid;

	@Column
	@JsonProperty(value = "stone_inspection_head_uuid")
	private String stoneInspectionHeadUuid;
	
	@Column
	private int order_id;
	
	@Column
	@JsonProperty(value = "insert_time")
    private Timestamp insertTime = new Timestamp(System.currentTimeMillis());
	
	@Column
	private String picture_path;
	
	@Column
	private String sketch_path;

	@Column
	@JsonProperty(value = "overlay_path")
	private String overlayPath;
	
	@Column
	private String uuid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStone_inspection_row_id() {
		return stone_inspection_row_id;
	}

	public void setStone_inspection_row_id(int stone_inspection_row_id) {
		this.stone_inspection_row_id = stone_inspection_row_id;
	}

	public String getStoneInspectionRowUuid() {
		return stoneInspectionRowUuid;
	}

	public void setStoneInspectionRowUuid(String stoneInspectionRowUuid) {
		this.stoneInspectionRowUuid = stoneInspectionRowUuid;
	}

	public String getStoneInspectionHeadUuid() {
		return stoneInspectionHeadUuid;
	}

	public void setStoneInspectionHeadUuid(String stoneInspectionHeadUuid) {
		this.stoneInspectionHeadUuid = stoneInspectionHeadUuid;
	}

	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	public byte[] getSketch() {
		return sketch;
	}
	
	public byte[] getPicture() {
		return picture;
	}

	public String getPicture_path() {
		return picture_path;
	}

	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}

	public String getSketch_path() {
		return sketch_path;
	}

	public void setSketch_path(String sketch_path) {
		this.sketch_path = sketch_path;
	}

	public String getOverlayPath() {
		return overlayPath;
	}

	public void setOverlayPath(String overlayPath) {
		this.overlayPath = overlayPath;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
