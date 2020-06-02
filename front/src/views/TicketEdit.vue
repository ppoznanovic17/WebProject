<template>
    <div>
        <Header/>
        <div style="text-align: center" class="container">
            <h1 style="margin-top: 1rem">Menjanje karte</h1>
            <div class="grid-container">
                <div class="grid-item">
                    <label class="prev">({{ticket.companyId}} - {{ticket.companyName}})</label>
                    <b-select :options="companies" v-model="company"></b-select>
                </div>
                <div class="grid-item">
                    <label class="prev">({{ticket.flightId}} - {{ticket.originCity}} - {{ticket.destinationCity}})</label>
                    <b-select :options="flights" v-model="flight"></b-select>
                </div>
                <div class="grid-item">
                    <label @click="cls">Datum polaska:</label>
                    <br>
                    <label class="prev"> ({{ticket.departDateString}})</label>
                    <b-form-datepicker  class="mb-2" v-model="departDate"></b-form-datepicker>
                </div>
                <div class="grid-item">
                    <label @click="cls" >Datum povratka:</label>
                    <br>
                    <label class="prev"> ({{ticket.returnDateString}})</label>
                    <b-form-datepicker class="mb-2" v-model="returnDate"></b-form-datepicker>
                </div>
                <div class="grid-item">
                    <label for="tentacles">Broj karata:</label>
                    <input type="number" id="tentacles" name="tentacles"
                           min="1" max="100" style="margin-left: 10px" v-model="ticket.ticketCount">

                </div>
                <div class="grid-item">
                    <label>U jednom pravcu
                        <br>
                        <span class="prev">({{numToStr(ticket.oneWay)}})</span>
                        <br>
                        <input type="checkbox" checked="checked" v-model="oneWay">
                        <span class="checkmark"></span>
                    </label>
                </div>
                <div class="grid-item">
                    <b-button variant="danger" style="width: 50%" @click="stopChange">Prekini izmenu</b-button>
                </div>
                <div class="grid-item">
                    <b-button variant="success" style="width: 50%" @click="makeChanges">Sacuvaj izmene</b-button>
                </div>

            </div>

            <br>
            <br>
            <p class="err-reservation" style="color: red" v-show="ticketErr">{{ticketErr}}</p>
            <p class="msg-reservation" style="color: green" v-show="ticketMsg">{{ticketMsg}}</p>
            <br>
            <br>

        </div>
    </div>
</template>

<script>
    import Header from "@/components/Header";
    import CompanyService from "@/services/company_service";
    import FlightService from "@/services/flight_service";
    import TicketService from "@/services/ticket_service";

    export default {
        name: "TicketEdit",
        components: {
            Header
        },
        data(){
            return{
                ticket: {},
                ticketId: this.$route.params.id,
                oneWay: false,
                newTicket: {
                    companyId: 0,
                    ticketCount: '',
                    departDate: '',
                    returnDate: '',
                    oneWay: false,
                    flightId: ''
                },
                companies:[],
                flights:[],
                ticketErr: '',
                ticketMsg: '',
                company: '',
                flight: '',
                companyId: '',
                ticketCount: '',
                departDate: '',
                returnDate: ''


            }
        },
        methods:{
            cls: function(){
              this.departDate = ''
              this.returnDate = ''
            },
            stopChange: function(){
              this.$router.push({path:'../home'})
            },
            numToStr: function(num){
                if(num === 1) return 'Jednosmerna'
                else return 'Dvosmerna'
            },
            loadCompaniesCombo: function () {
                CompanyService.companyCombo(this)
            },
            loadFlightsCombo: function () {
                FlightService.flightCombo(this)
            },
            loadTicket: function (id) {
                TicketService.getTicket(id, this)

            },
            isEmpty: function(str){
              if(str === null) return true
              if(str === '') return true
              if( str === ' ') return true
              return false
            },
            splitString: function(str){
                let pom = str.split(' ')
                pom = pom[0].trim()
                return pom
            },
            parseBool: function(bool){
              if(bool === true) return 1
                return 0
            },
            makeChanges: function() {
                this.ticketMsg = ''
                this.ticketErr = ''
                if(this.isEmpty(this.company) || this.isEmpty(this.flight) || this.isEmpty(this.departDate)
                    || this.isEmpty(this.ticket.ticketCount)){
                    this.ticketErr = 'Sva polja moraju biti popunjena!'
                    return
                }
                if(this.oneWay === false && this.isEmpty(this.returnDate)){
                    this.ticketErr = 'Sva polja moraju biti popunjena!'
                    return
                }

                if(this.oneWay === true){
                    this.returnDate = ''
                }
                /*console.log(this.company)
                console.log(this.flight)
                console.log(this.departDate)
                console.log(this.returnDate)
                console.log(this.oneWay)
                console.log(this.ticket.ticketCount)*/
                let d1 = Date.parse(this.departDate)
                let d2 = Date.parse(this.returnDate)
                if( d2 > d1) {
                    this.ticketErr = 'Datum poletanja mora biti pre datuma povratka.'
                    return
                }
                let companyId = this.splitString(this.company)
                let flightId = this.splitString(this.flight)
                console.log( parseInt(this.$route.params.id))
                let ticket = {
                    id: parseInt(this.$route.params.id),
                    companyId: parseInt(companyId),
                    ticketCount: parseInt(this.ticket.ticketCount),
                    departDate: this.departDate,
                    returnDate:  this.returnDate,
                    oneWay: this.parseBool(this.oneWay),
                    flightId: parseInt(flightId),
                    version: parseInt(this.ticket.version)
                }
                //console.log(ticket)
                //this.ticketMsg = 'Stiglooo'
                TicketService.updateTicket(ticket, this, this.$route.params.id)

            }
        },
        mounted: function () {
            this.loadCompaniesCombo()
            this.loadFlightsCombo()
            this.loadTicket(this.$route.params.id)
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
    .prev{
        font-size: 1rem;
    }
</style>
