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

function upload(){
    var image = convertCanvasToImage(canvas);
    var formdata = new FormData();
    formdata.append('file',new Blob([ image ], {type: "image/png"}));
    $.ajax({
        async: false,
        url: baseURL + uploadImage,
        type: 'post',
        data: formdata,
        contentType:false,//ajax上传图片需要添加
        processData:false,//ajax上传图片需要添加
        success: function (data) {
            console.log(data);
        },
        error: function (e) {
            alert("error");
        }
    })
}

function download(){
    let param = {
        id: 1
    };
    $.ajax({
        async: false,
        url: baseURL + downloadImage,
        type: 'get',
        param: param,
        contentType: 'application/json',
        responseType: 'blob',
        success: function (data) {
            let blob = data.response;
            let a = document.createElement('a');
            a.download = 'test.png';
            a.href = window.URL.createObjectURL(blob);
            a.click();
        },
        error: function (e) {
            alert("error");
        }
    })
}

function importing(){
    var image = convertCanvasToImage(canvas);
    var formdata = new FormData();
    formdata.append('file',new Blob([ image ], {type: "image/png"}));
    $.ajax({
        async: false,
        url: baseURL + uploadImage,
        type: 'post',
        data: formdata,
        contentType:false,//ajax上传图片需要添加
        processData:false,//ajax上传图片需要添加
        success: function (data) {
            console.log(data);
        },
        error: function (e) {
            alert("error");
        }
    })
}
