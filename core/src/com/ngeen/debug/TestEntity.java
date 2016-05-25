package com.ngeen.debug;

import com.ngeen.component.ComponentPoint;
import com.ngeen.engine.Ngeen;

public class TestEntity {
    public TestEntity(Ngeen ng) {
        SimpleTest(ng);
        StressTest(ng);
        Debugger.log("TestEntity()---PASS");
    }

    private void SimpleTest(Ngeen ng) {
        ng.EntityBuilder.makeEntity("e1");
        ng.EntityBuilder.makeEntity("e2");
        ng.EntityBuilder.makeEntity("e3");
        ng.EntityBuilder.makeEntity("e4");
        ng.EntityBuilder.makeEntity("e5");

        assert (ng.EntityBuilder.getByName("e1") != null);
        assert (ng.EntityBuilder.getByName("e2") != null);
        assert (ng.EntityBuilder.getByName("e3") != null);
        assert (ng.EntityBuilder.getByName("e4") != null);
        assert (ng.EntityBuilder.getByName("e5") != null);

        ng.EntityBuilder.getByName("e1").remove();
        ng.EntityBuilder.getByName("e2").remove();
        ng.EntityBuilder.getByName("e3").remove();
        ng.EntityBuilder.getByName("e4").remove();

        assert (ng.EntityBuilder.getByName("e1") == null);
        assert (ng.EntityBuilder.getByName("e2") == null);
        assert (ng.EntityBuilder.getByName("e3") == null);
        assert (ng.EntityBuilder.getByName("e4") == null);

        ng.EntityBuilder.makeEntity("e1");
        ng.EntityBuilder.makeEntity("e2");
        ng.EntityBuilder.makeEntity("e3");
        ng.EntityBuilder.makeEntity("e4");
        ng.EntityBuilder.makeEntity("e5");

        assert (ng.EntityBuilder.getByName("e1") != null);
        assert (ng.EntityBuilder.getByName("e2") != null);
        assert (ng.EntityBuilder.getByName("e3") != null);
        assert (ng.EntityBuilder.getByName("e4") != null);
        assert (ng.EntityBuilder.getByName("e5") != null);

        ng.EntityBuilder.getByName("e1").remove();
        ng.EntityBuilder.getByName("e2").remove();
        ng.EntityBuilder.getByName("e3").remove();
        ng.EntityBuilder.getByName("e4").remove();
        ng.EntityBuilder.getByName("e5").remove();
    }

    private void StressTest(Ngeen ng) {
        for (int i = 0; i < 2000; i++) {
            ng.EntityBuilder.makeEntity("test" + i).addComponent(ComponentPoint.class);
        }
        for (int i = 0; i < 2000; i++) {
            ng.EntityBuilder.getByName("test" + i).remove();
        }
    }
}
