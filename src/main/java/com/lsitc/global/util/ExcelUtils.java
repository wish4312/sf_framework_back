package com.lsitc.global.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.document.AbstractXlsView;

@Component
public class ExcelUtils extends AbstractXlsView {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    //HSSFWorkbook 대신 SXSSFWorkbook리턴
    @Override
	protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
    	//return new HSSFWorkbook();
		return new SXSSFWorkbook();
	}

    //파일 생성
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook wb, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cell cell = null;

        Sheet sheet = wb.createSheet("Sheet1");
        sheet.setDefaultColumnWidth(12);
        sheet.setDefaultRowHeight((short) 450);
        // 대상
        List<Map<String, Object>> targeList = (List<Map<String, Object>>) model.get("targeList");
        // header값
        Map<String, String> headerMap = (Map<String, String>) model.get("headerMap");
        // fileName
        String title = model.get("fileName").toString();
        // FIXME Locale수정
        // fileName_일시
        SimpleDateFormat frm = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
        title = frm.format(Calendar.getInstance().getTime()) + "_" + title.substring(title.indexOf('_') + 1);


        Map<String, CellStyle> sytleMap = createStyleMap(wb);

        //headers
        CellStyle stringSytle = wb.createCellStyle();
        stringSytle.setAlignment(HorizontalAlignment.LEFT);
        stringSytle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle intSytle = wb.createCellStyle();
        intSytle.setAlignment(HorizontalAlignment.RIGHT);
        intSytle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle doubleStyle = wb.createCellStyle();
        doubleStyle.setAlignment(HorizontalAlignment.RIGHT);
        doubleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 헤더 정보 셋팅
        Set<Entry<String, String>> entrySet = headerMap.entrySet();
        Iterator<Entry<String, String>> iterator = entrySet.iterator();
        int aliasCnt = 0;
        List<String> alias = new ArrayList<>();
        while (iterator.hasNext()) {
            Entry<String, String> next = iterator.next();
            Cell headCell = getCell(sheet, 0, aliasCnt++);
            CellStyle headCellStyle = wb.createCellStyle();

            headCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Font headFont = wb.createFont();
            headFont.setBold(true);

            headCellStyle.setFont(headFont);
            headCell.setCellStyle(headCellStyle);
            setText(headCell, next.getValue());
            alias.add(next.getKey());
        }

        //엑셀 생성
        for (int i = 0; i < targeList.size(); i++) {
            Map<String, Object> category = targeList.get(i);
            for (int j = 0; j < aliasCnt; j++) {
                cell = getCell(sheet, i + 1, j);
                if (category.get(alias.get(j)) != null) {
                    //setText(cell, category.get(alias.get(j)).toString());
                	setValue(cell, category.get(alias.get(j)), sytleMap);
                }
            }
        }
	}

	//특정 sheet에 특정행의 특저 컬럼을 반환하는데, 없으면 생성해서 리턴한다.
    protected Cell getCell(Sheet sheet, int row, int col) {
        Row sheetRow = sheet.getRow(row);
        if (sheetRow == null) {
            sheetRow = sheet.createRow(row);
        }
        Cell cell = sheetRow.getCell(col);
        if (cell == null) {
            cell = sheetRow.createCell(col);
        }
        return cell;
    }

    //cell에 문자열(text) 셋팅
    protected void setText(Cell cell, String text) {
        cell.setCellValue(text);
    }
    
    //형태에 맞게 cell에 값을 셋팅하고 스타일을 입힌다.
    protected void setValue(Cell cell, Object value, Map<String, CellStyle> sytleMap) {
    	if( value instanceof String ) {
    		//문자
            cell.setCellValue(value.toString());
            cell.setCellStyle(sytleMap.get("stringStyle"));
    	} else if ( value instanceof Date )  {
    		//날짜    		
            // FIXME Locale수정
            SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
            cell.setCellValue(frm.format(value));
            //동작안함..ㅠ
//            cell.setCellStyle(sytleMap.get("dateStyle"));
//            cell.setCellValue((Date)value );
    	} else if ( value instanceof BigDecimal ) {
    		try {
        		//정수
                cell.setCellStyle(sytleMap.get("intStyle"));
        		cell.setCellValue(((BigDecimal)value).intValueExact());
    		} catch (ArithmeticException ae) {
    			//정수아님..
                cell.setCellStyle(sytleMap.get("doubleStyle"));
        		cell.setCellValue(((BigDecimal)value).doubleValue());
    		}
    	} else if ( value instanceof Integer ) {
			//정수
	        cell.setCellStyle(sytleMap.get("intStyle"));
			cell.setCellValue((int)value);
    	}
    } 
    
    //createStyleMap
    protected Map<String, CellStyle> createStyleMap(Workbook wb) {
    	Map<String, CellStyle> styleMap = new HashMap<>();
        //headers
        CellStyle stringStyle = wb.createCellStyle();
        stringStyle.setAlignment(HorizontalAlignment.LEFT);
        stringStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        styleMap.put("stringStyle", stringStyle);

        CellStyle intStyle = wb.createCellStyle();
        intStyle.setAlignment(HorizontalAlignment.RIGHT);
        intStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        intStyle.setDataFormat(wb.createDataFormat().getFormat("#,##0"));      
        styleMap.put("intStyle", intStyle);
        
        CellStyle doubleStyle = wb.createCellStyle();
        doubleStyle.setAlignment(HorizontalAlignment.RIGHT);
        doubleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        styleMap.put("doubleStyle", doubleStyle);
        
        CellStyle dateStyle = wb.createCellStyle();
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
//        dateStyle.setDataFormat(wb.createDataFormat().getFormat("m/d/yy h:mm"));        
//        styleMap.put("dateStyle", doubleStyle);
        
		return styleMap;
	}
    
    //excel을 stream으로 받아서 Map으로 리턴함..
	public List<Map<String, Object>> readExcel(MultipartFile file, List<String> columList, int firstRowIndex) {
		//
		InputStream fis = null;
		//결과
		List<Map<String, Object>> resultList = new ArrayList<>();
		try {
			fis = file.getInputStream();
			//대상 workbook
			Workbook wb = WorkbookFactory.create(fis);
			//함수처리객체
			FormulaEvaluator formulaEval = wb.getCreationHelper().createFormulaEvaluator();
			
			//첫번째 sheet만
			Sheet sheet = wb.getSheetAt(0);
			//존재하는 rows
			int rowLen = sheet.getPhysicalNumberOfRows();
			
			for( int rowIdx=firstRowIndex-1; rowIdx<rowLen; rowIdx++ ) {
				//행을 담을 Map
				Map<String, Object> rowMap = new HashMap<>();
				
				//각 행
				Row row = sheet.getRow(rowIdx);
				
				//각 행에 존재하는 cols
				int cellLen = row.getPhysicalNumberOfCells();
				
				for ( int cellIdx=0; cellIdx<cellLen; cellIdx++ ) {
					//각 컬럼(셀)
					Cell cell = row.getCell(cellIdx);
					switch (cell.getCellType()) {
						case BOOLEAN:
							rowMap.put(columList.get(cellIdx), cell.getBooleanCellValue());
							break;
						case FORMULA:
							CellValue evaluateValue = formulaEval.evaluate(cell);
							switch (evaluateValue.getCellType()) {
								case BOOLEAN:
									rowMap.put(columList.get(cellIdx), evaluateValue.getBooleanValue());
									break;
								case NUMERIC:
									rowMap.put(columList.get(cellIdx), evaluateValue.getNumberValue());
									break;
								case STRING:
									rowMap.put(columList.get(cellIdx), evaluateValue.getStringValue());
									break;
								default:
									//판단 불가한 것들은 null처리
									rowMap.put(columList.get(cellIdx), null);
									break;
							}
							break;
						case NUMERIC:
							rowMap.put(columList.get(cellIdx), cell.getNumericCellValue());
							break;
						case STRING:
							rowMap.put(columList.get(cellIdx), cell.getStringCellValue());
							break;
						default:
							//read불가능해 null로 반환
							rowMap.put(columList.get(cellIdx), null);
							break;
					}
				}
				
				//결과List에 담는다.
				resultList.add(rowMap);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//List<Map>형태의 결과 반환
		return resultList;
	}
}
