let ws;
let xterm;
let type = [1, 3, 4]
let currType = 1;
let result = '';
let appId = '';
let state = false;
// let address = ["matrix-proxy-01.secoolocal.com:6080", "matrix-proxy-02.secoolocal.com:6080"];
// let address = ["matrix-proxy01.siku.cn", "matrix-proxy02.siku.cn"];
let address = ["localhost:6080", "localhost:6080"];
// let address = ["matrix-proxy01.siku.cn", "matrix-proxy.siku.cn"];

// window.CMDTYPE = {
//         "matrix-proxy-01.secoolocal.com:6080": 'matrix-proxy-01.secoolocal.com',
//         "matrix-proxy-02.secoolocal.com:6080":"matrix-proxy-02.secoolocal.com"
// }
window.CMDTYPE = {
    1: {
        text: '进入自定义命令模式 '
    },
    3: {
        text: '进入 arthas命令模式 '
    },
    4: {
        text: 'switch mode linux  command '
    }
}

window.addEventListener('message', function (event) {

    if (event.data == '' || event.data == null) {
        console.log(event.data)
        return;
    }
    var data = JSON.parse(event.data);
    if (data.event_id === 'datachange') {
        display();
        appId = data.data.id;
        console.log("切换agenId: " + appId)
        if (appId == '' || appId == null) {
            return;
        }
        $('#agentId').val(appId);
        console.log("开始关闭上一次连接")
        disconnect();
        console.log("启动websocket连接")
        startConnect();
    }
}, false)


function display() {
    // $('#agentIddev').hide();
    // $('#ipdev').hide();
    $('#portdev').hide();
    // $('#butdev').hide();
}

function disposeParam(data) {

    if (data == '' || data == null) {
        return;
    }
    let msg = data.toString().split(":");
    let ip = msg[0];

    if (ip != '' && ip != null) {
        $('#ip').val(ip);
    } else {
        $('#ip').val(window.location.hostname);
    }

}


/** get params in url **/
function getUrlParam(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function getCharSize() {
    var tempDiv = $('<div />').attr({'role': 'listitem'});
    var tempSpan = $('<div />').html('qwertyuiopasdfghjklzxcvbnm');
    tempDiv.append(tempSpan);
    $("html body").append(tempDiv);
    var size = {
        width: tempSpan.outerWidth() / 26,
        height: tempSpan.outerHeight(),
        left: tempDiv.outerWidth() - tempSpan.outerWidth(),
        top: tempDiv.outerHeight() - tempSpan.outerHeight(),
    };
    tempDiv.remove();
    return size;
}

function getWindowSize() {
    var e = window;
    var a = 'inner';
    if (!('innerWidth' in window)) {
        a = 'client';
        e = document.documentElement || document.body;
    }
    var terminalDiv = document.getElementById("terminal-card");
    var terminalDivRect = terminalDiv.getBoundingClientRect();
    return {
        width: terminalDivRect.width,
        height: e[a + 'Height'] - terminalDivRect.top
    };
}

function prompt() {
    xterm.write('\r\n$ ');
}

function getTerminalSize() {
    var charSize = getCharSize();
    var windowSize = getWindowSize();
    console.log('charsize');
    console.log(charSize);
    console.log('windowSize');
    console.log(windowSize);
    return {
        cols: Math.floor((windowSize.width - charSize.left) / 10),
        rows: Math.floor((windowSize.height - charSize.top) / 17)
    };
}

/** init websocket **/
function initWs(ip, port) {
    var path = 'ws://' + ip + ':' + port + '/ws';
    ws = new WebSocket(path);
}

/** init xterm **/
function initXterm(cols, rows) {
    xterm = new Terminal({
        cols: cols,
        rows: rows,
        cursorBlink: true, // 光标闪烁
        cursorStyle: "block", // 光标样式  null | 'block' | 'underline' | 'bar'
        scrollback: 800, //回滚
        screenKeys: true
    });
}

function initParams() {

    let id = appId;
    let fin = false;
    address.forEach(function (val) {

        try {
            if (fin) {
                return;
            }
            let request = new XMLHttpRequest();

            if (id == null | id == '') {
                id = $('#agentId').val();
            }
            request.open('GET', 'http://' + val + '/proxy/checkEAgentxist?agentId=' + id, false);
            request.send();
            console.log("result:" + request.responseText)
            if (request.responseText == 'true') {
                console.log("读取到agentId 机器信息：" + val);
                disposeParam(val);
                fin = true;
                state = fin;
            }
        } catch (e) {
            console.log(e)
        }


    });

}

function openXterm() {
    $('#fullSc').show();
    var terminalSize = getTerminalSize()
    // init xterm
    initXterm(terminalSize.cols, terminalSize.rows)
    xterm.open(document.getElementById('terminal'));
    xterm.writeln('欢迎使用代理终端，此终端可连接到目标机器进行命令操作');
    xterm.writeln('命令分为两类，自定义命令，Arthas命令（Arthas 命令请查看文档 https://alibaba.github.io/arthas/commands.html）');
    xterm.writeln('输入 1 回车进入自定义命令模式（默认）');
    xterm.writeln('输入 3 回车进入Arthas命令模式');
}
//清理缓存数据
function clearCacheData() {
    result = "";
}
/** begin connect **/
function startConnect(silent) {
    clearCacheData();

    if(ws != null && xterm != null){
        disconnect();
    }

    initParams();

    var agentId = $('#agentId').val();
    var ip = $('#ip').val();
    var port = $('#port').val();


    if (ip == '' || ip == null) {
        alert("ip can not null!");
        return;
    }


    if (agentId == '' || agentId == null) {
        alert("agentId can not null!");
        return;
    }

    if (ws != null) {
        alert('Already connected');
        return;
    }
    openXterm();

    // init webSocket
    initWs(ip, port);
    ws.onerror = function () {
        ws = null;
        xterm.writeln('websocket channel error ,will close this,please reconnect!');
        prompt()
    };
    ws.onclose = function (message) {
        if (xterm != null) {
            xterm.writeln('websocket channel close, please reconnect!');
            prompt()
        }
    };
    ws.onopen = function () {
        xterm.writeln('webscoket channel is open, host: ' + ip + ' agentId: ' + agentId + ' findState: ' + state);
        prompt();
        ws.onmessage = function (event) {
            console.log(event)
            if (event.type === 'message') {
                var d = event.data;
                xterm.write(d);
                if (!d.toString().trimEnd().endsWith("$") && currType != 3) {
                    prompt();
                }
            }
        };
        let copy = "";

        //获取选中时间
        xterm.onSelectionChange(function () {
            if (xterm.hasSelection()) {
                copy = xterm.getSelection();
            }
        });


        //监听copy事件
        document.addEventListener('copy', function(e){
            e.clipboardData.setData('text/plain', copy);
            e.preventDefault(); // We want our data, not data from any selection, to be written to the clipboard
        });


        //监听粘贴
        document.addEventListener('paste', function(e){
            let c = e.clipboardData.getData('text/plain');
            xterm.write(c);
            result += c;
            e.stopPropagation();
        });




        xterm.onKey(e => {
            const printable = !e.domEvent.altKey && !e.domEvent.altGraphKey && !e.domEvent.ctrlKey && !e.domEvent.metaKey;

                if (e.domEvent.keyCode === 13) {

                const typeSwtich = switchState(result);

                if (typeSwtich) {
                    sendMessage(agentId, result);
                    result = '';
                }
                prompt();
            } else if (e.domEvent.keyCode === 8) {
                // Do not delete the prompt
                if (xterm._core.buffer.x > 2) {
                    result = result.substring(0, result.length - 1)
                    xterm.write('\b \b');
                }
            } else if (printable) {
                xterm.write(e.key);
                result += e.key

            }
        });


        // ws.send(JSON.stringify({action: 'resize', cols: terminalSize.cols, rows: terminalSize.rows}));
        window.setInterval(function () {
            if (ws != null) {
                ws.send(JSON.stringify({
                    instanceUuid: agentId,
                    type: 0,
                    command: ""
                }));
            }
        }, 30000);
    }
}


function sendMessage(agentId, data) {

    if (data == 'clear') {
        xterm.clear();
        return;
    }
    ws.send(JSON.stringify({
        instanceUuid: agentId,
        type: currType,
        command: data
    }))
}

function switchState(data) {
    if (data == 1 || data == 3 || data == 4) {
        currType = data;

        prompt();
        xterm.write(window.CMDTYPE[data].text + '\r\n');
        result = '';
        return false;
    }
    return true;
}

function disconnect() {
    try {
        // ws.onmessage = null;
        // ws.onclose = null;
        if (ws != null) {
            ws.send(JSON.stringify({
                instanceUuid: appId,
                type: 999,
                command: result
            }))
            ws.close();
            ws = null;
            console.log('closed ws succ!');
        }
        if (xterm != null) {
            xterm.dispose();
            console.log('dispose xterm succ!');
            $('#fullSc').hide();
        }


        console.log('Connection was closed successfully!');
    } catch (e) {
        console.log('No connection, please start connect first.');
    }
}

/** full screen show **/
function xtermFullScreen() {
    var ele = document.getElementById('terminal-card');
    requestFullScreen(ele);
}

function requestFullScreen(element) {
    var requestMethod = element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen;
    if (requestMethod) {
        requestMethod.call(element);
    } else if (typeof window.ActiveXObject !== "undefined") {
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript !== null) {
            wscript.SendKeys("{F11}");
        }
    }
}
