import { useCustomFetch } from '@/composables/useCustomFetch'
import { defineStore } from 'pinia'
import { useSnackbarStore, SnackbarType } from './snackbar'
import { convertError } from '@/utils/error-converter'
import { useProductStore, type Product } from './products'

export type CreationPayload = {
  nombre: string,
  padre?: number
}

export type UpdatePayload = {
  id: number,
  nombre: string,
  padre?: number
}
 
export type Category = {
  id: number,
  nombre: string,
  padre?: number
}

export const useCategoryStore = defineStore('categories', {
  state: () => ({
    categories: new Array<Category>,
    loading: false,
    error: false
  }),
  actions: {
    async fetchAllCategories() {
      this.loading = true
      
      const { data, error } = await useCustomFetch<any>(
        'api/categoria/public/getCategorias',
        {
          method: 'GET',
        }
      )

      
      this.categories = data.value.data;

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
      console.log('termino esto')
      return { data, error: false }
    },
    async fetchCategory(category_id: number) {
      this.loading = true
      
      const { data, error } = await useCustomFetch<any>(
        `api/categoria/public/${category_id}`,
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
      this.loading = false
      return { data, error: false }
    },
    async createCategory(payload: CreationPayload) {
      console.log('payload actual');
      console.log(payload);
      this.loading = true

      const { data, error } = await useCustomFetch<any>(
        'api/categoria/protected/crearCategoria',
        {
          method: 'POST',
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
        title: 'Creacion Exitosa',
        message: `Categoria Creada Exitosamente`,
        type: SnackbarType.SUCCESS
      })
      
      await this.fetchAllCategories()
      // Return the data and error
      this.loading = false
      return { data, error: false }
    },
    async updateCategory(payload: UpdatePayload) {
      this.loading = true

      const { data, error } = await useCustomFetch<any>(
        'api/categoria/protected/updateCategoria',
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
        message: `Categoria Actualizada Exitosamente`,
        type: SnackbarType.SUCCESS
      })
      
      await this.fetchAllCategories()
      // Return the data and error
      this.loading = false
      return { data, error: false }
    }
  },
  getters: {
    categoriesHomepage: async (state) => {
      console.log('categorias aqui')
      console.log(state.categories)
      if (state.categories.length === 0) {
        return []
      }
      const categoriesLimit = state.categories.slice(0, 12)
      const categoriesShow = [];
      for (let index = 0; index < categoriesLimit.length; index++) {
        const category = categoriesLimit[index];
        const {data, error} = await useProductStore().fetchProductsByCategory(category.id);
        
        if (error.value) {
          categoriesShow.push(
            {id: category.id, name: category.nombre, path: `/categoria/${category.id}`, image: undefined}
          )
          continue;
        }
        
        const productos = data.value.data as Product[]
        const primerProducto = productos ? (productos.length > 0 ? productos[0] : undefined) : undefined
        if (primerProducto !== undefined) {
          const {data, error} = await useProductStore().fetchProduct(primerProducto.id)

          if (error.value) {
            categoriesShow.push(
              {id: category.id, name: category.nombre, path: `/categoria/${category.id}`, image: undefined}
            )
            continue;
          }

          const productoInfo = data.value.data as Product
          categoriesShow.push(
            {id: category.id, name: category.nombre, path: `/categoria/${category.id}`, image: productoInfo ? productoInfo.imagenesUrls![0] : undefined}
          )
        } else {
          categoriesShow.push(
            {id: category.id, name: category.nombre, path: `/categoria/${category.id}`, image: undefined}
          )
        }
      }
      return categoriesShow
    }
  }
})
