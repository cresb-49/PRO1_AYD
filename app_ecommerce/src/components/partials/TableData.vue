<template>
  <v-table>
    <thead>
      <tr>
        <th v-for="column in columns" :key="column.propertyName" class="text-left">
          {{ column.name }}
        </th>
        <th v-if="actions.length > 0">Acciones</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="row in data" :key="row[data_key as unknown as number]">
        <td v-for="column in columns" :key="column.propertyName">
          {{ column.preappend ? column.preappend : '' }}
          {{ accessRowByPropertyName(row, column.propertyName, column.type) }}
          {{ column.append ? column.append : '' }}
        </td>
        <td v-if="actions.length > 0">
          <v-btn
            style="margin: 3px;"
            v-for="action in actions"
            :key="action.name"
            :to="action.path ? replaceParameterPath(row, action.path, action.property) : undefined"
            @click="action.onClick ? action.onClick(row[data_key as unknown as number]) : () => {}"
          >
            {{ action.name }}
          </v-btn>
        </td>
      </tr>
    </tbody>
  </v-table>
</template>
<script setup lang="ts">
type Action = {
  name: string
  path?: string
  onClick?: (entry_id: number) => void
  property?: string
}
type Column = {
  name: string
  propertyName: string
  preappend?: string
  append?: string
  type?: string
}

export interface keyable {
  [key: number]: any
}
const props = defineProps({
  columns: { type: Array<Column>, required: true },
  data: { type: Array<keyable>, required: true },
  data_key: { type: String, required: true },
  actions: { type: Array<Action>, default: [] }
})

function accessRowByPropertyName(row: any, propertyName: string, type: string | undefined) {
  let properties = propertyName.split('.')
  properties.forEach((actual_property) => {
    row = row[actual_property]
  })
  if (type === 'date') {
    //asignamos el formado DD/MM/YYYY
    let date = new Date(row).toLocaleDateString()
    return date
  } else if (type === 'currency') {
    //asignamos el formato de moneda
    return new Intl.NumberFormat('es-GT', { style: 'currency', currency: 'GTQ' }).format(row)
  } else {
    return row
  }
}

//Reeplaza parametros del path con propiedades del objeto
function replaceParameterPath(row: any, path: string, property: string | undefined) {
  //Recorre todas las propiedades de la data
  if (props.data.length === 0) {
    return path
  } else {
    //Si existe en el action el valor property
    //buscamos el valor de la propiedad en la row
    if (property) {
      let properties = property.split('.')
      properties.forEach((actual_property) => {
        row = row[actual_property]
      })
      // En el path vamos a buscar el valor de la propiedad con la sintaxis :propiedad
      // y lo vamos a reemplazar por el valor de la propiedad en la row
      path = path.replace(`:${property}`, row)
    } else {
      Object.keys(row).forEach((propiedad) => {
        path = path.replace(`:${propiedad}`, row[propiedad])
      })
    }
    return path
  }
}
</script>
