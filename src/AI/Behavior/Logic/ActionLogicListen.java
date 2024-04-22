package AI.Behavior.Logic;

// ##### 简介 #####
// 认知逻辑
// 通过记忆内容来分析输入内容
// 并保留搜索链的到任务

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

import AI.Memory.Cognition.CognitionSector;
import AI.Memory.Cognition.Model.Chain.CognitionChain;
import AI.Memory.Experience.ExperienceSector;
import AI.Memory.Experience.Model.Chain.ExperienceChain;
import AI.Memory.Experience.Model.Node.ExperienceNodeSolution;
import AI.Memory.MemoryChain;
import AI.Memory.Reminisce.ReminisceSector;
import AI.Mind.General.Debug;

import java.util.ArrayList;

public class ActionLogicListen {

    // ##### 变量 #####
    // 认知分区
    private CognitionSector cognitionSector;
    // 经验分区
    private ExperienceSector experienceSector;
    // 回忆分区
    private ReminisceSector reminisceSector;

    // ##### 构造函数 #####
    // 创建新的认知逻辑
    public ActionLogicListen(CognitionSector input_cognitionSector, ExperienceSector input_experienceSector, ReminisceSector input_reminisceSector) {
        cognitionSector = input_cognitionSector;
        experienceSector = input_experienceSector;
        reminisceSector = input_reminisceSector;
    }

    // ##### 功能函数 #####
    // 返回认知团
    public ArrayList<String> getCognition(ArrayList<MemoryChain> chainList, String input_word) {
        CognitionChain temp_chain = cognitionSector.search(input_word);
        if (temp_chain == null) {
            return null;
        }
        chainList.add(temp_chain);
        return temp_chain.getConnection();
    }

    // 返回方法样本
    public ExperienceChain getSolution(ArrayList<MemoryChain> chainList, ArrayList<ArrayList<String>> cognition, int input_rate, String input_header) {
        ExperienceChain max_chain = null;
        int max_weight = 0;
        for (ArrayList<String> temp_situation : cognition) {
            ArrayList<String> new_situation = new ArrayList<>();
            new_situation.add(input_header);
            new_situation.addAll(temp_situation);
            ExperienceChain temp_chain = experienceSector.search(new_situation, input_rate);
            if (temp_chain != null && temp_chain.getWeight() > max_weight && temp_chain.isWork()) {
                max_weight = temp_chain.getWeight();
                max_chain = temp_chain;
            }
        }
        if (max_chain != null) {
            chainList.add(max_chain);
            return max_chain;
        }
        return null;
    }

    // 添加认知文字
    public void addCognition(String input_word) {
        cognitionSector.add(input_word);
    }

    // 无法认知引导
    public ArrayList<String> incomprehension(String temp_word) {
        ArrayList<String> new_list = new ArrayList<>();
        new_list.add("UNKNOW");
        new_list.add(temp_word);
        return new_list;
    }

    public ArrayList<String> independentWait(ArrayList<String> input_sentence) {
        ArrayList<String> new_list = new ArrayList<>();
        for (String temp_word : input_sentence) {
            new_list.add(temp_word);
            if (temp_word.equals("REPLY")) {
                new_list.remove(new_list.size() - 1);
                return new_list;
            }
        }
        return null;
    }



    // 被动记忆
    public void doRemember() {
        reminisceSector.add(null, null);
    }
}
