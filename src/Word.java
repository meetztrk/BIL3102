

public class Word {
    private String value;
    private int frequence;


    public Word(String value){
        this.value = value;
        frequence = 0;
    }

    public void increase(){
        frequence++;
    }

    public int getFrequence(){
        return frequence;
    }

    @Override
    public boolean equals(Object o) {
        return value.equals(((Word)o).value);
    }
}
