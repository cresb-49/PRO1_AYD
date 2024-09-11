import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
//import { convertError } from '@/utils/error-converter'
//import { useProductStore, type Product } from './products'

export type Order = {
    id: number,
    direccion: string,
    venta: {
      id: number,
      datosFacturacion: {
        nit: string,
        nombre: string
      }
    },
    estadoEnvio: {
      nombre: string
    }
  }
  
  export const useOrderStore = defineStore('orders', {
    state: () => ({
      orders: [] as Order[],
      loading: false,
      error: false,
    }),
    
    actions: {
      async fetchAllOrders() {
        this.loading = true;
        
        const { data, error } = await useCustomFetch<any>(
          '/api/envio/protected/getEnvios',
          {
            method: 'GET',
          }
        );
        
        // Error Handling
        if (error.value) {
          useSnackbarStore().showSnackbar({
            title: 'Error',
            message: error.value,
            type: SnackbarType.ERROR,
          });
          this.loading = false;
          return { data, error: error.value };
        }
  
        // Success
        this.orders = data.value;
        this.loading = false;
        return { data, error: false };
      },
  
      async completeOrder(orderId: number) {
        // Implementar AQUI la lógica para completar un pedido
        // por ejemplo, una request PATCH o POST a la ruta "'/api/envio/protected/cambiarEstadoEnvio/{id}'"
      }
    }
  });