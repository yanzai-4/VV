package AI.Memory.Reminisce.Model.Message;

// ##### 简介 #####
// 回忆节点搜索抗性计时指令
// 继承超线程功能
// 用于决定节点抗性的时长

// ##### 日志 #####
// 1.0 版本
// 简单构建
// 2.0
// 重命名
// 2.1
// 适配接口
// 2.2
// 修改报错内容
// done

import AI.Mind.General.Debug;
import AI.Memory.Reminisce.Model.Node.ReminisceNode;

public class ReminisceMsgResist extends Thread {

    // ##### 变量 #####
    // 回忆节点地址
    private ReminisceNode node;
    // 等待时间 60秒
    private final int time = 60000;
    // 报错信息
    private String info;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新指令
    public ReminisceMsgResist(ReminisceNode input_node) {
        node = input_node;
        info = input_node.toInfo();
    }

    // ##### 功能函数 #####
    // 复写超线程功能
    @Override
    public void run() {
        try {
            debug.log("#ReminisceBase       <Node>         -DeActive: " + info);
            Thread.sleep(time);
            node.active();
            debug.log("#ReminisceBase       <Node>         -Active: " + info);
        }
        catch (InterruptedException e) {
            debug.notice("#ReminisceBase       <Node>         -Interrupt: " + info);
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Node>         -Error: " + info);
        }
    }
}