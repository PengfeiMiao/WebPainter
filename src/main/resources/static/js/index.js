//$(document).ready(function(){});
var userInfo = '';
$(function(){

    userInfo = JSON.parse(localStorage.getItem('userInfo'));
    if(userInfo==null){
        window.location.href = "/login";
    }else{
        // console.log(userInfo.username);
        $("#user_name").html(`${userInfo.username},欢迎登录！`);
    }

    fileList(userInfo.id);

});

function fileList(id){
    let param = {
        creatorId: id
    };
    promiseAjax('get', filesFinder, param).then((res) => {
        console.log(res);
        let str = '';
        $('#file_select').html("");
        res.forEach(item=>{
            str += `<option value=${item.id}>${item.name}</option>`;
        });
        $('#file_select').append(str);
        layui.use(['form'], function() {
            layui.form.render();
        });
    });
}

layui.form.on('select(myselect)', function (data) {
    console.log(data.value);
    //download(data.value);
    $("#imageId").val(data.value);
});

function upload(){

    let input = $('#filename')[0];
    if(input.value==null || input.value===''){
        alert("文件名为空");
        return;
    }

    let image = convertCanvasToImage(canvas);
    let formdata = new FormData();
    formdata.append('file',new Blob([ image ], {type: "image/png"}));
    formdata.append('filename',input.value);
    formdata.append('creator',userInfo.id);
    $.ajax({
        async: false,
        url: baseURL + uploadImage,
        type: 'post',
        data: formdata,
        contentType:false,//ajax上传图片需要添加
        processData:false,//ajax上传图片需要添加
        success: function (data) {
            switch (data.code) {
                case "success":
                    alert('文件上传成功！');
                    fileList(userInfo.id);
                    break;
                case "error":
                    alert(data.data);
                    break;
                default: break;
            }
            console.log(data);
        },
        error: function (e) {
            alert(e);
        }
    })
}

function preview() {
    let id = $("#imageId").val();
    axios({
        method: "get",
        url: `${baseURL}${downloadImage}?id=${id}`,
        responseType: "arraybuffer",
        // 这里可以在header中加一些东西，比如token
    }).then(res => {
        return 'data:image/png;base64,' + btoa(
            new Uint8Array(res.data)
                .reduce((data, byte) => data + String.fromCharCode(byte), '')
        );
    }).then(data => {
        layer.open({
            type: 1,//Page层类型
            area: ['1200px', '600px'],
            title: '预览图片',
            shade: 0.6,//遮罩透明度
            maxmin: true,//允许全屏最小化
            anim: 1,//0-6的动画形式，-1不开启
            content: '<img src="'+data+'"/>'
        })
    }).catch(e=>{
        if(e.toString().indexOf('404')>-1){
            alert('图片已失效！')
        }
    })

}

function download(){

    let id = $("#imageId").val();

    axios({
        method: "get",
        url: `${baseURL}${downloadImage}?id=${id}`,
        responseType: "arraybuffer",
        // 这里可以在header中加一些东西，比如token
    }).then(res => {
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
    }).catch(error => {
        console.log("response: ", error);
        if(error.toString().indexOf('404')>-1){
            alert('图片已失效！')
        }
    });
}

function importing(file){
    if (!file.files || !file.files[0]) {
        return;
    }
    let reader = new FileReader();
    reader.onload = function (evt) {
        let img = new Image();
        img.src = evt.target.result;
        img.id = "tmp";
        //img.style.display = "none";
        document.body.appendChild(img);
        img.onload = function () {
            convertImageToCanvas(this);
            document.body.removeChild(img);
        }
    };
    reader.readAsDataURL(file.files[0]);
}

function logout(){

    let userInfo = JSON.parse(localStorage.getItem('userInfo'));;

    axios.post(baseURL+userLogout, userInfo).then(res => {
        console.log(res);
        if(res.status === 200){
            switch (res.data.code) {
                case "success": {
                    console.log(res.data.data.username);
                    localStorage.removeItem("userInfo");
                    window.location.href = "/login";
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
