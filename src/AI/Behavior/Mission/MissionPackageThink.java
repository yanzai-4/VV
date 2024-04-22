package AI.Behavior.Mission;

// ##### 简介 #####
// 行为任务包 想
// 通过衍生的方法样本得出结论并返回给之前的任务
// 如果遇到新的衍生想法 优先处理衍生想法
// 如果当前结论为思考结果 合并父任务
// 如果当前结论为反思结果 合并父任务后需思考整个结果
// 如果当前结论为功能结果 执行功能并结束任务

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

public class MissionPackageThink extends MissionPackage {

    // ##### 继承 #####
    // 变量； ArrayList<String> origin, ArrayList<String> cognition;
    //       MissionPackageType type，ArrayList<String> id, int reward;
    // 功能： getOrigin(), getCognition(), getType(), getId(), getReward(), toString()

    // ##### 变量 #####
    // 方法样本资源
    private ExperienceNodeSolution resource;
    // 主要结论
    private ArrayList<String> conclusion;
    // 次要结论 用于双结论思考
    private ArrayList<String> subConclusion;
    // 反思标签
    private boolean reflect;
    // 替换标签
    private boolean replace;
    // 断点定位标签
    private boolean first;

    private boolean newIdea;
    // 是否挂起
    private boolean isHold;
    // 衍生想法
    private ArrayList<String> idea;
    // 衍生想法断点
    private int index;
    // 指令标签
    private int flag;
    // 运行逻辑
    private ActionLogicThink logic;

    // ##### 构造函数 #####
    // 创建新的任务
    public MissionPackageThink(ArrayList<String> input_origin, ArrayList<String>  input_cognition,
                               ExperienceNodeSolution input_resource, ActionLogicThink input_logic,
                               ArrayList<Integer> input_id) {
        super(input_origin, input_id, 2);
        cognition = input_cognition;
        resource = input_resource;
        conclusion = new ArrayList<>();
        subConclusion = new ArrayList<>();
        //type = MissionPackageType.Think;
        newIdea = false;
        reflect = false;
        replace = false;
        first = true;
        idea = null;
        index = 0;
        flag = 0;
        logic = input_logic;
        this.classify();
    }

    // ##### 功能函数 #####
    // 返回衍生思想
    public ArrayList<String> getIdea() {
        return idea;
    }

    // 返回主要结论
    public ArrayList<String> getConclusion() {
        return conclusion;
    }

    public ExperienceNodeSolution getResource() {
        return resource;
    }

    // 根据方法类型分类
    public void classify() {
        switch (resource.getType()) {
            case Think-> {
                type = MissionPackageType.Think;
            }
            case Reminisce, Compare -> {
                type = MissionPackageType.Reflect;
            }
            case Enhance, Restrain, Except, Learn, Relate, Remember  -> {
                type = MissionPackageType.Function;
            }
        }
    }

    // 执行衍生思考任务
    public boolean thinking() {
        if (type == MissionPackageType.Think) {
            return this.convert();
        }
        return false;
    }

    // 执行衍生搜索任务
    public boolean reflecting() {
        if (type == MissionPackageType.Reflect) {
            switch (resource.getType()) {
                case Reminisce -> {
                    if (this.convert()) {
                        ArrayList<String> temp_conclusion = logic.toReminisce(chainList, conclusion);
                        conclusion = temp_conclusion;
                        return true;
                    }
                }
                case Compare -> {
                    if (this.duoConvert()) {
                        if (conclusion.size() == 1 && subConclusion.size() == 1) {
                            conclusion = logic.toCompare(conclusion.get(0), subConclusion.get(0));
                            conclusion.toString();
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 执行功能性思考任务
    public boolean functioning() {
        if (type == MissionPackageType.Function) {
            switch (resource.getType()) {
                case Enhance -> {
                    flag = 1;
                    return true;
                }
                case Restrain -> {
                    flag = 2;
                    return true;
                }
                case Except -> {
                    if (this.convert()) {
                        flag = 3;
                        return true;
                    }
                }
                case Learn -> {
                    if (this.duoConvert()) {
                        return logic.toLearn(conclusion, subConclusion);
                    }
                }
                case Remember -> {
                    if (this.duoConvert()) {
                        logic.toRemember(conclusion, subConclusion);
                        return true;
                    }
                }
                case Relate -> {
                    if (this.duoConvert()) {
                        if (conclusion.size() == 1 && subConclusion.size() == 1) {
                            logic.toRelate(conclusion.get(0), subConclusion.get(0));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // 执行双重思考
    private boolean duoConvert() {
        if (first) {
            if (this.convert()) {
                index = 0;
                first = false;
                return this.subConvert();
            }
            return false;
        }
        return this.subConvert();
    }

    // 思考方法并得出结果
    // 如果执行遇到衍生想法 挂起当前任务
    public boolean convert() {
        for (int i = index; i < resource.getSolution().size(); i ++) {
            switch (resource.getSolution().get(i).getType()) {
                case Idea -> {
                    idea = logic.getIdea(resource.getSolution().get(i), origin, cognition);
                    isHold = true;
                    index = i;
                    return false;
                }
                case Cite -> {
                    conclusion.addAll(logic.getCite(resource.getSolution().get(i), origin, cognition));
                }
                case Text -> {
                    conclusion.addAll(logic.getText(resource.getSolution().get(i)));
                }
            }
        }
        isHold = false;
        return true;
    }

    // 思考次要方法并得出结果
    // 如果执行遇到衍生想法 挂起当前任务
    public boolean subConvert() {
        for (int i = index; i < resource.getSubSolution().size(); i ++) {
            switch (resource.getSubSolution().get(i).getType()) {
                case Idea -> {
                    idea = logic.getIdea(resource.getSubSolution().get(i), origin, cognition);
                    isHold = true;
                    index = i;
                    return false;
                }
                case Cite -> {
                    subConclusion.addAll(logic.getCite(resource.getSubSolution().get(i), origin, cognition));
                }
                case Text -> {
                    subConclusion.addAll(logic.getText(resource.getSubSolution().get(i)));
                }
            }
        }
        isHold = false;
        return true;
    }

    // 合并并解除挂起状态
    public void combine(ArrayList<String> input_result) {
        this.release(input_result);
        reflect = true;
    }

    // 替换当前结论
    public void replace(ArrayList<String> input_result){
        conclusion = input_result;
        reflect = false;
        replace = false;
    }

    // 解除当前任务挂起状态 并添加结果
    public void release(ArrayList<String> input_result) {
        if (first) {
            conclusion.addAll(input_result);
        }
        else {
            subConclusion.addAll(input_result);
        }
        index ++;
        idea = null;
    }

    // 解除当前任务挂起状态
    public void release() {
        index ++;
        idea = null;
    }

    public void beHold() {
        idea = conclusion;
    }

    // 返回当前任务是否需要反复思考
    public void beReplace() {
        conclusion.add(0, "reflect");
        idea = conclusion;
        replace = true;
    }

    public void newIdea() {
        newIdea = true;
    }

    // 返回当前任务是否需要反复思考
    public boolean needReplace() {
        return replace;
    }

    // 返回当前任务是否需要反复思考
    public boolean isReflect() {
        return reflect;
    }

    // 返回当前任务是否执行增强
    public boolean isEnhance() {
        return flag == 1;
    }

    // 返回当前任务是否执行抑制
    public boolean isRestrain() {
        return flag == 2;
    }

    // 返回当前认为是否期待结果
    public boolean isExpect() {
        return flag == 3;
    }

    public boolean isNewIdea() {
        return newIdea;
    }

    // 返回当前任务是否挂起
    public boolean isHold() {
        return isHold;
    }
}
