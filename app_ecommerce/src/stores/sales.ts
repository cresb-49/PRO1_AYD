import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
import { storeToRefs } from "pinia";
import { useRegularAuthStore } from '@/stores/regular-auth';
import { convertError } from '@/utils/error-converter'

export type BillingData = {
  id: number
  nit: string
  nombre: string
}

export type SaleProduct = {
  id: number
  nombre: string
  descripcion: string
  stock: number
  precio: number
  categoria: {
    id: number
    nombre: string
  }
  porcentajeImpuesto: number
  habilitado: boolean
}

export type SaleLine ={
  id:number,
  precio:number,
  cantidad:number,
  impuestoPagado:number,
}

export type Sale = {
  id:number
  valorTotal:number
  totalImpuestosPagados:number
  cuotaPagContraEntrega:number
  cantidadProductos:number
  lineaVentas:SaleLine[]
  datosFacturacion:BillingData
  createdAt:string
}

export const useSalesStore = defineStore('sales', {
  state: () => ({
    sales: new Array<Sale>(),
    sale: {} as Sale,
    loading: false,
    loadingSale: false,
    error: false
  }),
  actions: {
    async fetchAllSalesOfUser() {
      const {user} = storeToRefs(useRegularAuthStore())
      this.loading = true
      const { data, error } = await useCustomFetch<any>(`api/ventas/usuario/cliente/${user ? user.value!.id : 0}`, {
        method: 'GET'
      })
      this.sales = data.value.data as Sale[]
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
    async fetchSaleById(id: number) {
      this.loadingSale = true
      const { data, error } = await useCustomFetch<any>(`api/venta/private/all/${id}`, {
        method: 'GET'
      })
      this.sale = data.value.data as Sale
      // Error Handling
      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        this.loadingSale = false
        return { data, error: error.value }
      }
      // Success
      // Return the data and error
      this.loadingSale = false
      return { data, error: false }
    }
  }
})
