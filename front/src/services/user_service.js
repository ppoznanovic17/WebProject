import axios from 'axios'

const baseUrl = "http://localhost:8088/projekat_za_web_war_exploded/app/user/";

let UserService = {
    register(username, password, parent) {
        let data = JSON.stringify({
            username: username,
            password: password
        })

        let headers = {
            "Content-Type": "application/json"
        }

        fetch(baseUrl + `reg`, {
            method: 'post',
            headers: headers,
            body: data
        }).then((response) => {
            if (!response.ok)
                throw response;

            return response.text()
        }).then((text) => {


            if(text.toLowerCase().includes('uspesno')){
                parent.msg = text
            }else{
                parent.err = text
            }
            parent.username = ''
            parent.password = ''

        }).catch((error) => {
            parent.err = 'Greska'
        });

    },

    newAdmin(username, password, parent) {
        let data = JSON.stringify({
            username: username,
            password: password
        })

        let headers = {
            "Content-Type": "application/json",
            'Authorization': localStorage.getItem('auth')
        }

        fetch(baseUrl + `create_admin`, {
            method: 'post',
            headers: headers,
            body: data
        }).then((response) => {
            if (!response.ok)
                throw response;

            return response.text()
        }).then((text) => {


            if(text.toLowerCase().includes('uspesno')){
                parent.msg = text
            }else{
                parent.err = text
            }
            parent.username = ''
            parent.password = ''
        }).catch((error) => {
            parent.err = 'Greska'
        });

    },

    login(username, password, parent){
        let headers = {
            "Content-Type": "application/json"
        }
        let data = JSON.stringify({
            username: username,
            password: password
        })

        fetch(baseUrl + `login`, {
            method: 'post',
            headers: headers,
            body: data
        }).then((response) => {
            if (!response.ok)
                throw response;

            return response.text()
        }).then((text) => {
            if(!text.toLowerCase().includes('greska')){
                let split = text.split(' ')
                let token = split[0]
                let id = split[1]
                let username = split[2]
                let role = split[3]

                localStorage.setItem('auth','Bearer ' + token)
                localStorage.setItem('id', id)
                localStorage.setItem('user', username)
                localStorage.setItem('role', role)
                parent.$router.push({ name: 'home' })
            }else{
                parent.err = text
            }

        }).catch((error) => {
            parent.err = 'Greska'
        });


    }
}
export default UserService
