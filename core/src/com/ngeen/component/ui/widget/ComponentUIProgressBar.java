package com.ngeen.component.ui.widget;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.utils.XmlWriter;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.ngeen.component.ComponentUILayout;
import com.ngeen.component.ComponentUIWidget;
import com.ngeen.engine.Ngeen;
import com.ngeen.entity.Entity;

public class ComponentUIProgressBar extends ComponentUIWidget {
	private boolean _Saved = false;
	
	public ComponentUIProgressBar(Ngeen ng, Entity ent) {
		super(ng, ent);
		ProgressBarStyle style = new ProgressBarStyle();
		_Widget = new ProgressBar(0, 1, 0.1f, true, style);
		getOwner().addSuperComponent((ComponentUIWidget)this);
	}

	@Override
	public void act(float delta) {
		_Widget.act(delta);
	}

	@Override
	public ComponentUIProgressBar remove() {
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