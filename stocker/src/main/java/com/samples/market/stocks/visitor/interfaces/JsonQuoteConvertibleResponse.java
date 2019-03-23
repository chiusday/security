package com.samples.market.stocks.visitor.interfaces;

/***
 * ConvertibleResponse for Quotes in Json format.
 * This abstract is necessary for corresponding Visitor to intercept visitorModel before
 * being visited by ConvertibleResponseVisitor.
 * @author chiusday
 *
 * @param <T>
 */
public abstract class JsonQuoteConvertibleResponse<T> extends ConvertibleResponse<T> {

	@Override
	public void accept(IVisitor<T> visitor) {
		visitor.visit(this);
	}
}
