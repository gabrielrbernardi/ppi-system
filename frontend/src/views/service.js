export default class ProductService {
	getProducts() {
		// get do axios futuramente
		return fetch('demo/content.json').then(res => res.json()).then(d => d.data);
    }
	
	async getProductsAlteracao() {
		// Get do axios futuramente
		return fetch('demo/product.json').then(res => res.json()).then(d => d.data); 

    }
}