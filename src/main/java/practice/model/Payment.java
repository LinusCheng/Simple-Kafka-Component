package practice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {

    private String paymentId;
    private String creditorAccNum;
    private String debtorAccNum;
    private String currency;
    private String amount;

}
