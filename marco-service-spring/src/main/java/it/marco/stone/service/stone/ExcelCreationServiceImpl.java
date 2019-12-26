package it.marco.stone.service.stone;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.stereotype.Service;

import it.marco.marco.bean.mail.Mail_attachment;
import it.marco.marco.cloud.configuration.properties.StoneProperties;
import it.marco.marco.service.log.LogService;
import it.marco.stone.bean.stone.Partner_account;
import it.marco.stone.bean.stone.Prj_activity;
import it.marco.stone.bean.stone.Stone_inspection_head;
import it.marco.stone.bean.stone.Stone_inspection_row;
import it.marco.stone.bean.stone.Tab_stone_quality;
import it.marco.stone.dao.stone.Coan_divisioni_DAO;
import it.marco.stone.dao.stone.Prj_activity_DAO;
import it.marco.stone.dao.stone.Stone_inspection_applicant_DAO;
import it.marco.stone.dao.stone.Stone_inspection_inspector_DAO;
import it.marco.stone.dao.stone.Supplier_DAO;
import it.marco.stone.dao.stone.Tab_delivery_mode_DAO;
import it.marco.stone.dao.stone.Tab_exchange_DAO;
import it.marco.stone.dao.stone.Tab_stone_finish_DAO;
import it.marco.stone.dao.stone.Tab_stone_inspection_feature_DAO;
import it.marco.stone.dao.stone.Tab_stone_inspection_quality_DAO;
import it.marco.stone.dao.stone.Tab_stone_inspection_shape_DAO;
import it.marco.stone.dao.stone.Tab_stone_quality_DAO;
import it.marco.stone.dao.stone.Tab_stone_selection_DAO;
import it.marco.stone.dao.stone.Tab_um_DAO;

@Service
public class ExcelCreationServiceImpl implements ExcelCreationService {

	@Inject
	private LogService logService;
	
	@Inject
	private Coan_divisioni_DAO coan_divisioni_DAO;
	@Inject
	private Prj_activity_DAO prj_activity_DAO;
	@Inject
	private Supplier_DAO supplier_DAO;
	
	@Inject
	private Stone_inspection_applicant_DAO stone_inspection_applicant_DAO;
	@Inject
	private Stone_inspection_inspector_DAO stone_inspection_inspector_DAO;
	
	@Inject
	private Tab_exchange_DAO tab_exchange_DAO;
	@Inject
	private Tab_delivery_mode_DAO tab_delivery_mode_DAO;
	@Inject
	private Tab_stone_finish_DAO tab_stone_finish_DAO;
	@Inject
	private Tab_stone_quality_DAO tab_stone_quality_DAO;
	@Inject
	private Tab_stone_selection_DAO tab_stone_selection_DAO;
	@Inject
	private Tab_um_DAO tab_um_DAO;
	
	@Inject
	private Tab_stone_inspection_feature_DAO tab_stone_inspection_feature_DAO;
	@Inject
	private Tab_stone_inspection_quality_DAO tab_stone_inspection_quality_DAO;
	@Inject
	private Tab_stone_inspection_shape_DAO tab_stone_inspection_shape_DAO;
	
	@Inject
	private StoneProperties stoneProps;
    
	@Override
    public Mail_attachment createExcelFiles(List<Stone_inspection_head> sIHs, String file_name, String jsonInspection) throws Exception {

		String method_name = new Throwable().getStackTrace()[0].getMethodName();
		
		logService.saveStoneLog(this.getClass().getName(), method_name, "", jsonInspection);
		
    	// keeps 100 rows in memory, exceeding rows will be flushed to disk
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		workbook.setCompressTempFiles(true);
		
	    CreationHelper createHelper = workbook.getCreationHelper();
	    
	    // define the styles
	    CellStyle dateStyle = workbook.createCellStyle();
	    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
	    
	    CellStyle timestStyle = workbook.createCellStyle();
	    timestStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy hh.mm.ss"));
	    
	    CellStyle colorStyle = workbook.createCellStyle();
	    XSSFFont font = (XSSFFont) workbook.createFont();
	    font.setColor(new XSSFColor(Color.RED));
	    colorStyle.setFont(font);
	    
	    CellStyle boldAndCenterStyle = workbook.createCellStyle();
	    XSSFFont bold_font = (XSSFFont) workbook.createFont();
	    bold_font.setBold(true);
	    boldAndCenterStyle.setFont(bold_font);
	    boldAndCenterStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    
	    //New Sheet
	    SXSSFSheet sheet1 = (SXSSFSheet) workbook.createSheet("myData");
	    sheet1.setRandomAccessWindowSize(100);
		
		int rowNum = 0;
		
		Row row;
		
		// I dati delle testate
		for (Stone_inspection_head sIH : sIHs) {
			
			row = sheet1.createRow(rowNum++);
			String label_collaudo_lastra_blocco = sIH.getStone_inspection_head_type_id() == 2 ? "COLLAUDO BLOCCO" : "COLLAUDO LASTRE";
			row.createCell(0).setCellValue(new XSSFRichTextString(label_collaudo_lastra_blocco));
			row.createCell(1).setCellValue(sIH.getId());
			
			row = sheet1.createRow(rowNum++);
			row = sheet1.createRow(rowNum++);
			int cellNum = 0;
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Data"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Cod.Fornit"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Desc.Forn."));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Materiale"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Collaudatore"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Richiedente"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Divisione"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Consegna"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Valuta"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Um."));
			
			this.autoSizeColumns(sheet1, row, boldAndCenterStyle, true);
			
			row = sheet1.createRow(rowNum++);
			cellNum = 0;
			row.createCell(cellNum++).setCellValue(new Date(sIH.getInspection_date().getTime()));
			row.getCell(cellNum - 1).setCellStyle(dateStyle);
			row.createCell(cellNum++).setCellValue(sIH.getSupplier_id());
			Partner_account supplier = supplier_DAO.checkSupplier(sIH.getSupplier_id());
			row.createCell(cellNum++).setCellValue(supplier != null ? supplier.getCust_supp_account_name() : "");
			Tab_stone_quality tab_stone_quality = tab_stone_quality_DAO.findById(sIH.getTab_stone_quality_id()).get();
			row.createCell(cellNum++).setCellValue(tab_stone_quality.getId() + " " + tab_stone_quality.getTitle());
			row.createCell(cellNum++).setCellValue(stone_inspection_inspector_DAO.findById(sIH.getStone_inspector_id()).get().getTitle());
			row.createCell(cellNum++).setCellValue(stone_inspection_applicant_DAO.findById(sIH.getStone_applicant_id()).get().getTitle());
			row.createCell(cellNum++).setCellValue(coan_divisioni_DAO.findById(sIH.getCoan_divisioni_id()).get().getTitle());
			row.createCell(cellNum++).setCellValue(tab_delivery_mode_DAO.findById(sIH.getDelivery_mode_id()).get().getTitle());
			row.createCell(cellNum++).setCellValue(tab_exchange_DAO.findById(sIH.getCurrency_id()).get().getTitle());
			row.createCell(cellNum++).setCellValue(tab_um_DAO.findById(sIH.getUm_id()).get().getTitle());
			
			this.autoSizeColumns(sheet1, row, boldAndCenterStyle, false);

			row = sheet1.createRow(rowNum++);
			
			String label_block_slab = sIH.getStone_inspection_head_type_id() == 2 ? "Blocco" : "Lastra";
			String label_mq_mc = sIH.getStone_inspection_head_type_id() == 2 ? "Mc" : "Mq";
			String label_tons_ql = sIH.getStone_inspection_head_type_id() == 2 ? "Tons" : "Ql";
			String label_spess_prof = sIH.getStone_inspection_head_type_id() == 2 ? "Prof." : "Spessore";
			
			cellNum = 0;
			
			row = sheet1.createRow(rowNum++);
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString(label_block_slab));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("N° Origine"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Scelta"));
			if(sIH.getStone_inspection_head_type_id() != 2) { // lastra
				row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Quantita"));
			}
			if(sIH.getStone_inspection_head_type_id() == 2) { // blocco
				row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Tipo Qualita"));
				row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Caratteristica"));
				row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Forma"));
			}
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Lung."));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Altezza"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString(label_spess_prof));
			if(sIH.getStone_inspection_head_type_id() != 2) { // lastra
				row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Finitura"));
			}
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString(label_mq_mc));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString(label_tons_ql));
			if(sIH.getStone_inspection_head_type_id() == 2) { // blocco
				row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Valutazione"));
			}
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Costo"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Commessa"));
			row.createCell(cellNum++).setCellValue(new XSSFRichTextString("Note"));
			
			this.autoSizeColumns(sheet1, row, boldAndCenterStyle, true);
			
			// I dati delle righe
			for (Stone_inspection_row sIR : sIH.getStone_inspection_row()) {
				
				row = sheet1.createRow(rowNum++);
				cellNum = 0;
				row.createCell(cellNum++).setCellValue(sIR.getBlock_number());
				row.getCell(cellNum - 1).setCellStyle(boldAndCenterStyle);
				row.createCell(cellNum++).setCellValue(sIR.getSupplier_code());
				row.createCell(cellNum++).setCellValue(tab_stone_selection_DAO.findById(sIR.getTab_stone_selection_id()).get().getTitle());
				if(sIH.getStone_inspection_head_type_id() != 2) { // lastra
					row.createCell(cellNum++).setCellValue(sIR.getPieces());
				}
				if(sIH.getStone_inspection_head_type_id() == 2) { // blocco
					row.createCell(cellNum++).setCellValue(tab_stone_inspection_quality_DAO.findById(sIR.getTab_stone_inspection_quality_id()).get().getTitle());
					row.createCell(cellNum++).setCellValue(tab_stone_inspection_feature_DAO.findById(sIR.getTab_stone_inspection_feature_id()).get().getTitle());
					row.createCell(cellNum++).setCellValue(tab_stone_inspection_shape_DAO.findById(sIR.getTab_stone_inspection_shape_id()).get().getTitle());
				}
				row.createCell(cellNum++).setCellValue(sIR.getGross_dim1().doubleValue());
				row.createCell(cellNum++).setCellValue(sIR.getGross_dim2().doubleValue());
				row.createCell(cellNum++).setCellValue(sIR.getGross_dim3().doubleValue());
				if(sIH.getStone_inspection_head_type_id() != 2) { // lastra
					row.createCell(cellNum++).setCellValue(tab_stone_finish_DAO.findById(sIR.getTab_stone_finish_id()).get().getTitle());
				}
				double value_mq_mc = sIR.getGross_mq_tot().doubleValue();
				if(sIH.getStone_inspection_head_type_id() == 2) { // blocco -> Tonnellate o Metri Cubi (MC)
					value_mq_mc = sIR.getGross_mc_tot().doubleValue();
				}
				row.createCell(cellNum++).setCellValue(value_mq_mc);
				row.createCell(cellNum++).setCellValue(sIR.getGross_tn_tot().doubleValue());
				if(sIH.getStone_inspection_head_type_id() == 2) { // blocco
					row.createCell(cellNum++).setCellValue(sIR.getCost_estimated_tot().doubleValue());
				}
				row.createCell(cellNum++).setCellValue(sIR.getCost_tot().doubleValue());
				Prj_activity prj_activity = prj_activity_DAO.findById(sIR.getPrj_activity_id()).get();
				String prj_activity_title = prj_activity.getTitle();
				String prj_activity_code = prj_activity.getCode();
				String sub = prj_activity_title.substring(prj_activity_code.length() + 3, prj_activity_title.length());
				row.createCell(cellNum++).setCellValue(sub);
				row.createCell(cellNum++).setCellValue(sIR.getGeneric_note());
				
				this.autoSizeColumns(sheet1, row, boldAndCenterStyle, false);
			}

			row = sheet1.createRow(rowNum++);
		}
		
	    sheet1.getPrintSetup().setLandscape(true);
	    sheet1.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
	    
		String file_nameXlsx = file_name + ".xlsx";
		String path = stoneProps.getFile_path_documents() + file_nameXlsx;
	    
		// converts the workbook into a byte array
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    workbook.write(bos);
	    byte[] wb_bytes = bos.toByteArray();
	    bos.close();
	    
	    // saves excel on file system
		FileOutputStream fos = new FileOutputStream(stoneProps.getFile_path_base() + path);
        fos.write(wb_bytes);
        fos.close();
		
		//String encoded = Base64.getEncoder().encodeToString(wb_bytes);
		
		return new Mail_attachment(file_nameXlsx, path, "");
    }
    
	/**
	 * Applies a certain style to all the cell of a specific row
	 * @param sheet the SXSSFSheet sheet
	 * @param row the specific row
	 * @param style the style to apply
	 * @param apply_style if false doesn't apply the style to the cells, applies it otherwise
	 */
    private void autoSizeColumns(SXSSFSheet sheet, Row row, CellStyle style, boolean apply_style) {
    	
    	Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int columnIndex = cell.getColumnIndex();
            sheet.autoSizeColumn(columnIndex);
            
            if (apply_style) {
            	cell.setCellStyle(style);
            }
        }
    }
}
