package explore.springboot.data.transaction.exception.unchecked;

public class TransactionRuntimeException_1 extends RuntimeException{
    @Override
    public String getMessage() {
        return "抛出的异常: TransactionRuntimeException_1().";
    }
}
