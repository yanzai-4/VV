package AI.Memory.Experience.Model.Message;

// ##### 简介 #####
// 经验方法样本搜索抗性计时指令
// 继承超线程功能
// 用于方法样本抗性的时长

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

import AI.Mind.General.Debug;
import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;

public class ExperienceMsgResist extends Thread {

    // ##### 变量 #####
    // 回忆节点地址
    private ExperienceNodeSolution node;
    // 等待时间 60秒
    private final int time = 60000;
    // 报错信息
    private String info;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新指令
    public ExperienceMsgResist(ExperienceNodeSolution input_node) {
        node = input_node;
        info = input_node.toInfo();
    }

    // ##### 功能函数 #####
    // 复写超线程功能
    @Override
    public void run() {
        try {
            debug.log("#ExperienceTree      <Node>         -DeActive: " + info);
            Thread.sleep(time);
            node.active();
            debug.log("#ExperienceTree      <Node>         -Active: " + info);
        }
        catch (InterruptedException e) {
            debug.notice("#ExperienceTree      <Node>         -Interrupt: " + info);
        }
        catch (Exception e) {
            debug.error("#ExperienceTree      <Node>         -Error: " + info);
        }
    }
}