package mainPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@org.springframework.stereotype.Controller
@RequestMapping(path="/cart")
@CrossOrigin
public class VendaController {
	@Autowired
	private ProductRepository productRepository;
	
	@PutMapping(value="/{id}")
	@ResponseBody
	public ResponseEntity<?> updateProduct(@PathVariable Long id) throws Exception{
		try {
			ProdutoDomain pd = productRepository.findById(id).orElseThrow(() -> new NotFoundException()); //retornara o objeto se for encontrado ou vazio, caso contrario
			
			if(pd.getQuantidadeProduto() == 0) {
				throw new Exception("Produto sem estoque");
			}else if(pd.getQuantidadeProduto() == 1) {
				pd.setStatusProduto("OUTOFSTOCK");
			}
			
			pd.setQuantidadeProduto(pd.getQuantidadeProduto() - 1);
			
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
}
