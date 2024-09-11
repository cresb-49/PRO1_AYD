import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
import { useCustomFetch } from '@/composables/useCustomFetch'

export enum ReportType {
  VENTAS = 'reporteVentas',
  INVENTARIO = 'reporteInventario',
  CLIENTES_FRECUENTES = 'reporteClientesFrecuentes',
  PEDIDOS = 'reportePedidos'
}

export enum ReportExportType {
  PDF = 'pdf',
  EXCEL = 'excel',
  WORD = 'word'
}

export type ReportExportPayload = {
  fecha1?: string
  fecha2?: string
  tipoReporte: ReportType
  tipoExporte: ReportExportType
}

export const useReportStore = defineStore('report', {
  state: () => ({
    loading: false,
    error: false
  }),
  actions: {
    async fetchReport(payload: ReportExportPayload) {
      this.loading = true
      const { data, error } = await useCustomFetch<any>(`api/reporte/protected/exportar`, {
        method: 'POST',
        body: JSON.stringify(payload)
      })
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
