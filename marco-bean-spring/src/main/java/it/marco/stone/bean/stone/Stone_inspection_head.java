package it.marco.stone.bean.stone;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.marco.marco.utils.serializer.DateAndTimeDeserializer;
import it.marco.marco.utils.serializer.DateAndTimeSerializer;

@Entity
public class Stone_inspection_head {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private Integer stone_inspection_head_type_id;

    private Integer prog_year;
	
    @JsonDeserialize(using = DateAndTimeDeserializer.class)
    @JsonSerialize(using = DateAndTimeSerializer.class)
	private Date inspection_date;

    private String supplier_id;

    private String tab_stone_quality_id;
	
	private Integer stone_inspector_id;
	
	private Integer stone_applicant_id;
	
	private String coan_divisioni_id;
	
	private String delivery_mode_id;

    private String currency_id;
	
	private BigDecimal currency_rate;

    private String um_id;

    @Transient
    private Timestamp insert_time;
	
	private Integer stone_inspection_status_id;
	
	private String ext_code_str;
	
	private Integer ext_code_int;
	
	private Integer company_input_source_id;
	
	private String uuid;
	
	private Integer stone_inspection_images;
	
	private Integer count_pictures = 0;
	
	private Integer count_sketches = 0;

    @Transient
    @JsonProperty(value = "stone_inspection_row")
    private List<Stone_inspection_row> stone_inspection_row = new ArrayList<Stone_inspection_row>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStone_inspection_head_type_id() {
		return stone_inspection_head_type_id;
	}

	public void setStone_inspection_head_type_id(Integer stone_inspection_head_type_id) {
		this.stone_inspection_head_type_id = stone_inspection_head_type_id;
	}

	public Integer getProg_year() {
		return prog_year;
	}

	public void setProg_year(Integer prog_year) {
		this.prog_year = prog_year;
	}

	public Date getInspection_date() {
		return inspection_date;
	}

	public void setInspection_date(Date inspection_date) {
		this.inspection_date = inspection_date;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getTab_stone_quality_id() {
		return tab_stone_quality_id;
	}

	public void setTab_stone_quality_id(String tab_stone_quality_id) {
		this.tab_stone_quality_id = tab_stone_quality_id;
	}

	public Integer getStone_inspector_id() {
		return stone_inspector_id;
	}

	public void setStone_inspector_id(Integer stone_inspector_id) {
		this.stone_inspector_id = stone_inspector_id;
	}

	public Integer getStone_applicant_id() {
		return stone_applicant_id;
	}

	public void setStone_applicant_id(Integer stone_applicant_id) {
		this.stone_applicant_id = stone_applicant_id;
	}

	public String getCoan_divisioni_id() {
		return coan_divisioni_id;
	}

	public void setCoan_divisioni_id(String coan_divisioni_id) {
		this.coan_divisioni_id = coan_divisioni_id;
	}

	public String getDelivery_mode_id() {
		return delivery_mode_id;
	}

	public void setDelivery_mode_id(String delivery_mode_id) {
		this.delivery_mode_id = delivery_mode_id;
	}

	public String getCurrency_id() {
		return currency_id;
	}

	public void setCurrency_id(String currency_id) {
		this.currency_id = currency_id;
	}

	public BigDecimal getCurrency_rate() {
		return currency_rate;
	}

	public void setCurrency_rate(BigDecimal currency_rate) {
		this.currency_rate = currency_rate;
	}

	public String getUm_id() {
		return um_id;
	}

	public void setUm_id(String um_id) {
		this.um_id = um_id;
	}

	public Timestamp getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}

	public Integer getStone_inspection_status_id() {
		return stone_inspection_status_id;
	}

	public void setStone_inspection_status_id(Integer stone_inspection_status_id) {
		this.stone_inspection_status_id = stone_inspection_status_id;
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
	
	public Integer getCompany_input_source_id() {
		return company_input_source_id;
	}

	public List<Stone_inspection_row> getStone_inspection_row() {
		return stone_inspection_row;
	}

	public void setStone_inspection_row(List<Stone_inspection_row> stone_inspection_row) {
		this.stone_inspection_row = stone_inspection_row;
	}

	public void setCompany_input_source_id(Integer company_input_source_id) {
		this.company_input_source_id = company_input_source_id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void resetRows() {
    	this.stone_inspection_row.clear();
    }

	public void addRow(Stone_inspection_row row) {
    	this.stone_inspection_row.add(row);
    }

	public Integer getStone_inspection_images() {
		return stone_inspection_images;
	}

	public void setStone_inspection_images(Integer stone_inspection_images) {
		this.stone_inspection_images = stone_inspection_images;
	}

	public Integer getCount_pictures() {
		return count_pictures;
	}

	public void setCount_pictures(Integer count_pictures) {
		this.count_pictures = count_pictures;
	}

	public Integer getCount_sketches() {
		return count_sketches;
	}

	public void setCount_sketches(Integer count_sketches) {
		this.count_sketches = count_sketches;
	}
}