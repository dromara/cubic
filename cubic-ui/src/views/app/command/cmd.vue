<template>
  <div class="app-container">
    <!--    <div class="filter-container">-->
    <!--      <el-input v-model="vm.ip" placeholder="IP" size="mini" style="width: 200px;" class="filter-item" />-->
    <!--      <el-input v-model="vm.port" placeholder="Port" size="mini" style="width: 200px;" class="filter-item" />-->
    <!--      <el-input v-model="vm.agentId" placeholder="AgentId" size="mini" style="width: 200px;" class="filter-item" />-->
    <!--      <el-button class="filter-item" size="mini" type="success" @click="startConnect">-->
    <!--        连接-->
    <!--      </el-button>-->
    <!--      <el-button class="filter-item" size="mini" type="danger" @click="disconnect">-->
    <!--        断开-->
    <!--      </el-button>-->
    <!--    </div>-->
    <div id="xterm" class="xterm" />
  </div>

</template>
<script>
import { Terminal } from 'xterm'
import { FitAddon } from 'xterm-addon-fit'
// import { AttachAddon } from 'xterm-addon-attach'
import { WebLinksAddon } from 'xterm-addon-web-links'
import 'xterm/css/xterm.css'
export default {
  name: 'WebShell',
  data() {
    return {
      vm: {
        ip: '47.104.79.116',
        port: 11901,
        agentId: 'cubic'
      },
      socket: null,
      term: null,
      result: '',
      currType: '3',
      state: '',
      type: [1, 3],
      msg: {
        '1': {
          text: '进入自定义命令模式 '
        },
        '3': {
          text: '进入 arthas命令模式 '
        }},
      rows: 400,
      cols: 100
    }
  },
  created() {
    //
    // if (Object.keys(this.$route.query).length > 0) {
    //   this.vm.agentId = this.$route.query.id
    // }
    console.log(this.$cookies.get('appId'))

    this.vm.agentId = this.$cookies.get('appId')
  },
  mounted() {
    // 实例化终端并设置参数
    this.startConnect()
    this.interval()
    // window.addEventListener('resize', resizeScreen);

    // // 监听resize,当窗口拖动的时候,监听事件,实时改变xterm窗口
    // window.addEventListener('resize', this.debounce(this.resizeScreen, 1500), false)
  },
  methods: {
    initXterm() {
      // 获取容器宽高/字号大小，定义行数和列数
      this.rows = document.querySelector('.app-container').offsetHeight
      this.cols = document.querySelector('.app-container').offsetWidth

      const _this = this
      this.term = new Terminal({
        cursorBlink: true, // 光标闪烁
        rows: parseInt(_this.rows), // 行数
        cols: parseInt(_this.cols), // 不指定行数，自动回车后光标从下一行开始
        cursorStyle: 'block', // 光标样式  null | 'block' | 'underline' | 'bar'
        screenKeys: true,
        theme: {
          foreground: '#7e9192', // 字体
          background: '#002833', // 背景色
          cursor: 'help', // 设置光标
          lineHeight: 16
        }
      })
      // 加载自适应组件
      const fitAddon = new FitAddon()
      this.term.loadAddon(fitAddon)
      // 加载weblink组件
      this.term.loadAddon(new WebLinksAddon())

      // 在绑定的组件上初始化窗口
      this.term.open(document.getElementById('xterm'))
      // 窗口初始化后,按照浏览器窗口自适应大小
      fitAddon.fit()

      // 这里还把窗口的column和row传入后端,使其能自动针对前端窗口边框改为输出
      this.socket = new WebSocket('ws://' + this.vm.ip + ':' + this.vm.port + '/ws')

      // xterm的socket组件与websocket实例结合
      // this.term.loadAddon(new AttachAddon(this.socket))
      // // 聚焦
      this.term.focus()
      this.term.writeln(' ')
      this.term.writeln(' > 欢迎进入Arthas代理终端，此终端可连接到目标机器进行命令操作')
      this.term.writeln('')
      this.term.writeln(' > Arthas 命令请查看文档 https://alibaba.github.io/arthas/commands.html')
      // this.term.writeln(' 输入 1 回车进入自定义命令模式（默认）')
      // this.term.writeln(' 输入 3 回车进入Arthas命令模式')
      this.term.writeln('')
      this.term.writeln(' > 正在连接。。。')
      this.term.writeln('')
    },
    resizeScreen(size) {
      console.log('size', size)
      try {
        this.fitAddon.fit()

        const _this = this
        // 窗口大小改变时触发xterm的resize方法，向后端发送行列数，格式由后端决定
        this.term.onResize(size => {
          _this.sendMessage(_this.agentId, { Op: 'resize', Cols: size.cols, Rows: size.rows })
        })
      } catch (e) {
        console.log('e', e.message)
      }
    },
    prompt() {
      this.term.write('\r\n$ ')
    },
    sendMessage(agentId, data) {
      if (data === 'clear') {
        this.term.clear()
        return
      }
      if (this.socket == null) {
        return
      }
      this.socket.send(JSON.stringify({
        instanceUuid: this.vm.agentId,
        type: this.currType,
        command: data
      }))
    },
    switchState(data) {
      if (data === '1' || data === '3') {
        this.currType = data

        this.prompt()
        this.term.write(this.msg[data].text + '\r\n')
        this.result = ''
        return false
      }
      return true
    },
    socketOnMessage() {
      const vueThis = this
      this.socket.onmessage = function(event) {
        console.log(event)
        if (event.type === 'message') {
          const d = event.data
          vueThis.term.write(d)
          if (!d.toString().trimEnd().endsWith('$') && vueThis.currType !== 3) {
            vueThis.prompt()
          }
        }
      }
    },
    termOnKey() {
      console.log(this.term)
      this.term.onKey(e => {
        const printable = !e.domEvent.altKey && !e.domEvent.ctrlKey

        if (e.domEvent.code === 'Enter') {
          const typeSwtich = this.switchState(this.result)

          if (typeSwtich) {
            this.sendMessage(this.vm.agentId, this.result)
            this.result = ''
          }
          this.prompt()
        } else if (e.domEvent.code === 'Backspace') {
          // Do not delete the prompt
          if (this.term._core.buffer.x > 2) {
            this.result = this.result.substring(0, this.result.length - 1)
            this.term.write('\b \b')
          }
        } else if (printable) {
          this.term.write(e.key)
          this.result += e.key
        }
      })
    },
    socketOnOpen() {
      this.socket.onopen = () => {
        this.term.writeln(' > 已连接, 当前 IP : ' + this.vm.ip + ', ID: ' + this.vm.agentId)
        this.prompt()
        this.termOnKey()
        this.socketOnMessage()

        const tis = this
        // let copy = ''
        //
        // this.term.attachCustomKeyEventHandler(function(ev) {
        //   // 粘贴 ctrl+v
        //   if (ev.code === 'KeyV' && (ev.ctrlKey || ev.metaKey)) {
        //     tis.term.write(copy)
        //     tis.result += copy
        //     // websocket.send(new TextEncoder().encode("\x00" + this.copy));
        //   }
        // })
        // // 获取选中时间
        // this.term.onSelectionChange(function() {
        //   if (tis.term.hasSelection()) {
        //     copy = tis.term.getSelection()
        //   }
        // })
        // //监听粘贴

        document.addEventListener('paste', function(e) {
          const c = e.clipboardData.getData('text/plain')
          tis.term.write(c)
          tis.result += c
          e.stopPropagation()
        })
      }
    },
    socketOnClose() {
      this.socket.onclose = () => {
        if (this.term != null) {
          this.term.writeln(' websocket channel close, please reconnect!')
          this.prompt()
        }
      }
    },
    socketOnError() {
      this.socket.onerror = () => {
        this.socket = null
        this.term.writeln(' websocket channel error ,will close this,please reconnect!')
        this.prompt()
      }
    },
    disconnect() {
      try {
        if (this.socket != null) {
          this.socket.send(JSON.stringify({
            instanceUuid: this.vm.agentId,
            type: 999,
            command: this.result
          }))
          this.socket.close()
          this.socket = null
          console.log('closed ws succ!')
        }
        if (this.term != null) {
          this.term.dispose()
          console.log('dispose xterm succ!')
          // $('#fullSc').hide();
        }
        console.log('Connection was closed successfully!')
      } catch (e) {
        console.log('No connection, please start connect first.')
      }
    },
    startConnect() {
      this.disconnect()
      this.initXterm()
      this.socketOnClose()
      this.socketOnOpen()
      this.socketOnError()
    },
    interval() {
      const vue = this
      window.setInterval(() => {
        if (vue.socket !== null) {
          vue.socket.send(JSON.stringify({
            instanceUuid: vue.vm.agentId,
            type: 0,
            command: 1
          }))
        }
      }, 5000)
    }
  }
}
</script>
