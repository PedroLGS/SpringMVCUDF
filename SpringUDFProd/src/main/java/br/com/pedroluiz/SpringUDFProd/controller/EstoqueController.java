package br.com.pedroluiz.SpringUDFProd.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.pedroluiz.SpringUDFProd.model.Produto;
import br.com.pedroluiz.SpringUDFProd.persistence.ProdutoDao;

@Controller
public class EstoqueController {
	
	@Autowired
	ProdutoDao pd;
	
	@RequestMapping(name = "estoque", value = "/estoque", method = RequestMethod.GET)
	public ModelAndView init(ModelMap model) {
		return new ModelAndView("estoque");
	}
	
	
	@RequestMapping(name = "estoque", value="/estoque", method = RequestMethod.POST)
	public ModelAndView init(ModelMap model, @RequestParam Map<String,String> allParam) {
		String bt = allParam.get("botao");
		String minval = allParam.get("minval");

		int val=0;
		List<Produto> lp = new ArrayList<>();
		try {	
			if(bt.equals("Quantidade")) {
				val= pd.consultaQtdForaEstoque(Integer.parseInt(minval));
			}
			else {
				lp = pd.consultaListaProdutosForaEstoque(Integer.parseInt(minval));
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			model.addAttribute("val",val);
			model.addAttribute("list",lp);
		}
		
		return new ModelAndView("estoque");
		
	}
}