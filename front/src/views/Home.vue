<template>
    <div>
        <Header/>
        <div class="container">
            <div v-show="isUser" class="user_search">
                <div class="grid-container">
                    <div class="grid-item">
                        <label class="search_label">Mesto polaska:</label>
                        <br>
                        <input type="text" placeholder="Mesto polaska" v-model="search.originCity">
                    </div>
                    <div class="grid-item">
                        <label class="search_label">Mesto sletanja:</label>
                        <br>
                        <input type="text" placeholder="Mesto sletanja" v-model="search.destinationCity">
                    </div>
                    <div class="grid-item">
                        <label class="search_label" @click="departDateClr()">Datum polaska:</label>
                        <b-form-datepicker id="example-datepicker" v-model="search.departDate" class="mb-2"></b-form-datepicker>
                    </div>
                    <div class="grid-item">
                        <label class="search_label" @click="returnDateClr()">Datum povratka:</label>
                        <b-form-datepicker id="example-datepicker" v-model="search.returnDate" class="mb-2"></b-form-datepicker>
                    </div>
                    <div class="grid-container">
                        <label >U jednom pravcu
                            <input type="checkbox" checked="checked" v-model="search.oneWay">
                            <span class="checkmark"></span>
                        </label>

                        <label>Povratna karta
                            <input type="checkbox" v-model="search.twoWay">
                            <span class="checkmark"></span>
                        </label>
                    </div>
                    <div class="grid-item">
                        <b-button variant="dark" @click="searchTickets()">Pretrazi</b-button>
                    </div>

                </div>
            </div>
            <div class="tickets">
                <p class="err-reservation" style="color: red" v-show="errRes">{{errRes}}</p>
                <p class="msg-reservation" style="color: green" v-show="msgRes">{{msgRes}}</p>
                <b-table striped hover :items="tickets" :fields="fields">
                    <template  v-slot:cell(id)="data">
                         <p>{{data.item.id}}</p>
                    </template>
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
            <div class="admin">
                <hr>
                <br>
                <br>
                <CreateTicket/>
                <hr>
                <br>
                <br>
                <CreateCompany/>
                <hr>
                <br>
                <br>
                <CreateUser/>
            </div>

        </div>

    </div>
</template>

<script>
    import Header from "@/components/Header";
    import TicketService from "@/services/ticket_service";
    import ReservationService from "@/services/reservation_service";
    import CreateTicket from "@/components/CreateTicket";
    import CreateCompany from "@/components/CreateCompany";
    import CreateUser from "@/components/CreateUser";
    export default {
        name: "Home",
        components:{
            CreateUser,
          Header,
            CreateTicket,
            CreateCompany
        },
        data() {
            return {
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
                    companyName: ''
                },
                isUser: false,
                tickets: [],
                fields: [
                    {   key: 'id',
                        label: '#',
                        sortable: true,
                    },
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
                    {
                        key: 'ticketCount',
                        label: 'Broj preostalih karata',
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
                this.errRes = ''
                this.msgRes = ''
                ReservationService.reserve({ticketId:id, flightId}, this, msg, this.search)

            }


        },
        mounted: function () {
            this.isUserLog()
            this.isUserFun()
            this.searchTickets()

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
