package it.marco.stone.bean.stone;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Prj_activity {

	@Id
	private Integer id;
	
	private String title;
	
	private String description;
	
	private Integer space_id;
	
	private Integer level_up;
	
	private String customer_id;
	
	private Integer level_order;
	
	private Date date_delivery;
	
	private Date date_ini;
	
	private Date date_end;
	
	private Integer status_id;
	
	private BigDecimal quotation_hh;
	
	private BigDecimal quotation_value;
	
	private Boolean accountability;
	
	private Integer acc_order_row_id;
	
	private Integer acc_offer_row_id;
	
	private Integer acc_role_id;
	
	private Boolean deleted;
    
    private Boolean date_delivery_expected;
    
    private String item_id;
    
    private String um_item;
    
    private String um_row;
    
    private BigDecimal quantity;
    
    private BigDecimal um_coeff;
    
    private String title_complete;
    
    private BigDecimal per_completition;
    
	private BigDecimal per_invoice;
	
	private BigDecimal worked_hh;
	
	private BigDecimal invoiced_value;
	
	private Boolean customer_open;
	
	private String level_path;
	
	private String level_row;
	
	private String level_title;
	
	private Boolean value_quotation_based;
	
	private Integer priority_id;
	
	private Integer type_id;
	
	private String username;
	
	private Timestamp timestp;
	
	private BigDecimal worked_cost;
	
	private BigDecimal worked_reimbursements;
	
	private Date date_delivery_stimated;
	
	private Integer prj_category_id;
	
	private String prj_maincontractor;
	
	private String code;
	
	private Integer last_prj_task_id;
	
	private Integer last_prj_update_id;
	
	private Integer[] level_paths;
	
	private Integer sell_rdo_head_id;
	
	private Integer crm_ticket_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSpace_id() {
		return space_id;
	}

	public void setSpace_id(Integer space_id) {
		this.space_id = space_id;
	}

	public Integer getLevel_up() {
		return level_up;
	}

	public void setLevel_up(Integer level_up) {
		this.level_up = level_up;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public Integer getLevel_order() {
		return level_order;
	}

	public void setLevel_order(Integer level_order) {
		this.level_order = level_order;
	}

	public Date getDate_delivery() {
		return date_delivery;
	}

	public void setDate_delivery(Date date_delivery) {
		this.date_delivery = date_delivery;
	}

	public Date getDate_ini() {
		return date_ini;
	}

	public void setDate_ini(Date date_ini) {
		this.date_ini = date_ini;
	}

	public Date getDate_end() {
		return date_end;
	}

	public void setDate_end(Date date_end) {
		this.date_end = date_end;
	}

	public Integer getStatus_id() {
		return status_id;
	}

	public void setStatus_id(Integer status_id) {
		this.status_id = status_id;
	}

	public BigDecimal getQuotation_hh() {
		return quotation_hh;
	}

	public void setQuotation_hh(BigDecimal quotation_hh) {
		this.quotation_hh = quotation_hh;
	}

	public BigDecimal getQuotation_value() {
		return quotation_value;
	}

	public void setQuotation_value(BigDecimal quotation_value) {
		this.quotation_value = quotation_value;
	}

	public Boolean getAccountability() {
		return accountability;
	}

	public void setAccountability(Boolean accountability) {
		this.accountability = accountability;
	}

	public Integer getAcc_order_row_id() {
		return acc_order_row_id;
	}

	public void setAcc_order_row_id(Integer acc_order_row_id) {
		this.acc_order_row_id = acc_order_row_id;
	}

	public Integer getAcc_offer_row_id() {
		return acc_offer_row_id;
	}

	public void setAcc_offer_row_id(Integer acc_offer_row_id) {
		this.acc_offer_row_id = acc_offer_row_id;
	}

	public Integer getAcc_role_id() {
		return acc_role_id;
	}

	public void setAcc_role_id(Integer acc_role_id) {
		this.acc_role_id = acc_role_id;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getDate_delivery_expected() {
		return date_delivery_expected;
	}

	public void setDate_delivery_expected(Boolean date_delivery_expected) {
		this.date_delivery_expected = date_delivery_expected;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getUm_item() {
		return um_item;
	}

	public void setUm_item(String um_item) {
		this.um_item = um_item;
	}

	public String getUm_row() {
		return um_row;
	}

	public void setUm_row(String um_row) {
		this.um_row = um_row;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUm_coeff() {
		return um_coeff;
	}

	public void setUm_coeff(BigDecimal um_coeff) {
		this.um_coeff = um_coeff;
	}

	public String getTitle_complete() {
		return title_complete;
	}

	public void setTitle_complete(String title_complete) {
		this.title_complete = title_complete;
	}

	public BigDecimal getPer_completition() {
		return per_completition;
	}

	public void setPer_completition(BigDecimal per_completition) {
		this.per_completition = per_completition;
	}

	public BigDecimal getPer_invoice() {
		return per_invoice;
	}

	public void setPer_invoice(BigDecimal per_invoice) {
		this.per_invoice = per_invoice;
	}

	public BigDecimal getWorked_hh() {
		return worked_hh;
	}

	public void setWorked_hh(BigDecimal worked_hh) {
		this.worked_hh = worked_hh;
	}

	public BigDecimal getInvoiced_value() {
		return invoiced_value;
	}

	public void setInvoiced_value(BigDecimal invoiced_value) {
		this.invoiced_value = invoiced_value;
	}

	public Boolean getCustomer_open() {
		return customer_open;
	}

	public void setCustomer_open(Boolean customer_open) {
		this.customer_open = customer_open;
	}

	public String getLevel_path() {
		return level_path;
	}

	public void setLevel_path(String level_path) {
		this.level_path = level_path;
	}

	public String getLevel_row() {
		return level_row;
	}

	public void setLevel_row(String level_row) {
		this.level_row = level_row;
	}

	public String getLevel_title() {
		return level_title;
	}

	public void setLevel_title(String level_title) {
		this.level_title = level_title;
	}

	public Boolean getValue_quotation_based() {
		return value_quotation_based;
	}

	public void setValue_quotation_based(Boolean value_quotation_based) {
		this.value_quotation_based = value_quotation_based;
	}

	public Integer getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(Integer priority_id) {
		this.priority_id = priority_id;
	}

	public Integer getType_id() {
		return type_id;
	}

	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getTimestp() {
		return timestp;
	}

	public void setTimestp(Timestamp timestp) {
		this.timestp = timestp;
	}

	public BigDecimal getWorked_cost() {
		return worked_cost;
	}

	public void setWorked_cost(BigDecimal worked_cost) {
		this.worked_cost = worked_cost;
	}

	public BigDecimal getWorked_reimbursements() {
		return worked_reimbursements;
	}

	public void setWorked_reimbursements(BigDecimal worked_reimbursements) {
		this.worked_reimbursements = worked_reimbursements;
	}

	public Date getDate_delivery_stimated() {
		return date_delivery_stimated;
	}

	public void setDate_delivery_stimated(Date date_delivery_stimated) {
		this.date_delivery_stimated = date_delivery_stimated;
	}

	public Integer getPrj_category_id() {
		return prj_category_id;
	}

	public void setPrj_category_id(Integer prj_category_id) {
		this.prj_category_id = prj_category_id;
	}

	public String getPrj_maincontractor() {
		return prj_maincontractor;
	}

	public void setPrj_maincontractor(String prj_maincontractor) {
		this.prj_maincontractor = prj_maincontractor;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getLast_prj_task_id() {
		return last_prj_task_id;
	}

	public void setLast_prj_task_id(Integer last_prj_task_id) {
		this.last_prj_task_id = last_prj_task_id;
	}

	public Integer getLast_prj_update_id() {
		return last_prj_update_id;
	}

	public void setLast_prj_update_id(Integer last_prj_update_id) {
		this.last_prj_update_id = last_prj_update_id;
	}

	public Integer[] getLevel_paths() {
		return level_paths;
	}

	public void setLevel_paths(Integer[] level_paths) {
		this.level_paths = level_paths;
	}

	public Integer getSell_rdo_head_id() {
		return sell_rdo_head_id;
	}

	public void setSell_rdo_head_id(Integer sell_rdo_head_id) {
		this.sell_rdo_head_id = sell_rdo_head_id;
	}

	public Integer getCrm_ticket_id() {
		return crm_ticket_id;
	}

	public void setCrm_ticket_id(Integer crm_ticket_id) {
		this.crm_ticket_id = crm_ticket_id;
	}
}
