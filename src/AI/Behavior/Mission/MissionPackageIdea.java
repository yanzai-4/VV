package AI.Behavior.Mission;

// ##### 简介 #####
// 行为任务包 想法
// 接受内部情况信息
// 查找对应解决方法

// ##### 日志 #####
// 1.0 版本
// 初步完成
// 1.2 版本
// 添加权重修改功能
// 1.3 版本
// 修复null 报错
// done

import AI.Behavior.Logic.ActionLogicListen;
import AI.Memory.Experience.Model.Chain.ExperienceChain;
import AI.Memory.Experience.Model.Node.ExperienceNode;
import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;
import AI.Memory.MemoryChain;
import AI.Mind.General.Debug;

import java.util.ArrayList;

public class MissionPackageIdea extends MissionPackage {

    // ##### 继承 #####
    // 变量； ArrayList<String> origin, ArrayList<String> cognition;
    //       MissionPackageType type，ArrayList<String> id, int reward;
    // 功能： getOrigin(), getCognition(), getType(), getId(), getReward(), toString()

    // ##### 变量 #####
    // 解决方法样本
    private ExperienceNodeSolution solution;
    // 语句理解列表
    private ArrayList<ArrayList<String>> comprehendList;
    // 单词猜测列表
    private ArrayList<ArrayList<String>> connectionList;
    // 头注释
    private String header;
    //
    private boolean isWait;
    // 运行逻辑
    private ActionLogicListen logic;

    // ##### 构造函数 #####
    // 创建新的任务
    public MissionPackageIdea(ArrayList<String> input_origin, ActionLogicListen input_logic,
                              ArrayList<Integer> input_id, String input_header) {
        super(input_origin, input_id, 1);
        comprehendList = new ArrayList<>();
        connectionList = new ArrayList<>();
        cognition = new ArrayList<>();
        type = MissionPackageType.Idea;
        header = input_header;
        logic = input_logic;
        isWait = false;
    }

    // ##### 功能函数 #####
    // 返回方法样本
    public ExperienceNodeSolution getSolution() {
        return solution;
    }

    public String getHeader() {
        return header;
    }

    // 理解原始信息
    public boolean comprehend() {
        for (String temp_word : origin) {
            ArrayList<String> temp_cognition = logic.getCognition(chainList, temp_word);
            if (temp_cognition == null) {
                // logic.addCognition(temp_word); 自动添加
                return false;
            }
            connectionList.add(temp_cognition);
        }
        for (int i = 0; i < connectionList.get(0).size(); i ++) {
            ArrayList<String> temp_list = new ArrayList<>();
            temp_list.add(connectionList.get(0).get(i));
            comprehendList.add(temp_list);
        }

        for (int i = 1; i < connectionList.size(); i ++) {
            comprehendList = doConnect(comprehendList, connectionList.get(i));
        }

        return true;
    }

    // 思考情况并得出方法
    public boolean thinking() {
        if (!comprehendList.isEmpty()) {
            ExperienceChain temp_chain = logic.getSolution(chainList, comprehendList, 75, header);
            if (temp_chain != null) {
                cognition = temp_chain.getSearchSituation();
                solution = temp_chain.getSolution();
            }
            return true;
        }
        return false;
    }

    // 思考相似情况并得出方法
    public void moreThinking() {
        ExperienceChain temp_chain = logic.getSolution(chainList, comprehendList, 60, header);
        if (temp_chain != null) {
            cognition = temp_chain.getSearchSituation();
            solution = temp_chain.getSolution();
        }
    }

    // 分配认知团到每一个认知列中
    public ArrayList<ArrayList<String>> doConnect(ArrayList<ArrayList<String>> input_leftList, ArrayList<String> input_rightList) {
        ArrayList<ArrayList<String>> new_list = new ArrayList<>();
        for (ArrayList<String> left_side : input_leftList) {
            for (String right_side : input_rightList) {
                ArrayList<String> temp_list = new ArrayList<>(left_side);
                temp_list.add(right_side);
                new_list.add(temp_list);
            }
        }
        return new_list;
    }

    public void beWait() {
        isWait = true;
    }

    public boolean isWait() {
        return isWait;
    }

    // 增强方法权重
    public void enhance() {
        for (MemoryChain temp_chain : chainList) {
            temp_chain.enhance();
        }
    }

    // 抑制方法权重
    public void restrain() {
        for (MemoryChain temp_chain : chainList) {
            temp_chain.restrain();
        }
    }
}
