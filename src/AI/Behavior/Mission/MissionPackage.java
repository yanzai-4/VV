package AI.Behavior.Mission;

// ##### 简介 #####
// 行为任务包
// 记录当前处理中的文字信息
// 用于分类不同的任务

// ##### 日志 #####
// 1.0 版本
// 初步完成
// 2.0 版本
// 添加修改权重功能
// done

import AI.Memory.MemoryChain;

import java.util.ArrayList;

public class MissionPackage {

    // ##### 变量 #####
    // 原始片段
    protected ArrayList<String> origin;
    // 认知理解
    protected ArrayList<String> cognition;
    // 记忆链
    protected ArrayList<MemoryChain> chainList;
    // 任务类型
    protected MissionPackageType type;
    // 源头id
    private ArrayList<Integer> id;
    // 权重
    private int weight;
    // 任务奖励
    // Say = 4 > Listen = 3 > think = 2 > idea = 1 > nothing = 0
    private int rank;

    // ##### 构造函数 #####
    // 创建新的任务
    public MissionPackage(ArrayList<String> input_origin, ArrayList<Integer> input_id, int input_rank) {
        origin = input_origin;
        chainList = new ArrayList<>();
        id = input_id;
        rank = input_rank;
        weight = 60;
    }

    // ##### 功能函数 #####
    // 返回原始片段
    public ArrayList<String> getOrigin() {
        return origin;
    }

    // 返回认知理解
    public ArrayList<String> getCognition() {
        return cognition;
    }

    // 返回当前类型
    public MissionPackageType getType() {
        return type;
    }

    // 返回任务id
    public ArrayList<Integer> getId() {
        return id;
    }

    // 返回任务当前id
    public int getCurrenId() {
        return id.get(id.size() - 1);
    }

    // 返回任务原始id
    public int getOriginId() {
        return id.get(0);
    }

    // 返回任务分值
    public int getRank() {
        return rank;
    }

    // 返回权重
    public boolean isDead() {
        return weight < 0;
    }

    // 减少权重
    public void lapse(int input_number) {
        weight -= input_number;
    }

    // 重置权重
    public void resetWeight() {
        weight = 60;
    }

    // 增强方法权重
    public void enhance() {
        return;
    }

    // 抑制方法权重
    public void restrain() {
        return;
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "MissionPackage{" +
                "origin=" + origin +
                ", cognition=" + cognition +
                ", type=" + type +
                ", id=" + id +
                ", weight=" + weight +
                '}';
    }
}
