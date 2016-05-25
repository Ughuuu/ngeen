package com.ngeen.component.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import com.ngeen.component.ComponentBase;
import com.ngeen.component.ComponentFactory;
import com.ngeen.engine.Ngeen;
import com.ngeen.entity.ComponentSpokesman;
import com.ngeen.entity.Entity;

public class ComponentUITextArea extends ComponentUIWidget {
    private boolean _Saved = false;
    private TextArea _TextArea;

    public ComponentUITextArea(Ngeen ng, Entity ent, ComponentFactory factory, ComponentSpokesman _ComponentSpokesman) {
        super(ng, ent, factory, _ComponentSpokesman);
        TextFieldStyle style = new TextFieldStyle();
        BitmapFont font = (BitmapFont) _Ng.Loader.getAsset("LoadScene/fonts/impact.fnt").getData();
        style.font = font;
        _TextArea = new TextArea("Text", style);
    }

    @Override
    public ComponentUITextArea remove() {
        getOwner().removeComponent(ComponentUIWidget.class);
        _Owner.removeComponent(this.getClass(), Id);
        return this;
    }

    @Override
    protected Actor getActor() {
        return _TextArea;
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
