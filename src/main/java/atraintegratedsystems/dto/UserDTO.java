package atraintegratedsystems.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Integer> roleIds;;
    private int organizationId;
}
