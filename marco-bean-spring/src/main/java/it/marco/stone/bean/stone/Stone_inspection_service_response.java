package it.marco.stone.bean.stone;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stone_inspection_service_response {

	@JsonProperty("TabStoneInspectionQuality")
	private List<Tab_stone_inspection_quality> tabStoneInspectionQualities;

	@JsonProperty("TabStoneInspectionFeature")
	private List<Tab_stone_inspection_feature> tabStoneInspectionFeatures;
	@JsonProperty("TabStoneInspectionShape")
	private List<Tab_stone_inspection_shape> tabStoneInspectionShapes;

	@JsonProperty("StoneInspectionApplicant")
	private List<Stone_inspection_applicant> stoneInspectionApplicants;
	@JsonProperty("StoneInspectionHeadType")
	private List<Stone_inspection_head_type> stoneInspectionHeadTypes;
	@JsonProperty("StoneInspectionInspector")
	private List<Stone_inspection_inspector> stoneInspectionInspector;
	@JsonProperty("TabDeliveryMode")
	private List<Tab_delivery_mode> tabDeliveryModes;
	
	@JsonProperty("TabStoneQuality")
	private List<Tab_stone_quality> tabStoneQualities;
	
	@JsonProperty("Supplier")
	private List<Partner_account> suppliers;
	@JsonProperty("TabUm")
	private List<Tab_um> tabUms;
	@JsonProperty("TabExchange")
	private List<Tab_exchange> tabExchanges;
	@JsonProperty("TabStoneType")
	private List<Tab_stone_type> tabStoneTypes;
	
	@JsonProperty("TabStoneFinish")
	private List<Tab_stone_finish> tabStoneFinishes;
	@JsonProperty("PrjActivity")
	private List<Prj_activity> prjActivities;
	@JsonProperty("CoanDivisioni")
	private List<Coan_divisioni> coanDivisioni;
	
	@JsonProperty("TabStoneSelection")
	private List<Tab_stone_selection> tabStoneSelections;

	public void setTabStoneInspectionQualities(List<Tab_stone_inspection_quality> tabStoneInspectionQualities) {
		this.tabStoneInspectionQualities = tabStoneInspectionQualities;
	}

	public void setStoneInspectionApplicants(List<Stone_inspection_applicant> stoneInspectionApplicants) {
		this.stoneInspectionApplicants = stoneInspectionApplicants;
	}

	public void setStoneInspectionHeadTypes(List<Stone_inspection_head_type> stoneInspectionHeadTypes) {
		this.stoneInspectionHeadTypes = stoneInspectionHeadTypes;
	}

	public void setTabStoneInspectionFeatures(List<Tab_stone_inspection_feature> tabStoneInspectionFeatures) {
		this.tabStoneInspectionFeatures = tabStoneInspectionFeatures;
	}

	public void setTabStoneInspectionShapes(List<Tab_stone_inspection_shape> tabStoneInspectionShapes) {
		this.tabStoneInspectionShapes = tabStoneInspectionShapes;
	}

	public void setStoneInspectionInspector(List<Stone_inspection_inspector> stoneInspectionInspector) {
		this.stoneInspectionInspector = stoneInspectionInspector;
	}

	public void setTabDeliveryModes(List<Tab_delivery_mode> tabDeliveryModes) {
		this.tabDeliveryModes = tabDeliveryModes;
	}
	
	public void setSuppliers(List<Partner_account> suppliers) {
		this.suppliers = suppliers;
	}

	public void setTabUms(List<Tab_um> tabUms) {
		this.tabUms = tabUms;
	}

	public void setTabStoneQualities(List<Tab_stone_quality> tabStoneQualities) {
		this.tabStoneQualities = tabStoneQualities;
	}

	public void setTabExchanges(List<Tab_exchange> tabExchanges) {
		this.tabExchanges = tabExchanges;
	}

	public void setTabStoneTypes(List<Tab_stone_type> tabStoneTypes) {
		this.tabStoneTypes = tabStoneTypes;
	}
	
	public void setTabStoneFinishes(List<Tab_stone_finish> tabStoneFinishes) {
		this.tabStoneFinishes = tabStoneFinishes;
	}
	
	public void setPrjActivities(List<Prj_activity> prjActivities) {
		this.prjActivities = prjActivities;
	}
	
	public void setCoanDivisioni(List<Coan_divisioni> coanDivisioni) {
		this.coanDivisioni = coanDivisioni;
	}
	
	public void setTabStoneSelections(List<Tab_stone_selection> tabStoneSelections) {
		this.tabStoneSelections = tabStoneSelections;
	}
}