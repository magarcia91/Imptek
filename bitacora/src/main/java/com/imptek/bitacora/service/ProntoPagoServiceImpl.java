package com.imptek.bitacora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imptek.bitacora.entity.ProntoPago;
import com.imptek.bitacora.repository.ProntoPagoRepository;

@Service
public class ProntoPagoServiceImpl implements ProntoPagoService {

	@Autowired
	private ProntoPagoRepository ppagoRepository;
	
	@Override
	@Transactional
	public List<ProntoPago> findAll() {		
		return (List<ProntoPago>) ppagoRepository.findAll();
	}

}
