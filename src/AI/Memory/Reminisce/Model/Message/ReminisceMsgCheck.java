package AI.Memory.Reminisce.Model.Message;

// ##### 简介 #####
// 回忆节点搜索指令超时检查
// 继承超线程功能
// 确保回忆节点搜索时长

// ##### 日志 #####
// 1.0 版本
// 简单构建
// 1.1 版本
// 增加报错
// done

import AI.Mind.General.Debug;
import AI.Memory.Reminisce.Function.ReminisceFuncSearch;

public class ReminisceMsgCheck extends Thread {

    // ##### 变量 #####
    // 回忆检索线程地址
    private ReminisceFuncSearch searchThread;
    // 等待时间 30秒
    private final int time = 30000;
    // 报错信息
    private String info;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新指令
    public ReminisceMsgCheck(ReminisceFuncSearch input_searchThread) {
        searchThread = input_searchThread;
        info = searchThread.getInfo();
    }

    // ##### 功能函数 #####
    // 复写超线程功能
    @Override
    public void run() {
        try {
            debug.log("#ReminisceBase       <Search>       -GoDeep: " + info);
            Thread.sleep(time);
            searchThread.interrupt();
            debug.notice("#ReminisceBase       <Search>       -Interrupt: " + info);
        }
        catch (InterruptedException e) {
            debug.notice("#ReminisceBase       <Search>       -StopCheck: " + info);
        }
        catch (Exception e) {
            debug.error("#ReminisceBase       <Search>       -Error: " + info);
        }
    }
}
