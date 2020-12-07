package com.imptek.bitacora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.imptek.bitacora.entity.Pago;
import com.imptek.bitacora.service.PagoService;

@Controller
public class PagoController {
	
	@Autowired
	private PagoService pagoService;
	
	@GetMapping("listar")
	public String listar(Model model, RedirectAttributes flash) {
		
		List<Pago> pago = (List<Pago>) pagoService.findAll();	
		model.addAttribute("pago", pago);
		
		return "pago";
	}

}
