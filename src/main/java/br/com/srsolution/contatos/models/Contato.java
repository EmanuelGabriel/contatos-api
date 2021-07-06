package br.com.srsolution.contatos.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Entity
public class Contato extends ResourceSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Nome não pode está vazio")
	@Column(length = 100, nullable = false)
	@Length(min = 5, max = 100, message = "Nome deve conter entre 5 a 100 caracteres")
	private String nome;

	@NotBlank(message = "E-mail não pode está vazio")
	@Email(message = "E-mail deve ser válido!")
	private String email;

	@NotBlank(message = "Telefone não pode está vazio")
	@Column(length = 18, nullable = false)
	@Length(max = 18, message = "Telefone deve conter no máximo 18 dígitos")
	private String telefone;

	@Override
	public Link getId() {
		return super.getId();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contato other = (Contato) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contato [codigo=" + codigo + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone + "]";
	}

}
