package mainPackage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="produtos")
public class ProdutoDomain {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	@NotNull
	private String codProduto;
	@NotNull
	private String nomeProduto;
	@NotNull
	@Min(0)
	private double precoProduto;
	@NotNull
	private int quantidadeProduto;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		this.Id = id;
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
	public double getPrecoProduto() {
		return precoProduto;
	}
	public void setPrecoProduto(double precoProduto) {
		this.precoProduto = precoProduto;
	}
	public int getQuantidadeProduto() {
		return quantidadeProduto;
	}
	public void setQuantidadeProduto(int quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}
	
	@Override
	public String toString() {
		return "ProdutoDomain [Id=" + Id + ", codProduto=" + codProduto + ", nomeProduto=" + nomeProduto
				+ ", precoProduto=" + precoProduto + ", quantidadeProduto=" + quantidadeProduto + ", toString()="
				+ super.toString() + "]";
	}	
}
