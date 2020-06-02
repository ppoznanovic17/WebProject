const baseUrl = "http://localhost:8088/projekat_za_web_war_exploded/app/ticket/";

let TicketService = {

    filter(data, parent) {
        data = JSON.stringify(data)
        console.log(data)
        fetch(  baseUrl  + `filter`, { method: 'post'
            , headers:{
                'Authorization': localStorage.getItem('auth'),
                "Content-Type": "application/json"
            },
            body: data})
            .then((response) => {
            if (!response.ok)
                console.log('greskica')
            return response.json()
        }).then((json) => {

            parent.tickets = json


        }).catch((error) => {
            console.log(error)

        });
    },

    newTicket(data, parent) {
        data = JSON.stringify(data)
        fetch(  baseUrl  + `new`, { method: 'post'
            , headers:{
                'Authorization': localStorage.getItem('auth'),
                "Content-Type": "application/json"
            },
            body: data})
            .then((response) => {
                if (!response.ok)
                    console.log('greskica')
                return response.text()
            }).then((json) => {

            if(json.toLowerCase().includes('uspesno')){
                parent.ticketMsg = json
            }else {
                parent.ticketErr = json
            }
            let filter = {
                "originCity": '',
                "destinationCity": '',
                "departDate": '',
                "returnDate": '',
                "oneWay": true,
                "twoWay": true,
                "company": ""
            }
            this.filter(filter, parent)


        }).catch((error) => {
            parent.ticketErr = 'Greska.'

        });
    },

    updateTicket(data, parent, id) {
        data = JSON.stringify(data)
        console.log(data)
        fetch(  baseUrl  + `${id}/put`, { method: 'post'
            , headers:{
                'Authorization': localStorage.getItem('auth'),
                "Content-Type": "application/json"
            },
            body: data})
            .then((response) => {
                if (!response.ok)
                    console.log('greskica')
                return response.text()
            }).then((json) => {

            if(json.toLowerCase().includes('uspesno')){
                parent.ticketMsg = json
            }else {
                parent.ticketErr = json
            }


        }).catch((error) => {
            parent.ticketErr = 'Greska.'

        });
    },

    deleteTicket(id, parent, search){
        fetch(  baseUrl  + `${id}/delete`, { method: 'get'
            , headers:{
                'Authorization': localStorage.getItem('auth')

            }}).then((response) => {
            if (!response.ok)
                console.log('greskica')
            return response.text()
        }).then((text) => {

            if(text.toLowerCase().includes('uspesno')){
                parent.msgRes = text
            }else{
                parent.errRes = text
            }
            this.filter(search, parent)


        }).catch((error) => {
            parent.err = 'Neocekivana greska.'
        });
    },

    getTicket(id, parent){
        fetch(  baseUrl  + `dao/${id}`, { method: 'get'
            , headers:{
                'Authorization': localStorage.getItem('auth')

            }}).then((response) => {
            if (!response.ok)
                console.log('greskica')
            return response.json()
        }).then((json) => {


            parent.ticket = json
            return json


        }).catch((error) => {
            parent.err = 'Neocekivana greska.'
        });
    },


}


export default TicketService
