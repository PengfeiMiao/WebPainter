const baseURL = "http://localhost:8766/";

const getUser = "user/getUser";
const uploadImage = "file/upload/image";
const downloadImage = "file/downloadFileById";
const filesFinder = "finder/filesFinder";
const userLogin = "user/login";

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

let get = function (api, param) {
    $.ajax({
        url: baseURL + api,
        type: 'get',
        dataType: 'json',
        data: param,
        success(res) {
            console.log(res);
        },
        error(err) {
            console.log(err);
        }
    });
}

