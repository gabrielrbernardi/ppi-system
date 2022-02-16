export default class ProductService {
	getProducts() {
		return fetch('demo/content.json').then(res => res.json()).then(d => d.data);
    }
	
	async getProductsAlteracao() {
		console.log(1);
		return fetch('demo/product.json').then(res => res.json()).then(d => d.data); 

    }
}