export default class ProductService {
	getProducts() {
		return fetch('demo/content.json').then(res => res.json()).then(d => d.data);
    }
	
	getProductsAlteracao() {
		return fetch('demo/data/products.json').then(res => res.json()).then(d => d.data);
    }
}