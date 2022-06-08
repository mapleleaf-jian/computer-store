package ms.store.util;

/**
 * @author maple
 * @create 2022-05-13 21:40
 */
public enum PayStatus {
    UnPaid(0, "unpaid"),
    Paid(1, "paid");

    private final Integer status;
    private final String statusDesc;

    PayStatus(Integer status, String statusDesc) {
        this.status = status;
        this.statusDesc = statusDesc;
    }
}
