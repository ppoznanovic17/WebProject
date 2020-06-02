<template>
    <div style="text-align: center">
        <h2>Kreiranje korisnika</h2>

        <div class="grid-container">
            <div class="grid-item">
                <label >Korisnicko ime:</label>
                <br>
                <input  class="mb-2" v-model="username">
            </div>
            <div class="grid-item">

                <label >Sifra:</label>
                <br>
                <input  class="mb-2" v-model="password" type="password">
            </div>

        </div>
        <label >Tip korisnika:</label>
        <br>
        <b-select style="width: 50%" :options="roles" v-model="role"></b-select>
        <br>
        <br>
        <b-button variant="dark" style="width: 50%" @click="createUser">Kreiraj korisnika</b-button>
        <p class="err-reservation" style="color: red" v-show="err">{{err}}</p>
        <p class="msg-reservation" style="color: green" v-show="msg">{{msg}}</p>
        <br>
        <br>
        <br>
        <br>
        <br>
    </div>

</template>

<script>
    import UserService from "@/services/user_service";

    export default {
        name: "CreateUser",
        data(){
            return{
                roles: ['USER', 'ADMIN'],
                role: '',
                username: '',
                password: '',
                err: '',
                msg: ''

            }
        },
        methods: {
            createUser: function(){
                if(this.username === null || this.username === '' || this.password === null || this.password === '' || this.role === null || this.role === ''){
                    this.errRes = 'Ne sme biti praznog polja.'
                }
                if(this.role === 'USER'){
                    UserService.register(this.username, this.password, this)
                }else{
                    UserService.newAdmin(this.username, this.password, this)
                }
            }
        }
    }
</script>

<style scoped>
    .grid-container {
        display: grid;
        grid-template-columns: auto auto;

    }
    .grid-item {
        background-color: rgba(255, 255, 255, 0.8);
        padding: 10px;
        font-size: 20px;
        text-align: center;
    }
    input{
        width: 100%;
        border-radius: 3%;
    }
</style>
