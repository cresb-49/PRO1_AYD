import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
import { convertError } from '@/utils/error-converter'

export type CreationPayload = {
  categoria: number
  nombre: string,
  stock: number,
  precio: number,
  impuesto: number,
  imagenes: Array<File>
}

export type UpdatePayload = {
  id: number,
  nombre: string,
  categoria: number,
  stock: number,
  precio: number,
  habilitado: boolean
}
 
export type Product = {
  id: number,
  categoria: number
  nombre: string,
  stock: number,
  precio: number,
  imagenesUrls?: string[]
}

export const useProductStore = defineStore('products', {
  state: () => ({
    products: new Array<Product>,
    productsLowStock: new Array<Product>,
    loading: false,
    loadingProduct: false,
    error: false
  }),
  actions: {
    async fetchAllProducts() {
      this.loading = true
      
      const { data, error } = await useCustomFetch<any>(
        'api/productos/public/getProductos',
        {
          method: 'GET',
        }
      )
      
      this.products = data.value.data;

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      // Success
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async fetchWithLowStock() {
      this.loading = true
      
      const { data, error } = await useCustomFetch<any>(
        'api/producto/protected/getStockBajo',
        {
          method: 'GET',
        }
      )
      
      this.productsLowStock = data.value.data;

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      // Success
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async fetchProduct(product_id: number) {
      this.loadingProduct = true
      
      const { data, error } = await useCustomFetch<any>(
        `api/producto/public/getProducto/${product_id}`,
        {
          method: 'GET',
        }
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      // Success
      // Return the data and error
      this.loadingProduct = false
      return { data, error: false }
    },
    async addStockProduct(product_id: number) {
      this.loadingProduct = true
      
      const { data, error } = await useCustomFetch<any>(
        `api/producto/public/getProducto/${product_id}`,
        {
          method: 'GET',
        }
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      // Success
      // Return the data and error
      this.loadingProduct = false
      return { data, error: false }
    },
    async createProduct(payload: CreationPayload) {
      const {categoria, nombre, stock, precio, impuesto, imagenes} = payload
      this.loading = true
      
      const formData = new FormData();

      imagenes.forEach(imagen => {
        formData.append("files", imagen)
      });

      formData.append("categoria", categoria as unknown as string);
      formData.append("nombre", nombre);
      formData.append("stock", stock as unknown as string);
      formData.append("precio", precio as unknown as string);
      formData.append("porcentajeImpuesto", impuesto as unknown as string);
      formData.append("habilitado", "true");
      
      const { data, error } = await useCustomFetch<any>(
        'api/producto/protected/crearProducto',
        {
          method: 'POST',
          body: formData
        },
        true
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      // Success
      // Show success snackbar
      useSnackbarStore().showSnackbar({
        title: 'Creacion Exitosa',
        message: `Producto Creado Exitosamente`,
        type: SnackbarType.SUCCESS
      })
      
      await this.fetchAllProducts()
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async updateProduct(payload: UpdatePayload) {
      this.loading = true
      payload.habilitado = true

      const { data, error } = await useCustomFetch<any>(
        'api/producto/protected/actualizarProducto',
        {
          method: 'PATCH',
          body: JSON.stringify(payload)
        }
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loading = false
        return { data, error: error.value }
      }
      // Success
      // Show success snackbar
      useSnackbarStore().showSnackbar({
        title: 'Actualizacion Exitosa',
        message: `Producto Actualizado Exitosamente`,
        type: SnackbarType.SUCCESS
      })
      
      await this.fetchAllProducts()
      // Return the data and error
      this.loading = false
      return { data, error: false }
    }
  }
})
