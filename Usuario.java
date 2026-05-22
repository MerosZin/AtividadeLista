public class Usuario {

    protected String nome;
    protected String cpf;
    protected String dataNascimento;

    public Usuario(String nome, String cpf, String dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
               ", CPF: " + cpf +
               ", Data Nascimento: " + dataNascimento;
    }
}