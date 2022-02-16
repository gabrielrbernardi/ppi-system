<template>
  <Navbar/>
  <router-view class="content m-5"/>
</template>

<script>
  import Navbar from './components/Navbar.vue'; 
  import FormPromocoes from "./components/FormPromocoes"; 
  export default {
    components: {
      Navbar,
      FormPromocoes
    }
  }
</script>

<style>

</style>
