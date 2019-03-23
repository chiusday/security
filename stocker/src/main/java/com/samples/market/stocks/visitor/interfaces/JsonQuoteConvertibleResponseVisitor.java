package com.samples.market.stocks.visitor.interfaces;

/***
 * State changes before and/or after conversion that are specific to abstracted
 * JsonQuoteConvertibleResponseModel
 * @author chiusday
 *
 * @param <T>
 */
public abstract class JsonQuoteConvertibleResponseVisitor<T> 
		extends ConvertibleResponseVisitor<T> {
	
	/***
	 * Any state changes or casting conversion should be done here
	 */
	@Override
	public void visit(JsonQuoteConvertibleResponse<T> visitorModel) {
		//state changes before conversion, if any, goes here.
		visitorModel.setHasError(false);
		super.visit((ConvertibleResponse<T>) visitorModel);
		//state changes, if any, goes here.
	}
}
