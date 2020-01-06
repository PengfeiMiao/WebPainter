function login() {
    let username = $('#username').val();
    let password = $('#password').val();
    if (username != null && password != null) {
        let param = {
            username: username,
            password: password
        };
        axios.post(baseURL+userLogin, param).then(res => {
            console.log(res);
            if(res.status === 200){
                switch (res.data.code) {
                    case "success": {
                        console.log(res.data.data.username);
                        localStorage.setItem("userInfo",JSON.stringify(res.data.data));
                        window.location.href = "/test/index";
                        break;
                    }
                    case "error": {
                        alert(res.data.msg);
                        break;
                    }
                    default: break;
                }
            }
        });
    }
}