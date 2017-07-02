package bank_system.clients.services.payment_exceptions;

/**
 * Payment exceptions was born from here. And will be.
 * Created on 3/22/2017.
 *
 * @author Serhii Petrusha aka Mr_Rism
 * @since   JDK1.8
 */
public class PaymentException extends Exception{
  public PaymentException(){
    super("General payment exception");
  }
  public PaymentException(String exceptionMassage){
    super(exceptionMassage);
  }

  @Override
  public String toString() {
    return "PaymentException{} " + super.toString();
  }
}
