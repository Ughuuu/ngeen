package com.gem.engine;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gem.component.ComponentCamera;
import com.gem.component.ComponentMaterial;
import com.gem.component.ComponentMesh;
import com.gem.component.ComponentPoint;
import com.gem.component.ComponentRigid;
import com.gem.component.ComponentScript;
import com.gem.component.ComponentSprite;
import com.gem.component.ComponentVariable;
import com.gem.component.ui.ComponentUIBase;
import com.gem.component.ui.ComponentUIButton;
import com.gem.component.ui.ComponentUIButtonGroup;
import com.gem.component.ui.ComponentUICheckBox;
import com.gem.component.ui.ComponentUIContainer;
import com.gem.component.ui.ComponentUIDialog;
import com.gem.component.ui.ComponentUIHorizontalGroup;
import com.gem.component.ui.ComponentUIImage;
import com.gem.component.ui.ComponentUIImageButton;
import com.gem.component.ui.ComponentUILabel;
import com.gem.component.ui.ComponentUILayout;
import com.gem.component.ui.ComponentUIList;
import com.gem.component.ui.ComponentUIProgressBar;
import com.gem.component.ui.ComponentUIScrollPane;
import com.gem.component.ui.ComponentUISelectBox;
import com.gem.component.ui.ComponentUISlider;
import com.gem.component.ui.ComponentUISplitPane;
import com.gem.component.ui.ComponentUIStack;
import com.gem.component.ui.ComponentUIStage;
import com.gem.component.ui.ComponentUITable;
import com.gem.component.ui.ComponentUITextArea;
import com.gem.component.ui.ComponentUITextButton;
import com.gem.component.ui.ComponentUITextField;
import com.gem.component.ui.ComponentUITouchpad;
import com.gem.component.ui.ComponentUITree;
import com.gem.component.ui.ComponentUIVerticalGroup;
import com.gem.component.ui.ComponentUIWidget;
import com.gem.component.ui.ComponentUIWindow;
import com.gem.entity.Entity;

/**
 * @author Dragos
 * @hidden Information data about engine.
 */
public class EngineInfo {

	public static final int CommandsSize = 1000000;
	public final static int ComponentCache = 1000;
	public final static Map<Class<?>, Integer> ComponentIndexMap = new HashMap<Class<?>, Integer>() {
		{
			int i = 0;
			put(ComponentCamera.class, i++);
			put(ComponentMaterial.class, i++);
			put(ComponentMesh.class, i++);
			put(ComponentPoint.class, i++);
			put(ComponentRigid.class, i++);
			put(ComponentScript.class, i++);
			put(ComponentSprite.class, i++);
			put(ComponentVariable.class, i++);

			put(ComponentUIStage.class, i++);
			put(ComponentUILayout.class, i++);
			put(ComponentUIWidget.class, i++);
			put(ComponentUIContainer.class, i++);
			put(ComponentUIHorizontalGroup.class, i++);
			put(ComponentUIScrollPane.class, i++);
			put(ComponentUISplitPane.class, i++);
			put(ComponentUIStack.class, i++);
			put(ComponentUITable.class, i++);
			put(ComponentUITree.class, i++);
			put(ComponentUIVerticalGroup.class, i++);

			put(ComponentUIBase.class, i++);
			put(ComponentUIButton.class, i++);
			put(ComponentUIButtonGroup.class, i++);
			put(ComponentUICheckBox.class, i++);
			put(ComponentUIDialog.class, i++);
			put(ComponentUIImage.class, i++);
			put(ComponentUIImageButton.class, i++);
			put(ComponentUILabel.class, i++);
			put(ComponentUIList.class, i++);
			put(ComponentUIProgressBar.class, i++);
			put(ComponentUISelectBox.class, i++);
			put(ComponentUISlider.class, i++);
			put(ComponentUITextArea.class, i++);
			put(ComponentUITextButton.class, i++);
			put(ComponentUITextField.class, i++);
			put(ComponentUITouchpad.class, i++);
			put(ComponentUIWindow.class, i++);

		}
	};
	/**
	 * If set to true, program will output a lot of stuff and have debug renders
	 * set up.
	 */
	public static final boolean Debug = true;
	public final static int EntitiesCache = 500;
	/**
	 * Mathematical constant. Very small float number.
	 */
	public static final float Epsilon = 1e-10f;
	/**
	 * Frames per second.
	 */
	public static final float Fps = 120;
	public final static Map<Integer, Class<?>> IndexComponentMap = new HashMap<Integer, Class<?>>() {
		{
			for (Map.Entry<Class<?>, Integer> entry : ComponentIndexMap.entrySet()) {
				put(entry.getValue(), entry.getKey());
			}
		}
	};
	/**
	 * MeterPerPixel. Used for Box2d. 1/PixelPerMeter.
	 */
	public static final float MeterPerPixel = 1.f / 100;
	/**
	 * Milliseconds passed for each iteration.
	 */
	public static final float Ms = 1.f / Fps;
	/**
	 * PixelPerMeter. Used for Box2D.
	 */
	public static final float PixelPerMeter = 100;
	public final static int TotalComponents = ComponentIndexMap.size();
	public static boolean Android = false;
	public static boolean Applet = false;
	public static Color BackgroundColor = new Color(.6f, .2f, .1f, 1);
	/**
	 * Game Speed.
	 */
	public static float GameSpeed = 1;
	/**
	 * Gravity. To be used in Box2D.
	 */
	public static Vector2 Gravity = new Vector2(0, -1f);
	/**
	 * Screen Height.
	 */
	public static float Height = 600;
	public static float ScreenHeight;
	public static float ScreenWidth;
	/**
	 * Value goes from 0 to 1, where 0 is mute and 1 is normal.
	 */
	public static float Volume = 1;

	/**
	 * Screen Width.
	 */
	public static float Width = 1024;

	protected static void makeBasicEntities(Gem ng, UIFactory UIBuilder) {
		ScreenWidth = Gdx.graphics.getWidth();
		ScreenHeight = Gdx.graphics.getHeight();

		Width = Height / ScreenHeight * ScreenWidth;

		if (ng == null || ng.EntityBuilder == null) {
			return;
		}
		Entity camera = ng.getEntity("~CAMERA");
		Entity uiCamera = ng.getEntity("~UICAMERA");
		if (camera == null || uiCamera == null) {
			camera = ng.EntityBuilder.makeEntity("~CAMERA");
			uiCamera = ng.EntityBuilder.makeEntity("~UICAMERA");
		}
		
		ComponentCamera cam = camera.addComponent(ComponentCamera.class);
		cam.createCamera(Width, Height);
		ComponentPoint pos = camera.addComponent(ComponentPoint.class);

		ComponentCamera uiCam = uiCamera.addComponent(ComponentCamera.class);
		uiCam.createCamera(ScreenWidth, ScreenHeight);
		ComponentPoint uiPos = uiCamera.addComponent(ComponentPoint.class);
		uiPos.setPosition(new Vector3(ScreenWidth / 2, ScreenHeight / 2, 0));

		// camera probably changed, reset it to viewport
		UIBuilder.resize((int) ScreenWidth, (int) ScreenHeight);
	}

	protected static void makeOptionalEntities(Gem ng) {
		if (Debug) {
			ng.Loader.enqueFolder("engine/");
			ng.Loader.addFolder("engine/");
			ng.Loader.finish();
		}
	}
}
