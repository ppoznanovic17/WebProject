const baseUrl = "http://localhost:8088/projekat_za_web_war_exploded/app/company/";

let CompanyService = {

    companyCombo(parent) {
        fetch(  baseUrl  + `combo`, { method: 'get'
            , headers:{
                'Authorization': localStorage.getItem('auth')

            }}).then((response) => {
            if (!response.ok)
                console.log('greskica')

            return response.json()
        }).then((text) => {


            parent.companies = text
            console.log(text[0])


        }).catch((error) => {
            console.log(error)

        });
    },


    deleteCompany(id, parent){
        fetch(  baseUrl  + `${id}/delete`, { method: 'get'
            , headers:{
                'Authorization': localStorage.getItem('auth')

            }}).then((response) => {
            if (!response.ok)
                console.log('greskica')
            return response.text()
        }).then((text) => {

            console.log(text)
            parent.$router.push({path:'../home'})


        }).catch((error) => {
            parent.err = 'Neocekivana greska.'
        });
    },

    getCompanyByName(name, parent){
        fetch(  baseUrl  + `${name}/company`, { method: 'get'
            , headers:{
                'Authorization': localStorage.getItem('auth')

            }}).then((response) => {
            if (!response.ok)
                console.log('greskica')
            return response.json()
        }).then((json) => {


            parent.company = json



        }).catch((error) => {
            parent.err = 'Neocekivana greska.'
        });
    },

    updateTicket(data, parent) {
        data = JSON.stringify(data)
        console.log(data)
        fetch(  baseUrl  + `update`, { method: 'post'
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
                parent.msggg = json
            }else {
                parent.errr = json
            }


        }).catch((error) => {
            parent.errr = 'Greska.'

        });
    },

    newCompany(parent, pom) {
        let data = JSON.stringify(pom)
        console.log(data)
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
                parent.compMsg = json
            }else {
                parent.compErr = json
            }


        }).catch((error) => {
            parent.ticketErr = 'Greska.'

        });
    },

}

export default CompanyService
