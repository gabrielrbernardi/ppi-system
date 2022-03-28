package mainPackage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="produtos")
public class ProdutoDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	private String codProduto;
	private String nomeProduto;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	
	@Override
	public String toString() {
		return "Domain [Id=" + Id + ", codProduto=" + codProduto + ", nomeProduto=" + nomeProduto + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
}
