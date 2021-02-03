<template>
    <div class="main_div">
    <div class="title">Login</div>
    <div class="social_icons">
      <a href="#"><em class="fab fa-facebook-f"></em> <span>Facebook</span></a>
      <a href="#"><em class="fab fa-google"></em><span>Google</span></a>
    </div>
    <form action="#" @submit.prevent="onSubmit">
      <div class="input_box">
        <input type="text" v-model="email" placeholder="Email" required>
        <div class="icon"><em class="fas fa-user"></em></div>
      </div>
      <div class="input_box">
        <input type="password" v-model="password" placeholder="Password" required>
        <div class="icon"><em class="fas fa-lock"></em></div>
      </div>
        <p class="forgot-password text-right mt-2 mb-4">
            <router-link to="/forgot-password">Forgot password?</router-link>
        </p>
      <div class="input_box button">
        <input type="submit" value="Login">
      </div>
      <div class="sign_up">
        Not a member? <router-link to="/sign-up">Sign Up</router-link>
      </div>
    </form>
  </div>
</template>

<script >
    export default {
      
        data() {
            return {             
                email:'',
                password:''                                        
            }
        },
      methods: {
      onSubmit () {
        const formData = {
          email: this.email,
          password: this.password,
        }
        console.log(formData)
        this.$store.dispatch('login', {email: formData.email, password: formData.password})
      }         
      }  
    }
</script>
