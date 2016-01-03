package com.ngeen.systems;

import com.ngeen.engine.Constant;

public class LogSystem extends EntityProcessingSystem {
	private ComponentMapper<TransformComponent> transformMapper;
	private ComponentMapper<TagComponent> tagMapper;

	@SuppressWarnings("unchecked")
	public LogSystem() {
		super(Aspect.all(TransformComponent.class));
	}

	@Override
	public void begin() {
		Constant.BATCH.begin();
		Constant.DEBUG_FONT.getData().setScale(0.1f);
	}

	@Override
	protected void process(Entity e) {
		TransformComponent transform = transformMapper.get(e);
		String text = tagMapper.get(e).name;
		Constant.DEBUG_FONT.draw(Constant.BATCH, text, transform.position.x,
				transform.position.y, 0, 1, false);
	}

	@Override
	public void end() {
		Constant.BATCH.end();
	}
}
