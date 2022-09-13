import org.apache.lucene.demo.IndexFiles;
import org.apache.lucene.demo.SearchFiles;

public class Main {
    public static void main(String[] args) throws Exception {
        task4();
    }

    public static void task3() {
        String[] indexArguments = {"-index", "assets/index/task3", "-docs", "assets/DataAssignment4"};
        IndexFiles.main(indexArguments);

        for (String query : new String[]{"God", "circumstances", "claims of duty"}) {
            long start = System.nanoTime();
            String[] searchArguments = {"-index", "assets/index/task3", "-query", query};
            SearchFiles.main(searchArguments);
            long end = System.nanoTime();
            long timeDif = (end-start)/1000000;
            System.out.println("The query '" + query + "' used " + timeDif+ " ms");
        }
    }

    public static void task4() {
        String[] indexArguments = {"-index", "assets/index/task4", "-docs", "assets/maildir"};
        IndexFiles.main(indexArguments);

        for (String query : new String[]{"Norwegian University Science Technology", "Information Retrieval"}) {
            long start = System.nanoTime();
            String[] searchArguments = {"-index", "assets/index/task4", "-query", query};
            SearchFiles.main(searchArguments);
            long end = System.nanoTime();
            long timeDif = (end-start)/1000000;
            System.out.println("The query '" + query + "' used " + timeDif+ " ms");
        }
    }
}
