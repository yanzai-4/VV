package AI.Mind;

// ##### 简介 #####
// 思维控制器
// 控制ai思考

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

import AI.Behavior.BehaviorPool;
import AI.Memory.MemoryPool;
import AI.Mind.General.Counter;
import AI.Mind.General.Debug;

import java.util.ArrayList;

public class MindController {

    // ##### 变量 #####
    // 记忆池
    MemoryPool memory;
    // 行为池
    BehaviorPool behavior;
    // 计数器
    Counter counter;
    // AI状态
    boolean status;

    // ##### 构造函数 #####
    // 创建思考控制器
    public MindController() {
        memory = new MemoryPool();
        behavior = new BehaviorPool(memory.getCognitionSector(), memory.getExperienceSector(), memory.getReminisceSector());
        counter = new Counter();
        counter.start();
        status = true;
    }

    // ##### 功能函数 #####
    // 做思考
    public void doThink() {
        behavior.doBehavior();
        counter.addTick();
    }

    // 返回数据库
    public MemoryPool getMemory() {
        return memory;
    }

    // 返回显示思考率
    public float getThinkRate() {
        return counter.getRefresh_rate();
    }

    // 重载按钮
    public void doReset() {
        memory.rebuild();
    }

    // 休眠按钮
    public void doSleep() {
        memory.sleep();
    }

    // 添加对话
    public void addListen(ArrayList<String> input_text) {
        behavior.addListen(input_text);
    }

    // 回去显示回复
    public ArrayList<String> getSay() {
        return behavior.getSay();
    }

    // 获取显示思考
    public ArrayList<String> getThink() {
        return behavior.getThink();
    }

    // 增强按钮
    public void enhance() {
        behavior.doEnhance();
    }

    // 抑制按钮
    public void restrain() {
        behavior.doRestrain();
    }

    // 返回AI是否还在工作
    public boolean isAlive() {
        return status && memory.isAlive();
    }

    // 打断AI
    public void interrupt() {
        status = false;
        //counter.interrupt();
    }
}
