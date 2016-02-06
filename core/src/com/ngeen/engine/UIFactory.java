package com.ngeen.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ngeen.component.ComponentCamera;
import com.ngeen.component.ui.ComponentUIStage;

/**
 * @hidden
 * @author Dragos
 *
 */
public class UIFactory {
	protected final InputMultiplexer _InputMultiplexer = new InputMultiplexer();
	private final Ngeen _Ng;
	protected final SpriteBatch _SpriteBatch;
	private Viewport _Viewport;

	public UIFactory(Ngeen _Ng) {
		this._Ng = _Ng;
		_SpriteBatch = new SpriteBatch();
	}

	protected void createMultiplexer() {
		Gdx.input.setInputProcessor(_InputMultiplexer);
		_InputMultiplexer.addProcessor(new GestureDetector(_Ng._SystemBuilder._SceneSystem));
		if (_Ng._SystemBuilder._OverlaySystem != null)
			_InputMultiplexer.addProcessor(_Ng._SystemBuilder._OverlaySystem);
	}

	public void createStage(ComponentUIStage stage) {
		Camera cam = _Ng.getEntity("~UICAMERA").getComponent(ComponentCamera.class).Camera;
		if (_Viewport == null) {
			_Viewport = new ScreenViewport(cam);
		}
		stage.setStage(_Viewport, _SpriteBatch, _InputMultiplexer);
	}

	protected void resize(int w, int h) {
		if (_Viewport != null)
			_Viewport.update(w, h);
	}
}
