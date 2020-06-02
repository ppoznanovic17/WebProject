<template>
    <div style="text-align: center">
        <h2>Kreiranje karte</h2>
        <div class="grid-container">
            <div class="grid-item">
                <b-select :options="companies" v-model="company"></b-select>
            </div>
            <div class="grid-item">
                <b-select :options="flights" v-model="flight"></b-select>
            </div>
            <div class="grid-item">
                <label  @click="departDateClrs()">Datum polaska:</label>
                <b-form-datepicker  class="mb-2" v-model="departDate"></b-form-datepicker>
            </div>
            <div class="grid-item">
                <label  @click="returnDateClrs()">Datum povratka:</label>
                <b-form-datepicker class="mb-2" v-model="returnDate"></b-form-datepicker>
            </div>
            <div class="grid-item">
                <label for="tentacles">Broj karata:</label>
                <input type="number" id="tentacles" name="tentacles"
                       min="1" max="100" style="margin-left: 10px" v-model="ticketCount">
                <br>
                <label>U jednom pravcu
                    <input type="checkbox" checked="checked" v-model="oneWay">
                    <span class="checkmark"></span>
                </label>
            </div>

        </div>
        <b-button variant="dark" style="width: 50%" @click="createTicket">Kreiraj kartu</b-button>
        <br>
        <br>
        <p class="err-reservation" style="color: red" v-show="ticketErr">{{ticketErr}}</p>
        <p class="msg-reservation" style="color: green" v-show="ticketMsg">{{ticketMsg}}</p>
        <br>
        <br>

    </div>

</template>

<script>
    import CompanyService from "@/services/company_service";
    import FlightService from "@/services/flight_service";
    import TicketService from "@/services/ticket_service";

    export default {
        name: "CreateTicket",
        data(){
            return{
                companies:[],
                flights:[],
                departDate: '',
                returnDate: '',
                company: '',
                flight: '',
                ticketCount: 1,
                oneWay: false,
                ticketMsg: '',
                ticketErr: ''
            }
        },
        methods:{
            loadCompaniesCombo: function () {
                CompanyService.companyCombo(this)
            },
            loadFlightsCombo: function () {
                FlightService.flightCombo(this)
            },
            departDateClrs: function () {
                this.departDate = ''
            },
            returnDateClrs: function () {
                this.returnDate = ''
            },
            splitString: function(str){
              let pom = str.split(' ')
                pom = pom[0].trim()
                return pom
            },
            boolToInt: function(br){
                if(br === true) return 1
                else  return 0
            },
            createTicket: function () {
                console.log(this.company)
                console.log(this.flight)
                console.log(this.departDate)
                console.log(this.returnDate)
                console.log(this.oneWay)
                console.log(this.ticketCount)
                this.ticketMsg = ''
                this.ticketErr = ''
                let ticket = {
                    companyId: this.splitString(this.company),
                    ticketCount: this.ticketCount,
                    departDate: this.departDate,
                    returnDate: this.returnDate,
                    oneWay: this.boolToInt(this.oneWay),
                    flightId: this.splitString(this.flight)

                }
                TicketService.newTicket(ticket, this)
                    this.company = ''
                    this.flight = ''
                    this.departDate = ''
                    this.returnDate = ''
                    this.oneWay = ''
                    this.ticketCount = ''
            }

        },
        mounted: function () {
            this.loadCompaniesCombo()
            this.loadFlightsCombo()
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
</style>
