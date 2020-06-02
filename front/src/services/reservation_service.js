import TicketService from "@/services/ticket_service";
import Header from "@/components/Header";

const baseUrl = "http://localhost:8088/projekat_za_web_war_exploded/app/reservation/";

let ReservationService = {

    numOfReservations(parent) {
        fetch(  baseUrl  + `count`, { method: 'get'
            , headers:{
                'Authorization': localStorage.getItem('auth')

            }}).then((response) => {
            if (!response.ok)
                console.log('greskica')
            return response.text()
        }).then((text) => {

            if(text.length > 3){
            parent.numOfRes = 'err'
            }else{
                parent.numOfRes = text
            }


        }).catch((error) => {
            console.log(error)

        });
    },

    getReservations(parent) {
        fetch(  baseUrl , { method: 'get'
            , headers:{
                'Authorization': localStorage.getItem('auth')
            }}).then((response) => {
            if (!response.ok)
                console.log('greskica')
            return response.json()
        }).then((json) => {

            parent.reservations = json

        }).catch((error) => {
            console.log(error)

        });
    },

    cancelReservation(id, parent, msg){
        fetch(  baseUrl  + `${id}/delete`, { method: 'get'
            , headers:{
                'Authorization': localStorage.getItem('auth')

        }}).then((response) => {
            if (!response.ok)
                console.log('greskica')
            return response.text()
        }).then((text) => {

            if(text.toLowerCase().includes('uspesno')){
                parent.msg = text + ' ' + msg
            }else{
                parent.err = text
            }
            parent.loadReservations(parent)


        }).catch((error) => {
            parent.err = 'Neocekivana greska.'
        });
    },

    reserve(data, parent, msg, search) {
        data = JSON.stringify(data)
        console.log(data)
        fetch(  baseUrl, { method: 'post'
            , headers:{
                'Authorization': localStorage.getItem('auth'),
                "Content-Type": "application/json"
            },
            body: data})
            .then((response) => {
                if (!response.ok)
                    console.log('greskica')
                return response.text()
            }).then((text) => {
            console.log(text)
                if(text.toLowerCase().includes('uspesno')){
                    parent.msgRes = text + ' ' + msg
                }else{
                    parent.errRes = text
                }
                TicketService.filter(search, parent)

            }).catch((error) => {
                parent.errRes = 'Greska.'

            });
    }


}

export default ReservationService
