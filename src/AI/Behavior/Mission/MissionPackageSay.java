package AI.Behavior.Mission;

// ##### 简介 #####
// 行为任务包 说
// 通过方法样本得出结论
// 如果遇到衍生想法 优先处理衍生想法

// ##### 日志 #####
// 1.0 版本
// 初步完成
// 1.2 版本
// 添加权重修改功能
// 1.3 版本
// 添加挂起判断
// done

import AI.Behavior.Logic.ActionLogicThink;
import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;
import AI.Mind.General.Debug;

import java.util.ArrayList;

public class MissionPackageSay extends MissionPackage {

    // ##### 继承 #####
    // 变量； ArrayList<String> origin, ArrayList<String> cognition;
    //       MissionPackageType type，ArrayList<String> id, int reward;
    // 功能： getOrigin(), getCognition(), getType(), getId(), getReward(), toString()

    // ##### 变量 #####
    // 方法样本资源
    private ExperienceNodeSolution resource;
    // 结果内容
    private ArrayList<String> content;
    // 衍生想法
    private ArrayList<String> idea;
    // 衍生想法断点
    private int index;
    // 是否挂起
    private boolean isHold;
    // 运行逻辑
    private ActionLogicThink logic;

    // ##### 构造函数 #####
    // 创建新的任务
    public MissionPackageSay(ArrayList<String> input_origin, ArrayList<String> input_cognition,
                             ExperienceNodeSolution input_resource, ActionLogicThink input_logic,
                             ArrayList<Integer> input_id) {
        super(input_origin, input_id, 4);
        cognition = input_cognition;
        type = MissionPackageType.Say;
        resource = input_resource;
        content = new ArrayList<>();
        idea = null;
        index = 0;
        isHold = false;
        logic = input_logic;

    }

    // ##### 功能函数 #####
    // 返回衍生想法
    public ArrayList<String> getIdea() {
        return idea;
    }

    // 返回结果内容
    public ArrayList<String> getContent() {
        return content;
    }

    public ExperienceNodeSolution getResource() {
        return resource;
    }

    // 返回思考是否完成
    public boolean thinking() {
        return convert();
    }

    // 思考方法并得出结果
    // 如果执行遇到衍生想法 挂起当前任务
    public boolean convert() {
        for (int i = index; i < resource.getSolution().size(); i ++) {
            switch (resource.getSolution().get(i).getType()) {
                case Idea -> {
                    idea = logic.getIdea(resource.getSolution().get(i), origin, cognition);
                    index = i;
                    isHold = true;
                    return false;
                }
                case Cite -> {
                    content.addAll(logic.getCite(resource.getSolution().get(i), origin, cognition));
                }
                case Text -> {
                    content.addAll(logic.getText(resource.getSolution().get(i)));
                }
            }
        }
        isHold = false;
        return true;
    }

    public void beHold() {
        idea = content;
    }

    // 解除当前任务的挂起状态 并添加结果
    public void release(ArrayList<String> input_result) {
        content.addAll(input_result);
        index ++;
        idea = null;
    }

    // 解除当前任务的挂起状态
    public void release() {
        index ++;
        idea = null;
    }

    // 返回当前任务是否挂起
    public boolean isHold() {
        return isHold;
    }
}
