package br.com.caelum.ingresso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.IngressoDao;

@Controller
public class VendaControler {
	
	@Autowired
	private IngressoDao dao;
	
	@GetMapping("/admin/vendas")
	public ModelAndView vendas(){
		ModelAndView modelAndView = new ModelAndView("venda/lista");
		modelAndView.addObject("ingressos",dao.listar());
		
				return modelAndView;
	}

	public IngressoDao getDao() {
		return dao;
	}

	public void setDao(IngressoDao dao) {
		this.dao = dao;
	}
}
