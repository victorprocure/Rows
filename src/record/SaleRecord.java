package record;

import java.math.BigDecimal;

import helpers.MethodHelpers;

/**
 * Created by vprocure on 7/11/2016.
 */
public class SaleRecord extends ActionRecord {
    private BigDecimal salePrice;

    @Override
    public void build(String[] columns) {
        RecordAction action = RecordAction.parseAction(columns[0]);
        if(action != RecordAction.SALE){
            throw new IllegalArgumentException("Not a sales record");
        }

        super.setAction(RecordAction.SALE);
        super.setEmployee(columns[1]);
        super.setRecordDate(columns[2]);
        super.setUnitsOrMultiplier(columns[3]);

        this.setSalePrice(columns[4]);
    }

    private void setSalePrice(String price){
        MethodHelpers.checkParameterForNull(price, "Sale price cannot be null");
        this.salePrice = new BigDecimal(price);
    }

    public BigDecimal getSalePrice(){
        return this.salePrice;
    }

    @Override
    public void accept(IRecordVisitor visitor) {
        visitor.visit(this);
    }
}
