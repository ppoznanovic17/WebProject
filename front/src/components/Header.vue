<template>
    <div class="head">
        <b-navbar toggleable="lg" type="light" variant="light">
            <b-navbar-brand href="/home">Petar's FlyCo</b-navbar-brand>
            <b-navbar-nav class="ml-auto">
                <b-navbar-nav>
                    <b-nav-item href="/home" v-show="login">Home</b-nav-item>
                </b-navbar-nav>
            </b-navbar-nav>
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
            <b-collapse id="nav-collapse" is-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                     <b-navbar-nav right>

                        <b-nav-item @click="goRegister" v-show="!login">Registracija</b-nav-item>
                         <b-nav-item @click="goLog"  v-show="!login">Prijava</b-nav-item>
                         <b-nav-item   v-show="username&&user&&role" @click="goReservations"><a>{{username}} ({{role}}) KORPA<sup class="num-res">{{numOfRes}}</sup></a></b-nav-item>
                         <b-nav-item   v-show="username&&!user&&role">{{username}} {{role}}</b-nav-item>
                         <b-nav-item @click="goLogout"  v-show="login">Odjavi se</b-nav-item>
                    </b-navbar-nav>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
    import ReservationService from "@/services/reservation_service";

    export default {
        name: "Header",
        methods: {
            numOfReservations: function () {
                if(this.user){
                    ReservationService.numOfReservations(this)
                }

            },
            goLog: function() {
                this.$router.push({ path: `/` })
            },
            goReservations: function() {
                this.$router.push({ path: `/res` })
            },
            goRegister: function() {
                this.$router.push({ path: `/reg` })
            },
            goLogout: function () {
                localStorage.removeItem('auth')
                localStorage.removeItem('user')
                localStorage.removeItem('id')
                localStorage.removeItem('role')
                this.login = false
                this.user = false
                this.$router.push({ path: `/` })


            },
            isLog: function () {
                if(localStorage.getItem('auth') != null){
                    this.login = true
                }else {
                    this.login = false
                }

            },
            isUser: function () {
                if(localStorage.getItem('role') === 'USER'){
                    this.user = true
                }else {
                    this.user = false
                }
            }
        },
        data() {
            return{
                login: false,
                user: false,
                username: '',
                role: '',
                numOfRes: ''
            }
        },
        mounted : function ()
        {
            this.isLog()
            this.isUser()
            this.numOfReservations()
            this.username = localStorage.getItem('user')
            this.role = localStorage.getItem('role')
        }
    }
</script>

<style scoped>
    .num-res{
        background-color: orange;
        color: white;
        border-radius: 50%;
        padding: 2px;
    }
    .head{
        border-bottom: 1px solid #aaa;
    }
</style>
