<template>
  <div class="login">
    <h3 class="login-element">Login</h3>
    <span class="login-element p-float-label">
      <InputText id="username" type="text" v-model="user.username" />
      <label for="username">Username</label>
    </span>
    <span class="login-element p-float-label">
      <InputText id="password" type="password" v-model="user.password" ></InputText>
      <label for="password">Password</label>
    </span>

    <Button class="login-element" label="Submit" @click="handleLogin()"/>
<!--    <Button label="Register" @click="this.$router.push('/register')"/>-->
  </div>
</template>

<script>

import User from '@/model/user'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'

export default {
  name: 'Login',
  components: {
    InputText,
    Button
  },
  created () {
    if (this.$store.state.auth.status.loggedIn) {
      this.$router.push('/notanothertodo')
    }
  },
  data: () => ({
    user: new User('', '')
  }),
  methods: {
    handleLogin () {
      this.$store.dispatch('auth/login', this.user).then(
        () => {
          this.$router.push('/notanothertodo')
        },
        error => {
          this.loading = false
          this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString()
        }
      )
    }
  }
}
</script>

<style lang="scss" scoped>
.login{
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;

  .login-element{
    margin-bottom: 25px;
  }
}
</style>
