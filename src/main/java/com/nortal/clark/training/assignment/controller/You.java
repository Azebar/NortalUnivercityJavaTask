package com.nortal.clark.training.assignment.controller;

import com.nortal.clark.training.assignment.model.CityMap;
import com.nortal.clark.training.assignment.model.Clark;
import com.nortal.clark.training.assignment.model.Direction;
import com.nortal.clark.training.assignment.model.Position;
import com.nortal.clark.training.assignment.model.SpeedLevel;
import com.nortal.clark.training.assignment.model.VoiceCommand;

import java.util.Comparator;
import java.util.List;

public class You {

    private List<Position> targetsToCapture;

    public VoiceCommand getNextStep(Clark clark, CityMap cityMap) {
        VoiceCommand voiceCommand = new VoiceCommand(Direction.SOUTH, SpeedLevel.L0_RUNNING_HUMAN);
        // TODO: Implement algorithm to return command to Clark to capture all the targets on the city map provided
        //<<SOLUTION START>>

        if (targetsToCapture == null) {
            targetsToCapture = cityMap.getTargets();
            //Sort targets so that Clark can reach them more efficiently
            targetsToCapture.sort(Comparator.comparingInt((Position p) -> p.x).thenComparingInt(p -> p.y));
        }

        Position targetToCapture = targetsToCapture.get(0);

        System.out.println(clark + " ->> x=" + targetToCapture.x + ", y=" + targetToCapture.y);

        int diffX = Math.abs(targetToCapture.x - clark.getPosition().x);
        int diffY = Math.abs(targetToCapture.y - clark.getPosition().y);

        SpeedLevel horizontalSpeedLevel = thinkOfSpeedLevel(diffX, clark.getHorizontal());
        SpeedLevel verticalSpeedLevel = thinkOfSpeedLevel(diffY, clark.getVertical());
        //Used the geometry formula for distance between two points to check if Clark is close enough to target
        if (Math.sqrt((diffX*diffX) + (diffY * diffY)) < 2) {
            System.out.println("Removing target");
            //Consider it captured
            targetsToCapture.remove(0);
        } else if (targetToCapture.x > clark.getPosition().x) {
            voiceCommand = new VoiceCommand(Direction.EAST, horizontalSpeedLevel);
        } else if (targetToCapture.y > clark.getPosition().y) {
            voiceCommand = new VoiceCommand(Direction.NORTH, verticalSpeedLevel);
        } else if (targetToCapture.x < clark.getPosition().x) {
            voiceCommand = new VoiceCommand(Direction.WEST, horizontalSpeedLevel);
        } else if (targetToCapture.y < clark.getPosition().y) {
            voiceCommand = new VoiceCommand(Direction.SOUTH, verticalSpeedLevel);
        }

        System.out.println(voiceCommand);
        //<<SOLUTION END>>
        return voiceCommand;
    }

    //<<SOLUTION START>>
    private SpeedLevel thinkOfSpeedLevel(int distanceDiff, double speed) {
        //WATER_DRAG_THRESHOLD = 1.6, TARGET_PROXIMITY_THRESHOLD = 2
        //To find drag for water you have to use this equation:
        //0.5 * velocity^2 * water density which is 1000kg/m^3 * Drag Coefficient * Cross Sectional Area
        double drag = 0.5 * speed * speed * 1.6 * 2;
        //Slowing Clark down not to miss the target, or not to go out of city limits, when target is near it
        if (distanceDiff < (int) Math.round(drag))
            return SpeedLevel.L0_RUNNING_HUMAN;

        return SpeedLevel.L4_MACH_9350;
    }
    //<<SOLUTION END>>
}
