var canvas = document.getElementById("cans");
var cxt = canvas.getContext("2d");
var startX, startY, endX, endY;
var data;
var shapes = new Array();
var mousedown, mouseout;
var shape = 1;
var myCans = document.getElementById("myCans");

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


//�����������ȡ��ʼ���꣬���ڼ��˱��������������һ��ƫ����
function StartPos(e) {
    mousedown = 0;
    mouseout = 0;
    var rect = canvas.getBoundingClientRect();
    startX = e.clientX - rect.left * (canvas.width / rect.width);
    startY = e.clientY - rect.top * (canvas.height / rect.height);
    //����������ߣ������ö���
    if (shape == 4) {
        create_shape(4, startX, startY, endX, endY);
        shapes[shapes.length - 1].x.push(startX);
        shapes[shapes.length - 1].y.push(startY);
    }
    //�������Ƥ�������ò������ܺ���
    if (shape == 5) {
        delete_picture();
    }
    //���浱ǰ����
    data = cxt.getImageData(0, 0, canvas.width, canvas.height);
}


// ��ȡ�յ�����
function EndPos(e) {
    if (startX != null) {
        var rect = canvas.getBoundingClientRect();
        endX = e.clientX - rect.left * (canvas.width / rect.width);
        endY = e.clientY - rect.top * (canvas.height / rect.height);
    }
}


// �ɿ����
function Mouseup() {
    if (startX != null && endX != null && shape != 5 && shape != 4 && !(mousedown == 1 && mouseout == 1)) {
        //������ͼ�ζ��󣬲������������
        create_shape(shape, startX, startY, endX, endY);
        endX = null;
    }
    startX = null;
}


// �������
function Mousedown() {
    //���������ڻ����ⰴ�µģ�mousedown=1
    if (mouseout == 1)
        mousedown = 1;
}

// ����Ƴ��˻�����mouseout=1
function MouseOut() {
    mouseout = 1;
}

// ѡ��ֱ��
function line() {
    shape = 1;
}

// ѡ��Բ
function circle() {
    shape = 2
}

// ѡ�񻭾���
function rectangle() {
    shape = 3;
}

// ѡ��������
function pencil() {
    shape = 4;
}

// ѡ����Ƥ��
function rubber() {
    shape = 5;
}

// ����ƶ������л���
function draw() {
    // �����ʼ���겻Ϊ�ն��Ҳ�����Ƥ��״̬
    if (startX != null && shape != 5) {
        //�������������״̬����յ�ǰ������չʾ��һ������״̬
        if (shape != 4) {
            cxt.clearRect(0, 0, canvas.width, canvas.height);
            cxt.putImageData(data, 0, 0);
        }
        //���û�ͼ������ͼ
        draw_picture(shape, startX, startY, endX, endY);
    }
}

// ��ͼ
function draw_picture(Shape, startx, starty, endx, endy) {
    switch (Shape) {
        case 1://ֱ��
            cxt.beginPath();
            cxt.moveTo(startx, starty);
            cxt.lineTo(endx, endy);
            cxt.stroke();
            cxt.closePath();
            break;
        case 2://Բ
            var temp = Math.sqrt(Math.pow((endx - startx), 2) + Math.pow((endy - starty), 2));
            cxt.beginPath();
            cxt.arc(startx, starty, temp, 0, Math.PI * 2, true);
            cxt.stroke();
            cxt.closePath();
            break;
        case 3://����
            cxt.beginPath();
            cxt.rect(startx, starty, endx - startx, endy - starty);
            cxt.stroke();
            cxt.closePath();
            break;
        case 4://������
            draw_pencil();
    }
}

//��������
function draw_pencil() {
    //���������Ƥ��״̬����¼�߹���λ������
    if (shape !== 5) {
        shapes[shapes.length - 1].x.push(endX);
        shapes[shapes.length - 1].y.push(endY);
    }
    //��������
    cxt.beginPath();
    cxt.lineJoin = "round";
    cxt.moveTo(startX, startY);
    cxt.lineTo(endX, endY);
    cxt.stroke();
    cxt.closePath();
    startX = endX;
    startY = endY;
}

//��Ƥ�����ܺ�����������Ƥ�ҵĹ�����ɾ�����λ�������ڵ�ͼ�ζ���
function delete_picture() {
    cxt.clearRect(0, 0, canvas.width, canvas.height);	// �������
    var list = new Array();// ��list��¼��Ҫɾ���Ķ�����±�
    var current_size = cxt.lineWidth;// ��current_size��¼��ǰ��lineWidth
    for (var j = 0 in shapes) {
        var isPointIn = -1;// ��־�õ��Ƿ���ڵ�ǰ������
        var Error = judgeError(shapes[j].size);
        switch (shapes[j].Shape) {
            case 1: // ֱ��
                //�жϵ㵽ֱ�ߵľ���
                var a = shapes[j].endy - shapes[j].starty;
                var b = shapes[j].startx - shapes[j].endx;
                var c = -shapes[j].endy * shapes[j].startx + shapes[j].endx * shapes[j].starty;
                var distance = Math.pow((a * startX + b * startY + c), 2) / (Math.pow(a, 2) + Math.pow(b, 2));
                //����þ���С��2������Ϊ�õ���ֱ���ϣ�ɾ���ö��󣬷��򻭳���ֱ��
                if (distance < (Error * Error)) {
                    isPointIn = 1;
                    break;
                }
                isPointIn = 0;
                break;

            case 2:// Բ
                var x1 = shapes[j].startx, y1 = shapes[j].starty, x2 = shapes[j].endx, y2 = shapes[j].endy;
                var radius = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
                cxt.arc(shapes[j].startx, shapes[j].starty, radius, 0, Math.PI * 2, true);
                // �жϸõ㵽Բ�ĵľ��룬��뾶���бȽ�
                var distance = Math.sqrt(Math.pow((x1 - startX), 2) + Math.pow((y1 - startY), 2));
                if (distance <= radius + Error && distance >= radius - Error) {
                    isPointIn = 1;
                    break;
                }
                isPointIn = 0;
                break;
            case 3://����
                // �жϸõ��Ƿ��ھ��α��ϣ�����ɾ���ö��󣬼�¼�±꣬���򻭳��þ���
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
                //����ȡ�������߹켣�е��������㣬������������Ϊ�ԽǶ��㣬������
                for (k = 0; k < shapes[j].x.length - 1; k++) {
                    var y1 = Math.max(shapes[j].y[k], shapes[j].y[k + 1]);
                    var y2 = Math.min(shapes[j].y[k], shapes[j].y[k + 1]);
                    var x1 = Math.max(shapes[j].x[k], shapes[j].x[k + 1]);
                    var x2 = Math.min(shapes[j].x[k], shapes[j].x[k + 1]);
                    cxt.rect(shapes[j].x[k], shapes[j].y[k], shapes[j].x[k + 1]
                        - shapes[j].x[k], shapes[j].y[k + 1] - shapes[j].y[k]);
                    //����õ�����������У�����Ϊ�õ�����������ϣ�ɾ������
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
                //�õ㲻�ڶ����ϣ����õ�ǰ����������ö���һ�£�������������
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
        //�����ͼ�β��������ߣ��Ҹõ㲻�ڶ����ϣ����õ�ǰ����������ö���һ�£�������ͼ��
        if (isPointIn === 0 && shapes[j].Shape !== 4) {
            cxt.lineWidth = shapes[j].size;
            cxt.strokeStyle = shapes[j].color;
            draw_picture(shapes[j].Shape, shapes[j].startx, shapes[j].starty, shapes[j].endx, shapes[j].endy);
        }
        if (isPointIn === 1) {
            list.push(j);
        }
    }
    //ɾ���õ��������Ķ���
    for (k = 0; k < list.length; k++) {
        var n = list[k] - k;
        shapes.splice(n, 1);
    }
    //�ָ���ǰ�������Ե�ʹ����Ƥ��֮ǰ
    cxt.strokeStyle = document.getElementById("color").value;
    cxt.lineWidth = current_size;
}


//�жϵ�ǰ���ʴ�ϸ����������Ƥ�������
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


//�������
function clear() {
    // ��ʾ�ò���������
    var reminder = confirm("The operation is irreversible!");
    // ���ȷ��Ҫ�������ʼ������
    if (reminder) {
        cxt.clearRect(0, 0, canvas.width, canvas.height);
        endX = null;
        shapes = [];
    }
}

//���ñʻ���ϸ
function line_Width(new_width) {
    cxt.lineWidth = new_width;
}

//���浱ǰ����
function reserve() {
    //���localstorage
    localStorage.clear();
    //�ѵ�ǰ���󱣴浽localstorage
    for (var j = 0 in shapes) {
        var json = JSON.stringify(shapes[j]);
        localStorage.setItem(j.toString(), json);
    }
}


//��֮ǰ����Ļ���
function open() {
    //��ջ�������ն���
    cxt.clearRect(0, 0, canvas.width, canvas.height);
    shapes = [];
    //��locastorag�Ķ�����ӵ���ǰ�Ķ�����
    for (var j = 0; j < localStorage.length; j++) {
        var json = localStorage.getItem(j.toString());
        shapes[j] = JSON.parse(json);
    }
    // �ڻ����������Щ����
    for (var j = 0 in localStorage) {
        cxt.strokeStyle = shapes[j].color;
        cxt.lineWidth = shapes[j].size;
        draw_picture(shapes[j].Shape, shapes[j].startx, shapes[j].starty, shapes[j].endx, shapes[j].endy);
    }
}


//�˵�������Ӧʽ���
document.getElementById("color").onchange = function () {
    cxt.strokeStyle = this.value
};
var x = document.getElementsByName("shape");
// ��shape_click��¼��ǰ��ѡ�еİ�ť��Ĭ��Ϊֱ�ߣ�ֱ�ߵı�����Ϊpink
var shape_click = 0;
x[0].style.background = "pink";
for (var j = 0; j < x.length; j++) {
    var select_shape = j;
    //������Ƶ�ͼ��ѡ����ʱ������ɫ�����ɫ
    x[j].onmouseover = (function (select_shape) {
        return function () {
            var Select = select_shape;
            if (shape_click != Select)
                this.style.background = "skyblue";
        }
    })(select_shape);
    //������뿪ʱ���ָ�ԭ���ı���ɫ
    x[j].onmouseout = (function (select_shape) {
        var Select = select_shape;
        return function () {
            if (shape_click != Select)
                this.style.background = "black";
        }
    })(select_shape);
    //��ѡ�и�ͼ��ѡ��ʱ������ɫ��Ϊ��ɫ
    x[j].onclick = (function (select_shape) {
        var Select = select_shape;
        return function () {
            recover(shape_click);
            shape_click = Select;
            this.style.background = "pink";
        }
    })(select_shape);
}


//��ͼ��ѡ��ı�ʱ���ָ���һ����ѡ��ѡ��ı���ɫ
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