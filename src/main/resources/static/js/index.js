let read = function () {
    let param = {
        'id': '1'
    };

    promiseAjax('get', getUser, param).then((res) => {

        $(".log").html('用户名：' + res.data["username"]);
        console.log(res);
        return res;

    });

};
