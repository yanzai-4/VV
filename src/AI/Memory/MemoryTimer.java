package AI.Memory;

// ##### 简介 #####
// 记忆池保存器
// 用于定期保存记忆

// ##### 日志 #####
// 1.0 版本
// 简单构建
// done

import AI.Mind.General.Debug;
import AI.Mind.General.Timer;
import AI.Memory.Cognition.CognitionSector;
import AI.Memory.Experience.ExperienceSector;
import AI.Memory.Reminisce.ReminisceSector;

import java.io.File;

public class MemoryTimer extends Thread {

    // ##### 变量 #####
    // 认知分区
    private CognitionSector cognitionSector;
    // 经验分区
    private ExperienceSector experienceSector;
    // 回忆分区
    private ReminisceSector reminisceSector;
    // 保存路径
    private String path;
    // 备份路径
    private String backupPath;
    // 保存间隔时间
    private int time = 300000;
    // 当前状态
    private boolean status;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    // 创建新指令
    public MemoryTimer(CognitionSector input_cognitionSector, ExperienceSector input_experienceSector,
                       ReminisceSector input_reminisceSector, String input_path, String input_backupPath) {
        cognitionSector = input_cognitionSector;
        experienceSector = input_experienceSector;
        reminisceSector = input_reminisceSector;
        path = input_path;
        status = true;
        backupPath = input_backupPath;
    }

    // ##### 功能函数 #####
    // 复写超线程功能
    @Override
    public void run() {
        int retry = 3;
        while(retry > 0) {
            try {
                Thread.sleep(time);
                debug.notice("#MemoryPool          <Save>         -Start");
                //String temp_backupPath = backupPath += "/" + new Timer().getTime();
                //new File(temp_backupPath).mkdirs();
                cognitionSector.setPath(path);
                cognitionSector.save();
                //cognitionSector.setPath(temp_backupPath);
                //cognitionSector.save();
                experienceSector.setPath(path);
                experienceSector.save();
                //experienceSector.setPath(temp_backupPath);
                //experienceSector.save();
                reminisceSector.setPath(path);
                reminisceSector.save();
                //reminisceSector.setPath(temp_backupPath);
                //reminisceSector.save();
                debug.done("#MemoryPool          <Save>         -Done");
                retry = 3;
            }
            catch (InterruptedException e ) {
                debug.notice("#MemoryPool          <Save>         -Interrupt");
                retry = 0;
            }
            catch (Exception e) {
                debug.error("#MemoryPool          <Save>         -Fail");
                retry--;
            }
        }
        debug.notice("#MemoryPool          <Save>         -Stop");
        status = false;
    }

    // 返回当前保存器是否工作
    public boolean isActive() {
        return status;
    }
}

