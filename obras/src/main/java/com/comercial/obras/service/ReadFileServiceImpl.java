package com.comercial.obras.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.comercial.obras.entity.Obras;
import com.comercial.obras.repository.ReadFileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
@Transactional
public class ReadFileServiceImpl implements ReadFileService {

	@Autowired
	private ReadFileRepository readFileRepository;

	@Override
	public List<Obras> findAll() {
		
		return (List<Obras>) readFileRepository.findAll();
	}

	@Override
	public boolean saveDataFromUploadfile(MultipartFile file) {
		
		boolean isFlag= false;
		
		String extension= FilenameUtils.getExtension(file.getOriginalFilename());
		if (extension.equalsIgnoreCase("json")) {			
			isFlag = readDataFromJson(file);			
		}else if (extension.equalsIgnoreCase("csv")) {
			isFlag = readDataFromCsv(file);			
		}else if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
			isFlag = readDataFromExcel(file);
		}		
		return isFlag;
	}


	private boolean readDataFromExcel(MultipartFile file) {
	
		Workbook workbook = getWorkBook(file);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		rows.next();
		
		while (rows.hasNext()) {
			Row row = rows.next();		
			Obras obras = new Obras();
			
			if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
				obras.setCiudadObr(row.getCell(0).getStringCellValue());				
			}
			
			if (row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING) {
				obras.setNombreObr(row.getCell(1).getStringCellValue());				
			}
			
			if (row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING) {				
				 obras.setZonaObr(row.getCell(4).getStringCellValue());
				
			}
			if (row.getCell(5).getCellType() == Cell.CELL_TYPE_STRING) {
				obras.setSectorObr(row.getCell(5).getStringCellValue());				
			}
			
			if (row.getCell(32).getCellType() == Cell.CELL_TYPE_STRING) {
				obras.setConstructoraObr(row.getCell(32).getStringCellValue());		
			}
		
			if (row.getCell(16).getCellType() == Cell.CELL_TYPE_STRING) {
				obras.setStatusObr(row.getCell(16).getStringCellValue());		
			}
			
			obras.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
			readFileRepository.save(obras);
		}		
		return true;
	}

	private Workbook getWorkBook(MultipartFile file) {
		Workbook workbook = null;
		String extension= FilenameUtils.getExtension(file.getOriginalFilename());
		try {
			
			if (extension.equalsIgnoreCase("xlsx")) {				
				workbook = new XSSFWorkbook(file.getInputStream());				
			}else if (extension.equalsIgnoreCase("xls")) {				
				workbook = new HSSFWorkbook(file.getInputStream());					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return workbook;
	}

	private boolean readDataFromCsv(MultipartFile file) {
		
		try {
			
			 InputStreamReader reader = new InputStreamReader(file.getInputStream());
			 CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
			 List<String[]> rows = csvReader.readAll();
			 for (String[] row : rows) {
				readFileRepository.save(new Obras(row[0], row[1], row[4], row[5], row[32], row[16],null,FilenameUtils.getExtension(file.getOriginalFilename())));
			}
			
			 return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean readDataFromJson(MultipartFile file) {
		 try {
			 InputStream inputStream = file.getInputStream();
			 ObjectMapper mapper = new ObjectMapper();
			 List<Obras> obras = Arrays.asList(mapper.readValue(inputStream, Obras[].class));
			 
			 if (obras!=null && obras.size()>0) {				 
				 for (Obras obra : obras) {					 
					 obra.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
					 readFileRepository.save(obra);					 
				}
				
			}
			return true;
				
		} catch (Exception e) {
			return false;
		}
	
	}

	@Override
	public List<Obras> listarCiudad() {
		return (List<Obras>) readFileRepository.listarCiudad();
	}
	
	
	@Override
	public Optional<Obras> findByObraId(Long idObr) {
		return readFileRepository.findById(idObr);
	}
	
	
	@Override
	public int save(Obras obra) {
		int res=0;
		Obras usu=readFileRepository.save(obra);
		
		if (!usu.equals(null)) {
			res=1;
		}
		return res;
	}
	
	@Override
	public Page<Obras> getAll(Pageable pageable) {
		return readFileRepository.findAll(pageable);
	}
	
	public Page<Obras> buscarDatos(Pageable pageable, String ciudad,String zona,String sector,String estado) {
		return readFileRepository.buscarDatos(pageable, ciudad,zona,sector,estado);
	}
	
	
	public Page<Obras> buscarByCiudad(Pageable pageable, String ciudad,String zona,String sector,String estado) {
		return readFileRepository.buscarByCiudad(pageable, ciudad,zona,sector,estado);
	}

}
