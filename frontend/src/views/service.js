export default class ProductService {
	getProducts() {
		// get do axios futuramente
		return fetch('demo/content.json').then(res => res.json()).then(d => d.data);
    }
	
	async getProductsAlteracao(prouct) {
		// Get do axios futuramente
		return fetch('demo/product.json').then(res => res.json()).then(d => d.data); 

    }

	async AddProductreceber(product) {
		/*
		return axios({
			method: 'POST',
			url: `nossoURLDEBACKEnd`,
			headers: { Authorization: `Bearer ${retornaJToken()}` },
			data: product
		});*/
    }

	async UpdateProduct(product) {
		/*
		return axios({
			method: 'PUT',
			url: `nossoURLDEBACKEnd/{Idproduct}`,
			headers: { Authorization: `Bearer ${retornaJToken()}` },
			data: product
		});*/

    }

	async DeleteProducts(listProducts) {
		/*
		return axios({
			method: 'DELETE',
			url: `nossoURLDEBACKEnd`,
			headers: { Authorization: `Bearer ${retornaJToken()}` },
			data: listProducts
		});*/
    }
}