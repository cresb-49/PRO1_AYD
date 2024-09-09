import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
import { useProductStore, type Product } from './products';
import { useCustomFetch } from '@/composables/useCustomFetch';
import { useCookies } from 'vue3-cookies';

export type CartProduct = {
  id: number,
  quantity: number
}

export type CartPayload = {
  id: number,
  cantidad: number
}

export type CartInfo = {
  id: number,
  quantity: number,
  info?: Product
}

export type BuyPayload = {
  idComprador: number,
  idCompradador?: number,
  productos?: CartPayload[],
  consumidorFinal: boolean,
  retiroEnTienda: boolean,
  pagoContraEntrega: boolean,
  direccion: string
}

function removeAtIndex<CartProduct>(array: CartProduct[], index: number): CartProduct[] {
  if (index < 0 || index >= array.length) {
      throw new Error("Index out of bounds");
  }

  return [...array.slice(0, index), ...array.slice(index + 1)];
}

export const useCartStore = defineStore('cart', {
  state: () => ({
    cart: [] as CartProduct[],
    productsInfo: [] as (Product|null)[],
    totalProducts: 0,
    totalTax: 0,
    loading: false
  }),
  persist: true,
  actions: {
    async fetchProductsCart() {
      const {fetchProduct} = useProductStore()
      this.totalProducts = 0
      this.totalTax = 0
      this.productsInfo = []
      for (const productCart of this.cart) {
        const {data, error} = await fetchProduct(productCart.id);
        if (error.value) {
          this.productsInfo.push(null);
          continue
        }
        
        const producto = data.value.data as Product
        this.totalProducts += producto.precio
        this.totalTax += producto.precio * (producto.porcentajeImpuesto / 100)
        this.productsInfo.push(producto);
      }
    },
    addProduct(product: CartProduct) {
      //Revisa si el producto ya ha sido agregado al carrito
      if (this.isProductInCart(product.id)) {
        //Si existe el producto se avisa que no se puede agregar ese producto de nuevo
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: 'El producto ya esta en el carrito',
          type: SnackbarType.ERROR
        })
        return false;
      }
      //Caso contrario se introduce el producto al carrito
      this.cart.push(product)
      useSnackbarStore().showSnackbar({
        title: 'Producto Agregado',
        message: 'El producto ha sido agregado al carrito!',
        type: SnackbarType.SUCCESS
      })
      return true
    },
    removeProduct(product_id: number) {
      //Revisa si el producto ya ha sido agregado al carrito
      if (this.isProductInCart(product_id)) {
        //Si existe el producto se elimina
        const index = this.cart.findIndex((product) => {return product.id == product_id})
        this.cart = removeAtIndex(this.cart, index)
        useSnackbarStore().showSnackbar({
          title: 'Producto eliminado',
          message: 'El producto se ha eliminado del carrito',
          type: SnackbarType.SUCCESS
        })
        return true;
      }
      //Caso contrario se avisa que el producto no esta en el carrito
      useSnackbarStore().showSnackbar({
        title: 'Error',
        message: 'El producto no esta en el carrito',
        type: SnackbarType.ERROR
      })
      return false;
    },
    isProductInCart(product_id: number) {
      for (const product of this.cart) {
        if (product.id == product_id) {
          return true;
        }
      };
      return false;
    },
    async buyProducts(payload: BuyPayload) {
      payload.idCompradador = payload.idComprador
      payload.productos = this.cart.map(product => {return {id: product.id, cantidad: product.quantity}})
      
      if (payload.direccion === "") {
        payload.direccion = "."
      }
      
      console.log('aqui estamos')
      console.log(payload)

      const { data, error } = await useCustomFetch<any>(
        'api/facturacion/cliente/generarCompra',
        {
          method: 'POST',
          body: JSON.stringify(payload)
        }
      )

      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      
      useSnackbarStore().showSnackbar({
        title: 'Compra Exitosa',
        message: `La compra se ha realizado exitosamente!`,
        type: SnackbarType.SUCCESS
      })
      
      this.$reset()
      // Return the data and error
      this.loading = false
      return { data, error: false }
    }
  },
  getters: {
    productAmount: (state) => state.cart ? state.cart.keys.length : 0,
    products: (state) => {
      if (!state.cart) {
        return []
      }
      const products: Array<CartInfo> = []
      for (let index = 0; index < state.cart.length; index++) {
        const productCart = state.cart[index];
        const productInfo = state.productsInfo[index];
        products.push({
          id: productCart.id,
          quantity: productCart.quantity,
          info: productInfo ?? undefined
        });
      }
      return products;
    }
  }
})
