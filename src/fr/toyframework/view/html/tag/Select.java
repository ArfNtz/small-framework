package fr.toyframework.view.html.tag;

import java.util.List;
import java.util.Map;

import fr.toyframework.action.A_Action;
import fr.toyframework.type.A_Type;
import fr.toyframework.view.ViewException;

public class Select extends A_CompositeTag {

	public Map<? extends A_Type,? extends A_Type> values_labels;
	public List<? extends A_Type> selectedValues;
	//public boolean emptyOption=false;
	public boolean multiple=false;
	public int multipleSize=0;

	public Select (
		String id,
		String style_class,
		Map<? extends A_Type,? extends A_Type> values_labels,
		List<? extends A_Type> selectedValues
	) {
		super("select",id,style_class,null);
		this.values_labels = values_labels;
		this.selectedValues = selectedValues;
	}

	public void build(A_Action a) throws ViewException {
		if (multiple) { addAttribute("multiple",null); addAttribute("size",""+multipleSize); }
		boolean selected = false;
		for (A_Type value : values_labels.keySet()) {
			if (selectedValues!=null) selected = selectedValues.contains(value);
			add(new Option(value,style_class,values_labels.get(value),selected));	
		}
	} 
}
