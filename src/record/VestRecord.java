package record;

import java.math.BigDecimal;

import helpers.MethodHelpers;

/**
 * Created by vprocure on 7/11/2016.
 */
public class VestRecord extends ActionRecord {
    private BigDecimal grantPrice;

    @Override
    public void build(String[] columns) {
        RecordAction action = RecordAction.parseAction(columns[0]);
        if(action != RecordAction.VEST){
            throw new IllegalArgumentException("Not a vesting record");
        }

        super.setAction(RecordAction.VEST);
        super.setEmployee(columns[1]);
        super.setRecordDate(columns[2]);
        super.setUnitsOrMultiplier(columns[3]);

        this.setGrantPrice(columns[4]);
    }

    private void setGrantPrice(String price){
        MethodHelpers.checkParameterForNull(price, "Grant price cannot be null");
        this.grantPrice = new BigDecimal(price);
    }

    public BigDecimal getGrantPrice(){
        return this.grantPrice;
    }

    @Override
    public void accept(IRecordVisitor visitor) {
        visitor.visit(this);
    }
}
