package AI.Mind.Editor;

import AI.Mind.General.Timer;

import java.io.File;

public class MemoryBuilder {

    public static void main(String[] args) throws InterruptedException {
        String path = "src/AI/Memory/Save/Rebuild";
        new CognitionEditor(path);
        new ExperienceEditor(path);
        new ReminisceEditor(path);
        Thread.sleep(3000);
        System.out.println("        ########## DONE ##########      ");

    }
}
