package UI.GUI;

import AI.Memory.Cognition.Model.Node.CognitionNode;
import AI.Memory.Experience.Model.Node.ExperienceNode;
import AI.Memory.Experience.Model.Node.ExperienceNodeKnot;
import AI.Memory.Reminisce.Model.Node.ReminisceNode;
import AI.Mind.MindController;

import java.awt.*;

public class Tester {
    static MindController mind = new MindController();
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    doTest();
                    System.out.println("Pass the loading test");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public static void doTest() {
        for(CognitionNode temp : mind.getMemory().getCognitionSector().getCognitionNet().values()) {
            System.out.println(temp);
        }
        for(ExperienceNodeKnot temp : mind.getMemory().getExperienceSector().getExperienceTree()) {
            for (ExperienceNode tem : temp.getNodes()) {
                System.out.println(tem);
            }
        }
        for (ExperienceNode temp : mind.getMemory().getExperienceSector().getExperienceSubTree()) {
            System.out.println(temp);
        }
        for (ReminisceNode temp : mind.getMemory().getReminisceSector().getReminisceBase()) {
            System.out.println(temp);
        }
    }
}
