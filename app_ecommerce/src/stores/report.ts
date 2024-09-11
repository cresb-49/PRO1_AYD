import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
import { useCustomFetch } from '@/composables/useCustomFetch'
import { FileWatcherEventKind } from 'typescript'

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

export type ReportPayload = {
  fecha1?: string
  fecha2?: string
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
    },
    async fetchReportVentas(payload: ReportPayload) {
      this.loading = true
      const { data, error } = await useCustomFetch<any>(
        `api/reporte/protected/generarReporteVentas`,
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
      this.loading = false
      return { data, error: false }
    },
    async fetchReportePedidos(payload: ReportPayload) {
      this.loading = true
      const { data, error } = await useCustomFetch<any>(
        `api/reporte/protected/generarReportePedidos`,
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
      this.loading = false
      return { data, error: false }
    },
    async fetchReporteInventario(payload: ReportPayload) {
      this.loading = true
      const { data, error } = await useCustomFetch<any>(
        `api/reporte/protected/generarReporteInventario`,
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
      this.loading = false
      return { data, error: false }
    },
    async fecthReporteClientesFrecuentes(payload: ReportPayload) {
      this.loading = true
      const { data, error } = await useCustomFetch<any>(
        `api/reporte/protected/generarReporteClientesFrecuentes`,
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
      this.loading = false
      return { data, error: false }
    }
  }
})
