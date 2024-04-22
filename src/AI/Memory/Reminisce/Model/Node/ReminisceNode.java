package AI.Memory.Reminisce.Model.Node;

// ##### 简介 #####
// 记忆节点
// 用于保存记忆团以及标签
// 数据储存结构：weight#memory-memory#label-1=label-2

// ##### 日志 #####
// 1.0 版本
// 简单构建
// 2.0 版本
// 重构
// 2.1 版本
// 接口普适化
// 2.2 版本
// 激活修改
// done

import AI.Memory.Reminisce.Model.Message.ReminisceMsgResist;

import java.util.ArrayList;

public class ReminisceNode {

    // ##### 变量 #####.
    // 节点记忆标签组
    private ArrayList<ReminisceNodeLabel> labelList;
    // 节点回忆团
    private ReminisceNodeCrumb reminisce;
    // 节点时间权重
    private int time_weight;
    // 节点优先权重
    private int pri_weight;
    // 节点状态
    private boolean active;

    // ##### 构造函数 #####
    // 创建新回忆节点
    public ReminisceNode(ArrayList<String> input_reminisce) {
        labelList = new ArrayList<>();
        reminisce = new ReminisceNodeCrumb(input_reminisce);
        pri_weight = 15;
        time_weight = 20;
        active = true;
    }

    // 加载现有回忆节点
    public ReminisceNode(ArrayList<String> input_reminisce, int input_weight) {
        labelList = new ArrayList<>();
        reminisce = new ReminisceNodeCrumb(input_reminisce);
        pri_weight = input_weight;
        time_weight = 0;
        active = true;
    }

    // ##### 功能函数 #####
    // 返回当前节点记忆团
    public ArrayList<String> getCrumb() {
        return reminisce.getCrumb();
    }

    // 返回当前节点权重
    public int getWeight() {
        return time_weight + pri_weight;
    }

    // 手动设置标签串
    public void setLabel(ArrayList<ReminisceNodeLabel> input_label) {
        labelList = input_label;
    }

    // 添加标签串
    public void addLabelList(ArrayList<String> input_labelList) {
        for (String temp_label : input_labelList) {
            labelList.add(new ReminisceNodeLabel(temp_label));
        }
    }

    // 添加标签
    public void addLabel(String input_label) {
        labelList.add(new ReminisceNodeLabel(input_label));
    }

    // 添加标签和权重
    public void addLabel(String input_label, int input_weight) {
        labelList.add(new ReminisceNodeLabel(input_label, input_weight));
    }

    // 增加节点权重
    public void addWeight() {
        if (pri_weight < 20) {
            pri_weight += 1;
        }
    }

    // 降低节点权重
    public void subWeight() {
        if (pri_weight > 0) {
            pri_weight -= 1;
        }
    }

    // 降低时间权重
    public void lapse() {
        if (time_weight > 0) {
            time_weight -= 1;
        }
    }

    // 检查输入标签组是否匹配当前节点
    // 如果符合 修正当前节点标签组权重并激活抗性信息
    public Boolean match(ArrayList<String> input_tagList) {
        int size = input_tagList.size();
        for (String temp_tag : input_tagList) {
            for (ReminisceNodeLabel temp_label : labelList) {
                if (temp_label.getTag().equals(temp_tag)) {
                    size --;
                }
            }
            if (size == 0) {
                this.changeLabelWeight(input_tagList);
                this.deActive();
                return true;
            }
        }
        return false;
    }

    // 修正当前节点标签组权重
    private void changeLabelWeight(ArrayList<String> input_labelList) {
        for (int i = 0; i < labelList.size(); i ++) {
            boolean empty = true;
            for (String temp_label : input_labelList) {
                if (labelList.get(i).getTag().equals(temp_label)) {
                    labelList.get(i).addWeight();
                    empty = false;
                    break;
                }
            }
            if (empty) {
                labelList.get(i).subWeight();
                if (labelList.get(i).isDead()) {
                    labelList.remove(i);
                }
            }
        }
    }

    // 沉默节点
    private void deActive() {
        active = false;
        new ReminisceMsgResist(this).start();
    }

    // 激活节点
    public void active() {
        this.active = true;
    }

    // 返回节点状态
    public Boolean isActive() {
        return active;
    }

    // 返回节点是否满足存在条件
    public Boolean isDead() {
        return labelList.size() == 0 || this.getWeight() == 0;
    }

    // 返回当前节点摘要信息
    public String toInfo() {
        return reminisce.toData();
    }

    // 打印当前节点信息
    public String toData() {
        String text = "";
        for (ReminisceNodeLabel temp_label : labelList) {
            text += (temp_label.toData() + "=");
        }
        return pri_weight + "#" + text.substring(0, text.length() - 1) + "#" + reminisce.toData();
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "ReminisceNode{" +
                "labelList=" + labelList +
                ", reminisce=" + reminisce +
                ", time_weight=" + time_weight +
                ", pri_weight=" + pri_weight +
                '}';
    }
}
