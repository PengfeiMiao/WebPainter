var canvas = document.getElementById("cans");
var cxt = canvas.getContext("2d");
var startX, startY, endX, endY;
var data;
var shapes = new Array();
var mousedown, mouseout;
var shape = 1;
var myCans = document.getElementById("myCans");

// 创建图形对象，保存该图形的开始、结束坐标以及相关属性
function create_shape(Shape, startx, starty, endx, endy) {
    var color = cxt.strokeStyle.toString();
    var size = cxt.lineWidth;
    shapes[shapes.length] = {
        "Shape": Shape,
        "startx": startx,
        "endx": endx,
        "starty": starty,
        "endy": endy,
        "color": color,
        "size": size,
        "x": [],
        "y": [],
    };
}


//点击画布，获取起始坐标，由于加了标题栏，坐标存在一个偏移量
function StartPos(e) {
    mousedown = 0;
    mouseout = 0;
    var rect = canvas.getBoundingClientRect();
    startX = e.clientX - rect.left * (canvas.width / rect.width);
    startY = e.clientY - rect.top * (canvas.height / rect.height);
    //如果是任意线，创建该对象
    if (shape == 4) {
        create_shape(4, startX, startY, endX, endY);
        shapes[shapes.length - 1].x.push(startX);
        shapes[shapes.length - 1].y.push(startY);
    }
    //如果是橡皮擦，调用擦除功能函数
    if (shape == 5) {
        delete_picture();
    }
    //保存当前画面
    data = cxt.getImageData(0, 0, canvas.width, canvas.height);
}


// 获取终点坐标
function EndPos(e) {
    if (startX != null) {
        var rect = canvas.getBoundingClientRect();
        endX = e.clientX - rect.left * (canvas.width / rect.width);
        endY = e.clientY - rect.top * (canvas.height / rect.height);
    }
}


// 松开鼠标
function Mouseup() {
    if (startX != null && endX != null && shape != 5 && shape != 4 && !(mousedown == 1 && mouseout == 1)) {
        //创建该图形对象，并保存相关属性
        create_shape(shape, startX, startY, endX, endY);
        endX = null;
    }
    startX = null;
}


// 按下鼠标
function Mousedown() {
    //如果鼠标是在画布外按下的，mousedown=1
    if (mouseout == 1)
        mousedown = 1;
}

// 鼠标移出了画布，mouseout=1
function MouseOut() {
    mouseout = 1;
}

// 选择画直线
function line() {
    shape = 1;
}

// 选择画圆
function circle() {
    shape = 2
}

// 选择画矩形
function rectangle() {
    shape = 3;
}

// 选择画自由线
function pencil() {
    shape = 4;
}

// 选择橡皮擦
function rubber() {
    shape = 5;
}

// 鼠标移动过程中画画
function draw() {
    // 如果起始坐标不为空而且不是橡皮擦状态
    if (startX != null && shape != 5) {
        //如果不是任意线状态，清空当前画布，展示上一个画布状态
        if (shape != 4) {
            cxt.clearRect(0, 0, canvas.width, canvas.height);
            cxt.putImageData(data, 0, 0);
        }
        //调用画图函数画图
        draw_picture(shape, startX, startY, endX, endY);
    }
}

// 画图
function draw_picture(Shape, startx, starty, endx, endy) {
    switch (Shape) {
        case 1://直线
            cxt.beginPath();
            cxt.moveTo(startx, starty);
            cxt.lineTo(endx, endy);
            cxt.stroke();
            cxt.closePath();
            break;
        case 2://圆
            var temp = Math.sqrt(Math.pow((endx - startx), 2) + Math.pow((endy - starty), 2));
            cxt.beginPath();
            cxt.arc(startx, starty, temp, 0, Math.PI * 2, true);
            cxt.stroke();
            cxt.closePath();
            break;
        case 3://矩形
            cxt.beginPath();
            cxt.rect(startx, starty, endx - startx, endy - starty);
            cxt.stroke();
            cxt.closePath();
            break;
        case 4://任意线
            draw_pencil();
    }
}

//画任意线
function draw_pencil() {
    //如果不是橡皮擦状态，记录走过的位置坐标
    if (shape !== 5) {
        shapes[shapes.length - 1].x.push(endX);
        shapes[shapes.length - 1].y.push(endY);
    }
    //画任意线
    cxt.beginPath();
    cxt.lineJoin = "round";
    cxt.moveTo(startX, startY);
    cxt.lineTo(endX, endY);
    cxt.stroke();
    cxt.closePath();
    startX = endX;
    startY = endY;
}

//橡皮擦功能函数，这里橡皮惨的功能是删除点击位置所存在的图形对象
function delete_picture() {
    cxt.clearRect(0, 0, canvas.width, canvas.height);	// 清除画布
    var list = new Array();// 用list记录需要删除的对象的下标
    var current_size = cxt.lineWidth;// 用current_size记录当前的lineWidth
    for (var j = 0 in shapes) {
        var isPointIn = -1;// 标志该点是否存在当前对象中
        var Error = judgeError(shapes[j].size);
        switch (shapes[j].Shape) {
            case 1: // 直线
                //判断点到直线的距离
                var a = shapes[j].endy - shapes[j].starty;
                var b = shapes[j].startx - shapes[j].endx;
                var c = -shapes[j].endy * shapes[j].startx + shapes[j].endx * shapes[j].starty;
                var distance = Math.pow((a * startX + b * startY + c), 2) / (Math.pow(a, 2) + Math.pow(b, 2));
                //如果该距离小于2，则视为该点在直线上，删除该对象，否则画出该直线
                if (distance < (Error * Error)) {
                    isPointIn = 1;
                    break;
                }
                isPointIn = 0;
                break;

            case 2:// 圆
                var x1 = shapes[j].startx, y1 = shapes[j].starty, x2 = shapes[j].endx, y2 = shapes[j].endy;
                var radius = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
                cxt.arc(shapes[j].startx, shapes[j].starty, radius, 0, Math.PI * 2, true);
                // 判断该点到圆心的距离，与半径进行比较
                var distance = Math.sqrt(Math.pow((x1 - startX), 2) + Math.pow((y1 - startY), 2));
                if (distance <= radius + Error && distance >= radius - Error) {
                    isPointIn = 1;
                    break;
                }
                isPointIn = 0;
                break;
            case 3://矩形
                // 判断该点是否在矩形边上，在则删除该对象，记录下标，否则画出该矩形
                var y1 = Math.max(shapes[j].endy, shapes[j].starty);
                var y2 = Math.min(shapes[j].endy, shapes[j].starty);
                var x1 = Math.max(shapes[j].startx, shapes[j].endx);
                var x2 = Math.min(shapes[j].endx, shapes[j].startx);
                if ((x1 <= startX + Error && x1 >= startX - Error) || (x2 <= startX + Error && x2 >= startX - Error)) {
                    if (startY >= y2 - Error && startY <= y1 + Error) {
                        isPointIn = 1;
                        break;
                    }
                }
                if ((y1 <= startY + Error && y1 >= startY - Error) || (y2 <= startY + Error && y2 >= startY - Error)) {
                    if (startX >= x2 - Error && startX <= x1 + Error) {
                        isPointIn = 1;
                        break;
                    }
                }
                isPointIn = 0;
                break;
            case 4:
                cxt.beginPath();
                var k;
                //依次取出任意线轨迹中的相邻两点，并且以这两点为对角顶点，做矩形
                for (k = 0; k < shapes[j].x.length - 1; k++) {
                    var y1 = Math.max(shapes[j].y[k], shapes[j].y[k + 1]);
                    var y2 = Math.min(shapes[j].y[k], shapes[j].y[k + 1]);
                    var x1 = Math.max(shapes[j].x[k], shapes[j].x[k + 1]);
                    var x2 = Math.min(shapes[j].x[k], shapes[j].x[k + 1]);
                    cxt.rect(shapes[j].x[k], shapes[j].y[k], shapes[j].x[k + 1]
                        - shapes[j].x[k], shapes[j].y[k + 1] - shapes[j].y[k]);
                    //如果该点在这个矩形中，则视为该点在这个对象上，删除对象
                    if (startX <= x1 && startX >= x2 && startY <= y1 && startY >= y2) {
                        k = -1;
                        break;
                    }
                    if (startX - Error < x1 && startX + Error > x2 && startY - Error < y1 && startY + Error > y2) {
                        k = -1;
                        break;
                    }
                }
                if (k === -1) {
                    cxt.closePath();
                    list.push(j);
                    break;
                }
                //该点不在对象上，设置当前画布属性与该对象一致，画出该任意线
                else {
                    cxt.lineWidth = shapes[j].size;
                    cxt.strokeStyle = shapes[j].color;
                    for (k = 0; k < shapes[j].x.length - 1; k++) {
                        cxt.beginPath();
                        cxt.moveTo(shapes[j].x[k], shapes[j].y[k]);
                        cxt.lineTo(shapes[j].x[k + 1], shapes[j].y[k + 1]);
                        cxt.stroke();
                        cxt.closePath();
                    }
                }
        }
        //如果该图形不是任意线，且该点不在对象上，设置当前画布属性与该对象一致，画出该图形
        if (isPointIn === 0 && shapes[j].Shape !== 4) {
            cxt.lineWidth = shapes[j].size;
            cxt.strokeStyle = shapes[j].color;
            draw_picture(shapes[j].Shape, shapes[j].startx, shapes[j].starty, shapes[j].endx, shapes[j].endy);
        }
        if (isPointIn === 1) {
            list.push(j);
        }
    }
    //删除该点所经过的对象
    for (k = 0; k < list.length; k++) {
        var n = list[k] - k;
        shapes.splice(n, 1);
    }
    //恢复当前画布属性到使用橡皮擦之前
    cxt.strokeStyle = document.getElementById("color").value;
    cxt.lineWidth = current_size;
}


//判断当前画笔粗细，来决定橡皮擦的误差
function judgeError(num) {
    switch (num) {
        case 1:
            return 2;
        case 3:
            return 2;
        case 5:
            return 3;
        case 10:
            return 4;
        case 15:
            return 8;
        case 20:
            return 11;
    }
}


//清除画布
function clear() {
    // 提示该操作不可逆
    var reminder = confirm("The operation is irreversible!");
    // 如果确认要清除，初始化界面
    if (reminder) {
        cxt.clearRect(0, 0, canvas.width, canvas.height);
        endX = null;
        shapes = [];
    }
}

//设置笔画粗细
function line_Width(new_width) {
    cxt.lineWidth = new_width;
}

//保存当前画布
function reserve() {
    //清空localstorage
    localStorage.clear();
    //把当前对象保存到localstorage
    for (var j = 0 in shapes) {
        var json = JSON.stringify(shapes[j]);
        localStorage.setItem(j.toString(), json);
    }
}

//打开之前保存的画布
function open() {
    //清空画布，清空对象
    cxt.clearRect(0, 0, canvas.width, canvas.height);
    shapes = [];
    //把locastorag的对象添加到当前的对象组
    for (var j = 0; j < localStorage.length; j++) {
        var json = localStorage.getItem(j.toString());
        shapes[j] = JSON.parse(json);
    }
    // 在画布上输出这些对象
    for (var j = 0 in shapes) {
        console.log(j, shapes[j]);
        cxt.strokeStyle = shapes[j].color;
        cxt.lineWidth = shapes[j].size;
        draw_picture(shapes[j].Shape, shapes[j].startx, shapes[j].starty, shapes[j].endx, shapes[j].endy);
    }
}

function b64ToUint8Array(b64Image) {
    var img = atob(b64Image.split(',')[1]);
    var img_buffer = [];
    var i = 0;
    while (i < img.length) {
        img_buffer.push(img.charCodeAt(i));
        i++;
    }
    return new Uint8Array(img_buffer);
}

//将图片转为canvas显示
function convertImageToCanvas(image) {
    cxt.getContext("2d").drawImage(image, 0, 0);
    return cxt;
}

//将canvas转为图片
function convertCanvasToImage(canvas) {
    var b64Image = canvas.toDataURL('image/png');
    var u8Image  = b64ToUint8Array(b64Image);
    return u8Image;
}

//菜单栏的响应式设计
document.getElementById("color").onchange = function () {
    cxt.strokeStyle = this.value
};
var x = document.getElementsByName("shape");
// 用shape_click记录当前被选中的按钮，默认为直线，直线的背景设为pink
var shape_click = 0;
x[0].style.background = "pink";
for (var j = 0; j < x.length; j++) {
    var select_shape = j;
    //当鼠标移到图形选项上时，背景色变成蓝色
    x[j].onmouseover = (function (select_shape) {
        return function () {
            var Select = select_shape;
            if (shape_click != Select)
                this.style.background = "skyblue";
        }
    })(select_shape);
    //当鼠标离开时，恢复原来的背景色
    x[j].onmouseout = (function (select_shape) {
        var Select = select_shape;
        return function () {
            if (shape_click != Select)
                this.style.background = "black";
        }
    })(select_shape);
    //当选中该图形选项时，背景色变为粉色
    x[j].onclick = (function (select_shape) {
        var Select = select_shape;
        return function () {
            recover(shape_click);
            shape_click = Select;
            this.style.background = "pink";
        }
    })(select_shape);
}


//当图形选项改变时，恢复上一个被选中选项的背景色
function recover(shape_num) {
    document.getElementsByName("shape")[shape_num].style.background = "black";
}

Line.addEventListener("click", line, false);
Circle.addEventListener("click", circle, false);
Rectangle.addEventListener("click", rectangle, false);
Pencil.addEventListener("click", pencil, false);
Rubber.addEventListener("click", rubber, false);
Clear.addEventListener("click", clear, false);
Reserve.addEventListener("click", reserve, false);
Open.addEventListener("click", open, false);

canvas.addEventListener("mousedown", StartPos, false);
myCans.addEventListener("mousedown", Mousedown, false);
canvas.addEventListener("mouseout", MouseOut, false);
myCans.addEventListener("mouseup", Mouseup, false);
myCans.addEventListener("mousemove", EndPos, false);
myCans.addEventListener("mousemove", draw, false);