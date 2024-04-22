package AI.Memory.Experience.Model.Node;

// ##### 简介 #####
// 经验节点情况样本
// 用于储存情况内容并用于对比当前情况
// 数据结构： situation-situation

// ##### 日志 #####
// 1.0 版本
// 添加对比方法
// 1.2 版本
// 修改对比参数
// 2.0 版本
// 优化搜索比分
// done

import AI.Mind.General.Debug;

import java.util.ArrayList;
import java.util.Objects;

public class ExperienceNodeSituation {
    // ##### 变量 #####
    // 情况样本
    private ArrayList<String> situation;

    // ##### 构造函数 #####
    // 创建新节点
    public ExperienceNodeSituation(ArrayList<String> input_situation) {
        situation = input_situation;
    }

    // ##### 功能函数 #####
    // 返回当前情况样本
    public ArrayList<String> getSituation() {
        return situation;
    }

    // 返回情况样本相似度
    public int match(ArrayList<String> input_situation) {
        if (!Objects.equals(input_situation.get(0), situation.get(0))) {
            return 0;
        }
        int gap = 0;
        int same_word = 0;
        int diff_word = 0;
        int miss_word = 0;
        int extra_word = 0;
        boolean noResult;
        boolean isANY = false;
        if(input_situation.size() > situation.size()) {
            miss_word = input_situation.size() - situation.size();
        }
        for(int i = 0; i < situation.size(); i ++) {
            noResult = true;
            for (int j = i + gap; j < input_situation.size(); j++) {
                if (isANY) {
                    if (i < situation.size() - 1 && input_situation.get(j).equals(situation.get(i + 1))) {
                        isANY = false;
                        noResult = false;
                        same_word += 1;
                        i ++;
                        gap --;
                        break;
                    }
                    if (i != situation.size() - 1 || j != input_situation.size() - 1) {
                        gap ++;
                        i --;
                    }
                    noResult = false;
                    extra_word += 1;
                    break;
                }
                else {
                    if (input_situation.get(j).equals(situation.get(i))) {
                        noResult = false;
                        same_word += 1;
                        gap = j - i;
                        break;
                    }
                    if (situation.get(i).equals("ANY")) {
                        if (i != situation.size() - 1 || j != input_situation.size() - 1) {
                            i --;
                        }
                        noResult = false;
                        extra_word += 1;
                        isANY = true;
                        gap += 1;
                        break;
                    }
                }
            }
            if (noResult) {
                diff_word += 1;
                gap -= 1;
            }
        }
        if (extra_word - diff_word >= miss_word) {
            miss_word = 0;
        }
        else {
            miss_word = miss_word - extra_word;
        }

        double point = same_word + (0.75 * extra_word);
        double total = same_word + extra_word+ (1.5 * miss_word) + (2.25 * diff_word);
        return (int) (100 * point/total);
    }

    // 对比两个方法样本是否相同
    public boolean isSame(ArrayList<String> input_situation) {
        ArrayList<String> temp_situation = input_situation;
        if (temp_situation.size() != situation.size()) {
            return false;
        }
        for (int i = 0; i < situation.size(); i++) {
            if (!temp_situation.get(i).equals(situation.get(i))) {
                return false;
            }
        }
        return true;
    }

    // 返回当前样本摘要信息
    public String toInfo() {
        String text = "";
        for (int i = 1; i < situation.size(); i ++) {
            text += situation.get(i) + "_";
        }
        return text.substring(0, text.length() - 1);
    }

    // 返回数据储存结构
    public String toData() {
        String text = "";
        for (String temp_word : situation) {
            text += temp_word + "-";
        }
        return text.substring(0, text.length() - 1);
    }

    // 返回打印信息
    @Override
    public String toString() {
        return "ExperienceNodeSituation{" +
                "situation=" + situation +
                '}';
    }
}
