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

export type ProductSaleReport = {
  cantidad: number
  precio: string
  producto: string
  descripcion: string
  impuesto: string
  total: string
}

export type SaleReport = {
  total: number
  noVentas: number
  totalPagoEntrega: number
  productosVendidos: ProductSaleReport[]
  fecha1: string
  fecha2: string
  totalImpuestoPagado: number
}

export type ClientReport = {
  id: number
  nombres: string
  apellidos: string
  numeroPedidos: number
  valorTotalCompras: number
  ticketPromedio: number
}

export type ClientsReport = {
  clienteFrecuentes: ClientReport[]
  fecha1: string
  fecha2: string
}

export function getTodayDateInUTC6() {
  const today = new Date()
  // Obtenemos la fecha en formato YYYY-MM-DD
  const year = today.getFullYear()
  const month = String(today.getMonth() + 1).padStart(2, '0') // Meses de 0-11
  const day = String(today.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
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
