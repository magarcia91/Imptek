package com.imptek.bitacora.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.imptek.bitacora.dto.FiltroFecha;
import com.imptek.bitacora.entity.Cliente;
import com.imptek.bitacora.entity.Factura;
import com.imptek.bitacora.entity.Pago;
import com.imptek.bitacora.entity.ProntoPago;
import com.imptek.bitacora.repository.FacturaRepository;
import com.imptek.bitacora.service.ClienteService;
import com.imptek.bitacora.service.FacturaService;
import com.imptek.bitacora.service.PagoService;
import com.imptek.bitacora.service.ProntoPagoService;
import com.imptek.bitacora.service.ReportService;

@Controller
@RequestMapping("/factura")
public class FacturaController {
	
	
	/*REPORTE */
	@Autowired
	public FacturaController(final FacturaRepository facturaRepository, final ReportService reportService){
		this.facturaRepository = facturaRepository;
		this.reportService = reportService;
	}
	
	private final ReportService reportService;
	private final FacturaRepository facturaRepository;
	
	/*FIN REPORTE*/
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private FacturaService factService;
	
	@Autowired
	private FacturaRepository factRepository;	

	@Autowired
	private PagoService pagoService;
	
	@Autowired
	private ProntoPagoService ppagoService;
	
	@Autowired
	private ServletContext context;
	
	/*@Autowired
	private UploadFileService uploadFileService;*/
	
	private final Logger log = LoggerFactory.getLogger(getClass());


	String codCliente;
	
	@GetMapping(value ="/buscarCliente")
	public String findAllCompletos(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
				
		codCliente = (params.get("codCliente") != null || !"".equals(params.get("codCliente"))) ? (String.valueOf(params.get("codCliente").toString())) :null;
		
		if("".equals(codCliente)) {
			codCliente=null;
		}else {
			codCliente=codCliente.trim();
		}
	
		PageRequest pageRequest = PageRequest.of(page, 6);
		
		Page<Factura> listaFactCli = factService.buscarDatos(pageRequest, codCliente);
		
		
		int totalPage = listaFactCli.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("factura", listaFactCli.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("codCliente",codCliente);
		
		return "listar_bitacora";

	}
	
	@GetMapping("listar")
	public String listar(Model model, RedirectAttributes flash) {
		
		List<Factura> factura = (List<Factura>) factRepository.findAll();	
		model.addAttribute("factura", factura);
		
		return "listar_bitacora";
	}
	
	@GetMapping("new/{idCliente}")
	public String crear(@PathVariable Long idCliente , Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(idCliente);

		if (cliente == null) {
			flash.addFlashAttribute("danger", "El cliente no existe en la base de datos");
			return "redirect:/cliente/listar";
		}	

		Factura factura = new Factura();
		factura.setCliente(cliente);		
	
		List<Pago> pago = new ArrayList<>();	 	        
		model.addAttribute("pago", pago);
			
		List<Pago> pagos = pagoService.findAll();	 	        
		model.addAttribute("pagos", pagos);
		
		List<ProntoPago> ppago = new ArrayList<>();	 	        
		model.addAttribute("ppago", ppago);
			
		List<ProntoPago> ppagos = ppagoService.findAll();	 	        
		model.addAttribute("ppagos", ppagos);

		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear Factura");
		
		return "registro_bitacora";
	}
	
	@PostMapping("save")
	public String guardar(@Valid Factura factura, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status, @RequestParam("fotoLote") MultipartFile multipartFile, @RequestParam("fotoPago") MultipartFile multipartFile1) throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String fileName1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
		
		
		if (fileName.contains("..") && fileName1.contains("..")) {
			
			System.out.println("Formato de archivo no válido");
			
		} else {
			
			Optional<Factura> factActualizada = null;
			if(null!=factura.getIdFact()) {
				factActualizada = factRepository.findById(factura.getIdFact());
			}
			
			if("".equals(fileName)) {
				if(factActualizada!=null) {
					factura.setFotoLote(factActualizada.get().getFotoLote());
				}
			}else {
				factura.setFotoLote(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
			}
			
			if("".equals(fileName1)) {
				if(factActualizada!=null) {
					factura.setFotoPago(factActualizada.get().getFotoPago());
				}
			}else {
			   factura.setFotoPago(Base64.getEncoder().encodeToString(multipartFile1.getBytes()));
			}
			
					
		}		
		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("successmessage", "Factura creada con éxito");

		return "redirect:/factura/bitacoraListar";
	}

	@GetMapping("editar/{idFact}")
	public String modificar(@PathVariable(value = "idFact") Long idFact, Model model) {
		
		List<Pago> pago = new ArrayList<>();	 	        
		model.addAttribute("pago", pago);	
		
		List<ProntoPago> ppago = new ArrayList<>();	 	        
		model.addAttribute("ppago", ppago);
		
		Optional<Factura> factura = factRepository.findById(idFact);
		
		if(factura.get().getPago()!=null && factura.get().getPpago()!=null ) {
			
			if("TARJETA".equals(factura.get().getPago().getFormaPago().toUpperCase()) && 
			   "NO APLICA".equals(factura.get().getPpago().getTipoAccion().toUpperCase())) {
				/*factura.get().setBoLote("disabled");
				factura.get().setBoPago("disabled");*/
				model.addAttribute("estiloPago", "visibility:hidden;");
				model.addAttribute("estiloLote", "visibility:hidden;");
				model.addAttribute("boPago","disabled");
				model.addAttribute("boLote","disabled");
				model.addAttribute("boPPago","disabled");
				
			}else if("EFECTIVO".equals(factura.get().getPago().getFormaPago().toUpperCase()) ||
					"TRANSFERENCIA".equals(factura.get().getPago().getFormaPago().toUpperCase()) ||
					"CHEQUE".equals(factura.get().getPago().getFormaPago().toUpperCase())) {
				
				model.addAttribute("boPago","disabled");
				model.addAttribute("boLote","disabled");
				model.addAttribute("boNumLote","disabled");
				model.addAttribute("boRef","disabled");
				model.addAttribute("boPPago","disabled");
	
				
				
			}else {
				/*factura.get().setBoLote("enabled");
				factura.get().setBoPago("enabled");*/
				model.addAttribute("boPago","false");
				model.addAttribute("boLote","false");
				model.addAttribute("boPPago","false");
						
			
			}
		}
		
		List<Pago> pagos = pagoService.findAll();
		List<ProntoPago> ppagos = ppagoService.findAll();
		
		model.addAttribute("factura", factura);
		model.addAttribute("ppagos", ppagos);
		model.addAttribute("pagos", pagos);
		
		return "registro_bitacora";
	}
	
	@GetMapping("eliminar/{idFact}")
	public String eliminar(@PathVariable Long idFact, RedirectAttributes flash) {

		Factura factura = clienteService.findFacturaById(idFact);
		clienteService.deleteFactura(idFact);
		flash.addFlashAttribute("danger", "La factura no existe en la base de datos, no se pudo eliminar");
		factura.getIdFact();
		
		return "redirect:/factura/bitacoraListar";
	}
	
	@GetMapping(value ="/bitacoraListar")
	public String findAll(@RequestParam Map<String, Object> params,Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		
		//List<Factura> factura = factRepository.getAllBetweenDates(startDate, endDate);
		
		PageRequest pageRequest = PageRequest.of(page, 6);
		
		Page<Factura> listaFacturas = factService.getAll(pageRequest);
		
		int totalPage = listaFacturas.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("filtro",new FiltroFecha());
		model.addAttribute("factura", listaFacturas.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
				
		return "listar_bitacora";
	}
	
	@GetMapping(value="/filtroFecha")		
	public String getAllBetweenDates(@Valid FiltroFecha filtro,@RequestParam Map<String, Object> params, Model model) throws ParseException {
		
		/*SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date stDate = formater.parse(filtro.getStartDate());
		Date enDate = formater.parse(filtro.getEndDate());*/
		
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
				
		if (!Objects.isNull(filtro.getStartDate()) && !Objects.isNull(filtro.getEndDate())) {		
			List<Factura> factura = factRepository.getAllBetweenDates(filtro.getStartDate(),filtro.getEndDate());			
			
			PageRequest pageRequest = PageRequest.of(page, 6);
			
			Page<Factura> listaFacturas = factService.getAll(pageRequest);
			
			int totalPage = listaFacturas.getTotalPages();
			if(totalPage > 0) {
				List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
				model.addAttribute("pages", pages);
			}
			model.addAttribute("factura",factura);
			model.addAttribute("current", page + 1);
			model.addAttribute("next", page + 2);
			model.addAttribute("prev", page);
			model.addAttribute("last", totalPage);			
			
		}else {
			
			return "redirect:/factura/bitacoraListar";
		}
		return "listar_bitacora";
	}
	
	@GetMapping(value="/facturasReport")		
	public void createExcel(HttpServletRequest request, HttpServletResponse response) {
			
	List<Factura>factura = (List<Factura>) factRepository.findAll();	
	boolean isFlag = factService.createExcel(factura,context,request,response);
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"facturas"+".xls");
			filedownload(fullPath,response,"facturas.xls");
		}
	}
	
	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		final int BUFFER_SIZE = 4096;
		if (file.exists()) {
			
			try {
				FileInputStream inputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment: filename="+fileName);				
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(buffer))!= -1) {
					outputStream.write(buffer,0,bytesRead);
				}
					
				inputStream.close();
				outputStream.close();
				file.delete();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}	

/*
private String upload_folder = ".//src//main//resources//files//";

public void saveFile(MultipartFile file) throws IOException {
    if(!file.isEmpty()){
        byte[] bytes = file.getBytes();
        Path path = Paths.get(upload_folder + file.getOriginalFilename());
        Files.write(path,bytes);
    }
}*/

/*@GetMapping(value = "/facturasReport.xlsx", produces = MediaType.ALL_VALUE)
@ResponseBody
public HttpEntity<byte[]> getFacturasReportXlsx(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
	final FacturasReport report = new FacturasReport((Collection<Factura>) facturaRepository.findAll());
	final byte[] data = reportService.getReportXlsx(report.getReport());
	HttpHeaders header = new HttpHeaders();
	header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=facturasReport.xls");
	header.setContentLength(data.length);
	return new HttpEntity<byte[]>(data, header);
}*/
	
	
/*	byte[] fotoLote;
	byte[] fotoPago;
	try {
		fotoLote = factura.getFotoLote().getBytes("UTF-8");
		factura.setfLote(FileUtils.writeByteArrayToFile(new File(""), fotoLote));

	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
	
	try {
		fotoPago = factura.getFotoLote().getBytes("UTF-8");
		BASE64DecodedMultipartFile var= new BASE64DecodedMultipartFile(fotoPago);
		factura.setfLote((MultipartFile) var.getResource());
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}*/
	
	/*if (fileName1.contains("..")) {
		
		System.out.println("Formato de archivo no válido");
		
	} else {
		
		factura.setPago(pago.setImgPago(Base64.getEncoder().encodeToString(multipartFile1.getBytes())));	

	}*/	
	
	/*String uploadDir = "/lote-photos/" + factura.getIdFact();
	Path uploadPath = Paths.get(uploadDir);
	
	if (!Files.exists(uploadPath)) {
		Files.createDirectories(uploadPath);
	}
	
	try (InputStream inputStream = multipartFile.getInputStream()){
		Path filePath = uploadPath.resolve(fileName);
		System.out.println(filePath.toString());
		Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("No se pudo cargar la imagen: "+ fileName);
		}*/	

	/*	@GetMapping("ver/{idFact}")
	public String ver(@PathVariable Long idFact, Model model, RedirectAttributes flash) {

		Factura factura = clienteService.findFacturaById(idFact);

		if (factura == null) {
			flash.addFlashAttribute("danger", "La factura no existe en la base de datos");
			return "redirect:/listar";
		}

		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura: ".concat(factura.getCliente().getNomCliente()));

		return "listar_bitacora";
	}*/
	
	/*@PostMapping("uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {
		
		if (file == null || file.isEmpty()) {
			
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo");
			return "redirect:listar_bitacora";
			
		} else {

		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.home"));
		builder.append(File.separator);
		builder.append("imagenes spring");
		builder.append(file.getOriginalFilename());
		
		byte [] fileBytes = file.getBytes();
		Path path = Paths.get(builder.toString());
		Files.write(path, fileBytes);
		
		attributes.addAttribute("message","Archivo cargado con éxito ["+builder.toString()+"]");
		
		
		return "redirect:listar_bitacora";
	}
*/

