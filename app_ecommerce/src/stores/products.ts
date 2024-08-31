import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore } from './snackbar'
import { convertError } from '@/utils/error-converter'

export enum SnackbarType {
  SUCCESS,
  ERROR,
  WARNING,
  MESSAGE
}

export type CreationPayload = {
  categoria: number
  nombre: string,
  stock: number,
  precio: number,
  imagenes: Array<File>
}

export type UpdatePayload = {
  id: number,
  nombre: string,
  padre?: number
}
 
export type Product = {
  id: number,
  categoria: number
  nombre: string,
  stock: number,
  precio: number
}

export const useProductStore = defineStore('products', {
  state: () => ({
    products: new Array<Product>,
    loading: false,
    error: false
  }),
  actions: {
    async fetchAllProducts() {
      this.loading = true
      
      const { data, error } = await useCustomFetch<any>(
        'api/productos/',
        {
          method: 'GET',
        }
      )

      
      this.products = data.value.data;

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: convertError(error.value),
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
      this.loading = true
      
      const { data, error } = await useCustomFetch<any>(
        `api/producto/${product_id}`,
        {
          method: 'GET',
        }
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: convertError(error.value),
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
    async createProduct(payload: CreationPayload) {
      const {categoria, nombre, stock, precio, imagenes} = payload
      this.loading = true
      
      const formData = new FormData();

      imagenes.forEach(imagen => {
        formData.append("files", imagen)
      });

      formData.append("categoria", categoria as unknown as string);
      formData.append("nombre", nombre);
      formData.append("stock", stock as unknown as string);
      formData.append("precio", precio as unknown as string);

      const { data, error } = await useCustomFetch<any>(
        'api/categoria/private/crearProducto',
        {
          method: 'POST',
          body: formData
        }
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: convertError(error.value),
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

      const { data, error } = await useCustomFetch<any>(
        'api/categoria/private/actualizarProducto',
        {
          method: 'PATCH',
          body: JSON.stringify(payload)
        }
      )

      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: convertError(error.value),
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
