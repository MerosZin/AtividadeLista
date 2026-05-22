public class Professor extends Usuario {

    private int cargaHoraria;
    private String siape;

    public Professor(String nome, String cpf, String dataNascimento, int cargaHoraria, String siape) {
        super(nome, cpf, dataNascimento);
        this.cargaHoraria = cargaHoraria;
        this.siape = siape;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    @Override
    public String toString() {
        return "[Professor] " + super.toString() +
                ", Carga Horária: " + cargaHoraria +
                ", SIAPE: " + siape;
    }
}