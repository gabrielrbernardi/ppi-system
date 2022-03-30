
<template>
    <div class="card">
        <DataView :value="products" :layout="layout" :paginator="true" :rows="9" 
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" 
            currentPageReportTemplate="Mostrando {first} de {last} de um total de {totalRecords} produtos" :rowsPerPageOptions="[3,9,27]">
			<template #header>
                <div class="grid grid-nogutter">
                    <div class="col-6" style="text-align: left">
                        <DataViewLayoutOptions v-model="layout" />
                    </div>
                    <div class="col-6" style="text-align: right">
                        <Button label="Recarregar" v-if="!isLoading" icon="pi pi-refresh" class="p-button-info mr-2" @click="getProducts" />
                        <Button label="Recarregar" v-else icon="pi pi-spin pi-spinner" class="p-button-info mr-2" disabled />
                    </div>
                </div>
			</template>
            
            <template #list="slotProps">
				<div class="col-12">
					<div class="product-list-item">
						<div class="product-list-detail">
                            <Skeleton v-if="isLoading" height="2rem" width="50vw" class="mb-2"></Skeleton>
                            <Skeleton v-if="isLoading" height="2rem" width="25vw" class="mb-2"></Skeleton>
							<div v-if="!isLoading" class="product-name">{{slotProps.data.nomeProduto}}</div>
							<div v-if="!isLoading" class="product-description">{{slotProps.data.descricaoProduto || "\"Produto sem descrição\""}}</div>
                            <div v-if="!isLoading" class="product-description">{{formatQuantity(slotProps.data.quantidadeProduto)}}</div>

						</div>
						<div class="product-list-action">
                            <Skeleton v-if="isLoading" height="2rem" width="15vw" class="mb-2"></Skeleton>
                            <Skeleton v-if="isLoading" height="2rem" width="15vw" class="mb-2"></Skeleton>
                            <Skeleton v-if="isLoading" height="1rem" width="15vw" class="mb-2"></Skeleton>
							<span v-if="!isLoading" class="product-price">{{formatCurrency(slotProps.data.precoProduto)}}</span>
							<Button v-if="!isLoading" icon="pi pi-eye" label="Visualizar produto" @click="viewProduct(slotProps.data)"></Button>
							<Button v-if="!isLoading" icon="pi pi-shopping-cart" label="Adicionar ao carrinho" :class="{'p-button-secondary': slotProps.data.statusProduto === 'OUTOFSTOCK'}" :disabled="slotProps.data.statusProduto === 'OUTOFSTOCK'" @click="handleCart(slotProps.data)"></Button>
							<span v-if="!isLoading" :class="'product-badge status-'+slotProps.data.statusProduto.toLowerCase()">{{slotProps.data.statusProduto}}</span>
						</div>
					</div>
				</div>
			</template>

			<template #grid="slotProps">
				<div class="col-12 md:col-4">
					<div class="product-grid-item card">
						<div class="product-grid-item-top">
                            <Skeleton v-if="isLoading" height="2rem" width="20%" class="mb-2"></Skeleton>
                            
                            <div v-if="!isLoading" class="product-description h5">{{formatQuantity(slotProps.data.quantidadeProduto)}}</div>
                            <Skeleton v-if="isLoading" height="2rem" width="20%" class="mb-2 product-badge"></Skeleton>
							<span v-if="!isLoading" :class="'product-badge status-'+slotProps.data.statusProduto.toLowerCase()">{{slotProps.data.statusProduto}}</span>
						</div>
						<div class="product-grid-item-content">
                            <Skeleton v-if="isLoading" height="2rem" width="50%" class="mb-2 mx-auto"></Skeleton>
                            <Skeleton v-if="isLoading" height="1rem" width="10%" class="mb-2 mx-auto"></Skeleton>
							<div v-if="!isLoading" class="product-name">{{slotProps.data.nomeProduto}}</div>
							<div v-if="!isLoading" class="product-description">{{slotProps.data.descricaoProduto || "\"Produto sem descrição\""}}</div>
						</div>
						<div class="product-grid-item-bottom">
                            <Skeleton v-if="isLoading" height="2rem" width="30%" class="mb-2"></Skeleton>
							<span v-if="!isLoading" class="product-price">{{formatCurrency(slotProps.data.precoProduto)}}</span>
							<div>
                                <Button class="mr-2" icon="pi pi-eye" :disabled="isLoading" @click="viewProduct(slotProps.data)"></Button>
                                <Button icon="pi pi-shopping-cart" :class="{'p-button-secondary': slotProps.data.statusProduto === 'OUTOFSTOCK'}" :disabled="isLoading || slotProps.data.statusProduto === 'OUTOFSTOCK'" @click="handleCart(slotProps.data)"></Button>
                            </div>
						</div>
					</div>
				</div>
			</template>
		</DataView>

        <Dialog v-model:visible="showProduct" :style="{width: '450px'}" header="Visualização" :modal="true" class="p-fluid">
            <div class="field">
                <label for="codProduto"><b>Código</b></label><br/>
                <label for="codProduto">{{product.codProduto}}</label>
            </div>

            <div class="field">
                <label for="nomeProduto"><b>Nome</b></label><br/>
                <label for="nomeProduto">{{product.nomeProduto}}</label>
            </div>

            <div class="field">
                <label for="descricaoProduto"><b>Descrição</b></label><br/>
                <label for="descricaoProduto">{{product.descricaoProduto || "\"Produto sem descrição\""}}</label>
            </div>

            <div class="field">
                <label for="precoProduto"><b>Preço</b></label><br/>
                <label for="precoProduto">{{formatCurrency(product.precoProduto)}}</label>

            </div>

            <div class="field">
                <label for="quantidadeProduto"><b>Quantidade</b></label><br/>
                <label for="quantidadeProduto">{{product.quantidadeProduto}}</label>
            </div>

            <div class="field">
                <span :class="'product-badge status-' + (product.statusProduto ? product.statusProduto.toLowerCase() : '')">{{product.statusProduto}}</span>
            </div>

            <template #footer>
                <Button label="Fechar" icon="pi pi-times" class="p-button-text" @click="this.showProduct = false"/>
            </template>
        </Dialog>
	</div>
</template>

<script>
import api from "../services/api.ts";
import ProductService from './service';

export default {
    data() {
        /* products: [
				{id: "1000",code: "f230fh0g3",name: "Bamboo Watch",description: "Product Description",image: "bamboo-watch.jpg",price: 65,category: "Accessories",quantity: 24,inventoryStatus: "INSTOCK",rating: 5},
			],*/
		return {
            products: [],
            layout: 'grid',
            showProduct: false,
            isLoading: false
        }
    },
    productService: null,
    created() {
        this.productService = new ProductService();
    },
    mounted() {
        this.getProducts();
        localStorage.products = this.products
        console.log(localStorage.products)
    },
    methods: {
        getProducts(){
            this.isLoading = true;
            setTimeout(() => {
                api.get("/", {headers: {'Access-Control-Allow-Credentials': "*"} }).then(response => {this.products = response.data; this.isLoading = false}).catch(err => {console.log(err); this.isLoading = false;})
            }, 1000);
        },
        viewProduct(product){
            this.product = {...product};
            this.showProduct = true;
        },
        handleCart(product){
            console.log(product)

            api.put(`/cart/${product.id}`).then(() => {this.getProducts();}).catch(err => alert(err))
        },
        formatQuantity(quantity){
            if(quantity === 1){
                return "1 unidade"
            }else{
                return quantity + " unidades"
            }
        },
        formatCurrency(value) {
            if(value)
				return value.toLocaleString('pt-BR', {style: 'currency', currency: 'BRL'});
            var zero = 0;
            return zero.toLocaleString('pt-BR', {style: 'currency', currency: 'BRL'});
        },
    }
}
</script>

<style lang="scss" scoped>
    .card {
        background: #ffffff;
        padding: 2rem;
        box-shadow: 0 2px 1px -1px rgba(0,0,0,.2), 0 1px 1px 0 rgba(0,0,0,.14), 0 1px 3px 0 rgba(0,0,0,.12);
        border-radius: 4px;
        margin-bottom: 2rem;
    }
    .p-dropdown {
        width: 14rem;
        font-weight: normal;
    }

    .product-name {
        font-size: 1.5rem;
        font-weight: 700;
    }

    .product-description {
        margin: 0 0 1rem 0;
    }

    .product-category-icon {
        vertical-align: middle;
        margin-right: .5rem;
    }

    .product-category {
        font-weight: 600;
        vertical-align: middle;
    }

    ::v-deep(.product-list-item) {
        display: flex;
        align-items: center;
        padding: 1rem;
        width: 100%;

        img {
            width: 50px;
            box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
            margin-right: 2rem;
        }

        .product-list-detail {
            flex: 1 1 0;
        }

        .p-rating {
            margin: 0 0 .5rem 0;
        }

        .product-price {
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: .5rem;
            align-self: flex-end;
        }

        .product-list-action {
            display: flex;
            flex-direction: column;
        }

        .p-button {
            margin-bottom: .5rem;
        }
    }

    ::v-deep(.product-grid-item) {
        margin: .5rem;
        border: 1px solid var(--surface-border);

        .product-grid-item-top,
        .product-grid-item-bottom {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        img {
            box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
            margin: 2rem 0;
        }

        .product-grid-item-content {
            text-align: center;
        }

        .product-price {
            font-size: 1.5rem;
            font-weight: 600;
        }
    }

    @media screen and (max-width: 576px) {
        .product-list-item {
            flex-direction: column;
            align-items: center;

            img {
                margin: 2rem 0;
            }

            .product-list-detail {
                text-align: center;
            }

            .product-price {
                align-self: center;
            }

            .product-list-action {
                display: flex;
                flex-direction: column;
            }

            .product-list-action {
                margin-top: 2rem;
                flex-direction: row;
                justify-content: space-between;
                align-items: center;
                width: 100%;
            }
        }
        
        .custom-skeleton {
            border: 1px solid var(--surface-border);
            border-radius: 4px;

            ul {
                list-style: none;
            }
        }

    }
</style>
