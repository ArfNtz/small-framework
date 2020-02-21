package fr.toyframework.cache;

public abstract class A_Cache {

	public abstract void store(Object key,Object group,Object val);

	public abstract Object retrieve(Object key,Object group);
	public abstract Object retrieveGroup(Object group);

	public abstract boolean contains(Object group);
	public abstract boolean contains(Object key,Object group);

	public abstract void clear();
	public abstract void clear(Object key,Object group);
	public abstract void clearGroup(Object group);
/*	
	addListener
	removeListener
*/	
}
