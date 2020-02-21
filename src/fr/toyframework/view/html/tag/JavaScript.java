package fr.toyframework.view.html.tag;

public class JavaScript extends Script {

	public static String TYPE_JAVASCRIPT = "text/javascript";

	public JavaScript() {
		super(TYPE_JAVASCRIPT,null);
	}
	public JavaScript(String src_file_url) {
		super(TYPE_JAVASCRIPT,src_file_url);
	}

}
