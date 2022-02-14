<template>
  <Navbar/>
  <router-view class="content m-5"/>
</template>

<script>
  import Navbar from './components/Navbar.vue';  
  export default {
    components: {
      Navbar
    }
  }
</script>

<style>

</style>
