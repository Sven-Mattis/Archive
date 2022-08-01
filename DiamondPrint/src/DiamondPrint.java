public class DiamondPrint <T> {

    private int max;

    public DiamondPrint (T i) {
        this.max = this.getValue(i)-1;
    }

    private int getValue (T i) {
        String valueAsString = i.toString().toLowerCase();
        if (valueAsString.replaceAll("( |^)[\\d ]+( |$)", "").equals(""))
            return Math.min(Integer.parseInt(valueAsString), 9);

        switch (valueAsString) {
            case "a":
                return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
            case "i":
                return 9;
        }

        return 0;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 65; i < 65 + this.max; i++) {
            int dots = this.max - ( i - 65 );
            int spaces = (this.max - dots)*2-1;
            for (int j = 0; j < dots; j++) {
                res.append(" ");
            }
            if( i - 65 != 0)
                res.append((char) i );
            for (int j = 0; j < spaces; j++) {
                res.append(".");
            }
            res.append((char) i );
            for (int j = 0; j < dots; j++) {
                res.append(" ");
            }
            res.append("\n");
        }
        for (int i = 65 + this.max; i >= 65; i--) {
            int dots = this.max - ( i - 65 );
            int spaces = (this.max - dots)*2-1;
            for (int j = 0; j < dots; j++) {
                res.append(" ");
            }
            if( i - 65 != 0)
                res.append((char) i );
            for (int j = 0; j < spaces; j++) {
                res.append(".");
            }
            res.append((char) i );
            for (int j = 0; j < dots; j++) {
                res.append(" ");
            }
            res.append("\n");
        }

        return res.toString();

    }

}
