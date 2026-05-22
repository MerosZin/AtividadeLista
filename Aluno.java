public class Aluno extends Usuario {

    private String matricula;
    private double cre;

    public Aluno(String nome, String cpf, String dataNascimento, String matricula, double cre) {
        super(nome, cpf, dataNascimento);
        this.matricula = matricula;
        this.cre = cre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setCre(double cre) {
        this.cre = cre;
    }

    @Override
    public String toString() {
        return "[Aluno] " + super.toString() +
                ", Matrícula: " + matricula +
                ", CRE: " + cre;
    }
}