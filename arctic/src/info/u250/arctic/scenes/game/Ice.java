package info.u250.arctic.scenes.game;

import info.u250.arctic.RandomHelper;
import info.u250.arctic.Values;
import info.u250.arctic.scenes.game.ices.AbstractIce;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Ice extends Group {
	public AbstractIce getIce(){
		return _ice;
	}
	
	AbstractIce _ice ;
	
	public static Ice lastIce = null;
	
	private static Array<Ice> list = new Array<Ice>();
	private static Pool<Ice> pool = new Pool<Ice>() {
		@Override
		protected Ice newObject() {
			return new Ice();
		}
		public Ice obtain() {
			Ice umbrella = super.obtain();
			umbrella.reset();
			list.add(umbrella);
			lastIce = umbrella;
			return umbrella;
		};
	};
	public static void $free(Ice object){
		pool.free(object);
		list.removeValue(object, false);
		object.remove();
	}
	public  static Array<Ice> $list(){
		return list;
	}
	public  static Ice $(){
		return pool.obtain();
	}
	public void reset(){
		this.clear();
		try {
			this._ice = AbstractIce.class.cast(Class.forName("info.u250.arctic.scenes.game.ices.Ice"+(RandomHelper.random.nextInt(11)+1)).newInstance());
			this.addActor(_ice);
			this.setSize(this._ice.getWidth(), this._ice.getHeight());
			this.setOrigin(this.getWidth()/2, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private Ice(){
		this.reset();
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		this.setX(this.getX()+Values.Speed *delta);
	}
}
