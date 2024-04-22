package AI.Mind.General;

// ##### 简介 #####
// 思考计数器
// 记录并返回每秒思考数量

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

import java.util.LinkedList;
import java.util.Queue;

public class Counter extends Thread{

    // ##### 变量 #####
    // 十秒内的累计思考数量
    private Queue<Integer> active_think;
    // 当前思考数量
    private int curren_tick;
    // 思考刷新率
    private float refresh_rate;
    // 刷新时间
    private int sec = 1000;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新的计数器
    public Counter() {
        active_think = new LinkedList<>();
    }

    // ##### 功能函数 #####
    // 获取思考刷新率
    public float getRefresh_rate() {
        return (float) (Math.round(refresh_rate * 100)) / 100;
    }

    // 复写超线程功能
    @Override
    public void run() {
        int retry = 3;
        while(retry > 0) {
            try {
                Thread.sleep(sec);
                this.lapse();
            }
            catch (InterruptedException e) {
                debug.error("#AI_BaseFunction     <Counter>      -Interrupt");
                retry = 0;
            }
            catch (Exception e) {
                debug.error("#AI_BaseFunction     <Counter>      -Error");
                retry--;
            }
        }
        debug.notice("#AI_BaseFunction     <Counter>      -Stop");
    }

    // 怎加每秒思考数
    public void addTick() {
        curren_tick ++;
    }

    // 计算10秒内每秒思考数量平均值
    public void lapse() {
        active_think.add(curren_tick);
        curren_tick = 0;
        while (active_think.size() > 4) {
            active_think.poll();
        }
        int total_num = 0;
        for (int temp_num : active_think) {
            total_num += temp_num;
        }
        refresh_rate = (float) total_num / active_think.size();
    }
}
