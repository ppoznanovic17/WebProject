<template>
    <div style="text-align: center">
        <h2>Kreiranje kompanije</h2>
         <div class="grid-item">
                <label  @click="departDateClrs()">Naziv kompanije:</label>
             <br>
                <input  class="mb-2" v-model="name"></input>
          </div>

        <b-button variant="dark" style="width: 50%" @click="newCompany">Kreiraj kompaniju</b-button>
        <br>
        <br>
        <p class="err-reservation" style="color: red" v-show="compErr">{{compErr}}</p>
        <p class="msg-reservation" style="color: green" v-show="compMsg">{{compMsg}}</p>
        <br>
        <br>

    </div>
</template>

<script>

    import CompanyService from "@/services/company_service";

    export default {
        name: "CreateCompany",
        data() {
            return {
                name: '',
                compErr: '',
                compMsg: ''
            }
        },
        methods: {
            newCompany: function () {
                this.compErr = ''
                this.compMsg = ''
                if(this.name === null || this.name === ' '){
                    this.compErr = 'Morate uneti ime kompanije.'
                }
                let data = {
                    name : this.name
                }
                CompanyService.newCompany(this, data)
            }
        }
    }
</script>

<style scoped>

</style>
