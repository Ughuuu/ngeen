package com.gem.component.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import com.gem.component.ComponentBase;
import com.gem.component.ComponentFactory;
import com.gem.engine.Gem;
import com.gem.entity.ComponentSpokesman;
import com.gem.entity.Entity;

public class ComponentUIList extends ComponentUIWidget {
	private List _List;
	private boolean _Saved = false;

	public ComponentUIList(Gem ng, Entity ent, ComponentFactory factory, ComponentSpokesman _ComponentSpokesman) {
		super(ng, ent, factory, _ComponentSpokesman);
		ListStyle style = new ListStyle();
		_List = new List(style);
	}

	@Override
	public ComponentUIList remove() {
		getOwner().removeComponent(ComponentUIWidget.class);
		Owner.removeComponent(this.getClass(), Id);
		return this;
	}

	@Override
	protected Actor getActor() {
		return _List;
	}

	@Override
	protected ComponentBase Load(Element element) throws Exception {
		return this;
	}

	@Override
	protected void Save(XmlWriter element) throws Exception {
		element.element("Component").attribute("Type", this.getClass().getName()).pop();
	}

	@Override
	protected void visitComponent(ComponentBase component, ComponentFactory factory) {
		factory.callComponentNotify(this, component);
	}
}
