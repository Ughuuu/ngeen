package com.ngeen.debug;

import com.ngeen.component.ComponentPoint;
import com.ngeen.engine.Ngeen;

public class TestEntity {
	public TestEntity(Ngeen ng){
		ng.EntityBuilder.makeEntity("e1");
		ng.EntityBuilder.makeEntity("e2");
		ng.EntityBuilder.makeEntity("e3");
		ng.EntityBuilder.makeEntity("e4");
		ng.EntityBuilder.makeEntity("e5");
		
		assert(ng.EntityBuilder.getByName("e1")!=null);
		assert(ng.EntityBuilder.getByName("e2")!=null);
		assert(ng.EntityBuilder.getByName("e3")!=null);
		assert(ng.EntityBuilder.getByName("e4")!=null);
		assert(ng.EntityBuilder.getByName("e5")!=null);

		ng.EntityBuilder.removeEntity("e1");
		ng.EntityBuilder.removeEntity("e2");
		ng.EntityBuilder.removeEntity("e3");
		ng.EntityBuilder.removeEntity("e4");
		
		assert(ng.EntityBuilder.getByName("e1")==null);
		assert(ng.EntityBuilder.getByName("e2")==null);
		assert(ng.EntityBuilder.getByName("e3")==null);
		assert(ng.EntityBuilder.getByName("e4")==null);
		
		
		ng.EntityBuilder.makeEntity("e1");
		ng.EntityBuilder.makeEntity("e2");
		ng.EntityBuilder.makeEntity("e3");
		ng.EntityBuilder.makeEntity("e4");
		ng.EntityBuilder.makeEntity("e5");

		assert(ng.EntityBuilder.getByName("e1")!=null);
		assert(ng.EntityBuilder.getByName("e2")!=null);
		assert(ng.EntityBuilder.getByName("e3")!=null);
		assert(ng.EntityBuilder.getByName("e4")!=null);
		assert(ng.EntityBuilder.getByName("e5")!=null);
		
		Debugger.log("TestEntity()---PASS");
	}
}
