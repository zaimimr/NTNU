public class Automat {
    int[] tilstander;
    char[] inputAlfabet;
    int[] aksepterteTilstander;
    int[][] nesteTilstand;


    public Automat(int[] tilstander, char[] inputAlfabet, int[] aksepterteTilstander, int[][] nesteTilstand) {
        this.tilstander = tilstander;
        this.inputAlfabet = inputAlfabet;
        this.aksepterteTilstander = aksepterteTilstander;
        this.nesteTilstand = nesteTilstand;
    }

    public boolean sjekkInput(String s) {
        if (s == "") {
            return false;
        }
        return (finnSiste(s) == (aksepterteTilstander[0]));
    }

    private int finnSiste(String s) {
        String[] tab = s.split("");
        int curr = 0;
        int temp = -1;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].charAt(0) == (this.inputAlfabet[0])) {
                temp = 0;
            } else if (tab[i].charAt(0) == (this.inputAlfabet[1])) {
                temp = 1;
            }
            if(temp != -1){
                curr = nesteTilstand[temp][curr];
            }
        }
        return curr;

    }

    public static void main(String[] args) {
        System.out.println("Oppgave a:");
        int[] tilstander = {0,1,2,3};
        char[] inputAlfabet = {'0', '1'};
        int[] aksepterteTilstander = {2};
        String[] inputStrenger = {"", "010", "111", "010110", "001000"};
        int[][] nesteTilstand = new int[inputAlfabet.length][tilstander.length];
        nesteTilstand[0][0] = 1;
        nesteTilstand[0][1] = 1;
        nesteTilstand[0][2] = 2;
        nesteTilstand[0][3] = 3;
        nesteTilstand[1][0] = 3;
        nesteTilstand[1][1] = 2;
        nesteTilstand[1][2] = 3;
        nesteTilstand[1][3] = 3;

        Automat automat = new Automat(tilstander, inputAlfabet,aksepterteTilstander, nesteTilstand);
        for (String s : inputStrenger) {
            System.out.println(automat.sjekkInput(s));
        }

        System.out.println("Oppgave b:");
        int[] tilstander1 = {0,1,2,3,4};
        char[] inputAlfabet1 = {'a', 'b'};
        int[] aksepterteTilstander1 = {3};
        String[] inputStrenger1 = {"abbb", "aaab", "babab"};
        int[][] nesteTilstand1 = new int[inputAlfabet1.length][tilstander1.length];
        /*
        a = 0
        b = 1
         */
        nesteTilstand1[0][0] = 2;
        nesteTilstand1[0][1] = 3;
        nesteTilstand1[0][2] = 4;
        nesteTilstand1[0][3] = 3;
        nesteTilstand1[0][4] = 4;
        nesteTilstand1[1][0] = 1;
        nesteTilstand1[1][1] = 4;
        nesteTilstand1[1][2] = 3;
        nesteTilstand1[1][3] = 3;
        nesteTilstand1[1][4] = 4;

        Automat automat1 = new Automat(tilstander1, inputAlfabet1,aksepterteTilstander1, nesteTilstand1);
        for (String s : inputStrenger1) {
            System.out.println(automat1.sjekkInput(s));
        }


    }
}

