const baseURL = "http://localhost:8080/";
const getUser = "user/getUser";

const promiseAjax = (method, api, param) => {
    return new Promise((resolve,reject) => {
        $.ajax({
            url: baseURL + api,
            type: method,
            dataType: 'json',
            data: param,
            success(res) {
                resolve(res);
            },
            error(err) {
                reject(err);
            }
        });
    });
};

let promiseGet = async (api, param) => {
    promiseAjax('get', api, param).then((res) => {
        console.log(res);
        return res;
    });
};

let promisePost = async (api, param) => {
    promiseAjax('post', api, param).then((res) => {
        return res;
    });
};

let get = function (api, param) {
    $.ajax({
        url: baseURL + api,
        type: method,
        dataType: 'json',
        data: param,
        success(res) {
            return res;
        },
        error(err) {
            console.log(err);
        }
    });
}

