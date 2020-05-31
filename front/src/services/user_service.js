import axios from 'axios'

const baseUrl = "http://localhost:8088/projekat_za_web_war_exploded/app/user/";

let UserService = {
    register(username, password, parent) {
        axios.post(baseUrl + 'reg', {
            data:{
                username: username,
                password: password
            },
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
        }).then((response) => {
            // Podaci sa servera
            let res = JSON.parse(JSON.stringify(response.data))
            console.log(JSON.parse(JSON.stringify(response.status)));
            parent.msg = res.toString()


        }, (error) => {
            console.log("An error occured:");
            console.log(error);
            parent.msg = 'Greska.'
        });
    },

    login(){
        console.log('log')
    }
}
export default UserService
