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
public class ProdutoController {
	
	@Autowired
	ProdutoDao pd;
	
	@RequestMapping(name = "produto", value = "/produto", method = RequestMethod.GET)
	public ModelAndView init(ModelMap model) {
		return new ModelAndView("produto");
	}
	
	
	@RequestMapping(name = "produto", value="/produto", method = RequestMethod.POST)
	public ModelAndView init(ModelMap model, @RequestParam Map<String,String> allParam) {
		String botao = allParam.get("botao");
		String botaocodigo = allParam.get("botaocodigo");
		String botaonome = allParam.get("botaonome");
		String botaovu = allParam.get("botaovu");
		String botaoqtd = allParam.get("botaoqtd");
		String erro ="";
		
		Produto p = new Produto();
		List<Produto> listap = new ArrayList<>();
		try {
			if((botao.equals("Inserir") || botao.equals("Atualizar"))&&
					(!botaocodigo.equals("")&&!botaonome.equals("")&&
					 !botaovu.equals("")&&!botaoqtd.equals("")
							)) {				
				p.setCodigo(Integer.parseInt(botaocodigo));
				p.setNome(botaonome);
				p.setQtd_estoque(Integer.parseInt(botaoqtd));
				p.setValor_unitario(Float.parseFloat(botaovu));
				if(botao.equals("Inserir")) {
					pd.insert(p);
				}else {
					pd.update(p);
				}
			}
			if(botao.equals("Deletar")) {
					pd.delete(Integer.parseInt(botaocodigo));
				} if(botao.equals("Buscar")) {
					pd.consultaProduto(p);
				}
			if(botao.equals("Listar")) {
				listap = pd.consultaProdutos();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			erro = e.getMessage();
		}finally {
			model.addAttribute("prod",p);
			model.addAttribute("listaproduto",listap);
			model.addAttribute("erro",erro);
		}	
		return new ModelAndView("produto");	
	}
}