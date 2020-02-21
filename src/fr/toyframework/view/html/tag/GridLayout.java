package fr.toyframework.view.html.tag;

import java.util.ArrayList;
import java.util.List;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.TypeException;
import fr.toyframework.view.ViewException;

public class GridLayout extends Table {

	private int nbCols;
	private int nbRows;
	
	private static String style_class = "gridlayout";
	
	protected List<A_Tag> components = new ArrayList<A_Tag>();
	
	public GridLayout(String id,int nbCols,int nbRows) {
		super(id,style_class);
		this.nbCols = nbCols;
		this.nbRows = nbRows;
	}

	public void build(A_Action a) throws ViewException, TypeException {
		super.build(a);
		Tr tr = new Tr(null,style_class);
		Td td = new Td(null,style_class);
		for (int i=0; i<nbRows; i++) {
			for (int j=0; j<nbCols; j++) {
				int _index = i*nbCols+j;
				if (_index<components.size()) tr.add(td.add(components.get(_index)));
				td.clear();
			}
			super.add(tr);
			tr.clear();
		}
	}

	public A_CompositeTag addFirst(A_Tag tag) throws ViewException {
		try {
			components.add(0,(A_Tag)tag.clone());
			return this;
		} catch (CloneNotSupportedException e) {
			throw new ViewException(e);
		}
	}
	public A_CompositeTag add(A_Tag tag) throws ViewException {
		try {
			components.add((A_Tag)tag.clone());
			return this;
		} catch (CloneNotSupportedException e) {
			throw new ViewException(e);
		}
	}
	public A_CompositeTag set(A_Tag tag) throws ViewException {
		try {
			components.clear();
			components.add((A_Tag)tag.clone());
			return this;
		} catch (CloneNotSupportedException e) {
			throw new ViewException(e);
		}
	}
	public A_CompositeTag clear() {
		super.clear();
		components.clear();
		return this;
	}
}
