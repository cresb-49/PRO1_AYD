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
                    {{ accessRowByPropertyName(row, column.propertyName) }}
                </td>
                <td v-if="actions.length > 0">
                    <v-btn v-for="action in actions" 
                    :key="action.name" 
                    :to="action.path ? replaceParameterPath(row, action.path) : undefined"
                        @click="action.onClick ? action.onClick(row[data_key as unknown as number]) : () => { }">
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
}
type Column = {
    name: string,
    propertyName: string
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

function accessRowByPropertyName(row: any, propertyName: string) {
    let properties = propertyName.split('.');
    properties.forEach(actual_property => {
        row = row[actual_property]
    });
    return row
}

//Reeplaza parametros del path con propiedades del objeto
function replaceParameterPath(row: any, path: string) {
    //Recorre todas las propiedades de la data
    if (props.data.length === 0) {
        return path;
    } else {
        Object.keys(row).forEach(propiedad => {
            path = path.replace(`:${propiedad}`, row[propiedad]);
        });
        return path
    }
}
</script>
