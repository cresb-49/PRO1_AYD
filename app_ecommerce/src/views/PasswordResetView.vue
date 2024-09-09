<template>
    <section class="login-page">
      <v-row justify="center" no-gutters>
          <v-col cols="11" sm="8" md="8" lg="5" xl="4">
          <PasswordResetForm
          @send-email="enviarEmail"
          />
        </v-col>
      </v-row>
    </section>
</template>

<!--Importar componentes hijos-->
<script setup lang="ts">
import PasswordResetForm from '@/components/forms/accounts/PasswordResetForm.vue';

function enviarEmail(params:{email:string}){
    const email = params.email;
}

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