package com.nortal.clark.training.test.provided;

import com.nortal.clark.training.assignment.model.*;
import com.nortal.clark.training.test.TrainingSimulationTest;
import org.junit.jupiter.api.Test;


public class ManyTargetsOnDifferentPositions extends TrainingSimulationTest {

    @Test
    void manyDifferentTargetsAcrossMap() {
        Clark clark = new Clark();
        CityMap cityMap = new CityMap(new Position(700, 700));
        cityMap.addTarget(4, 23);
        cityMap.addTarget(55, 98);
        cityMap.addTarget(230, 23);
        cityMap.addTarget(690, 239);
        cityMap.addTarget(550, 670);
        VoiceCommand voiceCommand = new VoiceCommand(Direction.SOUTH, SpeedLevel.L4_MACH_9350);
        runSimulation(cityMap, clark, voiceCommand);
    }
}
