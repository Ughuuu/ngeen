package com.gemengine.system.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gemengine.engine.GemConfiguration;
import com.gemengine.system.base.SystemBase;
import com.gemengine.system.base.TimedSystem;
import com.google.inject.Module;

public class SystemManager extends TypeManager<SystemBase> {
	private static enum State {
		Added, Started, Destroyed
	}

	private final List<SystemBase> baseSystems;
	private final Map<SystemBase, State> systemToState;
	protected int toStart = 0;

	private final Comparator<SystemBase> priorityComparator = new Comparator<SystemBase>() {
		@Override
		public int compare(SystemBase system1, SystemBase system2) {
			if (system1.getPriority() < system2.getPriority()) {
				return -1;
			}
			if (system1.getPriority() > system2.getPriority()) {
				return 1;
			}
			return 0;
		}
	};
	private final List<TimedSystem> timedSystems;
	private final GemConfiguration configuration;

	public SystemManager(GemConfiguration configuration) {
		super();
		this.configuration = configuration;
		baseSystems = new ArrayList<SystemBase>();
		systemToState = new HashMap<SystemBase, State>();
		timedSystems = new ArrayList<TimedSystem>();
		doMapping();
	}

	public void onInit() {
		super.onUpdate(0);
		for (SystemBase system : baseSystems) {
			system.onInit();
			systemToState.put(system, State.Started);
		}
		toStart = 0;
	}

	@Override
	public void onUpdate(float delta) {
		super.onUpdate(delta);
		for (TimedSystem system : timedSystems) {
			State state = systemToState.get(system);
			if (state == State.Started) {
				system.onUpdate(delta);
			}
		}
		if (toStart == 0) {
			return;
		}
		toStart = 0;
		for (SystemBase system : baseSystems) {
			State state = systemToState.get(system);
			if (state == State.Added) {
				system.onInit();
				systemToState.put(system, State.Started);
			}
		}
	}

	@Override
	protected void elementAdd(SystemBase element) {
		systemToState.put(element, State.Added);
		toStart++;
		baseSystems.add(element);
		if (element instanceof TimedSystem) {
			timedSystems.add((TimedSystem) element);
			Collections.sort(timedSystems, priorityComparator);
		}
		Collections.sort(baseSystems, priorityComparator);
	}

	@Override
	protected void elementCopy(SystemBase oldElement, SystemBase newElement) {
		State state = systemToState.get(oldElement);
		if (state == null) {
			elementAdd(newElement);
		} else {
			elementDelete(oldElement);
			elementAdd(newElement);
			toStart--;
			systemToState.put(newElement, state);
		}
	}

	@Override
	protected void elementDelete(SystemBase element) {
		baseSystems.remove(element);
		if (element instanceof TimedSystem) {
			timedSystems.remove(element);
			Collections.sort(timedSystems, priorityComparator);
		}
		Collections.sort(baseSystems, priorityComparator);
	}

	@Override
	protected Module getModule() {
		return new SystemManagerModule(this, configuration);
	}
}