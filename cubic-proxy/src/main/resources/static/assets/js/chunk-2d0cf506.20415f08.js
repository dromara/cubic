(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0cf506"],{"62d8":function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"app-container"},[s("div",{staticClass:"filter-container"},[s("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:"IP",size:"mini"},model:{value:t.vm.ip,callback:function(e){t.$set(t.vm,"ip",e)},expression:"vm.ip"}}),t._v(" "),s("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:"Port",size:"mini"},model:{value:t.vm.port,callback:function(e){t.$set(t.vm,"port",e)},expression:"vm.port"}}),t._v(" "),s("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{placeholder:"AgentId",size:"mini"},model:{value:t.vm.agentId,callback:function(e){t.$set(t.vm,"agentId",e)},expression:"vm.agentId"}}),t._v(" "),s("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{size:"mini",type:"success"},on:{click:t.startConnect}},[t._v("\n      连接\n    ")]),t._v(" "),s("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{size:"mini",type:"danger"},on:{click:t.disconnect}},[t._v("\n      断开\n    ")])],1),t._v(" "),s("div",{staticClass:"xterm",attrs:{id:"xterm"}})])},i=[],r=(s("6b54"),s("aef6"),s("fcf3")),o=s("47d0"),c=s("5995"),a=(s("abb2"),{name:"WebShell",data:function(){return{vm:{ip:"localhost",port:11901,agentId:"cubic"},socket:null,term:null,result:"",currType:"1",state:"",type:[1,3],msg:{1:{text:"进入自定义命令模式 "},3:{text:"进入 arthas命令模式 "}},rows:400,cols:100}},mounted:function(){this.startConnect(),this.interval()},methods:{initXterm:function(){this.term=new r["Terminal"]({cursorBlink:!0,cursorStyle:"block",screenKeys:!0,theme:{foreground:"#7e9192",background:"#002833",cursor:"help",lineHeight:16}});var t=new o["FitAddon"];this.term.loadAddon(t),this.term.loadAddon(new c["WebLinksAddon"]),this.term.open(document.getElementById("xterm")),t.fit(),this.socket=new WebSocket("ws://"+this.vm.ip+":"+this.vm.port+"/ws"),this.term.focus(),this.term.writeln(" "),this.term.writeln(" 欢迎使用代理终端，此终端可连接到目标机器进行命令操作"),this.term.writeln(" 命令分为两类，自定义命令，Arthas命令（Arthas 命令请查看文档 https://alibaba.github.io/arthas/commands.html）"),this.term.writeln(" 输入 1 回车进入自定义命令模式（默认）"),this.term.writeln(" 输入 3 回车进入Arthas命令模式"),this.term.writeln(" 正在连接。。。")},prompt:function(){this.term.write("\r\n$ ")},sendMessage:function(t,e){"clear"!==e?null!=this.socket&&this.socket.send(JSON.stringify({instanceUuid:this.vm.agentId,type:this.currType,command:e})):this.term.clear()},switchState:function(t){return"1"!==t&&"3"!==t||(this.currType=t,this.prompt(),this.term.write(this.msg[t].text+"\r\n"),this.result="",!1)},socketOnMessage:function(){var t=this;this.socket.onmessage=function(e){if(console.log(e),"message"===e.type){var s=e.data;t.term.write(s),s.toString().trimEnd().endsWith("$")||3===t.currType||t.prompt()}}},termOnKey:function(){var t=this;console.log(this.term),this.term.onKey((function(e){var s=!e.domEvent.altKey&&!e.domEvent.ctrlKey;if("Enter"===e.domEvent.code){var n=t.switchState(t.result);n&&(t.sendMessage(t.vm.agentId,t.result),t.result=""),t.prompt()}else"Backspace"===e.domEvent.code?t.term._core.buffer.x>2&&(t.result=t.result.substring(0,t.result.length-1),t.term.write("\b \b")):s&&(t.term.write(e.key),t.result+=e.key)}))},socketOnOpen:function(){var t=this;this.socket.onopen=function(){t.term.writeln(" 已连接, 当前 host: "+t.vm.ip+", agentId: "+t.vm.agentId+", findState: "+t.state),t.prompt(),t.termOnKey(),t.socketOnMessage();var e=t;document.addEventListener("paste",(function(t){var s=t.clipboardData.getData("text/plain");e.term.write(s),e.result+=s,t.stopPropagation()}))}},socketOnClose:function(){var t=this;this.socket.onclose=function(){null!=t.term&&(t.term.writeln(" websocket channel close, please reconnect!"),t.prompt())}},socketOnError:function(){var t=this;this.socket.onerror=function(){t.socket=null,t.term.writeln(" websocket channel error ,will close this,please reconnect!"),t.prompt()}},disconnect:function(){try{null!=this.socket&&(this.socket.send(JSON.stringify({instanceUuid:this.vm.agentId,type:999,command:this.result})),this.socket.close(),this.socket=null,console.log("closed ws succ!")),null!=this.term&&(this.term.dispose(),console.log("dispose xterm succ!")),console.log("Connection was closed successfully!")}catch(t){console.log("No connection, please start connect first.")}},startConnect:function(){null!=this.socket&&null!=this.term&&this.disconnect(),this.initXterm(),this.socketOnClose(),this.socketOnOpen(),this.socketOnError()},interval:function(){var t=this;window.setInterval((function(){null!==t.socket&&t.socket.send(JSON.stringify({instanceUuid:t.vm.agentId,type:0,command:1}))}),5e3)}}}),l=a,m=s("2877"),u=Object(m["a"])(l,n,i,!1,null,null,null);e["default"]=u.exports}}]);