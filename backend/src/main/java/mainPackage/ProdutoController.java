package mainPackage;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class ProdutoController {
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping(path="/status")
	public @ResponseBody FormatReturnMessage status() {
		return new FormatReturnMessage("true");
	}
	
	@PostMapping(value="/")
	@ResponseBody
	public ResponseEntity<?> addProduct (@Valid @RequestBody ProdutoDomain prod, Errors err){
		try {
			if(err.hasErrors()) {
				throw new Exception("Erro na passagem de parametro");
			}
			
			if(prod.getCodProduto().isBlank() || prod.getNomeProduto().isBlank() || prod.getPrecoProduto() <= 0.0 || prod.getQuantidadeProduto() < 0) {
				throw new Exception("Campos Invalidos");
			}
			ProdutoDomain pd = new ProdutoDomain();
			pd.setCodProduto(prod.getCodProduto());
			pd.setNomeProduto(prod.getNomeProduto());
			pd.setPrecoProduto(prod.getPrecoProduto());
			pd.setQuantidadeProduto(prod.getQuantidadeProduto());
			pd.setDescricaoProduto(prod.getDescricaoProduto());
			
			if(prod.getQuantidadeProduto() == 0) {
				pd.setStatusProduto("OUTOFSTOCK");
			}else {
				pd.setStatusProduto("INSTOCK");
			}
			productRepository.save(pd);
			
			return ResponseEntity.status(HttpStatus.OK).body(pd);		
		}
		catch (Exception e) {
			FormatErrorMessage errorMessage = new FormatErrorMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
	}
	
	@GetMapping(value="/{id}")
	@ResponseBody
	public ResponseEntity<?> getSpecificProduct(@PathVariable Long id){
		try {
			ProdutoDomain pd = productRepository.findById(id).orElseThrow(() -> new NotFoundException());
			return new ResponseEntity<>(pd, HttpStatus.OK);
		}catch (NotFoundException e) {
			FormatErrorMessage errorMessage = new FormatErrorMessage("Registro nao encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
		catch (Exception e) {
			FormatErrorMessage errorMessage = new FormatErrorMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
	}
	
	@GetMapping(value="/")
	@ResponseBody
	public Iterable<ProdutoDomain> getAllProducts() {
		return productRepository.findAll();
	}
	
	@PutMapping(value="/{id}")
	@ResponseBody
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProdutoDomain prod) throws Exception{
		try {
			ProdutoDomain pd = productRepository.findById(id).orElseThrow(() -> new NotFoundException()); //retornara o objeto se for encontrado ou vazio, caso contrario

			if(prod.getCodProduto().isBlank() || prod.getNomeProduto().isBlank() || prod.getPrecoProduto() <= 0.0 || prod.getQuantidadeProduto() < 0) {
				throw new Exception("Campos Invalidos");
			}
			
			pd.setNomeProduto(prod.getNomeProduto());
			pd.setCodProduto(prod.getCodProduto());
			pd.setPrecoProduto(prod.getPrecoProduto());
			pd.setQuantidadeProduto(prod.getQuantidadeProduto());
			pd.setDescricaoProduto(prod.getDescricaoProduto());
			
			if(prod.getQuantidadeProduto() == 0) {
				pd.setStatusProduto("OUTOFSTOCK");
			}else {
				pd.setStatusProduto("INSTOCK");
			}
			
			productRepository.save(pd);
			return ResponseEntity.status(HttpStatus.OK).body(pd);	
		}catch (NotFoundException e) {
			FormatErrorMessage errorMessage = new FormatErrorMessage("Registro nao encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		} 
		catch (Exception e) {
			FormatErrorMessage errorMessage = new FormatErrorMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteProduct(@PathVariable Long id){
		try {
			productRepository.findById(id).orElseThrow(() -> new NotFoundException());
			
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (NotFoundException e) {
			FormatErrorMessage errorMessage = new FormatErrorMessage("Registro nao encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}catch(Exception e) {
			return new ResponseEntity<>("Erro na execucao", HttpStatus.BAD_REQUEST);
		}
	}
}
