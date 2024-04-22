package AI.Behavior.Sensation;

// ##### 简介 #####
// 预行为模块
// 引流以执行完成的任务或者输入

// ##### 日志 #####
// 1.0 版本
// 初步完成
// 1.1 版本
// 添加报错信息
// done

import AI.Behavior.Logic.ActionLogicListen;
import AI.Behavior.Logic.ActionLogicThink;
import AI.Behavior.Mission.*;
import AI.Mind.General.Debug;

import java.util.ArrayList;

public class SensationSector {

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
    // 当前编号
    private int currenId;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新的预行动分区
    public SensationSector(ArrayList<MissionPackage> input_processMissionList, ArrayList<MissionPackage> input_waitMissionList,
                           ArrayList<MissionPackage> input_holdMissionList, ArrayList<MissionPackage> input_endMissionList,
                           ActionLogicListen input_listenLogic, ActionLogicThink input_thinkLogic) {
        processMissionList = input_processMissionList;
        waitMissionList = input_waitMissionList;
        holdMissionList = input_holdMissionList;
        endMissionList = input_endMissionList;
        listenLogic = input_listenLogic;
        thinkLogic = input_thinkLogic;
        currenId = 0;
    }

    // ##### 功能函数 #####
    // 降低所有闲置任务的权重
    public void lapse() {
        this.lapseProcess();
        this.lapseWait();
        this.lapseHold();
        this.lapseEnd();
        debug.log("#SensationSector     <Lapse>        -Finish");
    }

    // 对输入的任务进行预判断行为
    public void sensation(MissionPackage input_package) {
        try {
            switch (input_package.getType()) {
                case Think -> {
                    MissionPackageThink temp_package = (MissionPackageThink) input_package;
                    this.releaseHold(temp_package.getOrigin(), temp_package.getConclusion(), false);
                }
                case Reflect -> {
                    MissionPackageThink temp_package = (MissionPackageThink) input_package;
                    this.releaseHold(temp_package.getOrigin(), temp_package.getConclusion(), true);
                }
                case Function -> {
                    MissionPackageThink temp_package = (MissionPackageThink) input_package;
                    this.releaseHold(temp_package.getOrigin());
                }
            }
        }
        catch (Exception e) {
            debug.error(e);
            debug.error("#SensationSector     <Sensation>    -BigError");
        }
    }

    // 添加听任务并做预判断行为
    public void addListen(ArrayList<String> input_sentence) {
        if (input_sentence == null) {
            debug.fail("#SensationSector     <Listen>       -Null");
            return;
        }
        if (waitMissionList.isEmpty()) {
            ArrayList<Integer> temp_id = new ArrayList<>();
            temp_id.add(this.newId());
            processMissionList.add(new MissionPackageListen(input_sentence, listenLogic, temp_id));
            debug.notice("#SensationSector     <Listen>       -Add");
            return;
        }
        if (this.mergeWait(input_sentence)) {
            debug.notice("#SensationSector     <Listen>       -Merge");
            return;
        }
        debug.error("#SensationSector     <Listen>       -Error");
    }

    // 将输入内容和等待任务合并
    private boolean mergeWait(ArrayList<String> input_sentence) {
        for (MissionPackage temp_package : waitMissionList) {
            MissionPackageThink temp_think = (MissionPackageThink) temp_package;
            ArrayList<String> new_idea = thinkLogic.toFeedback(temp_think.getConclusion(), input_sentence);
            ArrayList<Integer> temp_id = new ArrayList<>();
            temp_id.add(this.newId());
            MissionPackageIdea temp_idea = new MissionPackageIdea(new_idea, listenLogic, temp_id, "LISTEN");
            temp_idea.beWait();
            processMissionList.add(temp_idea);
            this.removeWait(temp_think);
            return true;
        }
        return false;
    }

    // 释放挂起任务 并添加结果
    private boolean releaseHold(ArrayList<String> input_idea, ArrayList<String> input_result, boolean isReflect) {
        for (MissionPackage temp_package : holdMissionList) {
            switch (temp_package.getType()) {
                case Say -> {
                    MissionPackageSay temp_say = (MissionPackageSay) temp_package;
                    if (temp_say.getIdea().equals(input_idea)) {
                        debug.log("#SensationSector     <Sensation>    -Release");
                        temp_say.release(input_result);
                        this.toProcess(temp_say);
                        return true;
                    }
                }
                case Think, Reflect, Function -> {
                    MissionPackageThink temp_think = (MissionPackageThink) temp_package;
                    if (temp_think.getIdea().equals(input_idea)) {
                        if (temp_think.needReplace()) {
                            debug.notice("#SensationSector     <Sensation>    -Replace");
                            temp_think.replace(input_result);
                        }
                        else {
                            if (isReflect) {
                                debug.notice("#SensationSector     <Sensation>    -Combine");
                                temp_think.combine(input_result);
                            }
                            else {
                                debug.log("#SensationSector     <Sensation>    -Release");
                                temp_think.release(input_result);
                            }
                        }
                        this.toProcess(temp_think);
                        return true;
                    }
                }
            }
        }
        debug.fail("#SensationSector     <Sensation>    -NoHold");
        return false;
    }

    // 释放挂起任务
    private void releaseHold(ArrayList<String> input_idea) {
        for (MissionPackage temp_package : holdMissionList) {
            switch (temp_package.getType()) {
                case Say -> {
                    MissionPackageSay temp_say = (MissionPackageSay) temp_package;
                    if (temp_say.getIdea().equals(input_idea)) {
                        debug.log("#SensationSector     <Sensation>    -Release: " + input_idea);
                        temp_say.release();
                        this.toProcess(temp_say); // stop call back
                        //
                        return;
                    }
                }
                case Think, Reflect, Function -> {
                    MissionPackageThink temp_think = (MissionPackageThink) temp_package;
                    if (temp_think.getIdea().equals(input_idea)) {
                        debug.log("#SensationSector     <Sensation>    -Release: " + input_idea);
                        temp_think.release();
                        this.toProcess(temp_think);
                        //this.removeHold(temp_think);
                        return;
                    }
                }
            }
        }
        debug.fail("#SensationSector     <Sensation>    -NoHoldRelease: " + input_idea);
    }

    // 降低运行任务权重
    private void lapseProcess() {
        ArrayList<MissionPackage> dead_package = new ArrayList<>();
        for (MissionPackage temp_package : processMissionList) {
            temp_package.lapse(3);
            if (temp_package.isDead()) {
                dead_package.add(temp_package);
            }
        }
        if (dead_package.size() != 0) {
            for (MissionPackage temp_package : dead_package) {
                processMissionList.remove(temp_package);
                debug.log("#SensationSector     <Lapse>        -Process: " + temp_package.getCurrenId());
            }
        }
    }

    // 降低等待任务权重
    private void lapseWait() {
        ArrayList<MissionPackage> dead_package = new ArrayList<>();
        for (MissionPackage temp_package : waitMissionList) {
            temp_package.lapse(2);
            if (temp_package.isDead()) {
                debug.notice("#SensationSector     <Sensation>    -Delay");
                MissionPackageThink temp_think = (MissionPackageThink) temp_package;
                ArrayList<String> new_idea = thinkLogic.toNoFeedback(temp_think.getConclusion());
                ArrayList<Integer> temp_id = new ArrayList<>();
                temp_id.add(this.newId());
                processMissionList.add(new MissionPackageIdea(new_idea, listenLogic, temp_id, "LISTEN"));
                dead_package.add(temp_think);
            }
        }
        if (dead_package.size() != 0) {
            for (MissionPackage temp_package : dead_package) {
                waitMissionList.remove(temp_package);
                debug.log("#SensationSector     <Lapse>        -Wait: " + temp_package.getCurrenId());
            }
        }
    }

    // 降低挂起任务权重
    private void lapseHold() {
        ArrayList<MissionPackage> dead_package = new ArrayList<>();
        for (MissionPackage temp_package : holdMissionList) {
            temp_package.lapse(3);
            if (temp_package.isDead()) {
                dead_package.add(temp_package);
            }
        }
        if (dead_package.size() != 0) {
            for (MissionPackage temp_package : dead_package) {
                holdMissionList.remove(temp_package);
                debug.log("#SensationSector     <Lapse>        -Hold: " + temp_package.getCurrenId());
            }
        }
    }

    // 降低完成任务权重
    private void lapseEnd() {
        ArrayList<MissionPackage> dead_package = new ArrayList<>();
        for (MissionPackage temp_package : endMissionList) {
            temp_package.lapse(2);
            if (temp_package.isDead()) {
                dead_package.add(temp_package);
            }
        }
        if (dead_package.size() != 0) {
            for (MissionPackage temp_package : dead_package) {
                endMissionList.remove(temp_package);
                debug.log("#SensationSector     <Lapse>        -End: " + temp_package.getCurrenId());
            }
        }
    }

    // 转移任务到运行区
    private void toProcess(MissionPackage input_package){
        input_package.resetWeight();
        processMissionList.add(input_package);
        holdMissionList.remove(input_package);
    }

    // 移除等待任务
    private void removeWait(MissionPackage input_package){
        waitMissionList.remove(input_package);
    }

    private void removeHold(MissionPackage input_package){
        holdMissionList.remove(input_package);
    }

    // 获取新的唯一id
    private int newId(){
        currenId += 2;
        return currenId;
    }

}
