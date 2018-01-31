import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Puzzle {

    protected String [][] board;
    // Table to determine if a slot is mutable
    protected boolean [][] mutable;
    private final int ROWS;
    private final int COLUMNS;
    private final int BOXWIDTH;
    private final int BOXHEIGHT;
    private final int LEVEL;
    private final String [] VALIDVALUES;


    public Puzzle(int level) {
        this.ROWS = 9;
        this.COLUMNS = 9;
        this.BOXWIDTH = 3;
        this.BOXHEIGHT = 3;
        this.LEVEL = level;
        this.VALIDVALUES = new String[] {"1","2","3","4","5","6","7","8","9",""};
        this.board = new String[ROWS][COLUMNS];
        this.mutable = new boolean[ROWS][COLUMNS];
        initializeBoard();
    }

    public int getNumRows() {
        return this.ROWS;
    }

    public int getNumColumns() {
        return this.COLUMNS;
    }

    public int getBoxWidth() {
        return this.BOXWIDTH;
    }

    public int getBoxHeight() {
        return this.BOXHEIGHT;
    }

    public String [] getValidValues() {
        return this.VALIDVALUES;
    }

    public void makeMove(int row,int col,String value,boolean isMutable) {
        if(this.isValidValue(value) && this.isSlotMutable(row, col)) {
            this.board[row][col] = value;
            this.mutable[row][col] = isMutable;
        }
    }

    public boolean isSlotAvailable(int row,int col) {
        return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
    }

    public boolean isSlotMutable(int row,int col) {
        return this.mutable[row][col];
    }

    public String getValue(int row,int col) {
        if(this.inRange(row,col)) {
            return this.board[row][col];
        }
        return "";
    }

    public String [][] getBoard() {
        return this.board;
    }

    private boolean isValidValue(String value) {
        for(String str : this.VALIDVALUES) {
            if(str.equals(value)) return true;
        }
        return false;
    }

    public boolean inRange(int row,int col) {
        return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
    }

    public boolean boardFull() {
        for(int r = 0;r < this.ROWS;r++) {
            for(int c = 0;c < this.COLUMNS;c++) {
                if(this.board[r][c].equals("")) return false;
            }
        }
        return true;
    }

    public void makeSlotEmpty(int row,int col) {
        this.board[row][col] = "";
    }


    private void initializeBoard() {
        FileReader reader = null;
        try {
            String address = "src/puzzels/";
            if(LEVEL == 1){
                address += "e";
            }else
                if (LEVEL == 2){
                address += "m";
            }else
                address += "h";

            Random random = new Random();
            address += random.nextInt(5)+1;
            reader = new FileReader(address);
            int c = 0;
            int notMutable = reader.read();
        for(int row = 0;row < this.ROWS;row++) {
            for(int col = 0;col < this.COLUMNS;col++) {
                if((c = reader.read()) != -1){
                    this.board[row][col] = String.valueOf((char)c);
                    if(c != notMutable){
                        this.mutable[row][col] = false;
                    } else {
                        this.mutable[row][col] = true;
                    }
                }
            }
        }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader != null)
                    reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}