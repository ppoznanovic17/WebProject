const baseUrl = "http://localhost:8088/projekat_za_web_war_exploded/app/flight/";

let FlightService = {
        flightCombo(parent) {
        fetch(  baseUrl  + `combo`, { method: 'get'
            , headers:{
                'Authorization': localStorage.getItem('auth')

            }}).then((response) => {
            if (!response.ok)
                console.log('greskica')

            return response.json()
        }).then((text) => {


            parent.flights = text



        }).catch((error) => {
            console.log(error)

        });
    },
}

export default FlightService
