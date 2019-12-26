package it.marco.stone.bean.stone;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.marco.marco.utils.serializer.Base64ToByteDeserializer;
import it.marco.marco.utils.serializer.ByteToBase64Serializer;

@Entity
public class Stone_inspection_row {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stone_inspection_head_id")
    private Integer stoneInspectionHeadId;
	
	private Integer prj_activity_id;
	
	private Integer block_number;
	
	private Integer slab_number;
	
	private String supplier_code;
	
	private String um_row;
	
	private Integer pieces;

    private BigDecimal gross_dim1;

    private BigDecimal gross_dim2;

    private BigDecimal gross_dim3;

    private BigDecimal gross_mq;

    private BigDecimal gross_mc;

    private BigDecimal gross_tn;

    private BigDecimal gross_mq_tot;

    private BigDecimal gross_mc_tot;

    private BigDecimal gross_tn_tot;
	
	private String tab_stone_finish_id;
	
	private String tab_stone_quality_id;
	
	private String tab_stone_selection_id;
	
	private Integer tab_stone_inspection_quality_id;
	
	private Integer tab_stone_inspection_feature_id;
	
	private Integer tab_stone_inspection_shape_id;
	
	private BigDecimal cost_unit;
	
	private BigDecimal cost_estimated_unit;
	
	private BigDecimal cost_tot;
	
	private BigDecimal cost_estimated_tot;

    @Column(name = "sketch")
	@JsonSerialize(using = ByteToBase64Serializer.class)
	@JsonDeserialize(using = Base64ToByteDeserializer.class)
    private byte[] sketch;

    @Column(name = "picture")
	@JsonSerialize(using = ByteToBase64Serializer.class)
	@JsonDeserialize(using = Base64ToByteDeserializer.class)
    private byte[] picture;
	
    @Transient
	private List<Stone_inspection_image> images = new ArrayList<Stone_inspection_image>();
	
	private String ext_code_str;
	
	private Integer ext_code_int;
	
	private String generic_note;
	
	private String uuid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStoneInspectionHeadId() {
		return stoneInspectionHeadId;
	}

	public void setStoneInspectionHeadId(Integer stoneInspectionHeadId) {
		this.stoneInspectionHeadId = stoneInspectionHeadId;
	}

	public Integer getPrj_activity_id() {
		return prj_activity_id;
	}

	public void setPrj_activity_id(Integer prj_activity_id) {
		this.prj_activity_id = prj_activity_id;
	}

	public Integer getBlock_number() {
		return block_number;
	}

	public void setBlock_number(Integer block_number) {
		this.block_number = block_number;
	}

	public Integer getSlab_number() {
		return slab_number;
	}

	public void setSlab_number(Integer slab_number) {
		this.slab_number = slab_number;
	}

	public String getSupplier_code() {
		return supplier_code;
	}

	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}

	public String getUm_row() {
		return um_row;
	}

	public void setUm_row(String um_row) {
		this.um_row = um_row;
	}

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	public BigDecimal getGross_dim1() {
		return gross_dim1;
	}

	public void setGross_dim1(BigDecimal gross_dim1) {
		this.gross_dim1 = gross_dim1;
	}

	public BigDecimal getGross_dim2() {
		return gross_dim2;
	}

	public void setGross_dim2(BigDecimal gross_dim2) {
		this.gross_dim2 = gross_dim2;
	}

	public BigDecimal getGross_dim3() {
		return gross_dim3;
	}

	public void setGross_dim3(BigDecimal gross_dim3) {
		this.gross_dim3 = gross_dim3;
	}

	public BigDecimal getGross_mq() {
		return gross_mq;
	}

	public void setGross_mq(BigDecimal gross_mq) {
		this.gross_mq = gross_mq;
	}

	public BigDecimal getGross_mc() {
		return gross_mc;
	}

	public void setGross_mc(BigDecimal gross_mc) {
		this.gross_mc = gross_mc;
	}

	public BigDecimal getGross_tn() {
		return gross_tn;
	}

	public void setGross_tn(BigDecimal gross_tn) {
		this.gross_tn = gross_tn;
	}

	public BigDecimal getGross_mq_tot() {
		return gross_mq_tot;
	}

	public void setGross_mq_tot(BigDecimal gross_mq_tot) {
		this.gross_mq_tot = gross_mq_tot;
	}

	public BigDecimal getGross_mc_tot() {
		return gross_mc_tot;
	}

	public void setGross_mc_tot(BigDecimal gross_mc_tot) {
		this.gross_mc_tot = gross_mc_tot;
	}

	public BigDecimal getGross_tn_tot() {
		return gross_tn_tot;
	}

	public void setGross_tn_tot(BigDecimal gross_tn_tot) {
		this.gross_tn_tot = gross_tn_tot;
	}

	public String getTab_stone_finish_id() {
		return tab_stone_finish_id;
	}

	public void setTab_stone_finish_id(String tab_stone_finish_id) {
		this.tab_stone_finish_id = tab_stone_finish_id;
	}

	public String getTab_stone_quality_id() {
		return tab_stone_quality_id;
	}

	public void setTab_stone_quality_id(String tab_stone_quality_id) {
		this.tab_stone_quality_id = tab_stone_quality_id;
	}

	public String getTab_stone_selection_id() {
		return tab_stone_selection_id;
	}

	public void setTab_stone_selection_id(String tab_stone_selection_id) {
		this.tab_stone_selection_id = tab_stone_selection_id;
	}

	public Integer getTab_stone_inspection_quality_id() {
		return tab_stone_inspection_quality_id;
	}

	public void setTab_stone_inspection_quality_id(Integer tab_stone_inspection_quality_id) {
		this.tab_stone_inspection_quality_id = tab_stone_inspection_quality_id;
	}

	public Integer getTab_stone_inspection_feature_id() {
		return tab_stone_inspection_feature_id;
	}

	public void setTab_stone_inspection_feature_id(Integer tab_stone_inspection_feature_id) {
		this.tab_stone_inspection_feature_id = tab_stone_inspection_feature_id;
	}

	public Integer getTab_stone_inspection_shape_id() {
		return tab_stone_inspection_shape_id;
	}

	public void setTab_stone_inspection_shape_id(Integer tab_stone_inspection_shape_id) {
		this.tab_stone_inspection_shape_id = tab_stone_inspection_shape_id;
	}

	public BigDecimal getCost_unit() {
		return cost_unit;
	}

	public void setCost_unit(BigDecimal cost_unit) {
		this.cost_unit = cost_unit;
	}

	public BigDecimal getCost_estimated_unit() {
		return cost_estimated_unit;
	}

	public void setCost_estimated_unit(BigDecimal cost_estimated_unit) {
		this.cost_estimated_unit = cost_estimated_unit;
	}

	public BigDecimal getCost_tot() {
		return cost_tot;
	}

	public void setCost_tot(BigDecimal cost_tot) {
		this.cost_tot = cost_tot;
	}

	public BigDecimal getCost_estimated_tot() {
		return cost_estimated_tot;
	}

	public void setCost_estimated_tot(BigDecimal cost_estimated_tot) {
		this.cost_estimated_tot = cost_estimated_tot;
	}

	public String getExt_code_str() {
		return ext_code_str;
	}

	public void setExt_code_str(String ext_code_str) {
		this.ext_code_str = ext_code_str;
	}

	public Integer getExt_code_int() {
		return ext_code_int;
	}

	public void setExt_code_int(Integer ext_code_int) {
		this.ext_code_int = ext_code_int;
	}

	public String getGeneric_note() {
		return generic_note;
	}

	public void setGeneric_note(String generic_note) {
		this.generic_note = generic_note;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Stone_inspection_image> getImages() {
		return images;
	}

	public void setImages(List<Stone_inspection_image> images) {
		this.images = images;
	}
	
	public void resetImages() {
    	this.images.clear();
    }

	public void addImage(Stone_inspection_image image) {
    	images = new ArrayList<>();
    	this.images.add(image);
    }
}