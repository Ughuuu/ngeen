package com.ngeen.component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ngeen.debug.Debugger;
import com.ngeen.engine.EngineInfo;
import com.ngeen.engine.Ngeen;
import com.ngeen.entity.Entity;

public class ComponentFactory {
	private final Ngeen _Ng;
	// list not array because can't instantiate array of generic:(
	private Map<Class<?>, List<ComponentBase>> _ComponentCache;
	private Map<Class<?>, Integer> _ComponentCacheIndex;

	public ComponentFactory(Ngeen ng) {
		_Ng = ng;
		_ComponentCache = new HashMap<Class<?>, List<ComponentBase>>();
		_ComponentCacheIndex = new HashMap<Class<?>, Integer>();
	}

	public <T extends ComponentBase> T createComponent(Class<?> type, Entity ent) {
		try {
			T el = null;
			Integer size = _ComponentCacheIndex.get(type);
			if (size != null && size > 0) {
				size--;
				List<ComponentBase> list = _ComponentCache.get(type);
				el = (T) list.get(size);
				_ComponentCacheIndex.put(type, size);
			} else {
				el = (T) type.getConstructor(Ngeen.class).newInstance(_Ng);
			}
			el.OwnerId = ent.getId();
			return el;
		} catch (Exception e) {
			Debugger.log(e.toString());
		}
		return null;
	}

	public <T extends ComponentBase> void removeComponent(Class<?> type, T component) {
		Integer size = _ComponentCacheIndex.get(type);
		List<ComponentBase> list = _ComponentCache.get(type);
		if (list == null) {
			list = new ArrayList<ComponentBase>(EngineInfo.ComponentCache);
			for (int i = 0; i < EngineInfo.ComponentCache; i++) {
				list.add(null);
			}
			_ComponentCache.put(type, list);
			size = 0;
			_ComponentCacheIndex.put(type, size);
			_ComponentCache.put(type, list);
		}
		if (size < EngineInfo.ComponentCache) {
			_ComponentCacheIndex.put(type, size + 1);
			list.set(size, component);
		}
		size++;
	}
	
	public void clear(){
		_ComponentCache.clear();
		_ComponentCacheIndex.clear();
	}
}