<template>
    <div>
        <Header/>
        <div class="container">
            <h1>{{this.username}}</h1>
            <p v-show="msg" style="color: green">{{msg}}</p>
            <p v-show="err" style="color: red">{{err}}</p>
            <b-table striped hover :items="reservations" :fields="fields">
                <template  v-slot:cell(companyName)="data">
                    {{data.item.ticketDao.companyName}}
                </template>
                <template  v-slot:cell(originCity)="data">
                    {{data.item.ticketDao.originCity}}
                </template>
                <template  v-slot:cell(destinationCity)="data">
                    {{data.item.ticketDao.destinationCity}}
                </template>
                <template  v-slot:cell(departDateString)="data">
                    {{data.item.ticketDao.departDateString}}
                </template>
                <template  v-slot:cell(returnDateString)="data">
                    {{data.item.ticketDao.returnDateString}}
                </template>
                <template  v-slot:cell(oneWay)="data">
                    {{boolToText(data.item.ticketDao.oneWay)}}
                </template>
                <template v-slot:cell(cancel)="data" >
                    <b-button @click="deleteRes(data.item.id)" v-show="data.item.isAvailable===1" variant="danger">Otkazi rezervaciju</b-button>
                    <p v-show="data.item.isAvailable===0" variant="danger">Period otkazivanja zavrsen.</p>
                </template>

            </b-table>
        </div>
    </div>
</template>

<script>
    import Header from "@/components/Header";
    import ReservationService from "@/services/reservation_service";

    export default {
        name: "Reservations",
        components: {
            Header
        },
        data() {
            return {
               reservations: [],
                isUser: false,
                username: '',
                fields: [
                    {   key: 'id',
                        label: '#',
                    },
                    {   key: 'companyName',
                        label: 'Kompanija',
                    },
                    {   key: 'originCity',
                        label: 'Mesto polaska',

                    },
                    {   key: 'destinationCity',
                        label: 'Destinacija',

                    },
                    {   key: 'departDateString',
                        label: 'Datum polaska',

                    },
                    {   key: 'returnDateString',
                        label: 'Datum povratka',
                    },
                    {
                        key: 'oneWay',
                        label: 'U jednom pravcu',
                    },
                    {   key: 'isAvailable',
                        label: 'Aktivna?'
                    },
                    {   key: 'cancel',
                        label: ''
                    }

                ],
                err: '',
                msg: ''
            }
        },
        methods:{

            isUserLog:  function (){
                setTimeout(function(){

                    if(localStorage.getItem('auth') == null){

                        this.$router.push({ path: `/` })
                    }

                }, 200)

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
                    this.$router.push({ path: `/home` })
                }
                this.username = localStorage.getItem('user')
            },
            loadReservations: function () {
                this.msg = ''
                this.err = ''
                ReservationService.getReservations(this)
            },
            deleteRes: function (id) {
                ReservationService.cancelReservation(id, this, ' Obrisana rezervacija sa id brojem : ' + id + '.')
            }

        },
        mounted: function () {
            this.isUserLog()
            this.isUserFun()
            this.loadReservations()
        }

    }
</script>

<style scoped>
    .red{
        background-color: #ff000055;
    }
    .green{
        background-color: #00FF0055;
    }
</style>
