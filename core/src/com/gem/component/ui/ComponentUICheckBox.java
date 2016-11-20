package com.gem.component.ui;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import com.gem.component.ComponentBase;
import com.gem.component.ComponentFactory;
import com.gem.engine.Gem;
import com.gem.entity.ComponentSpokesman;
import com.gem.entity.Entity;

public class ComponentUICheckBox extends ComponentUILayout {
	private CheckBox _CheckBox;
	private boolean _Saved = false;

	public ComponentUICheckBox(Gem ng, Entity ent, ComponentFactory factory, ComponentSpokesman _ComponentSpokesman) {
		super(ng, ent, factory, _ComponentSpokesman);
		CheckBoxStyle style = new CheckBoxStyle();
		_CheckBox = new CheckBox("Text", style);
	}

	@Override
	public ComponentUICheckBox remove() {
		getOwner().removeComponent(ComponentUILayout.class);
		Owner.removeComponent(this.getClass(), Id);
		return this;
	}

	@Override
	protected void add(ComponentUIBase comp) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void del(ComponentUIBase comp) {
		// TODO Auto-generated method stub

	}

	@Override
	protected WidgetGroup get() {
		return _CheckBox;
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
	protected void swap(ComponentUIBase a, ComponentUIBase b) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void visitComponent(ComponentBase component, ComponentFactory factory) {
		factory.callComponentNotify(this, component);
	}
}
