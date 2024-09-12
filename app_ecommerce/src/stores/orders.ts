import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
//import { convertError } from '@/utils/error-converter'
//import { useProductStore, type Product } from './products'

export type Order = {
  id: number
  direccion: string
  venta: {
    id: number
    datosFacturacion: {
      nit: string
      nombre: string
    }
  }
  estadoEnvio: {
    nombre: string
  }
}

export const useOrderStore = defineStore('orders', {
  state: () => ({
    orders: [] as Order[],
    loading: false,
    error: false
  }),

  actions: {
    async fetchAllOrders() {
      this.loading = true

      const { data, error } = await useCustomFetch<any>('/api/envio/protected/getEnvios', {
        method: 'GET'
      })

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

      this.orders = data.value.data // Almacena solo el array de 'data'

      // Success - Guardamos la respuesta en el arreglo de órdenes y mostramos en consola
      // this.orders = data.value;
      // console.log('Stored orders:', this.orders);
      this.loading = false
      // return { data, error: false };
    },
    async fetchOrder(order_id: number) {
      this.loading = true

      const { data, error } = await useCustomFetch<any>(
        `api/envio/protected/getEnvio/${order_id}`,
        {
          method: 'GET'
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
      this.loading = false

      return { data, error: false }
    },
    async deliverOrder(order_id: number) {
      this.loading = true

      const { data, error } = await useCustomFetch<any>(
        `api/envio/protected/cambiarEstadoEnvio/${order_id}`,
        {
          method: 'PATCH'
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

      this.loading = false

      return { data, error: false }
    }
  }
})
