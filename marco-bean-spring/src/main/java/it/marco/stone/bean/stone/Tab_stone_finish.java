package it.marco.stone.bean.stone;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tab_stone_finish {

	@Id
	private String id;
	
	private String title;
	
	private Boolean isfinal;
	
	private Integer prd_machinetool_id_m;
	
	private Integer prd_machinetool_id_g;
	
	private Integer sort_by;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsfinal() {
		return isfinal;
	}

	public void setIsfinal(Boolean isfinal) {
		this.isfinal = isfinal;
	}

	public Integer getPrd_machinetool_id_m() {
		return prd_machinetool_id_m;
	}

	public void setPrd_machinetool_id_m(Integer prd_machinetool_id_m) {
		this.prd_machinetool_id_m = prd_machinetool_id_m;
	}

	public Integer getPrd_machinetool_id_g() {
		return prd_machinetool_id_g;
	}

	public void setPrd_machinetool_id_g(Integer prd_machinetool_id_g) {
		this.prd_machinetool_id_g = prd_machinetool_id_g;
	}

	public Integer getSort_by() {
		return sort_by;
	}

	public void setSort_by(Integer sort_by) {
		this.sort_by = sort_by;
	}
}