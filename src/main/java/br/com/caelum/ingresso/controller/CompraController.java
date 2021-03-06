package br.com.caelum.ingresso.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.CompraDao;
import br.com.caelum.ingresso.dao.IngressoDao;
import br.com.caelum.ingresso.dao.LugarDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Carrinho;
import br.com.caelum.ingresso.model.Cartao;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.form.CarrinhoForm;

@Controller
public class CompraController {
	
	@Autowired
	private SessaoDao sessaoDao;
	@Autowired
	private LugarDao lugarDao;
	@Autowired
	private CompraDao compraDao;
	
	@Autowired
	private IngressoDao ingressoDao;
	
	public IngressoDao getIngressoDao() {
		return ingressoDao;
	}

	public void setIngressoDao(IngressoDao ingressoDao) {
		this.ingressoDao = ingressoDao;
	}

	public CompraDao getCompraDao() {
		return compraDao;
	}

	public void setCompraDao(CompraDao compraDao) {
		this.compraDao = compraDao;
	}

	@Autowired
	private Carrinho carrinho;
	
	@PostMapping("/compra/comprar")
	@Transactional
	public ModelAndView comprar(@Valid Cartao cartao, BindingResult result) {
		
		synchronized (this) {
	
			ModelAndView modelAndView = new ModelAndView("redirect:/");
			
			for (Ingresso i : carrinho.getIngressos()){
				boolean disponivel = ingressoDao.isDisponivel(i);
				if (!disponivel) {
					return checkout(cartao);
				}
			}
		
			if (cartao.isValido()){
				compraDao.save(carrinho.toCompra());
			} else {
				result.rejectValue("vencimento",  "Vencimento Inválido");
				return checkout(cartao);
			}
			return modelAndView;
		}

	}
	
	@GetMapping ("/compra")
	public ModelAndView checkout(Cartao cartao){
		ModelAndView modelAndView = new ModelAndView("compra/pagamento");
		
		modelAndView.addObject("carrinho",carrinho);
		return modelAndView;
	}
	
	@PostMapping("/compra/ingressos")
	public ModelAndView enviarParaPagamento(CarrinhoForm carrinhoForm) {
		ModelAndView modelAndView = new ModelAndView("redirect:/compra");
		carrinhoForm.toIngressos(sessaoDao, lugarDao).forEach(carrinho::add);
		
		return modelAndView;
	}
	
	public SessaoDao getSessaoDao() {
		return sessaoDao;
	}

	public void setSessaoDao(SessaoDao sessaoDao) {
		this.sessaoDao = sessaoDao;
	}

	public LugarDao getLugarDao() {
		return lugarDao;
	}

	public void setLugarDao(LugarDao lugarDao) {
		this.lugarDao = lugarDao;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	
}
