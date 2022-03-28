package mainPackage;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@org.springframework.stereotype.Controller
@RequestMapping(path="/")
public class ProdutoController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping(path="/status")
	public @ResponseBody FormatReturnMessage status() {
		return new FormatReturnMessage("true", HttpStatus.OK);
	}
	
	@PostMapping(value="/")
	@ResponseBody
	public ResponseEntity<ProdutoDomain> addProduct (@Valid @RequestBody ProdutoDomain prod) {
		ProdutoDomain pd = new ProdutoDomain();
		pd.setCodProduto(prod.getCodProduto());
		pd.setNomeProduto(prod.getNomeProduto());
		productRepository.save(pd);
			
		return new ResponseEntity<>(pd, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	@ResponseBody
	public ResponseEntity<ProdutoDomain> getSpecificProduct(@PathVariable Long id){
		try {
			ProdutoDomain pd = productRepository.findById(id).orElseThrow(() -> new Exception("Produto nao encontrado"));
			return new ResponseEntity<>(pd, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value="/getAll")
	@ResponseBody
	public Iterable<ProdutoDomain> getAllProducts() {
		return productRepository.findAll();
	}
	
	@PutMapping(value="/{id}")
	@ResponseBody
	public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProdutoDomain product) throws Exception{
		try {
			ProdutoDomain pd = productRepository.findById(id).orElseThrow(() -> new Exception("Produto nao encontrado")); //retornara o objeto se for encontrado ou vazio, caso contrario
			pd.setNomeProduto(product.getNomeProduto());
			pd.setCodProduto(product.getCodProduto());
			
			productRepository.save(pd);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Produto nao encontrado", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseBody
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Erro na execucao", HttpStatus.NOT_FOUND);
		}
	}
}
