<template>
    <v-table>
        <thead>
            <tr>
                <th v-for="(header, i) in columns" :key="i" class="text-left">
                    {{ header }}
                </th>
                <th v-if="actions.length > 0">Acciones</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="row in data" :key="row[data_key as unknown as number]">
                <td v-for="(cell, n) in row" :key="n">
                    {{ cell }}
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

export interface keyable {
    [key: number]: any
}
const props = defineProps({
    columns: { type: Array<String>, required: true },
    data: { type: Array<keyable>, required: true },
    data_key: { type: String, required: true },
    actions: { type: Array<Action>, default: [] }
})

//Reeplaza parametros del path con propiedades del objeto
function replaceParameterPath(row: any, path: string) {
    //Recorre todas las propiedades de la data
    if (props.data.length === 0) {
        return path;
    } else {
        const prueba = props.data[0];
        Object.keys(prueba).forEach(propiedad => {
            console.log('propiedad');
            console.log(propiedad);
            path = path.replace(`:${propiedad}`, row[propiedad]);
        });
        return path
    }
}
</script>
