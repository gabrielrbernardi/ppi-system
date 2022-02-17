<template>
    <!--<div id="nav">
        <router-link to="/">Home</router-link> |
        <router-link to="/about">About</router-link>
    </div>
    -->
    <Menubar :model="items" />

</template>

<script>
    import router from '@/router'
    export default{
        name: "Navbar",
        data() {
            return {
                items: [
                    {
                        label:'Home',
                        icon:'pi pi-fw pi-home',
                        command: () => {
                            router.push({ path: '/' })
                        },
                    },
                    
                    {
                        label:'Promoções',
                        icon:'pi pi-fw pi-shopping-bag',
                        command: () => {
                            router.push({ path: '/promocoes' })
                        },
                    },

                    {
                        label:'CRUD',
                        icon:'pi pi-fw pi-shopping-bag',
                        command: () => {
                            router.push({ path: '/promocoesadmin' })
                        },
                    },
                ]
            }
        }
    }
</script>

<style scoped>
    
</style>