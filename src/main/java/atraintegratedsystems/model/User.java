package atraintegratedsystems.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotEmpty
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false,unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = {@JoinColumn(name="USER_ID", referencedColumnName="ID")},
    inverseJoinColumns={@JoinColumn(name = "ROLE_ID" ,referencedColumnName="ID")})
    private List<Role> roles;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="organization_id",referencedColumnName = "organization_id")
    private Organization organization;
    public User(User user) {
        this.id = id;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.organization=user.getOrganization();
    }
    public User(){
    }

}
