package AI.Behavior.Action;

// ##### 简介 #####
// 行为模块
// 执行当前最优先任务
// 根据情况进行分流或者输出

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
import AI.Memory.Experience.Model.Node.ExperienceNodeSolutionType;

import java.util.ArrayList;

public class ActionSector {

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
    // 当前任务
    private MissionPackage currenMission;
    // 当前思考内容
    private ArrayList<String> currenIdea;
    // 当前说话内容
    private ArrayList<String> currenSay;
    // 上一个思考内容
    private ArrayList<String> lastIdea;
    // 当前编号
    private int currenId;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新的行动分区
    public ActionSector(ArrayList<MissionPackage> input_processMissionList, ArrayList<MissionPackage> input_waitMissionList,
                        ArrayList<MissionPackage> input_holdMissionList, ArrayList<MissionPackage> input_endMissionList,
                        ActionLogicListen input_listenLogic, ActionLogicThink input_thinkLogic) {
        processMissionList = input_processMissionList;
        waitMissionList = input_waitMissionList;
        holdMissionList = input_holdMissionList;
        endMissionList = input_endMissionList;
        listenLogic = input_listenLogic;
        thinkLogic = input_thinkLogic;
        currenMission = null;
        currenIdea = null;
        currenSay = null;
        lastIdea = null;
        currenId = 1;
    }

    // ##### 功能函数 #####
    // 获取暂存任务
    public MissionPackage getCurrenMission() {
        return currenMission;
    }

    // 获取思考内容
    public ArrayList<String> getCurrenIdea() {
        return currenIdea;
    }

    // 获取说内容
    public ArrayList<String> getCurrenSay() {
        return currenSay;
    }

    // 重置全部输出变量
    public void resetCurren() {
        currenSay =  null;
        currenIdea =  null;
        currenMission =  null;
    }

    // 执行一个等级最高的行为
    // Say = 4 > Listen = 3 > think = 2 > idea = 1 > nothing = 0
    public void action() {
        MissionPackage max_package = null;
        try {
            int max = 0;
            for (MissionPackage temp_package : processMissionList) {
                if (temp_package.getRank() > max) {
                    max = temp_package.getRank();
                    max_package = temp_package;
                }
            }
            if (max_package != null) {
                if (!this.actionDiff(max_package)) {
                    debug.error("#ActionSector        <Action>       -Error: " + this.HexId(max_package.getCurrenId()));
                }
            }
        }
        catch (Exception e) {
            this.remove(max_package);
            debug.error(e);
            debug.error("#ActionSector        <Action>       -BigError");
        }
    }

    // 分类并执行任务
    private boolean actionDiff(MissionPackage input_package) {
        switch (input_package.getType()) {
            case Idea -> {
                debug.log("#ActionSector        <Action>       -Do Idea: " + this.HexId(input_package.getCurrenId()));
                return this.doIdea(input_package);
            }
            case Listen -> {
                debug.log("#ActionSector        <Action>       -Do Listen: " + this.HexId(input_package.getCurrenId()));
                return this.doListen(input_package);
            }
            case Say -> {
                debug.log("#ActionSector        <Action>       -Do Say: " + this.HexId(input_package.getCurrenId()));
                return this.doSay(input_package);
            }
            case Think, Reflect, Function -> {
                debug.log("#ActionSector        <Action>       -Do Think: " + this.HexId(input_package.getCurrenId()));
                return this.doThink(input_package);
            }
        }
        return false;
    }

    // 执行想法逻辑
    private boolean doIdea(MissionPackage input_package) {
        MissionPackageIdea temp_package = (MissionPackageIdea) input_package;
        if (!temp_package.comprehend()) {
            debug.fail("#ActionSector        <Idea>         -CanNotComprehend: " + this.HexId(temp_package.getCurrenId()));
            this.remove(temp_package);
            return true;
        }
        if (!temp_package.thinking()) {
            debug.error("#ActionSector        <Idea>         -Error: " + this.HexId(temp_package.getCurrenId()));
            this.remove(temp_package);
            return false;
        }
        if (temp_package.getSolution() == null) {
            //temp_package.moreThinking();
            if (temp_package.getSolution() == null) {
                if (temp_package.isWait()) {
                    ArrayList<String> new_idea = listenLogic.independentWait(temp_package.getOrigin());
                    ArrayList<Integer> temp_id = new ArrayList<>();
                    temp_id.add(this.newId());
                    MissionPackageIdea temp_idea = new MissionPackageIdea(new_idea, listenLogic, temp_id, "LISTEN");
                    processMissionList.add(temp_idea);
                    debug.notice("#ActionSector        <Idea>         -NewListen: " + this.HexId(temp_package.getCurrenId()));
                }
                else {
                    debug.fail("#ActionSector        <Idea>         -NoMoreSolution: " + this.HexId(temp_package.getCurrenId()));
                    this.makeIdea(temp_package.getOrigin(), "NoIdea");
                }
                this.remove(temp_package);
                this.toEnd(temp_package);
                return true;
            }
            //temp_package.getCognition().add(0, "guess");
        }
        if (temp_package.getSolution().getType() == ExperienceNodeSolutionType.Say) {
            debug.log("#ActionSector        <Idea>         -ToSay: " + this.HexId(temp_package.getCurrenId()));
            processMissionList.add(new MissionPackageSay(temp_package.getOrigin(), temp_package.getCognition(),
                    temp_package.getSolution(), thinkLogic, temp_package.getId()));
            this.makeIdea(temp_package.getOrigin(), "Done");
        }
        else {
            debug.log("#ActionSector        <Idea>         -ToThink: " + this.HexId(temp_package.getCurrenId()));
            // sikao
            MissionPackageThink x = new MissionPackageThink(temp_package.getOrigin(), temp_package.getCognition(),
                    temp_package.getSolution(), thinkLogic, temp_package.getId());
            processMissionList.add(x);

            //
            if (temp_package.getHeader().equals("SAY")) {
                this.makeIdea(temp_package.getOrigin(), "Say");
            }
            else {
                this.makeIdea(temp_package.getOrigin(), "Idea");
            }
        }
        this.toEnd(temp_package);
        this.remove(temp_package);
        return true;
    }

    // 执行听逻辑
    private boolean doListen(MissionPackage input_package) {
        MissionPackageListen temp_package = (MissionPackageListen) input_package;
        if (!temp_package.comprehend()) {
            if (temp_package.getQuery() == null) {
                debug.error("#ActionSector        <Listen>       -NoQuery: " + this.HexId(temp_package.getCurrenId()));
                this.remove(temp_package);
                return false;
            }
            ArrayList<Integer> temp_id = new ArrayList<>(temp_package.getId());
            temp_id.add(this.newId());
            processMissionList.add(new MissionPackageIdea(temp_package.getQuery(), listenLogic, temp_id, "LISTEN"));
            debug.log("#ActionSector        <Listen>       -Derivative: " + this.HexId(temp_package.getCurrenId()));
            this.toEnd(temp_package);
            this.remove(temp_package);
            return true;
        }
        if (!temp_package.thinking()) {
            debug.error("#ActionSector        <Listen>       -Error: " + this.HexId(temp_package.getCurrenId()));
            this.remove(temp_package);
            return false;
        }
        if (temp_package.getSolution() == null) {
            //temp_package.moreThinking();
            if (temp_package.getSolution() == null) {
                debug.fail("#ActionSector        <Listen>       -NoMoreSolution: " + this.HexId(temp_package.getCurrenId()));
                this.remove(temp_package);
                return true;
            }
            //temp_package.getCognition().add(0, "guess");
        }
        if (temp_package.getSolution().getType() == ExperienceNodeSolutionType.Say) {
            debug.log("#ActionSector        <Listen>       -ToSay: " + this.HexId(temp_package.getCurrenId()));
            processMissionList.add(new MissionPackageSay(temp_package.getOrigin(), temp_package.getCognition(),
                    temp_package.getSolution(), thinkLogic, temp_package.getId()));
        }
        else {
            debug.log("#ActionSector        <Listen>       -ToThink: " + this.HexId(temp_package.getCurrenId()));
            MissionPackageThink temp_think = new MissionPackageThink(temp_package.getOrigin(), temp_package.getCognition(),
                    temp_package.getSolution(), thinkLogic, temp_package.getId());
            temp_think.newIdea();
            processMissionList.add(temp_think);
        }
        this.toEnd(temp_package);
        this.remove(temp_package);
        return true;
    }

    // 执行说逻辑
    private boolean doSay(MissionPackage input_package) {
        MissionPackageSay temp_package = (MissionPackageSay) input_package;
        if (temp_package.thinking()) {
            this.makeSay(temp_package);
            debug.done("#ActionSector        <Say>          -Done: " + this.HexId(temp_package.getCurrenId()));
            ArrayList<Integer> temp_id = new ArrayList<>(temp_package.getId());
            temp_id.add(this.newId());
            processMissionList.add(new MissionPackageIdea(temp_package.getContent(), listenLogic, temp_id, "SAY"));
            debug.notice("#ActionSector        <Say>          -Derivative: " + this.HexId(temp_package.getCurrenId()));
            //temp_package.beHold();
            //this.toHold(temp_package);
            this.remove(temp_package);
            return true;
        }
        if (temp_package.isHold()) {
            ArrayList<Integer> temp_id = new ArrayList<>(temp_package.getId());
            temp_id.add(this.newId());
            processMissionList.add(new MissionPackageIdea(temp_package.getIdea(), listenLogic, temp_id, "THINK"));
            debug.log("#ActionSector        <Say>          -HoldUp: " + this.HexId(temp_package.getCurrenId()));
            this.toHold(temp_package);
            this.remove(temp_package);
            this.makeIdea(temp_package.getContent(), "Think");
            return true;
        }
        debug.error("#ActionSector        <Say>          -Error: " + this.HexId(temp_package.getCurrenId()));
        this.remove(temp_package);
        return false;
    }

    // 执行思考逻辑
    private boolean doThink(MissionPackage input_package) {
        try {
            MissionPackageThink temp_package = (MissionPackageThink) input_package;
            if (this.thinkDiff(temp_package)) {
                if (temp_package.isReflect()) {
                    temp_package.beReplace();
                    ArrayList<Integer> temp_id = new ArrayList<>(temp_package.getId());
                    temp_id.add(this.newId());
                    processMissionList.add(new MissionPackageIdea(temp_package.getConclusion(), listenLogic, temp_id, "THINK"));
                    debug.notice("#ActionSector        <Think>        -Reflect: " + this.HexId(temp_package.getCurrenId()));
                    this.toHold(temp_package);
                }
                else if (temp_package.isNewIdea()) {
                    ArrayList<Integer> temp_id = new ArrayList<>(temp_package.getId());
                    temp_id.add(this.newId());
                    processMissionList.add(new MissionPackageIdea(temp_package.getConclusion(), listenLogic, temp_id, "THINK"));
                    debug.notice("#ActionSector        <Think>        -NewIdea");
                    this.remove(temp_package);
                    return true;
                }
                else {
                    debug.done("#ActionSector        <Think>        -Finish: " + this.HexId(temp_package.getCurrenId()));
                    temp_package.beHold();
                    this.toHold(temp_package);
                    this.makeThink(temp_package);
                }
                if (temp_package.isExpect()) {
                    this.makeIdea(temp_package.getConclusion(), "Wait");
                }
                if (temp_package.isReflect()) {
                    this.makeIdea(temp_package.getConclusion(), "Reflect");
                }
                else {
                    this.makeIdea(temp_package.getConclusion(), "Done");
                }
                //this.toEnd(temp_package);
                this.remove(temp_package);
                return true;
            }
            if (temp_package.isHold()) {
                ArrayList<Integer> temp_id = new ArrayList<>(temp_package.getId());
                temp_id.add(this.newId());
                processMissionList.add(new MissionPackageIdea(temp_package.getIdea(), listenLogic, temp_id, "THINK"));
                debug.log("#ActionSector        <Think>        -HoldUp: " + this.HexId(temp_package.getCurrenId()));
                this.toHold(temp_package);
                this.remove(temp_package);
                this.makeIdea(temp_package.getConclusion(), "Think");
                return true;
            }
            debug.error("#ActionSector        <Think>        -Error: " + this.HexId(temp_package.getCurrenId()));
            this.remove(temp_package);
            return false;
        }
        catch (Exception e) {
            debug.error(e);
        }
        return false;
    }

    // 进行分类思考
    private boolean thinkDiff(MissionPackageThink input_package) {
        switch (input_package.getType()) {
            case Think -> {
                if (input_package.thinking()) {
                    return true;
                }
            }
            case Reflect -> {
                if (input_package.reflecting()) {
                    return true;
                }
            }
            case Function -> {
                if (input_package.functioning()) {
                    if (input_package.isEnhance()) {
                        debug.notice("#ActionSector        <Enhance>      -Start");
                        this.doEnhance(input_package.getOriginId());
                    }
                    else if (input_package.isRestrain()) {
                        debug.notice("#ActionSector        <Restrain>     -Start");
                        this.doRestrain(input_package.getOriginId());
                    }
                    else if (input_package.isExpect() && !input_package.isReflect()) {
                        debug.notice("#ActionSector        <Expect>       -Add");
                        this.toWait(input_package);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // 增强已经思考的路线
    private void doEnhance(int input_id) {
        for (MissionPackage temp_package : endMissionList) {
            if (temp_package.getId().contains(input_id)) {
                temp_package.enhance();
                endMissionList.remove(temp_package);
                debug.log("#ActionSector        <Enhance>      -" + this.HexId(temp_package.getCurrenId()));
                break;
            }
        }
        debug.done("#ActionSector        <Enhance>      -Done");
    }

    // 减弱已经思考的路线
    private void doRestrain(int input_id) {
        for (MissionPackage temp_package : endMissionList) {
            if (temp_package.getId().contains(input_id)) {
                temp_package.restrain();
                endMissionList.remove(temp_package);
                debug.log("#ActionSector        <Restrain>     -" + this.HexId(temp_package.getCurrenId()));
                break;
            }
        }
        debug.done("#ActionSector        <Restrain>     -Done");
    }

    // 奖励当前所有行为
    public void enhanceAll() {
        ArrayList<MissionPackage> deleteList = new ArrayList<>();
        for (MissionPackage temp_package : endMissionList) {
            temp_package.enhance();
            deleteList.add(temp_package);
            debug.log("#ActionSector        <Enhance>      -" + this.HexId(temp_package.getCurrenId()));
        }
        for (MissionPackage temp_package : deleteList) {
            endMissionList.remove(temp_package);
        }
        debug.done("#ActionSector        <Enhance>      -Done");
    }

    // 惩罚当前所有行为
    public void restrainAll() {
        ArrayList<MissionPackage> deleteList = new ArrayList<>();
        for (MissionPackage temp_package : endMissionList) {
            temp_package.restrain();
            deleteList.add(temp_package);
            debug.log("#ActionSector        <Restrain>     -" + this.HexId(temp_package.getCurrenId()));
        }
        for (MissionPackage temp_package : deleteList) {
            endMissionList.remove(temp_package);
        }
        debug.done("#ActionSector        <Restrain>     -Done");
    }


    // 发呆1秒钟
    public void beBored() {
        try {
            debug.log("#ActionSector        <Bored>        -Wait");
            Thread.sleep(1500);
        }
        catch (Exception e) {
            debug.error("#ActionSector        <Bored>        -Error");
        }
    }

    // 添加说内容到队列
    private void makeSay(MissionPackageSay input_package) {
        input_package.resetWeight();
        currenSay = input_package.getContent();
        endMissionList.add(input_package);
        processMissionList.remove(input_package);
    }

    // 添加思考内容到队列
    private void makeIdea(ArrayList<String> input_idea, String input_case) {
        if (input_idea.isEmpty()) {
            return;
        }
        if (lastIdea != null) {
            if (lastIdea.size() == input_idea.size()) {
                boolean isSame = true;
                for (int i = 0; i < lastIdea.size(); i ++){
                    if (!lastIdea.get(i).equals(input_idea.get(i))) {
                        isSame = false;
                        break;
                    }
                }
                if (isSame) {
                    return;
                }
            }
        }
        lastIdea = input_idea;
        switch (input_case) {
            case ("Idea") -> {
                currenIdea = new ArrayList<>();
                currenIdea.addAll(input_idea);
                currenIdea.add("?");
            }
            case ("Think") -> {
                currenIdea = new ArrayList<>();
                currenIdea.addAll(input_idea);
                currenIdea.add("...");
            }
            case ("Done") -> {
                currenIdea = new ArrayList<>();
                currenIdea.addAll(input_idea);
            }
            case ("Say") -> {
                currenIdea = new ArrayList<>();
                currenIdea.add("i said");
                currenIdea.addAll(input_idea);
            }
            case ("Wait") -> {
                currenIdea = new ArrayList<>();
                currenIdea.add("waiting response for");
                currenIdea.addAll(input_idea);
            }
            case ("Reflect") -> {
                currenIdea = new ArrayList<>();
                currenIdea.add("how to say");
                currenIdea.addAll(input_idea.subList(1,input_idea.size()));
            }
            case ("NoIdea") -> {
                currenIdea = new ArrayList<>();
                currenIdea.add("no more idea for");
                currenIdea.addAll(input_idea);
            }
        }
    }

    // 暂存当前思考任务
    private void makeThink(MissionPackageThink input_package) {
        input_package.resetWeight();
        currenMission = input_package;
        if (!input_package.isEnhance() && !input_package.isRestrain()) {
            endMissionList.add(input_package);
        }
        processMissionList.remove(input_package);
    }

    // 转移任务到等待区
    private void toWait(MissionPackage input_package) {
        input_package.resetWeight();
        waitMissionList.add(input_package);
        //processMissionList.remove(input_package);
    }

    // 转移任务到挂起区
    private void toHold(MissionPackage input_package) {
        input_package.resetWeight();
        holdMissionList.add(input_package);
        //processMissionList.remove(input_package);
    }

    // 转移任务到结束区
    private void toEnd(MissionPackage input_package) {
        input_package.resetWeight();
        endMissionList.add(input_package);
        //processMissionList.remove(input_package);
    }

    // 移除任务
    private void remove(MissionPackage input_package) {
        processMissionList.remove(input_package);
    }

    // 获取新的唯一id
    private int newId(){
        currenId += 2;
        return currenId;
    }

    // 转换id为16进制
    public String HexId(int input_id){
        StringBuilder text = new StringBuilder();
        String hex = Integer.toHexString(input_id).toUpperCase();
        text.append("0".repeat(Math.max(0, 4 - hex.length())));
        return text + hex;
    }
}
