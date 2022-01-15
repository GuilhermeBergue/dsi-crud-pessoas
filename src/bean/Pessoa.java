package bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pessoa {

   public Pessoa() {}

   public Pessoa( String email, String nome, String telefone, String cpfCnpj ) {
      super();
      this.email = email;
      this.nome = nome;
      this.telefone = telefone;
      this.cpfCnpj = cpfCnpj;
   }


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer codigo;

   private String email;
   private String nome;
   private String telefone;
   private String cpfCnpj;


   public Integer getCodigo() {
      return codigo;
   }
   public void setCodigo( int codigo ) {
      this.codigo = codigo;
   }
   public String getEmail() {
      return email;
   }
   public void setEmail( String email ) {
      this.email = email;
   }
   public String getNome() {
      return nome;
   }
   public void setNome( String nome ) {
      this.nome = nome;
   }
   public String getTelefone() {
      return telefone;
   }
   public void setTelefone( String telefone ) {
      this.telefone = telefone;
   }
   public String getCpfCnpj() {
      return cpfCnpj;
   }
   public void setCpfCnpj( String cpfCnpj ) {
      this.cpfCnpj = cpfCnpj;
   }


}
