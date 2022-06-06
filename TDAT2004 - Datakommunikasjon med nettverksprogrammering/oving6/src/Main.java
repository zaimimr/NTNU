
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        AccountDao account_dao = null;
        try {
            emf = Persistence.createEntityManagerFactory("account_entity");
            account_dao = new AccountDao(emf);

            account_dao.deleteAll();
            Account acc1 = new Account();
            acc1.setAccountNumber(1);
            acc1.setOwner("Zaim Imran");
            acc1.setBalance(500);
            account_dao.addNewAccount(acc1);
            System.out.println("\nAdded account 1");

            Account acc2 = new Account();
            acc2.setAccountNumber(2);
            acc2.setOwner("Jostein Kløgetveit");
            acc2.setBalance(10000);
            account_dao.addNewAccount(acc2);
            System.out.println("\nAdded account 2");

            System.out.println("\nPrinting all accounts:");
            System.out.println(account_dao.getAllAccounts());

            System.out.println("\nPrinting all acounts with balance higher than 2000kr");
            System.out.println(account_dao.getAllAccounts(2000.0));

            System.out.println("\nChanging the owner of accounts 2");
            acc2.setOwner("Kristian Nilsen");
            //account_dao.mergeAccount(acc2);

            Runnable run = () -> {
                try {
                    EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("account_entity");
                    AccountDao account_dao_1 = new AccountDao(emf1);
                    int amount = 1000;
                    acc2.withdraw(amount);
                    acc1.add(amount);
                    System.out.println("\nSending 1000kr");

                    account_dao_1.mergeAccount(acc2);
                    account_dao_1.mergeAccount(acc1);

                    System.out.println("\nPrinting all accounts to verify that the change of money went through");
                    System.out.println(account_dao_1.getAllAccounts());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            Thread t1 = new Thread(run);
            Thread t2 = new Thread(run);
            Thread t3 = new Thread(run);
            t1.start();
            t2.start();
            t3.start();
        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            assert emf != null;
            emf.close();
        }
    }
}