import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'

export type ConfigResponse = {
  nombreTienda: string,
  imagenTiendaString: string,
  precioPagoContraEntrega: string,
  costoEnvio: string,
  direccionEmpresa: string
}

export type UpdatePayload = {
  nombreTienda: string,
  precioPagoContraEntrega: string,
  costoEnvio: string,
  direccionEmpresa: string
}

export const useConfigsStore = defineStore('configs', {
  state: () => ({
    name: 'E-Commerce',
    logo: undefined as string | undefined,
    deliveryCost: 15 as number,
    cod: 0 as number,
    address: '' as string
  }),
  persist: true,
  actions: {
    async initCommerce() {
      const { data, error } = await useCustomFetch<any>(
        'api/tienda_config/public/getTiendaConfig',
        {
          method: 'GET',
        }
      )

      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        return { data, error: error.value }
      }
      
      const config = data.value.data as ConfigResponse
      
      this.name = config.nombreTienda
      this.logo = config.imagenTiendaString
      this.cod = config.precioPagoContraEntrega as unknown as number
      this.deliveryCost = config.costoEnvio as unknown as number
      this.address = config.direccionEmpresa
    },
    async updateConfig(payload: UpdatePayload){
      const { data, error } = await useCustomFetch<any>(
        'api/tienda_config/private/actualizarConfiguracion',
        {
          method: 'PATCH',
          body: JSON.stringify(payload)
        }
      )

      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        return { data, error: error.value }
      }

      useSnackbarStore().showSnackbar({
        title: 'Actualizacion Exitosa',
        message: `Actualizacion de la Configuracion Exitosa`,
        type: SnackbarType.SUCCESS
      })
      
      this.initCommerce()
    },
    async updateStoreImage(image: File){
      const formData = new FormData();

      formData.append("file", image)

      const { data, error } = await useCustomFetch<any>(
        'api/tienda_config/private/actualizarImagenDeLaTienda',
        {
          method: 'POST',
          body: formData
        },
        true
      )

      if (error.value) {
        useSnackbarStore().showSnackbar({
          title: 'Error',
          message: error.value,
          type: SnackbarType.ERROR
        })
        return { data, error: error.value }
      }

      useSnackbarStore().showSnackbar({
        title: 'Actualizacion Exitosa',
        message: `Actualizacion de Imagen Exitosa`,
        type: SnackbarType.SUCCESS
      })
      
      this.initCommerce()
    }
  }
})
