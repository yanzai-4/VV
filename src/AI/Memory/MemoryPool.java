package AI.Memory;

// ##### 简介 #####
// 记忆池
// 用于管理认知，经验，回忆分区

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

public class MemoryPool {

    // ##### 变量 #####
    // 认知分区
    private CognitionSector cognitionSector;
    // 经验分区
    private ExperienceSector experienceSector;
    // 回忆分区
    private ReminisceSector reminisceSector;
    // 默认保存路径
    private String path = "src/AI/Memory/Save/temp";
    // 默认重载路径
    private String rebuild_path = "src/AI/Memory/Save/Rebuild";
    // 默认备份路径
    private String backup_path = "src/AI/Memory/Save/Backup";
    // 自动保存进程
    private MemoryTimer timer;
    // 当前池状态
    private boolean status;
    // 报错开关
    private Debug debug = new Debug();

    // ##### 构造函数 #####
    public MemoryPool() {
        cognitionSector = new CognitionSector(path);
        experienceSector = new ExperienceSector(path);
        reminisceSector = new ReminisceSector(path);
        status = true;
        this.init();
    }

    // 返回当前认知分区
    public CognitionSector getCognitionSector() {
        return cognitionSector;
    }

    // 返回当前经验分区
    public ExperienceSector getExperienceSector() {
        return experienceSector;
    }

    // 返回当前回忆分区
    public ReminisceSector getReminisceSector() {
        return reminisceSector;
    }

    // 初始化记忆池
    public void init() {
        try{
            this.loadAll();
            if (cognitionSector.isAlive() && experienceSector.isAlive() && reminisceSector.isAlive()) {
                timer = new MemoryTimer(cognitionSector, experienceSector, reminisceSector, path, backup_path);
                timer.start();
            }
        }
        catch (Exception e) {
            status = false;
            debug.error("#MemoryPool          <Init>         -Fail");
        }
    }

    // 重载指令
    public void rebuild() {
        try{
            debug.notice("#MemoryPool          <Rebuild>      -Start");
            this.rebuildAll();
            this.pathAll(rebuild_path);
            this.loadAll();
            this.pathAll(path);
            debug.done("#MemoryPool          <Rebuild>      -Done");
        }
        catch (Exception e) {
            status = false;
            debug.error("#MemoryPool          <Rebuild>      -Fail");
        }
    }

    // 休眠指令
    public void sleep() {
        try{
            debug.notice("#MemoryPool          <Sleep>        -Start");
            timer.interrupt();
            this.pathAll(path);
            this.saveAll();
            String temp_backupPath = backup_path += "/" + new Timer().getTime();
            new File(temp_backupPath).mkdirs();
            //this.pathAll(temp_backupPath);
            //this.saveAll();
            debug.done("#MemoryPool          <Sleep>        -Done");
        }
        catch (Exception e) {
            status = false;
            debug.error("#MemoryPool          <Sleep>        -Fail");
        }
    }


    // 返回当前池是否存活
    public boolean isAlive() {
        return status && cognitionSector.isAlive() && experienceSector.isAlive() && reminisceSector.isAlive() && timer.isActive();
    }

    // 保存所有分区
    private void saveAll() {
        cognitionSector.save();
        experienceSector.save();
        reminisceSector.mergeSave();
    }

    // 读取所有分区
    private void loadAll() {
        cognitionSector.load();
        experienceSector.load();
        reminisceSector.load();
    }

    // 重载所有分区
    private void rebuildAll() {
        cognitionSector.rebuild();
        experienceSector.rebuild();
        reminisceSector.rebuild();
    }

    // 修改所有分区的保存地址
    private void pathAll(String input_path) {
        cognitionSector.setPath(input_path);
        experienceSector.setPath(input_path);
        reminisceSector.setPath(input_path);
    }
}
