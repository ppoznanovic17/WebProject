<template>
    <div>
        <Header/>
        <div class="container">
            <h1> {{search.companyName}} </h1>
            <b-button v-show="!isUser" variant="danger" @click="deleteCompany"> DELETE THIS COMPANY</b-button>
            <br>
            <br>

            <p class="err-reservation" style="color: red" v-show="errRes">{{errRes}}</p>
            <p class="msg-reservation" style="color: green" v-show="msgRes">{{msgRes}}</p>
            <div class="tickets">
                <b-table striped hover :items="tickets" :fields="fields">
                    <template  v-slot:cell(companyName)="data">
                        <b-button style="width: 100%; height: 70px" variant="info" @click="seeCompany(data.item.companyName)"> {{data.item.companyName}}</b-button>
                    </template>
                    <template  v-slot:cell(oneWay)="data">
                        {{boolToText(data.item.oneWay)}}
                    </template>
                    <template v-slot:cell(delete)="data">
                        <b-button  v-show="!isUser" variant="danger" @click="deleteTicket(data.item.id)">Brisanje</b-button>
                    </template>
                    <template  v-slot:cell(edit)="data">
                        <b-button v-show="!isUser" variant="outline-primary" @click="editTicket(data.item.id)">Menjanje</b-button>
                    </template>
                    <template v-slot:cell(reserve)="data">
                        <b-button  v-show="isUser&&data.item.isAvailable===1" variant="success" @click="reserveTicket(data.item.id, data.item.flightId, dataToString(data.item))">Rezervisi</b-button>
                        <p  v-show="isUser&&data.item.isAvailable===0">Datum polaska je prosao.</p>
                    </template>
                </b-table>
            </div>

            <div class="editComp" v-show="!isUser">
                <h2>Menjanje imena kompanije</h2>
                <div class="grid-item">
                    <label>Naziv kompanije:</label>
                    <br>
                    <input  class="mb-2" v-model="newName">
                    <br>

                    <b-button v-show="!isUser" variant="success" @click="updateCompany"> Sacuvaj novo ime</b-button>
                    <p class="err-reservation" style="color: red" v-show="errr">{{errr}}</p>
                    <p class="msg-reservation" style="color: green" v-show="msggg">{{msggg}}</p>
                </div>
            </div>

        </div>
    </div>
</template>

<script>

    import Header from "@/components/Header";
    import TicketService from "@/services/ticket_service";
    import ReservationService from "@/services/reservation_service";
    import CompanyService from "@/services/company_service";

    export default {
        name: "Company",
        components:{
            Header
        },
        data() {
            return {
                errr: '',
                msggg: '',
                company: {},
                newName: '',
                msgRes:'',
                errRes:'',
                ticket: {},
                search:{
                    originCity: '',
                    destinationCity: '',
                    departDate: '',
                    returnDate: '',
                    oneWay: true,
                    twoWay: true,
                    companyName: this.$route.params.name
                },
                isUser: false,
                tickets: [],
                fields: [
                    {   key: 'companyName',
                        label: 'Kompanija',
                        sortable: true,
                    },
                    {   key: 'originCity',
                        label: 'Mesto polaska',
                        sortable: true
                    },
                    {   key: 'destinationCity',
                        label: 'Destinacija',
                        sortable: true
                    },
                    {   key: 'departDateString',
                        label: 'Datum polaska',
                        sortable: true
                    },
                    {   key: 'returnDateString',
                        label: 'Datum povratka',
                        sortable: true
                    },
                    {
                        key: 'oneWay',
                        label: 'U jednom pravcu',
                    },
                    {   key: 'delete',
                        label: ''
                    },
                    {   key: 'reserve',
                        label: ''
                    },
                    {   key: 'edit',
                        label: ''
                    }

                ],
                res: 0
            }
        },
        methods:{
            updateCompany: function(){
                this.errr = ''
                this.msggg = ''

                if(this.newName === null || this.newName === '' || this.newName === ' '){
                    this.errr = 'Ime ne moze ostati prazno!'
                    return
                }

                let data = {
                    name: this.newName,
                    version: parseInt(company.version),
                    id: company.id
                }
              CompanyService.updateTicket(data, this)
            },
            deleteCompany: function(){
              CompanyService.deleteCompany(company.id, this)
                console.log(company)
            },
            editTicket: function(id){
                this.$router.push({path:`../editticket/${id}`})
            },
            deleteTicket: function(id){
                TicketService.deleteTicket(id, this, this.search)
            },
            seeCompany: function(name){
                this.$router.push({path:`../company/${name}`})
            },
            isUserLog:  function (){
                setTimeout(function(){

                    if(localStorage.getItem('auth') == null){

                        this.$router.push({ path: `/` })
                    }
                }, 200)

            },
            dataToString: function(data){
                return data.companyName + '(' + data.originCity + ' ' + data.departDateString + ')  ->  (' + data.destinationCity + ' ' + data.returnDateString + ').'
            },
            boolToText: function(bool){
                if(bool === 1){
                    return 'Da';
                }else if (bool === ''){
                    return '/';
                }else {
                    return 'Ne'
                }
            },
            isUserFun: function(){
                if(localStorage.getItem('role') === 'USER'){
                    this.isUser = true
                }else {
                    this.isUser = false
                }
            },
            searchTickets: function () {

                TicketService.filter(this.search, this)
            },
            departDateClr: function () {
                this.search.departDate = ''
            },
            returnDateClr: function () {
                this.search.returnDate = ''
            },
            reserveTicket: function (id, flightId, msg) {
                /*console.log(id)
                console.log(flightId)*/
                ReservationService.reserve({ticketId:id, flightId}, this, msg)
            },
            getCompanyByName : function (name) {
                CompanyService.getCompanyByName(name, parent)

            }


        },
        mounted: function () {
            this.isUserLog()
            this.isUserFun()
            this.searchTickets()
            this.getCompanyByName(this.$route.params.name)

        }
    }
</script>

<style scoped>

</style>
