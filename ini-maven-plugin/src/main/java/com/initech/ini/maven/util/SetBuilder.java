package com.initech.ini.maven.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetBuilder<T> {

	private final Set<T> result = new HashSet<T>();
	
	public SetBuilder() {
	}
	
	public SetBuilder(Set<T> initial){
		result.addAll(initial);
	}
	
	public SetBuilder<T> addAll(Set<T> add) {
		result.addAll(add);
		return this;
	}
	
	public SetBuilder<T> add(T add) {
		result.add(add);
		return this;
	}
	
	public Set<T> buildImmutable(){
		return Collections.unmodifiableSet(result);
	}
	
	public Set<T> build(){
		Set<T> tmp = new HashSet<T>(result);
		return tmp;
	}
}
