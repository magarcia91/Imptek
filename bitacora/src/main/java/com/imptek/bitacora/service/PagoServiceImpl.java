package com.imptek.bitacora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imptek.bitacora.entity.Pago;
import com.imptek.bitacora.repository.PagoRepository;

@Service
public class PagoServiceImpl implements PagoService {

	@Autowired
	private PagoRepository pagoRepository;
	
	@Override
	@Transactional
	public List<Pago> findAll() {		
		return (List<Pago>) pagoRepository.findAll();
	}

}
