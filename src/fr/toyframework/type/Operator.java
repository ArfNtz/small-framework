package fr.toyframework.type;


public class Operator extends Chars {
	private static final long serialVersionUID = 3292993849959464173L;

	public static int TYPE_ARITHMETIC 		= 1;
	public static int TYPE_LOGIC 			= 2;
	public static int TYPE_SQL 				= 3;
	public static int TYPE_SQL_FUNCTION 	= 4;
	public static int TYPE_GLOBAL_FUNCTION 	= 5;
	
	private int type;
	
	public static Operator DISTINCT	= new Operator(" DISTINCT ",TYPE_GLOBAL_FUNCTION);

	public static Operator HAVING 	= new Operator(" HAVING ",TYPE_SQL);
	public static Operator ORDERBY 	= new Operator(" ORDER BY ",TYPE_SQL);
	public static Operator GROUPBY 	= new Operator(" GROUP BY ",TYPE_SQL);	

	public static Operator SUM	 	= new Operator(" SUM",TYPE_SQL_FUNCTION); //NAU - NAUD-8GDJA2 - 02/05/2011
	public static Operator COUNT 	= new Operator(" COUNT",TYPE_SQL_FUNCTION);
	public static Operator MIN 		= new Operator(" MIN",TYPE_SQL_FUNCTION);
	public static Operator MAX 		= new Operator(" MAX",TYPE_SQL_FUNCTION);
	
	public static Operator UPPER	= new Operator(" UPPER",TYPE_SQL_FUNCTION);
	public static Operator LOWER	= new Operator(" LOWER",TYPE_SQL_FUNCTION);

	public static Operator ASC 		= new Operator(" ASC ",TYPE_SQL);
	public static Operator DESC 	= new Operator(" DESC ",TYPE_SQL);

	public static Operator IN 		= new Operator(" IN ",TYPE_ARITHMETIC);
	public static Operator NOT_IN 	= new Operator(" NOT IN ",TYPE_ARITHMETIC);
	public static Operator LIKE 	= new Operator(" LIKE ",TYPE_ARITHMETIC);

	public static Operator AND 		= new Operator(" AND ",TYPE_LOGIC);
	public static Operator OR 		= new Operator(" OR ",TYPE_LOGIC);

	public static Operator OB 		= new Operator(" ( ",TYPE_ARITHMETIC);
	public static Operator CB 		= new Operator(" ) ",TYPE_ARITHMETIC);
	public static Operator COMMA	= new Operator(" , ",TYPE_ARITHMETIC); //NAU - NAUD-8H9KQP - 08/06/2011

	public static Operator IS_NULL 	= new Operator(" IS NULL ",TYPE_ARITHMETIC);
	public static Operator IS_NOT_NULL= new Operator(" IS NOT NULL ",TYPE_ARITHMETIC);

	public static Operator GT 		= new Operator(" > ",TYPE_ARITHMETIC);
	public static Operator LT 		= new Operator(" < ",TYPE_ARITHMETIC);
	public static Operator GEQ 		= new Operator(" >= ",TYPE_ARITHMETIC);
	public static Operator LEQ 		= new Operator(" <= ",TYPE_ARITHMETIC);
	public static Operator EQ 		= new Operator(" = ",TYPE_ARITHMETIC);
	public static Operator NEQ 		= new Operator(" <> ",TYPE_ARITHMETIC);

	private Operator(String operator,int type) {
		super(operator);
		this.type = type;
	}
	
	public int getType() {return type;}
}
