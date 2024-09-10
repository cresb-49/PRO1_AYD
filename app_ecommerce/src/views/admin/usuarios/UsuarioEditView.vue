<template>
  <section class="login-page">
    <v-row justify="center" no-gutters>
      <v-col cols="11" sm="8" md="8" lg="5" xl="4">
        <EditUserForm
          :loading="loading"
          :error="error"
          @editUser="editUser($event)"
          :user="usuario"
        />
      </v-col>
    </v-row>
  </section>
</template>
<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useRoute, useRouter } from 'vue-router'
import { SnackbarType, useSnackbarStore } from '@/stores/snackbar'
import { onMounted, ref } from 'vue'
import EditUserForm from '@/components/forms/accounts/EditUserForm.vue'
import { useUserStore, type UpdateUserPayload, type UserByPermiso } from '@/stores/users'

const userStore = useUserStore()
const { loading, error } = storeToRefs(userStore)
const { fetchUser, updateUser } = userStore
const route = useRoute()
const router = useRouter()

const usuario = ref({} as UserByPermiso)

async function editUser(payload: UpdateUserPayload) {
  await updateUser(payload, false)
}

onMounted(() => {
  fetchUser(route.params.id as unknown as number)
    .then((r) => r.data.value.data as UserByPermiso)
    .then((user) => {
      usuario.value = user
    })
})
</script>

<style lang="scss" scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
</style>
