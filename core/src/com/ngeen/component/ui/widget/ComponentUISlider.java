package com.ngeen.component.ui.widget;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.utils.XmlWriter;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.ngeen.component.ComponentUILayout;
import com.ngeen.component.ComponentUIWidget;
import com.ngeen.engine.Ngeen;
import com.ngeen.entity.Entity;

public class ComponentUISlider extends ComponentUIWidget {
	private boolean _Saved = false;
	
	public ComponentUISlider(Ngeen ng, Entity ent) {
		super(ng, ent);
		SliderStyle style = new SliderStyle();
		_Widget = new Slider(0, 1, 0.1f, true, style);
		getOwner().addSuperComponent((ComponentUIWidget)this);
	}

	@Override
	public void act(float delta) {
		_Widget.act(delta);
	}

	@Override
	public ComponentUISlider remove() {
		getOwner().removeComponent(ComponentUIWidget.class);
		_Owner.removeComponent(this.getClass(), Id);
		return this;
	}

	@Override
	protected void Save(XmlWriter element) throws Exception {
		if(_Saved){
			_Saved = false;
			return;
		}
		_Saved = true;
		element.element("Component").attribute("_Type", this.getClass().getName()).pop();
	}

	@Override
	protected void Load(Element element) throws Exception {
	}
}