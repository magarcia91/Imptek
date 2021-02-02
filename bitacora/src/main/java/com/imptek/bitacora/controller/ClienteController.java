package com.imptek.bitacora.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.imptek.bitacora.entity.Cliente;
import com.imptek.bitacora.report.ClientesReport;
import com.imptek.bitacora.repository.ClienteRepository;
import com.imptek.bitacora.service.ClienteService;
import com.imptek.bitacora.service.ReportService;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/*REPORTE */
	@Autowired
	public ClienteController(final ClienteRepository clienteRepository, final ReportService reportService){
		this.clienteRepository = clienteRepository;
		this.reportService = reportService;
	}
	
	private final ReportService reportService;
	private final ClienteRepository clienteRepository;
	
	/*FIN REPORTE*/

	@Autowired
	private ClienteService clienteService;
	
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
	
		PageRequest pageRequest = PageRequest.of(page, 8);
		
		//Page<Cliente> listaClientes = clienteService.getAll(pageRequest);
		Page<Cliente> listaClientes = clienteService.buscarDatos(pageRequest, codCliente);
		
		
		int totalPage = listaClientes.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("cliente", listaClientes.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("codCliente",codCliente);
		
		return "listar_cliente";

	}
	
	@GetMapping(value ="/buscarClienteBitacora")
	public String findAllCompletos1(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		codCliente = (params.get("codCliente") != null || !"".equals(params.get("codCliente"))) ? (String.valueOf(params.get("codCliente").toString())) :null;
		
		if("".equals(codCliente)) {
			codCliente=null;
		}else {
			codCliente=codCliente.trim();
		}
	
		PageRequest pageRequest = PageRequest.of(page, 8);
		
		//Page<Cliente> listaClientes = clienteService.getAll(pageRequest);
		Page<Cliente> listaClientes = clienteService.buscarDatos(pageRequest, codCliente);
		
		
		int totalPage = listaClientes.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("cliente", listaClientes.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("codCliente",codCliente);
		
		return "listar_cliente_bitacora";

	}
		
	@GetMapping("ver/{idCliente}")
	public String ver(@PathVariable(value = "idCliente") Long idCliente, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.fetchByIdWithFacturas(idCliente);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNomCliente());

		return "listar_cliente";

	}

	@GetMapping("listar")
	public String listar(Model model) {

		List<Cliente> cliente = clienteService.findAll();
		model.addAttribute("cliente", cliente);

		return "listar_cliente";
	}
	

	@GetMapping("bitacora")
	public String bitacora(Model model) {

		List<Cliente> cliente = clienteService.findAll();
		model.addAttribute("cliente", cliente);

		return "listar_cliente_bitacora";
	}
	
	@GetMapping("new")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		return "registro_cliente";
	}

	@PostMapping("save")
	public String guardar(@Valid Cliente cliente,Model model, BindingResult result,RedirectAttributes attribute) {
		
		
		if (!Objects.isNull(cliente.getIdCliente())) {
			
			clienteService.save(cliente);		
			
		} else {
			
			Optional<Cliente> cli = clienteService.findByCodCliente(cliente.getCodCliente());
			
			if (cli.isPresent()) {				
				attribute.addFlashAttribute("errormessage", "El cliente ya existe, ingrese un nuevo registro");
				return "registro_cliente";
							
			}else {
								
				Cliente clie = new Cliente();
				model.addAttribute("cliente", clie);	
				clienteService.save(cliente);
				attribute.addFlashAttribute("successmessage", "Cliente creado con éxito");
			}		
						
		}		
		return "redirect:/cliente/clientesListar";
	}

	@GetMapping("editar/{idCliente}")
	public String modificar(@PathVariable(value = "idCliente") Long idCliente, Model model) {

		Cliente cliente = clienteService.findOne(idCliente);
		model.addAttribute("cliente", cliente);
		return "registro_cliente";
	}

	@GetMapping("eliminar/{idCliente}")
	public String eliminar(@PathVariable(value = "idCliente") Long idCliente, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(idCliente);
		clienteService.delete(idCliente);
		
		return "redirect:/cliente/listar";
	}
	
	@GetMapping(value ="/clientesListar")
	public String findAll(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
				
		PageRequest pageRequest = PageRequest.of(page, 8);
		
		Page<Cliente> listaClientes = clienteService.getAll(pageRequest);
		
		int totalPage = listaClientes.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("cliente", listaClientes.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		
		return "listar_cliente";
	}

	
	@GetMapping(value ="/clientesBitacoraListar")
	public String findAll1(@RequestParam Map<String, Object> params, Model model) {
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 8);
		
		Page<Cliente> listaClientes = clienteService.getAll(pageRequest);
		
		int totalPage = listaClientes.getTotalPages();
		if(totalPage > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("cliente", listaClientes.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		
		return "listar_cliente_bitacora";
	}
	
	@GetMapping(value = "/clientesReport.xlsx", produces = MediaType.ALL_VALUE)
	@ResponseBody
	public HttpEntity<byte[]> getClientesReportXlsx(final HttpServletResponse response) throws JRException, IOException, ClassNotFoundException {
		final ClientesReport report = new ClientesReport((Collection<Cliente>) clienteRepository.findAll());
		final byte[] data = reportService.getReportXlsx(report.getReport());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=clientesReport.xlsx");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
	}


}
