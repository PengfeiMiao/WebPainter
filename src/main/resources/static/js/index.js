//$(document).ready(function(){});
$(document).ready(function(){

    let param = {
        creatorId: '1'
    };
    promiseAjax('get', filesFinder, param).then((res) => {
        console.log(res);
        let str = '';
        res.forEach(item=>{
            str += `<option value=${item.id}>${item.name}</option>`;
        });
        $('#file_select').append(str);
        layui.form;
    });


});

layui.form.on('select(myselect)', function (data) {
    console.log(data);
});

let read = function () {
    let param = {
        'id': '1'
    };

    // axios.get(`${baseURL}${getUser}`, param).then((res) => {
    //     $(".log").html('用户名：' + res.data["username"]);
    //     console.log(res);
    //     return res;
    // });

    promiseAjax('get', getUser, param).then((res) => {
        $(".log").html('用户名：' + res.data["username"]);
        console.log(res);
        return res;
    });

};

function upload(){
    let image = convertCanvasToImage(canvas);
    let formdata = new FormData();
    formdata.append('file',new Blob([ image ], {type: "image/png"}));
    formdata.append('filename','test');
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

    let id = 1;

    axios({
        method: "get",
        url: `${baseURL}${downloadImage}?id=${id}`,
        responseType: "arraybuffer",
        // 这里可以在header中加一些东西，比如token
    })
        .then(res => {
            console.log(res);
            // new Blob([data])用来创建URL的file对象或者blob对象
            let url = window.URL.createObjectURL(new Blob([res.data]));
            // 生成一个a标签
            let link = document.createElement("a");
            link.style.display = "none";
            link.href = url;

            link.download = res.headers["content-disposition"].split("filename=")[1];
            document.body.appendChild(link);
            link.click();
        })
        .catch(error => {
            console.log("response: ", error);
        });
}

function importing(){
}
