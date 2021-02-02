package com.imptek.bitacora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.imptek.bitacora.entity.ProntoPago;
import com.imptek.bitacora.service.ProntoPagoService;

@Controller
public class ProntoPagoController {
	
	@Autowired
	private ProntoPagoService ppagoService;
	
	@GetMapping("listarP")
	public String listar(Model model, RedirectAttributes flash) {
		
		List<ProntoPago> ppago = (List<ProntoPago>) ppagoService.findAll();	
		model.addAttribute("ppago", ppago);
		
		return "ppago";
	}

}
