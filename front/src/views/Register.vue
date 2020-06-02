<template>
    <div>
        <Header></Header>
        <div class="body">
            <div class="login-box">
                <h1>New User</h1>
                <div class="text-box">
                    <i class="fas fa-user"></i>
                    <input type="text" placeholder="Korisnicko ime" v-model="username">
                </div>

                <div class="text-box">
                    <i class="fas fa-lock"></i>
                    <input type="password" placeholder="Sifra" v-model="password">
                </div>
                <span style="color: green" v-show="msg">{{msg}}</span>
                <span style="color: red" v-show="err">{{err}}</span>

                <input type="button" class="btn" value="Registruj se" @click="register()">
            </div>
        </div>
    </div>


</template>

<script>

    import Header from "@/components/Header";
    import UserService from "../services/user_service"
    export default {
        name: "Register",
        components: {
            Header
        },
        data() {
            return {
                cssProps: {
                    backgroundImage: `url(${require('../assets/bck.jpg')})`
                },
                username: '',
                password: '',
                msg: '',
                err: ''

            }
        },
        methods: {
            register:  function (){
                this.msg = ''
                this.err = ''
                UserService.register(this.username, this.password, this);
                this.username = ''
                this.password = ''
            },
        }
    }
</script>

<style scoped>
    @import "https://use.fontawesome.com/releases/v5.5.0/css/all.css";
    .body{
        margin: 0;
        padding: 0;
        font-family: sans-serif;
        background-image: url("../assets/airplane.jpg");
        background-size: cover;
        background-repeat: no-repeat;
        width: 100%;
        height: 93.3vh;
    }
    .login-box{
        width: 280px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        color: white;


    }
    .login-box h1{
        float: left;
        font-size: 40px;
        border-bottom: 6px solid white;
        margin-bottom: 50px;
        padding: 13px 0;
    }
    .text-box{
        width: 100%;
        overflow: hidden;
        font-size: 20px;
        padding: 8px 0;
        margin: 8px 0;
        border-bottom: 1px solid white;
    }
    .text-box i{
        width: 26px;
        float: left;
        text-align: center;

    }
    .text-box input{
        border: none;
        outline: none;
        background: none;
        color: white;
        font-size: 18px;
        width: 80%;
        float: left;
        margin: 0 5px;
    }
    .btn{
        width: 100%;
        background: none;
        border: 2px solid white;
        color: white;
        padding: 5px;
        font-size: 18px;
        cursor: pointer;
        margin: 6px 0;
    }
</style>
