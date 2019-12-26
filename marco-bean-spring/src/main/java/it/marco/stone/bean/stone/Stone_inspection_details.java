package it.marco.stone.bean.stone;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stone_inspection_details {

	@Id
	private Integer id;
	
	private String code;
	
	private String store_item_id;
	
	private String store_item_first_id;
	
	private String store_item_root_id;
	
	private String block_slab;
	
	private Integer block_number;
	
	private Integer cut_number;
	
	private Integer store_item_root_serial;
	
	private BigDecimal sell_dim1;

	private BigDecimal sell_dim2;

	private BigDecimal sell_dim3;
	
	private BigDecimal sell_mq;
	
	private String tsq_id;
	
	private String tsq_title;
	
	private String tsq_stone_type;
	
	private String tsf_id;
	
	private String tsf_title;

	private String tss_id;
	
	private String tss_title;
	
	private String sup_id;
	
	private String sup_title;
	
	private String prj_code;

	private String prj_title;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStore_item_id() {
		return store_item_id;
	}

	public void setStore_item_id(String store_item_id) {
		this.store_item_id = store_item_id;
	}

	public String getStore_item_first_id() {
		return store_item_first_id;
	}

	public void setStore_item_first_id(String store_item_first_id) {
		this.store_item_first_id = store_item_first_id;
	}

	public String getStore_item_root_id() {
		return store_item_root_id;
	}

	public void setStore_item_root_id(String store_item_root_id) {
		this.store_item_root_id = store_item_root_id;
	}

	public String getBlock_slab() {
		return block_slab;
	}

	public void setBlock_slab(String block_slab) {
		this.block_slab = block_slab;
	}

	public Integer getBlock_number() {
		return block_number;
	}

	public void setBlock_number(Integer block_number) {
		this.block_number = block_number;
	}

	public Integer getCut_number() {
		return cut_number;
	}

	public void setCut_number(Integer cut_number) {
		this.cut_number = cut_number;
	}

	public Integer getStore_item_root_serial() {
		return store_item_root_serial;
	}

	public void setStore_item_root_serial(Integer store_item_root_serial) {
		this.store_item_root_serial = store_item_root_serial;
	}

	public BigDecimal getSell_dim1() {
		return sell_dim1;
	}

	public void setSell_dim1(BigDecimal sell_dim1) {
		this.sell_dim1 = sell_dim1;
	}

	public BigDecimal getSell_dim2() {
		return sell_dim2;
	}

	public void setSell_dim2(BigDecimal sell_dim2) {
		this.sell_dim2 = sell_dim2;
	}

	public BigDecimal getSell_dim3() {
		return sell_dim3;
	}

	public void setSell_dim3(BigDecimal sell_dim3) {
		this.sell_dim3 = sell_dim3;
	}

	public BigDecimal getSell_mq() {
		return sell_mq;
	}

	public void setSell_mq(BigDecimal sell_mq) {
		this.sell_mq = sell_mq;
	}

	public String getTsq_id() {
		return tsq_id;
	}

	public void setTsq_id(String tsq_id) {
		this.tsq_id = tsq_id;
	}

	public String getTsq_title() {
		return tsq_title;
	}

	public void setTsq_title(String tsq_title) {
		this.tsq_title = tsq_title;
	}

	public String getTsq_stone_type() {
		return tsq_stone_type;
	}

	public void setTsq_stone_type(String tsq_stone_type) {
		this.tsq_stone_type = tsq_stone_type;
	}

	public String getTsf_id() {
		return tsf_id;
	}

	public void setTsf_id(String tsf_id) {
		this.tsf_id = tsf_id;
	}

	public String getTsf_title() {
		return tsf_title;
	}

	public void setTsf_title(String tsf_title) {
		this.tsf_title = tsf_title;
	}

	public String getTss_id() {
		return tss_id;
	}

	public void setTss_id(String tss_id) {
		this.tss_id = tss_id;
	}

	public String getTss_title() {
		return tss_title;
	}

	public void setTss_title(String tss_title) {
		this.tss_title = tss_title;
	}

	public String getSup_id() {
		return sup_id;
	}

	public void setSup_id(String sup_id) {
		this.sup_id = sup_id;
	}

	public String getSup_title() {
		return sup_title;
	}

	public void setSup_title(String sup_title) {
		this.sup_title = sup_title;
	}

	public String getPrj_code() {
		return prj_code;
	}

	public void setPrj_code(String prj_code) {
		this.prj_code = prj_code;
	}

	public String getPrj_title() {
		return prj_title;
	}

	public void setPrj_title(String prj_title) {
		this.prj_title = prj_title;
	}
	
}
