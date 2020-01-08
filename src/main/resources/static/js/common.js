const baseURL = "http://localhost:8766/";
// const baseURL = "http://116.62.202.107:8766/";

const uploadImage = "file/upload/image";
const downloadImage = "file/downloadFileById";
const filesFinder = "finder/filesFinder";
const userLogin = "user/login";
const userLogout = "user/logout";
const userRegister = "user/register";

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

