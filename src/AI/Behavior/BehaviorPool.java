package AI.Behavior;

// ##### 简介 #####
// 行动池
// 进行逻辑思考
// 可以输入文字并得到输出

// ##### 日志 #####
// 1.0 版本
// 初步完成
// done

import AI.Behavior.Action.ActionSector;
import AI.Behavior.Logic.ActionLogicListen;
import AI.Behavior.Logic.ActionLogicThink;
import AI.Behavior.Mission.MissionPackage;
import AI.Behavior.Sensation.SensationSector;
import AI.Memory.Cognition.CognitionSector;
import AI.Memory.Experience.ExperienceSector;
import AI.Memory.Reminisce.ReminisceSector;
import AI.Mind.General.Debug;

import java.util.ArrayList;

public class BehaviorPool {

    // ##### 变量 #####
    // 当前执行任务列表
    private ArrayList<MissionPackage> processMissionList;
    // 等待回应任务列表
    private ArrayList<MissionPackage> waitMissionList;
    // 挂起任务列表
    private ArrayList<MissionPackage> holdMissionList;
    // 结束任务列表
    private ArrayList<MissionPackage> endMissionList;
    // 分析逻辑
    private ActionLogicListen listenLogic;
    // 思考逻辑
    private ActionLogicThink thinkLogic;
    // 行动分区
    private ActionSector actionSector;
    // 预处理分区
    private SensationSector sensationSector;
    // 当前听信息
    private ArrayList<String> currenListen;
    // 当前思考信息
    private ArrayList<String> currenIdea;
    // 当前说信息
    private ArrayList<String> currenSay;

    // ##### 构造函数 #####
    // 创建新的行为池
    public BehaviorPool(CognitionSector input_cognitionSector, ExperienceSector input_experienceSector,
                        ReminisceSector input_reminisceSector) {
        processMissionList = new ArrayList<>();
        waitMissionList = new ArrayList<>();
        holdMissionList = new ArrayList<>();
        endMissionList = new ArrayList<>();
        listenLogic = new ActionLogicListen(input_cognitionSector, input_experienceSector, input_reminisceSector);
        thinkLogic = new ActionLogicThink(input_cognitionSector, input_experienceSector, input_reminisceSector);
        actionSector = new ActionSector(processMissionList, waitMissionList, holdMissionList, endMissionList, listenLogic, thinkLogic);
        sensationSector = new SensationSector(processMissionList, waitMissionList, holdMissionList, endMissionList, listenLogic, thinkLogic);
    }

    // ##### 功能函数 #####
    // 进行一次思考
    public void doBehavior() {
        try {
            currenIdea = null;
            currenSay = null;
            this.doAction();
            this.doSensation();
            actionSector.resetCurren();
        }
        catch (Exception e) {
            Debug debug = new Debug();
            debug.error(e);
        }
    }

    // 获得当前说内容
    public ArrayList<String> getSay() {
        return currenSay;
    }

    // 获得当前思考内容
    public ArrayList<String> getThink() {
        return currenIdea;
    }

    // 添加听内容
    public void addListen(ArrayList<String> input_listen) {
        currenListen = input_listen;
    }

    // 奖励当前所有行为
    public void doEnhance() {
        actionSector.enhanceAll();
    }

    // 惩罚当前所有行为
    public void doRestrain() {
        actionSector.restrainAll();
    }

    // 进行一次行动
    private void doAction() {
        if (processMissionList.isEmpty()) {
            actionSector.beBored();
        }
        else {
            actionSector.action();
            if (actionSector.getCurrenIdea() != null) {
                currenIdea = actionSector.getCurrenIdea();
            }
            else if (actionSector.getCurrenSay() != null) {
                currenSay = actionSector.getCurrenSay();
            }
        }
    }

    // 对行动结果进行预处理
    private void doSensation() {
        if (currenListen != null) {
            sensationSector.addListen(currenListen);
            currenListen = null;
        }
        if (actionSector.getCurrenMission() != null) {
            sensationSector.sensation(actionSector.getCurrenMission());

        }
        sensationSector.lapse();
    }
}
