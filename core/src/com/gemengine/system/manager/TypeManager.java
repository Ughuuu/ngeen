package com.gemengine.system.manager;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.DefaultBindingScopingVisitor;
import com.google.inject.spi.DefaultElementVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.InstanceBinding;

import lombok.val;

public abstract class TypeManager<T> {
	private Injector injector;
	protected final Map<String, T> types;
	private final List<String> removeList;
	private final List<Class<? extends T>> copyList;
	private final ObjectMapper objectMapper;
	private final Class<T> classType;

	public TypeManager(Class<T> type) {
		classType = type;
		types = new HashMap<String, T>();
		copyList = new ArrayList<Class<? extends T>>();
		removeList = new ArrayList<String>();
		objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NON_PRIVATE);
	}

	@SuppressWarnings("unchecked")
	public <U extends T> U getType(String type) {
		return (U) types.get(type);
	}

	public <T> T inject(Class<T> type) {
		return injector.getInstance(type);
	}

	public static boolean extendsType(Class<?> type, Class<?> extendsType) {
		if (type == null || type.equals(Object.class)) {
			return false;
		}
		return type.equals(extendsType) || extendsType(type.getSuperclass(), extendsType);
	}

	private void addAll(List<Class<? extends T>> types, Class<? extends T> type) {
		InjectionPoint injectionPoint = InjectionPoint.forConstructorOf(type);
		List<Dependency<?>> dependencies = injectionPoint.getDependencies();
		for (val dependency : dependencies) {
			Class<?> dependencyClass = dependency.getKey().getTypeLiteral().getRawType();
			if (extendsType(dependencyClass, classType)) {
				if (types.contains(dependencyClass)) {
					types.remove((Class<? extends T>) dependencyClass);
				}
				types.add((Class<? extends T>) dependencyClass);
				addAll(types, (Class<? extends T>) dependencyClass);
			}
		}
	}

	private List<Class<? extends T>> getAllSystems(List<Class<? extends T>> types) {
		List<Class<? extends T>> toInstantiate = new ArrayList<Class<? extends T>>();
		for (val cls : types) {
			if (toInstantiate.contains(cls)) {
				toInstantiate.remove(cls);
			}
			toInstantiate.add(cls);
			addAll(toInstantiate, cls);
		}
		return toInstantiate;
	}

	private void copyElement(T oldObject, T newObject) {
		try {
			if (oldObject != null) {
				String oldTypeData;
				oldTypeData = objectMapper.writeValueAsString(oldObject);
				objectMapper.readerForUpdating(newObject).readValue(oldTypeData);
				elementCopy(oldObject, newObject);
			} else {
				elementAdd(newObject);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	protected abstract List<Class<? extends T>> getExcludeList();

	private List<T> addInstances() {
		for (val key : injector.getAllBindings().entrySet()) {
			Class<?> type = key.getKey().getTypeLiteral().getRawType();
			final Binding<?> value = key.getValue();
			if (extendsType(type, classType) && (value instanceof InstanceBinding)) {
				InstanceBinding instanceBinding = (InstanceBinding) value;
				T instance = (T) instanceBinding.getInstance();
				types.put(type.getName(), instance);
			}
		}
		return null;
	}

	private void doCopySystemsLogic(List<Class<? extends T>> excludeList) {
		List<Class<? extends T>> toInstantiateList = getAllSystems(copyList);
		Collections.reverse(toInstantiateList);
		List<Class<? extends T>> toRemoveList = new ArrayList<Class<? extends T>>(toInstantiateList);
		toRemoveList.removeAll(excludeList);
		Map<String, T> oldTypes = new HashMap<String, T>();
		Map<String, T> newTypes = new HashMap<String, T>();
		// remove old types
		for (val copyItem : toRemoveList) {
			T oldType = types.remove(copyItem.getName());
			if (oldType != null) {
				oldTypes.put(oldType.getClass().getName(), oldType);
			}
		}
		doMapping();
		// instantiate new types
		for (val copyItem : toInstantiateList) {
			if (types.containsKey(copyItem)) {
				continue;
			}
			try {
				T type = (T) inject(copyItem);
				newTypes.put(type.getClass().getName(), type);
				types.put(type.getClass().getName(), type);
				addInstances();
				doMapping();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		// deserialize old types into new types
		for (val copyItem : toRemoveList) {
			String key = copyItem.getName();
			T oldElement = oldTypes.get(key);
			copyElement(oldElement, newTypes.get(key));
		}
		copyList.clear();
	}

	public void onInit() {
		doCopySystemsLogic(new ArrayList<>());
	}

	public void onUpdate(float delta) {
		doCopySystemsLogic(getExcludeList());
		for (String type : removeList) {
			T element = types.remove(type);
			elementDelete(element);
			doMapping();
		}
		removeList.clear();
	}

	public void removeType(String type) {
		removeList.add(type);
	}

	public <U extends T> void replaceType(Class<U> typeClass) {
		copyList.add(typeClass);
	}

	protected void doMapping() {
		injector = Guice.createInjector(getModule());
	}

	protected abstract void elementAdd(T element);

	protected abstract void elementCopy(T oldElement, T newElement);

	protected abstract void elementDelete(T element);

	protected abstract Module getModule();
}
