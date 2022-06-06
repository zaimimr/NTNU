class Valuta{
    private String name;
    private double rate;
    private int unit;

    public Valuta(String name, double rate, int unit){
        this.name = name;
        this.rate = rate;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public int getUnit() {
        return unit;
    }

    public double convert(Valuta v, double value){
        double toRate = v.getRate() / v.getUnit();
        double fromRate = getRate() / getUnit();

        return (value/fromRate)*toRate;
    }


    public String toString(){
        return name + ": " + rate;
    }
}