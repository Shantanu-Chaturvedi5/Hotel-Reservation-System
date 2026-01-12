import java.util.Scanner;

public class Payment {
    private static final double PROMO_CODE_DISCOUNT = 10.0; // 10% discount
    
    public static String selectPaymentMethod(Scanner sc) {
        System.out.println("\n--- Payment Methods ---");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. UPI");
        System.out.println("4. Net Banking");
        System.out.print("Select payment method (1-4): ");
        
        int choice = sc.nextInt();
        sc.nextLine();
        
        return switch (choice) {
            case 1 -> "Credit Card";
            case 2 -> "Debit Card";
            case 3 -> "UPI";
            case 4 -> "Net Banking";
            default -> "Credit Card";
        };
    }
    
    public static double applyPromoCode(Scanner sc) {
        System.out.print("Do you have a promo code? (yes/no): ");
        String choice = sc.nextLine();
        
        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter promo code: ");
            String promoCode = sc.nextLine();
            if (promoCode.equalsIgnoreCase("WELCOME10")) {
                System.out.println("✓ Promo code applied! " + PROMO_CODE_DISCOUNT + "% discount.");
                return PROMO_CODE_DISCOUNT;
            } else {
                System.out.println("✗ Invalid promo code.");
            }
        }
        return 0;
    }

    public static boolean processPayment(double amount, Scanner sc) {
        System.out.println("\n════════════════════════════════════");
        System.out.println("         PAYMENT GATEWAY");
        System.out.println("════════════════════════════════════");
        System.out.println("Total Amount: ₹" + String.format("%.2f", amount));
        
        String paymentMethod = selectPaymentMethod(sc);
        double discount = applyPromoCode(sc);
        
        double finalAmount = amount - (amount * discount / 100);
        System.out.println("\nDiscount Applied: ₹" + String.format("%.2f", amount * discount / 100));
        System.out.println("Final Amount: ₹" + String.format("%.2f", finalAmount));
        System.out.println("Payment Method: " + paymentMethod);
        
        System.out.print("\nConfirm payment? (yes/no): ");
        String confirm = sc.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            System.out.println("✓ Payment successful!");
            return true;
        } else {
            System.out.println("✗ Payment cancelled.");
            return false;
        }
    }
}