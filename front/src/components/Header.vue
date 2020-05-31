<template>
    <div>
        <b-navbar toggleable="lg" type="light" variant="light">
            <b-navbar-brand href="#">Petar's FlyCo</b-navbar-brand>
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
            <b-collapse id="nav-collapse" is-nav>
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                     <b-navbar-nav right>
                        <b-nav-item @click="goRegister" v-show="!login">Register</b-nav-item>
                         <b-nav-item @click="goLog"  v-show="!login">Log in</b-nav-item>
                         <b-nav-item @click="goLogout"  v-show="login">Log out</b-nav-item>
                    </b-navbar-nav>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
    export default {
        name: "Header",
        methods: {
            goLog: function() {
                this.$router.push({ path: `/` })
            },
            goRegister: function() {
                this.$router.push({ path: `/reg` })
            },
            goLogout: function () {
                localStorage.removeItem('auth')
                localStorage.removeItem('user')
                localStorage.removeItem('user_id')
                localStorage.removeItem('role')
                this.login = false
                this.$router.push({ path: `/log` })


            },
            isLog: function () {
                if(localStorage.getItem('auth')){
                    this.login = true
                }else {
                    this.login = false
                }

            },
            isUser: function () {
                if(localStorage.getItem('role') === 'u'){
                    this.user = true
                }else {
                    this.user = false
                }
            }
        },
        data() {
            return{
                login: true,
                user: false
            }
        },
        mounted : function ()
        {
            this.isLog()
            this.isUser()
        }
    }
</script>

<style scoped>

</style>
