let read = function () {
    let param = {
        'id': '1'
    };

    let getUserById = promiseAjax('get', getUser, param).then((res) => {
        console.log(res);
        return res;
    });

    setTimeout(()=>{
        console.log(getUserById.);
    },500);

};
