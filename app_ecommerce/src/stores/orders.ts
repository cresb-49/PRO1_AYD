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
        // Agregamos logs para ver lo que devuelve la solicitud
        console.log('Response from /api/envio/protected/getEnvios:', data.value);
        
        // Verifica que 'data' existe y contiene pedidos
        if (data && data.value && data.value.data) {
            this.orders = data.value.data; // Almacena solo el array de 'data'
            console.log('Stored orders:', this.orders); // Verifica el valor de orders en el store
        } else {
            console.log('No data returned or invalid structure');
      }
    //console.log('First order (if exists):', data.value.length > 0 ? data.value[0] : 'No orders');


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
  
        // Success - Guardamos la respuesta en el arreglo de órdenes y mostramos en consola
        // this.orders = data.value;
        // console.log('Stored orders:', this.orders);
        this.loading = false;
        // return { data, error: false };
      },
  
      async viewOrder(orderId: number) {
        // Implementar AQUI la lógica para ver los detalles de un pedido
        // por ejemplo, una request PATCH o POST a la ruta "'/api/envio/protected/getEnvio/{id}'"
      }
    }
  });