
<template>
    <div>
        <div class="card">
            <Toolbar class="mb-4">
                <template #start>
                    <Button label="Novo Produto" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" />
                </template>
                <template #end>
                    <Button label="Recarregar" v-if="!isLoading" icon="pi pi-refresh" class="p-button-info mr-2" @click="getProducts" />
                    <Button label="Recarregar" v-else icon="pi pi-spin pi-spinner" class="p-button-info mr-2" disabled />
                </template>
            </Toolbar>

            <DataTable ref="dt" :value="products" removableSort v-model:selection="selectedProducts" dataKey="id" 
                :paginator="true" :rows="10" :filters="filters"
                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
                currentPageReportTemplate="Mostrando {first} de {last} de um total de {totalRecords} produtos" responsiveLayout="scroll">
                <template #header>
                    <div class="table-header flex flex-column md:flex-row md:justiify-content-between">
						<h5 class="mb-2 md:m-0 p-as-md-center">Produtos</h5>
						<span class="p-input-icon-left">
                            <i class="pi pi-search" />
                            <InputText v-model="filters['global'].value" placeholder="Pesquisar..." />
                        </span>
					</div>
                </template>

                <!-- <Column style="width: 3rem" :exportable="false"></Column> -->
                <Column field="id" header="Id" :sortable="true" style="min-width:5rem">
                    <template #body="slotProps">
                        <Skeleton v-if="isLoading" height="2vh"></Skeleton>
                        <div v-else>{{slotProps.data.id}}</div>
                    </template>
                </Column>
                <Column field="codProduto" header="Código" :sortable="true" style="min-width:12rem">
                    <template #body="slotProps">
                        <Skeleton v-if="isLoading" height="2vh"></Skeleton>
                        <div v-else>{{slotProps.data.codProduto}}</div>
                    </template>
                </Column>
                <Column field="nomeProduto" header="Nome" :sortable="true" style="min-width:16rem">
                    <template #body="slotProps">
                        <Skeleton v-if="isLoading" height="2vh"></Skeleton>
                        <div v-else>{{slotProps.data.nomeProduto}}</div>
                    </template>
                </Column>
                <Column field="precoProduto" header="Preço" :sortable="true" style="min-width:8rem">
                    <template #body="slotProps">
                        <Skeleton v-if="isLoading" height="2vh"></Skeleton>
                        <div v-else>{{formatCurrency(slotProps.data.precoProduto)}}</div>
                    </template>
                </Column>
                <Column field="statusProduto" header="Status" :sortable="true" style="min-width:12rem">
                    <template #body="slotProps">
                        <Skeleton v-if="isLoading" height="2vh"></Skeleton>
                        <span v-else :class="'product-badge status-' + (slotProps.data.statusProduto ? slotProps.data.statusProduto.toLowerCase() : '')">{{slotProps.data.statusProduto}}</span>
                    </template>
                </Column>
                <Column field="quantidadeProduto" header="Quantidade" :sortable="true" style="min-width:12rem">
                    <template #body="slotProps">
                        <Skeleton v-if="isLoading" height="2vh"></Skeleton>
                        <div v-else>{{slotProps.data.quantidadeProduto}}</div>
                    </template>
                </Column>
                <Column :exportable="false" style="min-width:8rem">
                    <template #body="slotProps">
                        <Skeleton height="2vh" v-if="isLoading"></Skeleton>
                        <Button v-if="!isLoading" icon="pi pi-eye" class="p-button-rounded p-button-info mr-2" @click="viewProduct(slotProps.data)" />
                        <Button v-if="!isLoading" icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editProduct(slotProps.data)" />
                        <Button v-if="!isLoading" icon="pi pi-trash" class="p-button-rounded p-button-warning" @click="confirmDeleteProduct(slotProps.data)" />
                    </template>
                </Column>
            </DataTable>
        </div>

        <Dialog v-model:visible="productDialog" :style="{width: '450px'}" header="Produto" :modal="true" class="p-fluid">
            <div class="field">
                <label for="codProduto">Código</label>
                <InputText id="codProduto" v-model.trim="product.codProduto" required="true" autofocus :class="{'p-invalid': submitted && !product.codigo}" />
                <small class="p-error" v-if="submitted && !product.codProduto">O código é obrigatório.</small>
            </div>

            <div class="field">
                <label for="nomeProduto">Nome</label>
                <InputText id="nomeProduto" v-model.trim="product.nomeProduto" required="true" :class="{'p-invalid': submitted && !product.nome}" />
                <small class="p-error" v-if="submitted && !product.nomeProduto">O nome é obrigatório.</small>
            </div>
            
            <div class="field">
                <label for="descricaoProduto">Descrição</label>
                <Textarea id="descricaoProduto" v-model.trim="product.descricaoProduto" required="true" />
            </div>

            <div class="field">
                <label for="precoProduto">Preço</label>
                <InputNumber id="precoProduto" v-model="product.precoProduto" mode="currency" currency="BRL" locale="pt-BR" required="true" :class="{'p-invalid': submitted && !product.precoProduto}" showButtons :step="0.25" :min="0" />
                <small class="p-error" v-if="submitted && product.precoProduto <= 0">O preço deve ser maior que 0.</small>
            </div>

            <div class="field">
                <label for="quantidadeProduto">Quantidade</label>
                <InputNumber id="quantidadeProduto" v-model="product.quantidadeProduto" required="true" :class="{'p-invalid': submitted && !product.quantidadeProduto}" showButtons :min="0" />
                <small class="p-error" v-if="submitted && product.quantidadeProduto < 0">A quantidade deve ser maior ou igual a 0.</small>
            </div>

            <template #footer>
                <Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
                <Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="saveProduct" />
            </template>
        </Dialog>

        <!-- caixa de dialogo para visualizar detalhadamente um produto -->
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
                <label for="descricaoProduto">{{product.descricaoProduto || "\"Sem descrição\""}}</label>
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

        <Dialog v-model:visible="deleteProductDialog" :style="{width: '450px'}" header="Exclusão" :modal="true">
            <div class="confirmation-content">
                <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                <span v-if="product">Tem certeza que deseja excluir "{{this.product.nomeProduto}}"?</span>
            </div>
            <template #footer>
                <Button label="Não" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false"/>
                <Button label="Sim" icon="pi pi-check" class="p-button-text" @click="deleteProduct" />
            </template>
        </Dialog>
	</div>
</template>

<script>
import api from "../services/api.ts";
import { FilterMatchMode } from 'primevue/api';
import { useToast } from "primevue/usetoast";

export default {
    data() {
        return {
            products: null,
            productDialog: false,
            showProduct: false,
            deleteProductDialog: false,
            product: {},
            isLoading: false,
            selectedProducts: null,
            filters: {},
            submitted: false,
            statuses: [
				{label: 'INSTOCK', value: 'instock'},
				{label: 'OUTOFSTOCK', value: 'outofstock'}
            ]
        }
    },
    created() {
        this.initFilters();
    },
    mounted() {
        this.getProducts();
        // this.productService.getProducts().then(data => this.products = data);
    },
    methods: {
        getProducts(){
            this.isLoading = true;
            setTimeout(() => {
                api.get("/", {headers: {'Access-Control-Allow-Credentials': "*"} }).then(response => {this.products = response.data; this.isLoading = false}).catch(err => {console.log(err); this.isLoading = false;})
            }, 1000);
        },
        formatCurrency(value) {
            if(value)
				return value.toLocaleString('pt-BR', {style: 'currency', currency: 'BRL'});
            var zero = 0;
            return zero.toLocaleString('pt-BR', {style: 'currency', currency: 'BRL'});
        },
        openNew() {
            this.product = {};
            this.submitted = false;
            this.productDialog = true;
        },
        hideDialog() {
            this.productDialog = false;
            this.submitted = false;
        },
        saveProduct() {
            this.submitted = true;
			if (!this.product.id) {
                let obj = {codProduto: this.product.codProduto, nomeProduto: this.product.nomeProduto, precoProduto: this.product.precoProduto, quantidadeProduto: this.product.quantidadeProduto, descricaoProduto: this.product.descricaoProduto};
                api.post("/", obj)
                .then(() => {this.productDialog = false; this.product = {}; this.getProducts();})
                .catch(err => {alert(err)});            
            }else{
                let obj = {codProduto: this.product.codProduto, nomeProduto: this.product.nomeProduto, precoProduto: this.product.precoProduto, quantidadeProduto: this.product.quantidadeProduto, descricaoProduto: this.product.descricaoProduto};
                api.put(`/${this.product.id}`, obj)
                .then(() => {this.productDialog = false; this.product = {}; this.getProducts();})
                .catch(err => {alert(err)});
            }
        },
        editProduct(product) {
            this.product = {...product};
            this.productDialog = true;
        },
        viewProduct(product){
            this.product = {...product};
            this.showProduct = true;
        },
        confirmDeleteProduct(product) {
            this.product = product;
            this.deleteProductDialog = true;
        },
        deleteProduct() {
            console.log(this.product)
            if(this.product.id){
                api.delete(`/${this.product.id}`)
                .then(() => {this.product = {}; this.getProducts();})
                .catch(err => alert(err));
            }
            this.deleteProductDialog = false;
            this.product = {};
        },
        initFilters() {
            this.filters = {
                'global': {value: null, matchMode: FilterMatchMode.CONTAINS},
            }
        }
    }
}
</script>

<style lang="scss" scoped>
.table-header {
    display: flex;
    align-items: center;
    justify-content: space-between;

    @media screen and (max-width: 960px) {
        align-items: start;
	}
}

.product-image {
    width: 50px;
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
}

.p-dialog .product-image {
    width: 50px;
    margin: 0 auto 2rem auto;
    display: block;
}

.confirmation-content {
    display: flex;
    align-items: center;
    justify-content: center;
}
@media screen and (max-width: 960px) {
	::v-deep(.p-toolbar) {
		flex-wrap: wrap;
        
		.p-button {
            margin-bottom: 0.25rem;
        }
	}
}
</style>

